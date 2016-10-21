package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transaction;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zul.Messagebox;

import com.ecom.safecom.entity.CuentaContable;

@Repository
public class CuentaContableDao {

	@PersistenceContext
	EntityManager em;

	@Transactional(readOnly = true)
	public List<CuentaContable> getAll() {
		Query query = em.createQuery("from CuentaContable c");
		List<CuentaContable> result = query.getResultList();
		return result;
	}
	
	@Transactional(readOnly=true)
	public CuentaContable getByName(String name){
		String consulta = "select c from CuentaContable c where c.nombre=:name";
		Query query = em.createQuery(consulta);
		query.setParameter("name", name.toUpperCase());
		return (CuentaContable)query.getSingleResult();
	}
	
	@Transactional(readOnly=true)
	public CuentaContable searchByName(String name){
		String consulta = "select c from CuentaContable c where c.nombre like :name";
		Query query = em.createQuery(consulta);
		query.setParameter("name", "%"+name+"%");
		return query.getResultList().size()>0?(CuentaContable)query.getResultList().get(0):null;
	}
	
	@Transactional(readOnly=true)
	public List<CuentaContable> listByName(String name){
		String consulta = "select c from CuentaContable c where c.nombre like :name";
		Query query = em.createQuery(consulta);
		query.setParameter("name", "%"+name.toUpperCase()+"%");
		return query.getResultList();
	}
	
	@Transactional(readOnly=true)
	public List<CuentaContable> getChildren(String padre){
		String consulta = "select c from CuentaContable c where c.cuenta_padre=:padre";
		Query query = em.createQuery(consulta);
		query.setParameter("padre", padre);
		List<CuentaContable> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<CuentaContable> listaCuentasBalance(){
		String consulta = "from CuentaContable c where c.tipo='activo' or c.tipo='pasivo' or c.tipo='capital' order by c.cuenta";
		Query query = em.createQuery(consulta);
		List<CuentaContable> resultado = query.getResultList();
		return resultado;
	}

	@Transactional(readOnly = true)
	public CuentaContable reload(CuentaContable cuenta) {
		return em.find(CuentaContable.class, cuenta.getCuenta());
	}

	@Transactional(readOnly = true)
	public CuentaContable get(String _id) {
		return em.find(CuentaContable.class, _id);
	}

	@Transactional
	public CuentaContable save(CuentaContable cuenta) {
		em.persist(cuenta);
		return cuenta;
	}

	@Transactional
	public CuentaContable update(CuentaContable cuenta) {
		cuenta = em.merge(cuenta);
		return cuenta;
	}

	@Transactional
	public void delete(CuentaContable cuenta) {
		CuentaContable c = get(cuenta.getCuenta());
		if (c != null) {
			em.remove(c);
		}
	}

	@Transactional
	public boolean saveCatalogo(List<CuentaContable> cuentas) {
		boolean resultado = false;
		String consulta = "delete CuentaContable";
		Query query = em.createQuery(consulta);

		try {
			int borrado = query.executeUpdate();
			
			if (borrado >= 0) {
				for (CuentaContable cuenta : cuentas) {
					save(cuenta);
				}

				resultado = true;
			}
		} catch (TransactionRequiredException trex) {
			Messagebox
					.show("Excepción de transaccion. " + trex.getMessage());
			return resultado;
		} catch (Exception ex) {
			Messagebox.show("Ha ocurrido un error. Excepción:"
					+ ex.getMessage());
			return resultado;
		}
		return resultado;
	}
}
