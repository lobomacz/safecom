package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.TipoProductoDao;
import com.ecom.safecom.entity.TipoProducto;
import com.ecom.safecom.services.ITipoProductoService;

@Service("tipoProductoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class TipoProductoServiceImpl implements ITipoProductoService {
	
	@Autowired
	TipoProductoDao tpDao;

	public List<TipoProducto> getListaTipoProducto() {
		// TODO Auto-generated method stub
		return tpDao.getListaTipoProducto();
	}

	public TipoProducto getTipoProducto(Integer id_tipo_producto) {
		// TODO Auto-generated method stub
		return tpDao.get(id_tipo_producto);
	}

	public TipoProducto updateTipoProducto(TipoProducto tipo_producto) {
		// TODO Auto-generated method stub
		return tpDao.update(tipo_producto);
	}

	public TipoProducto saveTipoProducto(TipoProducto tipo_producto) {
		// TODO Auto-generated method stub
		return tpDao.save(tipo_producto);
	}

	public void deleteTipoProducto(TipoProducto tipo_producto) {
		// TODO Auto-generated method stub
		tpDao.delete(tipo_producto);
	}

}
