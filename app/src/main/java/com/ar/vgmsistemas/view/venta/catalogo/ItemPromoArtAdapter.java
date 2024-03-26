package com.ar.vgmsistemas.view.venta.catalogo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.AccionesComDetalle;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.List;
import java.util.Locale;

public class ItemPromoArtAdapter extends ListItemAdapter<AccionesComDetalle> {

    public ItemPromoArtAdapter(Context context, List<AccionesComDetalle> objects) {
        super(context, R.layout.lyt_item_promo_art, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_item_promo_art, null);
            holder = new ViewHolder();
            holder.txtPromoDesde = (TextView) convertView.findViewById(R.id.lblPromoDesde);
            holder.txtPromoHasta = (TextView) convertView.findViewById(R.id.lblPromoHasta);
            holder.txtPromoDto = (TextView) convertView.findViewById(R.id.lblPromoDto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AccionesComDetalle accComDet = items.get(position);
        if (accComDet != null) {
            holder.txtPromoDesde.setText(String.valueOf(accComDet.getRgLimiteInf()));
            holder.txtPromoHasta.setText(String.valueOf(accComDet.getRgLimiteSup()));
            String dtoPromoStr = String.format(Locale.GERMAN, "%.2f", accComDet.getTaDto() * 100) + " %";
            holder.txtPromoDto.setText(dtoPromoStr);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtPromoDesde;
        TextView txtPromoHasta;
        TextView txtPromoDto;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void sort() {

    }

}
