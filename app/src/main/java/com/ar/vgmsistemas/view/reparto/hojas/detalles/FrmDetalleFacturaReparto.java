package com.ar.vgmsistemas.view.reparto.hojas.detalles;

import static com.ar.vgmsistemas.view.reparto.hojas.HojaFragment.EXTRA_HOJA_DETALLE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaDetalleBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.MotivoAutorizacion;
import com.ar.vgmsistemas.entity.MotivoCredito;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.SlidingTabLayout;
import com.ar.vgmsistemas.view.cobranza.PagosFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.AutorizaConCodCCRepartoDialog.AutorizacionConCodListener;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.AutorizacionCCRepartoDialog.AutorizacionListener;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.GuardarHojaDetalleDialogFragment.DevolucionOkListener;

import java.util.Iterator;

public class FrmDetalleFacturaReparto extends BaseActivity {

    private static final int CABECERA = 0;
    private static final int DEVOLUCION = 1;
    private static final int ACTIVITY_CTA_CTE_INHABILITADA = 9999;
    private static final int PAGO = 2;
    private static final String TAG = FrmDetalleFacturaReparto.class.getCanonicalName();

    public static final String KEY_HOJA_DETALLE = "hoja_detalle";
    public static final String KEY_ENABLE = "hoja_enable";

    private ChangeFooterListener mFooterListener;
    private Venta mVenta;
    private DetalleFacturaFragment mDetalleFacturaFragment;
    private HojaPagosFragment mHojaPagosFragment;
    private CabeceraFacturaHojaFragment mCabeceraFacturaHojaFragment;

    private TextView tvPagado, tvCtaCte, tvTotal, tvCredito;
    private HojaDetalle mHojaDetalle;
    private ViewPager pager;

    private ViewPagerAdapter adapter;

    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Cabecera", "Devolucion", "Pago"};
    int mNumbOfTabs = 3;
    private boolean isEnviado;

    //BO´s
    private HojaDetalleBo mHojaDetalleBo;
    private VentaBo mVentaBo;

