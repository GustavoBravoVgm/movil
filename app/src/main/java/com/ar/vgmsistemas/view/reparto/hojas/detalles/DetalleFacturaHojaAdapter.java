package com.ar.vgmsistemas.view.reparto.hojas.detalles;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaBo;
import com.ar.vgmsistemas.bo.HojaDetalleBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.bo.VentaDetalleBo;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.KeyboardUtils;
import com.ar.vgmsistemas.utils.Matematica;
import com.ar.vgmsistemas.utils.TextViewUtils;
import com.ar.vgmsistemas.view.SelectableListItemAdapter;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.DevolucionDialogFragment.DevolucionListener;

import java.util.ArrayList;
import java.util.List;

public class DetalleFacturaHojaAdapter extends SelectableListItemAdapter<VentaDetalle> {
    private FragmentManager mFragmentManager;
    private SparseBooleanArray mDeletedItems;
    // private List<VentaDetalle> devoluciones;
    public static final int FILTER_DESCRIPCION = 1;
    public static final int FILTER_CODIGO = 0;
    private List<VentaDetalle> detalles;
    private HojaDetalle hojaDetalle;
    private boolean isEnviado = false;

    private DetalleFacturaListener mListener;
    private DetalleFacturaFilter mFilter;
    //BO´s
    private VentaDetalleBo mVentaDetalleBo;
    private HojaBo mHojaBo;
    //DAO
    RepositoryFactory _repoFactory;


    public DetalleFacturaHojaAdapter(Context context, int resource, HojaDetalle _hojaDetalle, List<VentaDetalle> objects, FragmentManager manager,
                                     DetalleFacturaListener listener) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
        detalles = objects;
        mFragmentManager = manager;
        mListener = listener;
        hojaDetalle = _hojaDetalle;
        this._repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        HojaDetalleBo mHojaDetalleBo = new HojaDetalleBo(_repoFactory);
        mHojaBo = new HojaBo(_repoFactory);
        mVentaDetalleBo = new VentaDetalleBo(_repoFactory);
        try {
            isEnviado = mHojaDetalleBo.isEnviado(hojaDetalle);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        selectItemsSinCredito();
        // devoluciones = new ArrayList<VentaDetalle>();
        mDeletedItems = new SparseBooleanArray();
        setCampoBusqueda(FILTER_DESCRIPCION);
    }

