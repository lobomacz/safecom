package com.ecom.safecom.controllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.ecom.safecom.entity.Usuario;
import com.ecom.safecom.services.impl.AutenticacionService;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LoginController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
	Textbox nombreusuario;
	
	@Wire
	Textbox contrasenna;

	@Wire
	Label mensaje;
	
	@WireVariable
	AutenticacionService autenService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		nombreusuario.focus();
	}
	
	@Listen("onClick=#entrar; onOK=#loginWin")
	public void doLogin(){
		String uname = nombreusuario.getValue();
		String password = contrasenna.getValue();
		
		if(!autenService.login(uname, password)){
			mensaje.setValue("nombre de usuario y/o contraseña incorrectos");
			return;
		}
		
		Usuario ucred = autenService.getUserCredential();
		mensaje.setValue("Bienvenid@ " + ucred.getEmpleado().toString());
		mensaje.setSclass("");
		Executions.sendRedirect("index.zul");
	}
}
