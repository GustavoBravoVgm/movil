package com.ar.vgmsistemas.view.articulo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.ListItemAdapter;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment.MultipleChoiceListener;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment.SingleChoiceListener;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.ar.vgmsistemas.view.menu.MenuManager;

import java.util.List;

public class FrmGestionArticulo extends BaseNavigationFragment {

    public static final String EXTRA_ARTICULO = "articulo";
    public static final int POS_VER_DETALLE = 0;
    public static final int POS_VER_PRECIOS = 1;

    protected ListItemAdapter<Articulo> mAdapter;
    private Articulo itemSeleccionado;
    private SearchView mSearchView;
    protected ArticuloBo articuloBo;
    protected ListView lstArticulos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.lyt_articulo_gestion, container, false);
        initComponents(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initVars();
    }

    private void initComponents(View view) {
        // lstArticulos
        this.lstArticulos = (ListView) view.findViewById(R.id.lstArticulos);
        this.lstArticulos.setOnItemClickListener((parent, view1, position, id) -> lstArticulosOnItemClick(parent, view1, position, id));
    }

    private void initVars() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        articuloBo = new ArticuloBo(repoFactory);
        List<Articulo> articulos = null;
        //this.mAdapter.setCampoFiltro(PreferenciaBo.getInstance().getPreferencia(getActivity()).getBusquedaPreferidaArticulo());
        try {
            articulos = articuloBo.recoveryAllWithClass();

            this.mAdapter = new ArticuloAdapter(getActivity(), R.layout.lyt_articulo_lista_item, articulos);
            this.mAdapter.setCampoFiltro(PreferenciaBo.getInstance().getPreferencia(getActivity()).getBusquedaPreferidaArticulo());
            this.lstArticulos.setAdapter(this.mAdapter);
            this.ordenarArticulos();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), ErrorManager.ErrorAccederALosDatos, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        int posBusqueda = PreferenciaBo.getInstance().getPreferencia(getActivity()).getBusquedaPreferidaArticulo();
        setHintSearchView(posBusqueda);
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String arg0) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                mAdapter.getFilter().filter(s);
                return false;
            }
        });

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.mn_gestion_articulo, menu);
        MenuItem item = menu.findItem(R.id.mni_search_articulo);

        item.setVisible(true);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.mni_configurar_busqueda_art) {
            showDialogOpcionesBusqueda();
        } else if (itemId == R.id.mni_ordenar_art) {
            showDialogOpcionesOrdenamiento();
        }
        return super.onOptionsItemSelected(item);
    }

    //
    protected void showDialogOpcionesBusqueda() {
        String codigo = getNavigationMenu().getString(R.string.lblCodigo);
        String descripcion = getNavigationMenu().getString(R.string.lblDescripcion);
        final String[] opciones = {codigo, descripcion};
        int busquedaPreferida = PreferenciaBo.getInstance().getPreferencia(getNavigationMenu()).getBusquedaPreferidaArticulo();

        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(opciones, busquedaPreferida,
                "Seleccione criterio de busqueda", new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        setHintSearchView(pos);
                        PreferenciaBo.getInstance().getPreferencia(getActivity()).setBusquedaPreferidaArticulo(pos);
                        mAdapter.setCampoFiltro(pos);
                        mAdapter.notifyDataSetChanged();
                    }
                });
        dialogFragment.show(getChildFragmentManager(), "nose");
    }

    protected void showDialogOpcionesOrdenamiento() {

        String codigo = getActivity().getString(R.string.lblCodigo);
        String descripcion = getActivity().getString(R.string.lblDescripcion);
        String[] items = {codigo, descripcion};

        int posSelected = PreferenciaBo.getInstance().getPreferencia(getActivity()).getOrdenPreferidoArticulo();

        String title = getActivity().getString(R.string.tituloOrdenarArticulos);

        final SingleChoiceDialogFragment choiceDialogFragment = SingleChoiceDialogFragment.newInstance(items, posSelected, title,
                new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        PreferenciaBo.getInstance().getPreferencia(getActivity()).setOrdenPreferidoArticulo(pos);
                        ordenarArticulos();

                    }
                });
        choiceDialogFragment.show(getChildFragmentManager(), "dialog");

    }

    protected void ordenarArticulos() {
        mAdapter.sort();
        mAdapter.notifyDataSetChanged();
        mAdapter.getFilter().filter("");
    }

    public void lstArticulosOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        itemSeleccionado = (Articulo) this.lstArticulos.getItemAtPosition(position);
        showDialogOpcionesArticulo();
    }

    private void showDialogOpcionesArticulo() {

        String verDetalle = this.getString(R.string.mnVerDetalles);
        String verPrecios = this.getString(R.string.mnVerPrecios);

        String[] itemsMenu = {verDetalle, verPrecios};
        OptionsDialogFragment choiceDialogFragment = OptionsDialogFragment.newInstance(itemsMenu, new MultipleChoiceListener() {

            @Override
            public void onItemSelected(int pos) {
                mSearchView.setIconified(true);
                mSearchView.setIconified(true);
                mAdapter.getFilter().filter("");
                switch (pos) {
                    case POS_VER_DETALLE:
                        Bundle argumentsDetalle = new Bundle();
                        argumentsDetalle.putSerializable(EXTRA_ARTICULO, itemSeleccionado);

                        Fragment fragmentDetalleArticulo = MenuManager.getFragment(ItemMenuNames.POS_DETALLE_ARTICULO);
                        fragmentDetalleArticulo.setArguments(argumentsDetalle);

                        getNavigationMenu().addFragment(fragmentDetalleArticulo, ItemMenuNames.STRING_DETALLE_ARTICULO);
                        break;
                    case POS_VER_PRECIOS:
                        Bundle argumentsVerPrecio = new Bundle();
                        argumentsVerPrecio.putSerializable(EXTRA_ARTICULO, itemSeleccionado);

                        Fragment fragmentVerPrecio = MenuManager.getFragment(ItemMenuNames.POS_PRECIO_ARTICULO);
                        fragmentVerPrecio.setArguments(argumentsVerPrecio);
                        getNavigationMenu().addFragment(fragmentVerPrecio, ItemMenuNames.STRING_PRECIO_ARTICULO);
                        break;
                }

            }
        });
        choiceDialogFragment.show(getChildFragmentManager(), OptionsDialogFragment.TAG);
    }

    private void setHintSearchView(int pos) {
        switch (pos) {
            case Preferencia.ARTICULO_CODIGO:
                mSearchView.setQueryHint("por codigo");
                break;
            case Preferencia.ARTICULO_DESCRIPCION:
                mSearchView.setQueryHint("por descripcion");
                break;
        }
    }


}
