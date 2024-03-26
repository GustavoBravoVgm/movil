package com.ar.vgmsistemas.view.informes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment.OkListener;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment.SingleChoiceListener;
import com.ar.vgmsistemas.view.informes.objetivos.VendedorObjetivoFragment;

import java.util.List;

public class FrmVentasXArticulo extends BaseActivity {
    public static final String KEY_CLIENTES = "Clientes";
    public static final String KEY_VENTA = "Venta";

    private Cliente _cliente;
    private ArticuloBo articuloBo;
    private SearchView mSearchView;
    protected ListView lstArticulos;

    private CantidadesAcumuladasAdapter mAdapter;

    /*private static final int DIALOG_ORDENAMIENTO = 0;
    private static final int DIALOG_BUSQUEDA = 1;
    private static final int DIALOG_VER_VENTAS = 2;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        _cliente = (Cliente) getIntent().getExtras().getSerializable(KEY_CLIENTES);
        setContentView(R.layout.lyt_articulo_gestion);

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        initComponents();
        loadData();
    }

    private void initComponents() {
        lstArticulos = (ListView) findViewById(R.id.lstArticulos);
        setTitle("Ventas por articulo");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mn_gestion_articulo, menu);
        MenuItem item = menu.findItem(R.id.mni_search_articulo);

        mSearchView = (SearchView) MenuItemCompat.getActionView(item);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onPrepareOptionsMenu(menu);
        int busquedaPreferida = PreferenciaBo.getInstance().getPreferencia(this).getBusquedaPreferidaArticulo();
        setHintSearchView(busquedaPreferida);
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String arg0) {
                mAdapter.getFilter().filter(arg0);
                return true;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                setActionBarTitle("Ventas por articulo");
            } else {
                setActionBarTitle("Objetivos");
            }
            getSupportFragmentManager().popBackStack();

        } else {

            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_configurar_busqueda_art:
                showDialogOpcionesBusqueda();
                break;
            case R.id.mni_ordenar_art:
                showDialogOpcionesOrdenamiento();
                break;
            case R.id.mni_objetivos_venta:
                showObjetivosVenta();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showObjetivosVenta() {

        VendedorObjetivoFragment objetivoVenta = new VendedorObjetivoFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.flObjetivos, objetivoVenta);
        transaction.addToBackStack("");
        transaction.commit();
    }

    private void showDialogOpcionesOrdenamiento() {

        String codigo = getString(R.string.lblCodigo);
        String descripcion = getString(R.string.lblDescripcion);
        String cantidad = getString(R.string.lblCantidad);

        String[] items = {codigo, descripcion, cantidad};

        int posSelected = PreferenciaBo.getInstance().getPreferencia(this).getOrdenPreferidoArticulo();

        String title = getString(R.string.tituloOrdenarArticulos);

        SingleChoiceDialogFragment choiceDialogFragment = SingleChoiceDialogFragment.newInstance(items, posSelected, title,
                new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        PreferenciaBo.getInstance().getPreferencia(FrmVentasXArticulo.this).setOrdenPreferidoArticulo(pos);
                        ordenarArticulos();

                    }
                });
        choiceDialogFragment.show(getSupportFragmentManager(), "dialog");

    }

    protected void ordenarArticulos() {
        mAdapter.sort();
        mAdapter.notifyDataSetChanged();
    }

    private void showDialogOpcionesBusqueda() {
        String codigo = getString(R.string.lblCodigo);
        String descripcion = getString(R.string.lblDescripcion);
        final String[] opciones = {codigo, descripcion};
        int busquedaPreferida = PreferenciaBo.getInstance().getPreferencia(this).getBusquedaPreferidaArticulo();

        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(opciones, busquedaPreferida,
                "Seleccione criterio de busqueda", new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        setHintSearchView(pos);
                        PreferenciaBo.getInstance().getPreferencia(FrmVentasXArticulo.this).setBusquedaPreferidaArticulo(pos);
                        mAdapter.setCampoFiltro(pos);
                    }
                });
        dialogFragment.show(getSupportFragmentManager(), "nose");
    }

    public Object getLastNonConfigurationInstance() {
        // TODO Auto-generated method stub
        return _cliente;
    }


    public void loadData() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        articuloBo = new ArticuloBo(repoFactory);
        List<Articulo> articulos = null;
        try {

            //articulos = articuloBo.getCantidadAcumuladaPorCliente(_cliente);
            articulos = articuloBo.getCantidadPorCliente(_cliente);

            if (articulos.size() == 0) {
                String msj = getString(R.string.msjNoVentaCliente) + " " + _cliente.getRazonSocial();
                SimpleDialogFragment dialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, msj, "Ventas acumuladas", new OkListener() {

                    @Override
                    public void onOkSelected() {
                        onBackPressed();

                    }

                });
                dialogFragment.show(getSupportFragmentManager(), "nose");
            }


            this.mAdapter = new CantidadesAcumuladasAdapter(this, articulos);
            this.lstArticulos.setAdapter(this.mAdapter);
            int posPreferida = PreferenciaBo.getInstance().getPreferencia(this).getBusquedaPreferidaArticulo();
            mAdapter.setCampoFiltro(posPreferida);
            //this.actualizarLeyendaBusqueda();
        } catch (Exception e) {
            ErrorManager.manageException("FrmGestionArticulo", "loadData", e, this);
        }
    }

    public void lstArticulosOnItemClick(AdapterView<?> parent, View view, int position, long id) {

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
