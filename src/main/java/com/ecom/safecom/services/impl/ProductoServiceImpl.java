package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.ProductoDao;
import com.ecom.safecom.entity.Producto;
import com.ecom.safecom.services.IProductoService;

@Service("productoService")
@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ProductoServiceImpl implements IProductoService {
	
	@Autowired
	ProductoDao pDao;

	public List<Producto> getListaProductos() {
		// TODO Auto-generated method stub
		return pDao.getListaProductos();
	}
	
	public List<Producto> getListaProductos(String descripcion){
		return pDao.getListaProductos(descripcion);
	}

	public Producto getProducto(String codigo_producto) {
		// TODO Auto-generated method stub
		return pDao.get(codigo_producto);
	}

	public Producto saveProducto(Producto producto) {
		// TODO Auto-generated method stub
		return pDao.save(producto);
	}

	public Producto updateProducto(Producto producto) {
		// TODO Auto-generated method stub
		return pDao.update(producto);
	}

	public void deleteProducto(Producto producto) {
		// TODO Auto-generated method stub
		pDao.delete(producto);
	}

	public List<String> getCodigosProductos() {
		return pDao.getCodigos();
	}

	public List<Producto> getProductosPorCodigo(String codigo_producto) {
		return pDao.getListaPorCodigo(codigo_producto);
	}

}
