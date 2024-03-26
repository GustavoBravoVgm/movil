package com.ar.vgmsistemas.view.cobranza.pagoEfectivo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.PagoEfectivo;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.List;

public class PagoEfectivoAdapter extends ListItemAdapter<PagoEfectivo> {

    public PagoEfectivoAdapter(Context context, List<PagoEfectivo> objects) {
        super(context, R.layout.lyt_pago_efectivo_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_pago_efectivo_item, null);
            holder = new ViewHolder();
            holder.txtMoneda = (TextView) convertView.findViewById(R.id.lblMonedaValue);
            holder.txtImporte = (TextView) convertView.findViewById(R.id.lblImporteValue);
            holder.txtCotizacion = (TextView) convertView.findViewById(R.id.lblCotizacionValue);
            holder.txtTotal = (TextView) convertView.findViewById(R.id.lblTotalValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PagoEfectivo pagoEfectivo = items.get(position);
        if (pagoEfectivo != null) {
            holder.txtMoneda.setText(pagoEfectivo.getTipoMoneda().getDescripcion());
            holder.txtImporte.setText(Formatter.formatMoney(pagoEfectivo.getImporteMoneda()));
            holder.txtCotizacion.setText(String.valueOf(pagoEfectivo.getTipoMoneda().getCotizacion()));
            holder.txtTotal.setText(Formatter.formatMoney(pagoEfectivo.getImporteMonedaCorriente()));
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtMoneda;
        TextView txtImporte;
        TextView txtCotizacion;
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
