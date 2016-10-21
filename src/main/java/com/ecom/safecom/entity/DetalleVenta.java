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

import org.springframework.beans.factory.annotation.Autowired;

import com.ecom.safecom.dao.ProductoDao;
import com.ecom.safecom.dao.VentaDao;

@Entity
@Table(name="tbl_detalle_ventas")
public class DetalleVenta implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	DetalleVentaPK iddetalle;
	
	@ManyToOne
	@JoinColumn(name="id_unidad_medida")
	UnidadMedida unidad;
	
	@Column
	BigDecimal cantidad;
	
	@Column
	BigDecimal precio_unitario;
	
	@Column
	BigDecimal monto_total;
	
	@Column
	Boolean entregado;
	
	@ManyToOne
	@JoinColumn(name="id_venta",insertable=false,updatable=false)
	Venta venta;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="codigo_producto",insertable=false,updatable=false)
	Producto producto;
	
	public DetalleVenta(){}
	
	public DetalleVenta(DetalleVentaPK iddetalle){
		this.iddetalle = iddetalle;
	}
	
	public DetalleVentaPK getIddetalle(){
		return this.iddetalle;
	}
	
	public void setIddetalle(DetalleVentaPK iddetalle){
		this.iddetalle = iddetalle;
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
	
	public void setProducto(Producto producto){
		this.producto = producto;
	}
	public Venta getVenta(){
		return this.venta;
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
		DetalleVenta other = (DetalleVenta) obj;
		if (this.iddetalle == null) {
			if (other.iddetalle != null)
				return false;
		} else if (!iddetalle.equals(other.iddetalle))
			return false;
		return true;
	}

	public static DetalleVenta clone(DetalleVenta obj) {
		try {
			return (DetalleVenta) obj.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}


}
