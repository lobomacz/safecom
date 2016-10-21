package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_conversiones_unidades")
public class ConversionUnidadMedida implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	ConversionUnidadPK idconversion;
	
	@Column
	BigDecimal conversion_directa;
	
	@Column
	BigDecimal conversion_inversa;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unidad_origen",insertable=false,updatable=false)
	UnidadMedida unidadorigen;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unidad_destino",insertable=false,updatable=false)
	UnidadMedida unidaddestino;
	
	public ConversionUnidadMedida(){}
	
	public ConversionUnidadPK getIdconversion(){
		return this.idconversion;
	}
	
	public void setIdconversion(ConversionUnidadPK id){
		this.idconversion = id;
	}
	
	public BigDecimal getConversion_directa(){
		return this.conversion_directa;
	}
	
	public void setConversion_directa(BigDecimal conv){
		this.conversion_directa = conv;
	}
	
	public BigDecimal getConversion_inversa(){
		return this.conversion_inversa;
	}
	
	public void setConversion_inversa(BigDecimal conv){
		this.conversion_inversa = conv;
	}
	
	public UnidadMedida getUnidadorigen(){
		return this.unidadorigen;
	}
	
	public UnidadMedida getUnidaddestino(){
		return this.unidaddestino;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idconversion == null) ? 0 : idconversion.hashCode());
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
		ConversionUnidadMedida other = (ConversionUnidadMedida) obj;
		if (idconversion == null) {
			if (other.idconversion != null)
				return false;
		} else if (!idconversion.equals(other.idconversion))
			return false;
		return true;
	}

	public static ConversionUnidadMedida clone(ConversionUnidadMedida conver) {
		try {
			return (ConversionUnidadMedida) conver.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
