package com.ar.vgmsistemas.view.reparto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.VentaDetalleBo;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.informes.ListadoVentaAdapter;

import java.util.List;

public class ListadoPedidoSugeridoAdapter extends ListadoVentaAdapter {

    private List<List<VentaDetalle>> _ventasDetalle;

    private final VentaDetalleBo ventaDetalleBo;

    public ListadoPedidoSugeridoAdapter(Context context, List<Venta> objects, List<List<VentaDetalle>> ventasDetalle) {
        super(context, objects);
        _ventasDetalle = ventasDetalle;
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        ventaDetalleBo = new VentaDetalleBo(repoFactory);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Venta venta = getItem(position);
        // No reutilizo en el caso de existir el convert view, porque solapa los
        // datos

        if (convertView == null) {

            convertView = View.inflate(this.getContext(), R.layout.lyt_listado_pedido_sugerido, null);
            holder = new ViewHolder();

            holder.txtCliente = (TextView) convertView.findViewById(R.id.lblClientePedidoSugerido);
            holder.txtFecha = (TextView) convertView.findViewById(R.id.lblFechaRegistroPedidoSugerido);
            holder.txtCodigo = (TextView) convertView.findViewById(R.id.lblCodigoClientePedidoSugerido);
            holder.layoutContenedorLineasPs = (LinearLayout) convertView.findViewById(R.id.linerLayoutContenedorLineasPs);
            holder.txtTotal = (TextView) convertView.findViewById(R.id.lblTotalEstadisticaPedido);
            holder.txtDomicilio = (TextView) convertView.findViewById(R.id.lblDomicilioValue);
            holder.ivGenerado = (ImageView) convertView.findViewById(R.id.ivGenerado);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (venta != null) {
            holder.txtCliente.setText(venta.getCliente().getRazonSocial());

            double total;
            if (venta.getDocumento() != null && !venta.getDocumento().isLegal()) {
                total = venta.getTotal();
            } else {
                total = venta.getSubtotal();
            }

            holder.txtTotal.setText(Formatter.formatMoney(total));
            holder.txtCodigo.setText(venta.getCliente().getId().toString());
            holder.txtFecha.setText(Formatter.formatDate(venta.getFechaVenta()));
            holder.txtDomicilio.setText(venta.getCliente().getDomicilio());
            holder.layoutContenedorLineasPs.removeAllViews();
            if (venta.getSnGenerado().equals(Venta.SI)) {
                holder.ivGenerado.setBackgroundResource(R.drawable.ic_ps_generado);
                holder.ivGenerado.setVisibility(View.VISIBLE);
            } else if (venta.getAnulo().equals(Venta.SI)) {
                holder.ivGenerado.setBackgroundResource(R.drawable.ic_ps_anulado);
                holder.ivGenerado.setVisibility(View.VISIBLE);
            } else {
                holder.ivGenerado.setVisibility(View.GONE);
            }
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            getLineasPs(holder.layoutContenedorLineasPs, position);
        }
        return convertView;
    }

    public void reloadData(List<Venta> ventas) {
        this.ventas = ventas;
        allVentas = ventas;
        notifyDataSetChanged();

    }

    private void getLineasPs(LinearLayout lnContenedor, int position) {

        for (int i = 0; i < _ventasDetalle.get(position).size(); i++) {
            View lineaPs = LayoutInflater.from(getContext()).inflate(
                    R.layout.lyt_venta_detalle_item_ps_hacienda, null);

            TextView txtDescripcion = (TextView) lineaPs.findViewById(R.id.txtDescripcion);
            TextView txtUnidades = (TextView) lineaPs.findViewById(R.id.lblUnidadesValue);
            TextView txtPrecioUnitario = (TextView) lineaPs.findViewById(R.id.lblPrecioUnitarioValue);
            TextView txtSubtotal = (TextView) lineaPs.findViewById(R.id.lblSubtotalValue);
            TextView txtCaKilos = (TextView) lineaPs.findViewById(R.id.lblCaKilosValue);

            txtDescripcion.setText(_ventasDetalle.get(position).get(i).getArticulo().getDescripcion());
            txtUnidades.setText(String.valueOf(_ventasDetalle.get(position).get(i).getUnidades()));
            txtPrecioUnitario.setText(String.valueOf(Formatter.formatMoney(_ventasDetalle.get(position).get(i).getPrKiloUnitario())));
            double total = ventaDetalleBo.getSubtotalKilos(_ventasDetalle.get(position).get(i).getCaArticulosKilos(), _ventasDetalle.get(position).get(i).getPrKiloUnitario());
            txtSubtotal.setText(Formatter.formatMoney((total)));
            txtCaKilos.setText(String.valueOf(_ventasDetalle.get(position).get(i).getCaArticulosKilos()));

            lnContenedor.addView(lineaPs);
        }
    }

    private static class ViewHolder {
        TextView txtCliente;
        TextView txtFecha;
        TextView txtTotal;
        TextView txtCodigo;
        TextView txtDomicilio;
        ImageView ivGenerado;
        LinearLayout layoutContenedorLineasPs;
    }

    public void setVentasDetalle(List<List<VentaDetalle>> ventasDetalle) {
        this._ventasDetalle = ventasDetalle;
    }

}
