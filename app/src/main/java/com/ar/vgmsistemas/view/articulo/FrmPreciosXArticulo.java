package com.ar.vgmsistemas.view.articulo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.List;

public class FrmPreciosXArticulo extends BaseNavigationFragment {

    private TextView lblDescripcionArticulo;
    private Articulo _articulo;
    private ListView lstPrecios;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._articulo = (Articulo) getArguments().getSerializable(FrmGestionArticulo.EXTRA_ARTICULO);
        getNavigationMenu().setTitle(ItemMenuNames.STRING_PRECIO_ARTICULO);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_precios_x_articulo, null);
        lblDescripcionArticulo = (TextView) view.findViewById(R.id.lblDescripcionArticulo);
        lstPrecios = (ListView) view.findViewById(R.id.lstPrecios);
        loadData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadData() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        ArticuloBo articuloBo = new ArticuloBo(repoFactory);

        this.lblDescripcionArticulo.setText(this._articulo.getDescripcion());
        try {
            articuloBo.recoveryListaPrecio(this._articulo);
        } catch (Exception e) {
            ErrorManager.manageException("FrmPreciosXArticulo", "loadData", e, getActivity());
        }
        List<ListaPrecioDetalle> listasPrecio = this._articulo.getListaPreciosDetalle();
        PrecioXArticuloAdapter adapter = new PrecioXArticuloAdapter(getActivity(), R.layout.lyt_precios_x_articulo_item, listasPrecio, _articulo);
        this.lstPrecios.setAdapter(adapter);
    }

}
