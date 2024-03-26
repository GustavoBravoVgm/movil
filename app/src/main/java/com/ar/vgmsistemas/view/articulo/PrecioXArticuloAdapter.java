package com.ar.vgmsistemas.view.articulo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.utils.ComparatorArticulo;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.Comparator;
import java.util.List;

public final class PrecioXArticuloAdapter extends ListItemAdapter<ListaPrecioDetalle> {

    List<ListaPrecioDetalle> detalles;
    List<ListaPrecioDetalle> allDetalles;
    private Articulo mCurrentArticulo;

    public PrecioXArticuloAdapter(Context context, int textViewResourceId, List<ListaPrecioDetalle> objects, Articulo currentArticulo) {
        super(context, textViewResourceId, objects);
        mCurrentArticulo = currentArticulo;
        this.detalles = objects;
        this.allDetalles = this.detalles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_precios_x_articulo_item, null);
            holder = new ViewHolder();
            holder.descripcion = (TextView) convertView.findViewById(R.id.lblDescripcion);
            holder.precioUnitario = (TextView) convertView.findViewById(R.id.lblPrecioUnitarioValue);
            holder.precioPorKg = (TextView) convertView.findViewById(R.id.lblPrecioPorKg);
            holder.precioPorKgValue = (TextView) convertView.findViewById(R.id.lblPrecioPorKgValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListaPrecioDetalle detalle = detalles.get(position);
        if (detalle != null) {
            holder.descripcion.setText(detalle.getListaPrecio().getDescripcion());
            holder.precioUnitario.setText(Formatter.formatMoney(detalle.getPrecioFinal()));

            //ver si el articulo es pesable o no para saber si mostrarlo
            if (mCurrentArticulo.getCantidadKilos() > 0) {
                holder.precioPorKg.setVisibility(View.VISIBLE);
                holder.precioPorKgValue.setVisibility(View.VISIBLE);
                double precioPorKg = detalle.getPrecioFinal() / mCurrentArticulo.getCantidadKilos();
                holder.precioPorKgValue.setText(Formatter.formatMoney(precioPorKg));
            } else {
                holder.precioPorKg.setVisibility(View.GONE);
                holder.precioPorKgValue.setVisibility(View.GONE);
            }

        }
        return convertView;
    }

    @Override
    public int getCount() {
        return detalles.size();
    }

    @Override
    public ListaPrecioDetalle getItem(int position) {
        return detalles.get(position);
    }

    @Override
    public void sort() {
        ComparatorPrecioXArticulo comparator = new ComparatorPrecioXArticulo();
        super.sort(comparator);
    }

    private static class ViewHolder {
        TextView descripcion;
        TextView precioUnitario;
        TextView precioPorKg;
        TextView precioPorKgValue;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    // TODO: Sacar en otra clase porque se usa en otro lugar tb
    class ComparatorPrecioXArticulo implements Comparator<ListaPrecioDetalle> {
        public int compare(ListaPrecioDetalle object1, ListaPrecioDetalle object2) {
            ComparatorArticulo comparatorArticulo = new ComparatorArticulo();
            return comparatorArticulo.compare(object1.getArticulo(), object2.getArticulo());
        }
    }

}
