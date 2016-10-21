package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Producto;

@Repository
public class ProductoDao {

	@PersistenceContext
	EntityManager em;
	
	
	@Transactional(readOnly=true)
	public List<Producto> getListaProductos(){
		Query query = em.createQuery("select pr from Producto pr");
		List<Producto> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public List<Producto> getListaProductos(String descripcion){
		String consulta = "select p from Producto p where p.descripcion like :descrip";
		//Query query = em.createQuery("select p from Producto p where p.descripcion like '%"+descripcion+"%'");
		//query.setParameter("descrip", "'"+descripcion+"%'");
		Query query = em.createQuery(consulta);
		query.setParameter("descrip", "%"+descripcion.toUpperCase()+"%");
		List<Producto> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public List<Producto> getListaPorCodigo(String codigo){
		String consulta = "select p from Producto p where p.codigo_producto like :codigo";
		Query query = em.createQuery(consulta);
		query.setParameter("codigo", "%"+codigo+"%");
		List<Producto> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public List<String> getCodigos(){
		Query query = em.createQuery("select pr.codigo_producto from Producto pr");
		List<String> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public Producto reload(Producto producto){
		return em.find(Producto.class, producto.getCodigo_producto());
	}
	
	@Transactional(readOnly=true)
	public Producto get(String codigo_producto){
		return em.find(Producto.class, codigo_producto);
	}
	
	@Transactional
	public Producto save(Producto producto){
		em.persist(producto);
		return producto;
	}
	
	@Transactional
	public Producto update(Producto producto){
		producto = em.merge(producto);
		return producto;
	}
	
	@Transactional
	public void delete(Producto producto){
		Producto p = get(producto.getCodigo_producto());
		if(p!=null){
			em.remove(p);
		}
		//em.remove(producto);
	}
}
