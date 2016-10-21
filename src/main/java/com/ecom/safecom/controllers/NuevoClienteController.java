package com.ecom.safecom.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.Telefono;
import com.ecom.safecom.entity.Tercero;
import com.ecom.safecom.services.impl.TelefonoServiceImpl;
import com.ecom.safecom.services.impl.TerceroServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class NuevoClienteController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@WireVariable
	TerceroServiceImpl terceroService;
	@WireVariable
	TelefonoServiceImpl telefonoService;
	
	@Wire
	Window wClientes;
	@Wire
	Textbox nombreCliente;
	@Wire
	Textbox direccionCliente;
	@Wire
	Textbox identificacionCliente;
	@Wire
	Textbox contactoCliente;
	@Wire
	Textbox correoCliente;
	@Wire
	Label lblMensajeError;
	@Wire
	Textbox txtTelefono;
	@Wire
	Radiogroup rgrpTipo;
	@Wire
	Listbox lstTelefonos;
	
	private Tercero cliente;
	private Telefono telefonoSelected;
	private String tipo = Executions.getCurrent().getArg().get("tipo").toString();
	private List<Telefono> listaTelefonos;
	private ListModelList<Telefono> listaModeloTelefonos;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		ClearFields();
		listaTelefonos = new ArrayList<Telefono>();
		listaModeloTelefonos = new ListModelList<Telefono>(listaTelefonos);
		lstTelefonos.setModel(listaModeloTelefonos);
	}
	
	@Listen("onClick=#guardarCliente")
	public void Guardar(){
		cliente = new Tercero();
		cliente.setTipo(tipo);
		cliente.setActivo(true);
		cliente.setIdentificacion(identificacionCliente.getValue());
		cliente.setNombre(nombreCliente.getValue());
		cliente.setDireccion(direccionCliente.getValue());
		cliente.setContacto(contactoCliente.getValue());
		cliente.setCorreo_electronico(correoCliente.getValue());
		cliente.setTelefonos(listaModeloTelefonos.getInnerList());
		try{
			terceroService.guardarTercero(cliente);
			Events.postEvent("onAddCliente", getSelf().getPreviousSibling(), cliente);
			Events.sendEvent("onClose", getSelf(), null);
		}catch(Exception ex){
			lblMensajeError.setValue("Ocurrió un error al guardar los datos. "+ex.getMessage());
		}
	}
	
	@Listen("onClose=#wClientes")
	public void Cerrar(){
		ClearFields();
	}
	
	@Listen("onClick=#btnCancelar")
	public void Cancelar(){
		Events.postEvent("onAddCliente", getSelf().getPreviousSibling(), null);
		Events.sendEvent("onClose", getSelf(), null);
	}
	
	@Listen("onClick=#btnAgregarTelefono")
	public void AgregarTelefono(){
		if(txtTelefono.getValue().equals("") || txtTelefono.getValue().length() < 9){
			lblMensajeError.setValue("No ha ingresado un número de teléfono válido.");
			return;
		}
		
		if(telefonoService.buscarTelefono(txtTelefono.getValue()) != null){
			lblMensajeError.setValue("El número de teléfono ya ha sido registrado.");
			rgrpTipo.setSelectedItem(null);
			txtTelefono.setValue("");
			return;
		}
		
		if(rgrpTipo.getSelectedItem() == null){
			lblMensajeError.setValue("Debe seleccionar el tipo de teléfono.");
			return;
		}
		
		telefonoSelected = new  Telefono();
		telefonoSelected.setTelefono(txtTelefono.getValue());
		telefonoSelected.setDescripcion(rgrpTipo.getSelectedItem().getValue().toString());
		
		listaModeloTelefonos.add(telefonoSelected);
		
		rgrpTipo.setSelectedItem(null);
		txtTelefono.setValue("");
		/*try{
			if(telefonoService.buscarTelefono(telefonoSelected.getTelefono())==null){
				listaModeloTelefonos.add(telefonoService.guardarTelefono(telefonoSelected));
			}else{
				listaModeloTelefonos.add(telefonoSelected);
			}
		}catch(Exception ex){
			String error = ex.getMessage();
			lblMensajeError.setValue("El número no se pudo agregar a la lista. "+error);
			return;
		}finally{
			rgrpTipo.setSelectedItem(null);
			txtTelefono.setValue("");
		}*/
	}
	
	@Listen("onClick=#btnRemoverTelefono")
	public void RemoverTelefono(){
		if(!listaModeloTelefonos.isSelectionEmpty() && telefonoSelected != null){
			Telefono tel = telefonoService.recargarTelefono(listaModeloTelefonos.remove(listaModeloTelefonos.indexOf(telefonoSelected)));
			if(tel!=null){
				try{
					telefonoService.borrarTelefono(tel);
				}catch(Exception ex){
					String mens = ex.getMessage();
					lblMensajeError.setValue(mens);
				}
			}
		}
	}
	
	@Listen("onSelect=#lstTelefonos")
	public void SeleccionaTelefono(){
		if(!listaModeloTelefonos.isSelectionEmpty()){
			telefonoSelected = listaModeloTelefonos.getSelection().iterator().next();
		}
	}
	
	private void ClearFields(){
		cliente = null;
		nombreCliente.setValue("");
		direccionCliente.setValue("");
		identificacionCliente.setValue("");
		contactoCliente.setValue("");
		correoCliente.setValue("");
		rgrpTipo.setSelectedItem(null);
		txtTelefono.setRawValue("");
		listaModeloTelefonos = new  ListModelList<Telefono>();
		lstTelefonos.setModel(listaModeloTelefonos);
	}

}
