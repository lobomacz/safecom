package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.EjercicioDao;
import com.ecom.safecom.entity.Ejercicio;
import com.ecom.safecom.services.IEjercicioService;

@Service("ejercicioService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class EjercicioServiceImpl implements IEjercicioService {
	
	@Autowired
	EjercicioDao dao;

	public List<Ejercicio> getListaEjercicios() {
		return dao.getListaEjercicios();
	}
	
	public Ejercicio getEjercicioActivo(){
		return dao.buscaEjercicioActivo();
	}

	public Ejercicio getEjercicio(Integer _id) {
		return dao.get(_id);
	}

	public Ejercicio saveEjercicio(Ejercicio ejercicio) {
		return dao.save(ejercicio);
	}

	public Ejercicio updateEjercicio(Ejercicio ejercicio) {
		return dao.update(ejercicio);
	}

	public void deleteEjercicio(Ejercicio ejercicio) {
		dao.delete(ejercicio);
	}

}
