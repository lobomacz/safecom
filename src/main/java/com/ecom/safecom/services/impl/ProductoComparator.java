package com.ecom.safecom.services.impl;

import java.io.Serializable;
import java.util.Comparator;

import com.ecom.safecom.entity.Producto;

public class ProductoComparator implements Serializable, Comparator<Producto> {
	private static final long serialVersionUID = 1L;

	private boolean asc = true;
	private int type = 0;

	public ProductoComparator(boolean asc, int type) {
		this.asc = asc;
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int compare(Producto o1, Producto o2) {
		switch (this.type) {
		case 1:
			return o1.getCodigo_producto().compareTo(o2.getCodigo_producto()) * (asc ? 1 : -1);
		case 2:
			return o1.getDescripcion().compareTo(o2.getDescripcion()) * (asc ? 1 : -1);
		default:
			return o1.getCodigo_producto().compareTo(o2.getCodigo_producto()) * (asc ? 1 : -1);
		}
	}

}
