package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.EntradaMateriales;

@Repository
public class EntradaMaterialesDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<EntradaMateriales> getAll(){
		Query query = em.createQuery("from EntradaMateriales entrada");
		List<EntradaMateriales> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<EntradaMateriales> getAllPorAlmacen(Integer idalmacen){
		String consulta = "select entrada from EntradaMateriales entrada where id_almacen=:almacen";
		Query query = em.createQuery(consulta);
		query.setParameter("almacen", idalmacen);
		List<EntradaMateriales> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public List<EntradaMateriales> getPendientesPorAlmacen(Integer almacen){
		String consulta = "select entrada from EntradaMateriales entrada where id_almacen=:almacen and entrada.recibido=false";
		Query query = em.createQuery(consulta);
		query.setParameter("almacen", almacen);
		List<EntradaMateriales> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public EntradaMateriales reload(EntradaMateriales entrada){
		entrada = em.find(EntradaMateriales.class, entrada.getId_entrada());
		return entrada;
	}
	
	@Transactional(readOnly=true)
	public EntradaMateriales get(Integer _id){
		return em.find(EntradaMateriales.class, _id);
	}
	
	@Transactional
	public EntradaMateriales save(EntradaMateriales entrada){
		em.persist(entrada);
		//Query query = em.createQuery("from EntradaMateriales entrada where entrada.id=(select max(id) from EntradaMateriales)");
		//EntradaMateriales resultado = (EntradaMateriales)query.getSingleResult();
		em.flush();
		return get(entrada.getId_entrada());
	}
	
	@Transactional
	public EntradaMateriales update(EntradaMateriales entrada){
		entrada = em.merge(entrada);
		return entrada;
	}
	
	@Transactional
	public void delete(EntradaMateriales entrada){
		EntradaMateriales e = get(entrada.getId_entrada());
		if(e!=null){
			em.remove(e);
		}
	}
	
}
