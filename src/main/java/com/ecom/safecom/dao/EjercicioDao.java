package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Ejercicio;

@Repository
public class EjercicioDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public Ejercicio buscaEjercicioActivo(){
		Query consulta = em.createQuery("from Ejercicio e where e.activo=true");
		return (Ejercicio)consulta.getSingleResult();
	}
	
	@Transactional(readOnly = true)
	public List<Ejercicio> getListaEjercicios() {
		Query consulta = em.createQuery("from Ejercicio");
		List<Ejercicio> resultado = consulta.getResultList();
		return resultado;
	}

	@Transactional(readOnly = true)
	public Ejercicio reload(Ejercicio ejercicio) {
		return em.find(Ejercicio.class, ejercicio.getId_ejercicio());
	}

	@Transactional(readOnly = true)
	public Ejercicio get(Integer _id) {
		return em.find(Ejercicio.class, _id);
	}

	@Transactional
	public Ejercicio save(Ejercicio ejercicio) {
		em.persist(ejercicio);
		return ejercicio;
	}

	@Transactional
	public Ejercicio update(Ejercicio ejercicio) {
		Ejercicio e = em.merge(ejercicio);
		return e;
	}

	@Transactional
	public void delete(Ejercicio ejercicio) {
		Ejercicio e = get(ejercicio.getId_ejercicio());
		if (e != null) {
			em.remove(e);
		}
	}

}
