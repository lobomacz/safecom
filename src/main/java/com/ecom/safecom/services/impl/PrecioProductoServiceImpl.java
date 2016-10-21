package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.PrecioProductoDao;
import com.ecom.safecom.entity.PrecioProducto;
import com.ecom.safecom.entity.PrecioProductoPK;
import com.ecom.safecom.entity.Producto;
import com.ecom.safecom.services.IPrecioProductoService;

@Service("precioProductoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PrecioProductoServiceImpl implements IPrecioProductoService {
	
	@Autowired
	PrecioProductoDao dao;

	public List<PrecioProducto> getListaPrecioProducto(Producto producto) {
		return dao.getListaPorProducto(producto);
	}
	
	public PrecioProducto reloadPrecioProducto(PrecioProducto precio){
		return dao.reload(precio);
	}

	public PrecioProducto getPrecioProducto(PrecioProductoPK _id) {
		return dao.get(_id);
	}

	public PrecioProducto savePrecioProducto(PrecioProducto precio) {
		return dao.save(precio);
	}

	public PrecioProducto updatePrecioProducto(PrecioProducto precio) {
		return dao.update(precio);
	}

	public void deletePrecioProducto(PrecioProducto precio) {
		dao.delete(precio);
		
	}

}
