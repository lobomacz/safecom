package com.ecom.safecom.controllers;

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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.Almacen;
import com.ecom.safecom.entity.EntradaMateriales;
import com.ecom.safecom.entity.Inventario;
import com.ecom.safecom.entity.SalidaMateriales;
import com.ecom.safecom.services.impl.AlmacenServiceImpl;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.EntradaMaterialesServiceImpl;
import com.ecom.safecom.services.impl.InventarioServiceImpl;
import com.ecom.safecom.services.impl.ProductoServiceImpl;
import com.ecom.safecom.services.impl.SalidaMaterialesServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AlmacenController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	// /TODO: - GENERAR CODIGO PARA LA CARGA Y ALMACENAMIENTO DE LA IMAGEN DE
	// PRODUCTO
	// / - ACTUALIZAR EL DATABINDING AL ELIMINAR REGISTROS
	// / - ESTABLECER MECANISMO DE HABILITAR Y DESHABILITAR BOTONES DE LA BARRA
	// DE HERRAMIENTAS

	@WireVariable
	AlmacenServiceImpl almacenService;
	@WireVariable
	InventarioServiceImpl inventarioService;
	@WireVariable
	ProductoServiceImpl productoService;
	@WireVariable
	EntradaMaterialesServiceImpl entradaMaterialesService;
	@WireVariable
	SalidaMaterialesServiceImpl salidaMaterialesService;
	@WireVariable
	CuentaContableServiceImpl cuentaContableService;

	@Wire
	Window winMateriales;
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
	@Wire
	Toolbarbutton tlbtnCheckEntrada;
	@Wire
	Toolbarbutton tlbtnVerEntrada;
	@Wire
	Toolbarbutton tlbtnEliminarEntrada;
	@Wire
	Toolbarbutton tlbtnCheckSalida;
	@Wire
	Toolbarbutton tlbtnVerSalida;
	@Wire
	Toolbarbutton tlbtnEliminarSalida;
	@Wire
	Intbox intbIdalmacen;
	@Wire
	Textbox txtCuenta_contable;
	@Wire
	Textbox txtUbicacion;
	@Wire
	Toolbarbutton tlbtnNuevaEntrada;
	@Wire
	Listbox lstAlmacenes;
	@Wire
	Listbox lstDetalleAlmacen;
	@Wire
	Listbox lstEntradasMateriales;
	@Wire
	Listbox lstSalidasMateriales;

	// VARIABLES LOCALES
	Almacen almacenSelected;

	EntradaMateriales entradaSelected;

	SalidaMateriales salidaSelected;

	List<Almacen> listaAlmacenes;
	ListModelList<Almacen> listaModeloAlmacenes;

	List<Inventario> listaInventario;
	ListModelList<Inventario> listaModeloInventario;

	List<EntradaMateriales> listaEntradas;
	ListModelList<EntradaMateriales> listaModeloEntradas;

	List<SalidaMateriales> listaSalidas;
	ListModelList<SalidaMateriales> listaModeloSalidas;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		listaAlmacenes = almacenService.listaTodo();
		listaModeloAlmacenes = new ListModelList<Almacen>(listaAlmacenes);
		lstAlmacenes.setModel(listaModeloAlmacenes);

	}

	@Listen("onSelect=#lstAlmacenes")
	public void SeleccionaAlmacen() {
		if (!listaModeloAlmacenes.isSelectionEmpty()) {
			this.almacenSelected = listaModeloAlmacenes.getSelection()
					.iterator().next();
			listaEntradas = entradaMaterialesService
					.listaPendientes(this.almacenSelected.getId_almacen());
			listaModeloEntradas = new ListModelList<EntradaMateriales>(
					listaEntradas);
			lstEntradasMateriales.setModel(listaModeloEntradas);

			listaInventario = inventarioService.listaTodo(almacenSelected
					.getId_almacen());
			listaModeloInventario = new ListModelList<Inventario>(
					listaInventario);
			lstDetalleAlmacen.setModel(listaModeloInventario);

			listaSalidas = salidaMaterialesService
					.listaPendientesPorAlmacen(this.almacenSelected
							.getId_almacen());
			listaModeloSalidas = new ListModelList<SalidaMateriales>(
					listaSalidas);
			lstSalidasMateriales.setModel(listaModeloSalidas);

			intbIdalmacen.setValue(this.almacenSelected.getId_almacen());
			txtCuenta_contable.setValue(this.almacenSelected.getCuenta()
					.getCuenta());
			txtUbicacion.setValue(this.almacenSelected.getUbicacion());

			tlbtnEditar.setDisabled(false);
			tlbtnEliminar.setDisabled(false);
		}
	}

	@Listen("onSelect=#lstEntradasMateriales")
	public void SeleccionaEntrada() {
		if (!listaModeloEntradas.isSelectionEmpty()) {
			entradaSelected = listaModeloEntradas.getSelection().iterator()
					.next();
			tlbtnVerEntrada.setDisabled(false);
			tlbtnEliminarEntrada.setDisabled(false);
		}
	}

	@Listen("onSelect=#lstSalidasMateriales")
	public void SeleccionaSalida() {
		if (!listaModeloSalidas.isSelectionEmpty()) {
			salidaSelected = listaModeloSalidas.getSelection().iterator()
					.next();
			tlbtnVerSalida.setDisabled(false);
			tlbtnEliminarSalida.setDisabled(false);
		}
	}

	@Listen("onClick=#tlbtnGuardar")
	public void GuardaAlmacen() {
		if (almacenSelected != null) {
			Messagebox
					.show("Se guardarán los datos en la base de datos.¿Desea continuar?",
							"Guardar Datos", Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION, new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (Messagebox.ON_YES.equals(event
											.getName())) {
										almacenSelected
												.setCuenta(cuentaContableService
														.getCuentaContable(txtCuenta_contable
																.getValue()));
										almacenSelected
												.setUbicacion(txtUbicacion
														.getValue());

										try {
											if (almacenSelected.getId_almacen() != null) {
												almacenService
														.guardarAlmacen(almacenSelected);
											} else {
												almacenService
														.actualizarAlmacen(almacenSelected);
											}
											Messagebox
													.show("Datos guardados con éxito.",
															"Datos Guardados",
															Messagebox.OK,
															Messagebox.EXCLAMATION);
											DesabilitarCampos();
										} catch (Exception ex) {
											Messagebox.show(
													"Ocurrió un error al tratar de guardar/actualizar el registro. Error: "
															+ ex.getMessage(),
													"Error!", Messagebox.OK,
													Messagebox.ERROR);
										} finally {
											RecargaListaAlmacen();
										}
									} else {
										almacenSelected = null;
										DesabilitarCampos();
									}
								}
							});
		}
	}

	@Listen("onClick=#tlbtnEliminar")
	public void RemueveAlmacen() {
		if (almacenSelected != null && almacenSelected.getId_almacen() != null) {
			Messagebox
					.show("Se eliminará el registro de la base de datos. ¿Desea Continuar?",
							"Eliminar Registro",
							Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION, new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (Messagebox.ON_YES.equals(event
											.getName())) {
										try {
											almacenService
													.borrarAlmacen(listaModeloAlmacenes
															.get(listaModeloAlmacenes
																	.indexOf(almacenSelected)));
											almacenSelected = null;
											Messagebox
													.show("El registro se elimnó con éxito!",
															"Registro Eliminado",
															Messagebox.OK,
															Messagebox.EXCLAMATION);
											DesabilitarCampos();
										} catch (Exception ex) {
											Messagebox.show(
													"Ocurrió un error al tratar de eliminar el registro. Error: "
															+ ex.getMessage(),
													"Error!", Messagebox.OK,
													Messagebox.ERROR);
										}
									} else {
										almacenSelected = null;
										DesabilitarCampos();
									}
								}
							});
		}
	}

	@Listen("onClick=#tlbtnAgregar")
	public void AgregaAlmacen() {
		almacenSelected = new Almacen();
		tlbtnGuardar.setDisabled(false);
		tlbtnEliminar.setDisabled(true);
		tlbtnEditar.setDisabled(true);
		txtCuenta_contable.setDisabled(false);
		txtUbicacion.setDisabled(false);
		txtCuenta_contable.focus();
	}

	@Listen("onClick=#tlbtnEditar")
	public void EditarAlmacen() {
		txtCuenta_contable.setDisabled(false);
		txtUbicacion.setDisabled(false);
		txtCuenta_contable.focus();
	}

	@Listen("onClick=#tlbtnNuevaEntrada")
	public void NuevaEntrada() {
		if (almacenSelected != null) {
			EntradaMateriales entrada = new EntradaMateriales();
			entrada.setAlmacen(almacenSelected);
			entrada.setRecibido(false);
			Executions.getCurrent().setAttribute("entradaMateriales", entrada);
			Include include = (Include) Selectors
					.iterable(winMateriales.getPage(), "#maininclude")
					.iterator().next();
			include.setSrc("/paginas/mat_entradas.zul");
			getPage().getDesktop().setBookmark("p_entradas");
		} else {
			Messagebox.show("No se ha seleccionado un registro de almacen.");
			return;
		}
	}

	@Listen("onClick=#tlbtnVerEntrada")
	public void VerEntrada() {
		if (this.entradaSelected != null) {
			Executions.getCurrent().setAttribute("entradaMateriales",
					entradaSelected);
			Include include = (Include) Selectors
					.iterable(winMateriales.getPage(), "#maininclude")
					.iterator().next();
			include.setSrc("/paginas/mat_entradas.zul");
			getPage().getDesktop().setBookmark("p_entradas");
		}
	}

	@Listen("onClick=#tlbtnEliminarEntrada")
	public void EliminarEntrada() {
		if (entradaSelected != null) {
			Messagebox
					.show("Se eliminará el registro de la base de datos. ¿Desea continuar?",
							"Eliminar Registro",
							Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION, new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (Messagebox.ON_YES.equals(event
											.getName())) {
										try {
											entradaMaterialesService
													.borrarEntrada(listaModeloEntradas
															.remove(listaModeloEntradas
																	.indexOf(entradaSelected)));
											entradaSelected = null;
										} catch (Exception ex) {
											Messagebox.show(
													"Ocurrió un error al tratar de eliminar el registro. Error: "
															+ ex.getMessage(),
													"Error!", Messagebox.OK,
													Messagebox.ERROR);
										}
									} else {
										listaModeloEntradas.clearSelection();
										entradaSelected = null;
									}
								}
							});
		} else {
			Messagebox.show("No se ha seleccionado un registro de entrada.");
		}
	}

	@Listen("onClick=#tlbtnNuevaSalida")
	public void NuevaSalida() {

	}

	@Listen("onClick=#tlbtnVerSalida")
	public void VerSalida() {

	}

	@Listen("onClick=#tlbtnEliminarSalida")
	public void EliminarSalida() {

	}

	private void DesabilitarCampos() {
		tlbtnEditar.setDisabled(true);
		tlbtnEliminar.setDisabled(true);
		txtCuenta_contable.setDisabled(true);
		txtUbicacion.setDisabled(true);
	}

	private void RecargaListaAlmacen() {
		listaAlmacenes = almacenService.listaTodo();
		listaModeloAlmacenes = new ListModelList<Almacen>(listaAlmacenes);
		lstAlmacenes.setModel(listaModeloAlmacenes);
	}

}
