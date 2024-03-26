package com.ar.vgmsistemas.view.informes.objetivos;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.VendedorObjetivoDetalleBo;
import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.entity.VendedorObjetivoDetalle;
import com.ar.vgmsistemas.view.CustomProgressBar;

import java.util.List;

public class ObjetivoDetalleAdapter extends ArrayAdapter<VendedorObjetivoDetalle> {
    private VendedorObjetivo mVendedorObjetivo;
    private List<VendedorObjetivoDetalle> mList;
    private String unidad;

    public ObjetivoDetalleAdapter(Context context, int resource,
                                  List<VendedorObjetivoDetalle> objects, VendedorObjetivo vendedorObjetivo) {
        super(context, resource, objects);
        mList = objects;
        mVendedorObjetivo = vendedorObjetivo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
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
            holder.pbLogrado = (CustomProgressBar) convertView.findViewById(R.id.pbLogrado);
            holder.tvRestante = (TextView) convertView.findViewById(R.id.tvRestante);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VendedorObjetivoDetalle detalle = getItem(position);
        setVisibilityViews(convertView);
        switch (mVendedorObjetivo.getTiObjetivo()) {
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
        String logradoStr = detalle.getCaLograda(mVendedorObjetivo.getTiCategoria()) + unidad;
        holder.tvLogrado.setText(logradoStr);

        holder.pbLogrado.getProgressDrawable().setColorFilter(VendedorObjetivoDetalleBo.getColorLogrado(detalle, mVendedorObjetivo.getTiCategoria(), getContext()), Mode.SRC_IN);
        holder.tvLogrado.setTextColor(VendedorObjetivoDetalleBo.getColorLogrado(detalle, mVendedorObjetivo.getTiCategoria(), getContext()));
        holder.pbLogrado.setProgress(VendedorObjetivoDetalleBo.getProgress(detalle, mVendedorObjetivo.getTiCategoria()));
        holder.pbLogrado.setMark(detalle.getObjetivo(mVendedorObjetivo.getTiCategoria()).intValue());
        holder.tvRestante.setText(String.valueOf(detalle.getCaRestante()));
        holder.tvRestante.setTextColor(VendedorObjetivoDetalleBo.getColorLogrado(detalle, mVendedorObjetivo.getTiCategoria(), getContext()));
        return convertView;
    }

    private String getGoal(VendedorObjetivoDetalle detalle) {
        if (mVendedorObjetivo.getTiCategoria() == VendedorObjetivo.CATEGORIA_GENERAL) {
            unidad = " unidades";
        }
        unidad = "%";
        return detalle.getObjetivo(mVendedorObjetivo.getTiCategoria()) + unidad;

    }

    @Override
    public VendedorObjetivoDetalle getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    private void setVisibilityViews(View view) {
        switch (mVendedorObjetivo.getTiObjetivo()) {
            case VendedorObjetivo.TIPO_NEGOCIO:
                ((ViewHolder) view.getTag()).lblMain.setText("Negocio: ");
                view.findViewById(R.id.llSegmento).setVisibility(View.GONE);
                view.findViewById(R.id.llSubrubro).setVisibility(View.GONE);

                break;

            case VendedorObjetivo.TIPO_SEGMENTO:
                ((ViewHolder) view.getTag()).lblMain.setText("Negocio: ");

                view.findViewById(R.id.llSubrubro).setVisibility(View.GONE);
                break;
            case VendedorObjetivo.TIPO_SUBRUBRO:
                ((ViewHolder) view.getTag()).lblMain.setText("Negocio: ");

                break;
            case VendedorObjetivo.TIPO_PROVEEDOR:
                ((ViewHolder) view.getTag()).lblMain.setText("Proveedor: ");
                view.findViewById(R.id.llSegmento).setVisibility(View.GONE);
                view.findViewById(R.id.llSubrubro).setVisibility(View.GONE);
                break;
            case VendedorObjetivo.TIPO_LINEA:
                ((ViewHolder) view.getTag()).lblMain.setText("Linea: ");
                view.findViewById(R.id.llSegmento).setVisibility(View.GONE);
                view.findViewById(R.id.llSubrubro).setVisibility(View.GONE);
                break;
            case VendedorObjetivo.TIPO_ARTICULO:
                ((ViewHolder) view.getTag()).lblMain.setText("Articulo: ");
                view.findViewById(R.id.llSegmento).setVisibility(View.GONE);
                view.findViewById(R.id.llSubrubro).setVisibility(View.GONE);
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
        CustomProgressBar pbLogrado;
        TextView tvRestante;
    }


}
