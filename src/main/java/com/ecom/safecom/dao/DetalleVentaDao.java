package com.ecom.safecom.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.DetalleVenta;
import com.ecom.safecom.entity.DetalleVentaPK;

@Repository
public class DetalleVentaDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public DetalleVenta reload(DetalleVenta detalle){
		detalle = em.find(DetalleVenta.class, detalle.getIddetalle());
		return detalle;
	}
	
	
	
	@Transactional(readOnly=true)
	public DetalleVenta get(DetalleVentaPK iddetalle){
		return em.find(DetalleVenta.class, iddetalle);
	}
	
	@Transactional
	public DetalleVenta guardar(DetalleVenta detalle){
		em.persist(detalle);
		return get(detalle.getIddetalle());
	}
	
	@Transactional
	public DetalleVenta actualizar(DetalleVenta detalle){
		detalle = em.merge(detalle);
		return detalle;
	}
	
	@Transactional
	public void eliminar(DetalleVenta detalle){
		DetalleVenta d = get(detalle.getIddetalle());
		if(d!=null){
			em.remove(d);
		}
	}

}
