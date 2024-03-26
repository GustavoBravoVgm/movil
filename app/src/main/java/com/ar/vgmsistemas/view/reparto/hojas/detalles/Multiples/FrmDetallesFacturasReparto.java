package com.ar.vgmsistemas.view.reparto.hojas.detalles.Multiples;

import android.annotation.SuppressLint;
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
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.SlidingTabLayout;
import com.ar.vgmsistemas.view.cobranza.PagosFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.AutorizacionCCRepartoDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FrmDetallesFacturasReparto extends BaseActivity {

    private static final int CABECERA = 0;
    private static final int DEVOLUCION = 1;
    private static final int ACTIVITY_CTA_CTE_INHABILITADA = 9999;
    private static final int PAGO = 2;
    private static List<HojaDetalle> mHojasDetalle;

    public static final String KEY_HOJA_DETALLE = "hoja_detalle";
    public static final String KEY_ENABLE = "hoja_enable";

    private ChangeFooterListener mFooterListener;
    private Venta mVenta;
    private List<Venta> mVentas;
    private DetallesFacturasFragment mDetalleFacturaFragment;
    private PagoMultiplesHojasFragment mPagoMultiplesHojasFragment;
    private CabecerasFacturasHojaFragment mCabeceraFacturaHojaFragment;
    private TextView tvPagado, tvCtaCte, tvTotal, tvCredito;
    private ViewPager pager;

    private ViewPagerAdapter adapter;

    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Cabecera", "Devolucion", "Pago"};
    int Numboftabs = 3;
    private boolean isEnviado;

    //BO´s
    private VentaBo mVentaBo;
    private HojaDetalleBo mHojaDetalleBo;
    //DAO
    RepositoryFactory _repoFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_detalle_factura_reparto);
        loadData();
        loadViews();

    }

    @SuppressLint("NewApi")
    private void loadViews() {
        // toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles
        // fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        mPagoMultiplesHojasFragment = new PagoMultiplesHojasFragment();
        mFooterListener = (ChangeFooterListener) mPagoMultiplesHojasFragment;
        mDetalleFacturaFragment = new DetallesFacturasFragment();
        mCabeceraFacturaHojaFragment = new CabecerasFacturasHojaFragment();

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
        tvPagado = (TextView) findViewById(R.id.tvPagos);
        tvCredito = (TextView) findViewById(R.id.tvCredito);
        tvTotal = (TextView) findViewById(R.id.tvTotal);


        tvTotal.setText(Formatter.formatMoney(getTotalFacturas()));
        tvCtaCte = (TextView) findViewById(R.id.tvCtaCte);
        updateFooter();
        setActionBarTitle("Hoja N° " + mHojasDetalle.get(0).getId().getIdHoja());
    }

    @Override
    public void onBackPressed() {
        if (!isEnviado) {
            SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(
                    SimpleDialogFragment.TYPE_OK_CANCEL, "Desea guardar los cambios", "Importante",
                    new SimpleDialogFragment.OkCancelListener() {

                        @Override
                        public void onOkSelected() {
                            Double total = 0D;
                            for (HojaDetalle mHojaDetalle : mHojasDetalle) {
                                total += mHojaDetalle.getPrTotal() - mHojaDetalle.getPrPagado() - mHojaDetalle.getPrNotaCredito();
                            }
                            if (total + 0.05 >= 0) {
                                showDialogGuardarDetalle();
                            } else {
                                SimpleDialogFragment sp = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, "Error", "El monto de lo pagado mas los créditos supera el total de la factura");
                                sp.show(getSupportFragmentManager(), "error al guardar");
                            }
                        }

                        @Override
                        public void onCancelSelected() {
                            SimpleDialogFragment simpleDialogFragment1 = SimpleDialogFragment.newInstance(
                                    SimpleDialogFragment.TYPE_OK_CANCEL, "Desea seguir editando la hoja", "Importante",
                                    new SimpleDialogFragment.OkCancelListener() {

                                        @Override
                                        public void onCancelSelected() {
                                            FrmDetallesFacturasReparto.this.finish();
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
            FrmDetallesFacturasReparto.super.onBackPressed();
        }
    }

    private void showDialogGuardarDetalle() {

        GuardarHojaDetalleMultipleDialogFragment dialogFragment = GuardarHojaDetalleMultipleDialogFragment.newInstance(mHojasDetalle,
                new GuardarHojaDetalleMultipleDialogFragment.DevolucionOkListener() {

                    @Override
                    public void onOkSelected(MotivoCredito motivoCredito) {
                        boolean muestroMotivoCtaCte = false;
                        for (HojaDetalle hojaDetalle : mHojasDetalle) {
                            if (hojaDetalle.getPrNotaCredito() > 0) {
                                if (mVentas != null && mVentas.size() > 0) {
                                    for (Venta miVenta : mVentas) {
                                        if (miVenta.getId().getIdDocumento().equals(hojaDetalle.getId().getIdFcnc()) &&
                                                miVenta.getId().getIdLetra().equals(hojaDetalle.getId().getIdTipoab()) &&
                                                miVenta.getId().getPuntoVenta() == hojaDetalle.getId().getIdPtovta() &&
                                                miVenta.getId().getIdNumeroDocumento() == hojaDetalle.getId().getIdNumdoc()) {
                                            /*seteo el motivo del CM*/
                                            miVenta.setIdMotivoRechazoNC(motivoCredito.getIdMotivoRechazoNC());
                                        }

                                    }
                                }
                            }
                            if (!hojaDetalle.getCondicionVenta().isCuentaCorriente() && HojaDetalleBo.isEnCuentaCorriente(hojaDetalle)) {
                                showAutorizacionDialog();
                                muestroMotivoCtaCte = true;
                            }
                        }
                        if (!muestroMotivoCtaCte) {
                            guardarHojaDetalle();
                        }
                    }
                });
        dialogFragment.show(getSupportFragmentManager(), "dialogGuardar");

    }

    private void guardarHojaDetalle() {
        try {
            HojaDetalleBo bo = new HojaDetalleBo(_repoFactory);
            int i = 0;
            //cuando la bandera esta en falso quiere decir que la entrega fue creada y no es necesario crear una nueva para las siguientes HojasDetalle
            boolean crearEntrega = true;
            for (HojaDetalle hojaDetalle : mHojasDetalle) {
                bo.guardarHojaDetalle(mVentas.get(i), hojaDetalle, getApplicationContext(), crearEntrega);
                i += 1;
                crearEntrega = false;
            }
            Intent intent = new Intent();
            intent.putExtra("hojadetalle", (Serializable) mHojasDetalle);
            setResult(Activity.RESULT_OK, intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            SimpleDialogFragment sp = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, "No se pudo guardar", e.getMessage());
            sp.show(getSupportFragmentManager(), "error al guardar");
        }

    }

    private void showAutorizacionDialog() {

        AutorizacionCCRepartoDialog dialog = AutorizacionCCRepartoDialog.newInstance(new AutorizacionCCRepartoDialog.AutorizacionListener() {

            @Override
            public void autorizacionAccepted(Object o) {
                if (o instanceof String) {
                    for (HojaDetalle hojaDetalle : mHojasDetalle) {
                        if (!hojaDetalle.getCondicionVenta().isCuentaCorriente() && HojaDetalleBo.isEnCuentaCorriente(hojaDetalle)) {
                            hojaDetalle.setTiEstado(HojaDetalle.CUENTA_CORRIENTE);
                        }
                        hojaDetalle.setCodigoAutorizacion(o.toString());
                    }
                } else if (o instanceof MotivoAutorizacion) {
                    for (HojaDetalle hojaDetalle : mHojasDetalle) {
                        if (!hojaDetalle.getCondicionVenta().isCuentaCorriente() && HojaDetalleBo.isEnCuentaCorriente(hojaDetalle)) {
                            hojaDetalle.setTiEstado(HojaDetalle.CUENTA_CORRIENTE);
                        }
                        hojaDetalle.setMotivoAutorizacion((MotivoAutorizacion) o);
                    }
                }
                guardarHojaDetalle();

            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");

    }

    public List<Venta> getVentas() {
        return mVentas;
    }

    public double getTotalFacturas() {
        double totalFacturas = 0;
        for (Venta venta : mVentas) {
            totalFacturas += venta.getTotal() * (venta.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
        }
        return totalFacturas;
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        CharSequence Titles[]; // This will Store the Titles of the Tabs which
        // are Going to be passed when ViewPagerAdapter
        // is created
        int NumbOfTabs; // Store the number of tabs, this will also be passed
        // when the ViewPagerAdapter is created

        // Build a Constructor and assign the passed Values to appropriate
        // values in the class
        public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
            super(fm);

            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabsumb;

        }

        // This method return the fragment for the every position in the View
        // Pager
        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            if (position == CABECERA) {
                args.putSerializable(FrmDetallesFacturasReparto.KEY_HOJA_DETALLE, (Serializable) mHojasDetalle);
                mCabeceraFacturaHojaFragment.setArguments(args);
                return mCabeceraFacturaHojaFragment;
            }
            if (position == DEVOLUCION) {
                args.putInt(PagosFragment.EXTRA_TYPE, PagosFragment.TYPE_HOJA_DETALLE);
                args.putSerializable(PagosFragment.EXTRA_ENTITY, (Serializable) mHojasDetalle);
                mDetalleFacturaFragment.setArguments(args);
                return mDetalleFacturaFragment;
            } else {
                args.putInt(PagosFragment.EXTRA_TYPE, PagosFragment.TYPE_HOJA_DETALLE);
                args.putSerializable(PagosFragment.EXTRA_ENTITY, (Serializable) mHojasDetalle);
                args.putBoolean(PagosFragment.EXTRA_IS_HOJA_DETALLE_ENVIADA, isEnviado);
                mPagoMultiplesHojasFragment.setArguments(args);
                return mPagoMultiplesHojasFragment;
            }

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
        Bundle b = getIntent().getExtras();
        mHojasDetalle = (List<HojaDetalle>) b.getSerializable(FrmDetallesFacturasReparto.KEY_HOJA_DETALLE);
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        mVentaBo = new VentaBo(_repoFactory);
        mVenta = new Venta();
        mVentas = new ArrayList<>();
        mHojaDetalleBo = new HojaDetalleBo(_repoFactory);
        for (HojaDetalle hojaDetalle : mHojasDetalle) {
            try {
                isEnviado = mHojaDetalleBo.isEnviado(hojaDetalle);
                mVenta = mVentaBo.recoveryById(hojaDetalle.getId().getIdFcnc(),
                        hojaDetalle.getId().getIdTipoab(), hojaDetalle.getId().getIdPtovta(),
                        hojaDetalle.getId().getIdNumdoc());

                reagruparCombos(mVenta);
                mVentas.add(mVenta);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
                //Calculo la cantidad porque pudo haber cambiado la definicion del combo
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

    public List<VentaDetalle> getVentaDetalles() {
        List<VentaDetalle> ventasDetalles = new ArrayList<>();
        for (Venta venta : mVentas) {
            ventasDetalles.addAll(venta.getDetalles());
        }
        return ventasDetalles;

    }

    private double getComprobantesCreditos() {
        double totalComprobanteNC = 0d;
        for (HojaDetalle hojaDetalle : mHojasDetalle) {
            if (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2) {
                totalComprobanteNC += hojaDetalle.getPrTotal();
            }
        }
        return totalComprobanteNC;
    }

    public void updateEntrega(Entrega entrega) {
        double totalEntrega;
        double montoAPagar;
        double montoCompNC = getComprobantesCreditos();//Comprobantes NC creados desde admin
        totalEntrega = Matematica.Round(entrega.obtenerTotalPagos(), 2);
        double totXComprobante = 0d;
        for (HojaDetalle hojaDetalle : mHojasDetalle) {
            hojaDetalle.setEntrega(entrega);
            totXComprobante = hojaDetalle.getPrTotal() - hojaDetalle.getPrNotaCredito();
            if ((totalEntrega + montoCompNC) >= totXComprobante) {
                montoAPagar = totXComprobante * (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
            } else {
                montoAPagar = totalEntrega * (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
            }
            hojaDetalle.setPrPagado(montoAPagar);
            totalEntrega -= montoAPagar;

        }
        updateFooter();

    }

    public void updateFooter() {
        Double totalPagado = 0D;
        Double totalCuentaCorriente = 0D;
        Double totalNotaCredito = 0D;
        for (HojaDetalle hojaDetalle : mHojasDetalle) {
            totalPagado += hojaDetalle.getPrPagado() * (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
            totalCuentaCorriente += HojaDetalleBo.getEnCuentaCorriente(hojaDetalle) * (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
            totalNotaCredito += hojaDetalle.getPrNotaCredito() * (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
        }
        tvPagado.setText(Formatter.formatMoney(totalPagado));
        tvCtaCte.setText(Formatter.formatMoney(totalCuentaCorriente));
        tvCredito.setText(Formatter.formatMoney(totalNotaCredito));
        mFooterListener.onChange(totalPagado + totalCuentaCorriente + totalNotaCredito);
    }

    public interface ChangeFooterListener {
        void onChange(double totalFactura);
    }

    public List<HojaDetalle> getHojaDetalle() {
        return mHojasDetalle;
    }

    public void setArguments(List<HojaDetalle> hojasDetalles) {
        mHojasDetalle = hojasDetalles;
    }

}
