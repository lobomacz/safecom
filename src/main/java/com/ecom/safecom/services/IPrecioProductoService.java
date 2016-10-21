package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.PrecioProducto;
import com.ecom.safecom.entity.PrecioProductoPK;
import com.ecom.safecom.entity.Producto;

public interface IPrecioProductoService {

	List<PrecioProducto> getListaPrecioProducto(Producto producto);
	
	PrecioProducto reloadPrecioProducto(PrecioProducto precio);
	
	PrecioProducto getPrecioProducto(PrecioProductoPK _id);
	
	PrecioProducto savePrecioProducto(PrecioProducto precio);
	
	PrecioProducto updatePrecioProducto(PrecioProducto precio);
	
	void deletePrecioProducto(PrecioProducto precio);
}
