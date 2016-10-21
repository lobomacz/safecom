package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.AlmacenDao;
import com.ecom.safecom.entity.Almacen;
import com.ecom.safecom.services.IAlmacenService;

@Service("almacenService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class AlmacenServiceImpl implements IAlmacenService {
	
	@Autowired
	AlmacenDao dao;

	public List<Almacen> listaTodo() {
		return dao.getAll();
	}

	public Almacen recargarAlmacen(Almacen almacen) {
		return dao.reload(almacen);
	}

	public Almacen buscarAlmacen(Integer idalmacen) {
		return dao.get(idalmacen);
	}

	public void guardarAlmacen(Almacen almacen) {
		dao.save(almacen);
		
	}

	public Almacen actualizarAlmacen(Almacen almacen) {
		return dao.update(almacen);
	}

	public void borrarAlmacen(Almacen almacen) {
		dao.delete(almacen);
		
	}

	public Almacen buscaPrincipal() {
		// TODO Auto-generated method stub
		return dao.getPrincipal();
	}

}
