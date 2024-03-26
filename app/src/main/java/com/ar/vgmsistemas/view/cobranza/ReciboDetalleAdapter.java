package com.ar.vgmsistemas.view.cobranza;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.ReciboDetalle;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;

import java.util.List;

public class ReciboDetalleAdapter extends ArrayAdapter<ReciboDetalle> {
    List<ReciboDetalle> reciboDetalles;
    List<ReciboDetalle> allReciboDetalles;
    boolean snVentanaDto;//dice o no si la ventana que instancia este adapter muestra o no el edittext de descuento

    public ReciboDetalleAdapter(Context context, int textViewResourceId, List<ReciboDetalle> list, boolean snVentanaDto) {
        super(context, textViewResourceId, list);
        this.reciboDetalles = list;
        this.allReciboDetalles = list;
        this.snVentanaDto = snVentanaDto;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        CuentaCorriente cuentaCorriente = reciboDetalles.get(position).getCuentaCorriente();
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_cuenta_corriente_item, null);
            holder = new ViewHolder();
            holder.lblDocumento = (TextView) convertView.findViewById(R.id.lblDocumentoValue);
            holder.lblFecha = (TextView) convertView.findViewById(R.id.lblFechaValue);
            holder.lblNumero = (TextView) convertView.findViewById(R.id.lblNumeroDocumentoValue);
            holder.lblPuntoVenta = (TextView) convertView.findViewById(R.id.lblPuntoVentaValue);
            holder.lblCondicionVenta = (TextView) convertView.findViewById(R.id.lblCondicionVentaValue);
            holder.lblTotal = (TextView) convertView.findViewById(R.id.lblTotalValue);
            holder.lblSaldo = (TextView) convertView.findViewById(R.id.lblSaldoValue);
            holder.lytDescuento = (LinearLayout) convertView.findViewById(R.id.lytDescuento);
            holder.lblDto = (TextView) convertView.findViewById(R.id.lblDtoValue);
            holder.lblComercio = (TextView) convertView.findViewById(R.id.lblComercioValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (cuentaCorriente != null) {
            String dtoStr = reciboDetalles.get(position).getTaDtoRecibo() + "%";
            holder.lblDto.setText(dtoStr);
            holder.lblDocumento.setText(cuentaCorriente.getId().getIdDocumento());
            holder.lblFecha.setText(Formatter.formatDate(cuentaCorriente.getFechaVenta()));
            holder.lblNumero.setText(String.valueOf(cuentaCorriente.getNumdoc()));
            holder.lblPuntoVenta.setText(String.valueOf(cuentaCorriente.getId().getPuntoVenta()));
            holder.lblComercio.setText(String.valueOf(cuentaCorriente.getCliente().getId().getIdComercio()));
            holder.lblCondicionVenta.setText(cuentaCorriente.getCondicionVenta().getDescripcion());
            holder.lblTotal.setText(Formatter.formatMoney(cuentaCorriente.getTotalCuota()));
            holder.lblSaldo.setText(Formatter.formatMoney(Matematica.restarPorcentaje(cuentaCorriente.calcularSaldo(), reciboDetalles.get(position).getTaDtoRecibo())));

            if (!snVentanaDto) {
                holder.lytDescuento.setVisibility(View.INVISIBLE);
            }
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private static class ViewHolder {
        TextView lblDocumento;
        TextView lblPuntoVenta;
        TextView lblComercio;
        TextView lblFecha;
        TextView lblNumero;
        TextView lblCondicionVenta;
        TextView lblTotal;
        TextView lblSaldo;
        TextView lblDto;
        LinearLayout lytDescuento;
    }
}
