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
@Table(name = "tbl_arqueo_caja")
public class ArqueoCaja implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id_arqueo;

	@Temporal(TemporalType.DATE)
	Date fecha_arqueo;
	
	@Temporal(TemporalType.TIME)
	Date hora_arqueo;

	@Column(length = 100)
	String observaciones;
	
	String tipo_arqueo;

	BigDecimal monto_libros;

	BigDecimal total_arqueo;

	Integer billetes_500;

	Integer billetes_200;

	Integer billetes_100;

	Integer billetes_50;

	Integer billetes_20;

	Integer billetes_10;

	Integer monedas_5;

	Integer monedas_1;

	Integer monedas_50_centavos;

	Integer monedas_25_centavos;

	Integer monedas_10_centavos;
	
	Integer monedas_5_centavos;
	
	Integer monedas_1_centavo;
	
	BigDecimal efectivo;

	BigDecimal dolares;

	BigDecimal cheques;

	BigDecimal vouchers;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_empleado")
	Empleado empleado;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_supervisor")
	Empleado supervisor;

	@ManyToOne
	@JoinColumn(name = "id_caja")
	Caja caja;

	public ArqueoCaja() {
	}

	public Integer getId_arqueo() {
		return this.id_arqueo;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String obs) {
		this.observaciones = obs;
	}

	public BigDecimal getMonto_libros() {
		return this.monto_libros;
	}

	public void setMonto_libros(BigDecimal monto) {
		this.monto_libros = monto;
	}
	
	public String getTipo_arqueo(){
		return this.tipo_arqueo;
	}
	
	public void setTipo_arqueo(String tipo){
		this.tipo_arqueo = tipo;
	}

	public BigDecimal getTotal_arqueo() {
		return this.total_arqueo;
	}

	public void setTotal_arqueo(BigDecimal total) {
		this.total_arqueo = total;
	}

	public Date getFecha_arqueo() {
		return this.fecha_arqueo;
	}

	public void setFecha_arqueo(Date fecha) {
		this.fecha_arqueo = fecha;
	}
	
	public Date getHora_arqueo(){
		return this.hora_arqueo;
	}
	
	public void setHora_arqueo(Date hora){
		this.hora_arqueo = hora;
	}

	public Integer getBilletes_500() {
		return this.billetes_500;
	}

	public void setBilletes_500(Integer billetes) {
		this.billetes_500 = billetes;
	}

	public Integer getBilletes_200() {
		return this.billetes_200;
	}

	public void setBilletes_200(Integer billetes) {
		this.billetes_200 = billetes;
	}

	public Integer getBillentes_100() {
		return this.billetes_100;
	}

	public void setBilletes_100(Integer billetes) {
		this.billetes_100 = billetes;
	}

	public Integer getBilletes_50() {
		return this.billetes_50;
	}

	public void setBilletes_50(Integer billetes) {
		this.billetes_50 = billetes;
	}

	public Integer getBilletes_20() {
		return this.billetes_20;
	}

	public void setBilletes_20(Integer billetes) {
		this.billetes_20 = billetes;
	}

	public Integer getBilletes_10() {
		return this.billetes_10;
	}

	public void setBilletes_10(Integer billetes) {
		this.billetes_10 = billetes;
	}

	public Integer getMonedas_5() {
		return this.monedas_5;
	}

	public void setMonedas_5(Integer monedas) {
		this.monedas_5 = monedas;
	}

	public Integer getMonedas_1() {
		return this.monedas_1;
	}

	public void setMonedas_1(Integer monedas) {
		this.monedas_1 = monedas;
	}

	public Integer getMonedas_50_centavos() {
		return this.monedas_50_centavos;
	}

	public void setMonedas_50_centavos(Integer monedas) {
		this.monedas_50_centavos = monedas;
	}

	public Integer getMonedas_25_centavos() {
		return this.monedas_25_centavos;
	}

	public void setMonedas_25_centavos(Integer monedas) {
		this.monedas_25_centavos = monedas;
	}

	public Integer getMonedas_10_centavos() {
		return this.monedas_10_centavos;
	}

	public void setMonedas_10_centavos(Integer monedas) {
		this.monedas_10_centavos = monedas;
	}
	
	public Integer getMonedas_5_centavos(){
		return this.monedas_5_centavos;
	}
	
	public void setMonedas_5_centavos(Integer monedas){
		this.monedas_5_centavos = monedas;
	}
	
	public Integer getMonedas_1_centavo(){
		return this.monedas_1_centavo;
	}
	
	public void setMonedas_1_centavo(Integer monedas){
		this.monedas_1_centavo = monedas;
	}

	public BigDecimal getDolares() {
		return this.dolares;
	}

	public void setDolares(BigDecimal dolares) {
		this.dolares = dolares;
	}
	
	public BigDecimal getEfectivo(){
		return this.efectivo;
	}
	
	public void setEfectivo(BigDecimal efectivo){
		this.efectivo = efectivo;
	}

	public BigDecimal getCheques() {
		return this.cheques;
	}

	public void setCheques(BigDecimal cheques) {
		this.cheques = cheques;
	}

	public BigDecimal getVouchers() {
		return this.vouchers;
	}

	public void setVouchers(BigDecimal vouchers) {
		this.vouchers = vouchers;
	}

	public Caja getCaja() {
		return this.caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
	public Empleado getSupervisor(){
		return this.supervisor;
	}
	
	public void setSupervisor(Empleado supervisor){
		this.supervisor = supervisor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.id_arqueo == null) ? 0 : this.id_arqueo.hashCode());
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
		ArqueoCaja other = (ArqueoCaja) obj;
		if (this.id_arqueo == null) {
			if (other.id_arqueo != null)
				return false;
		} else if (!id_arqueo.equals(other.id_arqueo))
			return false;
		return true;
	}

	public static ArqueoCaja clone(ArqueoCaja obj) {
		try {
			return (ArqueoCaja) obj.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
