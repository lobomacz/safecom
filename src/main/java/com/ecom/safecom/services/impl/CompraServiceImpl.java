package com.ecom.safecom.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.CompraDao;
import com.ecom.safecom.entity.Compra;
import com.ecom.safecom.services.ICompraService;

@Service("compraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CompraServiceImpl implements ICompraService {
	
	@Autowired
	CompraDao dao;

	public List<Compra> listaTodo() {
		return dao.getAll();
	}

	public List<Compra> listaCancelado() {
		return dao.getCanceladas();
	}

	public List<Compra> listaPorFecha(Date finicio, Date ffinal) {
		return dao.getPorFecha(finicio, ffinal);
	}

	public List<Compra> listaCanceladoPorFecha(Date finicio, Date ffinal) {
		return dao.getCanceladosPorFecha(finicio, ffinal);
	}

	public List<Compra> listaPorPeriodo() {
		return dao.getPorPeriodo();
	}

	public Compra recargarCompra(Compra compra) {
		return dao.reload(compra);
	}

	public Compra buscarCompra(Integer idcompra) {
		return dao.get(idcompra);
	}

	public void guardarCompra(Compra compra) {
		dao.save(compra);
		
	}

	public Compra actualizarCompra(Compra compra) {
		return dao.update(compra);
	}

	public void borrarCompra(Compra compra) {
		dao.delete(compra);
		
	}

	public List<Compra> listaPorPeriodo(String idperiodo) {
		return dao.getPorPeriodo(idperiodo);
	}

}
