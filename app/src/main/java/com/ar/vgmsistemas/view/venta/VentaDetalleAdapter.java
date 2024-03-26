package com.ar.vgmsistemas.view.venta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.bo.VentaDetalleBo;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.TextViewUtils;
import com.ar.vgmsistemas.view.SelectableListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class VentaDetalleAdapter extends SelectableListItemAdapter<VentaDetalle> {
    private String filter;
    public static final int DESCRIPCION_ARTICULO = 0;
    public static final int CODIG0 = 1;
    private VentaDetalleFilter mFilter;
    private Venta _venta;
    private boolean controlaRentPorProv;
    private boolean controlaRentPorSuc;
    VentaBo ventaBo;
    RepositoryFactory _repoFactory;

    public VentaDetalleAdapter(Context context, int textViewResourceId, List<VentaDetalle> objects, Venta venta) {

        super(context, textViewResourceId, objects);
        filter = "";
        this._venta = venta;
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        this.ventaBo = new VentaBo(_repoFactory);
        controlaRentPorProv = ventaBo.documentoControlaRentabilidadPorProveedor(_venta);
        controlaRentPorSuc = ventaBo.documentoControlaRentabilidadPorSucursal(_venta);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_venta_detalle_item, null);
            holder = new ViewHolder();

            holder.txtDescripcion = (TextView) convertView.findViewById(R.id.txtDescripcion);
            holder.txtCodigoEmpresa = (TextView) convertView.findViewById(R.id.lblCodigoEmpresaValue);
            holder.txtBultos = (TextView) convertView.findViewById(R.id.lblBultosValue);
            holder.txtUnidades = (TextView) convertView.findViewById(R.id.lblUnidadesValue);
            holder.txtPrecioUnitario = (TextView) convertView.findViewById(R.id.lblPrecioUnitarioValue);
            holder.txtSubTotal = (TextView) convertView.findViewById(R.id.lblSubtotalValue);
            holder.txtAlert = (TextView) convertView.findViewById(R.id.tvAlert);
            holder.llCombo = (LinearLayout) convertView.findViewById(R.id.llCombo);

            holder.linearRentProveedor = (LinearLayout) convertView.findViewById(R.id.idLinearRentProv);
            holder.linearRentEmpresa = (LinearLayout) convertView.findViewById(R.id.idLinearRentEmpresa);
            holder.nivelRentabilidadEmpresa = (TextView) convertView.findViewById(R.id.idNivelRentabilidadEmpresa);
            holder.nivelRentabilidadProveedor = (TextView) convertView.findViewById(R.id.idNivelRentabilidadProveedor);
            holder.txtProveedor = (TextView) convertView.findViewById(R.id.idProveedor);
            holder.idLinearRentabilidad = (LinearLayout) convertView.findViewById(R.id.idLinearRentabilidad);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        VentaDetalle ventaDetalle = items.get(position);
        if (ventaDetalle != null) {
            String descripcion = ventaDetalle.getArticulo().getDescripcion();
            String codigoEmpresa = ventaDetalle.getArticulo().getCodigoEmpresa();
            int bultos = ventaDetalle.getBultos();
            double unidades = ventaDetalle.getUnidades();
            double precioUnitario = ventaDetalle.getPrecioUnitarioSinDescuento();
            double subtotal = ventaDetalle.getImporteTotal();

            if (mCampoFiltro == DESCRIPCION_ARTICULO) {
                TextViewUtils.highlightText(holder.txtDescripcion, descripcion, filter);
                holder.txtCodigoEmpresa.setText(codigoEmpresa);
            } else {
                TextViewUtils.highlightText(holder.txtCodigoEmpresa, codigoEmpresa, filter);
                holder.txtDescripcion.setText(descripcion);
            }

            holder.txtBultos.setText(String.valueOf(bultos));
            holder.txtUnidades.setText(String.valueOf(unidades));
            holder.txtPrecioUnitario.setText(Formatter.formatMoney(precioUnitario));
            holder.txtSubTotal.setText(Formatter.formatMoney(subtotal));

            if (!ventaDetalle.isValid()) {
                holder.txtAlert.setVisibility(View.VISIBLE);
                holder.txtAlert.setError("");
            } else {
                holder.txtAlert.setVisibility(View.GONE);
            }
            setBackground(position, convertView);

            if (ventaDetalle.getDetalleCombo() != null) {
                holder.llCombo.setVisibility(View.VISIBLE);
                getLineasCombo(holder.llCombo, ventaDetalle);
            } else {
                holder.llCombo.setVisibility(View.GONE);
            }

            VentaDetalleBo ventaDetalleBo = new VentaDetalleBo(_repoFactory);
            ListaPrecioDetalle detalle = ventaDetalleBo.getListaPrecioDetalle(ventaDetalle);
            ventaBo.isProductoRentable(ventaDetalle, _venta, detalle);
            if (controlaRentPorSuc) {
                holder.linearRentEmpresa.setVisibility(View.VISIBLE);
                holder.nivelRentabilidadEmpresa.setText(detalle.getNivelRentabilidadEmpresa());
                if (detalle.isRentableXEmpresa())
                    holder.nivelRentabilidadEmpresa.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                else
                    holder.nivelRentabilidadEmpresa.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            } else {
                holder.linearRentEmpresa.setVisibility(View.GONE);
            }
            holder.txtProveedor.setText(ventaDetalle.getArticulo().getProveedor().toString());
            if (controlaRentPorProv && detalle.getNivelRentabilidadProveedor() != null) {
                holder.nivelRentabilidadProveedor.setText(detalle.getNivelRentabilidadProveedor());
                holder.linearRentProveedor.setVisibility(View.VISIBLE);
                if (ventaDetalle.getArticulo().getProveedor().isRentableEnPedido()) {
                    holder.txtProveedor.setTextColor(ContextCompat.getColor(getContext(), R.color.base_text_color));
                } else {
                    holder.txtProveedor.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
                }
                if (detalle.isRentableXProveedor())
                    holder.nivelRentabilidadProveedor.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                else
                    holder.nivelRentabilidadProveedor.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
            } else {
                holder.linearRentProveedor.setVisibility(View.GONE);
            }

            if (!controlaRentPorProv && !controlaRentPorSuc) {
                holder.idLinearRentabilidad.setVisibility(View.GONE);
            }
        }
        return convertView;
    }


    private void getLineasCombo(LinearLayout lnContenedor, VentaDetalle ventaDetalle) {
        lnContenedor.removeAllViews();
        for (VentaDetalle detalleCombo : ventaDetalle.getDetalleCombo()) {
            View lineaPs = LayoutInflater.from(getContext()).inflate(
                    R.layout.lyt_venta_detalle_item, null);
            lineaPs.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_item_normal));
            String descripcion = detalleCombo.getArticulo().getDescripcion();
            String codigoEmpresa = detalleCombo.getArticulo().getCodigoEmpresa();
            int bultos = detalleCombo.getBultos();
            double unidades = detalleCombo.getUnidades();
            double precioUnitario = detalleCombo.getPrecioUnitarioSinDescuento();
            double subtotal = detalleCombo.getImporteTotal();

            TextView tvDescripcion = (TextView) lineaPs.findViewById(R.id.txtDescripcion);
            TextView tvCodigo = (TextView) lineaPs.findViewById(R.id.lblCodigoEmpresaValue);
            TextView tvBultos = (TextView) lineaPs.findViewById(R.id.lblBultosValue);
            TextView tvUnidades = (TextView) lineaPs.findViewById(R.id.lblUnidadesValue);
            TextView tvPrecioUnitario = (TextView) lineaPs.findViewById(R.id.lblPrecioUnitarioValue);
            TextView tvSubtotal = (TextView) lineaPs.findViewById(R.id.lblSubtotalValue);


            TextView txtProveedor = (TextView) lineaPs.findViewById(R.id.idProveedor);
            txtProveedor.setVisibility(View.GONE);
            LinearLayout linearRentProveedor = (LinearLayout) lineaPs.findViewById(R.id.idLinearRentProv);
            linearRentProveedor.setVisibility(View.GONE);
            LinearLayout linearRentEmpresa = (LinearLayout) lineaPs.findViewById(R.id.idLinearRentEmpresa);
            linearRentEmpresa.setVisibility(View.GONE);

            tvDescripcion.setText(descripcion);
            tvCodigo.setText(codigoEmpresa);
            tvBultos.setText(String.valueOf(bultos));
            tvUnidades.setText(String.valueOf(unidades));
            tvPrecioUnitario.setText(Formatter.formatMoney(precioUnitario));
            tvSubtotal.setText(Formatter.formatMoney(subtotal));


            lnContenedor.addView(lineaPs);
        }
    }

    @Override
    public VentaDetalle getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    public void setListValids(List<Boolean> listValids) {
        VentaDetalleBo.setListValids(items, listValids);

        notifyDataSetChanged();
    }

    public void setValid(int position, boolean isValid) {
        items.get(position).setValid(isValid);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView txtDescripcion;
        TextView txtCodigoEmpresa;
        TextView txtBultos;
        TextView txtUnidades;
        TextView txtPrecioUnitario;
        TextView txtSubTotal;
        TextView txtAlert;
        TextView txtProveedor;
        LinearLayout llCombo;
        TextView nivelRentabilidadProveedor;
        TextView nivelRentabilidadEmpresa;
        LinearLayout linearRentProveedor;
        LinearLayout linearRentEmpresa;
        LinearLayout idLinearRentabilidad;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new VentaDetalleFilter();
        }
        return mFilter;
    }

    public int getCampoFiltro() {
        return mCampoFiltro;
    }

    private class VentaDetalleFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filter = constraint.toString();
            String datoDetalle = "";
            FilterResults filterResults = new FilterResults();
            List<VentaDetalle> aux = new ArrayList<>();
            for (VentaDetalle detalle : allItems) {
                switch (mCampoFiltro) {
                    case DESCRIPCION_ARTICULO:
                        datoDetalle = detalle.getArticulo().getDescripcion();
                        break;

                    default:
                        datoDetalle = String.valueOf(detalle.getArticulo().getCodigoEmpresa());
                        break;
                }
                if (constraint == null) {
                    aux.add(detalle);
                } else {
                    String filtro = constraint.toString();
                    filtro = filtro.toLowerCase();
                    datoDetalle = datoDetalle.toLowerCase();
                    if (datoDetalle.contains(filtro))
                        aux.add(detalle);
                }

            }
            filterResults.values = aux;
            filterResults.count = aux.size();

            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (List<VentaDetalle>) results.values;
            notifyDataSetChanged();
        }

    }

    @Override
    public void sort() {
        // TODO Auto-generated method stub

    }
}
