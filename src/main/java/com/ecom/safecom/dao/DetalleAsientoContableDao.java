package com.ecom.safecom.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContablePK;

@Repository
public class DetalleAsientoContableDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<DetalleAsientoContable> listaPorCuenta(String cuenta){
		String consulta = "from DetalleAsientoContable dac where dac.iddetalle.cuenta=:cuenta";
		Query query = em.createQuery(consulta);
		query.setParameter("cuenta", cuenta);
		List<DetalleAsientoContable> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<DetalleAsientoContable> listaAntesDeFecha(Date fecha){
		String consulta = "from DetalleAsientoContable dac where dac.asiento.fecha<:fecha_limite order by dac.iddetalle.cuenta";
		Query query = em.createQuery(consulta);
		query.setParameter(":fecha_limite", fecha);
		List<DetalleAsientoContable> resultado = query.getResultList();
		return resultado;
	}

	@Transactional(readOnly = true)
	public DetalleAsientoContable reload(DetalleAsientoContable detalle) {
		return em.find(DetalleAsientoContable.class, detalle.getIddetalle());
	}

	@Transactional(readOnly = true)
	public DetalleAsientoContable get(DetalleAsientoContablePK iddetalle) {
		return em.find(DetalleAsientoContable.class, iddetalle);
	}

	@Transactional
	public DetalleAsientoContable save(DetalleAsientoContable detalle) {
		em.persist(detalle);
		return detalle;
	}

	@Transactional
	public DetalleAsientoContable update(DetalleAsientoContable detalle) {
		detalle = em.merge(detalle);
		return detalle;
	}

	@Transactional
	public void delete(DetalleAsientoContable detalle) {
		detalle = reload(detalle);
		if (detalle != null) {
			em.remove(detalle);
		}
	}

}
