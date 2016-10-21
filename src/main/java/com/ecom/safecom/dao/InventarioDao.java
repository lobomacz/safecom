package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Inventario;
import com.ecom.safecom.entity.InventarioPK;

@Repository
public class InventarioDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<Inventario> getAll(Integer idalmacen){
		String consulta = "from Inventario i where i.id_inventario.id_almacen=:idalmacen";
		Query query = em.createQuery(consulta);
		query.setParameter("idalmacen", idalmacen);
		List<Inventario> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public Inventario reload(Inventario inventario){
		return em.find(Inventario.class, inventario.getId_inventario());
	}
	
	@Transactional(readOnly=true)
	public Inventario get(InventarioPK idinventario){
		return em.find(Inventario.class, idinventario);
	}
	
	@Transactional
	public Inventario save(Inventario inventario){
		em.persist(inventario);
		return inventario;
	}
	
	@Transactional
	public Inventario update(Inventario inventario){
		inventario = em.merge(inventario);
		return inventario;
	}
	
	@Transactional
	public void delete(Inventario inventario){
		inventario = reload(inventario);
		if(inventario != null){
			em.remove(inventario);
		}
	}

}
