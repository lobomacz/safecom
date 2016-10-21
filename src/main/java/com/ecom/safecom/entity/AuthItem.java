package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbl_auth_item")
public class AuthItem implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	String name;
	
	Integer type;
	
	@Column(length=100)
	String description;
	
	@Column(length=100)
	String bizrule;
	
	@Column(length=100)
	String data;
	
	@ManyToMany(mappedBy="authitems")
	private Set<Usuario> usuarios;
	
	public AuthItem(){}
	
	public String getName(){
		return this.name;
	}
}
