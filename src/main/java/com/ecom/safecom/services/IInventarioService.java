package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.Inventario;
import com.ecom.safecom.entity.InventarioPK;

public interface IInventarioService {
	
	List<Inventario> listaTodo(Integer idalmacen);
	Inventario recargaInventario(Inventario inventario);
	Inventario buscarInventario(InventarioPK idinventario);
	Inventario guardarInventario(Inventario inventario);
	Inventario actualizarInventario(Inventario inventario);
	void borrarInventario(Inventario inventario);
	
}
