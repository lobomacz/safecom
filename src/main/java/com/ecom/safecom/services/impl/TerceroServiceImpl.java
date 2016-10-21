package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.TerceroDao;
import com.ecom.safecom.entity.Tercero;
import com.ecom.safecom.services.ITerceroService;

@Service("terceroService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class TerceroServiceImpl implements ITerceroService {
	
	@Autowired
	TerceroDao dao;

	public List<Tercero> listaTodo() {
		return dao.getAll();
	}

	public Tercero recargarTercero(Tercero tercero) {
		return dao.reload(tercero);
	}

	public Tercero buscarTercero(Integer _id) {
		return dao.get(_id);
	}

	public List<Tercero> listaTipo(String tipo) {
		return dao.getTipo(tipo);
	}

	public void guardarTercero(Tercero tercero) {
		dao.save(tercero);
		
	}

	public Tercero actualizarTercero(Tercero tercero) {
		return dao.update(tercero);
	}

	public void borrarTercero(Tercero tercero) {
		dao.delete(tercero);
		
	}

}
