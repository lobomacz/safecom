package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.ecom.safecom.entity.ConversionUnidadMedida;
import com.ecom.safecom.entity.ConversionUnidadPK;
import com.ecom.safecom.entity.UnidadMedida;
import com.ecom.safecom.services.impl.ConversionUnidadMedidaServiceImpl;
import com.ecom.safecom.services.impl.UnidadMedidaServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UnidadesMedidaController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	UnidadMedidaServiceImpl unidadMedidaService;
	@WireVariable
	ConversionUnidadMedidaServiceImpl conversionUnidadMedidaService;

	@Wire
	Tabbox tabsUnidadesMedida;

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

	// CAMPOS DE UNIDAD DE MEDIDA

	@Wire
	Listbox lstUnidadesMedida;
	@Wire
	Textbox txtDescripcionUnidad;
	@Wire
	Textbox txtDescripcionCortaUnidad;
	@Wire
	Checkbox chkUnidadBase;

	// CAMPOS DE CONVERSION DE UNIDAD DE MEDIDA

	@Wire
	Bandbox bbxUnidadOrigen;
	@Wire
	Bandbox bbxUnidadDestino;
	@Wire
	Listbox lstUnidadOrigen;
	@Wire
	Listbox lstUnidadDestino;
	@Wire
	Decimalbox dcmbConversionDirecta;
	@Wire
	Decimalbox dcmbConversionInversa;
	@Wire
	Listbox lstConversionesUnidades;

	private UnidadMedida unidadMedidaSelected;
	private UnidadMedida unidadDestino;
	private ConversionUnidadMedida conversionSelected;
	//private UnidadMedida unidadMedidaDestino;

	ListModelList<UnidadMedida> listaModeloUnidadMedida1;
	ListModelList<UnidadMedida> listaModeloUnidadMedida2;
	ListModelList<ConversionUnidadMedida> listaModeloConversiones;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		//CargaListaUnidadesMedida();
		CargaDatos();
	}

	

	@Listen("onSelect=#tabsUnidadesMedida")
	public void SeleccionaTabUnidad() {
		listaModeloUnidadMedida1.clearSelection();
		/*if (tabsUnidadesMedida.getSelectedIndex() == 0) {
			
		} else {
			CargaListaConversionesMedidas();
		}*/
	}

	@Listen("onSelect=#lstUnidadesMedida")
	public void SeleccionaUnidadMedida() {
		if (!listaModeloUnidadMedida1.isSelectionEmpty()) {
			unidadMedidaSelected = listaModeloUnidadMedida1.getSelection()
					.iterator().next();
			RefreshUnidadMedidaDetail();
		}
	}

	@Listen("onSelect=#lstConversionesUnidades")
	public void SeleccionaConversionUnidad() {
		if (!listaModeloConversiones.isSelectionEmpty()) {
			conversionSelected = listaModeloConversiones.getSelection()
					.iterator().next();
			RefreshConversionesDetail();
		}
	}

	@Listen("onSelect=#lstUnidadOrigen")
	public void SeleccionaUnidadOrigen() {
		unidadMedidaSelected = listaModeloUnidadMedida1.getSelection()
				.iterator().next();
		bbxUnidadOrigen.setValue(unidadMedidaSelected.getDescripcion_unidad());
		bbxUnidadOrigen.close();
	}

	@Listen("onSelect=#lstUnidadDestino")
	public void SeleccionaUnidadDestino() {
		unidadDestino = listaModeloUnidadMedida2.getSelection().iterator()
				.next();
		bbxUnidadDestino.setValue(unidadDestino.getDescripcion_unidad());
		bbxUnidadDestino.close();
		if (listaModeloUnidadMedida1.getSelection().iterator().next().equals(listaModeloUnidadMedida2.getSelection().iterator().next())) {
			bbxUnidadDestino
					.setErrorMessage("La unidad destino no puede ser igual a la unidad origen.");
		} else {
			bbxUnidadDestino.clearErrorMessage();
		}
	}

	@Listen("onChange=#dcmbConversionDirecta")
	public void onChange_ConversionDirecta() {
		if (dcmbConversionDirecta.getValue().compareTo(new BigDecimal(0))>0) {
			BigDecimal inversa = BigDecimal.ONE;
			inversa = inversa.divide(dcmbConversionDirecta.getValue(),7, RoundingMode.UP); //1 / dblConversionDirecta.getValue();
			dcmbConversionInversa.setValue(inversa);
		}
	}

	@Listen("onClick=#tlbtnAgregar")
	public void Nuevo() {
		
		if (tabsUnidadesMedida.getSelectedIndex() == 0) {
			
			listaModeloUnidadMedida1.clearSelection();
			unidadMedidaSelected = new UnidadMedida();
			ClearUnidadMedidaFields();
			txtDescripcionUnidad.select();
			EnableUnidadMedidaFields();
			
		} else {
			
			listaModeloConversiones.clearSelection();
			conversionSelected = new ConversionUnidadMedida();
			ClearConversionesFields();
			bbxUnidadOrigen.select();
			EnableConversionesFields();
			
		}
		
		tlbtnGuardar.setDisabled(false);
		
	}

	@Listen("onClick=#tlbtnRecargar")
	public void Recargar() {
		CargaDatos();
		/*
		if (tabsUnidadesMedida.getSelectedIndex() == 0) {
			CargaListaUnidadesMedida();
		} else {
			CargaListaConversionesMedidas();
		}*/
	}

	@Listen("onClick=#tlbtnEditar")
	public void Editar() {
		if (tabsUnidadesMedida.getSelectedIndex() == 0) {
			EnableUnidadMedidaFields();
		} else {
			EnableConversionesFields();
		}
		tlbtnGuardar.setDisabled(false);
	}

	@Listen("onClick=#tlbtnGuardar")
	public void Guardar() {
		if (tabsUnidadesMedida.getSelectedIndex() == 0) {
			if (unidadMedidaSelected != null) {
				Messagebox
						.show("Se guardarán los datos en la base de datos. ¿Desea continuar?",
								"Guardar Datos de U/M", Messagebox.YES
										| Messagebox.ABORT,
								Messagebox.QUESTION,
								new EventListener<Event>() {

									public void onEvent(Event event)
											throws Exception {

										if (Messagebox.ON_YES.equals(event
												.getName())) {
											unidadMedidaSelected = GuardaUnidadMedida();
											if (unidadMedidaSelected != null) {
												listaModeloUnidadMedida1
														.addToSelection(unidadMedidaSelected);
												// RefreshUnidadMedidaDetail();
												Messagebox
														.show("Los datos se almacenaron satisfactoriamente!.",
																"Registro Guardado",
																Messagebox.OK,
																Messagebox.INFORMATION);
											}
										} else if (Messagebox.ON_ABORT
												.equals(event.getName())) {
											unidadMedidaSelected = null;
											listaModeloUnidadMedida1
													.clearSelection();
										}
										ClearUnidadMedidaFields();
										DisableUnidadMedidaFields();
										tlbtnGuardar.setDisabled(true);
										tlbtnEditar.setDisabled(true);
										tlbtnEliminar.setDisabled(true);
										
										//CargaListaUnidadesMedida();
									}
								});
			}
			CargaDatos();
		} else {
			if (conversionSelected != null) {
				Messagebox
						.show("Se aplicarán cambios a la base de datos. ¿Desea continuar?",
								"Guardar Cambios", Messagebox.YES
										| Messagebox.ABORT,
								Messagebox.QUESTION,
								new EventListener<Event>() {

									public void onEvent(Event event)
											throws Exception {
										if (Messagebox.ON_YES.equals(event
												.getName())) {
											conversionSelected = GuardarConversion();
											if (conversionSelected != null) {
												listaModeloConversiones
														.addToSelection(conversionSelected);
												Messagebox
														.show("Los datos se almacenaron satisfactoriamente!.",
																"Registro Guardado",
																Messagebox.OK,
																Messagebox.INFORMATION);
											}
										} else if (Messagebox.ON_ABORT
												.equals(event.getName())) {
											listaModeloConversiones
													.clearSelection();
										}
										ClearConversionesFields();
										tlbtnGuardar.setDisabled(true);
										tlbtnEditar.setDisabled(true);
										tlbtnEliminar.setDisabled(true);
										DisableConversionesFields();
										CargaDatos();
										
										//CargaListaConversionesMedidas();
									}
								});
			}
			CargaDatos();
		}
	}

	@Listen("onClick=#tlbtnEliminar")
	public void Eliminar() {
		if (tabsUnidadesMedida.getSelectedIndex() == 0) {
			if (unidadMedidaSelected != null
					&& unidadMedidaSelected.getId_unidad_medida() != null) {
				if (unidadMedidaSelected.getId_unidad_medida() == null) {
					Messagebox
							.show("No se ha seleccionado un registro válido para eliminar. Por favor revise y vuelva a intentarlo.",
									"Registro Inválido", Messagebox.OK,
									Messagebox.ERROR);
					return;
				}

				Messagebox
						.show("Se eliminará el registro de la base de datos. ¿Desea continuar?",
								"Eliminar U/M", Messagebox.NO | Messagebox.YES,
								Messagebox.EXCLAMATION,
								new EventListener<Event>() {

									public void onEvent(Event event)
											throws Exception {
										// TODO Auto-generated method stub
										if (Messagebox.ON_YES.equals(event
												.getName())) {
											BorraUnidadMedida();
										} else {
											listaModeloUnidadMedida1
													.clearSelection();
											unidadMedidaSelected = null;
										}
										ClearUnidadMedidaFields();
										DisableUnidadMedidaFields();
										tlbtnEliminar.setDisabled(true);
										tlbtnGuardar.setDisabled(true);
										tlbtnEditar.setDisabled(true);
									}
								});
			}
		} else {
			if (conversionSelected != null
					&& listaModeloConversiones.indexOf(conversionSelected) >= 0) {
				Messagebox
						.show("El registro se eliminará de la base de datos. ¿Desea Continuar?",
								"Eliminar Registro", Messagebox.YES
										| Messagebox.ABORT,
								Messagebox.QUESTION,
								new EventListener<Event>() {

									public void onEvent(Event event)
											throws Exception {
										if (Messagebox.ON_YES.equals(event
												.getName())) {
											BorrarConversion();
										} else if (Messagebox.ON_ABORT
												.equals(event.getName())) {
											conversionSelected = null;
										}
										ClearConversionesFields();
										DisableConversionesFields();
										tlbtnEliminar.setDisabled(true);
										tlbtnGuardar.setDisabled(true);
										tlbtnEditar.setDisabled(true);
									}
								});
			} else {
				Messagebox
						.show("No se ha seleccionado un registro válido para eliminar. Por favor revise y vuelva a intentarlo.",
								"Registro Inválido", Messagebox.OK,
								Messagebox.ERROR);
				return;
			}
		}
	}
	
	private void CargaDatos(){
		listaModeloUnidadMedida1 = new ListModelList<UnidadMedida>(
				unidadMedidaService
				.getListaUnidadMedida());
		lstUnidadesMedida.setModel(listaModeloUnidadMedida1);
		lstUnidadOrigen.setModel(listaModeloUnidadMedida1);
		listaModeloUnidadMedida2 = new ListModelList<UnidadMedida>(
				unidadMedidaService
				.getListaUnidadMedida());
		lstUnidadDestino.setModel(listaModeloUnidadMedida2);
		listaModeloConversiones = new ListModelList<ConversionUnidadMedida>(
				conversionUnidadMedidaService
				.getListaConversiones());
		lstConversionesUnidades.setModel(listaModeloConversiones);
	}

	private void RefreshUnidadMedidaDetail() {
		if (unidadMedidaSelected != null
				&& unidadMedidaSelected.getId_unidad_medida() != null) {
			txtDescripcionUnidad.setValue(unidadMedidaSelected
					.getDescripcion_unidad());
			txtDescripcionCortaUnidad.setValue(unidadMedidaSelected
					.getDescripcion_corta());
			chkUnidadBase.setChecked(unidadMedidaSelected.getUnidad_base());

			DisableUnidadMedidaFields();

			tlbtnGuardar.setDisabled(true);
			tlbtnEliminar.setDisabled(false);
			tlbtnEditar.setDisabled(false);
		}
	}

	private void RefreshConversionesDetail() {
		if (conversionSelected != null
				&& conversionSelected.getIdconversion() != null) {
			bbxUnidadOrigen.setValue(conversionSelected.getUnidadorigen()
					.getDescripcion_unidad());
			bbxUnidadDestino.setValue(conversionSelected.getUnidaddestino()
					.getDescripcion_unidad());
			dcmbConversionDirecta.setValue(conversionSelected
					.getConversion_directa());
			dcmbConversionInversa.setValue(conversionSelected
					.getConversion_inversa());

			DisableConversionesFields();

			tlbtnGuardar.setDisabled(true);
			tlbtnEliminar.setDisabled(false);
			tlbtnEditar.setDisabled(false);
		}
	}

	private UnidadMedida GuardaUnidadMedida() {
		unidadMedidaSelected.setDescripcion_unidad(txtDescripcionUnidad
				.getValue());
		unidadMedidaSelected.setDescripcion_corta(txtDescripcionCortaUnidad
				.getValue());
		unidadMedidaSelected.setUnidad_base(chkUnidadBase.isChecked());
		if (unidadMedidaSelected.getId_unidad_medida() != null) {
			try {
				unidadMedidaSelected = unidadMedidaService
						.updateUnidadMedida(unidadMedidaSelected);
			} catch (Exception ex) {
				Messagebox.show("Ocurrió un error al actualizar datos de U/M. "
						+ ex.getMessage(), "Error de Datos", Messagebox.OK,
						Messagebox.ERROR);
				unidadMedidaSelected = null;
			}

		} else {
			try {
				unidadMedidaSelected = unidadMedidaService
						.saveUnidadMedida(unidadMedidaSelected);
				listaModeloUnidadMedida1.add(unidadMedidaSelected);
			} catch (Exception ex) {
				Messagebox.show("Ocurrió un error al guardar datos de U/M. "
						+ ex.getMessage(), "Error de Datos", Messagebox.OK,
						Messagebox.ERROR);
				unidadMedidaSelected = null;
			}
		}

		return unidadMedidaSelected;
	}

	private void BorraUnidadMedida() {

		try {
			unidadMedidaService.deleteUnidadMedida(listaModeloUnidadMedida1
					.remove(listaModeloUnidadMedida1
							.indexOf(unidadMedidaSelected)));
			Messagebox
					.show("El registro de tipo de U/M fué eliminado!",
							"Registro Eliminado", Messagebox.OK,
							Messagebox.INFORMATION);
		} catch (Exception ex) {
			Messagebox.show(
					"Ocurrió un error al eliminar datos de U/M. "
							+ ex.getMessage(), "Error de Datos", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	private ConversionUnidadMedida GuardarConversion() {
		

		ConversionUnidadPK pk = new ConversionUnidadPK(
				unidadMedidaSelected.getId_unidad_medida(),
				unidadDestino.getId_unidad_medida());
		conversionSelected.setIdconversion(pk);
		conversionSelected.setConversion_directa(dcmbConversionDirecta
				.getValue());
		conversionSelected.setConversion_inversa(dcmbConversionInversa
				.getValue());
		if (conversionUnidadMedidaService.getConversion(pk) != null) {
			try {
				conversionSelected = conversionUnidadMedidaService
						.updateConversion(conversionSelected);
			} catch (Exception ex) {
				Messagebox.show(
						"Ocurrió un error al actualizar datos de Conversion de Unidad. "
								+ ex.getMessage(), "Error de Actualización",
						Messagebox.OK, Messagebox.ERROR);
				conversionSelected = null;
			}

		} else {
			try {

				conversionUnidadMedidaService
						.saveConversion(conversionSelected);
				conversionSelected = conversionUnidadMedidaService
						.reloadConversion(conversionSelected);
				listaModeloConversiones.add(conversionSelected);
			} catch (Exception ex) {
				Messagebox.show(
						"Ocurrió un error al guardar datos de Conversion de Unidad. "
								+ ex.getMessage(), "Error al Guardar",
						Messagebox.OK, Messagebox.ERROR);
				conversionSelected = null;
			}
		}
		return conversionSelected;
	}

	private void BorrarConversion() {
		try {
			conversionUnidadMedidaService
					.deleteConversion(listaModeloConversiones
							.remove(listaModeloConversiones
									.indexOf(conversionSelected)));
			Messagebox
					.show("El registro de Conversión de Unidad fué eliminado!",
							"Registro Eliminado", Messagebox.OK,
							Messagebox.INFORMATION);
		} catch (Exception ex) {
			Messagebox.show(
					"Ocurrió un error al eliminar datos de Conversion de Unidad. "
							+ ex.getMessage(), "Error al Eliminar Datos",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void ClearUnidadMedidaFields() {
		txtDescripcionUnidad.setRawValue("");
		txtDescripcionCortaUnidad.setRawValue("");
		chkUnidadBase.setChecked(false);
	}

	private void DisableUnidadMedidaFields() {
		txtDescripcionUnidad.setDisabled(true);
		txtDescripcionCortaUnidad.setDisabled(true);
		chkUnidadBase.setDisabled(true);
	}

	private void EnableUnidadMedidaFields() {
		txtDescripcionUnidad.setDisabled(false);
		txtDescripcionCortaUnidad.setDisabled(false);
		chkUnidadBase.setDisabled(false);
	}

	private void ClearConversionesFields() {
		
		bbxUnidadOrigen.setRawValue("");
		bbxUnidadDestino.setRawValue("");
		dcmbConversionDirecta.setValue(new BigDecimal(0));
		dcmbConversionInversa.setValue(new BigDecimal(0));
		
	}

	private void DisableConversionesFields() {
		bbxUnidadOrigen.setDisabled(true);
		bbxUnidadDestino.setDisabled(true);
		dcmbConversionDirecta.setDisabled(true);
		dcmbConversionInversa.setDisabled(true);
	}

	private void EnableConversionesFields() {
		bbxUnidadOrigen.setDisabled(false);
		bbxUnidadDestino.setDisabled(false);
		dcmbConversionDirecta.setDisabled(false);
		dcmbConversionInversa.setDisabled(false);
	}
}
