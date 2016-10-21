package com.ecom.safecom.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.TelefonoDao;
import com.ecom.safecom.entity.Telefono;
import com.ecom.safecom.services.ITelefonoService;

@Service("telefonoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class TelefonoServiceImpl implements ITelefonoService {
	
	@Autowired
	private TelefonoDao dao;

	public Telefono buscarTelefono(String telefono) {
		return dao.get(telefono);
	}

	public Telefono recargarTelefono(Telefono telefono) {
		return dao.reload(telefono);
	}

	public Telefono guardarTelefono(Telefono telefono) {
		return dao.save(telefono);
	}

	public Telefono actualizarTelefono(Telefono telefono) {
		return dao.update(telefono);
	}

	public void borrarTelefono(Telefono telefono) {
		dao.delete(telefono);
	}

}
