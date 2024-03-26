package com.ar.vgmsistemas.view.informes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MovimientoBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListadoReciboAdapter extends ArrayAdapter<Recibo> {

    private static final int textViewResourceId = R.layout.lyt_estadistica_recibo;
    private final MovimientoBo _movimientoDao;
    List<Recibo> recibos;
    List<Recibo> allRecibos;

    private RecibosFilter filter;

    public ListadoReciboAdapter(Context context, List<Recibo> recibos) {
        super(context, textViewResourceId, recibos);
        this.recibos = recibos;
        this.allRecibos = recibos;
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        this._movimientoDao = new MovimientoBo(repoFactory);

    }

    @Override
    public Recibo getItem(int position) {
        return recibos.get(position);
    }

    public void setRecibos(List<Recibo> recibos) {
        this.recibos = recibos;
        this.allRecibos = recibos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_estadistica_recibo, null);
            holder = new ViewHolder();

            holder.txtNumeroDocumento = (TextView) convertView.findViewById(R.id.lblNumeroRecibo);
            holder.txtCodigo = (TextView) convertView.findViewById(R.id.lblCodigoCliente);
            holder.txtCliente = (TextView) convertView.findViewById(R.id.lblCliente);
            holder.txtTotal = (TextView) convertView.findViewById(R.id.lblTotalRecibo);
            holder.txtFecha = (TextView) convertView.findViewById(R.id.lblFechaRecibo);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.lytEstadisticaRecibo);
            holder.txtEstadoRecibo = (TextView) convertView.findViewById(R.id.lblEstadoRecibo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Recibo recibo = recibos.get(position);
        if (recibo != null) {
            DecimalFormat df = new DecimalFormat("00000000");
            String numeroDocumento = df.format(recibo.getId().getIdRecibo());
            holder.txtNumeroDocumento.setText(numeroDocumento);
            holder.txtCodigo.setText(recibo.getCliente().getId().toString());
            holder.txtCliente.setText(recibo.getCliente().getRazonSocial());
            holder.txtTotal.setText(Formatter.formatMoney(recibo.getTotal()));
            holder.txtFecha.setText(Formatter.formatDate(recibo.getFechaMovil()));
            String estadoRecibo = "PENDIENTE";
            if (recibo.getIdEstado().equals(Recibo.ESTADO_ANULADO)) {
                estadoRecibo = "ANULADO";
            }
            if (recibo.getIdEstado().equals(Recibo.ESTADO_IMPUTADO)) estadoRecibo = "IMPUTADO";
            holder.txtEstadoRecibo.setText(estadoRecibo);
        }

        int resultadoEnvio = recibo != null ? recibo.getResultadoEnvio() : 1;
        Drawable mDraw;
        if (resultadoEnvio == CodeResult.RESULT_ALGUN_COMPROBANTE_TIENE_SALDO_CERO || resultadoEnvio == CodeResult.RESULT_MONTO_DISPONIBLE_CERO_RECIBO_BO || resultadoEnvio == CodeResult.RESULT_NO_SE_ENCONTRO_UN_COMPROBANTE_EN_CTACTE_RECIBO_BO) {
            mDraw = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.shape_item_envio_recibo, null);
        } else {
            mDraw = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.selector_item, null);
        }
        holder.layout.setBackground(mDraw);
        return convertView;
    }

    public static class ViewHolder {
        TextView txtNumeroDocumento;
        TextView txtCodigo;
        TextView txtCliente;
        TextView txtTotal;
        TextView txtFecha;
        LinearLayout layout;
        TextView txtEstadoRecibo;
    }

    @Override
    public int getCount() {
        return recibos.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new RecibosFilter();
        }
        return filter;
    }

    private class RecibosFilter extends Filter {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            recibos = (List<Recibo>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {

            FilterResults results = new FilterResults();
            List<Recibo> aux = new ArrayList<>();
            int filtroRecibosEnviados = PreferenciaBo.getInstance().getPreferencia(getContext()).getFiltroRecibosEnviados();
            for (int index = 0; index < allRecibos.size(); index++) {
                Recibo recibo = allRecibos.get(index);
                //Obtengo el movimiento del recibo
                Movimiento movimientoRecibo = null;
                try {
                    movimientoRecibo = _movimientoDao.recoveryByIdMovil(recibo.getIdMovil());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Date fechaSincronizacionRecibo;

                if (movimientoRecibo != null) {
                    fechaSincronizacionRecibo = movimientoRecibo.getFechaSincronizacion();
                } else {
                    fechaSincronizacionRecibo = Calendar.getInstance().getTime();
                }

                if ((filtroRecibosEnviados == Preferencia.FILTRO_TODOS)
                        || (filtroRecibosEnviados == Preferencia.FILTRO_ENVIADOS && fechaSincronizacionRecibo != null)
                        || (filtroRecibosEnviados == Preferencia.FILTRO_NO_ENVIADAS && fechaSincronizacionRecibo == null)) {

                    aux.add(recibo);
                }
            }
            results.values = aux;
            results.count = aux.size();
            return results;
        }
    }
}
