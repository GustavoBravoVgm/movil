package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Preferencia;

import java.util.Comparator;

public class ComparatorArticulo implements Comparator<Articulo> {
	private boolean orderByFecha = false;
	public ComparatorArticulo(boolean orderByFecha){
		this.orderByFecha = orderByFecha;
	}
	public ComparatorArticulo(){}
	public int compare(Articulo art1, Articulo art2) {
		String valor1 = "";
		String valor2 = "";
		int criterio = PreferenciaBo.getInstance().getPreferencia().getOrdenPreferidoArticulo();
		int orderFecha = 0;
		if(orderByFecha){
			orderFecha = art1.getFechaUltimaVenta().compareTo(art2.getFechaUltimaVenta());
		}
		if(orderFecha!=0)return orderFecha;
		switch (criterio) {
		case Preferencia.ARTICULO_CODIGO:
			valor1 = art1.getCodigoEmpresa();
			valor2 = art2.getCodigoEmpresa();
			break;
		case Preferencia.ARTICULO_DESCRIPCION:
			valor1 = art1.getDescripcion();
			valor2 = art2.getDescripcion();
			break;
		case Preferencia.ARTICULO_CANTIDAD:
			valor1 = String.valueOf(art1.getCantidadVendida());
			valor2 = String.valueOf(art2.getCantidadVendida());
		}
		if (criterio==Preferencia.ARTICULO_CANTIDAD){
			return Integer.valueOf(valor1).compareTo(Integer.valueOf(valor2))*-1;
		}
		return valor1.compareToIgnoreCase(valor2);
	}
}
