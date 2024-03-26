package com.ar.vgmsistemas.view.rendicion.egresos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.ComprasImpuestos;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.SelectableListItemAdapter;

import java.util.List;


public class LineaImpuestoAdapter extends SelectableListItemAdapter<ComprasImpuestos> {

    private final Context context;
    private Compra compra;

    public LineaImpuestoAdapter(Context context, int textViewResourceId, List<ComprasImpuestos> objects, Compra compra) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.compra = compra;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.context, R.layout.lyt_detalle_impuestos_item, null);
            holder = new ViewHolder();
            //TextView Impuesto
            holder.txtImpuesto = convertView.findViewById(R.id.idImpuesto);
            //TextView gravado
            holder.txtGravado = convertView.findViewById(R.id.idGravado);
            //TextView importe
            holder.txtImporte = convertView.findViewById(R.id.idImporte);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ComprasImpuestos comprasImpuestos = items.get(position);
        holder.txtImpuesto.setText(comprasImpuestos.getImpuesto().getDescripcion());
        holder.txtGravado.setText(Formatter.formatMoney(comprasImpuestos.getPrImpGravado()));
        holder.txtImporte.setText(Formatter.formatMoney(comprasImpuestos.getPrImpuesto()));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void sort() {

    }

    private static class ViewHolder {
        TextView txtImpuesto;
        TextView txtGravado;
        TextView txtImporte;
    }
}

