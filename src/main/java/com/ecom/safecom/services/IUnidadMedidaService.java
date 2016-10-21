package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.UnidadMedida;

public interface IUnidadMedidaService {

	List<UnidadMedida> getListaUnidadMedida();
	
	List<UnidadMedida> getUnidadesBase();
	
	List<String> getDescripcionCorta();
	
	UnidadMedida getUnidadMedida(Integer _id);
	
	UnidadMedida getPorDescCorta(String desc);
	
	UnidadMedida saveUnidadMedida(UnidadMedida unidad);
	
	UnidadMedida updateUnidadMedida(UnidadMedida unidad);
	
	void deleteUnidadMedida(UnidadMedida unidad);
}
