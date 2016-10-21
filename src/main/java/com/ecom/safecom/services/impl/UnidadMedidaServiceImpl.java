package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.UnidadMedidaDao;
import com.ecom.safecom.entity.UnidadMedida;
import com.ecom.safecom.services.IUnidadMedidaService;

@Service("unidadMedidaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UnidadMedidaServiceImpl implements IUnidadMedidaService {
	
	@Autowired
	UnidadMedidaDao dao;

	public List<UnidadMedida> getListaUnidadMedida() {
		return dao.queryAll();
	}

	public UnidadMedida getUnidadMedida(Integer _id) {
		return dao.get(_id);
	}

	public UnidadMedida saveUnidadMedida(UnidadMedida unidad) {
		return dao.save(unidad);
	}

	public UnidadMedida updateUnidadMedida(UnidadMedida unidad) {
		return dao.update(unidad);
	}

	public void deleteUnidadMedida(UnidadMedida unidad) {
		dao.delete(unidad);
	}

	public List<UnidadMedida> getUnidadesBase() {
		return dao.getUnidadesBase();
	}

	public List<String> getDescripcionCorta() {
		return dao.getDescripcionCorta();
	}

	public UnidadMedida getPorDescCorta(String desc) {
		return dao.getByDescCorta(desc);
	}

}
