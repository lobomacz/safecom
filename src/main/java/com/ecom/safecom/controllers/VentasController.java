package com.ecom.safecom.controllers;

import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.Empleado;
import com.ecom.safecom.entity.Venta;
import com.ecom.safecom.services.impl.AlmacenServiceImpl;
import com.ecom.safecom.services.impl.AutenticacionService;
import com.ecom.safecom.services.impl.VentaServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VentasController extends SelectorComposer<Component>{

	private static final long serialVersionUID = 1L;
	
	@WireVariable
	private VentaServiceImpl ventaService;
	@WireVariable
	private AutenticacionService autenService;
	@WireVariable
	private AlmacenServiceImpl almacenService;
	
	Venta ventaSelected;
	
	List<Venta> listaVentas;
	ListModelList<Venta> listaModeloVentas;
	
	@Wire
	Listbox lstListaVentas;
	@Wire
	Toolbarbutton tlbtnEditar;
	@Wire
	Toolbarbutton tlbtnEliminar;
	@Wire
	Toolbarbutton tlbtnImprimir;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		ventaSelected = null;
		
		RecargarVentas();
		
		if(Executions.getCurrent().hasAttribute("ventaSelected")){
			Executions.getCurrent().setAttribute("ventaSelected", null);
		}
	}
	
	@Listen("onSelect=#lstListaVentas")
	public void SelectVenta(){
		if(!listaModeloVentas.isSelectionEmpty()){
			ventaSelected = listaModeloVentas.getSelection().iterator().next();
			tlbtnEditar.setDisabled(false);
			tlbtnEliminar.setDisabled(false);
			tlbtnImprimir.setDisabled(false);
		}
	}
	
	@Listen("onClick=#tlbtnAgregar")
	public void IngresarVenta(){
		Empleado vendedor = autenService.getUserCredential().getEmpleado();
		Date fecha = new Date();
		
		ventaSelected = new Venta();
		ventaSelected.setProforma(false);
		ventaSelected.setAnulado(false);
		ventaSelected.setCancelado(false);
		ventaSelected.setExonerado(false);
		ventaSelected.setCredito(false);
		ventaSelected.setEntregado(false);
		ventaSelected.setFecha_venta(fecha);
		ventaSelected.setEmpleado(vendedor);
		ventaSelected.setAlmacen(almacenService.buscaPrincipal());
		
		Executions.getCurrent().setAttribute("ventaSelected", ventaSelected);
		Include include = (Include) Selectors.iterable(lstListaVentas.getPage(), "#maininclude").iterator().next();
		include.setSrc("/paginas/ventas_nuevo.zul");
		getPage().getDesktop().setBookmark("p_ventas_nuevo");
		
	}
	
	@Listen("onClick=#tlbtnEditar")
	public void EditarVenta(){
		
		ventaSelected = listaModeloVentas.getSelection().iterator().next();
		Executions.getCurrent().setAttribute("ventaSelected", ventaSelected);
		Include include = (Include) Selectors.iterable(lstListaVentas.getPage(), "#maininclude").iterator().next();
		include.setSrc("/paginas/ventas_nuevo.zul");
		getPage().getDesktop().setBookmark("p_ventas_editar");
		
	}
	
	@Listen("onClick=#tlbtnRecargar")
	public void RecargarVentas(){
		listaVentas = ventaService.listaTodo();
		listaModeloVentas = new ListModelList<Venta>(listaVentas);
		lstListaVentas.setModel(listaModeloVentas);
	}
	
	@Listen("onClick=#tlbtnEliminar")
	public void EliminarVenta(){
		
		if(!(ventaSelected.getCancelado() || ventaSelected.getCredito())){
			Messagebox.show("Se eliminará el registro de venta. ¿Desea continuar?", "Eliminar Registro", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
				
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(Messagebox.ON_YES.equals(event.getName())){
						try{
							ventaService.borrarVenta(listaModeloVentas.remove(listaModeloVentas.indexOf(ventaSelected)));
							tlbtnEliminar.setDisabled(true);
							tlbtnEditar.setDisabled(true);
							tlbtnImprimir.setDisabled(true);
							ventaSelected = null;
						}catch(Exception ex){
							Messagebox.show("Se produjo un error al eliminar el registro. "+ex.getMessage());
						}
					}
				}
			});
		}
		
		
	}
	
	@Listen("onClick=#tlbtnImprimir")
	public void ImprimirVenta(){
		
	}

}
