package com.ar.vgmsistemas.view.venta;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ClienteBo;
import com.ar.vgmsistemas.bo.CuentaCorrienteBo;
import com.ar.vgmsistemas.bo.DocumentoBo;
import com.ar.vgmsistemas.bo.DocumentosListaBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.MotivoAutorizacionBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.VendedorBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.bo.VentaDetalleBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CondicionVenta;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.MotivoAutorizacion;
import com.ar.vgmsistemas.entity.Repartidor;
import com.ar.vgmsistemas.entity.Vendedor;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.printer.PedidoPrinter;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.cobranza.MotivosAutorizacionDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FrmVenta extends BaseActivity implements Observer {

    public static final String EXTRA_MODO = "modo";
    public static final String EXTRA_VENTA = "venta";
    public static final String IS_FROM_RESTORE_INSTANCE = "IS_FROM_RESTORE_INSTANCE";
    private static final String STATE_VENTA = "ventaState";

    TextView nivelRentabilidadProveedor;
    TextView nivelRentabilidadEmpresa;
    LinearLayout linearRentProveedor;
    LinearLayout linearRentEmpresa;
    private TextView txtSubtotal;
    private TextView txtTotal;
    private TextView txtDescuentoLineas;
    private TextView txtDescuentoPedido;
    private TextView txtImpuestos;

    Integer requestCodeRestore;
    Integer resultCodeRestore;
    Intent dataRestore;

    private Venta _venta;
    private Vendedor _vendedor;

    //BO´s
    private EmpresaBo empresaBo;
    private CuentaCorrienteBo mCuentaCorrienteBo;

    //implementación con tablelayout y viewpager2 //private FragmentTabHost mTabHost;
    ConfigViewPagerVentaAdapter mViewAdapterVenta;
    ViewPager2 mViewPagerVenta;
    TabLayout mTabLayoutVenta;


    private DetalleVentaFragment detalleVentaFragment;

    public static final int ALTA = 0;
    public static final int MODIFICACION = 1;
    public static final int CONSULTA = 2;
    /**
     * Generacion de un remito a partir de un pedido sugerido.
     */
    public static final int GENERACION_REMITO = 3;


    private int modo = ALTA;
    private boolean isDataValid;
    private List<Boolean> _listValids;
    private static final String LETRA_B = "B";
    private final String TAG_CABECERA = "cabecera";
    private final String TAG_DETALLE = "detalle";
    public static final String EXTRA_CLIENT = "extra_client";
    public static final String EXTRA_FROM_PEDIDO_SUGERIDO = "extra_nuevo_remito";
    /**
     * la venta es a partir de un pedido sugerido?
     */
    private boolean isFromPedidoSugerido;
    private double mSaldoTotal;
    private final int DIALOG_GUARDAR_VENTA = 0;
    private final int DIALOG_VENTA_SIN_LINEAS = 1;
    private final int DIALOG_VALORES_POR_DEFECTO = 2;
    private final int DIALOG_MODIFICAR_VENTA = 3;
    private final int DIALOG_MONTO_MINIMO_NO_ALCANZADO = 4;
    private final int DIALOG_MONTO_MINIMO_PARA_DESCUENTO_ALCANZADO = 5;
    private final int DIALOG_PORCENTAJE_MAXIMO_SUPERADO = 6;
    private final int DIALOG_LIMITE_DISPONIBILIDAD_SUPERADO = 8;
    private final int DIALOG_DATOS_INVALIDOS = 7;
    private final int DIALOG_NUM_TROPA_FALTANTE = 9;
    private final int DIALOG_PEDIDO_NO_RENTABLE = 10;
    private final int DIALOG_DOCUMENTO_NO_VALIDO = 11;
    private final int DIALOG_IMPRIMIR_COMPROBANTE = 12;
    private boolean _ventaGuardada = false;
    private Cliente mCliente;
    private double mSaldoOriginalVenta;
    private CondicionVenta condicionVentaOriginal; //para saber si le tengo que restar o no al limite de disponibilidad
    private static final String TAG = FrmVenta.class.getCanonicalName();
    VentaBo bo;
    private boolean isGuardado;


    //BD
    private RepositoryFactory _repoFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_venta);

        Bundle b = this.getIntent().getExtras();
        initComponents();
        setActionBarTitle(ItemMenuNames.STRING_REGISTRAR_PEDIDO);
        mCliente = (Cliente) b.getSerializable(EXTRA_CLIENT);
        isFromPedidoSugerido = b.getBoolean(EXTRA_FROM_PEDIDO_SUGERIDO, false);
        Object ventaStateSaved = (savedInstanceState == null) ? null : savedInstanceState.getSerializable(STATE_VENTA);
        if (ventaStateSaved == null) {
            this._venta = (Venta) b.getSerializable(EXTRA_VENTA);

        } else {
            this._venta = (Venta) ventaStateSaved;
        }
        this.modo = (Integer) b.getSerializable(EXTRA_MODO);
        initVar();
        this._venta.addObserver(this);

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_VENTA, _venta);
        bundle.putSerializable(EXTRA_MODO, modo);
        bundle.putBoolean(IS_FROM_RESTORE_INSTANCE, savedInstanceState != null);

        /*mTabHost.addTab(mTabHost.newTabSpec(TAG_CABECERA).setIndicator("Cabecera"), CabeceraVentaFragment.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec(TAG_DETALLE).setIndicator("Detalle"), DetalleVentaFragment.class, bundle);*/


        //implementación con viewpager
        mViewPagerVenta = findViewById(R.id.pagerVenta);
        mViewAdapterVenta = new ConfigViewPagerVentaAdapter(getSupportFragmentManager(), getLifecycle(), bundle);
        //agrego los fragments que se van  a usar:
        mViewAdapterVenta.addFragment(new CabeceraVentaFragment());//fragment de cabecera de venta
        mViewAdapterVenta.addFragment(new DetalleVentaFragment());//fragment de detalle de venta
        //seteo el adapter al viewpager
        mViewPagerVenta.setAdapter(mViewAdapterVenta);
        //obtengo el tabLayout
        mTabLayoutVenta = findViewById(R.id.tabLayoutVenta);
        //determino las cabeceras que voy a usar por tabs
        String[] cabecerasDeVenta = new String[]{
                "Cabecera",// pos:0
                "Detalle"// pos:1
        };
        //creo un mediator para gestionar el tab con el viewpager
        new TabLayoutMediator(mTabLayoutVenta, mViewPagerVenta, ((tab, position) -> tab.setText(cabecerasDeVenta[position]))).attach();

        mTabLayoutVenta.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setTag(TAG_CABECERA);
                    case 1:
                        tab.setTag(TAG_DETALLE);
                    default:
                        tab.setTag(TAG_CABECERA);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 0:
                        tab.setTag(TAG_CABECERA);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_CABECERA);
                        if (fragment != null) fragment.onPause();
                    case 1:
                        tab.setTag(TAG_DETALLE);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_DETALLE);
                        if (fragment != null) fragment.onPause();
                    default:
                        tab.setTag(TAG_CABECERA);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_CABECERA);
                        if (fragment != null) fragment.onPause();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        int mTabASeleccionar;

        actualizarTotales();

        try {

            boolean ctaCteHabilitada = mCuentaCorrienteBo.isVentaCuentaCorrienteHabilitada(_venta.getCliente());
            if (modo == ALTA) {
                mSaldoTotal = mCuentaCorrienteBo.getTotalSaldo(mCliente, empresaBo.recoveryEmpresa().getSnClienteUnico());
            } else if (modo == MODIFICACION) {
                mSaldoOriginalVenta = _venta.getTotal();
                condicionVentaOriginal = _venta.getCondicionVenta();
                if (condicionVentaOriginal.isCuentaCorriente())
                    _venta.getCliente().setLimiteDisponibilidad(_venta.getCliente().getLimiteDisponibilidad() + mSaldoOriginalVenta);
            }
            if (!ctaCteHabilitada) {
                //mTabHost.setCurrentTab(0);
                mTabASeleccionar = 0;

            } else {
                //mTabHost.setCurrentTab(1);
                mTabASeleccionar = 1;
            }
            if (mTabASeleccionar >= 0 && mTabASeleccionar < mTabLayoutVenta.getTabCount()) {
                TabLayout.Tab tab = mTabLayoutVenta.getTabAt(mTabASeleccionar);
                if (tab != null) {
                    tab.select();
                }
                //cambio la vista
                mViewPagerVenta.setCurrentItem(mTabASeleccionar);
            }
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "initVar", ex, getApplicationContext());
        }

        isGuardado = false;
    }

    public void setEnableTabWidget(boolean enabled, int position) {
        // Habilitar una pestaña específica
        View tabViewToEnable = ((ViewGroup) mTabLayoutVenta.getChildAt(0)).getChildAt(position);
        tabViewToEnable.setEnabled(enabled);
    }

    public void showDetailTab() {
        TabLayout.Tab tab = mTabLayoutVenta.getTabAt(1);
        if (tab != null) {
            tab.select();
        }
        //cambio la vista
        mViewPagerVenta.setCurrentItem(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        guardarEstado.putSerializable(STATE_VENTA, _venta);

    }

    @Override
    protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
        _venta = (Venta) recEstado.getSerializable(STATE_VENTA);

        mSaldoTotal = (_venta != null) ? _venta.getTotal() : 0d;
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarTotales();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mn_venta, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int itemGuardar = 0;
        if (modo == CONSULTA)
            menu.getItem(itemGuardar).setEnabled(false);
        return true;
    }

    private void initComponents() {
        txtSubtotal = findViewById(R.id.lblSubtotalValue);//(TextView)
        txtTotal = findViewById(R.id.lblTotalValue);//(TextView)
        txtDescuentoPedido = findViewById(R.id.lblDescuentoCabeceraValue);//(TextView)
        txtDescuentoLineas = findViewById(R.id.lblDescuentoDetallesValue);//(TextView)
        txtImpuestos = findViewById(R.id.lblImpuestosValue);//(TextView)
        linearRentProveedor = findViewById(R.id.idLinearRentProv);//(LinearLayout)
        linearRentEmpresa = findViewById(R.id.idLinearRentEmpresa);//(LinearLayout)
        nivelRentabilidadEmpresa = findViewById(R.id.idRentEmpPedido);//(TextView)
        nivelRentabilidadProveedor = findViewById(R.id.idRentProvPedido);//(TextView)
    }

    private boolean initVar() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        this.bo = new VentaBo(_repoFactory);
        boolean isConfigurated = false;
        this.empresaBo = new EmpresaBo(_repoFactory);
        mCuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);
        // En caso de alta inicializo los valores
        if (modo == ALTA) {
            try {
                isConfigurated = asignarIdVenta();
            } catch (Exception e) {
                ErrorManager.manageException("frmVenta", "initVar", e);
            }

            // Fecha de venta
            Date fechaVenta = Calendar.getInstance().getTime();
            this._venta.setFechaVenta(fechaVenta);

            //Fecha de entrega
            Date fechaEntrega = Calendar.getInstance().getTime();
            this._venta.setFechaEntrega(fechaEntrega);

            // Condicion Venta
            CondicionVenta condicionVenta = _venta.getCliente().getCondicionVenta();
            this._venta.setCondicionVenta(condicionVenta);
            this._venta.setTasaDescuentoCondicionVenta(condicionVenta.getTasaDescuento());

            // Repartidor
            Repartidor repartidor;
            if (this._venta.getRepartidor() != null) {
                repartidor = this._venta.getRepartidor();
            } else {
                repartidor = _venta.getCliente().getRepartidor();
            }
            this._venta.setRepartidor(repartidor);

        } else {
            // Es consulta o modificacion por lo cual los datos ya tienen valores
            isConfigurated = true;
        }
        if (modo == GENERACION_REMITO) {
            // Fecha de venta
            Date fechaVenta = Calendar.getInstance().getTime();
            this._venta.setFechaVenta(fechaVenta);
            this._venta.setFechaRegistro(fechaVenta);

            // Fecha de entrega
            Date fechaEntrega = Calendar.getInstance().getTime();
            this._venta.setFechaEntrega(fechaEntrega);
            bo.setearRemito(_venta);
            bo.setVendedorPedido(_venta);
        }

        return isConfigurated;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_guardar:
                if (this._venta.getDetalles().isEmpty())
                    showDialog(DIALOG_VENTA_SIN_LINEAS);
                else
                    this.guardarVenta();
                return false;
            case R.id.mni_cancelar:
                finish();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private AppCompatDialog getDialogPedidoSinLineas() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloErrorGuardarPedido);
        builder.setMessage(ErrorManager.PedidoSinLineas);
        builder.setPositiveButton(this.getString(R.string.btnAceptar),
                (dialog, which) -> dialog.dismiss());
        return builder.create();
    }

    private AlertDialog getDialogGuardarVenta() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloGuardarPedido);
        builder.setMessage(R.string.msjGuardarPedido);
        builder.setPositiveButton(R.string.si, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!esPedidoRentable()) {
                    if (_vendedor.getTiControlPedidoRentable().equals(Vendedor.TI_CONTROL_PR_NO_PERMITE)) {
                        showDialog(DIALOG_PEDIDO_NO_RENTABLE);
                    } else {
                        if (_vendedor.getTiControlPedidoRentable().equals(Vendedor.TI_CONTROL_PR_CON_AUT)) {
                            showDialogMotivoAutorizacion();
                        } else if (_vendedor.getTiControlPedidoRentable().equals(Vendedor.TI_CONTROL_PR_SIN_AUT)) {
                            MotivoAutorizacionBo motivoAutorizacionBo = new MotivoAutorizacionBo(_repoFactory);
                            MotivoAutorizacion motivo;
                            try {
                                motivo = motivoAutorizacionBo.recoveryForPedidoRentableSinAutorizacion();
                                _venta.setIdMotivoAutorizaPedr(motivo.getId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            guardarVenta();
                        }
                    }
                } else {
                    guardarVenta();
                }
            }
        });
        builder.setNegativeButton(R.string.no, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder.create();
    }

    private AlertDialog getDialogGuardarModificacionVenta() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloGuardarPedidoModificado);
        builder.setMessage(R.string.msjGuardarModificacionesPedido);
        builder.setPositiveButton(R.string.si, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!esPedidoRentable()) {
                    if (_vendedor.getTiControlPedidoRentable().equals(Vendedor.TI_CONTROL_PR_NO_PERMITE)) {
                        showDialog(DIALOG_PEDIDO_NO_RENTABLE);
                    } else {
                        if (_vendedor.getTiControlPedidoRentable().equals(Vendedor.TI_CONTROL_PR_CON_AUT)) {
                            showDialogMotivoAutorizacion();
                        } else if (_vendedor.getTiControlPedidoRentable().equals(Vendedor.TI_CONTROL_PR_SIN_AUT)) {
                            MotivoAutorizacionBo motivoAutorizacionBo = new MotivoAutorizacionBo(_repoFactory);
                            MotivoAutorizacion motivo;
                            try {
                                motivo = motivoAutorizacionBo.recoveryForPedidoRentableSinAutorizacion();
                                _venta.setIdMotivoAutorizaPedr(motivo.getId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            guardarVenta();
                        }
                    }
                } else {
                    guardarVenta();
                }
            }
        });
        builder.setNegativeButton(R.string.no, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder.create();
    }

    private AlertDialog getDialogValoresPorDefecto() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(ErrorManager.ValorNoEncontrado);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //mTabHost.setCurrentTab(0);
                TabLayout.Tab tab = mTabLayoutVenta.getTabAt(0);
                if (tab != null) {
                    tab.select();
                }
                //cambio la vista
                mViewPagerVenta.setCurrentItem(0);
            }
        });

        return builder.create();
    }

    public void update(Observable observable, Object data) {
        this._ventaGuardada = false;
        actualizarTotales();
    }


    private void actualizarTotales() {
        double totalPorArticulo = this._venta.getTotalPorArticulo();
        double total = this._venta.getTotal();
        mSaldoTotal = total;
        double descuentoLineas;
        descuentoLineas = VentaBo.obtenerTotalDescuentosEnVenta(_venta);
        double descuentoPedido = totalPorArticulo * this._venta.getTasaDescuentoCondicionVenta();
        double totalImpuestos = VentaBo.obtenerTotalImpuestos(_venta);
        this.txtSubtotal.setText(Formatter.formatMoney(totalPorArticulo));
        this.txtTotal.setText(Formatter.formatMoney(total));
        this.txtDescuentoLineas.setText(Formatter.formatMoney(descuentoLineas));
        this.txtDescuentoPedido.setText(Formatter.formatMoney(descuentoPedido));
        this.txtImpuestos.setText(Formatter.formatMoney(totalImpuestos));
        bo.isPedidoRentable(_venta);
        if (bo.documentoControlaRentabilidadPorSucursal(_venta)) {
            linearRentEmpresa.setVisibility(View.VISIBLE);
            nivelRentabilidadEmpresa.setText(_venta.getNivelRentabilidadEmpresa());
            if (_venta.isRentableXEmpresa())
                nivelRentabilidadEmpresa.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            else
                nivelRentabilidadEmpresa.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
        } else {
            linearRentEmpresa.setVisibility(View.GONE);
        }

        if (bo.documentoControlaRentabilidadPorProveedor(_venta) && _venta.getNivelRentabilidadProveedor() != null) {
            linearRentProveedor.setVisibility(View.VISIBLE);
            if (_venta.isRentableXProveedor()) {
                nivelRentabilidadProveedor.setText(R.string.lblPedidoRentable);
                nivelRentabilidadProveedor.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            } else {
                nivelRentabilidadProveedor.setText(R.string.lblPedidoNoRentable);
                nivelRentabilidadProveedor.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
            }
        } else {
            linearRentProveedor.setVisibility(View.GONE);
        }

    }

    private boolean esPedidoRentable() {
        DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
        PkDocumento pkDocumento = new PkDocumento();
        pkDocumento.setIdDocumento(_venta.getId().getIdDocumento());
        pkDocumento.setPuntoVenta(Integer.parseInt(_venta.getId().getPuntoVenta() + ""));
        pkDocumento.setIdLetra(_venta.getId().getIdLetra());
        Documento documento = null;
        try {
            documento = documentoBo.recoveryById(pkDocumento);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (documento.getSnPedidoRentable() != null &&
                !documento.getSnPedidoRentable().equals("N") &&
                !bo.isPedidoRentable(_venta)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected AppCompatDialog onCreateDialog(int id) {
        AppCompatDialog dialog = null;
        switch (id) {
            case DIALOG_GUARDAR_VENTA:
                dialog = getDialogGuardarVenta();
                break;
            case DIALOG_MODIFICAR_VENTA:
                dialog = getDialogGuardarModificacionVenta();
                break;
            case DIALOG_VENTA_SIN_LINEAS:
                dialog = getDialogPedidoSinLineas();
                break;
            case DIALOG_VALORES_POR_DEFECTO:
                dialog = getDialogValoresPorDefecto();
                break;
            case DIALOG_MONTO_MINIMO_NO_ALCANZADO:
                dialog = getDialogMontoMinimoNoAlcanzado();
                break;
            case DIALOG_MONTO_MINIMO_PARA_DESCUENTO_ALCANZADO:
                dialog = getDialogMontoMinimoParaDescuentoNoAlcanzado();
                break;
            case DIALOG_PORCENTAJE_MAXIMO_SUPERADO:
                dialog = getDialogPorcentajeMaximoSuperado();
                break;
            case DIALOG_DATOS_INVALIDOS:
                dialog = getDialogDatosInvalidos();
                break;
            case DIALOG_LIMITE_DISPONIBILIDAD_SUPERADO:
                dialog = getDialogLimiteDisponibilidadSuperado();
                break;
            case DIALOG_NUM_TROPA_FALTANTE:
                dialog = getDialogNumeroTropaFaltante();
                break;
            case DIALOG_PEDIDO_NO_RENTABLE:
                dialog = getDialogNoPermitePedidoNoRentable();
                break;
            case DIALOG_DOCUMENTO_NO_VALIDO:
                dialog = getDialogDocumentoNoValido();
                break;
            case DIALOG_IMPRIMIR_COMPROBANTE:
                dialog = getDialogImprimirComprobante();
                break;
        }

        return dialog;
    }

    private AlertDialog getDialogImprimirComprobante() {
        Builder builder = new Builder(this);
        builder.setTitle("Imprimir Comprobante");
        builder.setMessage(getString(R.string.msjImprimirComprobante));
        builder.setPositiveButton(R.string.si, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                imprimirComprobante();
            }
        });
        builder.setNegativeButton(R.string.no, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder.create();
    }

    private void imprimirComprobante() {
        PedidoPrinter p = new PedidoPrinter();
        try {
            p.print(_venta, getApplicationContext());
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogMotivoAutorizacion() {
        MotivosAutorizacionDialogFragment autorizacionDialogFragment = MotivosAutorizacionDialogFragment.newInstance(MotivoAutorizacion.TI_AUTORIZACION_PEDIDO_RENTABLE, new MotivosAutorizacionDialogFragment.MotivoAutorizacionListener() {

//			@Override
//			public void onCancel() {
//				// TODO Auto-generated method stub
//
//			}

            @Override
            public void onAccept(MotivoAutorizacion autorizacion, String observacion) {

                /*Intent intentResult = new Intent();
                intentResult.putExtra(MOTIVO, autorizacion);
                intentResult.putExtra(OBSERVACION, observacion);
                setResult(RESULT_CON_MOTIVO, intentResult);
                */
                _venta.setIdMotivoAutorizaPedr(autorizacion.getId());
                guardarVenta();
            }
        });
        autorizacionDialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    private AppCompatDialog getDialogLimiteDisponibilidadSuperado() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        double limiteDisp = (modo == MODIFICACION && condicionVentaOriginal.isCuentaCorriente()) ? _venta.getCliente().getLimiteDisponibilidad() + mSaldoOriginalVenta : _venta.getCliente().getLimiteDisponibilidad();
        builder.setMessage(ErrorManager.ErrorLimiteSaldoSuperado + limiteDisp + ErrorManager.LimiteDisponibilidadModificar);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        return builder.create();
    }

    private AppCompatDialog getDialogNumeroTropaFaltante() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(ErrorManager.ErrorNumeroTropaFaltante);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        return builder.create();
    }

    private AppCompatDialog getDialogDatosInvalidos() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(R.string.msjListasNoValidasGuardar);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        int btnTitle;
        if (modo == ALTA) {
            btnTitle = R.string.btnCancelarPedido;
        } else {
            btnTitle = R.string.btnCancelarModificacionPedido;
        }
        builder.setNegativeButton(btnTitle, new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();

            }
        });
        return builder.create();

    }

    private AppCompatDialog getDialogDocumentoNoValido() {
        Builder builder;
        builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(R.string.msjDocumentoNoValido);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        int btnTitle;
        if (modo == ALTA) {
            btnTitle = R.string.btnCancelarPedido;
        } else {
            btnTitle = R.string.btnCancelarModificacionPedido;
        }
        builder.setNegativeButton(btnTitle, new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();

            }
        });
        return builder.create();

    }

    private AppCompatDialog getDialogNoPermitePedidoNoRentable() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(R.string.msjNoPermitePedidoNoRentable);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        int btnTitle;
        if (modo == ALTA) {
            btnTitle = R.string.btnCancelarPedido;
        } else {
            btnTitle = R.string.btnCancelarModificacionPedido;
        }
        builder.setNegativeButton(btnTitle, new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();

            }
        });
        return builder.create();

    }

    private AppCompatDialog getDialogMontoMinimoParaDescuentoNoAlcanzado() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloErrorGuardarPedido);

        String montoMinimoParaDescuento = null;
        try {
            montoMinimoParaDescuento = empresaBo.montoACubrirParaDescuento().toString();
        } catch (Exception e) {
            ErrorManager.manageException("frmVenta", "getDialogMontoMinimoDescuentoNoAlcanzado", e, getApplicationContext(), "Error", "Error al recuperar el monto minimo para descuento");
        }

        String mensaje = ErrorManager.ErrorMontoMinimoDescuentoNoAlcanzado + montoMinimoParaDescuento;
        builder.setMessage(mensaje);
        builder.setPositiveButton(this.getString(R.string.btnAceptar),
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    private AppCompatDialog getDialogPorcentajeMaximoSuperado() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloErrorGuardarPedido);
        String mensaje = ErrorManager.ErrorPorcentajeMaximoArticulosCriticos;
        builder.setMessage(mensaje);
        builder.setPositiveButton(this.getString(R.string.btnAceptar),
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();

    }

    private AppCompatDialog getDialogMontoMinimoNoAlcanzado() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloErrorGuardarPedido);
        String montoMinimoEmpresa = null;
        try {
            montoMinimoEmpresa = empresaBo.montoMinimoFactura().toString();
        } catch (Exception e) {
            ErrorManager.manageException("frmVenta", "getDialogMontoMinimoNoAlcanzado", e, getApplicationContext(), "Error", "Error al recuperar el monto minimo de factura");
        }

        String mensaje = ErrorManager.ErrorMontoMinimoNoAlcanzadao + montoMinimoEmpresa;
        builder.setMessage(mensaje);
        builder.setPositiveButton(this.getString(R.string.btnAceptar),
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onBackPressed() {

        if (_venta.getDetalles().size() > 0 && modo != CONSULTA) {
            int idVendedor = Integer.parseInt(String.valueOf(PreferenciaBo.getInstance().getPreferencia().getIdVendedor()));
            VendedorBo vendedorBo = new VendedorBo(_repoFactory);
            _vendedor = vendedorBo.recoveryById(idVendedor);
            switch (modo) {
                case ALTA:
                    if (this._venta.getDetalles().size() > 0 && !this._ventaGuardada) {
                        if (!isDataValid()) {
                            showDialog(DIALOG_DATOS_INVALIDOS);
                        } else if (!isDocumentoValid()) {
                            showDialog(DIALOG_DOCUMENTO_NO_VALIDO);
                        } else
                            showDialog(DIALOG_GUARDAR_VENTA);
                    }
                    break;
                case MODIFICACION:
                    if (this._venta.getDetalles().size() > 0 && !this._ventaGuardada) {
                        if (!isDataValid()) {
                            showDialog(DIALOG_DATOS_INVALIDOS);
                        } else if (!isDocumentoValid()) {
                            showDialog(DIALOG_DOCUMENTO_NO_VALIDO);
                        } else
                            showDialog(DIALOG_MODIFICAR_VENTA);
                    }
                    break;
                case GENERACION_REMITO:
                    if (_venta.getDetalles().size() > 0 && !_ventaGuardada) {
                        if (!isDataValid) {
                            showDialog(DIALOG_DATOS_INVALIDOS);
                        }
                    }
                    showDialog(DIALOG_GUARDAR_VENTA);
                    break;

            }
        } else {

            super.onBackPressed();
        }
    }

    //@SuppressWarnings("deprecation")
    protected void guardarVenta() {
        if (VentaDetalleBo.allVentasPedidoHaveNumTropa(_venta.getDetalles())) {
            if (isDataValid()) {
                CuentaCorrienteBo bo = new CuentaCorrienteBo(_repoFactory);
                if (_venta.getCodigoAutorizacionAccionComercial().equals("") || bo.validarCodigoAutorizacion(_venta.getCodigoAutorizacionAccionComercial())) {
                    try {
                        if (!bo.tieneCredito(_venta, mSaldoTotal)) {
                            showDialog(DIALOG_LIMITE_DISPONIBILIDAD_SUPERADO);
                        } else { //SI NO SE EXCEDE EL LIMITE DE DISPONIBILIDAD
                            String documento = this._venta.getId().getIdDocumento();
                            if (this._venta.getId() == null || documento == null || documento.trim().equals("")) {
                                showDialog(DIALOG_VALORES_POR_DEFECTO);
                            } else {
                                if (modo == ALTA) {
                                    tratarAltaVenta();
                                }
                                if (modo == MODIFICACION) {
                                    tratarModificacionVenta();
                                }
                                if (modo == GENERACION_REMITO) {
                                    tratarRemito();
                                }
                            }
                        }
                    } catch (Exception e) {
                        ErrorManager.manageException("FrmVenta", "guardarVenta", e, getApplicationContext());
                    }
                } else {
                    SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, ErrorManager.ERROR, ErrorManager.ErrorCodigoAutorizacionAccionComercial);
                    simpleDialogFragment.show(getSupportFragmentManager(), TAG);
                }

            } else {
                showDialog(DIALOG_DATOS_INVALIDOS);
            }
        } else {
            showDialog(DIALOG_NUM_TROPA_FALTANTE);
        }
    }

    private void tratarRemito() {
        try {
            bo.generarRemito(_venta);
            tratarAltaVenta();
        } catch (Exception e) {
            com.ar.vgmsistemas.view.AlertDialog alertDialog = new com.ar.vgmsistemas.view.AlertDialog(getApplicationContext(), ErrorManager.ERROR, "No se pudo genera el remito");
            alertDialog.show();
        }
    }

    private void tratarModificacionVenta() throws Exception {
        // Quito la marca si el pedido tiene descuentos
        _venta.setTieneDescuento(false);
        // Actualizo los totales y marca que indica si el pedido tiene descuestos
        VentaBo.actualizarTotales(_venta);
        // Calcular el porcentaje
        double porcentaje = _venta.getMontoTotalArticulosCriticos() / _venta.getSubtotal();

        //Tarea #3198

        if (porcentaje <= PreferenciaBo.getInstance().getPreferencia().getPorcentajeArticulosCriticos()) {
            //if (porcentaje <= empresaBo.recoveryEmpresa().getTasaMaximaArticulosCriticos()){
            //Requerimiento Tarea #2961 - Monto minimo de factura
            boolean isMontoMinimoFacturaValido = false;
            try {
                isMontoMinimoFacturaValido = validarMontoMinimoFactura();
            } catch (Exception e) {
                throw new Exception(ErrorManager.ErrorEvaluarMontoMinimoFactura);
            }

            if (isMontoMinimoFacturaValido) {
                //Requerimiento Tarea #2985 - Descuentos segun monto
                boolean isMontoValidoParaDescuento = false;
                try {
                    isMontoValidoParaDescuento = validarMontoMinimoParaDescuento();
                } catch (Exception e) {
                    throw new Exception(ErrorManager.ErrorEvaluarMontoMinimoDescuento);
                }

                if (isMontoValidoParaDescuento) {
                    bo.update(_venta, getApplicationContext());
                    ClienteBo clienteBo = new ClienteBo(_repoFactory);
                    clienteBo.updateLimiteDisponibilidad(mCliente, _venta.getTotal(), mSaldoOriginalVenta, condicionVentaOriginal, _venta.getCondicionVenta());
                    Toast.makeText(getApplicationContext(), "Modificaciones guardadas", Toast.LENGTH_SHORT).show();
                    this.finish();
                } else {
                    showDialog(DIALOG_MONTO_MINIMO_PARA_DESCUENTO_ALCANZADO);
                }
            } else {
                //Monto minimo no valido
                showDialog(DIALOG_MONTO_MINIMO_NO_ALCANZADO);
            }
        } else {
            showDialog(DIALOG_PORCENTAJE_MAXIMO_SUPERADO);
        }
    }

    private void tratarAltaVenta() throws Exception {
        // Quito la marca si el pedido tiene descuentos
        _venta.setTieneDescuento(false);
        // Actualizo los totales y marca que indica si el pedido tiene descuestos
        VentaBo.actualizarTotales(_venta);
        ClienteBo clienteBo = new ClienteBo(_repoFactory);

        EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
        //Controlo que tenga encendido el GPS
        if (empresaBo.gpsValido(getApplicationContext())) {
            // Actualizo los totales
            //VentaBo.actualizarTotales(_venta);
            // Calcular el porcentaje
            Double porcentaje = _venta.getMontoTotalArticulosCriticos() / _venta.getSubtotal();

            //if (porcentaje <= empresaBo.recoveryEmpresa().getTasaMaximaArticulosCriticos()){

            //Tarea #3198
            if (porcentaje <= PreferenciaBo.getInstance().getPreferencia().getPorcentajeArticulosCriticos() ||
                    porcentaje.equals(Float.NaN)) {

                //Requerimiento Tarea #2961 - Monto minimo de factura
                boolean isMontoMinimoFacturaValido = false;
                try {
                    isMontoMinimoFacturaValido = validarMontoMinimoFactura();
                } catch (Exception e) {
                    throw new Exception(ErrorManager.ErrorEvaluarMontoMinimoFactura);
                }
                if (isMontoMinimoFacturaValido) {
                    //Requerimiento Tarea #2985 - Descuentos segun monto
                    boolean isMontoValidoParaDescuento = false;
                    try {
                        isMontoValidoParaDescuento = validarMontoMinimoParaDescuento();
                    } catch (Exception e) {
                        throw new Exception(ErrorManager.ErrorEvaluarMontoMinimoDescuento);
                    }
                    if (isMontoValidoParaDescuento) {
                        isGuardado = bo.create(_venta, getApplicationContext(), isFromPedidoSugerido);
                        clienteBo.updateLimiteDisponibilidad(mCliente, _venta.getTotal(), _venta.getCondicionVenta());
                        Toast.makeText(getApplicationContext(), "Venta guardada", Toast.LENGTH_SHORT).show();

                        //Solo se va a imprimir desde el listado de pedidos, o si es venta directa y esta configurado para impresion el comprobante.
                        if (isGuardado && _venta.getDocumento().getSnVentaDirecta().equals("S") &&
                                _venta.getDocumento().getTiImpresionMovil() > 0) {
                            Toast.makeText(this, R.string.msjPedidoGuardado, Toast.LENGTH_SHORT).show();
                            showDialog(DIALOG_IMPRIMIR_COMPROBANTE);
                        } else {
                            FrmVenta.this.finish();
                        }

                    } else {
                        showDialog(DIALOG_MONTO_MINIMO_PARA_DESCUENTO_ALCANZADO);
                    }
                } else {
                    //Monto minimo de factura no valido
                    showDialog(DIALOG_MONTO_MINIMO_NO_ALCANZADO);
                }
            } else {
                showDialog(DIALOG_PORCENTAJE_MAXIMO_SUPERADO);
            }
        } else {
            notificarGpsApagado();
        }
    }

    private boolean validarMontoMinimoParaDescuento() throws Exception {
        boolean goGuardar = true;
        //Controlo que en el pedido algun detalle tenga descuento
        if (_venta.isTieneDescuento()) {
            //De aplicarse la funcionalidad, sumo los montos y controlo con el monto
            Double montoMinimoParaDescuento = empresaBo.montoACubrirParaDescuento();
            if (montoMinimoParaDescuento > 0) {
                //Float totalFactura = obtenerImporteTotalDetalles();
                Double totalFactura = _venta.getTotal();
                if (totalFactura < montoMinimoParaDescuento) {
                    goGuardar = false;
                }
            }
        }
        return goGuardar;
    }

    private boolean validarMontoMinimoFactura() throws Exception {
        boolean goGuardar = true;
        //Comparo el total de la factura
        Double montoMinimoFactura = empresaBo.montoMinimoFactura() == null
                                            ? 0d
                                            : empresaBo.montoMinimoFactura();
        if (montoMinimoFactura > 0) {
            //De aplicarse la funcionalidad, sumo y controlo el monto
            double totalFactura = _venta.getTotal();
            if (totalFactura < montoMinimoFactura) {
                goGuardar = false;
            }
        }
        return goGuardar;
    }


    private void notificarGpsApagado() {
        ErrorManager.manageException("DlgBldrOpcionesCliente", "dialogoOpcionesClienteOnClick",
                new Exception(), getApplicationContext(), R.string.tituloErrorGps, R.string.msjErrorGpsApagadoPedido);
    }

    public void startForActivityResult(Intent intent, int requestCode) {
        this.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (detalleVentaFragment != null)
            detalleVentaFragment.onActivityResults(requestCode, resultCode, data);
        else {
            requestCodeRestore = requestCode;
            resultCodeRestore = resultCode;
            dataRestore = data;
        }
    }

    protected void onActivityResultRestore() {
        detalleVentaFragment.onActivityResults(requestCodeRestore, resultCodeRestore, dataRestore);
        requestCodeRestore = null;
        resultCodeRestore = null;
        dataRestore = null;
    }

    private boolean asignarIdVenta() throws Exception {
        PkVenta id = new PkVenta();
        this._venta.setId(id);
        String letra = VentaBo.recoveryLetraComprobante(this._venta.getCliente());
        id.setIdLetra(letra);
        String idTipoDocumento = "";
        idTipoDocumento = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getIdTipoDocumentoPorDefecto();
        id.setIdDocumento(idTipoDocumento);
        int puntoVenta = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getIdPuntoVentaPorDefecto();
        this._venta.getId().setPuntoVenta(puntoVenta);
        DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
        long numeroDocumento;
        PkDocumento idDoc = new PkDocumento();
        idDoc.setIdDocumento(_venta.getId().getIdDocumento());
        idDoc.setIdLetra(_venta.getId().getIdLetra());
        idDoc.setPuntoVenta(_venta.getId().getPuntoVenta());
        Documento documento = documentoBo.recoveryById(idDoc);
        if (!documento.isLegal()) {
            idDoc.setIdLetra(LETRA_B);
            letra = LETRA_B;
            documento = documentoBo.recoveryById(idDoc);
        }
        numeroDocumento = documentoBo.recoveryNumeroDocumento(idTipoDocumento, letra, puntoVenta);
        this._venta.getId().setIdLetra(letra);
        this._venta.getId().setIdNumeroDocumento(numeroDocumento);
        _venta.setDocumento(documento);
        return true;
    }

    public void updateLineasPedido(List<VentaDetalle> listVentaDetalles, PkVenta pkVenta) {
        DocumentosListaBo documentosListaBo = new DocumentosListaBo(_repoFactory);
        try {
            _listValids = documentosListaBo.getValidListCabecera(listVentaDetalles, pkVenta);
            isDataValid = documentosListaBo.isListTrue(_listValids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    //Refs #38961
    public boolean isDocumentoValid() {
        boolean isValid = true;
        if (_venta.getCliente().getCategoriaFiscal().getSnBn() != null &&
                _venta.getCliente().getCategoriaFiscal().getSnBn().equals("S")) {

            Documento documento = _venta.getDocumento();
            if (documento != null && documento.getTipoBlancoNegro() != null
                    && documento.getTipoBlancoNegro().equals("B")) {
                isValid = false;
            }
        }
        return isValid;
    }

    public List<Boolean> get_listValids() {
        return _listValids;
    }

    public int getModo() {
        return modo;
    }

    /**
     * @return <b>true</b>: si el remito se genera desde un pedido sugerido
     * <b>false</b>: si el remito se genera desde cero
     */
    public boolean isFromPedidoSugerido() {
        return isFromPedidoSugerido;
    }


    public void setDetalleVentaFragment(DetalleVentaFragment detalleVentaFragment) {
        this.detalleVentaFragment = detalleVentaFragment;
    }
}
