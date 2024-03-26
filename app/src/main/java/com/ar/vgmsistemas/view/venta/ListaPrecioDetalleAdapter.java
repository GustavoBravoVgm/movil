package com.ar.vgmsistemas.view.venta;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ar.vgmsistemas.bo.ListaPrecioDetalleBo;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;

import java.util.List;

public class ListaPrecioDetalleAdapter extends ArrayAdapter<ListaPrecioDetalle> {
    private List<Boolean> mListValid;
    private List<ListaPrecioDetalle> mListaPrecioDetalles;
    private static final String LBL_NO_VALID = " (NO VALIDA)";
    private static final String LBL_NO_PRICE = " (SIN PRECIO)";

    public ListaPrecioDetalleAdapter(Context context, int resource, List<ListaPrecioDetalle> listaPrecioDetalles, List<Boolean> listValid) {
        super(context, resource, listaPrecioDetalles);
        mListValid = listValid;
        mListaPrecioDetalles = listaPrecioDetalles;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getDropDownView(position, null, parent);
        String nameList = mListaPrecioDetalles.get(position).getListaPrecio().getDescripcion().trim();

        ((TextView) v).setTextColor(getContext().getResources().getColor(com.ar.vgmsistemas.R.color.base_color));
        ((TextView) v).setText(nameList);
        ((TextView) v).setError(null);

        if (!mListValid.get(position)) {
            ((TextView) v).setTextColor(Color.GRAY);
            ((TextView) v).setText(nameList + LBL_NO_VALID);
        }
        if (!ListaPrecioDetalleBo.havePrice(mListaPrecioDetalles.get(position))) {
            ((TextView) v).setTextColor(Color.GRAY);
            ((TextView) v).setText(nameList + LBL_NO_PRICE);
        }
        return v;
    }

    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return mListValid.get(position);
    }
}
