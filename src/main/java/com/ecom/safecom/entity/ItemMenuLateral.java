package com.ecom.safecom.entity;


public class ItemMenuLateral extends PaginaMenuLateral{

	private static final long serialVersionUID = 1L;

	String name;
	String uri;

	public ItemMenuLateral(String name, String label, String iconuri, String uri) {
		super(label, iconuri);

		this.name = name;
		this.uri = uri;
	}

	public String getName() {
		return this.name;
	}

	public String getUri() {
		return this.uri;
	}
}
