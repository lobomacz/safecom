package com.ecom.safecom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbl_productos")
public class Producto implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length=15)
	String codigo_producto;
	
	@Column(length=45,nullable=false)
	String descripcion;
	
	BigDecimal costo;
	
	Float maximo;
	
	Float minimo;
	
	Boolean para_combo;
	
	Boolean exento;
	
	String imagen_url;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="tipo_producto")
	TipoProducto tipoProducto;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="codigo_producto")
	List<PrecioProducto> preciosProducto;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_unidad_medida")
	UnidadMedida unidadMedida;
	
	@OneToMany(mappedBy="producto")
	List<DetalleVenta> detallesVenta;
	
	public Producto(){}
	
	public String getCodigo_producto(){
		return this.codigo_producto;
	}
	
	public void setCodigo_producto(String codigo_producto){
		this.codigo_producto=codigo_producto.toUpperCase();
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion.toUpperCase();
	}
	
	public BigDecimal getCosto(){
		return this.costo;
	}
	
	public void setCosto(BigDecimal costo){
		this.costo=costo;
	}
	
	public Float getMaximo(){
		return this.maximo;
	}
	
	public void setMaximo(Float maximo){
		this.maximo=maximo;
	}
	
	public Float getMinimo(){
		return this.minimo;
	}
	
	public void setMinimo(Float minimo){
		this.minimo=minimo;
	}
	
	public Boolean getPara_combo(){
		return this.para_combo;
	}
	
	public void setPara_combo(Boolean para_combo){
		this.para_combo=para_combo;
	}
	
	public Boolean getExento(){
		return this.exento;
	}
	
	public void setExento(Boolean exento){
		this.exento = exento;
	}
	
	public String getImagen_url(){
		return this.imagen_url;
	}
	
	public void setImagen_url(String imagen_url){
		this.imagen_url=imagen_url;
	}
	
	public TipoProducto getTipoProducto(){
		return this.tipoProducto;
	}
	
	public void setTipoProducto(TipoProducto tipoProducto){
		this.tipoProducto=tipoProducto;
	}
	
	public List<PrecioProducto> getListaPrecios(){
		return this.preciosProducto;
	}
	
	public UnidadMedida getUnidadMedida(){
		return this.unidadMedida;
	}
	
	public void setUnidadMedida(UnidadMedida unidad){
		this.unidadMedida=unidad;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo_producto == null) ? 0 : codigo_producto.hashCode());
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
		Producto other = (Producto) obj;
		if (codigo_producto == null) {
			if (other.codigo_producto != null)
				return false;
		} else if (!codigo_producto.equals(other.codigo_producto))
			return false;
		return true;
	}

	public static Producto clone(Producto todo) {
		try {
			return (Producto) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
	
}
