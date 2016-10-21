package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Almacen;

@Repository
public class AlmacenDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<Almacen> getAll(){
		Query query = em.createQuery("from Almacen a");
		List<Almacen> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public Almacen reload(Almacen almacen){
		almacen = em.find(Almacen.class, almacen.getId_almacen());
		return almacen;
	}
	
	@Transactional(readOnly=true)
	public Almacen getPrincipal(){
		Query query = em.createQuery("from Almacen where principal=true");
		Almacen resultado = (Almacen)query.getSingleResult();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public Almacen get(Integer _id){
		return em.find(Almacen.class, _id);
	}
	
	@Transactional
	public void save(Almacen almacen){
		em.persist(almacen);
	}
	
	@Transactional
	public Almacen update(Almacen almacen){
		return em.merge(almacen);
	}
	
	@Transactional
	public void delete(Almacen almacen){
		Almacen a = get(almacen.getId_almacen());
		if(a!=null){
			em.remove(a);
		}
	}

}
