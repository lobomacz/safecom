package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Empleado;

@Repository
public class EmpleadoDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<Empleado> getListaEmpleados(){
		Query query = em.createQuery("select e From Empleado e");
		List<Empleado> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public Empleado reload(Empleado empleado){
		return em.find(Empleado.class, empleado.getId_empleado());
	}
	
	@Transactional
	public Empleado get(String id_empleado){
		return em.find(Empleado.class, id_empleado);
	}
	
	@Transactional
	public Empleado save(Empleado empleado){
		em.persist(empleado);
		return empleado;
	}
	
	@Transactional
	public Empleado update(Empleado empleado){
		empleado = em.merge(empleado);
		return empleado;
	}
	
	@Transactional
	public void delete(Empleado empleado){
		em.remove(empleado);
	}
}
