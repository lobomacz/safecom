package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_detalle_salida_materiales")
public class DetalleSalidaMateriales implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	DetalleSalidaMaterialesPK iddetalle;
	
	BigDecimal cantidad;
	
	BigDecimal precio_unitario;
	
	BigDecimal monto_total;
	
	Boolean entregado;
	
	@ManyToOne
	@JoinColumn(name="id_unidad_medida")
	UnidadMedida unidad_medida;
	
	@ManyToOne
	@JoinColumn(name="codigo_producto",insertable=false,updatable=false)
	Producto producto;
	
	public DetalleSalidaMateriales() {
		// TODO Auto-generated constructor stub
	}
	
	public DetalleSalidaMaterialesPK getIddetalle(){
		return this.iddetalle;
	}
	
	public void setIddetalle(DetalleSalidaMaterialesPK iddetalle){
		this.iddetalle = iddetalle;
	}
	
	public UnidadMedida getUnidad_medida(){
		return this.unidad_medida;
	}
	
	public void setUnidad_medida(UnidadMedida unidad){
		this.unidad_medida = unidad;
	}
	
	public BigDecimal getCantidad(){
		return this.cantidad;
	}
	
	public void setCantidad(BigDecimal cantidad){
		this.cantidad = cantidad;
	}
	
	public BigDecimal getPrecio_unitario(){
		return this.precio_unitario;
	}
	
	public void setPrecio_unitario(BigDecimal precio){
		this.precio_unitario = precio;
	}
	
	public BigDecimal getMonto_total(){
		return this.monto_total;
	}
	
	public void setMonto_total(BigDecimal monto){
		this.monto_total = monto;
	}
	
	public Boolean getEntregado(){
		return this.entregado;
	}
	
	public void setEntregado(Boolean entregado){
		this.entregado = entregado;
	}
	
	public Producto getProducto(){
		return this.producto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.iddetalle == null) ? 0 : this.iddetalle.hashCode());
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
		DetalleSalidaMateriales other = (DetalleSalidaMateriales) obj;
		if (this.iddetalle == null) {
			if (other.iddetalle != null)
				return false;
		} else if (!iddetalle.equals(other.iddetalle))
			return false;
		return true;
	}

	public static DetalleSalidaMateriales clone(DetalleSalidaMateriales obj) {
		try {
			return (DetalleSalidaMateriales) obj.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
