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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_caja")
public class Caja implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id_caja;

	@Column(length = 45)
	String descripcion;

	@Temporal(TemporalType.DATE)
	Date ultima_apertura;

	@Temporal(TemporalType.DATE)
	Date ultimo_arqueo;

	Boolean abierta;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "cuenta_contable")
	CuentaContable cuenta;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="caja")
	List<ArqueoCaja> arqueos;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="caja")
	List<MovimientoCaja> movimientos;

	public Caja() {
	}

	public Integer getId_caja() {
		return this.id_caja;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String desc) {
		this.descripcion = desc.toUpperCase();
	}

	public Date getUltima_apertura() {
		return this.ultima_apertura;
	}

	public void setUltima_apertura(Date apertura) {
		this.ultima_apertura = apertura;
	}

	public Date getUltimo_arqueo() {
		return this.ultimo_arqueo;
	}

	public void setUltimo_arqueo(Date farqueo) {
		this.ultimo_arqueo = farqueo;
	}

	public Boolean getAbierta() {
		return this.abierta;
	}

	public void setAbierta(Boolean abierta) {
		this.abierta = abierta;
	}

	public CuentaContable getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(CuentaContable cuenta) {
		this.cuenta = cuenta;
	}
	
	public List<ArqueoCaja> getArqueos(){
		return this.arqueos;
	}
	
	public List<MovimientoCaja> getMovimientos(){
		return this.movimientos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.id_caja == null) ? 0 : this.id_caja.hashCode());
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
		Caja other = (Caja) obj;
		if (this.id_caja == null) {
			if (other.id_caja != null)
				return false;
		} else if (!id_caja.equals(other.id_caja))
			return false;
		return true;
	}

	public static Caja clone(Caja caja) {
		try {
			return (Caja) caja.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
