package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DetalleSalidaMaterialesPK implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	Integer id_salida;

	String codigo_producto;

	public DetalleSalidaMaterialesPK() {
		// TODO Auto-generated constructor stub
	}

	public DetalleSalidaMaterialesPK(Integer idsalida, String codigoproducto) {
		this.id_salida = idsalida;
		this.codigo_producto = codigoproducto;
	}
	
	public Integer getId_salida(){
		return this.id_salida;
	}
	
	public String getCodigo_producto(){
		return this.codigo_producto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		String baseVal = id_salida.toString() + codigo_producto;
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
		DetalleSalidaMaterialesPK other = (DetalleSalidaMaterialesPK) obj;
		if (id_salida == null && codigo_producto == null) {
			if (other.id_salida != null && other.codigo_producto != null)
				return false;
		} else if (!id_salida.equals(other.id_salida)
				&& !codigo_producto.equals(other.codigo_producto))
			return false;
		return true;
	}

}
