package com.ar.vgmsistemas.view.articulo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.ar.vgmsistemas.view.menu.NavigationMenu;

public class FrmDetalleArticulo extends BaseNavigationFragment {
    private static final String EXTRA_TITLE = "titulo";

    private EditText txtCodigo;
    private EditText txtStock;
    private EditText txtDescripcion;
    private EditText txtUnidadesXBulto;
    private EditText txtNegocio;
    private EditText txtRubro;
    private EditText txtSubRubro;
    private String tituloMenu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Seteo titulo del menu
        if (savedInstanceState != null) {
            tituloMenu = savedInstanceState.getString(EXTRA_TITLE);
        } else {
            tituloMenu = ItemMenuNames.STRING_DETALLE_ARTICULO;
        }

        //txtCodigo
        ((NavigationMenu) getActivity()).setTitle(tituloMenu);
        View view = View.inflate(getActivity(), R.layout.lyt_articulo_detalles, null);
        this.txtCodigo = (EditText) view.findViewById(R.id.txtCodigo);
        this.txtCodigo.setKeyListener(null);

        // txtDescripcion
        this.txtDescripcion = (EditText) view.findViewById(R.id.txtDescripcionArticulo);
        this.txtDescripcion.setKeyListener(null);

        // txtStock
        this.txtStock = (EditText) view.findViewById(R.id.txtStock);
        this.txtStock.setKeyListener(null);

        // txtUnidadesXBulto
        this.txtUnidadesXBulto = (EditText) view.findViewById(R.id.txtUnidadesXBulto);
        this.txtUnidadesXBulto.setKeyListener(null);

        // txtNegocio
        this.txtNegocio = (EditText) view.findViewById(R.id.txtNegocioArticulo);
        this.txtNegocio.setKeyListener(null);

        // txtRubro
        this.txtRubro = (EditText) view.findViewById(R.id.txtRubroArticulo);
        this.txtRubro.setKeyListener(null);

        // txtSubRubro
        this.txtSubRubro = (EditText) view.findViewById(R.id.txtSubrubroArticulo);
        this.txtSubRubro.setKeyListener(null);

        Articulo articulo = (Articulo) getArguments().getSerializable(FrmGestionArticulo.EXTRA_ARTICULO);

        loadArticulo(articulo);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardar titulo del menu
        outState.putString(EXTRA_TITLE, tituloMenu);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // Restaurar datos después de que la vista se haya restaurado
        if (savedInstanceState != null) {
            tituloMenu = savedInstanceState.getString(EXTRA_TITLE);
        }
    }

    private void loadArticulo(Articulo art) {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        ArticuloBo articuloBo = new ArticuloBo(repoFactory);
        Articulo articulo;
        try {
            articulo = articuloBo.recoveryByIdWithClass(art.getId());
            txtCodigo.setText(articulo.getCodigoEmpresa());
            txtDescripcion.setText(articulo.getDescripcion());
            txtStock.setText(String.valueOf(articulo.getStock()));
            txtUnidadesXBulto.setText(String.valueOf(articulo.getUnidadPorBulto()));
            txtNegocio.setText(articulo.getSubrubro().getRubro().getNegocio().getDescripcion());
            txtRubro.setText(articulo.getSubrubro().getRubro().getDescripcion());
            txtSubRubro.setText(articulo.getSubrubro().getDescripcion());
        } catch (Exception e) {
            ErrorManager.manageException("FrmDetalleArticulo", "loadArticulo", e, getActivity());
        }
    }

}
