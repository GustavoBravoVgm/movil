package com.ar.vgmsistemas.view.informes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.entity.VendedorObjetivoDetalle;

import java.util.List;

public class ObjetivosDetalleAdapter extends ArrayAdapter<VendedorObjetivoDetalle> {
    private int mTipoObjetivo;
    private List<VendedorObjetivoDetalle> mList;
    private String unidad;

    public ObjetivosDetalleAdapter(Context context, int resource,
                                   List<VendedorObjetivoDetalle> objects, int tipoObjetivo) {
        super(context, resource, objects);
        mList = objects;
        mTipoObjetivo = tipoObjetivo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.lyt_objetivos_detalle_item, null);
            holder = new ViewHolder();
            holder.tvMain = (TextView) convertView.findViewById(R.id.tvMain);
            holder.lblMain = (TextView) convertView.findViewById(R.id.lblMain);
            holder.tvSegmento = (TextView) convertView.findViewById(R.id.tvSegmento);

            holder.tvSubrubro = (TextView) convertView.findViewById(R.id.tvSubrubro);
            holder.tvObjetivo = (TextView) convertView.findViewById(R.id.tvObjetivo);
            holder.tvLogrado = (TextView) convertView.findViewById(R.id.tvLogrado);
            setVisibilityViews(holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VendedorObjetivoDetalle detalle = getItem(position);

        switch (mTipoObjetivo) {
            case VendedorObjetivo.TIPO_ARTICULO:
                holder.tvMain.setText(detalle.getArticulo().getDescripcion());
                break;
            case VendedorObjetivo.TIPO_PROVEEDOR:
                holder.tvMain.setText(detalle.getProveedor().getDeProveedor());
                break;
            case VendedorObjetivo.TIPO_LINEA:
                holder.tvMain.setText(detalle.getLinea().getDescripcion());
                break;
            case VendedorObjetivo.TIPO_NEGOCIO:
                holder.tvMain.setText(detalle.getNegocio().getDescripcion());
                break;
            case VendedorObjetivo.TIPO_SEGMENTO:
                holder.tvMain.setText(detalle.getNegocio().getDescripcion());
                holder.tvSegmento.setText(detalle.getRubro().getDescripcion());
                break;
            case VendedorObjetivo.TIPO_SUBRUBRO:
                holder.tvMain.setText(detalle.getNegocio().getDescripcion());
                holder.tvSegmento.setText(detalle.getRubro().getDescripcion());
                holder.tvSubrubro.setText(detalle.getSubrubro().getDescripcion());
                break;
        }

        holder.tvObjetivo.setText(getGoal(detalle));
        String logradoStr = detalle.getCaLograda() + unidad;
        holder.tvLogrado.setText(logradoStr);
        return convertView;
    }

    private String getGoal(VendedorObjetivoDetalle detalle) {
        if (detalle.getCaArticulos() != null) {
            unidad = " unidades";
            return detalle.getCaArticulos().toString() + unidad;
        }

        unidad = "%";
        return detalle.getTaCobertura() + unidad;

    }

    @Override
    public VendedorObjetivoDetalle getItem(int position) {
        return mList.get(position);
    }

    private void setVisibilityViews(ViewHolder holder) {
        switch (mTipoObjetivo) {
            case VendedorObjetivo.TIPO_NEGOCIO:
                holder.lblMain.setText("Negocio");
                holder.tvSegmento.setVisibility(View.GONE);
                holder.tvSubrubro.setVisibility(View.GONE);
                break;

            case VendedorObjetivo.TIPO_SEGMENTO:
                holder.lblMain.setText("Negocio");

                holder.tvSubrubro.setVisibility(View.GONE);
                break;
            case VendedorObjetivo.TIPO_SUBRUBRO:
                holder.lblMain.setText("Negocio");

                break;
            case VendedorObjetivo.TIPO_PROVEEDOR:
                holder.lblMain.setText("Proveedor");
                holder.tvSegmento.setVisibility(View.GONE);
                holder.tvSubrubro.setVisibility(View.GONE);
                break;
            case VendedorObjetivo.TIPO_LINEA:
                holder.lblMain.setText("Linea");
                holder.tvSegmento.setVisibility(View.GONE);
                holder.tvSubrubro.setVisibility(View.GONE);
                break;
            case VendedorObjetivo.TIPO_ARTICULO:
                holder.lblMain.setText("Articulo");
                holder.tvSegmento.setVisibility(View.GONE);
                holder.tvSubrubro.setVisibility(View.GONE);
                break;
        }
    }

    private class ViewHolder {
        TextView tvMain;
        TextView lblMain;
        TextView tvSegmento;
        TextView tvSubrubro;
        TextView tvObjetivo;
        TextView tvLogrado;
    }

}
