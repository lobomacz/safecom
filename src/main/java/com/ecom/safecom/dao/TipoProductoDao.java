package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.TipoProducto;

@Repository
public class TipoProductoDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<TipoProducto> getListaTipoProducto(){
		Query query = em.createQuery("select tp from TipoProducto tp");
		List<TipoProducto> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public TipoProducto reload(TipoProducto tipo_producto){
		return em.find(TipoProducto.class, tipo_producto.getId_tipo_producto());
	}
	
	@Transactional(readOnly=true)
	public TipoProducto get(Integer id_tipo_producto){
		return em.find(TipoProducto.class, id_tipo_producto);
	}
	
	@Transactional
	public TipoProducto save(TipoProducto tipo_producto){
		em.persist(tipo_producto);
		return tipo_producto;
	}
	
	@Transactional
	public TipoProducto update(TipoProducto tipo_producto){
		tipo_producto=em.merge(tipo_producto);
		return tipo_producto;
	}
	
	@Transactional
	public void delete(TipoProducto tipo_producto){
		TipoProducto tp = get(tipo_producto.getId_tipo_producto());
		if(tp!=null){
			em.remove(tp);
		}
	}
}
