package com.ecom.safecom.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Telefono;

@Repository
public class TelefonoDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public Telefono get(String telefono){
		return em.find(Telefono.class, telefono);
	}
	
	@Transactional(readOnly=true)
	public Telefono reload(Telefono telefono){
		return em.find(Telefono.class, telefono.getTelefono());
	}
	
	@Transactional
	public Telefono save(Telefono telefono){
		em.persist(telefono);
		return telefono;
	}
	
	@Transactional
	public Telefono update(Telefono telefono){
		telefono = em.merge(telefono);
		return telefono;
	}
	
	@Transactional
	public void delete(Telefono telefono){
		Telefono tel = reload(telefono);
		if(tel!=null){
			em.remove(tel);
		}
	}
}
