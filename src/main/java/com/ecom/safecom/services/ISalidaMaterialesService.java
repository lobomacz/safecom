package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.DetalleSalidaMateriales;
import com.ecom.safecom.entity.SalidaMateriales;

public interface ISalidaMaterialesService {
	
	List<SalidaMateriales> listaTodo();
	List<SalidaMateriales> listaPorAlmacen(Integer idalmacen);
	List<SalidaMateriales> listaPendientesPorAlmacen(Integer idalmacen);
	SalidaMateriales recargaSalida(SalidaMateriales salida);
	SalidaMateriales buscaSalida(Integer idsalida);
	SalidaMateriales guardarSalida(SalidaMateriales salida);
	SalidaMateriales actualizarSalida(SalidaMateriales salida);
	void borrarSalida(SalidaMateriales salida);

	DetalleSalidaMateriales recargarDetalle(DetalleSalidaMateriales detalle);
	DetalleSalidaMateriales guardaDetalleSalida(DetalleSalidaMateriales detalle);
	DetalleSalidaMateriales buscarDetalle(Integer idsalida, String codigoproducto);
	DetalleSalidaMateriales actualiarDetalle(DetalleSalidaMateriales detalle);
	void borrarDetalle(DetalleSalidaMateriales detalle);
}
