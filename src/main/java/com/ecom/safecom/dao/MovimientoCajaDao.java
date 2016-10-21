package com.ecom.safecom.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.MovimientoCaja;

@Repository
public class MovimientoCajaDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<MovimientoCaja> listaTodo(Integer idcaja) {
		Query query = em.createQuery("from MovimientoCaja mc where mc.caja.id_caja=:idcaja");
		query.setParameter("idcaja", idcaja);
		List<MovimientoCaja> resultado = query.getResultList();
		return resultado;
	}

	@Transactional(readOnly = true)
	public List<MovimientoCaja> listaPorFecha(Integer idcaja, Date finicio, Date ffinal) {
		String consulta = "from MovimientoCaja mc where mc.caja.id_caja=:idcaja and mc.fecha_hora_movimiento between :finicio and :ffinal";
		Query query = em.createQuery(consulta);
		query.setParameter("idcaja", idcaja);
		query.setParameter("finicio", finicio);
		query.setParameter("ffinal", ffinal);
		List<MovimientoCaja> resultado = query.getResultList();
		return resultado;
	}

	@Transactional(readOnly = true)
	public MovimientoCaja get(Integer _id) {
		return em.find(MovimientoCaja.class, _id);
	}

	@Transactional(readOnly = true)
	public MovimientoCaja reload(MovimientoCaja movimiento) {
		return em
				.find(MovimientoCaja.class, movimiento.getId_movimiento_caja());
	}

	@Transactional
	public MovimientoCaja save(MovimientoCaja movcaja) {
		em.persist(movcaja);
		em.flush();
		return get(movcaja.getId_movimiento_caja());
	}

	@Transactional
	public MovimientoCaja update(MovimientoCaja movcaja) {
		movcaja = em.merge(movcaja);
		return movcaja;
	}

	@Transactional
	public void delete(MovimientoCaja movcaja) {
		MovimientoCaja mc = get(movcaja.getId_movimiento_caja());
		if (mc != null) {
			em.remove(mc);
		}
	}
}
