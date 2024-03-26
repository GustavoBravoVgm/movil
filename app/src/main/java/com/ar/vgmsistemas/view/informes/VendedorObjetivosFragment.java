package com.ar.vgmsistemas.view.informes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.VendedorObjetivoBo;
import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.List;

public class VendedorObjetivosFragment extends BaseNavigationFragment {
    private List<VendedorObjetivo> mList;
    private ListView mLvObjetivos;
    private VendedorObjetivoBo mVendedorObjetivoBo;
    private VendedorObjetivosAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_vendedor_objetivos, null);
        mLvObjetivos = (ListView) view.findViewById(R.id.lvObjetivos);
        mAdapter = new VendedorObjetivosAdapter(getActivity(), R.layout.lyt_objetivo_item, mList);
        mLvObjetivos.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ObjetivoVendedorDetalleFragment.KEY_OBJETIVO, mAdapter.getItem(position));
                ObjetivoVendedorDetalleFragment fragment = new ObjetivoVendedorDetalleFragment();
                fragment.setArguments(bundle);
                getNavigationMenu().addFragment(fragment, "Listado de Objetivo");

            }
        });
        mLvObjetivos.setAdapter(mAdapter);
        return view;
    }

    private void loadData() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        VendedorObjetivoBo bo = new VendedorObjetivoBo(repoFactory);

        try {
            mList = bo.recoveryAll();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
