package com.ar.vgmsistemas.view.objetivoVenta;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ListaPrecioDetalleBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.ObjetivoVenta;
import com.ar.vgmsistemas.entity.key.PkListaPrecioDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.List;

public class ObjetivoVentaAdapter extends ArrayAdapter<ObjetivoVenta> {

    private final List<ObjetivoVenta> _objetivosVenta;
    private final Empresa _empresa;

    public ObjetivoVentaAdapter(Context context, int textViewResourceId, List<ObjetivoVenta> objects, Empresa empresa) {
        super(context, textViewResourceId, objects);
        this._objetivosVenta = objects;
        this._empresa = empresa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final ObjetivoVenta objetivoVenta = _objetivosVenta.get(position);

        if (convertView == null) {

            convertView = View.inflate(this.getContext(), R.layout.lyt_objetivo_venta_item, null);
            holder = new ViewHolder();
            //TextView´s
            holder.lblDescripcionArticulo = convertView.findViewById(R.id.lblDescripcionArticuloObjetivo);
            holder.lblCodigoArticulo = convertView.findViewById(R.id.lblCodigoObjetivoValue);
            holder.lblStockArticulo = convertView.findViewById(R.id.lblStockObjetivoValue);
            holder.lblCantidadObjetivo = convertView.findViewById(R.id.lblObjetivo);
            holder.lblCantidadVendida = convertView.findViewById(R.id.lblCantidadVendida);
            holder.lblCantidadAVender = convertView.findViewById(R.id.lblCantidadAVender);
            holder.lblValueUnidadPorBulto = convertView.findViewById(R.id.lblValueUnidadPorBulto);
            // EditText
            holder.txtDescuento = convertView.findViewById(R.id.txtDescuento);

            holder.txtDescuento.setTag(objetivoVenta);
            holder.txtDescuento.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    txtDescuentoOnSelected(holder, s, objetivoVenta);
                    if (!s.toString().equals("")) {
                        double tasaDescuento = Double.parseDouble(s.toString());
                        ((ObjetivoVenta) holder.txtDescuento.getTag()).setTasaDescuento(tasaDescuento);
                    } else {
                        ((ObjetivoVenta) holder.txtDescuento.getTag()).setTasaDescuento(0);
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });
            // EditText
            holder.txtBultosAVender = convertView.findViewById(R.id.txtBultosAVender);
            holder.txtBultosAVender.setTag(objetivoVenta);
            holder.txtBultosAVender.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().equals("")) {
                        int bultosAVender = Integer.parseInt(s.toString());
                        ((ObjetivoVenta) holder.txtBultosAVender.getTag()).setBultosAVender(bultosAVender);
                    } else {
                        ((ObjetivoVenta) holder.txtBultosAVender.getTag()).setBultosAVender(0);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            // EditText txtUnidades A vender
            holder.txtUnidadesAVender = convertView.findViewById(R.id.txtUnidadesAVender);
            holder.txtUnidadesAVender.setTag(objetivoVenta);
            holder.txtUnidadesAVender.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (!s.toString().equals("")) {
                        double cantidadAVender = Double.parseDouble(s.toString());
                        ((ObjetivoVenta) holder.txtUnidadesAVender.getTag()).setCantidadAVender(cantidadAVender);
                    } else {
                        ((ObjetivoVenta) holder.txtUnidadesAVender.getTag()).setCantidadAVender(0D);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            // EditText
            holder.txtPrecio = convertView.findViewById(R.id.txtPrecioUnitario);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.txtUnidadesAVender.setTag(objetivoVenta);
            holder.txtBultosAVender.setTag(objetivoVenta);
            holder.txtDescuento.setTag(objetivoVenta);
        }

        if (objetivoVenta != null) {
            String descripcion = objetivoVenta.getArticulo().getDescripcion();
            long idArticulo = objetivoVenta.getArticulo().getId();
            double stock = objetivoVenta.getArticulo().getStock();
            double caObjetivo = objetivoVenta.getCantidad();
            double caVendida = objetivoVenta.getCantidadVendida();
            double cantidadUnidadesPorBulto = objetivoVenta.getArticulo().getUnidadPorBulto();
            double precioUnitario = getPrecioUnitario(objetivoVenta.getArticulo(), objetivoVenta.getCliente());

            holder.lblDescripcionArticulo.setText(descripcion);
            holder.lblCodigoArticulo.setText(String.valueOf(idArticulo));
            holder.lblStockArticulo.setText(String.valueOf(stock));
            holder.lblCantidadObjetivo.setText(String.valueOf(caObjetivo));
            holder.lblCantidadVendida.setText(String.valueOf(caVendida));
            holder.lblCantidadAVender.setText(String.valueOf(caObjetivo - caVendida));
            holder.lblValueUnidadPorBulto.setText(String.valueOf(cantidadUnidadesPorBulto));
            holder.txtPrecio.setText(Formatter.formatMoney(precioUnitario));

            if (!this._empresa.getIsDescuentoActivo().equals("S")) {
                holder.txtDescuento.setKeyListener(null);
                holder.txtDescuento.setEnabled(false);
            }

            int bultosAVender = ((ObjetivoVenta) holder.txtBultosAVender.getTag()).getBultosAVender();
            holder.txtBultosAVender.setText(String.valueOf(bultosAVender));
            double cantidadAVender = ((ObjetivoVenta) holder.txtUnidadesAVender.getTag()).getCantidadAVender();
            holder.txtUnidadesAVender.setText(String.valueOf(cantidadAVender));
            double tasaDescuento = ((ObjetivoVenta) holder.txtDescuento.getTag()).getTasaDescuento();
            holder.txtDescuento.setText(String.valueOf(tasaDescuento));
        }
        return convertView;
    }

    public double getPrecioUnitario(Articulo articulo, Cliente cliente) {
        double precioUnitario = 0D;
        if (articulo != null) {
            try {
                PkListaPrecioDetalle id = new PkListaPrecioDetalle();
                id.setIdArticulo(articulo.getId());
                id.setIdLista(cliente.getListaPrecio().getId());
                RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
                ListaPrecioDetalleBo listaDetalleBo = new ListaPrecioDetalleBo(repoFactory);
                precioUnitario = listaDetalleBo.recoveryById(id).getPrecioFinal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return precioUnitario;
    }

    private void txtDescuentoOnSelected(ViewHolder holder, CharSequence s, ObjetivoVenta objetivoVenta) {
        double tasaDescuento = 0d;
        if (s.length() > 0) {
            String sDescuento = holder.txtDescuento.getText().toString();
            if (sDescuento.equals(".")) {
                sDescuento = "0.";
                holder.txtDescuento.setText(sDescuento);
                holder.txtDescuento.setSelection(sDescuento.length());
            }
            if (sDescuento.length() > 0) {
                tasaDescuento = Double.parseDouble(sDescuento);
            }
        }

        double precioUnitarioConDto = getPrecioUnitario(objetivoVenta.getArticulo(), objetivoVenta.getCliente()) * (1 - (tasaDescuento / 100));
        holder.txtPrecio.setText(Formatter.formatMoney(precioUnitarioConDto));
    }


    @Override
    public int getCount() {
        return _objetivosVenta.size();
    }

    private static class ViewHolder {
        TextView lblDescripcionArticulo;
        TextView lblCodigoArticulo;
        TextView lblStockArticulo;
        TextView lblCantidadObjetivo;
        TextView lblCantidadVendida;
        TextView lblCantidadAVender;
        TextView lblValueUnidadPorBulto;
        EditText txtBultosAVender;
        EditText txtUnidadesAVender;
        EditText txtDescuento;
        EditText txtPrecio;
    }
}
