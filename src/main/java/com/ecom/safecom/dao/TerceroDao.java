package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Tercero;

@Repository
public class TerceroDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<Tercero> getAll(){
		Query query = em.createQuery("select t from Tercero t");
		List<Tercero> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public Tercero reload(Tercero tercero){
		return em.find(Tercero.class, tercero.getId_tercero());
	}
	
	@Transactional(readOnly=true)
	public Tercero get(Integer _id){
		return em.find(Tercero.class, _id);
	}
	
	@Transactional(readOnly=true)
	public List<Tercero> getTipo(String tipo){
		String consulta = "select t from Tercero t where t.tipo=:tipotercero";
		Query query = em.createQuery(consulta);
		query.setParameter("tipotercero", tipo);
		List<Tercero> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional
	public void save(Tercero tercero){
		em.persist(tercero);
	}
	
	@Transactional
	public Tercero update(Tercero tercero){
		tercero = em.merge(tercero);
		return tercero;
	}
	
	@Transactional
	public void delete(Tercero tercero){
		Tercero t = get(tercero.getId_tercero());
		if(t!=null){
			em.remove(t);
		}
	}

}
