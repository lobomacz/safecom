package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaginaMenuLateral implements Serializable {

	private static final long serialVersionUID = 1L;
	String label;
	String iconuri;
	List<ItemMenuLateral> items;
	
	
	public PaginaMenuLateral(String label, String iconuri){
		this.label = label;
		this.iconuri = iconuri;
		items = new ArrayList<ItemMenuLateral>();
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public String getIconUri(){
		return this.iconuri;
	}
	
	public List<ItemMenuLateral> getItems(){
		return this.items;
	}
	
	public void addItem(String name, String label, String iconuri, String uri){
		ItemMenuLateral item = new ItemMenuLateral(name, label, iconuri, uri);
		items.add(item);
	}
	
}
