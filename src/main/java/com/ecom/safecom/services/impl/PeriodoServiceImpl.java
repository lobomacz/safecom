package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.PeriodoDao;
import com.ecom.safecom.entity.Periodo;
import com.ecom.safecom.services.IPeriodoService;

@Service("periodoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PeriodoServiceImpl implements IPeriodoService {
	
	@Autowired
	PeriodoDao dao;

	public List<Periodo> getListaPeriodos() {
		return dao.getListaPeriodos();
	}

	public Periodo getPeriodo(String _id) {
		return dao.get(_id);
	}

	public Periodo savePeriodo(Periodo periodo) {
		return dao.save(periodo);
	}

	public Periodo updatePeriodo(Periodo periodo) {
		return dao.update(periodo);
	}

	public void deletePeriodo(Periodo periodo) {
		dao.delete(periodo);
	}

	public Periodo getPeriodoAbierto() {
		return dao.getPeriodoAbierto();
	}

	public List<Periodo> getListaPeriodosAbiertos() {
		return dao.getListaPeriodosAbiertos();
	}

}
