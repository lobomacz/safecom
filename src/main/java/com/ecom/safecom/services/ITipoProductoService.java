package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.TipoProducto;

public interface ITipoProductoService {

	List<TipoProducto> getListaTipoProducto();
	
	TipoProducto getTipoProducto(Integer id_tipo_producto);
	
	TipoProducto updateTipoProducto(TipoProducto tipo_producto);
	
	TipoProducto saveTipoProducto(TipoProducto tipo_producto);
	
	void deleteTipoProducto(TipoProducto tipo_producto);
}
