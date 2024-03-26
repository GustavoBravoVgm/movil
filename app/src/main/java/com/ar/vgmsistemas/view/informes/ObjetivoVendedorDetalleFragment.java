package com.ar.vgmsistemas.view.informes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.VendedorObjetivoDetalleBo;
import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.entity.VendedorObjetivoDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.List;

public class ObjetivoVendedorDetalleFragment extends BaseNavigationFragment {
    public static final String KEY_OBJETIVO = "objetivo";

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
        ObjetivosDetalleAdapter adapter = new ObjetivosDetalleAdapter(getActivity(), R.layout.lyt_objetivos_detalle_item, mList, mVendedorObjetivo.getTiObjetivo());
        lvDetallesObjetivo.setAdapter(adapter);

        return view;
    }

    private void loadData() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        mBo = new VendedorObjetivoDetalleBo(repoFactory);
        try {
            mList = mBo.recoveryByObjetivo(mVendedorObjetivo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
