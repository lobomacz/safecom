package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.Periodo;

public interface IPeriodoService {
	
	List<Periodo> getListaPeriodos();
	
	List<Periodo> getListaPeriodosAbiertos();
	
	Periodo getPeriodo(String _id);
	
	Periodo getPeriodoAbierto();
	
	Periodo savePeriodo(Periodo periodo);
	
	Periodo updatePeriodo(Periodo periodo);
	
	void deletePeriodo(Periodo periodo);

}
