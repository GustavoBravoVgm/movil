package com.ar.vgmsistemas.view.informes.objetivos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.VendedorObjetivoDetalleBo;
import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.entity.VendedorObjetivoDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.List;

public class VendedorObjetivoDetalleFragment extends BaseNavigationFragment {
    public static final String KEY_OBJETIVO = "objetivo";
    private ObjetivoDetalleAdapter mAdapter;
    private VendedorObjetivo mVendedorObjetivo;

    private List<VendedorObjetivoDetalle> mList;
    private VendedorObjetivoDetalleBo mBo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mVendedorObjetivo = (VendedorObjetivo) getArguments().getSerializable(KEY_OBJETIVO);
        loadData();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_objetivo_detalle, null);
        ListView lvDetallesObjetivo = (ListView) view.findViewById(R.id.lvObjetivosDetalle);
        lvDetallesObjetivo.setAdapter(mAdapter);
        ((TextView) view.findViewById(R.id.tvProveedor)).setText(mVendedorObjetivo.getProveedor().getDeProveedor());
        ((TextView) view.findViewById(R.id.tvTipo)).setText(VendedorObjetivo.getTipo(mVendedorObjetivo.getTiObjetivo()));
        ((TextView) view.findViewById(R.id.tvCategoria)).setText(VendedorObjetivo.getCategoria(mVendedorObjetivo.getTiCategoria()));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Listado de Objetivos");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadData() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        mBo = new VendedorObjetivoDetalleBo(repoFactory);
        try {
            mList = mBo.recoveryByObjetivo(mVendedorObjetivo);
            mAdapter = new ObjetivoDetalleAdapter(getActivity(), R.layout.lyt_objetivos_detalle_item, mList, mVendedorObjetivo);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
