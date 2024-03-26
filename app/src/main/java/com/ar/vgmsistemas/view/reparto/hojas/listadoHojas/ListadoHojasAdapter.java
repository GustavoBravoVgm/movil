package com.ar.vgmsistemas.view.reparto.hojas.listadoHojas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.view.SelectableListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListadoHojasAdapter extends SelectableListItemAdapter<Hoja> {
    //private List<MenuItem> items;
    public static final int FILTRO_NUMERO_HOJA = 0;
    public static final int CRITERIA_ID_HOJA = 0;
    private List<Hoja> _checkedItems;
    private HojasFilter mFilter;
    public static final int NUMERO_HOJA = 0;

    public ListadoHojasAdapter(Context context, int textViewResourceId, List<Hoja> objects) throws Exception {
        super(context, textViewResourceId, objects);
        items = objects;
        //this._checkedItems  = new ArrayList<Hoja>();
        this._checkedItems = objects;
        //items = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_hoja_reporte, null);
            holder = new ViewHolder();
            holder.tvHojaNumero = (TextView) convertView.findViewById(R.id.tvHojaNumero);
            holder.tvSucursal = (TextView) convertView.findViewById(R.id.tvSucursal);
            holder.tvEstado = (TextView) convertView.findViewById(R.id.tvEstado);
            holder.lytEstado = (LinearLayout) convertView.findViewById(R.id.llEstado);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (_checkedItems != null) {
            holder.tvSucursal.setText(String.valueOf(items.get(position).getIdSucursal()));
            holder.tvHojaNumero.setText(String.valueOf(items.get(position).getIdHoja()));
            switch (String.valueOf(items.get(position).getIsRendida())) {
                case Hoja.MOVIL:
                    holder.tvEstado.setText("MOVIL");
                    holder.lytEstado.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_item_anulado));
                    break;
                case Hoja.APROBADA:
                    holder.tvEstado.setText("APROBADA");
                    holder.lytEstado.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_item_aprobado));
                    break;
                case Hoja.PRERENDIDO:
                    holder.tvEstado.setText("PRERENDIDA");
                    holder.lytEstado.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_item_prerendido));
                    break;
                default:
                    break;
            }
            setBackground(position, convertView);
        }
        //convertView.setFocusable(false);

        return convertView;
    }

    private class ViewHolder {
        TextView tvHojaNumero;
        TextView tvSucursal;
        TextView tvEstado;
        LinearLayout lytEstado;

    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new HojasFilter();
        }
        return mFilter;
    }

    private class HojasFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            setFilteredString(prefix.toString());
            FilterResults results = new FilterResults();
            List<Hoja> aux = new ArrayList<Hoja>();

            for (int index = 0; index < allItems.size(); index++) {
                Hoja hoja = allItems.get(index);
                String datos = "";
                switch (getCampoBusqueda()) {
                    case FILTRO_NUMERO_HOJA:
                        datos = String.valueOf(hoja.getIdHoja());
                        break;
                }

                if (prefix == null)
                    aux.add(hoja);
                else {
                    String filtro = prefix.toString();
                    filtro = filtro.toLowerCase();
                    datos = datos.toLowerCase();
                    if (datos.contains(filtro))
                        aux.add(hoja);
                }
            }
            results.values = aux;
            results.count = aux.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (List<Hoja>) results.values;
            notifyDataSetChanged();

        }

    }

    @Override
    public void sort() {
        // TODO Auto-generated method stub
    }

    public void setChecked(Hoja Hoja) {
        if (this._checkedItems.contains(Hoja)) {
            this._checkedItems.remove(Hoja);
        } else {
            this._checkedItems.add(Hoja);
        }
    }

    public List<Hoja> getCheckedItems() {
        return this._checkedItems;
    }


}
