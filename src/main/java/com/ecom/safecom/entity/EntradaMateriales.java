package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ecom.safecom.dao.AsientoContableDao;

@Entity
@Table(name = "tbl_entradas_materiales")
public class EntradaMateriales implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id_entrada;

	@Column
	@Temporal(TemporalType.DATE)
	Date fecha_entrada;
	
	@Column(length=15)
	String referencia;

	@Column
	String observaciones;

	@Column
	@Temporal(TemporalType.DATE)
	Date fecha_recibido;

	@Column
	Boolean recibido;

	@ManyToOne
	@JoinColumn(name = "nombre_usuario")
	Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_almacen")
	Almacen almacen;
	
	@OneToOne(optional=true)
	@JoinColumn(name="id_asiento")
	AsientoContable asiento;
	
	/*@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="id_compra")
	Compra compra;*/
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="entrada",fetch=FetchType.EAGER)
	List<DetalleEntradaMateriales> detalle;

	public EntradaMateriales() {
	}

	public Integer getId_entrada() {
		return this.id_entrada;
	}

	public Date getFecha_entrada() {
		return this.fecha_entrada;
	}

	public void setFecha_entrada(Date fecha) {
		this.fecha_entrada = fecha;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String obs) {
		this.observaciones = obs.toUpperCase();
	}

	public Date getFecha_recibido() {
		return this.fecha_recibido;
	}

	public void setFecha_recibido(Date fecha) {
		this.fecha_recibido = fecha;
	}

	public Boolean getRecibido() {
		return this.recibido;
	}

	public void setRecibido(Boolean recibido) {
		this.recibido = recibido;
	}
	
	public String getReferencia(){
		return this.referencia;
	}
	
	public void setReferencia(String referencia){
		this.referencia = referencia.toUpperCase();
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Almacen getAlmacen() {
		return this.almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}
	
	public AsientoContable getAsiento(){
		return this.asiento;
	}
	
	public void setAsiento(AsientoContable asiento){
		this.asiento = asiento;
	}
	
	/*
	public Compra getCompra(){
		return this.compra;
	}
	
	public void setCompra(Compra compra){
		this.compra = compra;
	}*/
	
	public List<DetalleEntradaMateriales> getDetalle(){
		return this.detalle;
	}
	
	public void setDetalle(List<DetalleEntradaMateriales> detalle){
		this.detalle = detalle;
	}
	
	@Transient
	public BigDecimal getMonto_total(){
		BigDecimal total = new BigDecimal(0);
		for(DetalleEntradaMateriales detalle : this.detalle){
			total = total.add(detalle.costo_total);
		}
		return total;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.id_entrada == null) ? 0 : this.id_entrada.hashCode());
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
		EntradaMateriales other = (EntradaMateriales) obj;
		if (this.id_entrada == null) {
			if (other.id_entrada != null)
				return false;
		} else if (!id_entrada.equals(other.id_entrada))
			return false;
		return true;
	}

	public static EntradaMateriales clone(EntradaMateriales entrada) {
		try {
			return (EntradaMateriales) entrada.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
}
