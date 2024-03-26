package com.ar.vgmsistemas.view.cliente;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.SecuenciaRuteo;
import com.ar.vgmsistemas.utils.TextViewUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SecuenciaRuteoAdapter extends ArrayAdapter<SecuenciaRuteo> {

	List<SecuenciaRuteo> rutas;
	List<SecuenciaRuteo> allRutas;
	private int mCampoBusqueda;
	private ClienteFilter filter;
	private String filterString;
	private static final int LAYOUT_RESOURCE_ID = R.layout.lyt_cliente_lista_item;

	public SecuenciaRuteoAdapter(Context context, List<SecuenciaRuteo> objects) {
		super(context, LAYOUT_RESOURCE_ID, objects);
		this.rutas = objects;
		this.allRutas = this.rutas;
		this.filterString = "";
	}

	@Override
	public SecuenciaRuteo getItem(int position) {
		return rutas.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(this.getContext(), LAYOUT_RESOURCE_ID, null);
			holder = new ViewHolder();
			//txtCodigo (TextView)
			holder.txtCodigo = convertView.findViewById(R.id.lblCodigo);
			//txtCodigo(TextView)
			holder.txtApellido = convertView.findViewById(R.id.lblApellido);
			//txtCodigo(TextView)
			holder.txtDomicilio = convertView.findViewById(R.id.lblDomicilio);
			//txtCodigo(TextView)
			holder.txtCantidadVentas = convertView.findViewById(R.id.lblCantidadVentas);
			//txtCodigo(TextView)
			holder.txtCantidadNoPedido = convertView.findViewById(R.id.lblCantidadNoPedidos);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SecuenciaRuteo secuenciaRuteo = rutas.get(position);
		if (secuenciaRuteo != null) {
			switch (mCampoBusqueda) {
			case Preferencia.CODIGO:
				TextViewUtils.highlightText(holder.txtCodigo, secuenciaRuteo.getCliente().getId().toString(), filterString);
				holder.txtApellido.setText(secuenciaRuteo.getCliente().getRazonSocial());
				holder.txtDomicilio.setText(secuenciaRuteo.getCliente().getDomicilio());

				break;
			case Preferencia.DOMICILIO:
				TextViewUtils.highlightText(holder.txtDomicilio, secuenciaRuteo.getCliente().getDomicilio(), filterString);
				holder.txtCodigo.setText(secuenciaRuteo.getCliente().getId().toString());
				holder.txtApellido.setText(secuenciaRuteo.getCliente().getRazonSocial());

				break;
			case Preferencia.RAZON_SOCIAL:
				TextViewUtils.highlightText(holder.txtApellido, secuenciaRuteo.getCliente().getRazonSocial(), filterString);
				holder.txtCodigo.setText(secuenciaRuteo.getCliente().getId().toString());
				holder.txtDomicilio.setText(secuenciaRuteo.getCliente().getDomicilio());

				break;
			}
			holder.txtCantidadVentas.setText(String.valueOf(secuenciaRuteo.getCliente().getCantidadVentas()));
			holder.txtCantidadNoPedido.setText(String.valueOf(secuenciaRuteo.getCliente().getCantidadNoPedidos()));

			/*tratamiento tarea #50674: colores para estado habilitado o no*/
			holder.txtApellido.setTextColor(getContext().getResources().getColor(R.color.base_color ));
			boolean habitadoParaVenderAlcohol = secuenciaRuteo.getCliente().getIsHabilitadoParaAlcohol();
			boolean tieneFechaVencimientoAsignada = (secuenciaRuteo.getCliente().getFeVencHabilitacionAlcohol() != null);
			if (habitadoParaVenderAlcohol){
				if (tieneFechaVencimientoAsignada){
					Calendar cal = new GregorianCalendar();
					Date fechaActual = cal.getTime();
					if(secuenciaRuteo.getCliente().getFeVencHabilitacionAlcohol().compareTo(fechaActual) >= 0 ){
						holder.txtApellido.setTextColor(getContext().getResources().getColor(R.color.color_verde));
					}else{
						holder.txtApellido.setTextColor(getContext().getResources().getColor(R.color.vgm_color));
					}
				}else {
					holder.txtApellido.setTextColor(getContext().getResources().getColor(R.color.vgm_color));
				}
			}
			if (!habitadoParaVenderAlcohol && tieneFechaVencimientoAsignada){
				holder.txtApellido.setTextColor(getContext().getResources().getColor(R.color.vgm_color));
			}
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return rutas.size();
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new ClienteFilter();
		}
		return filter;
	}

	private static class ViewHolder {
		TextView txtCodigo;
		TextView txtApellido;
		TextView txtDomicilio;
		TextView txtCantidadVentas;
		TextView txtCantidadNoPedido;
	}

	private class ClienteFilter extends Filter {
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence prefix, FilterResults results) {
			rutas = (List<SecuenciaRuteo>) results.values;
			notifyDataSetChanged();
		}
		
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			filterString = prefix.toString();
			FilterResults results = new FilterResults();
			List<SecuenciaRuteo> aux = new ArrayList<>();

			for (int index = 0; index < allRutas.size(); index++) {
				SecuenciaRuteo ruta = allRutas.get(index);
				String datos = "";

				int filtroPreferidoCliente = PreferenciaBo.getInstance().getPreferencia(getContext()).getFiltroPreferidoCliente();

				Cliente cliente = ruta.getCliente();

				if ((filtroPreferidoCliente == Preferencia.FILTRO_CLIENTES_TODOS)
						|| (filtroPreferidoCliente == Preferencia.FILTRO_CLIENTES_VISITADOS && (cliente.getCantidadVentas() > 0 || cliente
								.getCantidadNoPedidos() > 0))
						|| (filtroPreferidoCliente == Preferencia.FILTRO_CLIENTES_SIN_VISITAS && (cliente.getCantidadVentas() == 0 && cliente
								.getCantidadNoPedidos() == 0))) {
					mCampoBusqueda = PreferenciaBo.getInstance().getPreferencia(getContext()).getBusquedaPreferidaCliente();
					switch (mCampoBusqueda) {
					case Preferencia.CODIGO:
						datos = ruta.getCliente().getId().toString();
						break;
					case Preferencia.DOMICILIO:
						datos = ruta.getCliente().getDomicilio();
						break;
					case Preferencia.RAZON_SOCIAL:
						datos = ruta.getCliente().getRazonSocial();
						break;
					case Preferencia.ORDEN_VISITA:
						datos = ruta.getNumeroOrden() + "";
					}

					if (prefix == null)
						aux.add(ruta);
					else {
						String filtro = prefix.toString();
						filtro = filtro.toLowerCase();
						datos = datos.toLowerCase();
						if (datos.contains(filtro))
							aux.add(ruta);
					}
				}
			}
			results.values = aux;
			results.count = aux.size();
			return results;
		}
	}

}
