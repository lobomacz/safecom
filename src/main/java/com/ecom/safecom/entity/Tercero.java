package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbl_terceros")
public class Tercero implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id_tercero;
	
	@Column(length=16)
	String 	identificacion;

	@Column
	String tipo;
	
	@Column(length=45)
	String nombre;
	
	@Column(length=200)
	String direccion;
	
	@Column(length=45)
	String contacto;
	
	@Column(length=45)
	String correo_electronico;
	
	Boolean activo;
	
	@ManyToMany(targetEntity=Telefono.class,cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name="tbl_terceros_has_tbl_telefonos",joinColumns=@JoinColumn(name="id_tercero"),inverseJoinColumns=@JoinColumn(name="telefono"))
	List<Telefono> telefonos;
	
	public Tercero(){}
	
	public Integer getId_tercero(){
		return this.id_tercero;
	}
	
	public String getIdentificacion(){
		return this.identificacion;
	}
	
	public void setIdentificacion(String identif){
		this.identificacion = identif.toUpperCase();
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre.toUpperCase();
	}
	
	public String getDireccion(){
		return this.direccion;
	}
	
	public void setDireccion(String direccion){
		this.direccion = direccion.toUpperCase();
	}
	
	public String getContacto(){
		return this.contacto;
	}
	
	public void setContacto(String contacto){
		this.contacto = contacto.toUpperCase();
	}
	
	public String getCorreo_electronico(){
		return this.correo_electronico;
	}
	
	public void setCorreo_electronico(String correo){
		this.correo_electronico = correo.toUpperCase();
	}
	
	public Boolean getActivo(){
		return this.activo;
	}
	
	public void setActivo(Boolean activo){
		this.activo = activo;
	}
	
	public List<Telefono> getTelefonos(){
		return this.telefonos;
	}
	
	public void setTelefonos(List<Telefono> telefonos){
		this.telefonos = telefonos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.id_tercero == null) ? 0 : this.id_tercero.hashCode());
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
		Tercero other = (Tercero) obj;
		if (this.id_tercero == null) {
			if (other.id_tercero != null)
				return false;
		} else if (!id_tercero.equals(other.id_tercero))
			return false;
		return true;
	}

	public static Tercero clone(Tercero obj) {
		try {
			return (Tercero) obj.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
