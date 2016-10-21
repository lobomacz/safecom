package com.ecom.safecom.controllers;

import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Toolbarbutton;

import com.ecom.safecom.entity.Empleado;
import com.ecom.safecom.entity.Venta;
import com.ecom.safecom.services.impl.AutenticacionService;
import com.ecom.safecom.services.impl.VentaServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ProformasController extends SelectorComposer<Component> {

	
	private static final long serialVersionUID = 1L;
	
	@WireVariable
	VentaServiceImpl ventaService;
	@WireVariable
	private AutenticacionService autenService;
	
	Venta ventaSelected;
	
	List<Venta> listaProformas;
	ListModelList<Venta> listaModeloProformas;
	
	@Wire
	Listbox lstProformas;
	@Wire
	Toolbarbutton tlbtnImprimir;
	@Wire
	Toolbarbutton tlbtnEditar;
	@Wire
	Toolbarbutton tlbtnEliminar;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		ventaSelected = null;
		
		if(Executions.getCurrent().hasAttribute("ventaSelected")){
			Executions.getCurrent().setAttribute("ventaSelected", ventaSelected);
		}
		
		listaProformas = ventaService.listaProformas();
		listaModeloProformas = new ListModelList<Venta>(listaProformas);
		lstProformas.setModel(listaModeloProformas);
	}
	
	@Listen("onSelect=#lstProformas")
	public void SeleccionaProforma(){
		
		if(!listaModeloProformas.isSelectionEmpty()){
			ventaSelected = listaModeloProformas.getSelection().iterator().next();
			
			tlbtnEditar.setDisabled(false);
			tlbtnEliminar.setDisabled(false);
			tlbtnImprimir.setDisabled(false);
			
		}
	}
	
	@Listen("onClick=#tlbtnAgregar")
	public void IngresarProforma(){
		ventaSelected = new Venta();
		
		Empleado vendedor = autenService.getUserCredential().getEmpleado();
		Date fecha = new Date();
		
		ventaSelected = new Venta();
		ventaSelected.setAnulado(false);
		ventaSelected.setCancelado(false);
		ventaSelected.setCredito(false);
		ventaSelected.setFecha_venta(fecha);
		ventaSelected.setEmpleado(vendedor);
		ventaSelected.setProforma(true);
		Executions.getCurrent().setAttribute("ventaSelected", ventaSelected);
		Include include = (Include)Selectors.iterable(lstProformas.getPage(), "#maininclude").iterator().next();
		include.setSrc("/paginas/ventas_nuevo.zul");
		getPage().getDesktop().setBookmark("p_proformas_nuevo");
	}
	
	@Listen("onClick=#tlbtnEditar")
	public void EditarProforma(){
		
	}
	
	@Listen("onClick=#tlbtnEliminar")
	public void EliminarProforma(){
		
	}

}
