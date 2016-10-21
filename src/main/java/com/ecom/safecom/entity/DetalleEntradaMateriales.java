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
@Table(name="tbl_detalle_entrada_materiales")
public class DetalleEntradaMateriales implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	DetalleEntradaMaterialesPK id;
	
	@ManyToOne
	@JoinColumn(name="unidad")
	UnidadMedida unidad;
	
	@Column
	BigDecimal cantidad;
	
	@Column
	BigDecimal costo_unitario;
	
	@Column
	BigDecimal costo_total;
	
	@Column
	Boolean recibido;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_entrada",insertable=false,updatable=false)
	EntradaMateriales entrada;
	
	public DetalleEntradaMateriales(){}
	
	public DetalleEntradaMaterialesPK getId(){
		return this.id;
	}
	
	public void setId(DetalleEntradaMaterialesPK identrada){
		this.id=identrada;
	}
	
	public UnidadMedida getUnidad(){
		return this.unidad;
	}
	
	public void setUnidad(UnidadMedida unidad){
		this.unidad = unidad;
	}
	
	public BigDecimal getCantidad(){
		return this.cantidad;
	}
	
	public void setCantidad(BigDecimal cantidad){
		this.cantidad = cantidad;
	}
	
	public BigDecimal getCosto_unitario(){
		return this.costo_unitario;
	}
	
	public void setCosto_unitario(BigDecimal costo_unitario){
		this.costo_unitario = costo_unitario;
	}
	
	public BigDecimal getCosto_total(){
		return this.costo_total;
	}
	
	public void setCosto_total(BigDecimal costo_total){
		this.costo_total = costo_total;
	}
	
	public Boolean getRecibido(){
		return this.recibido;
	}
	
	public void setRecibido(Boolean recibido){
		this.recibido = recibido;
	}
	
	public EntradaMateriales getEntrada(){
		return this.entrada;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.id == null) ? 0 : this.id.hashCode());
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
		DetalleEntradaMateriales other = (DetalleEntradaMateriales) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public static DetalleEntradaMateriales clone(DetalleEntradaMateriales obj) {
		try {
			return (DetalleEntradaMateriales) obj.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
