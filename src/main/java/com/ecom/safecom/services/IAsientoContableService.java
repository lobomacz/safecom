package com.ecom.safecom.services;

import java.util.Date;
import java.util.List;

import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContablePK;

public interface IAsientoContableService {

	List<AsientoContable> listaTodo();
	List<AsientoContable> listaPorPeriodo(String idperiodo);
	AsientoContable recargarAsiento(AsientoContable asiento);
	AsientoContable buscarAsiento(Integer idasiento);
	AsientoContable guardarAsiento(AsientoContable asiento);
	AsientoContable actualizarAsiento(AsientoContable asiento);
	void borrarAsiento(AsientoContable asiento);
	
	List<DetalleAsientoContable> listaPorCuenta(String cuenta);
	List<DetalleAsientoContable> listaAntesDeFecha(Date fecha);
	DetalleAsientoContable recargarDetalle(DetalleAsientoContable detalle);
	DetalleAsientoContable buscarDetalle(Integer idasiento, String cuenta);
	DetalleAsientoContable buscarDetalle(DetalleAsientoContablePK _id);
	DetalleAsientoContable guardarDetalle(DetalleAsientoContable detalle);
	DetalleAsientoContable actualizarDetalle(DetalleAsientoContable detalle);
	void borrarDetalle(DetalleAsientoContable detalle);
	
}
