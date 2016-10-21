package com.ecom.safecom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.safecom.entity.Usuario;

@Repository
public class UsuarioDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<Usuario> getListaUsuarios(){
		Query query = em.createQuery("select u from Usuario u");
		List<Usuario> resultado = query.getResultList();
		return resultado;
	}
	
	@Transactional(readOnly=true)
	public Usuario reload(Usuario usuario){
		return em.find(Usuario.class, usuario.getNombre_usuario());
	}
	
	@Transactional
	public Usuario get(String nombreusuario){
		return em.find(Usuario.class, nombreusuario);
	}
	
	@Transactional
	public Usuario save(Usuario usuario){
		em.persist(usuario);
		return usuario;
	}
	
	@Transactional
	public Usuario update(Usuario usuario){
		usuario = em.merge(usuario);
		return usuario;
	}
	
	@Transactional
	public void delete(Usuario usuario){
		em.remove(usuario);
	}
}
