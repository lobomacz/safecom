package com.ecom.safecom.services;

import java.util.Date;
import java.util.List;

import com.ecom.safecom.entity.ArqueoCaja;

public interface IArqueoCajaService {
	
	ArqueoCaja buscarArqueo(Integer _id);
	ArqueoCaja recargarArqueo(ArqueoCaja arqueo);
	ArqueoCaja guardarArqueo(ArqueoCaja arqueo);
	ArqueoCaja actualizarArqueo(ArqueoCaja arqueo);
	void borrarArqueo(ArqueoCaja arqueo);
	ArqueoCaja ultimoArqueo(Integer idcaja);
	
	List<ArqueoCaja> listaTodo(Integer idcaja);
	List<ArqueoCaja> listaPorFecha(Integer idcaja,Date finicio,Date ffinal);

}
