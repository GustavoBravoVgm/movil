package com.ar.vgmsistemas.view.reparto;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.LineaIntegradoMercaderiaBo;
import com.ar.vgmsistemas.entity.LineaIntegradoMercaderia;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.List;

public class FrmIntegradoMercaderia extends BaseNavigationFragment {

    private List<LineaIntegradoMercaderia> _listadoIntegrado;
    private LineaIntegradoMercaderiaBo _lineaIntegrado;
    protected ListView listadoIntegrado;
    private ListadoIntegradoMercaderiaAdapter _adapterPs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.lyt_integrado_mercaderia, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        loadData();
        initVar();
    }

    private void initComponents() {
        this.listadoIntegrado = (ListView) getView().findViewById(R.id.listadoMercaderia);

        TextView txtArticulo = (TextView) getView().findViewById(R.id.txtArticulo);
        TextView txtCant = (TextView) getView().findViewById(R.id.txtCant);

        SpannableString content = new SpannableString(getResources().getString(R.string.lblArticulo));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txtArticulo.setText(content);

        content = new SpannableString(getResources().getString(R.string.lblCantidadVentaDetalle));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txtCant.setText(content);
    }

    private void loadData() {
        RepositoryFactory repo = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        _lineaIntegrado = new LineaIntegradoMercaderiaBo(repo);

        try {
            this._listadoIntegrado = _lineaIntegrado.getLineas();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void initVar() {
        try {
            this._adapterPs = new ListadoIntegradoMercaderiaAdapter(getActivity(), this._listadoIntegrado);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.listadoIntegrado.setAdapter(this._adapterPs);
    }
}
