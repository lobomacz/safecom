package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class InventarioPK implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	Integer id_almacen;
	
	String codigo_producto;
	
	public InventarioPK() {
		// TODO Auto-generated constructor stub
	}
	
	public InventarioPK(Integer almacen,String producto){
		this.id_almacen = almacen;
		this.codigo_producto = producto;
	}
	
	public String getCodigo_producto(){
		return this.codigo_producto;
	}
	
	public Integer getId_almacen(){
		return this.id_almacen;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		String baseVal = id_almacen.toString() + codigo_producto;
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
		InventarioPK other = (InventarioPK) obj;
		if (id_almacen == null && codigo_producto == null) {
			if (other.id_almacen != null && other.codigo_producto != null)
				return false;
		} else if (!id_almacen.equals(other.id_almacen)
				&& !codigo_producto.equals(other.codigo_producto))
			return false;
		return true;
	}

}