    private void selectItemsSinCredito() {
        if (!isEnviado) {
            for (int i = 0; i < detalles.size(); i++) {
                getSelectedItems().put(i,
                        !(detalles.get(i).getUnidadesDevueltas() != 0 || detalles.get(i).getBultosDevueltos() != 0));
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        boolean isCreditos = false;

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.lyt_hoja_detalle_item_factura, null);
            holder.tvArticulo = convertView.findViewById(R.id.tvArticulo);//(TextView)
            holder.tvCodEmpresa = convertView.findViewById(R.id.tvCodEmpresa);//(TextView)
            holder.tvPrecioUnidad = convertView.findViewById(R.id.tvPrecioUnitario);//(TextView)
            holder.tvUnidades = convertView.findViewById(R.id.tvUnidades);//(TextView)
            holder.tvBulto = convertView.findViewById(R.id.tvBulto);//(TextView)
            holder.tvSubtotal = convertView.findViewById(R.id.tvSubtotal);//(TextView)
            holder.tvUnidadesDevueltas = convertView.findViewById(R.id.tvUnidadesDevueltas);//(TextView)
            holder.tvBultosDevueltos = convertView.findViewById(R.id.tvBultosDevueltos);//(TextView)
            holder.llDevolucion = convertView.findViewById(R.id.llDevolucion);//(LinearLayout)
            holder.llCombo = convertView.findViewById(R.id.llCombo);//(LinearLayout)
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final VentaDetalle ventaDetalle = items.get(position);
        class DialogDevolucion {
            void showDialog() {
                DevolucionDialogFragment devolucionDialogFragment = DevolucionDialogFragment.newInstance(
                        ventaDetalle,
                        new DevolucionListener() {

                            @Override
                            public void onAccepted(int bultosDev, double unidDev) {
                                if (bultosDev + unidDev > 0) {
                                    deselectView(position);
                                } else {
                                    selectView(position, false);
                                }

                                if (controlDisponibilidadEnDevoluciones(ventaDetalle, bultosDev, unidDev)) {
                                    ventaDetalle.setUnidadesDevueltas(unidDev);
                                    ventaDetalle.setBultosDevueltos(bultosDev);

                                    //Devuelvo detalles si es combo
                                    if (ventaDetalle.isCabeceraPromo() && ventaDetalle.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_ESPECIALES) {
                                        double cantidad = 0d;
                                        for (VentaDetalle vd : ventaDetalle.getDetalleCombo()) { //Sumo cantidades de detalles con idPromo = idArticulo de la cabecera
                                            cantidad += vd.getCantidad();
                                        }
                                        //Calculo la cantidad porque pudo haber cambiado la definicion del combo
                                        double cantidadPorCombo = cantidad / ventaDetalle.getCantidad();
                                        double cantidadTotalDevuelta = 0d;
                                        double cantidadDevueltaCombo = cantidadPorCombo * unidDev + cantidadPorCombo * bultosDev * ventaDetalle.getArticulo().getUnidadPorBulto();
                                        for (VentaDetalle vd : ventaDetalle.getDetalleCombo()) {
                                            if (cantidadTotalDevuelta < cantidadDevueltaCombo) {
                                                double cantidadADevolver = 0;
                                                if (vd.getUnidades() > 0) {
                                                    //devuelvo unidades controlando no devolver de mas
                                                    if (vd.getUnidades() - vd.getUnidadesDevueltas()
                                                            < cantidadDevueltaCombo - cantidadTotalDevuelta + vd.getUnidades() - vd.getUnidadesDevueltas()) {
                                                        cantidadADevolver = vd.getUnidades() - vd.getUnidadesDevueltas();
                                                    } else if (vd.getUnidades() - vd.getUnidadesDevueltas()
                                                            > cantidadDevueltaCombo - cantidadTotalDevuelta + vd.getUnidades() - vd.getUnidadesDevueltas()) {
                                                        cantidadADevolver = cantidadDevueltaCombo - cantidadTotalDevuelta;
                                                    }
                                                    vd.setUnidadesDevueltas(cantidadADevolver);
                                                    cantidadTotalDevuelta += cantidadADevolver;

                                                }
                                                if (vd.getBultos() > 0) {
                                                    //devuelvo bultos
                                                    if ((vd.getBultos() - vd.getBultosDevueltos()) * ventaDetalle.getArticulo().getUnidadPorBulto()
                                                            < cantidadDevueltaCombo - cantidadTotalDevuelta +
                                                            (vd.getBultos() - vd.getBultosDevueltos()) * ventaDetalle.getArticulo().getUnidadPorBulto()) {
                                                        cantidadADevolver = vd.getBultos() - vd.getBultosDevueltos();
                                                    } else if ((vd.getBultos() - vd.getBultosDevueltos()) * ventaDetalle.getArticulo().getUnidadPorBulto()
                                                            > cantidadDevueltaCombo - cantidadTotalDevuelta +
                                                            (vd.getBultos() - vd.getBultosDevueltos()) * ventaDetalle.getArticulo().getUnidadPorBulto()) {

                                                        cantidadADevolver = (cantidadDevueltaCombo - cantidadTotalDevuelta);
                                                    }
                                                    int bultosADevolver = (int) Matematica.Round(cantidadADevolver / ventaDetalle.getArticulo().getUnidadPorBulto(), 0);
                                                    vd.setBultosDevueltos(bultosADevolver);
                                                    cantidadTotalDevuelta += bultosADevolver * ventaDetalle.getArticulo().getUnidadPorBulto();
                                                }

                                            }
                                        }
                                    }
                                }
                                mListener.onChangeDevolucion();
                            }

                            @Override
                            public void onCloseDialog() {
                                KeyboardUtils.hideKeyboard(getContext());

                            }

                        });

                devolucionDialogFragment.show(mFragmentManager, "dialogPolenta");
            }
        }

        if (getCampoBusqueda() == FILTER_CODIGO) {
            TextViewUtils.highlightText(holder.tvCodEmpresa, ventaDetalle.getArticulo().getCodigoEmpresa(),
                    getFilteredString());
            holder.tvArticulo.setText(ventaDetalle.getArticulo().getDescripcion());

        } else {
            TextViewUtils.highlightText(holder.tvArticulo, ventaDetalle.getArticulo().getDescripcion(),
                    getFilteredString());
            holder.tvCodEmpresa.setText(ventaDetalle.getArticulo().getCodigoEmpresa());
        }

        if (ventaDetalle.isCabeceraPromo()) {
            List<VentaDetalle> ventaDetalleList;
            ventaDetalleList = mVentaDetalleBo.recoveryDetallesCombo(ventaDetalle);
            double precio = 0;
            double precio_iva = 0;
            for (VentaDetalle item : ventaDetalleList) {
                precio += (item.getPrecioUnitarioSinIva() * item.getCantidad()) / ventaDetalle.getCantidad();
                precio_iva += (item.getPrecioIvaUnitario() * item.getCantidad()) / ventaDetalle.getCantidad();
            }
            ventaDetalle.setPrecioUnitarioSinDescuento(precio);
            ventaDetalle.setPrecioUnitarioSinDescuentoCliente(precio);
            ventaDetalle.setPrecioUnitarioSinDescuentoProveedor(precio);
            ventaDetalle.setPrecioUnitarioSinIva(precio);
            ventaDetalle.setPrecioIvaUnitario(precio_iva);
        }

        holder.tvPrecioUnidad.setText(Formatter.formatMoney(ventaDetalle.getPrecioUnitarioSinDescuento()));
        holder.tvBulto.setText(String.valueOf(ventaDetalle.getBultos() - ventaDetalle.getBultosDevueltos()));
        holder.tvUnidades.setText(String.valueOf(ventaDetalle.getUnidades() - ventaDetalle.getUnidadesDevueltas()));
        holder.tvSubtotal.setText(Formatter.formatMoney(ventaDetalle.getImporteTotal()));
        holder.tvUnidadesDevueltas.setText(String.valueOf(ventaDetalle.getUnidadesDevueltas()));
        holder.tvBultosDevueltos.setText(String.valueOf(ventaDetalle.getBultosDevueltos()));

        if (ventaDetalle.getDetalleCombo() != null) {
            holder.llCombo.setVisibility(View.VISIBLE);
            getLineasCombo(holder.llCombo, ventaDetalle);
        } else {
            holder.llCombo.setVisibility(View.GONE);
        }

        String snRendida = "";
        try {
            snRendida = mHojaBo.getSnRendida(hojaDetalle.getId().getIdSucursal(), hojaDetalle.getId().getIdHoja());
        } catch (Exception e) {
            e.printStackTrace();
        }


        VentaBo mVentaBo = new VentaBo(_repoFactory);
        try {
            isCreditos = mVentaBo.isCredito(hojaDetalle.getId().getIdFcnc(), hojaDetalle.getId().getIdTipoab(),
                    hojaDetalle.getId().getIdPtovta(), hojaDetalle.getId().getIdNumdoc());

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isEnviado && snRendida.equals(Hoja.MOVIL) && !isCreditos) {
            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!isSelected(position)) {
                        new DialogDevolucion().showDialog();
                    }


                }
            });
            setDevolucionBackground(position, convertView, ventaDetalle);
            convertView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View arg0) {
                    if (isSelected(position)) {
                        new DialogDevolucion().showDialog();
                    } else {
                        toggleSelection(position);
                        ventaDetalle.setUnidadesDevueltas(0);
                        ventaDetalle.setBultosDevueltos(0);
                        mListener.onChangeDevolucion();
                    }

                    return true;
                }
            });
        } else {
            setBackground(position, convertView);
        }
        return convertView;
    }

    private void getLineasCombo(LinearLayout lnContenedor, VentaDetalle ventaDetalle) {
        lnContenedor.removeAllViews();
        for (VentaDetalle detalleCombo : ventaDetalle.getDetalleCombo()) {
            View lineaPs = LayoutInflater.from(getContext()).inflate(
                    R.layout.lyt_hoja_detalle_item_factura, null);
            lineaPs.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_item_normal));
            String descripcion = detalleCombo.getArticulo().getDescripcion();
            String codigoEmpresa = detalleCombo.getArticulo().getCodigoEmpresa();
            int bultos = detalleCombo.getBultos();
            double unidades = detalleCombo.getUnidades();
            double precioUnitario = detalleCombo.getPrecioUnitarioSinDescuento();
            double subtotal = detalleCombo.getImporteTotal();

            TextView tvDescripcion = lineaPs.findViewById(R.id.tvArticulo);//(TextView)
            TextView tvCodigo = lineaPs.findViewById(R.id.tvCodEmpresa);//(TextView)
            TextView tvBultos = lineaPs.findViewById(R.id.tvBulto);//(TextView)
            TextView tvUnidades = lineaPs.findViewById(R.id.tvUnidades);//(TextView)
            TextView tvPrecioUnitario = lineaPs.findViewById(R.id.tvPrecioUnitario);//(TextView)
            TextView tvSubtotal = lineaPs.findViewById(R.id.tvSubtotal);//(TextView)

            //Oculto devolucion de los detalles
            LinearLayout llDevolucion = lineaPs.findViewById(R.id.llDevolucion);//(LinearLayout)
            llDevolucion.setVisibility(View.GONE);

            tvDescripcion.setText(descripcion);
            tvCodigo.setText(codigoEmpresa);
            tvBultos.setText(String.valueOf(bultos));
            tvUnidades.setText(String.valueOf(unidades));
            tvPrecioUnitario.setText(Formatter.formatMoney(precioUnitario));
            tvSubtotal.setText(Formatter.formatMoney(subtotal));

            lnContenedor.addView(lineaPs);
        }
    }

    private void setDevolucionBackground(int position, View convertView, VentaDetalle ventaDetalle) {
        if (mDeletedItems.get(position)) {
            convertView
                    .setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_item_envio_recibo));

            ((EditText) convertView.findViewById(R.id.etUnidades)).setText(String.valueOf(ventaDetalle.getUnidades()));
            ((EditText) convertView.findViewById(R.id.etBultos)).setText(String.valueOf(ventaDetalle.getBultos()));

            return;
        }
        setBackground(position, convertView);
    }

    public boolean toggleDeleted(int position) {
        if (!mDeletedItems.get(position)) {
            mDeletedItems.put(position, true);

        } else {
            mDeletedItems.delete(position);
        }

        notifyDataSetChanged();
        return mDeletedItems.get(position);
    }

    public boolean isSelectedDeleted(int position) {
        return mDeletedItems.get(position);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new DetalleFacturaFilter();
        }
        return mFilter;
    }

    @Override
    public void sort() {
        HojaDetalleComparator comparator = new HojaDetalleComparator(getCampoOrden());
        super.sort(comparator);
    }

    private static class ViewHolder {
        TextView tvArticulo;
        TextView tvCodEmpresa;
        TextView tvPrecioUnidad;
        TextView tvBulto;
        TextView tvUnidades;
        TextView tvSubtotal;
        LinearLayout llDevolucion;
        TextView tvUnidadesDevueltas;
        TextView tvBultosDevueltos;
        LinearLayout llCombo;
    }

    public interface DetalleFacturaListener {
        void onChangeDevolucion();
    }

    private class DetalleFacturaFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            setFilteredString(prefix.toString());
            FilterResults results = new FilterResults();
            List<VentaDetalle> aux = new ArrayList<>();

            for (int index = 0; index < allItems.size(); index++) {
                VentaDetalle ventaDetalle = allItems.get(index);

                String datos;
                if (getCampoBusqueda() == FILTER_CODIGO) {
                    datos = ventaDetalle.getArticulo().getCodigoEmpresa();
                } else {
                    datos = ventaDetalle.getArticulo().getDescripcion();
                }

                if (prefix.equals(null))
                    aux.add(ventaDetalle);
                else {
                    String filtro = prefix.toString();
                    filtro = filtro.toLowerCase();
                    datos = datos.toLowerCase();
                    if (datos.contains(filtro))
                        aux.add(ventaDetalle);
                }
            }
            results.values = aux;
            results.count = aux.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (List<VentaDetalle>) results.values;
            notifyDataSetChanged();

        }

    }

    public List<VentaDetalle> getDetalles() {
        return detalles;
    }

    private boolean controlDisponibilidadEnDevoluciones(VentaDetalle vtaDetalleDevuelta, int bultosDevueltos, double unidDevueltas) {
        double totalPendiente = hojaDetalle.getPrTotal() - hojaDetalle.getPrPagado() - hojaDetalle.getPrNotaCredito();
        double importeDevuelto = 0d;
        Venta mVenta;
        VentaBo mVentaBo = new VentaBo(_repoFactory);
        try {
            mVenta = mVentaBo.recoveryById(hojaDetalle.getId().getIdFcnc(),
                    hojaDetalle.getId().getIdTipoab(), hojaDetalle.getId().getIdPtovta(),
                    hojaDetalle.getId().getIdNumdoc());
            for (VentaDetalle vDet : mVenta.getDetalles()) {
                if (vDet.getId().getIdDocumento().equals(vtaDetalleDevuelta.getId().getIdDocumento()) &&
                        vDet.getId().getIdLetra().equals(vtaDetalleDevuelta.getId().getIdLetra()) &&
                        vDet.getId().getPuntoVenta() == vtaDetalleDevuelta.getId().getPuntoVenta() &&
                        vDet.getId().getIdNumeroDocumento() == vtaDetalleDevuelta.getId().getIdNumeroDocumento() &&
                        vDet.getId().getSecuencia() == vtaDetalleDevuelta.getId().getSecuencia()) {

                    vDet.setUnidadesDevueltas(unidDevueltas);
                    vDet.setBultosDevueltos(bultosDevueltos);

                    //Devuelvo detalles si es combo
                    if (vDet.isCabeceraPromo() && vDet.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_ESPECIALES) {
                        double cantidad = 0d;
                        for (VentaDetalle vd : vDet.getDetalleCombo()) { //Sumo cantidades de detalles con idPromo = idArticulo de la cabecera
                            cantidad += vd.getCantidad();
                        }
                        //Calculo la cantidad porque pudo haber cambiado la definicion del combo
                        double cantidadPorCombo = cantidad / vDet.getCantidad();
                        double cantidadTotalDevuelta = 0d;
                        double cantidadDevueltaCombo = cantidadPorCombo * unidDevueltas + cantidadPorCombo * bultosDevueltos * vDet.getArticulo().getUnidadPorBulto();
                        for (VentaDetalle vd : vDet.getDetalleCombo()) {
                            if (cantidadTotalDevuelta < cantidadDevueltaCombo) {
                                double cantidadADevolver = 0;
                                if (vd.getUnidades() > 0) {
                                    //devuelvo unidades controlando no devolver de mas
                                    if (vd.getUnidades() - vd.getUnidadesDevueltas()
                                            < cantidadDevueltaCombo - cantidadTotalDevuelta + vd.getUnidades() - vd.getUnidadesDevueltas()) {
                                        cantidadADevolver = vd.getUnidades() - vd.getUnidadesDevueltas();
                                    } else if (vd.getUnidades() - vd.getUnidadesDevueltas()
                                            > cantidadDevueltaCombo - cantidadTotalDevuelta + vd.getUnidades() - vd.getUnidadesDevueltas()) {
                                        cantidadADevolver = cantidadDevueltaCombo - cantidadTotalDevuelta;
                                    }
                                    vd.setUnidadesDevueltas(cantidadADevolver);
                                    cantidadTotalDevuelta += cantidadADevolver;

                                }
                                if (vd.getBultos() > 0) {
                                    //devuelvo bultos
                                    if ((vd.getBultos() - vd.getBultosDevueltos()) * vDet.getArticulo().getUnidadPorBulto()
                                            < cantidadDevueltaCombo - cantidadTotalDevuelta +
                                            (vd.getBultos() - vd.getBultosDevueltos()) * vDet.getArticulo().getUnidadPorBulto()) {
                                        cantidadADevolver = vd.getBultos() - vd.getBultosDevueltos();
                                    } else if ((vd.getBultos() - vd.getBultosDevueltos()) * vDet.getArticulo().getUnidadPorBulto()
                                            > cantidadDevueltaCombo - cantidadTotalDevuelta +
                                            (vd.getBultos() - vd.getBultosDevueltos()) * vDet.getArticulo().getUnidadPorBulto()) {

                                        cantidadADevolver = (cantidadDevueltaCombo - cantidadTotalDevuelta);
                                    }
                                    int bultosADevolver = (int) Matematica.Round(cantidadADevolver / vDet.getArticulo().getUnidadPorBulto(), 0);
                                    vd.setBultosDevueltos(bultosADevolver);
                                    cantidadTotalDevuelta += bultosADevolver * vDet.getArticulo().getUnidadPorBulto();
                                }

                            }
                        }
                    }
                }
            }

            importeDevuelto = VentaBo.calcularTotal(mVenta, VentaBo.NC_DEVOLUCION);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (totalPendiente < importeDevuelto) {
            Toast.makeText(getContext(), "Importe de devoluciones $" + importeDevuelto +
                    " mas los pagos registrados, superan el importe del comprobante.", Toast.LENGTH_LONG).show();
        }
        return (totalPendiente >= importeDevuelto);
    }

}
