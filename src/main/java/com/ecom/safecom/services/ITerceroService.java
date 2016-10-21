package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.Tercero;

public interface ITerceroService {
	
	List<Tercero> listaTodo();
	Tercero recargarTercero(Tercero tercero);
	Tercero buscarTercero(Integer _id);
	List<Tercero> listaTipo(String tipo);
	void guardarTercero(Tercero tercero);
	Tercero actualizarTercero(Tercero tercero);
	void borrarTercero(Tercero tercero);

}
