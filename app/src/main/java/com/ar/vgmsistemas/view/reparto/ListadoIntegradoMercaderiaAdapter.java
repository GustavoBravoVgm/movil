package com.ar.vgmsistemas.view.reparto;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.LineaIntegradoMercaderia;

import java.util.List;

public class ListadoIntegradoMercaderiaAdapter extends ArrayAdapter<LineaIntegradoMercaderia> {

    private static final int listViewResourceId = R.layout.lyt_integrado_mercaderia;
    private final List<LineaIntegradoMercaderia> _listadoIntegrado;

    public ListadoIntegradoMercaderiaAdapter(Context context, List<LineaIntegradoMercaderia> listadoIntegrado) throws Exception {
        super(context, listViewResourceId, listadoIntegrado);
        _listadoIntegrado = listadoIntegrado;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_integrado_mercaderia_item, null);
            holder = new ViewHolder();
            holder.articulo = (TextView) convertView.findViewById(R.id.lblArticuloValue);
            holder.cantidad = (TextView) convertView.findViewById(R.id.lblCantidadValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (_listadoIntegrado != null) {
            holder.articulo.setText(_listadoIntegrado.get(position).getArticulo().getDescripcion());
            holder.cantidad.setText(String.valueOf(_listadoIntegrado.get(position).getCantidad()));
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView articulo;
        TextView cantidad;
    }


}
