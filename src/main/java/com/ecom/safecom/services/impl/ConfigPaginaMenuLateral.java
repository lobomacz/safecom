package com.ecom.safecom.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.ecom.safecom.entity.PaginaMenuLateral;
import com.ecom.safecom.services.IConfigPaginaMenuLateral;

@Component("configPaginaMenuLateral")
@Scope(value="request",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ConfigPaginaMenuLateral implements IConfigPaginaMenuLateral {
	
	HashMap<String, PaginaMenuLateral> pageMap = new LinkedHashMap<String, PaginaMenuLateral>();
	
	public ConfigPaginaMenuLateral(){
		pageMap.put("contabilidad", new PaginaMenuLateral("Contabilidad", "imgs/32/view-expenses-categories.png"));
		pageMap.get("contabilidad").addItem("contabilidad", "Contabilidad", "imgs/32/view-expenses-categories.png", "paginas/contabilidad.zul");
		pageMap.get("contabilidad").addItem("calendario", "Calendario", "imgs/32/calendar.png", "paginas/conta_calendario.zul");
		pageMap.get("contabilidad").addItem("cuentas", "Cuentas", "imgs/32/conta-cuentas.png", "paginas/conta_cuentas.zul");
		pageMap.put("materiales", new PaginaMenuLateral("Materiales", "imgs/32/almacen.png"));
		pageMap.get("materiales").addItem("almacen", "Almacen", "imgs/32/almacen.png", "paginas/materiales.zul");
		pageMap.get("materiales").addItem("articulos", "Articulos", "imgs/32/articulos.png", "paginas/mat_articulos.zul");
		pageMap.get("materiales").addItem("tipos", "Tipos de Articulos", "imgs/32/tipo-producto.png", "paginas/mat_tipos.zul");
		pageMap.get("materiales").addItem("unidades", "Unidades de Medida", "imgs/32/medidas.png", "paginas/mat_unidades.zul");
		pageMap.put("ventas",new PaginaMenuLateral("Ventas", "imgs/32/mail-outbox.png"));
		pageMap.get("ventas").addItem("ventas", "Ventas", "imgs/32/mail-outbox.png", "paginas/ventas_ventas.zul");
		pageMap.get("ventas").addItem("proformas", "Proformas", "imgs/32/applications-accessories1.png", "paginas/ventas_proformas.zul");
		pageMap.put("compras", new PaginaMenuLateral("Compras", "imgs/32/mail-inbox.png"));
		pageMap.get("compras").addItem("compras", "Compras", "imgs/32/mail-inbox.png", "paginas/compras.zul");
		pageMap.put("caja", new PaginaMenuLateral("Caja", "imgs/32/finanzas.png"));
		pageMap.get("caja").addItem("caja", "Caja", "imgs/32/finanzas.png", "paginas/caja_main.zul");
		pageMap.get("caja").addItem("arqueo_caja", "Arqueo de Caja", "imgs/32/view-income-categories.png", "paginas/caja_arqueo.zul");
		pageMap.put("empleados", new PaginaMenuLateral("Empleados", "imgs/32/empleados.png"));
		pageMap.get("empleados").addItem("empleados", "Empleados", "imgs/32/empleados.png", "paginas/empleados.zul");
		pageMap.put("clientes", new PaginaMenuLateral("Clientes", "imgs/32/clientes.png"));
		pageMap.get("clientes").addItem("clientes", "Clientes", "imgs/32/clientes.png", "paginas/clientes.zul");
		pageMap.put("proveedores", new PaginaMenuLateral("Proveedores", "imgs/32/proveedor.png"));
		pageMap.get("proveedores").addItem("proveedores", "Proveedores", "imgs/32/proveedor.png", "paginas/proveedores.zul");
		pageMap.put("reportes", new PaginaMenuLateral("Reportes", "imgs/32/view-documents-finances.png"));
		pageMap.get("reportes").addItem("balance", "Balance General", "imgs/32/view-financial-list.png", "paginas/reportes/reporte_balance_general.zul");
		
		//PaginaMenuLateral pagina = new PaginaMenuLateral("Contabilidad", "imgs/view-expenses-categories.png");
		//pagina.addItem("contabilidad", "Contabilidad", "imgs/view-expenses-categories.png", "paginas/contabilidad.zul");
		//pagina.addItem("calendario", "Calendario", "", "paginas/conta_calendario.zul");
		//pagina.addItem("cuentas", "Cuentas", "", "paginas/conta_cuentas.zul");
		
		//pagina = new PaginaMenuLateral("Materiales", "imgs/32/almacen.png");
		//pagina.addItem("almacen", "Almacen", "imgs/32/almacen.png", "paginas/materiales.zul");
		//pagina.addItem("articulos", "Articulos", "", "paginas/mat_articulos.zul");
		//pagina.addItem("tipos", "Tipos de Articulos", "", "mat_tipos.zul");
		//pagina.addItem("unidades", "Unidades de Medida", "", "paginas/mat_unidades.zul");
		//pageMap.put("materiales", pagina);
		/*
		pageMap.put("contabilidad", new PaginaMenuLateral("contabilidad", "Contabilidad", "imgs/view-expenses-categories.png", "paginas/contabilidad.zul"));
		pageMap.put("materiales", new PaginaMenuLateral("materiales", "Materiales", "imgs/32/almacen.png", "paginas/materiales.zul"));
		//pageMap.put("compras", new PaginaMenuLateral("compras", "Compras", "imgs/mail-inbox.png", "paginas/compras.zul"));
		pageMap.put("ventas",new PaginaMenuLateral("ventas", "Ventas", "imgs/32/productos.png", "paginas/ventas.zul"));
		pageMap.put("productos", new PaginaMenuLateral("caja", "Caja", "imgs/32/finanzas.png", "paginas/caja.zul"));
		pageMap.put("empleados", new PaginaMenuLateral("empleados", "Empleados", "imgs/32/empleados.png", "paginas/empleados.zul"));
		pageMap.put("clientes", new PaginaMenuLateral("clientes", "Clientes", "imgs/32/clientes.png", "paginas/clientes.zul"));
		pageMap.put("proveedores", new PaginaMenuLateral("proveedores", "Proveedores", "imgs/32/proveedor.png", "paginas/proveedores.zul"));
		*/
	}
	
	public List<PaginaMenuLateral> getPages(){
		return new ArrayList<PaginaMenuLateral>(pageMap.values());
	}
	
	public PaginaMenuLateral getPage(String name){
		return pageMap.get(name);
	}

}
