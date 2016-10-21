package com.ecom.safecom.controllers;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ErrorEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.ecom.safecom.entity.CuentaContable;
import com.ecom.safecom.entity.TipoProducto;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.TipoProductoServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TipoProductoController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	TipoProductoServiceImpl tipoProductoService;
	@WireVariable
	CuentaContableServiceImpl cuentaContableService;

	@Wire
	Toolbarbutton tlbtnRecargar;
	@Wire
	Toolbarbutton tlbtnGuardar;
	@Wire
	Toolbarbutton tlbtnAgregar;
	@Wire
	Toolbarbutton tlbtnEditar;
	@Wire
	Toolbarbutton tlbtnEliminar;

	// CAMPOS DE TIPO DE PRODUCTO
	@Wire
	Listbox lstTiposProducto;
	@Wire
	Textbox txtDescripcionTipoProducto;
	@Wire
	Combobox cmbCuentaTipoProducto;
	@Wire 
	Combobox cmbCuentaCostoProducto;

	private TipoProducto tipoProductoSelected;

	ListModelList<TipoProducto> listaModeloTiposProducto;
	ListModelList<CuentaContable> listaModeloCuentas;
	ListModelList<CuentaContable> listaModeloCuentasCosto;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		CargaListaTiposProductos();
	}

	private void CargaListaTiposProductos() {
		List<TipoProducto> listaTiposProducto = tipoProductoService
				.getListaTipoProducto();
		listaModeloTiposProducto = new ListModelList<TipoProducto>(
				listaTiposProducto);
		lstTiposProducto.setModel(listaModeloTiposProducto);
		// MEJORAR LA RECUPERACION DE CUENTAS FILTRANDO SOLO LAS CUENTAS DE
		// INVENTARIO
		CuentaContable cuenta = cuentaContableService.getByName("INVENTARIO");
		CuentaContable costos = cuentaContableService.getByName("COSTO DE VENTA");
		if(cuenta != null && costos != null){
			List<CuentaContable> listaCuentas = cuentaContableService
					.getChildren(cuenta.getCuenta());
			listaModeloCuentas = new ListModelList<CuentaContable>(listaCuentas);
			cmbCuentaTipoProducto.setModel(listaModeloCuentas);
			
			listaModeloCuentasCosto = new ListModelList<CuentaContable>(costos.getCuentasHijo());
			cmbCuentaCostoProducto.setModel(listaModeloCuentasCosto);
		}
		
		if (listaModeloTiposProducto.isSelectionEmpty()) {
			tipoProductoSelected = null;
		} else {
			tipoProductoSelected = listaModeloTiposProducto.getSelection()
					.iterator().next();
		}
		RefreshTipoProductoDetail();
	}

	private void RefreshTipoProductoDetail() {
		if (tipoProductoSelected != null
				&& tipoProductoSelected.getId_tipo_producto() != null) {
			txtDescripcionTipoProducto.setValue(tipoProductoSelected
					.getDescripcion());
			/*for (Comboitem item : cmbCuentaTipoProducto.getItems()) {
				if (item.getValue()
						.toString()
						.equals(tipoProductoSelected.getCuentaContable()
								.getCuenta())) {
					cmbCuentaTipoProducto.setSelectedItem(item);
				}
			}*/
			
			listaModeloCuentas.clearSelection();
			listaModeloCuentasCosto.clearSelection();
			
			
			for(CuentaContable cuenta : listaModeloCuentas){
				if(tipoProductoSelected.getCuentaContable().getCuenta().equals(cuenta.getCuenta())){
					List<CuentaContable> seleccion = new ArrayList<CuentaContable>();
					seleccion.add(cuenta);
					listaModeloCuentas.setSelection(seleccion);
					break;
				}
			}
			
			for(CuentaContable cuenta : listaModeloCuentasCosto){
				if(tipoProductoSelected.getCuentaCosto().getCuenta().equals(cuenta.getCuenta())){
					List<CuentaContable> seleccion = new ArrayList<CuentaContable>();
					seleccion.add(cuenta);
					listaModeloCuentasCosto.setSelection(seleccion);
					break;
				}
			}

			txtDescripcionTipoProducto.setDisabled(true);
			cmbCuentaTipoProducto.setDisabled(true);
			cmbCuentaCostoProducto.setDisabled(true);

			tlbtnEditar.setDisabled(false);
			tlbtnEliminar.setDisabled(false);
			tlbtnGuardar.setDisabled(true);
		}
	}

	private TipoProducto GuardaTipoProducto() {
		if (tipoProductoSelected != null) {
			tipoProductoSelected.setDescripcion(txtDescripcionTipoProducto
					.getValue());
			//tipoProductoSelected.setCuentaContable(cuentaContableService.getCuentaContable(cmbCuentaTipoProducto.getValue()));
			
			tipoProductoSelected.setCuentaContable(cuentaContableService.reloadCuentaContable(listaModeloCuentas.getSelection().iterator().next()));
			tipoProductoSelected.setCuentaCosto(listaModeloCuentasCosto.getSelection().iterator().next());
			if (tipoProductoSelected.getId_tipo_producto() != null) {
				try {
					tipoProductoSelected = tipoProductoService
							.updateTipoProducto(tipoProductoSelected);
				} catch (Exception ex) {

				}
			} else {
				try {
					tipoProductoSelected = tipoProductoService
							.saveTipoProducto(tipoProductoSelected);
				} catch (Exception ex) {

				} finally {
					listaModeloTiposProducto.add(tipoProductoSelected);
				}
			}
		}

		return tipoProductoSelected;
	}

	private void BorraTipoProducto() {
		if (tipoProductoSelected != null
				&& tipoProductoSelected.getId_tipo_producto() != null) {
			try {
				// tipoProductoService.deleteTipoProducto(listaModeloTiposProducto.remove(listaModeloProducto.indexOf(tipoProductoSelected)));
				tipoProductoService.deleteTipoProducto(tipoProductoSelected);
				listaModeloTiposProducto.remove(tipoProductoSelected);
				Messagebox.show(
						"El registro de tipo de producto fué eliminado!",
						"Registro Eliminado", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (Exception ex) {
				Messagebox.show(
						"Ocurrió un error al eliminar datos de tipo de producto. "
								+ ex.getMessage(), "Error de Datos",
						Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	private void ClearTipoProductoFields() {
		txtDescripcionTipoProducto.setRawValue("");
	}

	@Listen("onSelect=#lstTiposProducto")
	public void SeleccionaTipoProducto() {
		if (!listaModeloTiposProducto.isSelectionEmpty()) {
			tipoProductoSelected = listaModeloTiposProducto.getSelection()
					.iterator().next();
			RefreshTipoProductoDetail();
		}
	}

	@Listen("onClick=#tlbtnAgregar")
	public void Nuevo() {
		listaModeloTiposProducto.clearSelection();
		tipoProductoSelected = new TipoProducto();
		ClearTipoProductoFields();
		txtDescripcionTipoProducto.select();
		txtDescripcionTipoProducto.setDisabled(false);
		cmbCuentaTipoProducto.setDisabled(false);
		cmbCuentaCostoProducto.setDisabled(false);
		tlbtnGuardar.setDisabled(false);
	}

	@Listen("onClick=#tlbtnEditar")
	public void Editar() {
		txtDescripcionTipoProducto.setDisabled(false);
		cmbCuentaTipoProducto.setDisabled(false);
		cmbCuentaCostoProducto.setDisabled(false);
		tlbtnGuardar.setDisabled(false);
	}

	@Listen("onClick=#tlbtnGuardar")
	public void Guardar() {
		if (tipoProductoSelected != null) {
			Messagebox
					.show("Se guardarán los datos en la base de datos. ¿Desea continuar?",
							"Guardar Datos de Tipo de Producto", Messagebox.YES
									| Messagebox.ABORT, Messagebox.QUESTION,
							new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (Messagebox.ON_YES.equals(event
											.getName())) {
										tipoProductoSelected = GuardaTipoProducto();

										if (tipoProductoSelected != null) {
											listaModeloTiposProducto
													.addToSelection(tipoProductoSelected);
											// RefreshTipoProductoDetail();
											Messagebox
													.show("Los datos se almacenaron satisfactoriamente!.",
															"Registro Guardado",
															Messagebox.OK,
															Messagebox.INFORMATION);
											

										}
									} else if (Messagebox.ON_ABORT.equals(event
											.getName())) {
										tipoProductoSelected = null;
										listaModeloTiposProducto.iterator()
												.next();
										
									}
									ClearTipoProductoFields();
									listaModeloCuentas.clearSelection();
									listaModeloCuentasCosto.clearSelection();
									txtDescripcionTipoProducto
											.setDisabled(true);
									cmbCuentaTipoProducto.setDisabled(true);
									cmbCuentaCostoProducto.setDisabled(true);
									tlbtnGuardar.setDisabled(true);
									tlbtnEditar.setDisabled(true);
									tlbtnEliminar.setDisabled(true);
								}
							});
		}
	}

	@Listen("onClick=#tlbtnEliminar")
	public void Eliminar() {
		if (tipoProductoSelected != null) {

			if (tipoProductoSelected.getId_tipo_producto() == null) {
				Messagebox
						.show("No se ha seleccionado un registro válido para eliminar. Por favor revise y vuelva a intentarlo.",
								"Registro Inválido", Messagebox.OK,
								Messagebox.ERROR);
				return;
			}

			Messagebox
					.show("Se eliminará el registro de la base de datos. ¿Desea continuar?",
							"Eliminar Tipo de Producto", Messagebox.NO
									| Messagebox.YES, Messagebox.EXCLAMATION,
							new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (Messagebox.ON_YES.equals(event
											.getName())) {
										BorraTipoProducto();
										ClearTipoProductoFields();

									} else {
										listaModeloTiposProducto
												.clearSelection();
										tipoProductoSelected = null;
										ClearTipoProductoFields();
									}

									txtDescripcionTipoProducto
											.setDisabled(true);
									tlbtnEliminar.setDisabled(true);
									tlbtnGuardar.setDisabled(true);
									tlbtnEditar.setDisabled(true);
								}
							});
		}
	}

}
