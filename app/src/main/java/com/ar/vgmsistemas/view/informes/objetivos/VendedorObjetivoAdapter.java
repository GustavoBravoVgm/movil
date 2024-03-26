package com.ar.vgmsistemas.view.informes.objetivos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.VendedorObjetivoBo;
import com.ar.vgmsistemas.entity.VendedorObjetivo;

import java.util.ArrayList;
import java.util.List;

public class VendedorObjetivoAdapter extends ArrayAdapter<VendedorObjetivo> {
    private List<VendedorObjetivo> mItems, mAllItems;
    private Context mContext;
    private VendedorObjetivoFilter filter;
    private int filterCategoria;
    private int filterEstado;
    private int filterTipo;

    public VendedorObjetivoAdapter(Context context, int resource, List<VendedorObjetivo> objects) {
        super(context, resource, objects);

        mItems = objects;
        mAllItems = objects;
        mContext = context;
        getFilter().filter("");
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItems.size();
    }

    @Override
    public VendedorObjetivo getItem(int position) {
        // TODO Auto-generated method stub
        return mItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.lyt_objetivo_item, null);

            holder = new ViewHolder();
            holder.tvIdObjetivo = (TextView) convertView.findViewById(R.id.tvIdObjetivo);
            holder.tvTipoObjetivo = (TextView) convertView.findViewById(R.id.tvTipoObjetivo);
            holder.tvProveedor = (TextView) convertView.findViewById(R.id.tvProveedor);
            holder.tvCategoria = (TextView) convertView.findViewById(R.id.tvCategoria);
            holder.cbCompleted = (CheckBox) convertView.findViewById(R.id.cbCompleted);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VendedorObjetivo vendedorObjetivo = getItem(position);

        holder.tvIdObjetivo.setText(String.valueOf(vendedorObjetivo.getIdObjetivo()));
        holder.tvCategoria.setText(VendedorObjetivo.getCategoria(vendedorObjetivo.getTiCategoria()));
        holder.tvProveedor.setText(vendedorObjetivo.getProveedor().getDeProveedor());
        holder.tvTipoObjetivo.setText(VendedorObjetivo.getTipo(vendedorObjetivo.getTiObjetivo()));
        holder.cbCompleted.setChecked(VendedorObjetivoBo.isCompleted(vendedorObjetivo));
        return convertView;
    }

    public void setFilterEstado(int estado) {
        filterEstado = estado;
    }

    public void setFilterCategoria(int categoria) {
        filterCategoria = categoria;
    }

    public void setFilterTipo(int tipo) {
        filterTipo = tipo;
    }

    private class ViewHolder {
        TextView tvIdObjetivo;
        TextView tvTipoObjetivo;
        TextView tvProveedor;
        TextView tvCategoria;
        CheckBox cbCompleted;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new VendedorObjetivoFilter();
        }
        return filter;
    }

    private class VendedorObjetivoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<VendedorObjetivo> aux = new ArrayList<VendedorObjetivo>();

            for (VendedorObjetivo objetivo : mAllItems) {
                boolean add = true;
                switch (filterCategoria) {
                    case VendedorObjetivo.CATEGORIA_COBERTURA:
                        if (!(objetivo.getTiCategoria() == VendedorObjetivo.CATEGORIA_COBERTURA))
                            add = !add;
                        break;
                    case VendedorObjetivo.CATEGORIA_GENERAL:
                        if (!(objetivo.getTiCategoria() == VendedorObjetivo.CATEGORIA_GENERAL))
                            add = !add;
                        break;
                }

                if (!add)
                    continue;

                switch (filterEstado) {
                    case VendedorObjetivo.LOGRADO:
                        if (!VendedorObjetivoBo.isCompleted(objetivo))
                            add = !add;
                        break;
                    case VendedorObjetivo.NO_LOGRADO:
                        if (VendedorObjetivoBo.isCompleted(objetivo))
                            add = !add;
                        break;
                }

                if (!add)
                    continue;

                switch (filterTipo) {
                    case VendedorObjetivo.TIPO_NEGOCIO:
                        if (!(objetivo.getTiObjetivo() == VendedorObjetivo.TIPO_NEGOCIO))
                            add = !add;
                        break;
                    case VendedorObjetivo.TIPO_SEGMENTO:
                        if (!(objetivo.getTiObjetivo() == VendedorObjetivo.TIPO_SEGMENTO))
                            add = !add;
                        break;
                    case VendedorObjetivo.TIPO_SUBRUBRO:
                        if (!(objetivo.getTiObjetivo() == VendedorObjetivo.TIPO_SUBRUBRO))
                            add = !add;
                        break;
                    case VendedorObjetivo.TIPO_PROVEEDOR:
                        if (!(objetivo.getTiObjetivo() == VendedorObjetivo.TIPO_PROVEEDOR))
                            add = !add;
                        break;
                    case VendedorObjetivo.TIPO_ARTICULO:
                        if (!(objetivo.getTiObjetivo() == VendedorObjetivo.TIPO_ARTICULO))
                            add = !add;
                        break;
                    case VendedorObjetivo.TIPO_LINEA:
                        if (!(objetivo.getTiObjetivo() == VendedorObjetivo.TIPO_LINEA))
                            add = !add;
                        break;
                }

                if (add) aux.add(objetivo);


            }
            results.values = aux;
            int count = aux.size();
            results.count = count;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mItems = (List<VendedorObjetivo>) results.values;
            notifyDataSetChanged();
        }

    }
}
