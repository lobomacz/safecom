package com.ecom.safecom.services;

import com.ecom.safecom.entity.Telefono;

public interface ITelefonoService {

	Telefono buscarTelefono(String telefono);
	
	Telefono recargarTelefono(Telefono telefono);
	
	Telefono guardarTelefono(Telefono telefono);
	
	Telefono actualizarTelefono(Telefono telefono);
	
	void borrarTelefono(Telefono telefono);
}
