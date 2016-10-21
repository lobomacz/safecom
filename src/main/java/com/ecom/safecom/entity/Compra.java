package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_compras")
public class Compra implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id_compra;
	
	@Column(length=15)
	String documento_referencia;
	
	@Temporal(TemporalType.DATE)
	Date fecha_compra;
	
	@Column
	BigDecimal subtotal;
	
	@Column
	BigDecimal iva;
	
	@Column
	BigDecimal total;
	
	@Column
	String tipo_pago;
	
	Boolean cancelado;
	
	Boolean recibido;
	
	Boolean anulado;
	
	@Column(length=200)
	String observaciones;
	
	Boolean proforma;
	
	@OneToOne(optional=true)
	@JoinColumn(name="id_asiento")
	AsientoContable asiento;
	
	@ManyToOne
	@JoinColumn(name="id_empleado")
	Empleado empleado;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="id_proveedor")
	Tercero proveedor;
	
	public  Compra(){}
	
	public Integer getId_compra(){
		return this.id_compra;
	}
	
	public Date getFecha_compra(){
		return this.fecha_compra;
	}
	
	public void setFecha_compra(Date fecha_compra){
		this.fecha_compra = fecha_compra;
	}
	
	public BigDecimal getSubtotal(){
		return this.subtotal;
	}
	
	public void setSubtotal(BigDecimal subtotal){
		this.subtotal = subtotal;
	}
	
	public BigDecimal getIva(){
		return this.iva;
	}
	
	public void setIva(BigDecimal iva){
		this.iva = iva;
	}
	
	public BigDecimal getTotal(){
		return this.total;
	}
	
	public void setTotal(BigDecimal total){
		this.total = total;
	}
	
	public String getObservaciones(){
		return this.observaciones;
	}
	
	public void setObservaciones(String obs){
		this.observaciones = obs.toUpperCase();
	}
	
	public String getTipo_pago(){
		return this.tipo_pago;
	}
	
	public void setTipo_pago(String tipo){
		this.tipo_pago = tipo;
	}
	
	public Boolean getCancelado(){
		return this.cancelado;
	}
	
	public void setCancelado(Boolean cancelado){
		this.cancelado = cancelado;
	}
	
	public Boolean getAnulado(){
		return this.anulado;
	}
	
	public void setAnulado(Boolean anulado){
		this.anulado = anulado;
	}
	
	public Boolean getRecibido(){
		return this.recibido;
	}
	
	public void setRecibido(Boolean recibido){
		this.recibido = recibido;
	}
	
	public Boolean getProforma(){
		return this.proforma;
	}
	
	public void setProforma(Boolean proforma){
		this.proforma = proforma;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.id_compra == null) ? 0 : this.id_compra.hashCode());
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
		Compra other = (Compra) obj;
		if (this.id_compra == null) {
			if (other.id_compra != null)
				return false;
		} else if (!id_compra.equals(other.id_compra))
			return false;
		return true;
	}

	public static Compra clone(Compra compra) {
		try {
			return (Compra) compra.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
