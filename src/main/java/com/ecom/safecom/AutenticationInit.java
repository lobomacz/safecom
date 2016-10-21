package com.ecom.safecom;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Initiator;

import com.ecom.safecom.entity.Usuario;
import com.ecom.safecom.services.impl.AutenticacionService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AutenticationInit implements Initiator {

	@WireVariable
	AutenticacionService autenService;
	
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		// TODO Auto-generated method stub
		
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		Usuario ucred = autenService.getUserCredential();
		if(ucred==null){
			Executions.sendRedirect("login.zul");
		}
	}

}
