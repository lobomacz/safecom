package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Caja;

@Repository
public class CajaDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Caja> listaTodo() {
		Query query = em.createQuery("from Caja c");
		List<Caja> resultado = query.getResultList();
		return resultado;
	}

	@Transactional(readOnly = true)
	public Caja get(Integer id_caja) {
		return em.find(Caja.class, id_caja);
	}

	@Transactional(readOnly = true)
	public Caja reload(Caja caja) {
		return em.find(Caja.class, caja.getId_caja());
	}

	@Transactional
	public Caja save(Caja caja) {
		em.persist(caja);
		em.flush();
		return caja;//get(caja.getId_caja());
	}

	@Transactional
	public Caja update(Caja caja) {
		caja = em.merge(caja);
		return caja;
	}

	@Transactional
	public void delete(Caja caja) {
		Caja c = get(caja.getId_caja());
		if (c != null) {
			em.remove(c);
		}
	}

}
