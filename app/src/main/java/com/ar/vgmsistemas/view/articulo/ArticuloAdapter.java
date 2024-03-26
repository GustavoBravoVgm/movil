package com.ar.vgmsistemas.view.articulo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.utils.ComparatorArticulo;
import com.ar.vgmsistemas.utils.TextViewUtils;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public final class ArticuloAdapter extends ListItemAdapter<Articulo> {

	private ArticuloFilter filter;
	private String filterString;

	public ArticuloAdapter(Context context, int textViewResourceId, List<Articulo> objects) {
		super(context, textViewResourceId, objects);
		filterString = "";
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = View.inflate(this.getContext(), R.layout.lyt_articulo_lista_item, null);
			holder = new ViewHolder();
			holder.codigo = (TextView) convertView.findViewById(R.id.lblCodigo);
			holder.descripcion = (TextView) convertView.findViewById(R.id.lblDescripcion);
			holder.stock = (TextView) convertView.findViewById(R.id.lblStockValue);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Articulo articulo = (Articulo) items.get(position);
		if (articulo != null) {

			// CON FILTRO
			if (mCampoFiltro == Preferencia.ARTICULO_CODIGO) {
				TextViewUtils.highlightText(holder.codigo, articulo.getCodigoEmpresa(), filterString);
				holder.descripcion.setText(articulo.getDescripcion());
			} else {
				holder.codigo.setText(articulo.getCodigoEmpresa());
				TextViewUtils.highlightText(holder.descripcion, articulo.getDescripcion(), filterString);
			}

			holder.stock.setText(String.valueOf(articulo.getStock()));
		}

		return convertView;
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new ArticuloFilter();
		}
		return filter;
	}

	private static class ViewHolder {
		TextView codigo;
		TextView descripcion;
		TextView stock;
	}

	@Override
	public void sort() {
		ComparatorArticulo comparator = new ComparatorArticulo();
		super.sort(comparator);
	}

	private class ArticuloFilter extends Filter {

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence prefix, FilterResults results) {
			items = (List<Articulo>) results.values;
			notifyDataSetChanged();
		}

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {

			FilterResults results = new FilterResults();
			Articulo articuloEquals = null;
			List<Articulo> auxContains = new ArrayList<>();
			List<Articulo> auxStarts = new ArrayList<>();
			
			filterString = (prefix == null) ? "" : prefix.toString();
			if (prefix != null && prefix.toString().length() > 0) {

				for (int index = 0; index < allItems.size(); index++) {
					Articulo articulo = (Articulo) allItems.get(index);
					String filtro = prefix.toString();
					filtro = filtro.toLowerCase();
					switch (mCampoFiltro) {
					case Preferencia.ARTICULO_DESCRIPCION:
						String descripcion = articulo.getDescripcion();
						descripcion = descripcion.toLowerCase();
						if(descripcion.equals(filtro)){
							articuloEquals = articulo;
							continue;
						}
						if (descripcion.startsWith(filtro)) {
							auxStarts.add(articulo);
						} else if (descripcion.contains(filtro))
							auxContains.add(articulo);
						break;

					case Preferencia.ARTICULO_CODIGO:
						String codigo = articulo.getCodigoEmpresa();
						codigo = codigo.toLowerCase();
						if(codigo.equals(filtro)){
							articuloEquals = articulo;
							continue;
						}
							
						if (codigo.startsWith(filtro)) {
							auxStarts.add(articulo);
							continue;
						} 
						if (codigo.contains(filtro)) {
							auxContains.add(articulo);
							continue;
						}
						break;
					}
				}
				if(articuloEquals!=null)auxStarts.add(0,articuloEquals);
				auxStarts.addAll(auxContains);
				results.values = auxStarts;
				results.count = auxStarts.size();
			} else {
				synchronized (allItems) {
					results.values = allItems;
					results.count = allItems.size();
				}
			}
			return results;
		}
	}
}
