package com.ar.vgmsistemas.view.rendicion.egresos;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.CompraBo;
import com.ar.vgmsistemas.bo.RendicionBo;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.Proveedor;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.configuracion.ConfigViewPagerAdapter;
import com.ar.vgmsistemas.view.reparto.hojas.HojaFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Observable;

public class FrmEgreso extends BaseActivity {
    private final static String STATE_EGRESO = "estado_egreso";
    private final int DIALOG_GUARDAR_EGRESO = 0;

    public static final int ALTA = 0;
    public static final int MODIFICACION = 1;
    public static final int CONSULTA = 2;
    //private FragmentTabHost mTabHost;
    private final String TAG_CABECERA = "cabecera";
    private final String TAG_DETALLE = "detalle";
    private final String TAG_DIALOG_GUARDAR_EGRESO = "guardar_egreso";

    private int modo = ALTA;
    private TextView subtotal;
    private TextView impInterno;
    private TextView exento;
    private TextView iva;
    private TextView perDGR;
    private TextView perDGI;
    private TextView total;
    private Proveedor proveedorAnteriorSeleccionado;
    Compra compra;
    //private final int DIALOG_MODIFICAR_EGRESO = 1;

    //implementación con tablelayout y viewpager2
    ConfigViewPagerAdapter mViewAdapterEgreso;
    ViewPager2 mViewPagerEgreso;
    TabLayout mTabLayoutEgreso;

