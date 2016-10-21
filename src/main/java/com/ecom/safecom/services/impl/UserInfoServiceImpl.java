package com.ecom.safecom.services.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.UsuarioDao;
import com.ecom.safecom.entity.Usuario;
import com.ecom.safecom.services.IUserInfoService;

@Service("userInfoServe")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserInfoServiceImpl implements IUserInfoService,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	UsuarioDao uDao;
	
	public Usuario getUsuario(String nombreusuario){
		return uDao.get(nombreusuario);
	}
	
	public List<Usuario> getListaUsuarios() {
		return uDao.getListaUsuarios();
	}

	public Usuario updateUsuario(Usuario user) {
		return uDao.update(user);
	}

	public Usuario saveUsuario(Usuario user) {
		return uDao.save(user);
	}

	public void deleteUsuario(Usuario user) {
		uDao.delete(user);
	}
}
