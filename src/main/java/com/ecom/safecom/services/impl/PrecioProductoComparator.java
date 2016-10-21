package com.ecom.safecom.services.impl;

import java.io.Serializable;
import java.util.Comparator;

import com.ecom.safecom.entity.PrecioProducto;

public class PrecioProductoComparator implements Serializable,
		Comparator<PrecioProducto> {

	private static final long serialVersionUID = 1L;

	private boolean asc = true;
	
	public PrecioProductoComparator(){}

	public PrecioProductoComparator(boolean asc) {
		this.asc = asc;
	}

	public int compare(PrecioProducto o1, PrecioProducto o2) {
		return o1.getPrecioPK().getNumero_precio()
				.compareTo(o2.getPrecioPK().getNumero_precio())
				* (this.asc ? 1 : -1);
	}

}
