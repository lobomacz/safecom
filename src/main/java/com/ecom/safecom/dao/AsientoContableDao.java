package com.ecom.safecom.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.AsientoContable;

@Repository
public class AsientoContableDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<AsientoContable> getAll(){
		Query query = em.createQuery("from AsientoContable a");
		List<AsientoContable> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<AsientoContable> getPorPeriodo(String idperiodo){
		String consulta = "select a from AsientoContable a where a.id_periodo=:idperiodo";
		Query query = em.createQuery(consulta);
		query.setParameter(":idperiodo", idperiodo);
		List<AsientoContable> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public AsientoContable reload(AsientoContable asiento){
		asiento = em.find(AsientoContable.class, asiento.getId_asiento());
		return asiento;
	}
	
	@Transactional(readOnly=true)
	public AsientoContable get(Integer _id){
		return em.find(AsientoContable.class, _id);
	}
	
	@Transactional
	public AsientoContable save(AsientoContable asiento){
		//Query query = em.createQuery("from AsientoContable where id_asiento=(select max(id) from AsientoContable)");
		em.persist(asiento);
		//asiento = (AsientoContable)query.getSingleResult();
		em.flush();
		return get(asiento.getId_asiento());
	}
	
	@Transactional
	public AsientoContable update(AsientoContable asiento){
		return em.merge(asiento);
	}
	
	@Transactional
	public void delete(AsientoContable asiento){
		AsientoContable a = get(asiento.getId_asiento());
		if(a!=null){
			em.remove(a);
		}
	}
}
