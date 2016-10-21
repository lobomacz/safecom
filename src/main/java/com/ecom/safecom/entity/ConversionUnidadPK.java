package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ConversionUnidadPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Integer unidad_origen;
	
	Integer unidad_destino;
	
	public ConversionUnidadPK() {
		
	}
	
	public ConversionUnidadPK(Integer origen, Integer destino){
		this.unidad_origen = origen;
		this.unidad_destino = destino;
	}
	
	public Integer getIdorigen(){
		return this.unidad_origen;
	}
	
	public void setIdorigen(Integer origen){
		this.unidad_origen = origen;
	}
	
	public Integer getIddestino(){
		return this.unidad_destino;
	}
	
	public void setIddestino(Integer destino){
		this.unidad_destino = destino;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		String baseVal = unidad_origen.toString() + unidad_destino.toString();
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
		ConversionUnidadPK other = (ConversionUnidadPK) obj;
		if (unidad_origen == null && unidad_destino == null) {
			if (other.unidad_origen != null && other.unidad_destino != null)
				return false;
		} else if (!unidad_origen.equals(other.unidad_origen)
				&& !unidad_destino.equals(other.unidad_destino))
			return false;
		return true;
	}

}
