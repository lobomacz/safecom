package com.ecom.safecom.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.DetalleEntradaMateriales;
import com.ecom.safecom.entity.DetalleEntradaMaterialesPK;

@Repository
public class DetalleEntradaMaterialesDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public DetalleEntradaMateriales reload(DetalleEntradaMateriales detalle){
		detalle = em.find(DetalleEntradaMateriales.class, detalle.getId());
		return detalle;
	}
	
	@Transactional(readOnly=true)
	public DetalleEntradaMateriales get(DetalleEntradaMaterialesPK _id){
		return em.find(DetalleEntradaMateriales.class, _id);
	}
	
	@Transactional
	public DetalleEntradaMateriales save(DetalleEntradaMateriales detalle){
		em.persist(detalle);
		return get(detalle.getId());
	}
	
	@Transactional
	public DetalleEntradaMateriales update(DetalleEntradaMateriales detalle){
		detalle = em.merge(detalle);
		return detalle;
	}
	
	@Transactional
	public void delete(DetalleEntradaMateriales detalle){
		DetalleEntradaMateriales d = get(detalle.getId());
		if(d!=null){
			em.remove(d);
		}
	}
}
