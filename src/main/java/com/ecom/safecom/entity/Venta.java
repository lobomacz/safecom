package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_ventas")
public class Venta implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id_venta;

	@Column(length = 45)
	String numero_factura;

	@Temporal(value = TemporalType.DATE)
	Date fecha_venta;
	
	@Temporal(value=TemporalType.DATE)
	Date fecha_entregado;

	@Column
	BigDecimal subtotal;

	@Column
	BigDecimal iva;

	@Column
	BigDecimal total;

	@Column
	String tipo_pago;

	Boolean proforma;

	Boolean credito;

	Boolean cancelado;

	Boolean entregado;

	Boolean anulado;
	
	Boolean exonerado;

	@Column(length = 200)
	String observaciones;

	@ManyToOne
	@JoinColumn(name = "id_empleado")
	Empleado empleado;

	@OneToOne(optional = true)
	@JoinColumn(name = "id_asiento")
	AsientoContable asiento;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	Tercero cliente;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="id_almacen")
	Almacen almacen;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "venta")
	List<DetalleVenta> detalle;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "ventas", targetEntity = SalidaMateriales.class)
	List<SalidaMateriales> salidas;

	public Venta() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId_venta() {
		return this.id_venta;
	}

	public Date getFecha_venta() {
		return this.fecha_venta;
	}

	public void setFecha_venta(Date fecha) {
		this.fecha_venta = fecha;
	}
	
	public Date getFecha_entregado(){
		return this.fecha_entregado;
	}
	
	public void setFecha_entregado(Date fecha){
		this.fecha_entregado = fecha;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getIva() {
		return this.iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getTipo_pago() {
		return this.tipo_pago;
	}

	public void setTipo_pago(String tipo) {
		this.tipo_pago = tipo.toLowerCase();
	}

	public Boolean getProforma() {
		return this.proforma;
	}

	public void setProforma(Boolean proforma) {
		this.proforma = proforma;
	}

	public Boolean getCredito() {
		return this.credito;
	}

	public void setCredito(Boolean credito) {
		this.credito = credito;
	}

	public Boolean getEntregado() {
		return this.entregado;
	}

	public void setEntregado(Boolean entregado) {
		this.entregado = entregado;
	}

	public Boolean getCancelado() {
		return this.cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}

	public Boolean getAnulado() {
		return this.anulado;
	}

	public void setAnulado(Boolean anulado) {
		this.anulado = anulado;
	}
	
	public Boolean getExonerado(){
		return this.exonerado;
	}
	
	public void setExonerado(Boolean exonerado){
		this.exonerado = exonerado;
	}

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String obs) {
		this.observaciones = obs;
	}

	public String getNumero_factura() {
		return this.numero_factura;
	}

	public void setNumero_factura(String factura) {
		this.numero_factura = factura;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public AsientoContable getAsiento() {
		return this.asiento;
	}

	public void setAsiento(AsientoContable asiento) {
		this.asiento = asiento;
	}

	public Tercero getCliente() {
		return this.cliente;
	}

	public void setCliente(Tercero cliente) {
		this.cliente = cliente;
	}

	public List<DetalleVenta> getDetalle() {
		return this.detalle;
	}

	public void setDetalle(List<DetalleVenta> detalle) {
		this.detalle = detalle;
	}

	public List<SalidaMateriales> getSalidas() {
		return this.salidas;
	}

	public void setSalidas(List<SalidaMateriales> salidas) {
		this.salidas = salidas;
	}

	public Almacen getAlmacen() {
		return this.almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.id_venta == null) ? 0 : this.id_venta.hashCode());
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
		Venta other = (Venta) obj;
		if (this.id_venta == null) {
			if (other.id_venta != null)
				return false;
		} else if (!id_venta.equals(other.id_venta))
			return false;
		return true;
	}

	public static Venta clone(Venta obj) {
		try {
			return (Venta) obj.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
