package com.ecom.safecom.controllers;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.ecom.safecom.entity.Ejercicio;
import com.ecom.safecom.entity.Periodo;
import com.ecom.safecom.services.impl.EjercicioServiceImpl;
import com.ecom.safecom.services.impl.PeriodoServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ContaCalendarioController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	EjercicioServiceImpl ejercicioService;
	@WireVariable
	PeriodoServiceImpl periodoService;

	@Wire
	Tabbox tabsCalendario;

	@Wire
	Toolbarbutton tlbtnGuardar;
	@Wire
	Toolbarbutton tlbtnEliminar;
	@Wire
	Toolbarbutton tlbtnRecargar;
	@Wire
	Toolbarbutton tlbtnAgregar;
	@Wire
	Toolbarbutton tlbtnEditar;

	// CAMPOS DE CALENDARIO

	@Wire
	Intbox intEjercicio;
	@Wire
	Textbox txtDescripcionEjercicio;
	@Wire
	Checkbox chkEjercicioActivo;
	@Wire
	Listbox lstEjercicios;
	@Wire
	Button btnCrearPeriodos;
	@Wire
	Button btnVerPeriodos;

	@Wire
	Intbox intNumeroPeriodo;
	@Wire
	Textbox txtNombrePeriodo;
	@Wire
	Combobox cmbIdEjercicio;
	@Wire
	Datebox dtbFechaInicio;
	@Wire
	Datebox dtbFechaFinal;
	@Wire
	Checkbox chkPeriodoActivo;
	@Wire
	Checkbox chkPeriodoAbierto;
	@Wire
	Listbox lstPeriodosContables;

	private Ejercicio ejercicioSelected = null;
	private Periodo periodoSelected = null;
	ListModelList<Ejercicio> listaModeloEjercicios;
	ListModelList<Periodo> listaModeloPeriodos;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		List<Ejercicio> listaEjercicios = ejercicioService.getListaEjercicios();
		listaModeloEjercicios = new ListModelList<Ejercicio>(listaEjercicios);
		lstEjercicios.setModel(listaModeloEjercicios);
		
		List<Periodo> listaPeriodos = periodoService.getListaPeriodos();
		listaModeloPeriodos = new ListModelList<Periodo>(listaPeriodos);
		lstPeriodosContables.setModel(listaModeloPeriodos);
	}

	private void CargaDatosCalendario() {
		switch (tabsCalendario.getSelectedIndex()) {
		case 0:
			List<Ejercicio> listaEjercicios = ejercicioService
					.getListaEjercicios();
			listaModeloEjercicios = new ListModelList<Ejercicio>(
					listaEjercicios);
			lstEjercicios.setModel(listaModeloEjercicios);
			break;
		case 1:
			List<Periodo> listaPeriodos = periodoService.getListaPeriodos();
			listaModeloPeriodos = new ListModelList<Periodo>(listaPeriodos);
			lstPeriodosContables.setModel(listaModeloPeriodos);
			break;
		}
	}
	
	@Listen("onSelect=#tabsCalendario")
	public void tabsCalendario_onSelectListener(){
		CargaDatosCalendario();
	}

	@Listen("onSelect=#lstEjercicios")
	public void SeleccionaEjercicio() {
		if (!listaModeloEjercicios.isSelectionEmpty()) {
			ejercicioSelected = ejercicioService.getEjercicio(listaModeloEjercicios.getSelection().iterator()
					.next().getId_ejercicio());
			ActualizaDetalleEjercicio();
			DisableEjercicioFields();
			tlbtnEditar.setDisabled(false);
			tlbtnEliminar.setDisabled(false);
			btnVerPeriodos.setDisabled(false);
			if (ejercicioSelected.getPeriodos().size() > 0) {
				btnCrearPeriodos.setDisabled(true);
			} else {
				btnCrearPeriodos.setDisabled(false);
			}
		}
	}

	@Listen("onClick=#btnCrearPeriodos")
	public void CrearPeriodos() {
		if (ejercicioSelected != null
				&& listaModeloEjercicios.indexOf(ejercicioSelected) >= 0) {
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
			cal.set(Calendar.YEAR, ejercicioSelected.getId_ejercicio());

			for (int i = 0; i < 12; i++) {
				if (i > 0) {
					cal.add(Calendar.MONTH, 1);
				} else {
					cal.set(Calendar.MONTH, Calendar.JANUARY);
				}
				// cal.set(Calendar.MONTH, i+1);

				Periodo per = new Periodo();
				per.setId(GeneraIdPeriodo(cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH)));
				per.setNumero(i + 1);
				per.setNombre(per.getId());
				per.setEjercicio(ejercicioSelected);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				per.setFecha_inicio(cal.getTime());
				cal.set(Calendar.DATE,
						cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				per.setFecha_final(cal.getTime());
				per.setActivo(true);
				per.setAbierto(false);

				try {
					periodoService.savePeriodo(per);
				} catch (Exception ex) {
					Messagebox.show("Ocurrió un error al crear períodos. "
							+ ex.getMessage());
					return;
				}
			}
		} else {
			Messagebox
					.show("No se ha seleccionado un Ejercicio válido. Por favor revise y vuelva a intentarlo.");
		}
	}

	@Listen("onClick=#btnVerPeriodos")
	public void VerPeriodos() {
		if (ejercicioSelected != null
				&& listaModeloEjercicios.indexOf(ejercicioSelected) >= 0) {
			List<Periodo> nuevosPeriodos = ejercicioSelected.getPeriodos();
			listaModeloPeriodos = new ListModelList<Periodo>(nuevosPeriodos);
			lstPeriodosContables.setModel(listaModeloPeriodos);
			tabsCalendario.setSelectedIndex(1);
		} else {
			Messagebox
					.show("No se ha seleccionado un Ejercicio Contable. Por favor intentelo de nuevo.");
		}
	}

	@Listen("onSelect=#lstPeriodosContables")
	public void SeleccionaPeriodoContable() {
		if (!listaModeloPeriodos.isSelectionEmpty()) {
			periodoSelected = listaModeloPeriodos.getSelection().iterator()
					.next();
			ActualizaDetallePeriodo();
			DisablePeriodoFields();
			tlbtnEditar.setDisabled(false);
			tlbtnEliminar.setDisabled(false);
		}
	}

	@Listen("onClick=#tlbtnRecargar")
	public void Recargar() {
		CargaDatosCalendario();
	}

	@Listen("onClick=#tlbtnAgregar")
	public void Nuevo() {
		switch (tabsCalendario.getSelectedIndex()) {
		case 0:
			ejercicioSelected = new Ejercicio();
			ResetDatosEjercicio();
			EnableEjercicioFields();
			tlbtnGuardar.setDisabled(false);
			break;
		case 1:
			periodoSelected = new Periodo();
			ResetDatosPeriodo();
			EnablePeriodoFields();
			tlbtnGuardar.setDisabled(false);
			break;
		}
	}

	@Listen("onClick=#tlbtnEditar")
	public void Editar() {
		switch (tabsCalendario.getSelectedIndex()) {
		case 0:
			EnableEjercicioFields();
			intEjercicio.setDisabled(true);
			tlbtnGuardar.setDisabled(false);
			break;
		case 1:
			EnablePeriodoFields();
			tlbtnGuardar.setDisabled(false);
			break;
		}
	}

	@Listen("onClick=#tlbtnGuardar")
	public void Guardar() {
		switch (tabsCalendario.getSelectedIndex()) {
		case 0:
			if (ejercicioSelected != null) {
				if (listaModeloEjercicios.indexOf(ejercicioSelected) >= 0
						|| ejercicioService.getEjercicio(intEjercicio
								.getValue()) != null) {
					intEjercicio
							.setErrorMessage("El ejercicio ya existe. Por favor, verifique!");
					return;
				}

				Messagebox
						.show("Se guardarán los datos de Ejercicio Contable. ¿Desea Continuar?",
								"Guardar Ejercicio", Messagebox.YES
										| Messagebox.ABORT,
								Messagebox.QUESTION,
								new EventListener<Event>() {

									public void onEvent(Event event)
											throws Exception {
										if (Messagebox.ON_YES.equals(event
												.getName())) {
											ejercicioSelected = GuardarEjercicio();
											if (ejercicioSelected != null) {
												Messagebox
														.show("Los datos se guardaron con éxito.",
																"Datos Guardados",
																Messagebox.OK,
																Messagebox.EXCLAMATION);
												listaModeloEjercicios
														.addToSelection(ejercicioSelected);
											}
										}
										DisableEjercicioFields();
										ResetDatosEjercicio();
										tlbtnGuardar.setDisabled(true);
										tlbtnEditar.setDisabled(true);
										tlbtnEliminar.setDisabled(true);
										CargaDatosCalendario();
									}
								});
			}
			break;
		case 1:
			if (periodoSelected != null) {
				Messagebox
						.show("Se guardarán los datos de Período Contable. ¿Desea Continuar?",
								"Guardar Período", Messagebox.YES
										| Messagebox.ABORT,
								Messagebox.QUESTION,
								new EventListener<Event>() {

									public void onEvent(Event event)
											throws Exception {
										if (Messagebox.ON_YES.equals(event
												.getName())) {
											periodoSelected = GuardarPeriodo();
											if (periodoSelected != null) {
												Messagebox
														.show("Los datos se guardaron con éxito.",
																"Datos Guardados",
																Messagebox.OK,
																Messagebox.EXCLAMATION);
												listaModeloPeriodos
														.addToSelection(periodoSelected);
											}
										}
										DisablePeriodoFields();
										ResetDatosPeriodo();
										tlbtnGuardar.setDisabled(true);
										tlbtnEditar.setDisabled(true);
										tlbtnEliminar.setDisabled(true);
									}
								});
			}
			break;
		}
	}

	@Listen("onClick=#tlbtnEliminar")
	public void Eliminar() {
		switch (tabsCalendario.getSelectedIndex()) {
		case 0:
			if (ejercicioSelected != null) {
				if (listaModeloEjercicios.indexOf(ejercicioSelected) < 0
						&& ejercicioService.getEjercicio(intEjercicio
								.getValue()) == null) {
					Messagebox.show(
							"No se ha seleccionado un registro válido.",
							"Registro No Válido", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}

				Messagebox
						.show("Se va a eliminar el registro de la base de datos. ¿Desea continuar?",
								"Eliminar Registro", Messagebox.YES
										| Messagebox.ABORT,
								Messagebox.QUESTION,
								new EventListener<Event>() {

									public void onEvent(Event event)
											throws Exception {
										if (Messagebox.ON_YES.equals(event
												.getName())) {
											EliminarEjercicio();
										} else {
											ejercicioSelected = null;
										}
										ResetDatosEjercicio();
										DisableEjercicioFields();
										listaModeloEjercicios.clearSelection();
										tlbtnEliminar.setDisabled(true);
										tlbtnGuardar.setDisabled(true);
										tlbtnEditar.setDisabled(true);
									}
								});
			}
			break;
		case 1:
			if (periodoSelected != null) {
				Messagebox
						.show("Se va a eliminar el registro de la base de datos. ¿Desea continuar?",
								"Eliminar Registro", Messagebox.YES
										| Messagebox.ABORT,
								Messagebox.QUESTION,
								new EventListener<Event>() {

									public void onEvent(Event event)
											throws Exception {
										if (Messagebox.ON_YES.equals(event
												.getName())) {
											EliminarPeriodo();
										} else {
											periodoSelected = null;
										}
										ResetDatosPeriodo();
										DisablePeriodoFields();
										listaModeloPeriodos.clearSelection();
										tlbtnEliminar.setDisabled(true);
										tlbtnGuardar.setDisabled(true);
										tlbtnEditar.setDisabled(true);
									}
								});
			}
			break;
		}
	}

	private Ejercicio GuardarEjercicio() {
		Ejercicio ejercicio = null;
		ejercicioSelected.setId_ejercicio(intEjercicio.getValue());
		ejercicioSelected.setDescripcion(txtDescripcionEjercicio.getValue());
		ejercicioSelected.setActivo(chkEjercicioActivo.isChecked());
		if (listaModeloEjercicios.indexOf(ejercicio) < 0) {
			try {
				ejercicio = ejercicioService.saveEjercicio(ejercicioSelected);
				listaModeloEjercicios.add(ejercicioSelected);
			} catch (Exception ex) {
				Messagebox.show(
						"Ocurrió un error al guardar los datos. "
								+ ex.getMessage(), "Error al Guardar Datos",
						Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			try {
				ejercicio = ejercicioService.updateEjercicio(ejercicioSelected);
			} catch (Exception ex) {
				Messagebox.show("Ocurrió un error al actualizar los datos. "
						+ ex.getMessage(), "Error al Actualizar Datos",
						Messagebox.OK, Messagebox.ERROR);
			}
		}
		return ejercicio;
	}

	private void EliminarEjercicio() {
		try {
			ejercicioService.deleteEjercicio(ejercicioSelected);
			listaModeloEjercicios.remove(ejercicioSelected);
		} catch (Exception ex) {
			Messagebox.show("Error al eliminar datos de Ejercicio Contable. "
					+ ex.getMessage(), "Error al Eliminar Datos",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void ActualizaDetalleEjercicio() {
		if (ejercicioSelected != null
				&& listaModeloEjercicios.indexOf(ejercicioSelected) >= 0) {
			intEjercicio.setValue(ejercicioSelected.getId_ejercicio());
			txtDescripcionEjercicio
					.setValue(ejercicioSelected.getDescripcion());
			chkEjercicioActivo.setChecked(ejercicioSelected.getActivo());
		}
	}

	private void ResetDatosEjercicio() {
		intEjercicio.setRawValue("");
		txtDescripcionEjercicio.setRawValue("");
	}

	private void DisableEjercicioFields() {
		intEjercicio.setDisabled(true);
		txtDescripcionEjercicio.setDisabled(true);
		chkEjercicioActivo.setDisabled(true);
	}

	private void EnableEjercicioFields() {
		intEjercicio.setDisabled(false);
		txtDescripcionEjercicio.setDisabled(false);
		chkEjercicioActivo.setDisabled(false);
	}

	private void ActualizaDetallePeriodo() {
		if (periodoSelected != null) {
			intNumeroPeriodo.setValue(periodoSelected.getNumero());
			txtNombrePeriodo.setValue(periodoSelected.getNombre());
			cmbIdEjercicio.setValue(periodoSelected.getEjercicio()
					.getId_ejercicio().toString());
			dtbFechaInicio.setValue(periodoSelected.getFecha_inicio());
			dtbFechaFinal.setValue(periodoSelected.getFecha_final());
			chkPeriodoActivo.setChecked(periodoSelected.getActivo());
			chkPeriodoAbierto.setChecked(periodoSelected.getAbierto());
		}
	}

	private void DisablePeriodoFields() {
		intNumeroPeriodo.setDisabled(true);
		txtNombrePeriodo.setDisabled(true);
		cmbIdEjercicio.setDisabled(true);
		dtbFechaInicio.setDisabled(true);
		dtbFechaFinal.setDisabled(true);
		chkPeriodoActivo.setDisabled(true);
		chkPeriodoAbierto.setDisabled(true);
	}

	private void EnablePeriodoFields() {
		intNumeroPeriodo.setDisabled(false);
		txtNombrePeriodo.setDisabled(false);
		cmbIdEjercicio.setDisabled(false);
		dtbFechaInicio.setDisabled(false);
		dtbFechaFinal.setDisabled(false);
		chkPeriodoActivo.setDisabled(false);
		chkPeriodoAbierto.setDisabled(false);
	}

	private void ResetDatosPeriodo() {
		intNumeroPeriodo.setRawValue("");
		txtNombrePeriodo.setRawValue("");
		cmbIdEjercicio.setSelectedItem(null);
		dtbFechaInicio.setValue(new Date());
		dtbFechaFinal.setValue(new Date());
		chkPeriodoActivo.setChecked(false);
		chkPeriodoAbierto.setChecked(false);
	}

	private Periodo GuardarPeriodo() {
		Periodo per = null;

		periodoSelected.setNumero(intNumeroPeriodo.getValue());
		periodoSelected.setNombre(txtNombrePeriodo.getValue());
		periodoSelected.setEjercicio(ejercicioService.getEjercicio(Integer
				.parseInt(cmbIdEjercicio.getValue())));
		periodoSelected.setFecha_inicio(dtbFechaInicio.getValue());
		periodoSelected.setFecha_final(dtbFechaFinal.getValue());
		periodoSelected.setActivo(chkPeriodoActivo.isChecked());
		periodoSelected.setAbierto(chkPeriodoAbierto.isChecked());

		if (listaModeloPeriodos.indexOf(periodoSelected) < 0) {

			periodoSelected.setId(GeneraIdPeriodo(
					Integer.parseInt(cmbIdEjercicio.getValue()),
					intNumeroPeriodo.getValue()));

			try {
				per = periodoService.savePeriodo(periodoSelected);
				listaModeloPeriodos.add(periodoSelected);
			} catch (Exception e) {
				Messagebox.show(
						"Ocurrió un error al guardar los datos. "
								+ e.getMessage(), "Error al Guardar",
						Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			try {
				if(periodoSelected.getAbierto()){
					List<Periodo> periodos = periodoService.getListaPeriodosAbiertos();
					for(Periodo periodo : periodos){
						periodo.setAbierto(false);
						periodoService.updatePeriodo(periodo);
					}
				}
				per = periodoService.updatePeriodo(periodoSelected);
			} catch (Exception ex) {
				Messagebox.show("Ocurrió un error al actualizar los datos. "
						+ ex.getMessage(), "Error al Actualizar",
						Messagebox.OK, Messagebox.ERROR);
			}
		}

		return per;
	}

	private void EliminarPeriodo() {
		try {
			periodoService.deletePeriodo(periodoSelected);
			listaModeloPeriodos.remove(periodoSelected);
		} catch (Exception ex) {
			Messagebox.show(
					"Ocurrió un error al eliminar los datos. "
							+ ex.getMessage(), "Error al Eliminar Datos",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private String GeneraIdPeriodo(Integer a, Integer m) {

		String mes = Month.of(m + 1).toString().substring(0, 3);// .toString().substring(0,
																// 4);
		String _id = String.format("%s-%s", mes, a.toString());
		return _id;
	}
}
