package com.ar.vgmsistemas.view.cobranza;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.DocumentoBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.ReciboBo;
import com.ar.vgmsistemas.bo.SincronizacionBo;
import com.ar.vgmsistemas.bo.VendedorBo;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.ReciboDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.entity.key.PkRecibo;
import com.ar.vgmsistemas.printer.ReciboPrinter;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public class FrmRecibo extends BaseActivity implements Observer {

    private static final String TAG = FrmRecibo.class.getCanonicalName();
    private static final int DIALOG_ERROR_AL_IMPRIMIR_RECIBO = 3;
    private static final int DIALOG_ERROR_GUARDAR_RECIBO = 4;
    public static final String EXTRA_RECIBO = "entity";
    //private final DaoFactory _daoFactory = DaoFactory.getDaoFactory(DaoFactory.SQLITE);
    private TextView lblTotalPagos;
    private TextView lblTotalDocumentos;
    private TextView lblSaldo;
    private Recibo _recibo;
    private final int DIALOG_GUARDAR_RECIBO = 0;
    private final int DIALOG_VENTA_SIN_LINEAS = 1;
    private final int DIALOG_IMPRIMIR_RECIBO = 2;
    private boolean isGuardado;

    private final String TAG_CABECERA_RECIBO = "cabecera_recibo";
    private final String TAG_DETALLE_RECIBO = "detallle_recibo";
    private final String TAG_PAGOS_RECIBO = "pago_recibos";

    //implementación con tablelayout y viewpager2
    ConfigViewPagerReciboAdapter mViewAdapterRecibo;
    ViewPager2 mViewPagerRecibo;
    TabLayout mTabLayoutRecibo;
    // DAO
    private RepositoryFactory _repoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.lyt_recibo);
        setActionBarTitle(R.string.lblRecibo);
        Bundle b = getIntent().getExtras();

        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);

        Object data = getLastNonConfigurationInstance();
        if (data == null) {
            if (b != null) this._recibo = (Recibo) b.getSerializable("recibo");
            initVar();
        } else
            this._recibo = (Recibo) data;

        initComponents();
        actualizarTotales();
        isGuardado = false;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void initVar() {
        this._recibo.setId(new PkRecibo());
        this._recibo.setVendedor(VendedorBo.getVendedor());
        this._recibo.setFechaMovil(Calendar.getInstance().getTime());
        this._recibo.setFechaRegistroMovil(Calendar.getInstance().getTime());
        actualizarNumeroRecibo();
        //determinarTipoImpresionRecibo();
        this._recibo.addObserver(this);
    }

    private void initComponents() {
        //Cabecera
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_RECIBO, _recibo);

        //implementación con viewpager
        mViewPagerRecibo = findViewById(R.id.pagerRecibo);
        mViewAdapterRecibo = new ConfigViewPagerReciboAdapter(getSupportFragmentManager(), getLifecycle(), bundle);
        //agrego los fragments que se van  a usar:
        mViewAdapterRecibo.addFragment(new CabeceraReciboFragment()); //fragment de cabecera de recibo
        mViewAdapterRecibo.addFragment(new DetalleReciboFragment());//fragment de detalle de recibo
        mViewAdapterRecibo.addFragment(new ReciboPagosFragment()); //fragment de pagos de recibo
        //seteo el adapter al viewpager
        mViewPagerRecibo.setAdapter(mViewAdapterRecibo);
        //obtengo el tabLayout
        mTabLayoutRecibo = findViewById(R.id.tabLayoutRecibo);
        //determino las cabeceras que voy a usar por tabs
        String[] cabecerasDeRecibo = new String[]{
                "Recibo",// pos:0
                "Documentos imputados",// pos:1
                "Medios de pago"// pos:2
        };
        //creo un mediator para gestionar el tab con el viewpager
        new TabLayoutMediator(mTabLayoutRecibo, mViewPagerRecibo, ((tab, position) -> tab.setText(cabecerasDeRecibo[position]))).attach();

        mTabLayoutRecibo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setTag(TAG_CABECERA_RECIBO);
                    case 1:
                        tab.setTag(TAG_DETALLE_RECIBO);
                    case 2:
                        tab.setTag(TAG_PAGOS_RECIBO);
                    default:
                        tab.setTag(TAG_CABECERA_RECIBO);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 0:
                        tab.setTag(TAG_CABECERA_RECIBO);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_CABECERA_RECIBO);
                        if (fragment != null) fragment.onPause();
                    case 1:
                        tab.setTag(TAG_DETALLE_RECIBO);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_DETALLE_RECIBO);
                        if (fragment != null) fragment.onPause();
                    case 2:
                        tab.setTag(TAG_PAGOS_RECIBO);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_PAGOS_RECIBO);
                        if (fragment != null) fragment.onPause();
                    default:
                        tab.setTag(TAG_CABECERA_RECIBO);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_CABECERA_RECIBO);
                        if (fragment != null) fragment.onPause();
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //lblTotalDocumentos
        this.lblTotalDocumentos = findViewById(R.id.lblTotalDocumentosValue);

        //lblTotalPagos//(TextView)
        this.lblTotalPagos = findViewById(R.id.lblTotalPagosValue);

        //lblSaldo//(TextView)
        this.lblSaldo = findViewById(R.id.lblSaldoValue);
    }

    public void actualizarTotales() {
        this.lblTotalDocumentos.setText(Formatter.formatMoney(this._recibo.obtenerTotalDocumentosImputados()));
        this.lblTotalPagos.setText(Formatter.formatMoney(this._recibo.obtenerTotalPagos()));
        this.lblSaldo.setText(Formatter.formatMoney(this._recibo.getSaldo()));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_GUARDAR_RECIBO:
                dialog = getDialogGuardarRecibo();
                break;
            case DIALOG_VENTA_SIN_LINEAS:
                dialog = getDialogReciboSinLineas();
            case DIALOG_IMPRIMIR_RECIBO:
                dialog = getDialogImprimirRecibo();
                break;
            case DIALOG_ERROR_AL_IMPRIMIR_RECIBO:
                if (_recibo.getTipoImpresionRecibo() != 0) {
                    dialog = getDialogErrorAlImprimir();
                }
                break;
            case DIALOG_ERROR_GUARDAR_RECIBO:
                dialog = getDialogErrorAlGuardar();
                break;
            default:
                break;
        }
        return dialog;
    }

    private AlertDialog getDialogReciboSinLineas() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloErrorGuardarPedido);
        builder.setMessage(ErrorManager.PedidoSinLineas);
        builder.setPositiveButton(this.getString(R.string.btnAceptar), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private AlertDialog getDialogErrorAlImprimir() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloImprimirRecibo);
        builder.setMessage(R.string.msjErrorImprimirRecibo);
        builder.setPositiveButton(this.getString(R.string.btnAceptar), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mostrarListadoRecibo();
            }
        });
        return builder.create();
    }

    private AlertDialog getDialogErrorAlGuardar() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloNoSeGuardoRecibo);
        builder.setMessage(R.string.msjErrorGuardarRecibo);
        builder.setPositiveButton(this.getString(R.string.btnAceptar), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        return builder.create();
    }

    private void mostrarListadoRecibo() {
        finish();
    }

    private AlertDialog getDialogGuardarRecibo() {
        Builder builder = new Builder(this);
        builder.setTitle(getString(R.string.tituloGuardarRecibo));
        builder.setMessage(getString(R.string.msjGuardarRecibo));
        builder.setPositiveButton(R.string.si, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                guardarRecibo();
            }
        });
        builder.setNegativeButton(R.string.no, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder.create();
    }

    private AlertDialog getDialogImprimirRecibo() {
        Builder builder = new Builder(this);
        builder.setTitle("Imprimir recibo");
        builder.setMessage(getString(R.string.msjImprimirRecibo));
        builder.setPositiveButton(R.string.si, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                imprimirRecibo();
            }
        });
        builder.setNegativeButton(R.string.no, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder.create();
    }

    @Override
    public void onBackPressed() {
        if (!isGuardado) {
            showDialog(DIALOG_GUARDAR_RECIBO);
        } else {
            if (_recibo.getTipoImpresionRecibo() != 0) {
                showDialog(DIALOG_IMPRIMIR_RECIBO);
            } else {
                finish();// no se imprime nada
            }
        }
    }

    private void guardarRecibo() {
        EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
        Empresa empresa = null;
        try {
            empresa = empresaBo.recoveryEmpresa();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (empresa != null && !empresa.isAnticipoHabilitado() && _recibo.getSaldo() < -0.01) {
            SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.error), getString(R.string.noAnticipo)).show(getSupportFragmentManager(), "");
        } else {
            //Validación Tarea #2347 - Recibo con monto 0.0
            if (!reciboNoVacio()) {
                //Caso de Recibo vacio
                com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, "Error", ErrorManager.ErrorReciboVacio);
                alert.show();
            } else {
                //Caso de Recibo no vacio, intentamos guardarlo
                try {
                    ReciboBo reciboBo = new ReciboBo(_repoFactory);
                    boolean guardar = true;
                    double total = _recibo.getEntrega().obtenerTotalPagos();
                    if (total < 0) {
                        guardar = false;
                        Iterator<ReciboDetalle> iteradorReciboDetalle = _recibo.getDetalles().iterator();
                        DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
                        PkDocumento id = new PkDocumento();
                        while (iteradorReciboDetalle.hasNext() && !guardar) {
                            CuentaCorriente cuentaCorriente = iteradorReciboDetalle.next().getCuentaCorriente();
                            id.setIdDocumento(cuentaCorriente.getId().getIdDocumento());
                            id.setIdLetra(cuentaCorriente.getId().getIdLetra());
                            id.setPuntoVenta(cuentaCorriente.getId().getPuntoVenta());
                            Documento documento = documentoBo.recoveryById(id);
                            long id_funcion = documento.getFuncionTipoDocumento();
                            double totalCtaCte = cuentaCorriente.calcularSaldo();

                            guardar = (id_funcion == Documento.FUNCION_CREDITO && totalCtaCte > 0);
                        }
                    }

                    if (guardar) {
                        //isGuardado = reciboBo.guardar(this._recibo);
                        //Controlo que tenga prendido el GPS antes de guardar el recibo
                        if (empresaBo.gpsValido(getApplicationContext())) {
                            //Validación Tarea #2347 - Recibo con monto 0.0
                            if (reciboNoVacio()) {
                                isGuardado = reciboBo.guardar(this._recibo, getApplicationContext());
                            } else {
                                //Caso de Recibo no vacío
                                com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, "Error", ErrorManager.ErrorReciboVacio);
                                alert.show();
                            }

                            if (isGuardado) {
                                try {
                                    boolean backUpOk;
                                    backUpOk = (_repoFactory != null && _repoFactory.backupDb());

                                    //Notifico que no se hace el backup por espacio insuficiente
                                    if (!backUpOk) {
                                        Toast.makeText(this, R.string.msjEspacioInsuficienteSD, Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(this, R.string.msjReciboGuardado, Toast.LENGTH_SHORT).show();
                                    if (_recibo.getTipoImpresionRecibo() != 0)
                                        showDialog(DIALOG_IMPRIMIR_RECIBO);

                                    if (PreferenciaBo.getInstance().getPreferencia().isEnvioReciboAutomatico()) {
                                        Thread t = new Thread() {
                                            public void run() {
                                                try {
                                                    SincronizacionBo sincronizacionBo = new SincronizacionBo(getApplicationContext(), _repoFactory);
                                                    sincronizacionBo.enviarRecibosPendientes();
                                                } catch (Exception e) {
                                                    //No se trata la exception porque el servicio va a intentar despues
                                                }
                                            }
                                        };
                                        t.start();
                                    }
                                } catch (Exception ex) {
                                    new Exception(ErrorManager.GuardarRecibo);
                                }
                            } else {
                                com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, "Error", ErrorManager.GuardarRecibo);
                                alert.show();
                            }
                        } else {
                            notificarGpsApagado();
                        }
                    } else {
                        com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, "Error", ErrorManager.FaltaSeleccionArticulo);
                        alert.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showDialog(DIALOG_ERROR_GUARDAR_RECIBO);
                }
            }
        }
    }

    /**
     * Controlo que las entregas y los detalles no sean empty y Total no sea 0
     *
     * @return boolean si esta o no vacio
     */
    private boolean reciboNoVacio() {

        boolean isNoVacio = true;

        boolean entregasVacias = false;
        boolean detallesVacios = false;

        //Validar Entregas
        if (_recibo.getEntrega().getCheques().isEmpty()) {

            if (_recibo.getEntrega().getDepositos().isEmpty()) {

                if (_recibo.getEntrega().getRetenciones().isEmpty()) {

                    if (_recibo.getEntrega().getEntregasEfectivo().isEmpty()) {

                        entregasVacias = true;

                    }
                }
            }
        }

        //Validar Detalles
        if (_recibo.getDetalles().isEmpty()) {
            detallesVacios = true;
        }

        if (entregasVacias) {
            if (detallesVacios) {
                //Recibo sin detalles y sin entregas
                String totalRecibo = String.valueOf(_recibo.getTotal());
                String cero = "0.0";

                if (totalRecibo.equals(cero)) {
                    isNoVacio = false;
                }

            } else {

                if (soloComprobantesPorPagar()) {
                    //Recibo con facturas solamente
                    isNoVacio = false;
                }
            }
        }

        return isNoVacio;

    }

    private boolean soloComprobantesPorPagar() {
        boolean soloFacturas = true;
        for (int i = 0; i < _recibo.getDetalles().size(); i++) {
            if (_recibo.getDetalles().get(i).getCuentaCorriente().getSigno() < 0) {
                soloFacturas = false;
                break;
            }
        }
        return soloFacturas;
    }

    private void imprimirRecibo() {
        ReciboPrinter miRecibo = new ReciboPrinter();
        miRecibo._context = getApplicationContext();
        try {
            miRecibo.print(_recibo);
            exit();
        } catch (Exception e) {
            //ErrorManager.manageException(TAG, "imprimirRecibo", e, this);
            showDialog(DIALOG_ERROR_AL_IMPRIMIR_RECIBO);
        }
    }

    private void exit() {
        this.finish();

    }


    public Object getLastNonConfigurationInstance() {
        return this._recibo;
    }

    public void update(Observable observable, Object data) {
        actualizarTotales();
    }

    private void notificarGpsApagado() {
        ErrorManager.manageException("DlgBldrOpcionesCliente", "dialogoOpcionesClienteOnClick",
                new Exception(), this, R.string.tituloErrorGps, R.string.msjErrorGpsApagadoRecibo);
    }

    private void actualizarNumeroRecibo() {
        long numeroRecibo = -1;
        ReciboBo reciboBo = new ReciboBo(_repoFactory);
        try {
            numeroRecibo = reciboBo.getSiguienteNumeroRecibo(this._recibo.getId().getIdPuntoVenta());
            this._recibo.getId().setIdRecibo(numeroRecibo);
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "actualizarNumeroRecibo", ex);
        }
    }

}
