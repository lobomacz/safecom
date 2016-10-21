package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_movimientos_caja")
public class MovimientoCaja implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id_movimiento_caja;

	@Temporal(TemporalType.TIMESTAMP)
	Date fecha_hora_movimiento;

	BigDecimal monto;

	@Column(length = 45)
	String concepto;

	@Column
	String tipo_movimiento;

	@Column(length = 45)
	String referencia;

	@Column
	String tipo_pago;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_empleado")
	Empleado empleado;

	@ManyToOne
	@JoinColumn(name = "id_caja")
	Caja caja;

	public MovimientoCaja() {
	}

	public Integer getId_movimiento_caja() {
		return this.id_movimiento_caja;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTipo_movimiento() {
		return this.tipo_movimiento;
	}

	public void setTipo_movimiento(String tipomov) {
		this.tipo_movimiento = tipomov;
	}

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Date getFecha_hora_movimiento() {
		return this.fecha_hora_movimiento;
	}

	public void setFecha_hora_movimiento(Date fechahora) {
		this.fecha_hora_movimiento = fechahora;
	}

	public String getTipo_pago() {
		return this.tipo_pago;
	}

	public void setTipo_pago(String tipo) {
		this.tipo_pago = tipo;
	}
	
	public Caja getCaja() {
		return this.caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	
	public BigDecimal getMonto(){
		return this.monto;
	}
	
	public void setMonto(BigDecimal monto){
		this.monto = monto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((this.id_movimiento_caja == null) ? 0
						: this.id_movimiento_caja.hashCode());
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
		MovimientoCaja other = (MovimientoCaja) obj;
		if (this.id_movimiento_caja == null) {
			if (other.id_movimiento_caja != null)
				return false;
		} else if (!id_movimiento_caja.equals(other.id_movimiento_caja))
			return false;
		return true;
	}

	public static MovimientoCaja clone(MovimientoCaja obj) {
		try {
			return (MovimientoCaja) obj.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
