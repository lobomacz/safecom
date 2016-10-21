package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_ejercicios_contables")
public class Ejercicio implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	Integer id_ejercicio;

	@Column(length = 45)
	String descripcion;

	@Column
	Boolean activo;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="ejercicio",fetch=FetchType.EAGER)
	List<Periodo> periodos;

	public Ejercicio() {
	}

	public Integer getId_ejercicio() {
		return this.id_ejercicio;
	}

	public void setId_ejercicio(Integer _id) {
		this.id_ejercicio = _id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String desc) {
		this.descripcion = desc.toUpperCase();
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<Periodo> getPeriodos() {
		return this.periodos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id_ejercicio == null) ? 0 : id_ejercicio.hashCode());
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
		Ejercicio other = (Ejercicio) obj;
		if (id_ejercicio == null) {
			if (other.id_ejercicio != null)
				return false;
		} else if (!id_ejercicio.equals(other.id_ejercicio))
			return false;
		return true;
	}

	public static Ejercicio clone(Ejercicio todo) {
		try {
			return (Ejercicio) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
