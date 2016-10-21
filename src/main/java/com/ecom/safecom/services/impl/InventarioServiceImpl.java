package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.InventarioDao;
import com.ecom.safecom.entity.Inventario;
import com.ecom.safecom.entity.InventarioPK;
import com.ecom.safecom.services.IInventarioService;

@Service("inventarioService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class InventarioServiceImpl implements IInventarioService {
	
	@Autowired
	InventarioDao dao;

	public List<Inventario> listaTodo(Integer idalmacen) {
		return dao.getAll(idalmacen);
	}

	public Inventario recargaInventario(Inventario inventario) {
		return dao.reload(inventario);
	}

	public Inventario buscarInventario(InventarioPK idinventario) {
		return dao.get(idinventario);
	}

	public Inventario guardarInventario(Inventario inventario) {
		return dao.save(inventario);
	}

	public Inventario actualizarInventario(Inventario inventario) {
		return dao.update(inventario);
	}

	public void borrarInventario(Inventario inventario) {
		dao.delete(inventario);
	}

}
