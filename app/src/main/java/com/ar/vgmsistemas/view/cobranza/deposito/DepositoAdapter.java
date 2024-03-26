package com.ar.vgmsistemas.view.cobranza.deposito;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.List;

public class DepositoAdapter extends ListItemAdapter<Deposito> {

    public DepositoAdapter(Context context, List<Deposito> objects) {
        super(context, R.layout.lyt_deposito_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_deposito_item, null);
            holder = new ViewHolder();
            holder.txtBanco = (TextView) convertView.findViewById(R.id.lblBancoValue);
            holder.txtNumeroComprobante = (TextView) convertView.findViewById(R.id.lblNumeroComprobanteValue);
            holder.txtFecha = (TextView) convertView.findViewById(R.id.lblFechaValue);
            holder.txtTotal = (TextView) convertView.findViewById(R.id.lblTotalValue);
            holder.txtCantidadCheques = (TextView) convertView.findViewById(R.id.lblCantidadChequesValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Deposito deposito = items.get(position);
        if (deposito != null) {
            holder.txtBanco.setText(deposito.getBanco().getDenominacion());
            holder.txtNumeroComprobante.setText(String.valueOf(deposito.getNumeroComprobante()));
            holder.txtFecha.setText(Formatter.formatDate(deposito.getFechaDepositoMovil()));
            holder.txtTotal.setText(Formatter.formatMoney(deposito.getImporte()));
            holder.txtCantidadCheques.setText(String.valueOf(deposito.getCantidadCheques()));
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtBanco;
        TextView txtNumeroComprobante;
        TextView txtFecha;
        TextView txtTotal;
        TextView txtCantidadCheques;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void sort() {

    }
}