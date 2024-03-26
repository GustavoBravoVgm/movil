package com.ar.vgmsistemas.view.cobranza.retencion;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.List;

public class RetencionAdapter extends ListItemAdapter<Retencion> {

    public RetencionAdapter(Context context, List<Retencion> objects) {
        super(context, R.layout.lyt_retencion_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_retencion_item, null);
            holder = new ViewHolder();
            holder.txtDocumento = (TextView) convertView.findViewById(R.id.lblDocumentoValue);
            holder.txtPuntoVenta = (TextView) convertView.findViewById(R.id.lblPuntoVentaValue);
            holder.txtNumero = (TextView) convertView.findViewById(R.id.lblNumeroValue);
            holder.txtFecha = (TextView) convertView.findViewById(R.id.lblFechaValue);
            holder.txtTotal = (TextView) convertView.findViewById(R.id.lblTotalValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Retencion retencion = items.get(position);
        if (retencion != null) {
            holder.txtDocumento.setText(retencion.getId().getIdDocumento());
            holder.txtPuntoVenta.setText(String.valueOf(retencion.getId().getPuntoVenta()));
            holder.txtNumero.setText(String.valueOf(retencion.getId().getIdNumeroDocumento()));
            holder.txtFecha.setText(Formatter.formatDate(retencion.getFechaMovil()));
            holder.txtTotal.setText(Formatter.formatMoney(retencion.getImporte()));
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtDocumento;
        TextView txtPuntoVenta;
        TextView txtNumero;
        TextView txtFecha;
        TextView txtTotal;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void sort() {

    }

}
