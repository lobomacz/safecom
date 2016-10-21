package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.ConversionUnidadMedida;
import com.ecom.safecom.entity.ConversionUnidadPK;

@Repository
public class ConversionUnidadMedidaDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<ConversionUnidadMedida> getAll(){
		Query q = em.createQuery("from ConversionUnidadMedida c");
		List<ConversionUnidadMedida> resultado = q.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public ConversionUnidadMedida reload(ConversionUnidadMedida conversion){
		return em.find(ConversionUnidadMedida.class, conversion.getIdconversion());
	}
	
	@Transactional(readOnly=true)
	public ConversionUnidadMedida get(ConversionUnidadPK _id){
		return em.find(ConversionUnidadMedida.class, _id);
	}
	
	@Transactional
	public ConversionUnidadMedida save(ConversionUnidadMedida conv){
		em.persist(conv);
		return reload(conv);
	}
	
	@Transactional
	public ConversionUnidadMedida update(ConversionUnidadMedida conv){
		conv = em.merge(conv);
		return conv;
	}
	
	@Transactional
	public void delete(ConversionUnidadMedida conv){
		ConversionUnidadMedida c = get(conv.getIdconversion());
		if(c!=null){
			em.remove(c);
		}
	}

}
