package com.ar.vgmsistemas.view.rendicion.entregas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.ValorHardcodeado;

import java.util.List;


public class EntregaAdapter extends ArrayAdapter<ValorHardcodeado> {
    List<ValorHardcodeado> objects;
    Context context;

    public EntregaAdapter(Context context, int textViewResourceId, List<ValorHardcodeado> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.context, R.layout.lyt_entrega_item, null);
            holder = new ViewHolder();
            holder.txtCantidad = convertView.findViewById(R.id.idCantidad);
            holder.txtValor = convertView.findViewById(R.id.idValor);
            holder.txtImporte = convertView.findViewById(R.id.idImporte);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ValorHardcodeado valor = objects.get(position);

        if (valor.getValor() > 0) {
            holder.txtCantidad.setVisibility(View.VISIBLE);
        } else {
            holder.txtCantidad.setVisibility(View.INVISIBLE);
        }
        String cantidadStr = valor.getCantidad() + "";
        holder.txtCantidad.setText(cantidadStr);
        holder.txtValor.setText(valor.getDescripcion());
        String importeStr = valor.getImporte() + "";
        holder.txtImporte.setText(importeStr);
        return convertView;
    }

    private static class ViewHolder {
        TextView txtCantidad;
        TextView txtValor;
        TextView txtImporte;
    }
}
