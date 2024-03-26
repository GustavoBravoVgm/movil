package com.ar.vgmsistemas.view.reparto.hojas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaBo;
import com.ar.vgmsistemas.bo.HojaDetalleBo;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.TextViewUtils;
import com.ar.vgmsistemas.view.SelectableListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class HojasAdapter extends SelectableListItemAdapter<HojaDetalle> {
    private HojaDetalleFilter mFilter;
    public static final int CRITERIA_NOMBRE = 0;
    public static final int CRITERIA_DIRECCION = 1;
    public static final int CRITERIA_ID = 2;

    public static final int FILTRO_TODOS = 0;
    public static final int FILTRO_ATENDIDOS = 1;
    public static final int FILTRO_ANULADOS = 2;
    public static final int FILTRO_NO_ENTREGADOS = 3;
    public static final int FILTRO_NO_ATENDIDOS = 4;

    //para calculo de cabecera
    public static double mTotalContado;
    public static double mTotalCtaCte;
    public static double mTotalAnulado;
    public static double mTotalNCs;
    public static double mTotalPendientes;


    public HojasAdapter(Context context, int textViewResourceId, List<HojaDetalle> objects) {
        super(context, textViewResourceId, objects);
        items = objects;

        mTotalContado = HojaBo.getCdoDetalles(objects);
        mTotalAnulado = HojaBo.getAnulado(objects);
        mTotalCtaCte = HojaBo.getCtaCteDetalles(objects);
        mTotalNCs = HojaBo.getCreditoDetalles(objects);
        mTotalPendientes = HojaBo.getPendiente(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.lyt_hoja_factura, null);
            holder.tvNombre = (TextView) convertView.findViewById(R.id.lblApellido);
            holder.tvId = (TextView) convertView.findViewById(R.id.lblCodigo);
            holder.tvDomicilio = (TextView) convertView.findViewById(R.id.lblDomicilio);
            holder.tvPkVenta = (TextView) convertView.findViewById(R.id.tvPkVenta);
            holder.tvCondVta = (TextView) convertView.findViewById(R.id.tvCondVta);
            holder.tvFecha = (TextView) convertView.findViewById(R.id.tvFecha);
            holder.tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
            holder.llValores = (LinearLayout) convertView.findViewById(R.id.llValores);
            holder.tvCdo = (TextView) convertView.findViewById(R.id.tvCdo);
            holder.tvCredito = (TextView) convertView.findViewById(R.id.tvCredito);
            holder.tvCuentaCorriente = (TextView) convertView.findViewById(R.id.tvCuentaCorriente);
            holder.tvEstado = (TextView) convertView.findViewById(R.id.tvEstado);
            setParent(parent);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HojaDetalle detalle = items.get(position);
        updateView(holder, detalle);
        setBackground(position, convertView);

        return convertView;
    }

    public void updateItemView(int index, HojaDetalle detalle) {
        View view = getParent().getChildAt(index - getParent().getFirstVisiblePosition());
        updateView((ViewHolder) view.getTag(), detalle);
    }

    private void updateView(ViewHolder holder, HojaDetalle detalle) {
        switch (getCampoBusqueda()) {
            case CRITERIA_NOMBRE:
                TextViewUtils.highlightText(holder.tvNombre, detalle.getCliente().getRazonSocial(), getFilteredString());
                holder.tvId.setText(detalle.getCliente().getId().toString());
                holder.tvDomicilio.setText(detalle.getCliente().getDomicilio());
                break;
            case CRITERIA_DIRECCION:
                holder.tvNombre.setText(detalle.getCliente().getRazonSocial());
                TextViewUtils.highlightText(holder.tvDomicilio, detalle.getCliente().getDomicilio(),
                        getFilteredString());
                holder.tvId.setText(detalle.getCliente().getId().toString());
                break;

            case CRITERIA_ID:
                holder.tvNombre.setText(detalle.getCliente().getRazonSocial());
                holder.tvDomicilio.setText(detalle.getCliente().getDomicilio());
                TextViewUtils.highlightText(holder.tvId, detalle.getCliente().getId().toString(), getFilteredString());

                break;
            default:
                break;
        }

        holder.tvPkVenta.setText(detalle.getId().toString());
        holder.tvCondVta.setText(detalle.getCondicionVenta().getDescripcion().trim());
        holder.tvFecha.setText(Formatter.formatDate(detalle.getFeVenta()));
        holder.tvTotal.setText(Formatter.formatMoney(detalle.getPrTotal()));
        holder.llValores.setVisibility((HojaDetalleBo.hojaTratada(detalle) || detalle.getTiEstado().equals(HojaDetalle.PENDIENTE)) ? View.VISIBLE : View.GONE);
        holder.tvEstado.setText(detalle.getTiEstado().equals(HojaDetalle.SIN_ESTADO) ? "" : detalle.getTiEstado());
        holder.tvCdo.setText(Formatter.formatMoney(detalle.getPrPagado()));
        holder.tvCredito.setText(Formatter.formatMoney(detalle.getPrNotaCredito()));
        holder.tvCuentaCorriente.setText(Formatter.formatMoney(HojaDetalleBo.getEnCuentaCorriente(detalle)));
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new HojaDetalleFilter();
        }
        return mFilter;
    }

    @Override
    public void sort() {
        // TODO Auto-generated method stub

    }

    private class ViewHolder {
        TextView tvNombre;
        TextView tvId;
        TextView tvDomicilio;
        TextView tvPkVenta;
        TextView tvCondVta;
        TextView tvFecha;
        TextView tvTotal;
        TextView tvEstado;

        LinearLayout llValores;
        TextView tvCdo;
        TextView tvCredito;
        TextView tvCuentaCorriente;

    }

    private class HojaDetalleFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            setFilteredString(prefix.toString());
            FilterResults results = new FilterResults();
            List<HojaDetalle> aux = new ArrayList<>();

            for (int index = 0; index < allItems.size(); index++) {
                HojaDetalle hojaDetalle = allItems.get(index);
                switch (getCampoFiltro()) {
                    case FILTRO_NO_ENTREGADOS:
                        if (!hojaDetalle.getTiEstado().equals(HojaDetalle.PENDIENTE)) {
                            continue;
                        }
                        break;
                    case FILTRO_ANULADOS:
                        if (!hojaDetalle.getTiEstado().equals(HojaDetalle.ANULADO)) {
                            continue;
                        }
                        break;
                    case FILTRO_NO_ATENDIDOS:
                        if (!hojaDetalle.getTiEstado().equals(HojaDetalle.SIN_ESTADO)) {
                            continue;
                        }
                        break;
                    case FILTRO_ATENDIDOS:
                        if (!hojaDetalle.getTiEstado().equals(HojaDetalle.CONTADO)
                                || !hojaDetalle.getTiEstado().equals(HojaDetalle.CUENTA_CORRIENTE)) {
                            continue;
                        }
                        break;
                }
                String datos = "";
                switch (getCampoBusqueda()) {
                    case CRITERIA_DIRECCION:
                        datos = hojaDetalle.getCliente().getDomicilio();
                        break;
                    case CRITERIA_NOMBRE:
                        datos = hojaDetalle.getCliente().getRazonSocial();
                        break;

                    case CRITERIA_ID:
                        datos = hojaDetalle.getCliente().getId().toString();
                        break;
                }

                if (prefix == null)
                    aux.add(hojaDetalle);
                else {
                    String filtro = prefix.toString();
                    filtro = filtro.toLowerCase();
                    datos = datos.toLowerCase();
                    if (datos.contains(filtro))
                        aux.add(hojaDetalle);
                }
            }
            //calculo totales segun filtro
            if (aux != null && aux.size() > 0) {
                mTotalContado = HojaBo.getCdoDetalles(aux);
                mTotalAnulado = HojaBo.getAnulado(aux);
                mTotalCtaCte = HojaBo.getCtaCteDetalles(aux);
                mTotalNCs = HojaBo.getCreditoDetalles(aux);
                mTotalPendientes = HojaBo.getPendiente(aux);
            } else {
                inicializarTotales();
            }

            results.values = aux;
            results.count = aux.size();
            return results;
        }

        private void inicializarTotales() {
            mTotalContado = 0;
            mTotalCtaCte = 0;
            mTotalAnulado = 0;
            mTotalNCs = 0;
            mTotalPendientes = 0;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (List<HojaDetalle>) results.values;
            notifyDataSetChanged();

        }

    }

}
