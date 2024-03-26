package com.ar.vgmsistemas.view.reparto.hojas.resumenCobranza;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.List;

public class ChequeListadoHojasAdapter extends ListItemAdapter<Cheque> {

    public ChequeListadoHojasAdapter(Context context, List<Cheque> objects) {
        super(context, R.layout.lyt_cheques_item_cobranza, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_cheques_item, null);
            holder = new ViewHolder();
            holder.txtBanco = (TextView) convertView.findViewById(R.id.lblBancoValue);
            holder.txtSucursal = (TextView) convertView.findViewById(R.id.lblSucursalValue);
            holder.txtNumeroCheque = (TextView) convertView.findViewById(R.id.lblNumeroChequeValue);
            holder.txtNroCuenta = (TextView) convertView.findViewById(R.id.lblNumeroCuentaValue);
            holder.txtFecha = (TextView) convertView.findViewById(R.id.lblFechaValue);
            holder.txtTotal = (TextView) convertView.findViewById(R.id.lblTotalValue);
            holder.txtCuit = (TextView) convertView.findViewById(R.id.lblCuitValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Cheque cheque = items.get(position);
        if (cheque != null) {
            holder.txtBanco.setText(cheque.getBanco().getDenominacion());
            holder.txtSucursal.setText(String.valueOf(cheque.getId().getSucursal()));
            holder.txtNumeroCheque.setText(String.valueOf(cheque.getId().getNumeroCheque()));
            holder.txtNroCuenta.setText(String.valueOf(cheque.getId().getNroCuenta()));
            holder.txtFecha.setText(Formatter.formatDate(cheque.getFechaChequeMovil()));
            holder.txtTotal.setText(Formatter.formatMoney(cheque.getImporte()));
            holder.txtCuit.setText(cheque.getCuit());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtBanco;
        TextView txtSucursal;
        TextView txtNumeroCheque;
        TextView txtNroCuenta;
        TextView txtFecha;
        TextView txtTotal;
        TextView txtCuit;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void sort() {

    }

}
