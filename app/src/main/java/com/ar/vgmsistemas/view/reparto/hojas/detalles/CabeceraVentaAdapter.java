package com.ar.vgmsistemas.view.reparto.hojas.detalles;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.List;


public class CabeceraVentaAdapter extends ListItemAdapter<HojaDetalle> implements Filterable {
    Context context;
    List<HojaDetalle> hojasDetalles;

    public CabeceraVentaAdapter(Context context, List<HojaDetalle> hojasDetalle) {
        super(context, R.layout.lyt_cabecera_venta_hoja_detalle, hojasDetalle);
        this.context = context;
        this.hojasDetalles = hojasDetalle;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(this.getContext(), R.layout.lyt_cabecera_venta_hoja_detalle, null);
            holder.tvDoc = convertView.findViewById(R.id.tvDoc);
            holder.tvLetra = convertView.findViewById(R.id.tvLetra);
            holder.tvPtoVta = convertView.findViewById(R.id.tvPtoVta);
            holder.tvNum = convertView.findViewById(R.id.tvNum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HojaDetalle hojaDetalle = hojasDetalles.get(position);
        holder.tvDoc.setText(hojaDetalle.getId().getIdFcnc());
        holder.tvLetra.setText(hojaDetalle.getId().getIdTipoab());
        holder.tvPtoVta.setText(String.valueOf(hojaDetalle.getId().getIdPtovta()));
        holder.tvNum.setText(String.valueOf(hojaDetalle.getId().getIdNumdoc()));
        return convertView;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void sort() {

    }

    private static class ViewHolder {
        TextView tvDoc;
        TextView tvLetra;
        TextView tvPtoVta;
        TextView tvNum;

    }
}
