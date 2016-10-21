package com.ecom.safecom.services;

import java.util.Date;
import java.util.List;

import com.ecom.safecom.entity.ArqueoCaja;
import com.ecom.safecom.entity.Caja;
import com.ecom.safecom.entity.MovimientoCaja;

public interface ICajaService {

	List<Caja> ListaCajas();
	
	Caja BuscarCaja(Integer id_caja);
	
	Caja RecargarCaja(Caja caja);
	
	Caja ActualizarCaja(Caja caja);
	
	Caja GuardarCaja(Caja caja);
	
	void BorrarCaja(Caja caja);
	
	List<MovimientoCaja> ListaMovimientos(Integer idcaja);
	
	List<MovimientoCaja> ListaMovimientosPorFecha(Integer idcaja, Date finicio,Date ffinal);
	
	MovimientoCaja BuscarMovimientoCaja(Integer id_mov);
	
	MovimientoCaja RecargarMovimientoCaja(MovimientoCaja mov);
	
	MovimientoCaja GuardarMovimientoCaja(MovimientoCaja mov);
	
	MovimientoCaja ActualizarMovimientoCaja(MovimientoCaja mov);
	
	void BorrarMovimientoCaja(MovimientoCaja mov);
	
	List<ArqueoCaja> ListaArqueos(Integer idcaja);
	
	List<ArqueoCaja> ListaArqueosPorFecha(Integer idcaja, Date finicio, Date ffinal);
	
	ArqueoCaja BuscarArqueoCaja(Integer idarq);
	
	ArqueoCaja RecargarArqueoCaja(ArqueoCaja arq);
	
	ArqueoCaja GuardarArqueoCaja(ArqueoCaja arq);
	
	ArqueoCaja ActualizarArqueoCaja(ArqueoCaja arq);
	
	void BorrarArqueoCaja(ArqueoCaja arq);
}
