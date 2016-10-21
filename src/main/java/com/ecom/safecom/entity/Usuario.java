package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ecom.safecom.BCrypt;

@Entity
@Table(name = "tbl_usuarios")
public class Usuario implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false, length = 15)
	String nombre_usuario;

	@Column(nullable = false, length = 65)
	String contrasena;

	//@Column(nullable = false, length = 14)
	//String id_empleado;

	Boolean activo;

	@OneToOne
	@JoinColumn(name="id_empleado")
	Empleado empleado;
	
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name="tbl_auth_assignment",
	joinColumns=@JoinColumn(name="id_usuario"),
	inverseJoinColumns=@JoinColumn(name="itemname"))
	private Set<AuthItem> authitems;

	public Usuario() {
	}

	public String getNombre_usuario() {
		return this.nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario.toLowerCase();
	}

	/*public String getContrasena() {
		return this.contrasena;
	}*/

	public void setContrasena(String contrasena) {
		int cost = 12;
		this.contrasena = BCrypt.hashpw(contrasena, BCrypt.gensalt(cost));
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public Empleado getEmpleado(){
		return this.empleado;
	}
	
	public Set<AuthItem> getRoles(){
		return this.authitems;
	}
	
	public boolean Validar(String contrasena){
		return BCrypt.checkpw(contrasena, this.contrasena);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre_usuario == null) ? 0 : nombre_usuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (nombre_usuario == null) {
			if (other.nombre_usuario != null)
				return false;
		} else if (!nombre_usuario.equals(other.nombre_usuario))
			return false;
		return true;
	}

	public static Usuario clone(Usuario todo) {
		try {
			return (Usuario) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
