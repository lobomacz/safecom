package com.ecom.safecom.controllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.ecom.safecom.entity.Usuario;
import com.ecom.safecom.services.impl.UserInfoServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AutenUserController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	
	@WireVariable
	private UserInfoServiceImpl userInfoServe;
	@Wire
	Textbox txtUsername;
	@Wire
	Textbox txtPassword;
	@Wire
	Label lblMensajeError;
	
	
	private Usuario usuario;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		usuario = null;
	}
	
	@Listen("onClick=#btnOk")
	public void btnOk_onClickListener(){
		String autenUsuario = txtUsername.getValue();
		String autenPassword = txtPassword.getValue();
		
		Usuario userTemp = userInfoServe.getUsuario(autenUsuario);
		
		if(userTemp != null && userTemp.Validar(autenPassword)){
			usuario = userTemp;
			Events.postEvent("onClose",getSelf(),null);
		}else{
			lblMensajeError.setValue("El nombre de usuario o contraseña no son correctos.");
		}
	}
	
	@Listen("onClick=#btnCancel")
	public void btnCancel_onClickListener(){
		usuario = null;
		Events.postEvent("onClose",getSelf(),null);
	}
	
	@Listen("onChanging=textbox")
	public void Textbox_onChangingListener(){
		lblMensajeError.setValue("");
	}
	
	@Listen("onClose=#wAutenUser")
	public void wAutenUser_onCloseListener(){
		Events.postEvent("onSetSupervisor", getSelf().getPreviousSibling(), usuario);
	}

}
