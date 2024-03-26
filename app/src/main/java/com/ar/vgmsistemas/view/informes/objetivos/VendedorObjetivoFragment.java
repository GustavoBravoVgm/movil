package com.ar.vgmsistemas.view.informes.objetivos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.VendedorObjetivoBo;
import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment.SingleChoiceListener;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.List;

public class VendedorObjetivoFragment extends BaseNavigationFragment {
    private List<VendedorObjetivo> mList;
    private ListView mLvObjetivos;

    private int posEstado = VendedorObjetivo.TODOS;
    private int posCategoria = VendedorObjetivo.TODOS;
    private int posTipo = VendedorObjetivo.TODOS;
    private Menu mMenu;
    private VendedorObjetivoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        initViews();
        loadData();

        super.onResume();
    }

    private void initViews() {
        mLvObjetivos = (ListView) getActivity().findViewById(R.id.lvObjetivos);
        mLvObjetivos.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(VendedorObjetivoDetalleFragment.KEY_OBJETIVO, mAdapter.getItem(position));
                VendedorObjetivoDetalleFragment fragment = new VendedorObjetivoDetalleFragment();
                fragment.setArguments(bundle);
                if (getNavigationMenu() != null) {
                    getNavigationMenu().addFragment(fragment, "Listado de Objetivos");
                } else {
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.flObjetivosDetalle, fragment, "Listado de Objetivos");
                    transaction.addToBackStack("");
                    transaction.commit();

                }

            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ItemMenuNames.STRING_OBJETIVOS);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_vendedor_objetivos, null);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMenu = menu;
        mMenu.clear();
        inflater.inflate(R.menu.mn_vendedor_objetivos, menu);
        setMenuTitles();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setMenuTitles() {
        mMenu.findItem(R.id.miCategoria).setTitle(VendedorObjetivo.CATEGORIA + "(" + VendedorObjetivo.CATEGORIAS[posCategoria] + ")");
        mMenu.findItem(R.id.miEstado).setTitle(VendedorObjetivo.ESTADO + "(" + VendedorObjetivo.ESTADOS[posEstado] + ")");
        mMenu.findItem(R.id.miTipo).setTitle(VendedorObjetivo.TIPO + "(" + VendedorObjetivo.TIPOS[posTipo] + ")");
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCategoria:

                SingleChoiceDialogFragment choiceDialogFragment = SingleChoiceDialogFragment.newInstance(VendedorObjetivo.CATEGORIAS,
                        posCategoria, "Categoria", new SingleChoiceListener() {

                            @Override
                            public void onAcceptItem(int pos) {
                                posCategoria = pos;
                                item.setTitle(VendedorObjetivo.CATEGORIA + "(" + VendedorObjetivo.CATEGORIAS[posCategoria] + ")");
                                mAdapter.setFilterCategoria(pos);
                                mAdapter.getFilter().filter("");
                            }
                        });
                choiceDialogFragment.show(getChildFragmentManager(), "dialogCategoria");
                return true;
            case R.id.miEstado:

                SingleChoiceDialogFragment estadoDialogFragment = SingleChoiceDialogFragment.newInstance(VendedorObjetivo.ESTADOS, posEstado,
                        "Estado", new SingleChoiceListener() {

                            @Override
                            public void onAcceptItem(int pos) {
                                posEstado = pos;
                                item.setTitle(VendedorObjetivo.ESTADO + "(" + VendedorObjetivo.ESTADOS[posEstado] + ")");
                                mAdapter.setFilterEstado(pos);
                                mAdapter.getFilter().filter("");
                            }
                        });
                estadoDialogFragment.show(getChildFragmentManager(), "dialogEstado");
                return true;
            case R.id.miTipo:

                SingleChoiceDialogFragment tipoDialogFragment = SingleChoiceDialogFragment.newInstance(VendedorObjetivo.TIPOS, posTipo,
                        "Tipo", new SingleChoiceListener() {

                            @Override
                            public void onAcceptItem(int pos) {
                                posTipo = pos;
                                item.setTitle(VendedorObjetivo.TIPO + "(" + VendedorObjetivo.TIPOS[posTipo] + ")");
                                mAdapter.setFilterTipo(pos);
                                mAdapter.getFilter().filter("");
                            }
                        });
                tipoDialogFragment.show(getChildFragmentManager(), "dialogTipo");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        VendedorObjetivoBo bo = new VendedorObjetivoBo(repoFactory);

        try {
            mList = bo.recoveryAll();
            if (mAdapter == null) {
                mAdapter = new VendedorObjetivoAdapter(getActivity(), R.layout.lyt_objetivo_item, mList);
            }
            mLvObjetivos.setAdapter(mAdapter);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
