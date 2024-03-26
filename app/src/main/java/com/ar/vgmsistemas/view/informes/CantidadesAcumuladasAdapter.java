package com.ar.vgmsistemas.view.informes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.utils.ComparatorArticulo;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.TextViewUtils;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public final class CantidadesAcumuladasAdapter extends ListItemAdapter<Articulo> {

    List<Articulo> articulos;
    List<Articulo> allArticulos;
    private HashMap<String, Integer> positionHeaders;
    private ArticuloFilter filter;
    private int _campoFiltro;
    Articulo articuloSeleccionado;
    private String filteredString;

    public CantidadesAcumuladasAdapter(Context context, List<Articulo> objects) {
        super(context, R.layout.lyt_cantidades_acumuladas_por_cliente_item, objects);
        this.articulos = (objects != null) ? objects : new ArrayList<Articulo>();
        this.allArticulos = this.articulos;
        filteredString = "";
        positionHeaders = new HashMap<String, Integer>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_cantidades_acumuladas_por_cliente_item, null);
            holder = new ViewHolder();
            holder.codigo = (TextView) convertView.findViewById(R.id.lblCodigo);
            holder.descripcion = (TextView) convertView.findViewById(R.id.lblDescripcion);
            holder.cantidad = (TextView) convertView.findViewById(R.id.lblCantidadValue);
            holder.fechaUltimaVenta = (TextView) convertView.findViewById(R.id.tvFecha);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Articulo articulo = articulos.get(position);
        if (articulo != null) {
            if (_campoFiltro == Preferencia.ARTICULO_CODIGO) {
                TextViewUtils.highlightText(holder.codigo, articulo.getCodigoEmpresa(), filteredString);
                holder.descripcion.setText(articulo.getDescripcion());
            } else {
                holder.codigo.setText(articulo.getCodigoEmpresa());
                TextViewUtils.highlightText(holder.descripcion, articulo.getDescripcion(), filteredString);
            }
            holder.cantidad.setText(String.valueOf(articulo.getCantidadVendida()));
            String actualDate = Formatter.formatDate(articulo.getFechaUltimaVenta());
            if (!positionHeaders.containsKey(actualDate)) {
                positionHeaders.put(actualDate, position);
                holder.fechaUltimaVenta.setText(actualDate);
                holder.fechaUltimaVenta.setVisibility(View.VISIBLE);
            } else if (positionHeaders.containsKey(actualDate) && positionHeaders.get(actualDate) == position) {
                holder.fechaUltimaVenta.setText(actualDate);
                holder.fechaUltimaVenta.setVisibility(View.VISIBLE);
            } else {
                holder.fechaUltimaVenta.setVisibility(View.GONE);
            }

        }
        return convertView;
    }

    @Override
    public int getCount() {
        return articulos.size();
    }

    @Override
    public Articulo getItem(int position) {
        return articulos.get(position);
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ArticuloFilter();
        }
        return filter;
    }

    /**
     * @param campoFiltro the campoFiltro to set
     */
    public void setCampoFiltro(int campoFiltro) {
        this._campoFiltro = campoFiltro;
    }

    public int getCampoFiltro() {
        return _campoFiltro;
    }

    private static class ViewHolder {
        TextView codigo;
        TextView descripcion;
        TextView cantidad;
        TextView fechaUltimaVenta;
    }

    @Override
    public void sort() {
        Comparator<Articulo> comparator = new ComparatorArticulo(true);
        super.sort(comparator);
    }

    private class ArticuloFilter extends Filter {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            articulos = (List<Articulo>) results.values;
            positionHeaders.clear();
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            List<Articulo> aux = new ArrayList<Articulo>();
            filteredString = (prefix == null) ? "" : prefix.toString();
            if (prefix != null && prefix.toString().length() > 0) {

                for (int index = 0; index < allArticulos.size(); index++) {
                    Articulo articulo = allArticulos.get(index);
                    String filtro = filteredString.toLowerCase();
                    switch (_campoFiltro) {
                        case Preferencia.ARTICULO_DESCRIPCION:
                            String descripcion = articulo.getDescripcion();
                            descripcion = descripcion.toLowerCase();
                            if (descripcion.contains(filtro))
                                aux.add(articulo);
                            break;

                        case Preferencia.ARTICULO_CODIGO:
                            String codigo = articulo.getCodigoEmpresa();
                            if (codigo.contains(filtro)) {
                                aux.add(articulo);
                            }
                            break;
                    }

                }
                results.values = aux;
                results.count = aux.size();
                System.out.println("size adapter:" + aux.size());
            } else {
                synchronized (allArticulos) {
                    results.values = allArticulos;
                    results.count = allArticulos.size();
                }
            }

            return results;
        }
    }

}
