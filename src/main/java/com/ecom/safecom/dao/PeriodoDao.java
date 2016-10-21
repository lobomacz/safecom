package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Periodo;


@Repository
public class PeriodoDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<Periodo> getListaPeriodos(){
		Query consulta = em.createQuery("from Periodo p order by p.numero");
		List<Periodo> res = consulta.getResultList();
		return res;
	}
	
	@Transactional(readOnly=true)
	public List<Periodo> getListaPeriodosAbiertos(){
		Query consulta = em.createQuery("from Periodo p where p.abierto=true order by p.numero");
		List<Periodo> res = consulta.getResultList();
		return res;
	}
	
	@Transactional(readOnly=true)
	public Periodo reload(Periodo periodo){
		return em.find(Periodo.class, periodo.getId());
	}
	
	@Transactional(readOnly=true)
	public Periodo get(String _id){
		return em.find(Periodo.class, _id);
	}
	
	@Transactional(readOnly=true)
	public Periodo getPeriodoAbierto(){
		Query query = em.createQuery("from Periodo p where p.abierto=true");
		Periodo resultado = (Periodo)query.getSingleResult();
		return resultado;
	}

	@Transactional
	public Periodo save(Periodo periodo){
		em.persist(periodo);
		return periodo;
	}
	
	@Transactional
	public Periodo update(Periodo periodo){
		Periodo per = em.merge(periodo);
		return per;
	}
	
	@Transactional
	public void delete(Periodo periodo){
		Periodo p = get(periodo.getId());
		if(p!=null){
			em.remove(p);
		}
	}
}
