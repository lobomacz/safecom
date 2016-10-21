package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_periodos_contables")
public class Periodo implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	String id;

	@Column
	Integer numero;

	@Column(length = 15)
	String nombre;

	@Temporal(TemporalType.DATE)
	Date fecha_inicio;

	@Temporal(TemporalType.DATE)
	Date fecha_final;
	
	@Column
	Boolean activo;

	@Column
	Boolean abierto;

	@ManyToOne
	@JoinColumn(name = "id_ejercicio")
	Ejercicio ejercicio;

	public Periodo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String _id) {
		this.id = _id;
	}
	
	public Integer getNumero(){
		return this.numero;
	}
	
	public void setNumero(Integer num){
		this.numero = num;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Ejercicio getEjercicio() {
		return this.ejercicio;
	}

	public void setEjercicio(Ejercicio ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Date getFecha_inicio() {
		return this.fecha_inicio;
	}

	public void setFecha_inicio(Date fecha) {
		this.fecha_inicio = fecha;
	}

	public Date getFecha_final() {
		return this.fecha_final;
	}

	public void setFecha_final(Date fecha) {
		this.fecha_final = fecha;
	}
	
	public Boolean getActivo(){
		return this.activo;
	}
	
	public void setActivo(Boolean activo){
		this.activo = activo;
	}

	public Boolean getAbierto() {
		return this.abierto;
	}

	public void setAbierto(Boolean abierto) {
		this.abierto = abierto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
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
		Periodo other = (Periodo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public static Periodo clone(Periodo todo) {
		try {
			return (Periodo) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
