package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_precios_productos")
// SE REMOVIO LA DEFINICION DE UNIQUE CONSTRAINT EN LOS CAMPOS DE LA PK
public class PrecioProducto implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	PrecioProductoPK precioPK;

	BigDecimal precio;

	String tipo_precio;

	@ManyToOne
	@JoinColumn(name = "id_unidad_medida")
	UnidadMedida unidad;

	public PrecioProducto() {
	}

	public PrecioProductoPK getPrecioPK() {
		return this.precioPK;
	}

	public void setPrecioPK(PrecioProductoPK precioPK) {
		this.precioPK = precioPK;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getTipo_precio() {
		return this.tipo_precio;
	}

	public void setTipo_precio(String tipo_precio) {
		this.tipo_precio = tipo_precio;
	}

	public UnidadMedida getUnidad() {
		return this.unidad;
	}

	public void setUnidad(UnidadMedida unidad) {
		this.unidad = unidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((precioPK == null) ? 0 : precioPK.hashCode());
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
		PrecioProducto other = (PrecioProducto) obj;
		if (precioPK == null) {
			if (other.precioPK != null)
				return false;
		} else if (!precioPK.equals(other.precioPK))
			return false;
		return true;
	}

	public static PrecioProducto clone(PrecioProducto todo) {
		try {
			return (PrecioProducto) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
