package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.EntradaMateriales;
import com.ecom.safecom.entity.SalidaMateriales;

@Repository
public class SalidaMaterialesDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<SalidaMateriales> getAll() {
		Query query = em.createQuery("from Salida s");
		List<SalidaMateriales> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly = true)
	public List<SalidaMateriales> getAllPorAlmacen(Integer idalmacen){
		String consulta = "select s from SalidaMateriales s where id_almacen=:almacen";
		Query query = em.createQuery(consulta);
		query.setParameter("almacen", idalmacen);
		List<SalidaMateriales> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<SalidaMateriales> getPendientesPorAlmacen(Integer almacen){
		String consulta = "select s from SalidaMateriales s where id_almacen=:almacen and s.despachado=false";
		Query query = em.createQuery(consulta);
		query.setParameter("almacen", almacen);
		List<SalidaMateriales> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public SalidaMateriales reload(SalidaMateriales salida){
		return em.find(SalidaMateriales.class, salida.getId_salida());
	}
	
	@Transactional(readOnly=true)
	public SalidaMateriales get(Integer idsalida){
		return em.find(SalidaMateriales.class, idsalida);
	}
	
	@Transactional
	public SalidaMateriales save(SalidaMateriales salida){
		em.persist(salida);
		em.flush();
		return salida;
	}
	
	@Transactional
	public SalidaMateriales update(SalidaMateriales salida){
		salida = em.merge(salida);
		return salida;
	}
	
	@Transactional
	public void delete(SalidaMateriales salida){
		salida = reload(salida);
		if(salida!=null){
			em.remove(salida);
		}
	}

}
