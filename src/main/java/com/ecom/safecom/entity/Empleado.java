package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_empleados")
public class Empleado implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false, length = 14)
	String id_empleado;

	@Column(nullable = false, length = 45)
	String primer_nombre;

	@Column(length = 45)
	String segundo_nombre;

	@Column(nullable = false, length = 45)
	String primer_apellido;

	@Column(length = 45)
	String segundo_apellido;

	@Column(length = 200)
	String direccion;

	@Temporal(TemporalType.DATE)
	Date fecha_nacimiento;

	Character sexo;

	@Temporal(TemporalType.DATE)
	Date fecha_ingreso;

	Float salario;

	@Column(length = 15)
	String cargo;

	@Column(length = 9)
	String numero_inss;

	@Column(length = 45)
	String foto_url;

	Boolean activo;

	@OneToOne(mappedBy = "empleado")
	Usuario usuario;

	/*
	 * @OneToOne(cascade=CascadeType.ALL)
	 * 
	 * @JoinColumn(name="id_empleado") Usuario usuario;
	 */

	public Empleado() {
	}

	public String getId_empleado() {
		return this.id_empleado;
	}

	public void setId_empleado(String id_empleado) {
		this.id_empleado = id_empleado.toUpperCase();
	}

	public String getPrimer_nombre() {
		return this.primer_nombre;
	}

	public void setPrimer_nombre(String primer_nombre) {
		this.primer_nombre = primer_nombre.toUpperCase();
	}

	public String getSegundo_nombre() {
		return this.segundo_nombre;
	}

	public void setSegundo_nombre(String segundo_nombre) {
		this.segundo_nombre = segundo_nombre.toUpperCase();
	}

	public String getPrimer_apellido() {
		return this.primer_apellido;
	}

	public void setPrimer_apellido(String primer_apellido) {
		this.primer_apellido = primer_apellido;
	}

	public String getSegundo_apellido() {
		return this.segundo_apellido;
	}

	public void setSegundo_apellido(String segundo_apellido) {
		this.segundo_apellido = segundo_apellido.toUpperCase();
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion.toUpperCase();
	}

	public Date getFecha_nacimiento() {
		return this.fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public Character getSexo() {
		return this.sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = Character.toUpperCase(sexo);
	}

	public Date getFecha_ingreso() {
		return this.fecha_ingreso;
	}

	public void setFecha_ingreso(Date fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}

	public Float getSalario() {
		return this.salario;
	}

	public void setSalario(Float salario) {
		this.salario = salario;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo.toUpperCase();
	}

	public String getNumero_inss() {
		return this.numero_inss;
	}

	public void setNumero_inss(String numero_inss) {
		this.numero_inss = numero_inss;
	}

	public String getFoto_url() {
		return this.foto_url;
	}

	public void setFoto_url(String foto_url) {
		this.foto_url = foto_url;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.primer_nombre, this.primer_apellido);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id_empleado == null) ? 0 : id_empleado.hashCode());
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
		Empleado other = (Empleado) obj;
		if (id_empleado == null) {
			if (other.id_empleado != null)
				return false;
		} else if (!id_empleado.equals(other.id_empleado))
			return false;
		return true;
	}

	public static Empleado clone(Empleado todo) {
		try {
			return (Empleado) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
