package com.ecom.safecom.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.ArqueoCajaDao;
import com.ecom.safecom.entity.ArqueoCaja;
import com.ecom.safecom.services.IArqueoCajaService;

@Service("arqueoCajaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ArqueoCajaServiceImpl implements IArqueoCajaService {
	
	@Autowired
	private ArqueoCajaDao dao;

	public ArqueoCaja buscarArqueo(Integer _id) {
		return dao.get(_id);
	}

	public ArqueoCaja recargarArqueo(ArqueoCaja arqueo) {
		return dao.reload(arqueo);
	}

	public ArqueoCaja guardarArqueo(ArqueoCaja arqueo) {
		return dao.save(arqueo);
	}

	public ArqueoCaja actualizarArqueo(ArqueoCaja arqueo) {
		return dao.update(arqueo);
	}

	public void borrarArqueo(ArqueoCaja arqueo) {
		dao.delete(arqueo);

	}

	public ArqueoCaja ultimoArqueo(Integer idcaja) {
		return dao.getLast(idcaja);
	}

	public List<ArqueoCaja> listaTodo(Integer idcaja) {
		return dao.listaTodo(idcaja);
	}

	public List<ArqueoCaja> listaPorFecha(Integer idcaja, Date finicio,
			Date ffinal) {
		return dao.listaPorFecha(idcaja, finicio, ffinal);
	}

}
