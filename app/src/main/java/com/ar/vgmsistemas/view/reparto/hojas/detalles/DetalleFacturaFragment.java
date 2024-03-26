package com.ar.vgmsistemas.view.reparto.hojas.detalles;

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
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.KeyboardUtils;
import com.ar.vgmsistemas.view.BaseFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment.SingleChoiceListener;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.DetalleFacturaHojaAdapter.DetalleFacturaListener;

public class DetalleFacturaFragment extends BaseFragment {

    private HojaDetalle mHojaDetalle;
    private HojaDetalleBo mHojaDetalleBo;
    private FrmDetalleFacturaReparto mDetalleFacturaReparto;
    private SearchView mSearchView;
    private DetalleFacturaHojaAdapter mAdapter;
    private boolean isEnviado;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (savedInstanceState != null) {
            try {
                mHojaDetalle = (HojaDetalle) savedInstanceState.getSerializable(EXTRA_HOJA_DETALLE);
            } catch (Exception e) {
                mHojaDetalle = (HojaDetalle) bundle.get(FrmDetalleFacturaReparto.KEY_HOJA_DETALLE);
            }
        } else {
            mHojaDetalle = (HojaDetalle) bundle.get(FrmDetalleFacturaReparto.KEY_HOJA_DETALLE);
        }
        mDetalleFacturaReparto = (FrmDetalleFacturaReparto) getActivity();

        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        mHojaDetalleBo = new HojaDetalleBo(repoFactory);
        isEnviado = false;
        try {
            isEnviado = mHojaDetalleBo.isEnviado(mHojaDetalle);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void showDialogConfigBusqueda() {
        String[] opciones = {"Codigo", "Descripcion"};
        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(opciones,
                mAdapter.getCampoBusqueda(), "Seleccione criterio de busqueda", new SingleChoiceListener() {

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
        mAdapter = new DetalleFacturaHojaAdapter(getActivity(), R.layout.lyt_hoja_detalle_item_factura, mHojaDetalle,
                mDetalleFacturaReparto.getVenta().getDetalles(), getChildFragmentManager(), new DetalleFacturaListener() {

            @Override
            public void onChangeDevolucion() {
                updateFooter();
            }
        });

        lvLineas.setAdapter(mAdapter);

        updateFooter();
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
            mSearchView.setQueryHint("Por descripcion");
        } else {
            mSearchView.setQueryHint("Por codigo");
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
        String[] opciones = {"Secuencia", "Codigo", "Descripcion"};
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
            double importeDevuelto = 0d;
            try {
                importeDevuelto = VentaBo.calcularTotal(mDetalleFacturaReparto.getVenta(), VentaBo.NC_DEVOLUCION);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mDetalleFacturaReparto.getHojaDetalle().setPrNotaCredito(importeDevuelto);
            mDetalleFacturaReparto.updateFooter();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_HOJA_DETALLE, mHojaDetalle);
    }
}
