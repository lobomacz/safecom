package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbl_telefonos")
public class Telefono implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	String telefono;
	
	@Column
	String descripcion;
	
	@ManyToMany(targetEntity=Tercero.class,cascade={CascadeType.PERSIST,CascadeType.MERGE},mappedBy="telefonos")
	List<Tercero> terceros;
	
	public Telefono(){}
	
	public String getTelefono(){
		return this.telefono;
	}
	
	public void setTelefono(String telefono){
		this.telefono = telefono;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public void setDescripcion(String desc){
		this.descripcion = desc;
	}
	
	public List<Tercero> getTerceros(){
		return this.terceros;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
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
		Telefono other = (Telefono) obj;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	public static Telefono clone(Telefono todo) {
		try {
			return (Telefono) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
