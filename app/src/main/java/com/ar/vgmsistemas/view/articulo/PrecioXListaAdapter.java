package com.ar.vgmsistemas.view.articulo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ComparatorArticulo;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.TextViewUtils;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class PrecioXListaAdapter extends ListItemAdapter<ListaPrecioDetalle> {
    //Con esta variable indico el valor recibido para filtrar o no por subrubro
    private static final long NO_FILTRO_POR_SUBRUBRO = -1;

    List<ListaPrecioDetalle> detalles;
    List<ListaPrecioDetalle> allDetalles;

    private int _campoFiltro;
    long _idSubrubro;
    private ArticuloFilter filter;
    private String filterString;
    private Venta venta;
    private final VentaBo ventaBo;

    public PrecioXListaAdapter(Context context, int textViewResourceId, List<ListaPrecioDetalle> listasPrecioDetalleRecibidas, long idSubrubro, Venta venta) {
        super(context, textViewResourceId, listasPrecioDetalleRecibidas);
        _idSubrubro = idSubrubro;
        this.detalles = listasPrecioDetalleRecibidas;
        this.allDetalles = this.detalles;
        _campoFiltro = PreferenciaBo.getInstance().getPreferencia(super.getContext()).getBusquedaPreferidaArticulo();
        filterString = "";
        this.venta = venta;
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        this.ventaBo = new VentaBo(repoFactory);
    }

    /*
     * Este metodo se llama por cada ListaPrecioDetalle
     * (non-Javadoc)
     * @see com.ar.vgmsistemas.view.ListItemAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_precios_x_lista_item, null);
            holder = new ViewHolder();
            holder.codigo = (TextView) convertView.findViewById(R.id.lblCodigo);
            holder.descripcion = (TextView) convertView.findViewById(R.id.lblDescripcion);
            holder.precioUnitario = (TextView) convertView.findViewById(R.id.lblPrecioUnitarioValue);
            holder.linearRentProveedor = (LinearLayout) convertView.findViewById(R.id.idLinearRentProv);
            holder.linearRentEmpresa = (LinearLayout) convertView.findViewById(R.id.idLinearRentEmpresa);
            holder.nivelRentabilidadEmpresa = (TextView) convertView.findViewById(R.id.idNivelRentabilidadEmpresa);
            holder.nivelRentabilidadProveedor = (TextView) convertView.findViewById(R.id.idNivelRentabilidadProveedor);
            holder.stockPorLista = (TextView) convertView.findViewById(R.id.idStockList);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // #12793

        //EVALUAR LOS ARTICULOS EN EL CASO DE FILTRAR POR SUBRUBRO
        ListaPrecioDetalle detalle = detalles.get(position);
        VentaDetalle ventaDetalle = new VentaDetalle();

        ventaDetalle.setArticulo(detalle.getArticulo());
        if (ventaDetalle.getArticulo().getListaPreciosDetalle().size() == 0)
            ventaDetalle.getArticulo().getListaPreciosDetalle().add(detalle);
        ventaDetalle.setListaPrecio(detalle.getListaPrecio());

        if (detalle.getNivelRentabilidadProveedor() == null)
            ventaBo.isProductoRentable(ventaDetalle, venta, detalle);
        if (ventaBo.documentoControlaRentabilidadPorSucursal(venta)) {
            holder.linearRentEmpresa.setVisibility(View.VISIBLE);
            holder.nivelRentabilidadEmpresa.setText(detalle.getNivelRentabilidadEmpresa());
            if (detalle.isRentableXEmpresa())
                holder.nivelRentabilidadEmpresa.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            else
                holder.nivelRentabilidadEmpresa.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
        } else {
            holder.linearRentEmpresa.setVisibility(View.GONE);
        }
        if (ventaBo.documentoControlaRentabilidadPorProveedor(venta)) {
            holder.nivelRentabilidadProveedor.setText(detalle.getNivelRentabilidadProveedor());
            holder.linearRentProveedor.setVisibility(View.VISIBLE);
            if (detalle.isRentableXProveedor())
                holder.nivelRentabilidadProveedor.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            else
                holder.nivelRentabilidadProveedor.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
        } else {
            holder.linearRentProveedor.setVisibility(View.GONE);
        }
        switch (_campoFiltro) {
            case Preferencia.ARTICULO_CODIGO:
                TextViewUtils.highlightText(holder.codigo, detalle.getArticulo().getCodigoEmpresa(), filterString);
                holder.descripcion.setText(detalle.getArticulo().getDescripcion());
                break;
            case Preferencia.ARTICULO_DESCRIPCION:
                TextViewUtils.highlightText(holder.descripcion, detalle.getArticulo().getDescripcion(), filterString);
                holder.codigo.setText(detalle.getArticulo().getCodigoEmpresa());
                break;
        }

        holder.precioUnitario.setText(Formatter.formatMoney(detalle.getPrecioFinal()));
        holder.stockPorLista.setText(String.valueOf(detalle.getArticulo().getStock()));

        return convertView;
    }

    @Override
    public int getCount() {
        if (detalles == null) {
            return 0;
        } else {
            return detalles.size();
        }
    }

    @Override
    public ListaPrecioDetalle getItem(int position) {
        return detalles.get(position);
    }

    private static class ViewHolder {
        TextView codigo;
        TextView descripcion;
        TextView precioUnitario;
        TextView nivelRentabilidadProveedor;
        TextView nivelRentabilidadEmpresa;
        TextView stockPorLista;
        LinearLayout linearRentProveedor;
        LinearLayout linearRentEmpresa;
    }

    public void setCampoFiltro(int campoFiltro) {
        this._campoFiltro = campoFiltro;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ArticuloFilter();
        }
        return filter;
    }

    private class ArticuloFilter extends Filter {
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            detalles = (List<ListaPrecioDetalle>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            List<ListaPrecioDetalle> aux = new ArrayList<>();
            filterString = (prefix == null) ? "" : prefix.toString();
            if (prefix != null && prefix.toString().length() > 0) {
                for (int index = 0; index < allDetalles.size(); index++) {
                    ListaPrecioDetalle detalle = allDetalles.get(index);
                    String filtro = prefix.toString();
                    filtro = filtro.toLowerCase();
                    switch (_campoFiltro) {
                        case Preferencia.ARTICULO_DESCRIPCION:
                            String descripcion = detalle.getArticulo().getDescripcion();
                            descripcion = descripcion.toLowerCase();
                            if (descripcion.contains(filtro))
                                aux.add(detalle);
                            break;

                        case Preferencia.ARTICULO_CODIGO:
                            String codigo = detalle.getArticulo().getCodigoEmpresa();
                            if (codigo.contains(filtro)) {
                                aux.add(detalle);
                            }
                            break;
                    }

                }
                results.values = aux;
                results.count = aux.size();
            } else {
                synchronized (allDetalles) {
                    results.values = allDetalles;
                    results.count = allDetalles.size();
                }
            }
            return results;
        }
    }


    @Override
    public void sort() {
        ComparatorPrecioXArticulo comparator = new ComparatorPrecioXArticulo();
        super.sort(comparator);
    }

    //TODO: Sacar en otra clase porque se usa en otro lugar tb
    class ComparatorPrecioXArticulo implements Comparator<ListaPrecioDetalle> {
        public int compare(ListaPrecioDetalle object1, ListaPrecioDetalle object2) {
            ComparatorArticulo comparatorArticulo = new ComparatorArticulo();
            return comparatorArticulo.compare(object1.getArticulo(), object2.getArticulo());
        }
    }

    public List<ListaPrecioDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ListaPrecioDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<ListaPrecioDetalle> getAllDetalles() {
        return allDetalles;
    }

    public void setAllDetalles(List<ListaPrecioDetalle> allDetalles) {
        this.allDetalles = allDetalles;
    }

}
