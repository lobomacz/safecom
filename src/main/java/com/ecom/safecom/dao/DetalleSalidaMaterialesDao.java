package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.DetalleSalidaMateriales;
import com.ecom.safecom.entity.DetalleSalidaMaterialesPK;

@Repository
public class DetalleSalidaMaterialesDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<DetalleSalidaMateriales> getDetalle(Integer idsalida) {
		String consulta = "select ds from DetalleSalidaMateriales ds where ds.id_salida=:idsalida";
		Query query = em.createQuery(consulta);
		query.setParameter(":idsalida", idsalida);
		List<DetalleSalidaMateriales> resultado = query.getResultList();
		return resultado;
	}

	@Transactional(readOnly = true)
	public DetalleSalidaMateriales reload(DetalleSalidaMateriales detalle) {
		return em.find(DetalleSalidaMateriales.class, detalle.getIddetalle());
	}

	@Transactional(readOnly = true)
	public DetalleSalidaMateriales get(DetalleSalidaMaterialesPK iddetalle) {
		return em.find(DetalleSalidaMateriales.class, iddetalle);
	}

	@Transactional
	public DetalleSalidaMateriales save(DetalleSalidaMateriales detalle) {
		em.persist(detalle);
		return get(detalle.getIddetalle());
	}

	@Transactional
	public DetalleSalidaMateriales update(DetalleSalidaMateriales detalle) {
		detalle = em.merge(detalle);
		return detalle;
	}
	
	@Transactional
	public void delete(DetalleSalidaMateriales detalle){
		detalle = get(detalle.getIddetalle());
		if(detalle!=null){
			em.remove(detalle);
		}
	}
}
