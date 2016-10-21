package com.ecom.safecom.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.ArqueoCaja;

@Repository
public class ArqueoCajaDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<ArqueoCaja> listaTodo(Integer idcaja) {
		Query query = em
				.createQuery("from ArqueoCaja ac where ac.id_caja=:idcaja");
		query.setParameter("idcaja", idcaja);
		List<ArqueoCaja> resultado = query.getResultList();
		return resultado;
	}

	@Transactional(readOnly = true)
	public List<ArqueoCaja> listaPorFecha(Integer idcaja, Date finicio,
			Date ffinal) {
		String consulta = "from ArqueoCaja ac where ac.id_caja=:idcaja and ac.fecha_arqueo between :finicio and :ffinal";
		Query query = em.createQuery(consulta);
		query.setParameter("idcaja", idcaja);
		query.setParameter("finicio", finicio);
		query.setParameter("ffinal", ffinal);
		List<ArqueoCaja> resultado = query.getResultList();
		return resultado;
	}

	@Transactional(readOnly = true)
	public ArqueoCaja getLast(Integer idcaja) {
		ArqueoCaja resultado = null;
		try {
			Query query = em
					.createQuery("from ArqueoCaja aq where aq.id_arqueo=(select max(ac.id_arqueo) from ArqueoCaja ac where ac.caja.id_caja=:idCaja)");
			query.setParameter("idCaja", idcaja);
			resultado = (ArqueoCaja) query.getSingleResult();
		} catch (NoResultException ex) {
			
		}
		return resultado;
	}

	@Transactional
	public ArqueoCaja get(Integer idarqueo) {
		return em.find(ArqueoCaja.class, idarqueo);
	}

	@Transactional
	public ArqueoCaja reload(ArqueoCaja arqueo) {
		return em.find(ArqueoCaja.class, arqueo.getId_arqueo());
	}

	@Transactional
	public ArqueoCaja save(ArqueoCaja arq) {
		em.persist(arq);
		em.flush();
		return get(arq.getId_arqueo());
	}

	@Transactional
	public ArqueoCaja update(ArqueoCaja arq) {
		arq = em.merge(arq);
		return arq;
	}

	@Transactional
	public void delete(ArqueoCaja arq) {
		ArqueoCaja a = get(arq.getId_arqueo());
		if (a != null) {
			em.remove(a);
		}
	}
}
