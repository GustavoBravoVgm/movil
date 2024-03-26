package com.ar.vgmsistemas.view.informes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.List;


public class ListadoVentaAdapter extends ArrayAdapter<Venta> {

    public List<Venta> ventas;
    public Empresa empresa;
    public List<Venta> allVentas;
    private VentasFilter filter;
    private static final int textViewResourceId = R.layout.lyt_estadistica_pedido;


    public ListadoVentaAdapter(Context context, List<Venta> objects) {
        super(context, textViewResourceId, objects);
        this.ventas = objects;
        this.allVentas = this.ventas;
    }


    public ListadoVentaAdapter(Context context, List<Venta> objects, Empresa empresa) {
        super(context, textViewResourceId, objects);
        this.ventas = objects;
        this.allVentas = this.ventas;
        this.empresa = empresa;
    }

    @Override
    public Venta getItem(int position) {
        return ventas.get(position);
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
        this.allVentas = ventas;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        //No reutilizo en el caso de existir el convert view, porque solapa los datos

        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_estadistica_pedido, null);
            holder = new ViewHolder();

            holder.txtDocumento = convertView.findViewById(R.id.lblIdDocumentoEstadisticaPedido);//(TextView)
            holder.txtPuntoVenta = convertView.findViewById(R.id.lblIdPuntoVentaEstadisticaPedido);//(TextView)
            holder.txtNumeroDocumento = convertView.findViewById(R.id.lblIdNumeroDocumentoEstadisticaPedido);//(TextView)
            holder.txtCodigo = convertView.findViewById(R.id.lblCodigoClienteEstadisticaPedido);//(TextView)
            holder.txtCliente = convertView.findViewById(R.id.lblClienteEstadisticaPedido);//(TextView)
            holder.txtTotal = convertView.findViewById(R.id.lblTotalEstadisticaPedido);//(TextView)
            holder.txtTotalGral = convertView.findViewById(R.id.lblTotalGralEstadisticaPedido);//(TextView)
            holder.txtFecha = convertView.findViewById(R.id.lblFechaRegistroEstadisticaPedido);//(TextView)
            //holder.txtAnulado = (TextView) convertView.findViewById(R.id.lblAnulado);//(TextView)
            holder.layout = convertView.findViewById(R.id.lyt_estadistica_lyt);//(LinearLayout)

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        Venta venta = getItem(position);
        if (venta != null) {
            holder.txtDocumento.setText(venta.getId().getIdDocumento());
            String ptoVtaStr = venta.getId().getPuntoVenta() + "";
            holder.txtPuntoVenta.setText(ptoVtaStr);
            holder.txtNumeroDocumento.setText(venta.getNumDoc());
            PkCliente pkCliente = new PkCliente();
            pkCliente.setIdSucursal(venta.getCliente().getId().getIdSucursal());
            pkCliente.setIdCliente(venta.getCliente().getId().getIdCliente());
            pkCliente.setIdComercio(venta.getCliente().getId().getIdComercio());
            holder.txtCodigo.setText(pkCliente.toString());

            boolean clienteValido;
            try {
                clienteValido = !venta.getCliente().getRazonSocial().equals("");
            } catch (NullPointerException ne) {
                clienteValido = false;
            }
            if (!clienteValido) {
                holder.txtCliente.setText(venta.getDeCliente());
            } else {
                holder.txtCliente.setText(venta.getCliente().getRazonSocial());
            }

            double totalSinImp;
            double total;

            if (venta.getDocumento() != null && venta.getDocumento().isLegal() || (empresa.getSnSumIvaReporteMovil().equals("N") && venta.getDocumento().getTiAplicaIva() == 0)) {
                totalSinImp = venta.getSubtotal();
            } else {
                totalSinImp = venta.getTotal();
            }
            total = venta.getTotal();

            holder.txtTotal.setText(Formatter.formatMoney(totalSinImp));
            holder.txtTotalGral.setText(Formatter.formatMoney(total));
            holder.txtFecha.setText(Formatter.formatDate(venta.getFechaVenta()));

            String anulado = "S";
            if (venta.getAnulo().equals(anulado)) {
                holder.layout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_item_anulado));
            } else {
                holder.layout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.selector_item));
            }

        }

        return convertView;
    }

    private static class ViewHolder {
        TextView txtDocumento;
        TextView txtPuntoVenta;
        TextView txtNumeroDocumento;
        TextView txtCodigo;
        TextView txtCliente;
        TextView txtTotal;
        TextView txtFecha;
        LinearLayout layout;
        TextView txtTotalGral;
    }

    @Override
    public int getCount() {
        return ventas.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new VentasFilter();
        }
        return filter;
    }

    private class VentasFilter extends Filter {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            ventas = (List<Venta>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {

            FilterResults results = new FilterResults();
            List<Venta> aux = new ArrayList<>();

            for (int index = 0; index < allVentas.size(); index++) {
                Venta venta = allVentas.get(index);

                int filtroVentasEnviadas = PreferenciaBo.getInstance().getPreferencia(getContext()).getFiltroVentasEnviadas();

                String snAnulo = "S";

                if ((filtroVentasEnviadas == Preferencia.FILTRO_TODOS)
                        || (filtroVentasEnviadas == Preferencia.FILTRO_ENVIADOS && venta.getFechaSincronizacion() != null)
                        || (filtroVentasEnviadas == Preferencia.FILTRO_NO_ENVIADAS && venta.getFechaSincronizacion() == null)
                        || (filtroVentasEnviadas == Preferencia.FILTRO_ANULADOS && venta.getAnulo().equals(snAnulo))) {

                    String filtroTipoDocumento = PreferenciaBo.getInstance().getPreferencia(getContext()).getFiltroTipoDocumento();
                    if (filtroTipoDocumento.equals("") || venta.getId().getIdDocumento().equals(filtroTipoDocumento))
                        aux.add(venta);
                }
            }
            results.values = aux;
            results.count = aux.size();
            return results;
        }
    }
}
