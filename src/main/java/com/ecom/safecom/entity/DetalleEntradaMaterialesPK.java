package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DetalleEntradaMaterialesPK implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	Integer id_entrada;
	
	String codigo_producto;
	
	public DetalleEntradaMaterialesPK() {}
	
	public DetalleEntradaMaterialesPK(Integer id_entrada, String codigo_producto){
		this.id_entrada=id_entrada;
		this.codigo_producto=codigo_producto;
	}
	
	public String getCodigo_producto(){
		return this.codigo_producto;
	}
	
	public Integer getId_entrada(){
		return this.id_entrada;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		String baseVal = id_entrada.toString() + codigo_producto;
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
		DetalleEntradaMaterialesPK other = (DetalleEntradaMaterialesPK) obj;
		if (id_entrada == null && codigo_producto == null) {
			if (other.id_entrada != null && other.codigo_producto != null)
				return false;
		} else if (!id_entrada.equals(other.id_entrada)
				&& !codigo_producto.equals(other.codigo_producto))
			return false;
		return true;
	}

}
