package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.UnidadMedida;

@Repository
public class UnidadMedidaDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<UnidadMedida> queryAll(){
		Query query = em.createQuery("from UnidadMedida u");
		List<UnidadMedida> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public List<UnidadMedida> getUnidadesBase(){
		Query query = em.createQuery("from UnidadMedida u where u.unidad_base = true");
		List<UnidadMedida> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public UnidadMedida reload(UnidadMedida unidad){
		return em.find(UnidadMedida.class, unidad.getId_unidad_medida());
	}
	
	@Transactional(readOnly=true)
	public UnidadMedida get(Integer _id){
		return em.find(UnidadMedida.class, _id);
	}
	
	@Transactional(readOnly=true)
	public UnidadMedida getByDescCorta(String desc){
		String consulta = "select u from UnidadMedida u where u.descripcion_corta=:corta";
		Query query = em.createQuery(consulta);
		query.setParameter("corta", desc);
		UnidadMedida resultado = (UnidadMedida)query.getSingleResult();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<String> getDescripcionCorta(){
		Query query = em.createQuery("select u.descripcion_corta from UnidadMedida u");
		List<String> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional
	public UnidadMedida save(UnidadMedida unidad){
		em.persist(unidad);
		em.flush();
		return get(unidad.getId_unidad_medida());
	}
	
	@Transactional
	public UnidadMedida update(UnidadMedida unidad){
		unidad = em.merge(unidad);
		return unidad;
	}
	
	@Transactional
	public void delete(UnidadMedida unidad){
		UnidadMedida u = get(unidad.getId_unidad_medida());
		if(u!=null){
			em.remove(u);
		}
	}
}
