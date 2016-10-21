package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.PaginaMenuLateral;

public interface IConfigPaginaMenuLateral {
	public List<PaginaMenuLateral> getPages();
	
	public PaginaMenuLateral getPage(String name);
}
