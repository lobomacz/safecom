package com.ecom.safecom.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.AsientoContableDao;
import com.ecom.safecom.dao.DetalleAsientoContableDao;
import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContablePK;
import com.ecom.safecom.services.IAsientoContableService;

@Service("asientoContableService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class AsientoContableServiceImpl implements IAsientoContableService {
	
	@Autowired
	AsientoContableDao dao;
	
	@Autowired
	DetalleAsientoContableDao ddao;

	public List<AsientoContable> listaTodo() {
		return dao.getAll();
	}

	public List<AsientoContable> listaPorPeriodo(String idperiodo) {
		return dao.getPorPeriodo(idperiodo);
	}

	public AsientoContable recargarAsiento(AsientoContable asiento) {
		return dao.reload(asiento);
	}

	public AsientoContable buscarAsiento(Integer idasiento) {
		return dao.get(idasiento);
	}

	public AsientoContable guardarAsiento(AsientoContable asiento) {
		return dao.save(asiento);
	}

	public AsientoContable actualizarAsiento(AsientoContable asiento) {
		return dao.update(asiento);
	}

	public void borrarAsiento(AsientoContable asiento) {
		dao.delete(asiento);
		
	}

	public DetalleAsientoContable recargarDetalle(DetalleAsientoContable detalle) {
		return ddao.reload(detalle);
	}

	public DetalleAsientoContable buscarDetalle(Integer idasiento, String cuenta) {
		return ddao.get(new DetalleAsientoContablePK(idasiento, cuenta));
	}

	public DetalleAsientoContable guardarDetalle(DetalleAsientoContable detalle) {
		return ddao.save(detalle);
	}

	public DetalleAsientoContable actualizarDetalle(
			DetalleAsientoContable detalle) {
		return ddao.update(detalle);
	}

	public void borrarDetalle(DetalleAsientoContable detalle) {
		ddao.delete(detalle);
	}

	public DetalleAsientoContable buscarDetalle(DetalleAsientoContablePK _id) {
		return ddao.get(_id);
	}

	public List<DetalleAsientoContable> listaPorCuenta(String cuenta) {
		return ddao.listaPorCuenta(cuenta);
	}
	
	public List<DetalleAsientoContable> listaAntesDeFecha(Date fecha){
		return ddao.listaAntesDeFecha(fecha);
	}

}
