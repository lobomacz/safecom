package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_almacenes")
public class Almacen implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id_almacen;
	
	@Column(length=45)
	String ubicacion;
	
	@OneToOne
	@JoinColumn(name="cuenta_contable")
	CuentaContable cuenta;
	
	Boolean principal;
	
	public Almacen(){}
	
	public Integer getId_almacen(){
		return this.id_almacen;
	}
	
	public String getUbicacion(){
		return this.ubicacion;
	}
	
	public void setUbicacion(String ubicacion){
		this.ubicacion = ubicacion;
	}
	
	public CuentaContable getCuenta(){
		return this.cuenta;
	}
	
	public void setCuenta(CuentaContable cuenta){
		this.cuenta = cuenta;
	}
	
	public Boolean getPrincipal(){
		return this.principal;
	}
	
	public void setPrincipal(Boolean principal){
		this.principal = principal;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id_almacen == null) ? 0 : id_almacen.hashCode());
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
		Almacen other = (Almacen) obj;
		if (id_almacen == null) {
			if (other.id_almacen != null)
				return false;
		} else if (!id_almacen.equals(other.id_almacen))
			return false;
		return true;
	}

	public static Almacen clone(Almacen almacen) {
		try {
			return (Almacen) almacen.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}


}
