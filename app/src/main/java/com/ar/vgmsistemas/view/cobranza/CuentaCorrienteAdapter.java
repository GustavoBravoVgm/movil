package com.ar.vgmsistemas.view.cobranza;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.CuentaCorrienteBo;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.SelectableListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class CuentaCorrienteAdapter extends SelectableListItemAdapter<CuentaCorriente> {

    private ArrayList<CuentaCorriente> _checkedItems;

    public CuentaCorrienteAdapter(Context context, int textViewResourceId, List<CuentaCorriente> objects) {
        super(context, textViewResourceId, objects);
        this._checkedItems = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_cuenta_corriente_item, null);
            holder = new ViewHolder();
            holder.lblDocumento = convertView.findViewById(R.id.lblDocumentoValue);//(TextView)
            holder.lblFecha = convertView.findViewById(R.id.lblFechaValue);//(TextView)
            holder.lblNumero = convertView.findViewById(R.id.lblNumeroDocumentoValue);//(TextView)
            holder.lblPuntoVenta = convertView.findViewById(R.id.lblPuntoVentaValue);//(TextView)
            holder.lblCondicionVenta = convertView.findViewById(R.id.lblCondicionVentaValue);//(TextView)
            holder.lblTotal = convertView.findViewById(R.id.lblTotalValue);//(TextView)
            holder.lblSaldo = convertView.findViewById(R.id.lblSaldoValue);//(TextView)
            holder.lblVendedor = convertView.findViewById(R.id.lblVendedorValue);//(TextView)
            holder.lytDescuento = convertView.findViewById(R.id.lytDescuento);//(LinearLayout)
            holder.lblComercio = convertView.findViewById(R.id.lblComercioValue);//(TextView)
            holder.lblAutorizaCtaCteMovilRep = convertView.findViewById(R.id.lblAutorizaCtaCte);//(TextView)
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CuentaCorriente cuentaCorriente = items.get(position);//(CuentaCorriente)
        if (cuentaCorriente != null) {
            RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
            CuentaCorrienteBo ctaCteBo = new CuentaCorrienteBo(repoFactory);

            holder.lblDocumento.setText(cuentaCorriente.getId().getIdDocumento());

            holder.lblFecha.setText(Formatter.formatDate(cuentaCorriente.getFechaVenta()));
            holder.lblNumero.setText(String.valueOf(cuentaCorriente.getNumdoc()));
            holder.lblComercio.setText(String.valueOf(cuentaCorriente.getCliente().getId().getIdComercio()));
            holder.lblPuntoVenta.setText(String.valueOf(cuentaCorriente.getId().getPuntoVenta()));
            holder.lblCondicionVenta.setText(cuentaCorriente.getCondicionVenta().getDescripcion());
            holder.lblTotal.setText(Formatter.formatMoney(cuentaCorriente.getTotalCuota()));
            holder.lblSaldo.setText(Formatter.formatMoney(cuentaCorriente.calcularSaldo()));
            setBackground(position, convertView);

            long idVendedor = cuentaCorriente.getVendedor().getId();
            holder.lblVendedor.setText(String.valueOf(idVendedor));
            holder.lytDescuento.setVisibility(View.INVISIBLE);
            if (!ctaCteBo.mostrarMsjComprobanteAutorizado(cuentaCorriente)) {
                holder.lblAutorizaCtaCteMovilRep.setVisibility(View.INVISIBLE);
            }

        }
        return convertView;
    }

    public List<CuentaCorriente> getCheckedItems() {
        return this._checkedItems;
    }


    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void sort() {

    }

    private static class ViewHolder {
        TextView lblDocumento;
        TextView lblPuntoVenta;
        TextView lblFecha;
        TextView lblNumero;
        TextView lblCondicionVenta;
        TextView lblTotal;
        TextView lblSaldo;
        /*CheckBox chkSeleccionado;*/
        TextView lblVendedor;
        TextView lblComercio;
        TextView lblAutorizaCtaCteMovilRep;
        LinearLayout lytDescuento;
    }

    public void setChecked(CuentaCorriente cuentaCorriente) {
        if (this._checkedItems.contains(cuentaCorriente)) {
            this._checkedItems.remove(cuentaCorriente);
        } else {
            this._checkedItems.add(cuentaCorriente);
        }
    }

}
