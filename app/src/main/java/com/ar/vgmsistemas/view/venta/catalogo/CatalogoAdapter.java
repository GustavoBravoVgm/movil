package com.ar.vgmsistemas.view.venta.catalogo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.GrupoClientesDetalle;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.utils.Formatter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.ViewHolder> implements Filterable {
    List<ListaPrecioDetalle> listaPrecioDetalles;
    List<ListaPrecioDetalle> allListaPrecioDetalles;
    Context context;
    CatalogoFilter catalogoFilter;
    ItemClickListener listenerItem;
    Cliente cliente;
    GrupoClientesDetalle grupoClientesDetalle;

    public CatalogoAdapter(Context context, List<ListaPrecioDetalle> listaPrecioDetalles, ItemClickListener onItemClickListener, Cliente cliente) {
        this.context = context;
        this.listaPrecioDetalles = listaPrecioDetalles;
        this.allListaPrecioDetalles = listaPrecioDetalles;
        this.catalogoFilter = new CatalogoFilter(this);
        this.listenerItem = onItemClickListener;
        this.cliente = cliente;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lyt_catalogo_adapter, parent, false);
        return new ViewHolder(itemView, getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListaPrecioDetalle listaPrecioDetalle = listaPrecioDetalles.get(position);
        File imgFile;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imgFile = new File(context.getExternalFilesDir(null) + "" +
                    File.separator + Preferencia.getPathImages() + "/Thumb_" + listaPrecioDetalle.getArticulo().getId() + ".JPG");
        } else {
            imgFile = new File(Environment.getExternalStorageDirectory() +
                    File.separator + Preferencia.getPathImages() + "/Thumb_" + listaPrecioDetalle.getArticulo().getId() + ".JPG");
        }
        if (imgFile.exists()) {
            holder.imagen.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        } else {
            holder.imagen.setImageResource(R.drawable.imgnodisponible);
        }
        holder.descripcionArticulo.setText(listaPrecioDetalle.getArticulo().getDescripcion());
        double precioFinal = listaPrecioDetalle.getPrecioFinal();
        holder.precioCdto.setText(Formatter.formatMoney(precioFinal));

        holder.bind(listaPrecioDetalles.get(position), listenerItem, position);
        holder.stock.setText(String.valueOf(listaPrecioDetalle.getArticulo().getStock()));
        holder.cantidadPedida.setText(String.valueOf(listaPrecioDetalle.getCantidadPedida()));
        if (listaPrecioDetalle.getCantidadPedida() > 0) {
            holder.cantidadPedida.setTextColor(Color.GREEN);
            holder.cantidadPedida.setTypeface(holder.cantidadPedida.getTypeface(), Typeface.BOLD);
            holder.lblCantidadPedida.setTextColor(Color.GREEN);
            holder.lblCantidadPedida.setTypeface(holder.lblCantidadPedida.getTypeface(), Typeface.BOLD);
        } else {
            holder.cantidadPedida.setTextColor(Color.BLACK);
            holder.lblCantidadPedida.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return listaPrecioDetalles.size();
    }

    public List<ListaPrecioDetalle> getListaPrecioDetalles() {
        return listaPrecioDetalles;
    }

    public void setListaPrecioDetalles(List<ListaPrecioDetalle> listaPrecioDetalles) {
        this.listaPrecioDetalles = listaPrecioDetalles;
    }

    public List<ListaPrecioDetalle> getAllListaPrecioDetalles() {
        return allListaPrecioDetalles;
    }

    public void setAllListaPrecioDetalles(List<ListaPrecioDetalle> allListaPrecioDetalles) {
        allListaPrecioDetalles = allListaPrecioDetalles;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return catalogoFilter;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView descripcionArticulo;
        TextView precioCdto;
        TextView tvVerPromociones;
        TextView stock;
        TextView cantidadPedida;
        TextView lblCantidadPedida;


        public ViewHolder(View itemView, Context context) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenCatalogo);
            descripcionArticulo = itemView.findViewById(R.id.tvDescripcion);
            //precioSdto = itemView.findViewById(R.id.tvPrecioSDto);
            precioCdto = itemView.findViewById(R.id.tvPrecioCDto);
            tvVerPromociones = itemView.findViewById(R.id.tvVerPromociones);
            stock = itemView.findViewById(R.id.lblStockValueCatalogo);
            cantidadPedida = itemView.findViewById(R.id.tvCantidadPedida);
            lblCantidadPedida = itemView.findViewById(R.id.lblCaPedida);
        }

        public void bind(final ListaPrecioDetalle item, final ItemClickListener listener, int posicion) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v("Fede", "Item");
                    listener.onItemClickItem(item);

                }
            });
            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickImagen(item);
                }
            });
            tvVerPromociones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickPromociones(item);
                    Log.v("Fede", "tvVerPromociones");
                }
            });
        }
    }

    private class CatalogoFilter extends Filter {

        private CatalogoAdapter catalogoAdapter;

        private CatalogoFilter(CatalogoAdapter catalogoAdapter) {
            super();
            this.catalogoAdapter = catalogoAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //TODO: Hacer
            final FilterResults results = new FilterResults();
            List<ListaPrecioDetalle> listaPrecioDetallesTemp = new ArrayList<ListaPrecioDetalle>();
            try {


                if (constraint.length() == 0) {
                    listaPrecioDetallesTemp.addAll(this.catalogoAdapter.getAllListaPrecioDetalles());
                } else {
                    String[] constraints = constraint.toString().split(",");
                    //0: Negocio, 1:Rubro, 2:Subrubro, 3:Marca
                    for (final ListaPrecioDetalle listaPrecioDetalle : this.catalogoAdapter.getAllListaPrecioDetalles()) {
                        if ((constraints[0].equals("-1") ||
                                Long.toString(listaPrecioDetalle.getArticulo().getSubrubro().getId().getIdNegocio()).equals(constraints[0])
                        ) &&
                                (constraints[1].equals("-1") ||
                                        Long.toString(listaPrecioDetalle.getArticulo().getSubrubro().getId().getIdRubro()).equals(constraints[1])
                                ) &&
                                (constraints[2].equals("-1") ||
                                        Long.toString(listaPrecioDetalle.getArticulo().getSubrubro().getId().getIdSubrubro()).equals(constraints[2])
                                ) &&
                                (constraints[3].equals("-1") ||
                                        Long.toString(listaPrecioDetalle.getArticulo().getMarca().getId()).equals(constraints[3])
                                ) &&
                                (constraints[4].equals("-1") ||
                                        listaPrecioDetalle.getArticulo().getDescripcion().toLowerCase().contains(constraints[4].toLowerCase()))
                        ) {
                            listaPrecioDetallesTemp.add(listaPrecioDetalle);
                        }
                    }
                }
            } catch (Exception e) {
                Log.v("GEMA Catalogo", e.getMessage());
            }
            listaPrecioDetalles = listaPrecioDetallesTemp;
            results.values = listaPrecioDetalles;
            results.count = listaPrecioDetalles.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            this.catalogoAdapter.notifyDataSetChanged();
        }
    }

    public interface ItemClickListener {
        void onItemClickItem(ListaPrecioDetalle item);

        void onItemClickPromociones(ListaPrecioDetalle item);

        void onItemClickImagen(ListaPrecioDetalle item);
    }

}