    private RepositoryFactory _repoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Agregar Egreso");
        if (savedInstanceState != null) {
            try {
                if (compra == null) {
                    compra = (Compra) savedInstanceState.getSerializable(Compra.EXTRA_COMPRA);
                }
            } catch (Exception e) {

            }
            ;
            if (compra == null) {
                compra = new Compra();
            }
            try {
                modo = (int) savedInstanceState.getSerializable(Compra.EXTRA_MODO);
            } catch (Exception e) {
                modo = ALTA;
            }
            try {
                compra.setIdHoja((int) savedInstanceState.getSerializable(HojaFragment.EXTRA_ID_HOJA));
                compra.setIdSucursal((int) savedInstanceState.getSerializable(HojaFragment.EXTRA_ID_SUCURSAL));
            } catch (Exception e) {
                // nada
            }
        } else {
            Bundle b = this.getIntent().getExtras();
            try {
                if (compra == null) {
                    compra = (Compra) b.getSerializable(Compra.EXTRA_COMPRA);
                }
            } catch (Exception e) {

            }
            ;
            if (compra == null) {
                compra = new Compra();
            }
            try {
                modo = (int) b.getSerializable(Compra.EXTRA_MODO);
            } catch (Exception e) {
                modo = ALTA;
            }
            try {
                compra.setIdHoja((int) b.getSerializable(HojaFragment.EXTRA_ID_HOJA));
                compra.setIdSucursal((int) b.getSerializable(HojaFragment.EXTRA_ID_SUCURSAL));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setContentView(R.layout.lyt_egreso);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Bundle b = new Bundle();
        b.putSerializable(Compra.EXTRA_COMPRA, compra);
        b.putSerializable(Compra.EXTRA_MODO, modo);
        //implementación con viewpager
        mViewPagerEgreso = findViewById(R.id.pagerEgreso);
        mViewAdapterEgreso = new ConfigViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        //agrego los fragments que se van  a usar:
        mViewAdapterEgreso.addFragment(new CabeceraEgresoFragment()); //fragment de cabecera de egreso
        mViewAdapterEgreso.addFragment(new DetalleEgresoFragment());//fragment de detalle de egreso
        //seteo el adapter al viewpager
        mViewPagerEgreso.setAdapter(mViewAdapterEgreso);
        //obtengo el tabLayout
        mTabLayoutEgreso = findViewById(R.id.tabLayoutEgreso);
        //determino las cabeceras que voy a usar por tabs
        String[] cabecerasDeEgreso = new String[]{
                "Cabecera",// pos:0
                "Impuestos"// pos:1
        };
        //creo un mediator para gestionar el tab con el viewpager
        new TabLayoutMediator(mTabLayoutEgreso, mViewPagerEgreso, ((tab, position) -> tab.setText(cabecerasDeEgreso[position]))).attach();

        mTabLayoutEgreso.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

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
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        initComponents();
        actualizarTotales();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void setEnableTabWidget(boolean enabled, int position) {
        // Habilitar una pestaña específica
        View tabViewToEnable = ((ViewGroup) mTabLayoutEgreso.getChildAt(0)).getChildAt(position);
        tabViewToEnable.setEnabled(enabled);
        if (enabled) {
            tabViewToEnable.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.base_background_activity_color));
        } else {
            tabViewToEnable.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.background_gray));
        }
    }


    public void initComponents() {
        subtotal = (TextView) findViewById(R.id.idSubtotal);
        impInterno = (TextView) findViewById(R.id.idImpInterno);
        exento = (TextView) findViewById(R.id.idExento);
        iva = (TextView) findViewById(R.id.idIva);
        perDGR = (TextView) findViewById(R.id.idPercDGR);
        perDGI = (TextView) findViewById(R.id.idPercDGI);
        total = (TextView) findViewById(R.id.idTotal);
    }

    public void update(Observable observable, Object data) {
        actualizarTotales();
    }

    public void actualizarTotales() {
        subtotal.setText(Formatter.formatMoney(compra.getPrSubtotal()));
        exento.setText(Formatter.formatMoney(compra.getPrExento()));
        iva.setText(Formatter.formatMoney(compra.getPrIva()));
        perDGR.setText(Formatter.formatMoney(compra.getPrPercIngrBruto()));
        perDGI.setText(Formatter.formatMoney(compra.getPrPercIva()));
        impInterno.setText(Formatter.formatMoney(compra.getPrIpInterno()));
        total.setText(Formatter.formatMoney(compra.getPrCompra()));
    }

   /* private AlertDialog getDialogGuardarEgreso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tituloGuardarEgreso);
        builder.setMessage(R.string.msjGuardarEgreso);
        builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                guardarEgreso();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }*/

    private void guardarEgreso() {
        try {
            if (modo == ALTA) {
                tratarAltaEgreso();
            }
            if (modo == MODIFICACION) {
                tratarModificacionEgreso();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorManager.manageException("FrmEgreso", "guardarEgreso", e, getApplicationContext());
        }
    }

    private void tratarAltaEgreso() {
        if (validarDatos()) {
            RendicionBo rendicionBo = new RendicionBo(_repoFactory);
            try {
                rendicionBo.createEgreso(compra, getApplicationContext());
                Toast.makeText(getApplicationContext(), "Egreso guardado", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Se produjo un error al guardar", Toast.LENGTH_SHORT).show();
            }
            this.finish();
        }
    }

    private void tratarModificacionEgreso() {
        if (validarDatos()) {
            RendicionBo rendicionBo = new RendicionBo(_repoFactory);
            try {
                rendicionBo.updateEgreso(compra, getApplicationContext());
                Toast.makeText(getApplicationContext(), "Modificaciones guardadas", Toast.LENGTH_SHORT).show();
                limpiarInputs();
                actualizarTotales();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Se produjo un error al guardar", Toast.LENGTH_SHORT).show();
            }
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        switch (modo) {
            case ALTA:
            case MODIFICACION:
                //showDialog(DIALOG_GUARDAR_EGRESO);
                createDialog(DIALOG_GUARDAR_EGRESO);
                break;
            case CONSULTA:
                finish();
                break;
        }
    }

    /*@Override
    protected AppCompatDialog onCreateDialog(int id) {
        AppCompatDialog dialog = null;
        switch (id) {
            case DIALOG_GUARDAR_EGRESO:
                dialog = getDialogGuardarEgreso();
                break;
        }

        return dialog;
    }*/
    protected void createDialog(int id) {
        SimpleDialogFragment simpleDialogFragment;
        String sTituloDialogo;
        String sMensajeDialogo;
        switch (id) {
            case DIALOG_GUARDAR_EGRESO:
                sTituloDialogo = this.getString(R.string.tituloGuardarEgreso);
                sMensajeDialogo = this.getString(R.string.msjGuardarEgreso);
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL,
                        sMensajeDialogo, sTituloDialogo,
                        new SimpleDialogFragment.OkCancelListener() {

                            @Override
                            public void onOkSelected() {
                                guardarEgreso();
                            }

                            @Override
                            public void onCancelSelected() {
                                // TODO Auto-generated method stub
                                finish();
                            }
                        });
                simpleDialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_GUARDAR_EGRESO);
                break;
        }
    }


    public int getModo() {
        return modo;
    }

    private boolean validarDatos() {
        boolean valido = false;
        if (!(compra.getPrCompra() > 0)) {
            com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.tituloError), ErrorManager.CargarPrecioCompra);
            alert.show();
        } else if (compra.getId().getIdPuntoVenta() == null) {
            com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.tituloError), ErrorManager.SeleccionarPuntoVenta);
            alert.show();
        } else if (compra.getId().getIdNumero() == 0) {
            com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.tituloError), ErrorManager.IngresarNumero);
            alert.show();
        } else if (compra.getId().getIdNumero() > 99999999) {
            com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.tituloError), ErrorManager.NumeroMaxCifras);
            alert.show();
        } else if (compra.getDeConcepto() != null && compra.getDeConcepto().length() > 60) {
            com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.tituloError), ErrorManager.ConceptoMaxCaracteres);
            alert.show();
        } else {
            CompraBo compraDao = new CompraBo(_repoFactory);
            try {
                if (!compraDao.existsEgreso(compra.getId())) {
                    valido = true;
                } else {
                    com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.tituloError), ErrorManager.EgresoExistente);
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.tituloError), ErrorManager.ErrorAlbuscarEgreso);
                alert.show();
            }
        }
        return valido;
    }

    private void limpiarInputs() {
        this.compra = new Compra();
        CabeceraEgresoFragment frmCabecera = (CabeceraEgresoFragment) getSupportFragmentManager().findFragmentByTag(TAG_CABECERA);
        DetalleEgresoFragment frmDetalle = (DetalleEgresoFragment) getSupportFragmentManager().findFragmentByTag(TAG_DETALLE);
        if (frmCabecera != null) {
            frmCabecera.setCompra(compra);
        }
        if (frmDetalle != null) {
            frmDetalle.setCompra(compra);
        }
        if (frmCabecera != null && frmCabecera.isVisible()) {
            frmCabecera.limpiarImputs();
        } else {
            if (frmCabecera != null) {
                frmCabecera.setLimpiarInputs(true);
            }
        }
        if (frmDetalle != null && frmDetalle.isVisible()) {
            frmDetalle.limpiarLista();
        }
    }

    public Proveedor getProveedorAnteriorSeleccionado() {
        return proveedorAnteriorSeleccionado;
    }

    public void setProveedorAnteriorSeleccionado(Proveedor proveedorAnteriorSeleccionado) {
        this.proveedorAnteriorSeleccionado = proveedorAnteriorSeleccionado;
    }

    @Override
    protected void onSaveInstanceState(Bundle guardarEstado) {
        guardarEstado.putSerializable(STATE_EGRESO, compra);
        guardarEstado.putSerializable(Compra.EXTRA_MODO, modo);
        super.onSaveInstanceState(guardarEstado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
        compra = (Compra) recEstado.getSerializable(STATE_EGRESO);
        modo = (int) recEstado.getSerializable(Compra.EXTRA_MODO);
    }
}
