package com.ecom.safecom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_unidades_medida")
public class UnidadMedida implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id_unidad_medida;
	
	@Column(nullable=false)
	String descripcion_unidad;
	
	@Column(nullable=false)
	String descripcion_corta;
	
	@Column
	Boolean unidad_base;
	
	public UnidadMedida(){}
	
	public Integer getId_unidad_medida(){
		return this.id_unidad_medida;
	}
	
	public void setId_unidad_medida(Integer id){
		this.id_unidad_medida=id;
	}
	
	public String getDescripcion_unidad(){
		return this.descripcion_unidad;
	}
	
	public void setDescripcion_unidad(String descripcion){
		this.descripcion_unidad=descripcion.toUpperCase();
	}
	
	public String getDescripcion_corta(){
		return this.descripcion_corta;
	}
	
	public void setDescripcion_corta(String descripcion){
		this.descripcion_corta=descripcion.toUpperCase();
	}
	
	public Boolean getUnidad_base(){
		return this.unidad_base;
	}
	
	public void setUnidad_base(Boolean unidadbase){
		this.unidad_base=unidadbase;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_unidad_medida == null) ? 0 : id_unidad_medida.hashCode());
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
		UnidadMedida other = (UnidadMedida) obj;
		if (id_unidad_medida == null) {
			if (other.id_unidad_medida != null)
				return false;
		} else if (!id_unidad_medida.equals(other.id_unidad_medida))
			return false;
		return true;
	}

	public static UnidadMedida clone(UnidadMedida todo) {
		try {
			return (UnidadMedida) todo.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}
	
}
