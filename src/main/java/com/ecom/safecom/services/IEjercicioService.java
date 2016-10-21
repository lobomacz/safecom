package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.Ejercicio;

public interface IEjercicioService {

	List<Ejercicio> getListaEjercicios();
	
	Ejercicio getEjercicioActivo();
	
	Ejercicio getEjercicio(Integer _id);
	
	Ejercicio saveEjercicio(Ejercicio ejercicio);
	
	Ejercicio updateEjercicio(Ejercicio ejercicio);
	
	void deleteEjercicio(Ejercicio ejercicio);
}
