package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.DetalleEntradaMateriales;
import com.ecom.safecom.entity.DetalleEntradaMaterialesPK;
import com.ecom.safecom.entity.EntradaMateriales;

public interface IEntradaMaterialesService {
	
	List<EntradaMateriales> listaTodo();
	List<EntradaMateriales> listaPorAlmacen(Integer idalmacen);
	List<EntradaMateriales> listaPendientes(Integer idalmacen);
	EntradaMateriales recargarEntrada(EntradaMateriales entrada);
	EntradaMateriales guardarEntrada(EntradaMateriales entrada);
	EntradaMateriales buscarEntrada(Integer identrada);
	EntradaMateriales actualizarEntrada(EntradaMateriales entrada);
	void borrarEntrada(EntradaMateriales entrada);
	
	DetalleEntradaMateriales recargarDetalle(DetalleEntradaMateriales detalle);
	DetalleEntradaMateriales guardaDetalleEntrada(DetalleEntradaMateriales detalle);
	DetalleEntradaMateriales buscarDetalle(Integer identrada, String codigoproducto);
	DetalleEntradaMateriales buscarDetalle(DetalleEntradaMaterialesPK iddetalle);
	DetalleEntradaMateriales actualizarDetalle(DetalleEntradaMateriales detalle);
	void borrarDetalle(DetalleEntradaMateriales detalle);
}
