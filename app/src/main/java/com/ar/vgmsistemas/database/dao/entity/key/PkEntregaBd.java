package com.ar.vgmsistemas.database.dao.entity.key;

import java.util.Date;

public class PkEntregaBd {

	private Date fecha;
	private int veces;
	private int idLegajo;
	
	public Date getFecha() {
		return this.fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getVeces() {
		return this.veces;
	}
	public void setVeces(int veces) {
		this.veces = veces;
	}
	public int getIdLegajo() {
		return this.idLegajo;
	}
	public void setIdLegajo(int idLegajo) {
		this.idLegajo = idLegajo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.fecha == null) ? 0 : this.fecha.hashCode());
		result = prime * result + this.idLegajo;
		result = prime * result + this.veces;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PkEntregaBd other = (PkEntregaBd) obj;
		if (this.fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!this.fecha.equals(other.fecha))
			return false;
		if (this.idLegajo != other.idLegajo)
			return false;
		if (this.veces != other.veces)
			return false;
		return true;
	}
	
}
