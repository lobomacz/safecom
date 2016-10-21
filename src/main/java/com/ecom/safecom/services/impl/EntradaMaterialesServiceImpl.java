package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.DetalleEntradaMaterialesDao;
import com.ecom.safecom.dao.EntradaMaterialesDao;
import com.ecom.safecom.entity.DetalleEntradaMateriales;
import com.ecom.safecom.entity.DetalleEntradaMaterialesPK;
import com.ecom.safecom.entity.EntradaMateriales;
import com.ecom.safecom.services.IEntradaMaterialesService;

@Service("entradaMaterialesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class EntradaMaterialesServiceImpl implements IEntradaMaterialesService {
	
	@Autowired
	EntradaMaterialesDao dao;
	
	@Autowired
	DetalleEntradaMaterialesDao ddao;

	public List<EntradaMateriales> listaTodo() {
		return dao.getAll();
	}

	public List<EntradaMateriales> listaPorAlmacen(Integer idalmacen) {
		return dao.getAllPorAlmacen(idalmacen);
	}

	public List<EntradaMateriales> listaPendientes(Integer idalmacen) {
		return dao.getPendientesPorAlmacen(idalmacen);
	}

	public EntradaMateriales recargarEntrada(EntradaMateriales entrada) {
		return dao.reload(entrada);
	}

	public EntradaMateriales guardarEntrada(EntradaMateriales entrada) {
		return dao.save(entrada);
	}

	public EntradaMateriales buscarEntrada(Integer identrada) {
		return dao.get(identrada);
	}

	public EntradaMateriales actualizarEntrada(EntradaMateriales entrada) {
		return dao.update(entrada);
	}

	public void borrarEntrada(EntradaMateriales entrada) {
		dao.delete(entrada);
		
	}

	public DetalleEntradaMateriales recargarDetalle(
			DetalleEntradaMateriales detalle) {
		return ddao.reload(detalle);
	}

	public DetalleEntradaMateriales guardaDetalleEntrada(DetalleEntradaMateriales detalle) {
		return ddao.save(detalle);
	}

	public DetalleEntradaMateriales buscarDetalle(Integer identrada,
			String codigoproducto) {
		return ddao.get(new DetalleEntradaMaterialesPK(identrada, codigoproducto));
	}

	public DetalleEntradaMateriales actualizarDetalle(
			DetalleEntradaMateriales detalle) {
		return ddao.update(detalle);
	}

	public void borrarDetalle(DetalleEntradaMateriales detalle) {
		ddao.delete(detalle);
	}

	public DetalleEntradaMateriales buscarDetalle(
			DetalleEntradaMaterialesPK iddetalle) {
		return ddao.get(iddetalle);
	}

}
