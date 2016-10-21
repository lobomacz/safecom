package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.Almacen;

public interface IAlmacenService {
	
	List<Almacen> listaTodo();
	Almacen recargarAlmacen(Almacen almacen);
	Almacen buscarAlmacen(Integer idalmacen);
	Almacen buscaPrincipal();
	void guardarAlmacen(Almacen almacen);
	Almacen actualizarAlmacen(Almacen almacen);
	void borrarAlmacen(Almacen almacen);

}
