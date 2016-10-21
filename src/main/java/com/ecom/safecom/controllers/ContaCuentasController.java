package com.ecom.safecom.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.ecom.safecom.entity.CuentaContable;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ContaCuentasController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@WireVariable
	CuentaContableServiceImpl cuentaContableService;
	
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
	
	// CAMPOS DE CUENTAS CONTABLES
		@Wire
		Textbox txtCuenta;
		@Wire
		Textbox txtNombreCuenta;
		@Wire
		Textbox txtDescripcionCuenta;
		@Wire
		Combobox cmbTipoCuenta;
		@Wire
		Textbox txtCuentaPadre;
		@Wire
		Radiogroup grpGrupo;
		@Wire
		Checkbox chkCuentaResumen;
		@Wire
		Checkbox chkCuentaActiva;
		@Wire
		Listbox lstCatalogoCuentas;
		@Wire
		Fileupload fuplCatalogo;

		private CuentaContable cuentaContableSelected = null;
		ListModelList<CuentaContable> listaModeloCuentasContables;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		fuplCatalogo.addEventListener("onUpload",
				new EventListener<UploadEvent>() {

					public void onEvent(UploadEvent event) throws Exception {
						final Media media = event.getMedia();
						if (media != null) {
							List<String> formatos = new ArrayList<String>();
							formatos.add("xls");
							formatos.add("xlsx");

							if (formatos.indexOf(media.getFormat()) >= 0) {

								Messagebox
										.show("Esta operación sobreescribirá TODO el contenido del catálog de cuentas. ¿Desea Continuar?",
												"Importar Catálogo",
												Messagebox.YES
														| Messagebox.ABORT,
												Messagebox.QUESTION,
												new EventListener<Event>() {

													public void onEvent(
															Event evento)
															throws Exception {
														if (Messagebox.ON_YES.equals(evento
																.getName())) {
															boolean resultado = CargaCatalogoExcel(media);
															if (resultado) {
																Messagebox
																		.show("Los registros se guardaron con éxito!",
																				"Registros Guardados",
																				Messagebox.OK,
																				Messagebox.EXCLAMATION);
																CargaDatosCuentas();
															}
														}

													}
												});
							} else {
								Messagebox
										.show("El formato de archivo no es permitido.",
												"Error de Formato",
												Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});
		
		CargaDatosCuentas();
	}
	
	@Listen("onSelect=#lstCatalogoCuentas")
	public void SeleccionaCuentaContable() {
		if (!listaModeloCuentasContables.isSelectionEmpty()) {
			cuentaContableSelected = listaModeloCuentasContables.getSelection()
					.iterator().next();
			ActualizaDetalleCuentaContable();
		}
	}
	
	@Listen("onClick=#tlbtnAgregar")
	public void Nuevo(){
		cuentaContableSelected = new CuentaContable();
		ResetDatosCuenta();
		EnableCuentaFields();
		txtCuenta.setFocus(true);
		tlbtnGuardar.setDisabled(false);
	}
	
	@Listen("onClick=#tlbtnRecargar")
	public void Recargar(){
		CargaDatosCuentas();
	}
	
	@Listen("onClick=#tlbtnGuardar")
	public void Guardar(){
		if (cuentaContableSelected != null) {
			if (listaModeloCuentasContables.indexOf(cuentaContableSelected) < 0
					&& cuentaContableService.getCuentaContable(txtCuenta
							.getValue()) != null) {
				txtCuenta
						.setErrorMessage("La cuenta ya existe. Por favor, verifique!");
				return;
			}

			Messagebox
					.show("Se enviarán datos a la base de datos. ¿Desea continuar?",
							"Guardar Datos",
							Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION,
							new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									if (Messagebox.ON_YES.equals(event
											.getName())) {
										cuentaContableSelected = GuardarCuenta();
										if (cuentaContableSelected != null) {
											listaModeloCuentasContables
													.addToSelection(cuentaContableSelected);
											Messagebox
													.show("Los datos se guardaron con éxito!",
															"Datos Guardados",
															Messagebox.OK,
															Messagebox.EXCLAMATION);
											cuentaContableSelected = null;

										}
									} else if (Messagebox.ON_NO
											.equals(event.getName())) {
										cuentaContableSelected = null;

									}

									ResetDatosCuenta();
									DisableCuentaFields();
									tlbtnGuardar.setDisabled(true);
									tlbtnEditar.setDisabled(true);
									tlbtnEliminar.setDisabled(true);
								}
							});
		}
	}
	
	@Listen("onClick=#tlbtnEditar")
	public void Editar(){
		EnableCuentaFields();
		txtCuenta.setDisabled(true);
		tlbtnGuardar.setDisabled(false);
	}

	@Listen("onClick=#tlbtnEliminar")
	public void Eliminar(){
		if (cuentaContableSelected != null) {
			if (listaModeloCuentasContables.indexOf(cuentaContableSelected) < 0
					&& cuentaContableService.getCuentaContable(txtCuenta
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
										EliminarCuenta();
										listaModeloCuentasContables
												.remove(cuentaContableSelected);

									} else {
										cuentaContableSelected = null;
									}
									ResetDatosCuenta();
									DisableCuentaFields();
									tlbtnGuardar.setDisabled(true);
									tlbtnEliminar.setDisabled(true);
									tlbtnEditar.setDisabled(true);
								}
							});
		}
	}
	
	private void CargaDatosCuentas() {
		List<CuentaContable> listaCuentasContables = cuentaContableService
				.getListaCuentasContables();
		listaModeloCuentasContables = new ListModelList<CuentaContable>(
				listaCuentasContables);
		lstCatalogoCuentas.setModel(listaModeloCuentasContables);
		cuentaContableSelected = null;
	}
	
	private boolean CargaCatalogoExcel(Media media) throws IOException {
		boolean result = false;

		InputStream fis = media.getStreamData();
		List<CuentaContable> listaCuentasNuevas = new ArrayList<CuentaContable>();
		try {
			Workbook workbook = null;
			if (media.getFormat().equals("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else {
				workbook = new HSSFWorkbook(fis);
			}

			Sheet hoja = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = hoja.iterator();

			while (rowIterator.hasNext()) {
				CuentaContable cuentaNueva = null;
				Row row = rowIterator.next();
				// Messagebox.show(String.format("fila=%d", row.getRowNum()));
				if (row.getRowNum() != 0) {
					cuentaNueva = new CuentaContable();
					if(row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC){
						cuentaNueva.setCuenta(String.valueOf(row.getCell(0).getNumericCellValue()));
					}else{
						cuentaNueva.setCuenta(row.getCell(0).getStringCellValue());
					}
					
					cuentaNueva.setNombre(row.getCell(1).getStringCellValue()
							.toUpperCase());
					cuentaNueva.setDescripcion(row.getCell(2)
							.getStringCellValue().toUpperCase());
					cuentaNueva.setTipo(row.getCell(3).getStringCellValue()
							.toLowerCase());
					if(row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC){
						cuentaNueva.setCuenta_padre(String.valueOf(row.getCell(4).getNumericCellValue()));
					}else{
						cuentaNueva.setCuenta_padre(row.getCell(4).getStringCellValue().toUpperCase());
					}
					char resumen = row.getCell(5).getStringCellValue()
							.toUpperCase().charAt(0);
					if (resumen == 'Y') {
						cuentaNueva.setCuenta_resumen(true);
					} else {
						cuentaNueva.setCuenta_resumen(false);
					}
					cuentaNueva.setGrupo(row.getCell(6).getStringCellValue()
							.toUpperCase().charAt(0));
					cuentaNueva.setActiva(true);
					// Messagebox.show(String.format("grupo %c",cuentaNueva.getGrupo()));
					listaCuentasNuevas.add(cuentaNueva);
				}
				workbook.close();
				fis.close();
				/*
				 * Iterator<Cell> cellIterator = row.cellIterator();
				 * 
				 * while(cellIterator.hasNext()){ Cell celda =
				 * cellIterator.next();
				 * 
				 * switch(celda.getColumnIndex()){ case 0:
				 * cuentaNueva.setCuenta(celda.getStringCellValue()); break; } }
				 */
			}
			// Messagebox.show(String.format("lista=%d,filas=%d",
			// listaCuentasNuevas.size(),hoja.getLastRowNum()));
			if (listaCuentasNuevas.size() == hoja.getLastRowNum()) {
				result = cuentaContableService
						.insertCatalogoCuentas(listaCuentasNuevas);
			}

		} catch (FileNotFoundException e) {
			Messagebox.show(
					"Ha surgido una excepción de archivo no encontrado. Excepción:"
							+ e.getMessage(), "Excepción", Messagebox.OK,
					Messagebox.ERROR);
		} catch (IOException ex) {
			Messagebox.show(
					"Ha surgido una excepción de E/S. Excepción:"
							+ ex.getMessage(), "Excepción", Messagebox.OK,
					Messagebox.ERROR);
		}

		return result;
	}
	
	private void ActualizaDetalleCuentaContable() {
		if (cuentaContableSelected != null
				&& listaModeloCuentasContables.indexOf(cuentaContableSelected) >= 0) {
			DisableCuentaFields();
			tlbtnGuardar.setDisabled(true);
			tlbtnEliminar.setDisabled(false);
			tlbtnEditar.setDisabled(false);
			txtCuenta.setValue(cuentaContableSelected.getCuenta());
			txtNombreCuenta.setValue(cuentaContableSelected.getNombre());
			txtDescripcionCuenta.setValue(cuentaContableSelected
					.getDescripcion());
			for (Comboitem item : cmbTipoCuenta.getItems()) {
				if (item.getValue().toString()
						.equals(cuentaContableSelected.getTipo())) {
					cmbTipoCuenta.setSelectedItem(item);
				}
			}
			txtCuentaPadre.setValue(cuentaContableSelected.getCuenta_padre());
			for (Radio opcion : grpGrupo.getItems()) {
				if (opcion.getValue().toString().charAt(0) == cuentaContableSelected
						.getGrupo()) {
					grpGrupo.setSelectedItem(opcion);
				}
			}
			chkCuentaResumen.setChecked(cuentaContableSelected
					.getCuenta_resumen());
			chkCuentaActiva.setChecked(cuentaContableSelected.getActiva());
		}
	}
	
	private CuentaContable GuardarCuenta() {
		CuentaContable cuenta = null;
		cuentaContableSelected.setCuenta(txtCuenta.getValue());
		cuentaContableSelected.setNombre(txtNombreCuenta.getValue());
		cuentaContableSelected.setDescripcion(txtDescripcionCuenta.getValue());
		cuentaContableSelected.setTipo(cmbTipoCuenta.getSelectedItem()
				.getValue().toString());
		cuentaContableSelected.setCuenta_padre(txtCuentaPadre.getValue());
		for (Component item : grpGrupo.getChildren()) {
			Radio opcion = (Radio) item;
			if (opcion.isChecked()) {
				cuentaContableSelected.setGrupo(opcion.getValue().toString()
						.charAt(0));
			}
		}
		cuentaContableSelected.setCuenta_resumen(chkCuentaResumen.isChecked());
		cuentaContableSelected.setActiva(chkCuentaActiva.isChecked());
		if (listaModeloCuentasContables.indexOf(cuentaContableSelected) < 0) {
			try {
				cuenta = cuentaContableService
						.saveCuentaContable(cuentaContableSelected);
				listaModeloCuentasContables.add(cuentaContableSelected);
			} catch (Exception ex) {
				Messagebox
						.show("Ocurrió un error al guardar datos. "
								+ ex.getMessage(), "Error al Guardar Datos",
								Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			try {
				cuenta = cuentaContableService
						.updateCuentaContable(cuentaContableSelected);
			} catch (Exception ex) {
				Messagebox.show(
						"Ocurrió un error al actualizar datos. "
								+ ex.getMessage(), "Error al Actualizar Datos",
						Messagebox.OK, Messagebox.ERROR);
			}
		}
		return cuenta;
	}
	
	private void EliminarCuenta() {
		if (cuentaContableSelected != null
				&& listaModeloCuentasContables.indexOf(cuentaContableSelected) >= 0) {
			cuentaContableService.deleteCuentaContable(cuentaContableSelected);
		}
	}

	private void ResetDatosCuenta() {
		txtCuenta.setRawValue("");
		txtNombreCuenta.setRawValue("");
		txtDescripcionCuenta.setRawValue("");
		cmbTipoCuenta.setSelectedItem(null);
		txtCuentaPadre.setRawValue("");
		for (Component component : grpGrupo.getChildren()) {
			Radio opcion = (Radio) component;
			opcion.setChecked(false);
		}
		chkCuentaResumen.setChecked(false);
		chkCuentaActiva.setChecked(true);
		txtCuenta.setFocus(true);
	}

	private void DisableCuentaFields() {
		txtCuenta.setDisabled(true);
		txtNombreCuenta.setDisabled(true);
		txtDescripcionCuenta.setDisabled(true);
		cmbTipoCuenta.setDisabled(true);
		txtCuentaPadre.setDisabled(true);
		for (Component comp : grpGrupo.getChildren()) {
			((Radio) comp).setDisabled(true);
		}
		chkCuentaResumen.setDisabled(true);
		chkCuentaActiva.setDisabled(true);
	}

	private void EnableCuentaFields() {
		txtCuenta.setDisabled(false);
		txtNombreCuenta.setDisabled(false);
		txtDescripcionCuenta.setDisabled(false);
		cmbTipoCuenta.setDisabled(false);
		txtCuentaPadre.setDisabled(false);
		for (Component comp : grpGrupo.getChildren()) {
			((Radio) comp).setDisabled(false);
		}
		chkCuentaResumen.setDisabled(false);
		chkCuentaActiva.setDisabled(false);
	}

}
