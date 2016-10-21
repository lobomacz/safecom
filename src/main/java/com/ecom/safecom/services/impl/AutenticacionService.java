package com.ecom.safecom.services.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.ecom.safecom.BCrypt;
import com.ecom.safecom.dao.UsuarioDao;
import com.ecom.safecom.entity.UserCredential;
import com.ecom.safecom.entity.Usuario;
import com.ecom.safecom.services.IAuthService;

@Service("autenService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class AutenticacionService implements IAuthService,Serializable
{
	private static final long serialVersionUID = 1L;
	
	/*@Autowired
	UserInfoServices userInfoServ;*/
	@Autowired
	UserInfoServiceImpl userInfoService;
	
	/*@Autowired
	UserCredential userCredential;*/
	

	public boolean login(String account, String password) {
		// TODO Auto-generated method stub
		//Usuario user = userInfoServ.encuentraUsuario(account);
		Usuario user = userInfoService.getUsuario(account);
		if(user == null || !user.Validar(password)){
			return false;
		}
		
		Session sess = Sessions.getCurrent();
		
		//userCredential.setAccount(account);
		//userCredential.setName(user.getEmpleado().toString());
		sess.setAttribute("userCredential", user);
		
		return true;
	}

	public void logout() {
		// TODO Auto-generated method stub
		Session sess = Sessions.getCurrent();
		sess.removeAttribute("userCredential");
	}

	public Usuario getUserCredential() {
		// TODO Auto-generated method stub
		
		Session sesion = Sessions.getCurrent();
		Usuario ucred = (Usuario)sesion.getAttribute("userCredential");
		
		/*if(ucred == null){
			ucred = new Usuario();
			sesion.setAttribute("userCredential", ucred);
		}*/
		return ucred;
	}

	public boolean hasAccess(String funcion, String access) {
		// TODO Auto-generated method stub
		return false;
	}

}
