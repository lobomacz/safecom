package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.Producto;

public interface IProductoService {
	
	List<Producto> getListaProductos();
	
	List<Producto> getListaProductos(String descripcion);
	
	List<String> getCodigosProductos();
	
	List<Producto> getProductosPorCodigo(String codigo_producto);
	
	Producto getProducto(String codigo_producto);

	Producto saveProducto(Producto producto);
	
	Producto updateProducto(Producto producto);
	
	void deleteProducto(Producto producto);
}
