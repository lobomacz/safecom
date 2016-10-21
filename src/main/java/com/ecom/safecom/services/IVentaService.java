package com.ecom.safecom.services;

import java.util.Date;
import java.util.List;

import com.ecom.safecom.entity.DetalleVenta;
import com.ecom.safecom.entity.DetalleVentaPK;
import com.ecom.safecom.entity.Venta;

public interface IVentaService {

	List<Venta> listaTodo();
	
	List<Venta> listaTodoCredito();
	
	List<Venta> listaPorEmpleado(String idempleado);
	
	List<Venta> listaCanceladas();
	
	List<Venta> listaPorFecha(Date finicio,Date ffinal);
	
	List<Venta> listaCanceladosPorFecha(Date finicio,Date ffinal);
	
	List<Venta> listaPorPeriodo(String idperiodo);
	
	List<Venta> listaProformas();
	
	List<Venta> listaProformasPorEmpleado(String idempleado);
	
	Venta recargarVenta(Venta venta);
	
	Venta buscarVenta(Integer idventa);
	
	Venta guardarVenta(Venta venta);
	
	Venta actualizarVenta(Venta venta);
	
	void borrarVenta(Venta venta);
	
	DetalleVenta recargarDetalle(DetalleVenta detalle);
	
	DetalleVenta buscarDetalle(DetalleVentaPK iddetalle);
	
	DetalleVenta guardarDetalle(DetalleVenta detalle);
	
	DetalleVenta actualizarDetalle(DetalleVenta detalle);
	
	void borrarDetalle(DetalleVenta detalle);
}
