package com.ar.vgmsistemas.view.reparto.hojas.listadoHojas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaBo;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.ArrayList;
import java.util.List;

public class ListadoHojasFragment extends BaseNavigationFragment implements android.view.ActionMode.Callback {
    private int currentPosition;
    private static final String[] optionsSoloVerDetalles = {"Ver detalles"};
    private SearchView mSearchView;
    private HojaBo mHojaBo;
    private List<Hoja> mHojas;
    private List<Hoja> mHojasSelected;
    private Hoja mHoja;
    protected ListView mListView;
    private ListadoHojasAdapter mAdapter;
    private android.view.ActionMode mActionMode;
    private MenuItem itemSelectAll;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_listado_hojas, null);
        initViews(view);
        loadData();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViews(View view) {
        mListView = (ListView) view.findViewById(R.id.listadoHojas);

    }

    private void loadData() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        mHojaBo = new HojaBo(repoFactory);
        try {
            mHojas = mHojaBo.recoveryAllEntities();
            this.mAdapter = new ListadoHojasAdapter(getActivity(), R.layout.lyt_hoja_reporte, mHojas);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.mListView.setAdapter(this.mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
                onLisItemCheck(position);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long l) {
                onLisItemCheckLong(position);
                return true;
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.mn_listado_hojas, menu);
        MenuItem item = menu.findItem(R.id.miSearch);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
    }

    private void setHintSearchView(int pos) {
        switch (pos) {
            case ListadoHojasAdapter.CRITERIA_ID_HOJA:
                mSearchView.setQueryHint("Por numero de hoja");
                break;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mSearchView != null) {
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String arg0) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String arg0) {
                    mAdapter.setCampoBusqueda(0);
                    mAdapter.getFilter().filter(arg0);
                    return false;
                }
            });
            setHintSearchView(ListadoHojasAdapter.NUMERO_HOJA);
        }
    }

    private void onLisItemCheck(int position) {
        mAdapter.toggleSelection(position);
        if (mAdapter.getSelectedCount() > 0 && mActionMode == null) {
            //List<Hoja> hojas = mAdapter.getSelected();
            List<Hoja> hojas = new ArrayList<Hoja>();
            hojas.add(mAdapter.getItem(position));
            DetallesHojaFragment detallesHojaFragment = new DetallesHojaFragment();
            detallesHojaFragment.setArguments(hojas);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.dl_navigation, detallesHojaFragment)
                    .addToBackStack(null)
                    .commit();
            mAdapter.deselectView(position);
        } else if (!(mAdapter.getSelectedCount() > 0) && mActionMode != null) {
            mActionMode.finish();

        }
        if (mActionMode != null) {
            String msj = (mAdapter.getSelectedCount() > 1) ? " seleccionadas" : " seleccionada";
            mActionMode.setTitle(String.valueOf(mAdapter.getSelectedCount()) + msj);
            Drawable icon = (mAdapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected) : getResources()
                    .getDrawable(R.drawable.ic_select_all_unselected);
            itemSelectAll.setIcon(icon);
        }


    }

    private void onLisItemCheckLong(int position) {
        mAdapter.toggleSelection(position);
        if (mAdapter.getSelectedCount() > 0 && mActionMode == null) {
            mActionMode = getActivity().startActionMode(ListadoHojasFragment.this);
        } else if (!(mAdapter.getSelectedCount() > 0) && mActionMode != null) {
            mActionMode.finish();

        }
        if (mActionMode != null) {
            String msj = (mAdapter.getSelectedCount() > 1) ? " seleccionadas" : " seleccionada";
            mActionMode.setTitle(String.valueOf(mAdapter.getSelectedCount()) + msj);
            Drawable icon = (mAdapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected) : getResources()
                    .getDrawable(R.drawable.ic_select_all_unselected);
            itemSelectAll.setIcon(icon);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miVerTotales:
                verTotales();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void verTotales() {
        if (mActionMode == null) {
            mActionMode = getActivity().startActionMode(ListadoHojasFragment.this);
        } else if (!(mAdapter.getSelectedCount() > 0) && mActionMode != null) {
            mActionMode.finish();

        }
        if (mActionMode != null) {
            String msj = (mAdapter.getSelectedCount() > 1) ? " seleccionadas" : " seleccionada";
            mActionMode.setTitle(String.valueOf(mAdapter.getSelectedCount()) + msj);
            Drawable icon = (mAdapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected) : getResources()
                    .getDrawable(R.drawable.ic_select_all_unselected);
            itemSelectAll.setIcon(icon);
        }
    }

    @Override
    public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {
        actionMode.getMenuInflater().inflate(R.menu.menu_select_items_hoja, menu);
        itemSelectAll = menu.findItem(R.id.mni_select_all);
        itemSelectAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mAdapter.getSelectedCount() < mAdapter.getCount()) {
                    mAdapter.selectAllViews();
                    Drawable icon = (mAdapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected)
                            : getResources().getDrawable(R.drawable.ic_select_all_unselected);
                    itemSelectAll.setIcon(icon);
                    mActionMode.setTitle(String.valueOf(mAdapter.getSelectedCount()) + " seleccionadas");
                } else if (mAdapter.getSelectedCount() == mAdapter.getCount()) {
                    mAdapter.removeSelection();
                    mActionMode.finish();
                }
                return true;
            }
        });
        final MenuItem itemPagar = menu.findItem(R.id.mni_imprimir_resumen);
        itemPagar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mHojasSelected = mAdapter.getSelected();
                //DESABILITO EL BOTON DE IMPRIMIR RESUMEN HOJAS HASTA QUE TERMINE DE EJECUTARSE LA OPERACION
                item.setEnabled(false);
                item.getIcon().setAlpha(255);
                imprimirResumen();
                mActionMode.finish();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem arg1) {
        SparseBooleanArray selected = mAdapter.getSelectedItems();
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < selected.size(); i++) {
            if (selected.valueAt(i)) {
                Hoja selectedItem = mAdapter.getItem(selected.keyAt(i));
                message.append(selectedItem + "\n");
            }
        }
        //Toast.makeText(mContext, message.toString(), Toast.LENGTH_LONG).show();

        // close action mode
        actionMode.finish();
        return false;
    }

    @Override
    public void onDestroyActionMode(android.view.ActionMode actionMode) {
        mAdapter.removeSelection();
        mActionMode = null;
    }

    private void imprimirResumen() {
        if (mHojasSelected.size() == 0) {
            //getActivity().showDialog();
        } else {
            imprimirResumenDialogFragment dialogFragment = new imprimirResumenDialogFragment();
            dialogFragment.setHojas(mHojasSelected);
            dialogFragment.show(getFragmentManager(), "dialog");
        }
    }

}
