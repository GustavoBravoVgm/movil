package com.ar.vgmsistemas.view.reparto.hojas.detalles.Multiples;

import static com.ar.vgmsistemas.view.reparto.hojas.HojaFragment.EXTRA_HOJA_DETALLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.view.MenuItemCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaDetalleBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.KeyboardUtils;
import com.ar.vgmsistemas.view.BaseFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment.SingleChoiceListener;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.DetalleFacturaHojaAdapter;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.Multiples.DetallesFacturasHojaAdapter.DetalleFacturaListener;

import java.util.List;

public class DetallesFacturasFragment extends BaseFragment {


    private HojaDetalle mHojaDetalle;
    private List<HojaDetalle> mHojasDetalle;
    private HojaDetalleBo mHojaDetalleBo;
    private FrmDetallesFacturasReparto mDetalleFacturaReparto;
    private SearchView mSearchView;
    private DetallesFacturasHojaAdapter mAdapter;
    private boolean isEnviado;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (savedInstanceState != null) {
            try {
                mHojasDetalle = (List<HojaDetalle>) savedInstanceState.getSerializable(EXTRA_HOJA_DETALLE);
            } catch (Exception e) {
                mHojasDetalle = (List<HojaDetalle>) bundle.get(FrmDetallesFacturasReparto.KEY_HOJA_DETALLE);
            }
        } else {
            mHojasDetalle = (List<HojaDetalle>) bundle.get(FrmDetallesFacturasReparto.KEY_HOJA_DETALLE);
        }
        mHojaDetalle = mHojasDetalle.get(0);
        mDetalleFacturaReparto = (FrmDetallesFacturasReparto) getActivity();
        mHojaDetalleBo = new HojaDetalleBo(RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM));
        isEnviado = false;
        try {
            isEnviado = mHojaDetalleBo.isEnviado(mHojaDetalle);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //	mVenta = mDetalleFacturaReparto.getVenta();

    }

    private void showDialogConfigBusqueda() {
        String[] opciones = {"Código", "Descripción"};
        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(opciones,
                mAdapter.getCampoBusqueda(), "Seleccione criterio de búsqueda", new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        setHintSearchView(pos);
                        mAdapter.setCampoBusqueda(pos);

                    }
                });
        dialogFragment.show(getChildFragmentManager(), "dialog");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.lyt_devolucion, null);
        ListView lvLineas = (ListView) view.findViewById(R.id.lvLineas);
        lvLineas.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                KeyboardUtils.hideKeyboard(getActivity());
                return false;
            }
        });
        mAdapter = new DetallesFacturasHojaAdapter(getActivity(), R.layout.lyt_hoja_detalle_item_factura, mHojasDetalle,
                mDetalleFacturaReparto.getVentaDetalles(), getChildFragmentManager(), new DetalleFacturaListener() {

            @Override
            public void onChangeDevolucion() throws Exception {
                updateFooter();
            }
        });

        lvLineas.setAdapter(mAdapter);

        try {
            updateFooter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onPrepareOptionsMenu(menu);
        if (isEnviado) {
            menu.findItem(R.id.mi_guardar).setEnabled(false);
        }
        if (mSearchView != null) {
            mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String arg0) {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String arg0) {
                    mAdapter.getFilter().filter(arg0);
                    return false;
                }
            });
            setHintSearchView(DetalleFacturaHojaAdapter.FILTER_DESCRIPCION);
        }
    }

    private void setHintSearchView(int pos) {
        if (pos == DetalleFacturaHojaAdapter.FILTER_DESCRIPCION) {
            mSearchView.setQueryHint("Por descripción");
        } else {
            mSearchView.setQueryHint("Por código");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.mn_devolucion_reparto, menu);

        MenuItem item = menu.findItem(R.id.mi_buscar);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_guardar:
                getActivity().onBackPressed();
                return true;
            case R.id.mi_filtro:
                showDialogConfigBusqueda();
                return true;
            case R.id.mi_ordenar:
                showDialogOrdenar();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialogOrdenar() {
        String[] opciones = {"Secuencia", "Código", "Descripción"};
        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(opciones,
                mAdapter.getCampoOrden(), "Seleccione criterio de orden", new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        mAdapter.setCampoOrden(pos);
                        mAdapter.sort();
                    }
                });
        dialogFragment.show(getChildFragmentManager(), "dialog");
    }

    public void updateFooter() {
        try {
            isEnviado = mHojaDetalleBo.isEnviado(mHojaDetalle);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!isEnviado) {
            int i = 0;
            for (Venta venta : mDetalleFacturaReparto.getVentas()) {
                double importeDevuelto = 0d;
                try {
                    importeDevuelto = VentaBo.calcularTotal(venta, VentaBo.NC_DEVOLUCION);
                    if (mDetalleFacturaReparto.getHojaDetalle() != null) {
                        for (HojaDetalle hojaDetCalc : mDetalleFacturaReparto.getHojaDetalle()) {
                            if (hojaDetCalc != null) {
                                if (hojaDetCalc.getId().getIdFcnc().equals(venta.getId().getIdDocumento()) &&
                                        hojaDetCalc.getId().getIdTipoab().equals(venta.getId().getIdLetra()) &&
                                        hojaDetCalc.getId().getIdPtovta() == venta.getId().getPuntoVenta() &&
                                        hojaDetCalc.getId().getIdNumdoc().equals(venta.getId().getIdNumeroDocumento())
                                ) {
                                    hojaDetCalc.setPrNotaCredito(importeDevuelto);
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    mDetalleFacturaReparto.getHojaDetalle().get(i).setPrNotaCredito(importeDevuelto);
                    e.printStackTrace();
                }
                i = +1;
            }
            mDetalleFacturaReparto.updateFooter();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_HOJA_DETALLE, mHojaDetalle);
    }
}
