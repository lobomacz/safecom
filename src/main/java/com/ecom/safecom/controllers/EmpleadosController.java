package com.ecom.safecom.controllers;

import java.awt.BorderLayout;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;

public class EmpleadosController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@Wire
	BorderLayout lyEmpl;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
	}
}
