package com.ecom.safecom.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Compra;

@Repository
public class CompraDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<Compra> getAll(){
		Query query = em.createQuery("from Compra c order by c.fecha_compra asc");
		List<Compra> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Compra> getCanceladas(){
		Query query = em.createQuery("from Compra c where c.cancelado=true");
		List<Compra> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Compra> getPorFecha(Date fecha_inicio, Date fecha_final){
		String consulta = "from Compra c where c.fecha_compra between :finicio and :ffinal";
		Query query = em.createQuery(consulta);
		query.setParameter(":finicio", fecha_inicio);
		query.setParameter(":ffinal", fecha_final);
		List<Compra> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Compra> getCanceladosPorFecha(Date fecha_inicio, Date fecha_final){
		String consulta = "from Compra c where c.cancelado=true c.fecha_compra between :finicio and :ffinal";
		Query query = em.createQuery(consulta);
		query.setParameter(":finicio", fecha_inicio);
		query.setParameter(":ffinal", fecha_final);
		List<Compra> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Compra> getPorPeriodo(){
		String consulta = "select c from Compra c, Periodo p where p.activo=true and c.fecha_compra between p.fecha_inicio and p.fecha_final";
		Query query = em.createQuery(consulta);
		List<Compra> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Compra> getPorPeriodo(String idperiodo){
		String consulta = "select c from Compra c, Periodo p where p.id=:idperiodo and c.fecha_compra between p.fecha_inicio and p.fecha_final";
		Query query = em.createQuery(consulta);
		query.setParameter(":idperiodo", idperiodo);
		List<Compra> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public Compra reload(Compra compra){
		compra = em.find(Compra.class, compra.getId_compra());
		return compra;
	}
	
	@Transactional(readOnly=true)
	public Compra get(Integer _id){
		return em.find(Compra.class, _id);
	}
	
	@Transactional
	public void save(Compra compra){
		em.persist(compra);
	}
	
	@Transactional
	public Compra update(Compra compra){
		compra = em.merge(compra);
		return compra;
	}
	
	@Transactional
	public void delete(Compra compra){
		Compra c = get(compra.getId_compra());
		if(c!=null){
			em.remove(c);
		}
	}
	
}
