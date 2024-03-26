package com.ar.vgmsistemas.view.articulo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.PromocionDetalle;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.List;


public class PromocionDetalleAdapter extends ArrayAdapter<PromocionDetalle> {
    List<PromocionDetalle> detalle;
    private static int textViewResourceId = R.layout.lyt_promocion_detalle_item;

    public PromocionDetalleAdapter(Context context, List<PromocionDetalle> objects) {
        super(context, textViewResourceId, objects);
        this.detalle = objects;
    }

    @Override
    public PromocionDetalle getItem(int position) {
        return detalle.get(position);
    }

    public void setPromocionDetalle(List<PromocionDetalle> detalle) {
        this.detalle = detalle;
    }

    /**
     * Setea la lista de precio con la que se van a mostrar los precios normales de
     * los componentes de la promoción.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_promocion_detalle_item, null);
            holder = new ViewHolder();
            holder.txtDescripcion = (TextView) convertView.findViewById(R.id.lblDescripcion);
            holder.txtCantidad = (TextView) convertView.findViewById(R.id.lblCantidadValue);
            holder.txtPrecio = (TextView) convertView.findViewById(R.id.lblPrecioComboValue);
            holder.txtPrecioNormal = (TextView) convertView.findViewById(R.id.lblPrecioNormalValue);
            holder.txtPorcentajeAhorro = (TextView) convertView.findViewById(R.id.lblPorcentajeAhorroValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PromocionDetalle promocionDetalle = getItem(position);
        if (promocionDetalle != null) {
            double precioConIva = promocionDetalle.getPrecio() * (1 + promocionDetalle.getArticulo().getTasaIva());
            double porcentajeAhorro = (1 - precioConIva / promocionDetalle.getPrecioNormal());

            holder.txtDescripcion.setText(promocionDetalle.getArticulo().getDescripcion());
            holder.txtCantidad.setText(String.valueOf(promocionDetalle.getCantidadComboComun()));
            holder.txtPrecio.setText(Formatter.formatMoney(precioConIva));
            holder.txtPrecioNormal.setText(Formatter.formatMoney(promocionDetalle.getPrecioNormal()));
            holder.txtPorcentajeAhorro.setText(Formatter.formatPercent(porcentajeAhorro));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView txtDescripcion;
        TextView txtCantidad;
        TextView txtPrecio;
        TextView txtPrecioNormal;
        TextView txtPorcentajeAhorro;
    }

    @Override
    public int getCount() {
        return detalle.size();
    }
}
