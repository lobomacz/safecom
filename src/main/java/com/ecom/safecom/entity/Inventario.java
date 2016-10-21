package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_inventario_productos")
public class Inventario implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	InventarioPK id_inventario;
	
	BigDecimal cantidad;
	
	@ManyToOne
	@JoinColumn(name="unidad")
	UnidadMedida unidadMedida;
	
	@OneToOne
	@JoinColumn(name="codigo_producto",insertable=false,updatable=false)
	Producto producto;
	
	public Inventario() {
		
	}
	
	public InventarioPK getId_inventario(){
		return this.id_inventario;
	}
	
	public void setId_inventario(InventarioPK _id){
		this.id_inventario = _id;
	}
	
	public BigDecimal getCantidad(){
		return this.cantidad;
	}
	
	public void setCantidad(BigDecimal cantidad){
		this.cantidad = cantidad;
	}
	
	public UnidadMedida getUnidadMedida(){
		return this.unidadMedida;
	}
	
	public void setUnidadMedida(UnidadMedida unidad){
		this.unidadMedida = unidad;
	}
	
	public Producto getProducto(){
		return this.producto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.id_inventario == null) ? 0 : this.id_inventario.hashCode());
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
		Inventario other = (Inventario) obj;
		if (this.id_inventario == null) {
			if (other.id_inventario != null)
				return false;
		} else if (!id_inventario.equals(other.id_inventario))
			return false;
		return true;
	}

	public static Inventario clone(Inventario obj) {
		try {
			return (Inventario) obj.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
