package com.ecom.safecom.services;

import com.ecom.safecom.entity.Usuario;

public interface IAuthService {

	public boolean login(String account, String password);
	
	public void logout();
	
	public Usuario getUserCredential();
	
	public boolean hasAccess(String funcion, String access);
}
