package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_salidas_materiales")
public class SalidaMateriales implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id_salida;

	@Temporal(value = TemporalType.DATE)
	Date fecha_salida;

	@Column(length = 200)
	String observaciones;

	@Temporal(value = TemporalType.DATE)
	Date fecha_entregado;

	Boolean despachado;

	@Column(length = 15)
	String referencia;

	@ManyToOne
	@JoinColumn(name = "nombre_usuario")
	Usuario usuario;

	/*
	@ManyToOne(optional = true)
	@JoinColumn(name = "id_venta")
	Venta venta;*/

	@ManyToOne
	@JoinColumn(name = "id_almacen")
	Almacen almacen;

	@OneToOne(optional = true,cascade=CascadeType.REMOVE)
	@JoinColumn(name = "id_asiento")
	AsientoContable asiento;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_salida")
	List<DetalleSalidaMateriales> detalle_salida;
	
	@ManyToMany(targetEntity=Venta.class,cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name="tbl_salidas_materiales_has_tbl_ventas",joinColumns=@JoinColumn(name="id_salida"),
	inverseJoinColumns=@JoinColumn(name="id_venta"))
	List<Venta> ventas;
	

	public SalidaMateriales() {
	}

	public Integer getId_salida() {
		return this.id_salida;
	}

	public Date getFecha_salida() {
		return this.fecha_salida;
	}

	public void setFecha_salida(Date fecha) {
		this.fecha_salida = fecha;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String obs) {
		this.observaciones = obs;
	}

	public Date getFecha_entregado() {
		return this.fecha_entregado;
	}

	public void setFecha_entregado(Date fecha) {
		this.fecha_entregado = fecha;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Almacen getAlmacen() {
		return this.almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public AsientoContable getAsiento() {
		return this.asiento;
	}

	public void setAsiento(AsientoContable asiento) {
		this.asiento = asiento;
	}
	
	public Boolean getDespachado(){
		return this.despachado;
	}
	
	public void setDespachado(Boolean despachado){
		this.despachado = despachado;
	}
	
	public String getReferencia(){
		return this.referencia;
	}
	
	public void setReferencia(String referencia){
		this.referencia = referencia;
	}
	
	public List<DetalleSalidaMateriales> getDetalle_salida(){
		return this.detalle_salida;
	}
	
	public void setDetalle_salida(List<DetalleSalidaMateriales> detalle){
		this.detalle_salida = detalle;
	}
	
	@Transient
	public BigDecimal getMonto_total(){
		BigDecimal total = new BigDecimal(0);
		for(DetalleSalidaMateriales salida : this.detalle_salida){
			total = total.add(salida.monto_total);
		}
		return total;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.id_salida  == null) ? 0 : this.id_salida.hashCode());
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
		SalidaMateriales other = (SalidaMateriales) obj;
		if (this.id_salida == null) {
			if (other.id_salida != null)
				return false;
		} else if (!id_salida.equals(other.id_salida))
			return false;
		return true;
	}

	public static SalidaMateriales clone(SalidaMateriales obj) {
		try {
			return (SalidaMateriales) obj.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

}
