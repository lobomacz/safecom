package com.ecom.safecom.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component("userCredential")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserCredential implements Serializable{

	/**
	 * CLASE CREADA PARA MANEJAR LAS CREDENCIALES DE USUARIO
	 * DURANTE LA SESION ESTÉ ACTIVA
	 */
	private static final long serialVersionUID = 1L;
	
	String account;
	String name;
	
	Set<AuthItem> roles;
	
	public UserCredential(String account, String name){
		this.account = account;
		this.name = name;
	}
	
	public UserCredential(){
		this.account = "anonimo";
		this.name = "Anónimo";
		roles = new HashSet<AuthItem>();
	}
	
	public String getAccount(){
		return this.account;
	}
	
	public void setAccount(String account){
		this.account = account;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean esAnonimo(){
		return tieneRol("anonimo") || "anonimo".equals(this.account);
	}
	
	public boolean tieneRol(String rol){
		return roles.contains(rol);
	}
	
	public void agregaRol(String rol){
		//this.roles.add(rol);
	}

}
