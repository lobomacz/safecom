package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_catalogo_cuentas")
public class CuentaContable implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false, length = 15)
	String cuenta;

	@Column(nullable = false, length = 45)
	String nombre;

	@Column(length = 100)
	String descripcion;

	@Column(nullable = false)
	String tipo;

	@Column(nullable = true)
	String cuenta_padre;

	Boolean cuenta_resumen;

	char grupo;

	Boolean activa;
	
	@OneToMany
	@JoinColumn(name="cuenta_padre",insertable=false,updatable=false)
	List<CuentaContable> cuentasHijo;

	public CuentaContable() {
	}

	public String getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta.toUpperCase();
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String desc) {
		this.descripcion = desc.toUpperCase();
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo.toLowerCase();
	}

	public String getCuenta_padre() {
		return this.cuenta_padre;
	}

	public void setCuenta_padre(String padre) {
		this.cuenta_padre = padre.toUpperCase();
	}

	public Boolean getCuenta_resumen() {
		return this.cuenta_resumen;
	}

	public void setCuenta_resumen(Boolean resumen) {
		this.cuenta_resumen = resumen;
	}

	public char getGrupo() {
		return this.grupo;
	}

	public void setGrupo(char grupo) {
		this.grupo = grupo;
	}

	public Boolean getActiva() {
		return this.activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}
	
	public List<CuentaContable> getCuentasHijo(){
		return this.cuentasHijo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.cuenta == null) ? 0 : this.cuenta.hashCode());
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
		CuentaContable other = (CuentaContable) obj;
		if (this.cuenta == null) {
			if (other.cuenta != null)
				return false;
		} else if (!cuenta.equals(other.cuenta))
			return false;
		return true;
	}

	public static CuentaContable clone(CuentaContable todo) {
		try {
			return (CuentaContable) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
