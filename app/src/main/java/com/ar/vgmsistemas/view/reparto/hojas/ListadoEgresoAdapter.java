package com.ar.vgmsistemas.view.reparto.hojas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MovimientoBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.ProveedorBo;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Proveedor;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListadoEgresoAdapter extends ArrayAdapter<Compra> {

    private static final int textViewResourceId = R.layout.lyt_estadistica_recibo;
    private final MovimientoBo movimientoBo;
    List<Compra> egresos;
    List<Compra> allEgresos;

    private EgresosFilter filter;

    public ListadoEgresoAdapter(Context context, List<Compra> egresos) {
        super(context, textViewResourceId, egresos);
        this.egresos = egresos;
        this.allEgresos = egresos;
        RepositoryFactory _repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        movimientoBo = new MovimientoBo(_repoFactory);
    }

    @Override
    public Compra getItem(int position) {
        return egresos.get(position);
    }

    public void setEgresos(List<Compra> egresos) {
        this.egresos = egresos;
        this.allEgresos = egresos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_estadistica_egreso, null);
            holder = new ViewHolder();

            holder.txtCodigo = (TextView) convertView.findViewById(R.id.lblCodigoEgreso);
            holder.txtProveedor = (TextView) convertView.findViewById(R.id.lblProveedorValue);
            holder.txtTotal = (TextView) convertView.findViewById(R.id.lblTotalEgreso);
            holder.txtFecha = (TextView) convertView.findViewById(R.id.lblFechaEgreso);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.lytEstadisticaEgreso);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Compra egreso = egresos.get(position);
        if (egreso != null) {
            holder.txtCodigo.setText(egreso.getId().toString());
            try {
                RepositoryFactory _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
                ProveedorBo proveedorBo = new ProveedorBo(_repoFactory);
                Proveedor proveedor = proveedorBo.recoveryProveedorById(egreso.getId().getIdProveedor());
                holder.txtProveedor.setText(proveedor.getDeProveedor());
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.txtTotal.setText(Formatter.formatMoney(egreso.getPrCompra()));
            holder.txtFecha.setText(Formatter.formatDate(egreso.getFeFactura()));
        }

        if (egreso.getSnAnulo() != null && egreso.getSnAnulo().equals(Compra.SI)) {
            holder.layout.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_item_anulado));
        } else {
            holder.layout.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.selector_item));
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView txtCodigo;
        TextView txtProveedor;
        TextView txtTotal;
        TextView txtFecha;
        LinearLayout layout;
    }

    @Override
    public int getCount() {
        return egresos.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new EgresosFilter();
        }
        return filter;
    }

    private class EgresosFilter extends Filter {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            egresos = (List<Compra>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {

            FilterResults results = new FilterResults();
            List<Compra> aux = new ArrayList<>();
            int filtroEgresosEnviados = PreferenciaBo.getInstance().getPreferencia(getContext()).getFiltroEgresosEnviados();
            for (int index = 0; index < allEgresos.size(); index++) {
                Compra egreso = allEgresos.get(index);
                //Obtengo el movimiento del recibo
                Movimiento movimientoRecibo = null;
                try {
                    movimientoRecibo = movimientoBo.recoveryByIdMovil(egreso.getIdMovil());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Date fechaSincronizacionEgreso;

                if (movimientoRecibo != null) {
                    fechaSincronizacionEgreso = movimientoRecibo.getFechaSincronizacion();
                } else {
                    fechaSincronizacionEgreso = Calendar.getInstance().getTime();
                }

                if ((filtroEgresosEnviados == Preferencia.FILTRO_TODOS)
                        || (filtroEgresosEnviados == Preferencia.FILTRO_ENVIADOS && fechaSincronizacionEgreso != null)
                        || (filtroEgresosEnviados == Preferencia.FILTRO_NO_ENVIADAS && fechaSincronizacionEgreso == null)) {

                    aux.add(egreso);
                }
            }
            results.values = aux;
            results.count = aux.size();
            return results;
        }
    }
}
