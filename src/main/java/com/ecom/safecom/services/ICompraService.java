package com.ecom.safecom.services;

import java.util.Date;
import java.util.List;

import com.ecom.safecom.entity.Compra;

public interface ICompraService {
	
	List<Compra> listaTodo();
	List<Compra> listaCancelado();
	List<Compra> listaPorFecha(Date finicio, Date ffinal);
	List<Compra> listaCanceladoPorFecha(Date finicio, Date ffinal);
	List<Compra> listaPorPeriodo();
	List<Compra> listaPorPeriodo(String idperiodo);
	Compra recargarCompra(Compra compra);
	Compra buscarCompra(Integer idcompra);
	void guardarCompra(Compra compra);
	Compra actualizarCompra(Compra compra);
	void borrarCompra(Compra compra);
	
}
