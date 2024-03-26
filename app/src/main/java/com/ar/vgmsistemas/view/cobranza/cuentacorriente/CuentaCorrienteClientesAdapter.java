package com.ar.vgmsistemas.view.cobranza.cuentacorriente;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.utils.TextViewUtils;

import java.util.ArrayList;
import java.util.List;

public class CuentaCorrienteClientesAdapter extends ArrayAdapter<Cliente> {
    private List<Cliente> mListCC;
    private List<Cliente> mListAllCC;
    private ClienteFilter filter;
    private int posFilter;
    private String filterString;

    public CuentaCorrienteClientesAdapter(Context context, int textViewResourceId, List<Cliente> listCuentaCorriente) {
        super(context, textViewResourceId, listCuentaCorriente);
        mListAllCC = mListCC = listCuentaCorriente;
        posFilter = PreferenciaBo.getInstance().getPreferencia(getContext()).getBusquedaPreferidaCliente();
        filterString = "";
    }

    @Override
    public Cliente getItem(int position) {
        // TODO Auto-generated method stub
        return mListCC.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mListCC.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.lyt_cc_cliente_item, null);
            holder.tvCodigo = (TextView) convertView.findViewById(R.id.lblCodigo);
            holder.tvNombre = (TextView) convertView.findViewById(R.id.lblApellido);
            holder.tvDomicilio = (TextView) convertView.findViewById(R.id.lblDomicilio);
            holder.tvSaldo = (TextView) convertView.findViewById(R.id.tvSaldoTotal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Cliente cliente = mListCC.get(position);
        switch (posFilter) {

            case Preferencia.RAZON_SOCIAL:
                TextViewUtils.highlightText(holder.tvNombre, cliente.getRazonSocial(), filterString);
                holder.tvDomicilio.setText(cliente.getDomicilio());
                holder.tvCodigo.setText(cliente.getId().toString());
                break;

            case Preferencia.DOMICILIO:
                holder.tvNombre.setText(cliente.getRazonSocial());
                TextViewUtils.highlightText(holder.tvDomicilio, cliente.getDomicilio(), filterString);
                holder.tvCodigo.setText(cliente.getId().toString());
                break;

            default:
                TextViewUtils.highlightText(holder.tvCodigo, cliente.getId().toString(), filterString);
                holder.tvNombre.setText(cliente.getRazonSocial());
                holder.tvDomicilio.setText(cliente.getDomicilio());
                break;
        }

        holder.tvSaldo.setText(cliente.getTotalSaldoCCString());
        return convertView;
    }

    private static class ViewHolder {
        TextView tvNombre;
        TextView tvCodigo;
        TextView tvDomicilio;
        TextView tvSaldo;
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new ClienteFilter();
        }
        return filter;
    }

    private class ClienteFilter extends Filter {
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            mListCC = (List<Cliente>) results.values;
            posFilter = PreferenciaBo.getInstance().getPreferencia(getContext()).getBusquedaPreferidaCliente();
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            List<Cliente> aux = new ArrayList<>();
            filterString = (prefix == null) ? "" : prefix.toString();

            for (int index = 0; index < mListAllCC.size(); index++) {
                Cliente cliente = mListAllCC.get(index);
                String datos = "";

				/*int filtroPreferidoCliente = PreferenciaBo.getInstance().getPreferencia(getContext()).getFiltroPreferidoCliente();
								
								
				if ((filtroPreferidoCliente == Preferencia.FILTRO_CLIENTES_TODOS)
						|| (filtroPreferidoCliente == Preferencia.FILTRO_CLIENTES_VISITADOS && (cliente.getCantidadVentas() > 0 || cliente.getCantidadNoPedidos() > 0))
						|| (filtroPreferidoCliente == Preferencia.FILTRO_CLIENTES_SIN_VISITAS && (cliente.getCantidadVentas() == 0 && cliente.getCantidadNoPedidos() == 0))						
						)*/
                {

                    switch (PreferenciaBo.getInstance().getPreferencia(getContext()).getBusquedaPreferidaCliente()) {
                        case Preferencia.CODIGO:
                            datos = cliente.getId().toString();
                            break;
                        case Preferencia.DOMICILIO:
                            datos = cliente.getDomicilio();
                            break;
                        case Preferencia.RAZON_SOCIAL:
                            datos = cliente.getRazonSocial();
                            break;

                    }

                    if (prefix == null)
                        aux.add(cliente);
                    else {
                        String filtro = filterString.toLowerCase();
                        datos = datos.toLowerCase();
                        if (datos.contains(filtro))
                            aux.add(cliente);
                    }
                }
            }
            results.values = aux;
            results.count = aux.size();
            return results;

        }
    }

}