    //DAO
    RepositoryFactory _repoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_detalle_factura_reparto);
        loadData();
        loadViews();

    }

    private void loadViews() {
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles
        // fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, mNumbOfTabs);

        // Assigning ViewPager View and setting the adapter
        mHojaPagosFragment = new HojaPagosFragment();
        mFooterListener = (ChangeFooterListener) mHojaPagosFragment;
        mDetalleFacturaFragment = new DetalleFacturaFragment();
        mCabeceraFacturaHojaFragment = new CabeceraFacturaHojaFragment();

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true,
        // This makes the tabs Space Evenly in
        // Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(android.R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setCustomTabView(R.layout.custom_tab, 0);
        tabs.setViewPager(pager);
        pager.setCurrentItem(DEVOLUCION);
        tvPagado = findViewById(R.id.tvPagos);//(TextView)
        tvCredito = findViewById(R.id.tvCredito);//(TextView)
        tvTotal = findViewById(R.id.tvTotal);//(TextView)
        tvTotal.setText(Formatter.formatMoney(mVenta.getTotal()));
        tvCtaCte = findViewById(R.id.tvCtaCte);//(TextView)
        updateFooter();
        setActionBarTitle("Hoja N " + mHojaDetalle.getId().getIdHoja());
    }

    @Override
    public void onBackPressed() {
        if (!isEnviado) {
            SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(
                    SimpleDialogFragment.TYPE_OK_CANCEL, "Desea guardar los cambios", "Importante",
                    new SimpleDialogFragment.OkCancelListener() {

                        @Override
                        public void onOkSelected() {
                            if (!mHojaDetalle.getCondicionVenta().isCuentaCorriente() && HojaDetalleBo.isEnCuentaCorriente(mHojaDetalle) &&
                                    HojaDetalleBo.isCtrlCodCuentaCorriente(mHojaDetalle)) {
                                showCodAutorizacionDialog();
                            } else {
                                if (mHojaDetalle.getPrTotal() - mHojaDetalle.getPrPagado() - mHojaDetalle.getPrNotaCredito() + 0.05 >= 0) {
                                    showDialogGuardarDetalle();
                                } else {
                                    SimpleDialogFragment sp = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, "Error", "El monto de lo pagado mas los creditos supera el total de la factura");
                                    sp.show(getSupportFragmentManager(), "error al guardar");
                                }
                            }


                        }

                        @Override
                        public void onCancelSelected() {
                            SimpleDialogFragment simpleDialogFragment1 = SimpleDialogFragment.newInstance(
                                    SimpleDialogFragment.TYPE_OK_CANCEL, "Desea seguir editando la hoja", "Importante",
                                    new SimpleDialogFragment.OkCancelListener() {

                                        @Override
                                        public void onCancelSelected() {
                                            FrmDetalleFacturaReparto.this.finish();
                                        }

                                        @Override
                                        public void onOkSelected() {

                                        }
                                    }
                            );
                            simpleDialogFragment1.show(getSupportFragmentManager(), "dialog");
                        }
                    });
            simpleDialogFragment.show(getSupportFragmentManager(), "dialog");
        } else {
            FrmDetalleFacturaReparto.super.onBackPressed();
        }
    }

    private void showDialogGuardarDetalle() {
        GuardarHojaDetalleDialogFragment dialogFragment = GuardarHojaDetalleDialogFragment.newInstance(mHojaDetalle,
                new DevolucionOkListener() {

                    @Override
                    public void onOkSelected(MotivoCredito motivoCredito) {

                        if (mHojaDetalle.getPrNotaCredito() > 0)
                            mVenta.setIdMotivoRechazoNC(motivoCredito.getIdMotivoRechazoNC());

                        if (!mHojaDetalle.getCondicionVenta().isCuentaCorriente() && HojaDetalleBo.isEnCuentaCorriente(mHojaDetalle)) {
                            showAutorizacionDialog();
                        } else {
                            guardarHojaDetalle();
                        }

                    }

                });
        dialogFragment.show(getSupportFragmentManager(), "dialogGuardar");

    }

    private void guardarHojaDetalle() {
        try {
            HojaDetalleBo bo = new HojaDetalleBo(_repoFactory);
            bo.guardarHojaDetalle(mVenta, mHojaDetalle, getApplicationContext());
            Intent intent = new Intent();
            intent.putExtra("hojadetalle", mHojaDetalle);
            setResult(Activity.RESULT_OK, intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            ErrorManager.manageException(TAG, "Guardar HojasDetalle", e);
            SimpleDialogFragment sp = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, "No se pudo guardar", e.getMessage() + " " + e.getStackTrace().toString());
            sp.show(getSupportFragmentManager(), "error al guardar");
        }

    }

    private void showAutorizacionDialog() {
        AutorizacionCCRepartoDialog dialog = AutorizacionCCRepartoDialog.newInstance(new AutorizacionListener() {
            @Override
            public void autorizacionAccepted(Object o) {
                mHojaDetalle.setTiEstado(HojaDetalle.CUENTA_CORRIENTE);
                if (o instanceof String) {
                    mHojaDetalle.setCodigoAutorizacion(o.toString());
                } else if (o instanceof MotivoAutorizacion) {
                    mHojaDetalle.setMotivoAutorizacion((MotivoAutorizacion) o);
                }
                guardarHojaDetalle();
            }
        });
        dialog.show(getSupportFragmentManager(), "dialogShowAutorizacionDialog");
    }

    private void showCodAutorizacionDialog() {
        AutorizaConCodCCRepartoDialog dialog = AutorizaConCodCCRepartoDialog.newInstance(new AutorizacionConCodListener() {

            @Override
            public void autorizacionAccepted(Object o) {
                mHojaDetalle.setTiEstado(HojaDetalle.CUENTA_CORRIENTE);
                if (o instanceof String) {
                    mHojaDetalle.setCodAutCtaCte(o.toString());
                    showDialogGuardarDetalle();
                }

            }
        }, mHojaDetalle);
        dialog.show(getSupportFragmentManager(), "dialogoConCodAutoriza");
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        CharSequence Titles[]; // This will Store the Titles of the Tabs which
        // are Going to be passed when ViewPagerAdapter
        // is created
        int NumbOfTabs; // Store the number of tabs, this will also be passed
        // when the ViewPagerAdapter is created

        // Build a Constructor and assign the passed Values to appropriate
        // values in the class
        public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabs) {
            super(fm);

            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabs;

        }

        // This method return the fragment for the every position in the View
        // Pager
        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            if (position == CABECERA) {
                args.putSerializable(FrmDetalleFacturaReparto.KEY_HOJA_DETALLE, mHojaDetalle);
                mCabeceraFacturaHojaFragment.setArguments(args);
                return mCabeceraFacturaHojaFragment;
            }
            if (position == DEVOLUCION) {
                args.putInt(PagosFragment.EXTRA_TYPE, PagosFragment.TYPE_HOJA_DETALLE);
                args.putSerializable(PagosFragment.EXTRA_ENTITY, mHojaDetalle);
                mDetalleFacturaFragment.setArguments(args);
                return mDetalleFacturaFragment;
            }

            args.putInt(PagosFragment.EXTRA_TYPE, PagosFragment.TYPE_HOJA_DETALLE);
            args.putSerializable(PagosFragment.EXTRA_ENTITY, mHojaDetalle);
            args.putBoolean(PagosFragment.EXTRA_IS_HOJA_DETALLE_ENVIADA, isEnviado);
            mHojaPagosFragment.setArguments(args);
            return mHojaPagosFragment;

        }

        // This method return the titles for the Tabs in the Tab Strip

        @Override
        public CharSequence getPageTitle(int position) {
//			return Titles[position];
            Drawable image = getResources().getDrawable(imageResId[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }

        // This method return the Number of tabs for the tabs Strip
        private int[] imageResId = {
                R.drawable.selector_sincronizacion,
                R.drawable.ic_assignment_white,
                R.drawable.ic_attach_money_white
        };

        @Override
        public int getCount() {
            return NumbOfTabs;
        }

    }

    private void loadData() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        mVentaBo = new VentaBo(_repoFactory);
        mHojaDetalleBo = new HojaDetalleBo(_repoFactory);
        mVenta = new Venta();
        mHojaDetalle = (HojaDetalle) getIntent().getSerializableExtra(FrmDetalleFacturaReparto.KEY_HOJA_DETALLE);
        try {
            isEnviado = mHojaDetalleBo.isEnviado(mHojaDetalle);
            mVenta = mVentaBo.recoveryById(mHojaDetalle.getId().getIdFcnc(),
                    mHojaDetalle.getId().getIdTipoab(), mHojaDetalle.getId().getIdPtovta(),
                    mHojaDetalle.getId().getIdNumdoc());

            reagruparCombos(mVenta);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Venta reagruparCombos(Venta venta) {
        //Recorrer venta buscando cabeceras de combo, buscar detalles con idPromo = id cabecera, asignar a detalleCombo de la cabecera y quitar de la venta
        for (Iterator<VentaDetalle> iterator = venta.getDetalles().iterator(); iterator.hasNext(); ) {
            VentaDetalle vd = iterator.next();
            if (vd.isCabeceraPromo()) {
                //Tirar magia
                double cantidad = 0d;
                for (VentaDetalle vd2 : venta.getDetalles()) { //Sumo cantidades de detalles con idPromo = idArticulo de la cabecera
                    if (vd2.getTipoLista() == vd.getTipoLista() && vd2.getIdPromo() != null && vd2.getIdPromo() == vd.getArticulo().getId()) {
                        cantidad += vd2.getCantidad();
                    }
                }
                //Calculo la cantidad porque pudo haber cambiado la definición del combo
                double cantidadPorCombo = cantidad / vd.getCantidad();
                double cantidadAsignada = 0d;

                //Vuelvo a recorrer para cargar detalles del combo
                for (Iterator<VentaDetalle> iterator2 = venta.getDetalles().iterator(); iterator2.hasNext(); ) {
                    VentaDetalle vd2 = iterator2.next();
                    if (vd2.getTipoLista() == vd.getTipoLista() && vd2.getIdPromo() != null && vd2.getIdPromo() == vd.getArticulo().getId() && vd2.getCantidad() > 0) { //Es detalle del combo

                        if (cantidadAsignada < cantidadPorCombo * vd.getCantidad()) {

                            VentaDetalle vdAux = null;
                            try {
                                vdAux = vd2.clone();
                                double cantidadAAsignar = 0d;
                                if (vdAux.getCantidad() <= cantidadPorCombo * vd.getCantidad() - cantidadAsignada) {
                                    cantidadAAsignar = vdAux.getCantidad();
                                } else {
                                    cantidadAAsignar = cantidadPorCombo * vd.getCantidad() - cantidadAsignada;
                                }

                                vdAux.setUnidades(0);
                                vdAux.setBultos(0);
                                vdAux.setIdFcSecuencia(vdAux.getId().getSecuencia());
                                //Veo si saco de unidades o de bultos
                                if (vd2.getUnidades() >= cantidadAAsignar) {
                                    vd2.setUnidades(vd2.getUnidades() - cantidadAAsignar);
                                    vdAux.setUnidades(cantidadAAsignar);
                                } else if (vd2.getBultos() * vd2.getArticulo().getUnidadPorBulto() >= cantidadAAsignar) {
                                    vd2.setBultos(Math.toIntExact(vd2.getBultos() - Math.round(cantidadAAsignar / vd2.getArticulo().getUnidadPorBulto())));
                                    vdAux.setBultos(Math.toIntExact(Math.round(cantidadAAsignar / vd2.getArticulo().getUnidadPorBulto())));
                                } else { //Tengo que sacar de bultos y de unidades
                                    cantidadAAsignar -= vd2.getBultos() * vd2.getArticulo().getUnidadPorBulto();
                                    cantidadAsignada += vd2.getBultos() * vd2.getArticulo().getUnidadPorBulto();
                                    vdAux.setBultos(vd2.getBultos());
                                    vd2.setBultos(0);
                                    vdAux.setUnidades(cantidadAAsignar);
                                    vd2.setUnidades(vd2.getUnidades() - cantidadAAsignar);
                                }
                                cantidadAsignada += cantidadAAsignar;
                                vd.getDetalleCombo().add(vdAux);
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        //Elimino detalles que hayan quedado con cantidad 0
        for (Iterator<VentaDetalle> iterator = venta.getDetalles().iterator(); iterator.hasNext(); ) {
            VentaDetalle vd = iterator.next();
            if (vd.getCantidad() == 0) {
                iterator.remove();
            }
        }
        return venta;
    }

    ;

    public Venta getVenta() {
        return mVenta;
    }

    public void updateEntrega(Entrega entrega) {
        mHojaDetalle.setEntrega(entrega);
        mHojaDetalle.setPrPagado(HojaDetalleBo.getTotalPagado(mHojaDetalle.getEntrega()));
        updateFooter();
    }

    public void updateFooter() {

        tvPagado.setText(Formatter.formatMoney(mHojaDetalle.getPrPagado() * (mHojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1)));
        tvCtaCte.setText(Formatter.formatMoney(HojaDetalleBo.getEnCuentaCorriente(mHojaDetalle) * (mHojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1)));
        tvCredito.setText(Formatter.formatMoney(mHojaDetalle.getPrNotaCredito() * (mHojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1)));
        mFooterListener.onChange(/*HojaDetalleBo.getTotalAPagar(*/mHojaDetalle.getPrTotal() * (mHojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1)/*, mHojaDetalle.getEntrega())*/);
    }

    public interface ChangeFooterListener {
        void onChange(double totalFactura);
    }

    public HojaDetalle getHojaDetalle() {
        return mHojaDetalle;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_HOJA_DETALLE, mHojaDetalle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            mHojaDetalle = (HojaDetalle) savedInstanceState.getSerializable(EXTRA_HOJA_DETALLE);
        } catch (Exception e) {
            //
        }
    }
}
