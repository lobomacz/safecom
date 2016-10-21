package com.ecom.safecom.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Venta;

@Repository
public class VentaDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<Venta> getAll(){
		Query query = em.createQuery("from Venta v where v.proforma=false and v.entregado=false and v.anulado=false and v.cancelado=false and v.credito=false");
		List<Venta> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Venta> getAllCredito(){
		Query query = em.createQuery("from Venta v where v.proforma=false and v.entregado=true and v.anulado=false and v.cancelado=false and v.credito=true");
		List<Venta> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Venta> getCanceladas(){
		Query query = em.createQuery("from Venta v where v.cancelado=true");
		List<Venta> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Venta> getPorEmpleado(String idempleado){
		String consulta = "from Venta v where v.id_empleado=:empleado and v.proforma=false and v.entregado=false";
		Query query = em.createQuery(consulta);
		query.setParameter(":empleado", idempleado);
		List<Venta> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Venta> getPorFecha(Date finicio,Date ffinal){
		String consulta = "select v from Venta v where v.fecha_venta between :finicio and :ffinal";
		Query query = em.createQuery(consulta);
		query.setParameter(":finicio", finicio);
		query.setParameter(":ffinal", ffinal);
		List<Venta> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Venta> getCanceladosPorFecha(Date finicio,Date ffinal){
		String consulta = "select v from Venta v where v.cancelado=true and v.fecha_venta between :finicio and :ffinal";
		Query query = em.createQuery(consulta);
		query.setParameter(":finicio", finicio);
		query.setParameter(":ffinal", ffinal);
		List<Venta> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Venta> getPorPeriodo(String idperiodo){
		String consulta = "select v from Venta v, Periodo p where p.id=:idperiodo and v.fecha_compra between p.fecha_inicio and p.fecha_final";
		Query query = em.createQuery(consulta);
		query.setParameter(":idperiodo", idperiodo);
		List<Venta> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Venta> getProformas(){
		String consulta = "from Venta v where v.proforma=true";
		Query query = em.createQuery(consulta);
		List<Venta> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<Venta> getProformasPorEmpleado(String idempleado){
		String consulta = "from Venta v where v.id_empleado=:empleado and v.proforma=true";
		Query query = em.createQuery(consulta);
		query.setParameter(":empleado", idempleado);
		List<Venta> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public Venta reload(Venta venta){
		return em.find(Venta.class, venta.getId_venta());
	}
	
	@Transactional(readOnly=true)
	public Venta get(Integer idventa){
		return em.find(Venta.class, idventa);
	}
	
	@Transactional
	public Venta save(Venta venta){
		em.persist(venta);
		em.flush();
		return get(venta.getId_venta());
	}
	
	@Transactional
	public Venta update(Venta venta){
		venta = em.merge(venta);
		return venta;
	}
	
	@Transactional
	public void delete(Venta venta){
		venta = reload(venta);
		if(venta!=null){
			em.remove(venta);
		}
	}

}
