package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.PrecioProducto;
import com.ecom.safecom.entity.PrecioProductoPK;
import com.ecom.safecom.entity.Producto;

@Repository
public class PrecioProductoDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<PrecioProducto> getListaPorProducto(Producto producto){
		Query query = em.createQuery("from PrecioProducto precio where precio.codigo_producto=@codigo");
		query.setParameter("@codigo", producto.getCodigo_producto());
		List<PrecioProducto> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public PrecioProducto reload(PrecioProducto precio){
		return em.find(PrecioProducto.class, precio.getPrecioPK());
	}
	
	@Transactional(readOnly=true)
	public PrecioProducto get(PrecioProductoPK pk){
		return em.find(PrecioProducto.class, pk);
	}
	
	@Transactional
	public PrecioProducto save(PrecioProducto precio){
		em.persist(precio);
		return precio;
	}
	
	@Transactional
	public PrecioProducto update(PrecioProducto precio){
		precio = em.merge(precio);
		return precio;
	}
	
	@Transactional
	public void delete(PrecioProducto precio){
		PrecioProducto p = get(precio.getPrecioPK());
		if(p!=null){
			em.remove(p);
		}
		
	}
}
