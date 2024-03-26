package com.ar.vgmsistemas.view.nopedido;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MovimientoBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoPedidoAdapter extends ArrayAdapter<NoPedido> {
    private final MovimientoBo _movimientoBo;
    List<NoPedido> noPedidos;
    List<NoPedido> allNoPedidos;
    private NoPedidoFilter filter;


    public NoPedidoAdapter(Context context, int textViewResourceId, List<NoPedido> objects) {
        super(context, textViewResourceId, objects);
        this.noPedidos = objects;
        this.allNoPedidos = this.noPedidos;
        getFilter().filter("");
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        this._movimientoBo = new MovimientoBo(repoFactory);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_estadistica_no_pedido, null);
            holder = new ViewHolder();
            //TextView´s
            holder.txtCodigo = convertView.findViewById(R.id.lblCodigoClienteEstadisticaNoPedido);
            holder.txtCliente = convertView.findViewById(R.id.lblClienteEstadisticaNoPedido);
            holder.txtMotivo = convertView.findViewById(R.id.lblMotivoEstadisticaNoPedido);
            holder.txtFecha = convertView.findViewById(R.id.lblFechaEstadisticaNoPedido);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NoPedido noPedido = noPedidos.get(position);
        if (noPedido != null) {
            holder.txtCodigo.setText(noPedido.getCliente().getId().toString());
            holder.txtCliente.setText(noPedido.getCliente().getRazonSocial());
            holder.txtMotivo.setText(noPedido.getMotivoNoPedido().getDescripcion());
            holder.txtFecha.setText(Formatter.formatDate(noPedido.getFechaNoPedido()));
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtCodigo;
        TextView txtCliente;
        TextView txtMotivo;
        TextView txtFecha;
    }

    @Override
    public int getCount() {
        return noPedidos.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new NoPedidoFilter();
        }
        return filter;
    }


    private class NoPedidoFilter extends Filter {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            noPedidos = (List<NoPedido>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {

            FilterResults results = new FilterResults();
            List<NoPedido> aux = new ArrayList<>();
            int filtroNoPedidosEnviados = PreferenciaBo.getInstance().getPreferencia(getContext()).getFiltroNoAtencionEnviadas();
            for (int index = 0; index < allNoPedidos.size(); index++) {
                NoPedido noPedido = allNoPedidos.get(index);
                Movimiento movimientoRecibo = null;
                try {
                    movimientoRecibo = _movimientoBo.recoveryByIdMovil(noPedido.getIdMovil());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Date fechaSincronizacionNoPedido = null;

                if (movimientoRecibo != null) {
                    fechaSincronizacionNoPedido = movimientoRecibo.getFechaSincronizacion();
                }

                if ((filtroNoPedidosEnviados == Preferencia.FILTRO_TODOS)
                        || (filtroNoPedidosEnviados == Preferencia.FILTRO_ENVIADOS && fechaSincronizacionNoPedido != null)
                        || (filtroNoPedidosEnviados == Preferencia.FILTRO_NO_ENVIADAS && fechaSincronizacionNoPedido == null)) {

                    aux.add(noPedido);
                }
            }
            results.values = aux;
            results.count = aux.size();
            return results;
        }
    }
}
