package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.CuentaContableDao;
import com.ecom.safecom.entity.CuentaContable;
import com.ecom.safecom.services.ICuentaContableService;

@Service("cuentaContableService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CuentaContableServiceImpl implements ICuentaContableService {

	@Autowired
	CuentaContableDao dao;

	public List<CuentaContable> getListaCuentasContables() {

		return dao.getAll();
	}
	
	public List<CuentaContable> listaCuentasBalance(){
		return dao.listaCuentasBalance();
	}

	public CuentaContable getCuentaContable(String cuenta) {

		return dao.get(cuenta);
	}

	public CuentaContable reloadCuentaContable(CuentaContable cuenta) {

		return dao.reload(cuenta);
	}

	public CuentaContable saveCuentaContable(CuentaContable cuenta) {

		return dao.save(cuenta);
	}

	public CuentaContable updateCuentaContable(CuentaContable cuenta) {

		return dao.update(cuenta);
	}

	public void deleteCuentaContable(CuentaContable cuenta) {
		dao.delete(cuenta);
	}

	public boolean insertCatalogoCuentas(List<CuentaContable> listaCuentas) {
		return dao.saveCatalogo(listaCuentas);
	}

	public CuentaContable getByName(String nombre) {
		return dao.getByName(nombre);
	}

	public List<CuentaContable> getChildren(String padre) {
		return dao.getChildren(padre);
	}

	public CuentaContable searchByName(String nombre) {
		return dao.searchByName(nombre);
	}

	public List<CuentaContable> listByName(String name) {
		return dao.listByName(name);
	}

}
