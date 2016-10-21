package com.ecom.safecom.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.DetalleVentaDao;
import com.ecom.safecom.dao.VentaDao;
import com.ecom.safecom.entity.DetalleVenta;
import com.ecom.safecom.entity.DetalleVentaPK;
import com.ecom.safecom.entity.Venta;
import com.ecom.safecom.services.IVentaService;

@Service("ventaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class VentaServiceImpl implements IVentaService {
	
	@Autowired
	VentaDao dao;
	
	@Autowired
	DetalleVentaDao ddao;

	public List<Venta> listaTodo() {
		return dao.getAll();
	}

	public List<Venta> listaCanceladas() {
		return dao.getCanceladas();
	}

	public List<Venta> listaPorFecha(Date finicio, Date ffinal) {
		return dao.getPorFecha(finicio, ffinal);
	}

	public List<Venta> listaCanceladosPorFecha(Date finicio, Date ffinal) {
		return dao.getCanceladosPorFecha(finicio, ffinal);
	}

	public List<Venta> listaPorPeriodo(String idperiodo) {
		return dao.getPorPeriodo(idperiodo);
	}

	public Venta recargarVenta(Venta venta) {
		return dao.reload(venta);
	}

	public Venta buscarVenta(Integer idventa) {
		return dao.get(idventa);
	}

	public Venta guardarVenta(Venta venta) {
		return dao.save(venta);
	}

	public Venta actualizarVenta(Venta venta) {
		return dao.update(venta);
	}

	public void borrarVenta(Venta venta) {
		dao.delete(venta);
	}

	public DetalleVenta recargarDetalle(DetalleVenta detalle) {
		return ddao.reload(detalle);
	}

	public DetalleVenta buscarDetalle(DetalleVentaPK iddetalle) {
		return ddao.get(iddetalle);
	}

	public DetalleVenta guardarDetalle(DetalleVenta detalle) {
		return ddao.guardar(detalle);
	}

	public DetalleVenta actualizarDetalle(DetalleVenta detalle) {
		return ddao.actualizar(detalle);
	}

	public void borrarDetalle(DetalleVenta detalle) {
		ddao.eliminar(detalle);
	}

	public List<Venta> listaPorEmpleado(String idempleado) {
		return dao.getPorEmpleado(idempleado);
	}

	public List<Venta> listaProformas() {
		return dao.getProformas();
	}

	public List<Venta> listaProformasPorEmpleado(String idempleado) {
		return dao.getProformasPorEmpleado(idempleado);
	}

	public List<Venta> listaTodoCredito() {
		return dao.getAllCredito();
	}

}
