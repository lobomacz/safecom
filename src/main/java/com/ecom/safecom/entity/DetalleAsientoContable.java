package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_detalle_asientos_contables")
public class DetalleAsientoContable implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	DetalleAsientoContablePK iddetalle;

	@Column(length = 45, nullable = false)
	String descripcion;

	@Column
	String tipo_movimiento;

	@Column()
	BigDecimal monto;

	@Column
	Boolean contabilizado;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_asiento",insertable=false,updatable=false)
	AsientoContable asiento;

	public DetalleAsientoContable() {
	}

	public DetalleAsientoContablePK getIddetalle() {
		return this.iddetalle;
	}

	public void setIddetalle(DetalleAsientoContablePK _id) {
		this.iddetalle = _id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String desc) {
		this.descripcion = desc.toUpperCase();
	}

	public String getTipo_movimiento() {
		return this.tipo_movimiento;
	}

	public void setTipo_movimiento(String tipmov) {
		this.tipo_movimiento = tipmov.toLowerCase();
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Boolean getContabilizado() {
		return this.contabilizado;
	}

	public void setContabilizado(Boolean contab) {
		this.contabilizado = contab;
	}
	
	public AsientoContable getAsiento(){
		return this.asiento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((iddetalle == null) ? 0 : iddetalle.hashCode());
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
		DetalleAsientoContable other = (DetalleAsientoContable) obj;
		if (iddetalle == null) {
			if (other.iddetalle != null)
				return false;
		} else if (!iddetalle.equals(other.iddetalle))
			return false;
		return true;
	}

	public static DetalleAsientoContable clone(DetalleAsientoContable detalle) {
		try {
			return (DetalleAsientoContable) detalle.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
