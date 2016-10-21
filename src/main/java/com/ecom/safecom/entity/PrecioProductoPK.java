package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PrecioProductoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	String codigo_producto;

	Integer numero_precio;

	public PrecioProductoPK() {
	}

	public PrecioProductoPK(String codigo_producto, Integer numero_precio) {
		this.codigo_producto = codigo_producto;
		this.numero_precio = numero_precio;
	}

	// NOTA
	// POSIBLEMENTE REQUIERA GENERAR GETTERS Y SETTERS...

	public String getCodigo_producto() {
		return this.codigo_producto;
	}

	public void setCodigo_producto(String codigo) {
		this.codigo_producto = codigo;
	}

	public Integer getNumero_precio() {
		return this.numero_precio;
	}

	public void setNumero_precio(Integer numero) {
		this.numero_precio = numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		String baseVal = codigo_producto + numero_precio.toString();
		int result = 1;
		result = prime * result + ((baseVal == null) ? 0 : baseVal.hashCode());
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
		PrecioProductoPK other = (PrecioProductoPK) obj;
		if (codigo_producto == null && numero_precio == null) {
			if (other.codigo_producto != null && other.numero_precio != null)
				return false;
		} else if (!codigo_producto.equals(other.codigo_producto)
				&& !numero_precio.equals(other.numero_precio))
			return false;
		return true;
	}
}
