package com.ar.vgmsistemas.view.cobranza.cheque;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Banco;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class BancoAdapter extends ListItemAdapter<Banco> {

    private BancoFilter filter;

    public BancoAdapter(Context context, List<Banco> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.simple_spinner_item, null);
            holder = new ViewHolder();
            holder.txtDescripcion = (TextView) convertView.findViewById(R.id.lblSimpleSpinnerItemText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Banco banco = items.get(position);
        if (banco != null) {
            holder.txtDescripcion.setText(banco.toString());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtDescripcion;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new BancoFilter();
        }
        return filter;
    }

    @Override
    public void sort() {

    }

    private class BancoFilter extends Filter {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            items = (List<Banco>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            List<Banco> aux = new ArrayList<>();

            if (prefix != null && prefix.toString().length() > 0) {

                for (int index = 0; index < allItems.size(); index++) {
                    Banco banco = (Banco) allItems.get(index);
                    String filtro = prefix.toString();
                    filtro = filtro.toLowerCase();
                    String bancoString = banco.getDenominacion().toLowerCase();
                    if (bancoString.contains(filtro)) {
                        aux.add(banco);
                    }
                }
                results.values = aux;
                results.count = aux.size();
            } else {
                synchronized (allItems) {
                    results.values = allItems;
                    results.count = allItems.size();
                }
            }
            return results;
        }
    }

}
