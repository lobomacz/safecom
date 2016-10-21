package com.ecom.safecom.controllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.ecom.safecom.services.impl.AutenticacionService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LogoutController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@WireVariable
	AutenticacionService autenService;
	
	@Listen("onClick=#salir")
	public void doLogout(){
		autenService.logout();
		Executions.sendRedirect("login.zul");
	}

}
