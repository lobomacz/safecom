package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_asientos_contables")
public class AsientoContable implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id_asiento;

	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	Date fecha;

	@Column(length = 45,nullable=false)
	String descripcion;

	@Column(length = 15)
	String referencia;

	Boolean contabilizado;

	@ManyToOne
	@JoinColumn(name = "nombre_usuario")
	Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_periodo")
	Periodo periodo;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_asiento")
	List<DetalleAsientoContable> detallesAsiento;
	
	public AsientoContable(){}

	public Integer getId_asiento() {
		return this.id_asiento;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String desc) {
		this.descripcion = desc.toUpperCase();
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia.toUpperCase();
	}

	public Boolean getContabilizado() {
		return this.contabilizado;
	}

	public void setContabilizado(Boolean contab) {
		this.contabilizado = contab;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	public List<DetalleAsientoContable> getDetallesAsiento(){
		return this.detallesAsiento;
	}
	
	public void setDetalleAsiento(List<DetalleAsientoContable> detalle){
		this.detallesAsiento = detalle;
	}
	
	@Transient
	public BigDecimal getTotalCredito(){
		BigDecimal total = new BigDecimal(0);
		for(DetalleAsientoContable detalle : this.detallesAsiento){
			if(detalle.tipo_movimiento == "cr")
				total = total.add(detalle.getMonto());
		}
		return total;
	}
	
	@Transient
	public BigDecimal getTotalDebito(){
		BigDecimal total = new BigDecimal(0);
		for(DetalleAsientoContable detalle : this.detallesAsiento){
			if(detalle.tipo_movimiento == "db")
				total = total.add(detalle.getMonto());
		}
		return total;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id_asiento == null) ? 0 : id_asiento.hashCode());
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
		AsientoContable other = (AsientoContable) obj;
		if (id_asiento == null) {
			if (other.id_asiento != null)
				return false;
		} else if (!id_asiento.equals(other.id_asiento))
			return false;
		return true;
	}

	public static AsientoContable clone(AsientoContable todo) {
		try {
			return (AsientoContable) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
