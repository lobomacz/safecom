package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.DetalleSalidaMaterialesDao;
import com.ecom.safecom.dao.SalidaMaterialesDao;
import com.ecom.safecom.entity.DetalleSalidaMateriales;
import com.ecom.safecom.entity.DetalleSalidaMaterialesPK;
import com.ecom.safecom.entity.SalidaMateriales;
import com.ecom.safecom.services.ISalidaMaterialesService;

@Service("salidaMaterialesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SalidaMaterialesServiceImpl implements ISalidaMaterialesService {
	
	@Autowired
	SalidaMaterialesDao dao;
	
	@Autowired
	DetalleSalidaMaterialesDao ddao;

	public List<SalidaMateriales> listaTodo() {
		return dao.getAll();
	}

	public List<SalidaMateriales> listaPorAlmacen(Integer idalmacen) {
		return dao.getAllPorAlmacen(idalmacen);
	}

	public List<SalidaMateriales> listaPendientesPorAlmacen(Integer idalmacen) {
		return dao.getPendientesPorAlmacen(idalmacen);
	}

	public SalidaMateriales recargaSalida(SalidaMateriales salida) {
		return dao.reload(salida);
	}

	public SalidaMateriales buscaSalida(Integer idsalida) {
		return dao.get(idsalida);
	}

	public SalidaMateriales guardarSalida(SalidaMateriales salida) {
		return dao.save(salida);
	}

	public SalidaMateriales actualizarSalida(SalidaMateriales salida) {
		return dao.update(salida);
	}

	public void borrarSalida(SalidaMateriales salida) {
		dao.delete(salida);
	}

	public DetalleSalidaMateriales recargarDetalle(
			DetalleSalidaMateriales detalle) {
		return ddao.reload(detalle);
	}

	public DetalleSalidaMateriales guardaDetalleSalida(DetalleSalidaMateriales detalle) {
		return ddao.save(detalle);
	}

	public DetalleSalidaMateriales buscarDetalle(Integer idsalida,
			String codigoproducto) {
		return ddao.get(new DetalleSalidaMaterialesPK(idsalida, codigoproducto));
	}

	public DetalleSalidaMateriales actualiarDetalle(
			DetalleSalidaMateriales detalle) {
		return ddao.update(detalle);
	}

	public void borrarDetalle(DetalleSalidaMateriales detalle) {
		ddao.delete(detalle);
	}

}
