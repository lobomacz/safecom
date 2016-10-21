package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DetalleVentaPK implements Serializable, Cloneable {

	
	private static final long serialVersionUID = 1L;
	
	Integer id_venta;
	
	String codigo_producto;
	
	public DetalleVentaPK(){}
	
	public DetalleVentaPK(Integer idventa, String cod_producto){
		this.id_venta = idventa;
		this.codigo_producto = cod_producto;
	}
	
	public Integer getId_venta(){
		return this.id_venta;
	}
	
	public void setId_venta(Integer idventa){
		this.id_venta = idventa;
	}
	
	public String getCodigo_producto(){
		return this.codigo_producto;
	}
	
	public void setCodigo_producto(String codigo_producto){
		this.codigo_producto = codigo_producto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		String baseVal = id_venta.toString() + codigo_producto;
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
		DetalleVentaPK other = (DetalleVentaPK) obj;
		if (id_venta == null && codigo_producto == null) {
			if (other.id_venta != null && other.codigo_producto != null)
				return false;
		} else if (!id_venta.equals(other.id_venta)
				&& !codigo_producto.equals(other.codigo_producto))
			return false;
		return true;
	}

}
