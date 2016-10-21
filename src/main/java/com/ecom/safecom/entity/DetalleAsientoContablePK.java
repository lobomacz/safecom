package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DetalleAsientoContablePK implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	Integer id_asiento;

	String cuenta;

	public DetalleAsientoContablePK() {
	}

	public DetalleAsientoContablePK(Integer asiento, String cuenta) {
		this.id_asiento = asiento;
		this.cuenta = cuenta;
	}

	public Integer getId_asiento() {
		return this.id_asiento;
	}

	public void setId_asiento(Integer idasiento) {
		this.id_asiento = idasiento;
	}

	public String getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		String baseVal = id_asiento.toString() + cuenta;
		int result = 1;
		result = prime * result + ((baseVal == null) ? 0 : baseVal.hashCode());
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
		DetalleAsientoContablePK other = (DetalleAsientoContablePK) obj;
		if (id_asiento == null && cuenta == null) {
			if (other.id_asiento != null && other.cuenta != null)
				return false;
		} else if (!id_asiento.equals(other.id_asiento)
				&& !cuenta.equals(other.cuenta))
			return false;
		return true;
	}

}
