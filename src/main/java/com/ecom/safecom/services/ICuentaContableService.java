package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.CuentaContable;

public interface ICuentaContableService {
	
	List<CuentaContable> getListaCuentasContables();

	CuentaContable getCuentaContable(String cuenta);
	
	CuentaContable getByName(String nombre);
	
	CuentaContable searchByName(String nombre);
	
	List<CuentaContable> getChildren(String padre);
	
	List<CuentaContable> listByName(String name);
	
	List<CuentaContable> listaCuentasBalance();
	
	CuentaContable reloadCuentaContable(CuentaContable cuenta);
	
	CuentaContable saveCuentaContable(CuentaContable cuenta);
	
	CuentaContable updateCuentaContable(CuentaContable cuenta);
	
	void deleteCuentaContable(CuentaContable cuenta);
}
