package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_tipo_producto")
public class TipoProducto implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id_tipo_producto;
	
	@Column(length=45,nullable=false)
	String descripcion;
	
	@ManyToOne
	@JoinColumn(name="cuenta")
	CuentaContable cuentaContable;
	
	@ManyToOne
	@JoinColumn(name="ccosto")
	CuentaContable cuentaCosto;
	
	public TipoProducto(){}
	
	public Integer getId_tipo_producto(){
		return this.id_tipo_producto;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion=descripcion.toUpperCase();
	}
	
	public CuentaContable getCuentaContable(){
		return this.cuentaContable;
	}
	
	public void setCuentaContable(CuentaContable cuenta){
		this.cuentaContable = cuenta;
	}
	
	public CuentaContable getCuentaCosto(){
		return this.cuentaCosto;
	}
	
	public void setCuentaCosto(CuentaContable cuenta){
		this.cuentaCosto = cuenta;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_tipo_producto == null) ? 0 : id_tipo_producto.hashCode());
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
		TipoProducto other = (TipoProducto) obj;
		if (this.id_tipo_producto == null) {
			if (other.id_tipo_producto != null)
				return false;
		} else if (!this.id_tipo_producto.equals(other.id_tipo_producto))
			return false;
		return true;
	}

	public static TipoProducto clone(TipoProducto todo) {
		try {
			return (TipoProducto) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
	
}
