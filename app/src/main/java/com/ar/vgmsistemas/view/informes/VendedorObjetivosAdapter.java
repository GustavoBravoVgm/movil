package com.ar.vgmsistemas.view.informes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.VendedorObjetivo;

import java.util.List;

public class VendedorObjetivosAdapter extends ArrayAdapter<VendedorObjetivo> {
    private List<VendedorObjetivo> mList;
    private Context mContext;

    public VendedorObjetivosAdapter(Context context, int resource, List<VendedorObjetivo> objects) {
        super(context, resource, objects);
        mList = objects;
        mContext = context;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public VendedorObjetivo getItem(int position) {
        return mList.get(position);
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

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VendedorObjetivo vendedorObjetivo = mList.get(position);

        holder.tvIdObjetivo.setText(String.valueOf(vendedorObjetivo.getIdObjetivo()));
        holder.tvCategoria.setText(VendedorObjetivo.getCategoria(vendedorObjetivo.getTiCategoria()));
        holder.tvProveedor.setText(vendedorObjetivo.getProveedor().getDeProveedor());
        holder.tvTipoObjetivo.setText(VendedorObjetivo.getCategoria(vendedorObjetivo.getTiCategoria()));

        return convertView;
    }

    private class ViewHolder {
        TextView tvIdObjetivo;
        TextView tvTipoObjetivo;
        TextView tvProveedor;
        TextView tvCategoria;
    }
}
