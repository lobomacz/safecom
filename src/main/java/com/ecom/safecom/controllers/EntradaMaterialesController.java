package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.Almacen;
import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.Compra;
import com.ecom.safecom.entity.ConversionUnidadMedida;
import com.ecom.safecom.entity.ConversionUnidadPK;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContablePK;
import com.ecom.safecom.entity.DetalleEntradaMateriales;
import com.ecom.safecom.entity.DetalleEntradaMaterialesPK;
import com.ecom.safecom.entity.EntradaMateriales;
import com.ecom.safecom.entity.Inventario;
import com.ecom.safecom.entity.InventarioPK;
import com.ecom.safecom.entity.Producto;
import com.ecom.safecom.entity.UnidadMedida;
import com.ecom.safecom.services.impl.AlmacenServiceImpl;
import com.ecom.safecom.services.impl.AsientoContableServiceImpl;
import com.ecom.safecom.services.impl.AutenticacionService;
import com.ecom.safecom.services.impl.CompraServiceImpl;
import com.ecom.safecom.services.impl.ConversionUnidadMedidaServiceImpl;
import com.ecom.safecom.services.impl.EntradaMaterialesServiceImpl;
import com.ecom.safecom.services.impl.InventarioServiceImpl;
import com.ecom.safecom.services.impl.PeriodoServiceImpl;
import com.ecom.safecom.services.impl.ProductoServiceImpl;
import com.ecom.safecom.services.impl.UnidadMedidaServiceImpl;
import com.mysql.jdbc.log.Log;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EntradaMaterialesController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	// SERVICIOS INTERNOS
	@WireVariable
	private AlmacenServiceImpl almacenService;
	@WireVariable
	private ProductoServiceImpl productoService;
	@WireVariable
	private UnidadMedidaServiceImpl unidadMedidaService;
	@WireVariable
	private EntradaMaterialesServiceImpl entradaMaterialesService;
	@WireVariable
	private AsientoContableServiceImpl asientoContableService;
	@WireVariable
	private CompraServiceImpl compraService;
	@WireVariable
	private AutenticacionService autenService;
	@WireVariable
	private InventarioServiceImpl inventarioService;
	@WireVariable
	private ConversionUnidadMedidaServiceImpl conversionUnidadMedidaService;
	@WireVariable
	private PeriodoServiceImpl periodoService;

	// VARIABLES INTERNAS
	private Almacen almacen;
	private EntradaMateriales entradaMateriales;

	private DetalleEntradaMateriales detalleEntradaSelected;

	private List<Producto> listaProductos;
	private ListModelList<Producto> listaModeloProductos;

	private List<UnidadMedida> listaUnidades;
	private ListModelList<UnidadMedida> listaModeloUnidades;

	private List<DetalleEntradaMateriales> listaDetalle;
	private ListModelList<DetalleEntradaMateriales> listaModeloDetalle;

	// CAMPOS DEL FORMULARIO

	@Wire
	Window wEntradaMateriales;
	@Wire
	Toolbarbutton tlbtnGuardar;
	@Wire
	Toolbarbutton tlbtnEliminar;
	@Wire
	Toolbarbutton tlbtnEditar;
	@Wire
	Datebox dtbFechaEntrada;
	@Wire
	Datebox dtbFechaRecibido;
	@Wire
	Label lblAlmacen;
	@Wire
	Textbox txtReferencia;
	@Wire
	Textbox txtObservaciones;
	@Wire
	Textbox txtDescripcionProducto;
	@Wire
	Checkbox chkRecibido;
	@Wire
	Grid grdDetalleEntrada;
	@Wire
	Button btnAsiento;
	@Wire
	Combobox cmbUnidadMedida;
	@Wire
	Bandbox bndbCodigoProducto;
	@Wire
	Decimalbox dcmbCantidad;
	@Wire
	Decimalbox dcmbCostoUnitario;
	@Wire
	Decimalbox dcmbCostoTotal;
	@Wire
	Checkbox chkRecibirDetalle;
	@Wire
	Button btnEliminarDetalle;
	@Wire
	Button btnAgregarDetalle;
	@Wire
	Button btnGuardarDetalle;
	@Wire
	Listbox lstDetalleEntrada;
	@Wire
	Listbox lstCodigosProducto;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		this.entradaMateriales = Executions.getCurrent().hasAttribute(
				"entradaMateriales") ? (EntradaMateriales) Executions
				.getCurrent().getAttribute("entradaMateriales") : null;

		this.almacen = this.entradaMateriales.getAlmacen() != null ? this.entradaMateriales
				.getAlmacen() : null;

		lblAlmacen.setValue(this.entradaMateriales.getAlmacen().getUbicacion());

		listaProductos = productoService.getListaProductos();
		listaModeloProductos = new ListModelList<Producto>(listaProductos);
		lstCodigosProducto.setModel(listaModeloProductos);

		listaUnidades = unidadMedidaService.getListaUnidadMedida();
		listaModeloUnidades = new ListModelList<UnidadMedida>(listaUnidades);
		cmbUnidadMedida.setModel(listaModeloUnidades);

		// listaModeloDetalle = new ListModelList<DetalleEntradaMateriales>();

		if (this.entradaMateriales != null) {
			if (this.entradaMateriales.getId_entrada() != null) {
				DisableEntradaFields();
			}
			// CARGAR LOS DATOS DE LA ENTRADA RECIBIDA
			CargaDatos();
			if (this.entradaMateriales.getAsiento() != null) {
				DisableDetalleFields();
				btnAsiento.setDisabled(true);
			}
			tlbtnGuardar.setDisabled(false);
			tlbtnEditar.setDisabled(false);
			tlbtnEliminar.setDisabled(false);

		}

	}
	
	@Listen("onChanging=#txtDescripcionProducto")
	public void txtDescripcionProducto_onChangingListener(InputEvent event){
		String desc = event.getValue();
		listaModeloProductos = new ListModelList<Producto>(productoService.getListaProductos(desc));
		lstCodigosProducto.setModel(listaModeloProductos);
	}
	
	@Listen("onSelect=#lstCodigosProducto")
	public void lstCodigosProducto_onSelectListener(){
		bndbCodigoProducto.setValue(listaModeloProductos.getSelection().iterator().next().getCodigo_producto());
		bndbCodigoProducto.close();
	}

	@Listen("onSetAsiento=#wEntradaMateriales")
	public void AsientoIngresado(Event event) {

		if (event.getData() != null) {
			AsientoContable asiento = (AsientoContable) event.getData();
			entradaMateriales.setAsiento(asiento);
			try {
				entradaMateriales = entradaMaterialesService
						.actualizarEntrada(entradaMateriales);
				this.chkRecibido.setDisabled(false);

				this.btnAgregarDetalle.setDisabled(true);
				this.btnGuardarDetalle.setDisabled(true);
				this.btnEliminarDetalle.setDisabled(true);
			} catch (Exception ex) {
				Messagebox.show(
						"Se produjo un error al actualizar el asiento de la entrade de materiales."
								+ ex.getMessage(), "Error", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	@Listen("onClick=#tlbtnNuevo")
	public void NuevaEntrada() {
		entradaMateriales = new EntradaMateriales();
		entradaMateriales.setAlmacen(almacen);
		ClearFieldsEntrada();
		ClearFieldsDetalle();

		tlbtnGuardar.setDisabled(false);
		tlbtnEditar.setDisabled(true);
		tlbtnEliminar.setDisabled(true);
	}

	@Listen("onClick=#tlbtnEditar")
	public void EditarEntrada() {
		if (entradaMateriales.getAsiento() == null) {
			tlbtnGuardar.setDisabled(false);
			EnableDetalleFields();
		} else {
			Messagebox
					.show("No se puede editar la entrada. Ya ha sido generado un asiento contable.",
							"Operación Ilegal", Messagebox.ABORT,
							Messagebox.ERROR);
			return;
		}
	}

	@Listen("onClick=#tlbtnGuardar")
	public void Guardar() {
		Messagebox.show(
				"Se aplicarán cambios a la base de datos! ¿Desea Continuar?",
				"Guardar Datos", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.ON_YES == event.getName()) {

							// TODO: MEJORAR EL MECANISMO DE INGRESO DE LOS
							// DETALLES
							// DE LA ENTRADA DE MATERIALES

							try {

								for (DetalleEntradaMateriales detalle : listaModeloDetalle) {
									detalleEntradaSelected = entradaMaterialesService
											.recargarDetalle(detalle);
									if (detalleEntradaSelected != null
											&& (!detalleEntradaSelected
													.getRecibido() && detalle
													.getRecibido())) {
										AplicaExistencia(detalle);
									}
								}

								if (entradaMateriales.getId_entrada() != null) {

									if (chkRecibido.isChecked() && !dtbFechaRecibido.isDisabled()) {
										entradaMateriales.setRecibido(true);
										entradaMateriales
												.setFecha_recibido(dtbFechaRecibido
														.getValue());
										if(PendienteRecibir()){
											if(!RecibeTodo()){
												return;
											}
										}
									}
									
									entradaMateriales
											.setDetalle(listaModeloDetalle
													.getInnerList());

									entradaMateriales = entradaMaterialesService
											.actualizarEntrada(entradaMateriales);

								} else {

									entradaMateriales
											.setFecha_entrada(dtbFechaEntrada
													.getValue());
									entradaMateriales
											.setObservaciones(txtObservaciones
													.getValue());
									entradaMateriales
											.setReferencia(txtReferencia
													.getValue());
									entradaMateriales.setUsuario(autenService
											.getUserCredential());
									entradaMateriales = entradaMaterialesService
											.guardarEntrada(entradaMateriales);
									if (listaModeloDetalle.size() > 0) {
										for (DetalleEntradaMateriales detalle : listaModeloDetalle) {
											detalle.setId(new DetalleEntradaMaterialesPK(
													entradaMateriales
															.getId_entrada(),
													detalle.getId()
															.getCodigo_producto()));
										}

										entradaMateriales
												.setDetalle(listaModeloDetalle
														.getInnerList());
										entradaMateriales = entradaMaterialesService
												.actualizarEntrada(entradaMateriales);
									}

								}

								entradaMateriales = entradaMaterialesService
										.buscarEntrada(entradaMateriales
												.getId_entrada());
								CargaDatos();

								ClearFieldsDetalle();
								DisableDetalleFields();
								DisableEntradaFields();

								//tlbtnGuardar.setDisabled(true);
								tlbtnEditar.setDisabled(false);
								tlbtnEliminar.setDisabled(false);

							} catch (Exception ex) {
								Messagebox.show(
										"Ocurrió un error en la operación."
												+ ex.getMessage(), "Error",
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});

	}

	@Listen("onClick=#tlbtnEliminar")
	public void EliminarEntrada() {
		if (entradaMateriales.getAsiento() != null
				&& entradaMateriales.getAsiento().getContabilizado()) {
			Messagebox
					.show("La entrada ya ha sido contabilizada y no se puede eliminar.",
							"Operación Ilegal", Messagebox.OK, Messagebox.ERROR);
		} else {
			Messagebox.show(
					"Se eliminará el registro de entrada. ¿Desea continuar?",
					"Eliminar Entrada de Materiales", Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
					new EventListener<Event>() {

						public void onEvent(Event event) throws Exception {
							// TODO Auto-generated method stub
							if (Messagebox.ON_YES.equals(event.getName())) {
								try {
									entradaMaterialesService
											.borrarEntrada(entradaMateriales);
									Messagebox
											.show("La entrada de materiales se borró con éxito!");
									NuevaEntrada();
								} catch (Exception ex) {
									Messagebox
											.show("Ocurrió un error al intentar eliminar la entrada de materiales. "
													+ ex.getMessage());
								}
							}
						}
					});
		}
	}

	@Listen("onClick=#tlbtnRecargar")
	public void Recargar() {
		if (entradaMateriales != null
				&& entradaMateriales.getId_entrada() != null) {
			entradaMateriales = entradaMaterialesService
					.recargarEntrada(entradaMateriales);
			CargaDatos();
		}
	}

	@Listen("onCheck=#chkRecibido")
	public void RecibirEntrada(CheckEvent event) {
		if (event.isChecked()) {
			if (entradaMateriales != null
					&& entradaMateriales.getAsiento() != null) {

				entradaMateriales.setRecibido(true);
				dtbFechaRecibido.setDisabled(false);

				tlbtnGuardar.setDisabled(false);
				tlbtnEditar.setDisabled(true);
				tlbtnEliminar.setDisabled(true);

			} else {
				Messagebox
						.show("La operación no puede ser completada. No existe un asiento contable para esta entrada de materiales. Ingrese el asiento correspondiente y vuelva a intentarlo.",
								"Operación Ilegal", Messagebox.OK,
								Messagebox.EXCLAMATION);
				((Checkbox) event.getTarget()).setChecked(false);
			}
		} else {
			Messagebox.show("La entrada ya fue recibida.", "Operación Ilegal",
					Messagebox.OK, Messagebox.EXCLAMATION);
			((Checkbox) event.getTarget()).setChecked(true);
		}
	}

	@Listen("onClick=#btnAsiento")
	public void IngresaAsiento() {

		Map<String, Object> arg = new HashMap<String, Object>();

		if (entradaMateriales.getAsiento() == null) {

			AsientoContable asiento = new AsientoContable();
			asiento.setFecha(entradaMateriales.getFecha_entrada());
			asiento.setDescripcion("Entrada de materiales.");
			asiento.setContabilizado(false);
			asiento.setReferencia(entradaMateriales.getId_entrada().toString());
			asiento.setUsuario(autenService.getUserCredential());

			// TODO: AGREGAR UNA VERIFICACION DEL PERIODO ABIERTO CON RESPECTOA
			// LA FECHA DE LA ENTRADA
			// EN VEZ DE ASIGNAR EL PERIODO ABIERTO DIRECTAMENTE.
			asiento.setPeriodo(periodoService.getPeriodoAbierto());
			List<DetalleAsientoContable> detalleAsiento = new ArrayList<DetalleAsientoContable>();

			for (DetalleEntradaMateriales detalleEntrada : listaModeloDetalle) {

				DetalleAsientoContable detalle = new DetalleAsientoContable();
				detalle.setIddetalle(new DetalleAsientoContablePK(0,
						productoService
								.getProducto(
										detalleEntrada.getId()
												.getCodigo_producto())
								.getTipoProducto().getCuentaContable()
								.getCuenta()));
				if (detalleAsiento.indexOf(detalle) >= 0) {
					detalleAsiento.get(detalleAsiento.indexOf(detalle))
							.setMonto(
									detalleAsiento
											.get(detalleAsiento
													.indexOf(detalle))
											.getMonto()
											.add(detalleEntrada
													.getCosto_total()));
				} else {
					detalle.setDescripcion(productoService
							.getProducto(
									detalleEntrada.getId().getCodigo_producto())
							.getTipoProducto().getCuentaContable().getNombre());
					detalle.setTipo_movimiento("db");
					detalle.setContabilizado(false);
					detalle.setMonto(detalleEntrada.getCosto_total());
					detalleAsiento.add(detalle);
				}
			}

			asiento.setDetalleAsiento(detalleAsiento);
			arg.put("asiento", asiento);

		} else {
			arg.put("asiento", entradaMateriales.getAsiento());
		}

		Window wAsiento = (Window) Executions.createComponents(
				"/paginas/asientos.zul", wEntradaMateriales.getParent(), arg);
		wAsiento.setClosable(true);
		wAsiento.setSizable(false);
		wAsiento.setPosition("center");
		wAsiento.doModal();
	}

	@Listen("onSelect=#lstDetalleEntrada")
	public void SeleccionaDetalle() {
		if (!listaModeloDetalle.isSelectionEmpty()) {
			detalleEntradaSelected = listaModeloDetalle.getSelection()
					.iterator().next();

			ClearFieldsDetalle();

			/*
			for (Producto producto : listaModeloProductos) {
				if (producto.getCodigo_producto().equals(
						detalleEntradaSelected.getId().getCodigo_producto()))
					listaModeloProductos.addToSelection(producto);
			}
			*/
			
			bndbCodigoProducto.setValue(detalleEntradaSelected.getId().getCodigo_producto());

			for (UnidadMedida unidad : listaModeloUnidades) {
				if (unidad.equals(detalleEntradaSelected.getUnidad())) {
					listaModeloUnidades.addToSelection(unidad);
				}
			}

			dcmbCantidad.setValue(detalleEntradaSelected.getCantidad());
			dcmbCostoUnitario.setValue(detalleEntradaSelected
					.getCosto_unitario());
			dcmbCostoTotal.setValue(detalleEntradaSelected.getCosto_total());
			chkRecibirDetalle.setChecked(detalleEntradaSelected.getRecibido());

			if (!detalleEntradaSelected.getRecibido()
					&& entradaMateriales.getAsiento() == null) {

				if (!detalleEntradaSelected.getRecibido()) {
					EnableDetalleFields();
					bndbCodigoProducto.setDisabled(true);
				}

			} else {
				DisableDetalleFields();
				if (!detalleEntradaSelected.getRecibido()) {
					chkRecibirDetalle.setDisabled(false);
				}
			}
		}
	}

	@Listen("onClick=#btnAgregarDetalle")
	public void AgregarDetalle() {
		if (entradaMateriales.getAsiento() == null
				&& !entradaMateriales.getRecibido()) {
			detalleEntradaSelected = new DetalleEntradaMateriales();
			detalleEntradaSelected.setRecibido(false);
			EnableDetalleFields();
			chkRecibirDetalle.setDisabled(true);
			bndbCodigoProducto.focus();
		} else {
			Messagebox
					.show("Ya se registró un asiento contable. La entrada no admite modificaciones en este punto.",
							"Operación Ilegal", Messagebox.OK, Messagebox.ERROR);
		}

	}

	@Listen("onClick=#btnEliminarDetalle")
	public void EliminarDetalle(MouseEvent event) {
		if (detalleEntradaSelected != null) {

			if (detalleEntradaSelected.getRecibido()
					|| entradaMateriales.getRecibido()
					|| entradaMateriales.getAsiento() != null) {
				Messagebox
						.show("No se puede eliminar la línea de detalle. La entrada de materiales no acepta modificaciones en este punto.",
								"Operación Inválida", Messagebox.OK,
								Messagebox.ERROR);
				return;
			}

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
										DetalleEntradaMateriales detalle = entradaMaterialesService
												.buscarDetalle(detalleEntradaSelected
														.getId());
										try {
											if (detalle != null) {
												entradaMaterialesService
														.borrarDetalle(listaModeloDetalle
																.remove(listaModeloDetalle
																		.indexOf(detalleEntradaSelected)));
											} else {
												listaModeloDetalle
														.remove(listaModeloDetalle
																.indexOf(detalleEntradaSelected));
											}
										} catch (Exception ex) {
											Messagebox.show(
													"Ocurrió un problema al tratar de eliminar el registro. "
															+ ex.getLocalizedMessage(),
													"Error", Messagebox.OK,
													Messagebox.ERROR);
										}
										detalleEntradaSelected = null;
										ClearFieldsDetalle();
										DisableDetalleFields();
										Messagebox.show("Registro Eliminado.",
												"", Messagebox.OK,
												Messagebox.EXCLAMATION);

									}
								}
							});
		} else {
			Messagebox
					.show("No se ha seleccionado un registro para eliminar! Verifique y vuelva a intentarlo.",
							"Error!", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Listen("onCheck=#chkRecibirDetalle")
	public void RecibirDetalleEntrada(CheckEvent event) {
		if (event.isChecked()) {
			int cuentaRecibidos = 0;

			for (DetalleEntradaMateriales detalle : listaModeloDetalle) {
				if (detalle.getRecibido())
					cuentaRecibidos++;
			}

			if (++cuentaRecibidos == listaModeloDetalle.getSize()) {
				if (entradaMateriales.getAsiento() == null) {
					Messagebox
							.show("Está por recibir la última línea de detalle de una entrada sin un asiento contable. Ingrese el asiento contable y luego de clic en 'Recibido.'",
									"Operación Ilegal", Messagebox.ABORT,
									Messagebox.ERROR);
					ClearFieldsDetalle();
					DisableDetalleFields();
					return;
				}
			} else {
				detalleEntradaSelected.setRecibido(chkRecibirDetalle
						.isChecked());
				DisableDetalleFields();
			}
		}
	}

	@Listen("onChange=#dcmbCostoUnitario")
	public void CalculaTotal() {
		dcmbCostoTotal.setValue(dcmbCantidad.getValue().multiply(
				dcmbCostoUnitario.getValue()));
	}

	// TODO: DEPURAR EL PROCEDIMIENTO DE GUARDADO DE LAS LINEAS DE DETALLE DE LA
	// ENTRADA DE MATERIALES

	@Listen("onClick=#btnGuardarDetalle")
	public void GuardaDetalle() {
		if (detalleEntradaSelected != null) {

			if (detalleEntradaSelected.getId() != null) {
				if (listaModeloDetalle.indexOf(detalleEntradaSelected) >= 0
						&& !detalleEntradaSelected.getRecibido()) {

					Messagebox
							.show("El código de producto ya existe en el detalle de la entrada. No puede modificar el registro existente con los nuevos datos. Si desea modificarlo, elimine el registro existente y vuelva a ingresarlo.",
									"Código Existente!", Messagebox.OK,
									Messagebox.INFORMATION);
					ClearFieldsDetalle();
					return;
				}
			} else {
				if (listaModeloDetalle.size() > 0) {
					for (DetalleEntradaMateriales detalle : listaModeloDetalle) {
						if (detalle
								.getId()
								.getCodigo_producto()
								.equals(listaModeloProductos.getSelection()
										.iterator().next().getCodigo_producto())) {
							Messagebox
									.show("El código de producto ya existe en el detalle de la entrada. No puede modificar el registro existente con los nuevos datos. Si desea modificarlo, elimine el registro existente y vuelva a ingresarlo.",
											"Código Existente!", Messagebox.OK,
											Messagebox.INFORMATION);
							ClearFieldsDetalle();
							return;
						}
					}
				}
			}

			try {
				Integer identrada = entradaMateriales.getId_entrada();
				

				detalleEntradaSelected.setUnidad(listaModeloUnidades
						.getSelection().iterator().next());
				detalleEntradaSelected.setCantidad(dcmbCantidad.getValue());
				detalleEntradaSelected.setCosto_unitario(dcmbCostoUnitario
						.getValue());
				detalleEntradaSelected
						.setCosto_total(dcmbCostoTotal.getValue());
				detalleEntradaSelected.setRecibido(chkRecibirDetalle
						.isChecked());

				if (listaModeloDetalle.indexOf(detalleEntradaSelected) >= 0) {
					listaModeloDetalle.set(
							listaModeloDetalle.indexOf(detalleEntradaSelected),
							detalleEntradaSelected);
				} else {
					detalleEntradaSelected
							.setId(new DetalleEntradaMaterialesPK(identrada,
									listaModeloProductos.getSelection()
											.iterator().next()
											.getCodigo_producto()));
					listaModeloDetalle.add(detalleEntradaSelected);
				}

				ClearFieldsDetalle();
				DisableDetalleFields();

			} catch (Exception e) {
				Messagebox.show(
						"Se ha producido un error al ingresar la línea de detalle. Causa: "
								+ e.getCause() + " Mensaje: " + e.getMessage()
								+ " Error: " + e.toString(), "Error!",
						Messagebox.OK, Messagebox.ERROR);
			}
		}

	}

	private void CargaDatos() {

		// intbIdCompra.setValue(entrada.getCompra() != null ?
		// entrada.getCompra()
		// .getId_compra() : null);
		dtbFechaEntrada
				.setValue(entradaMateriales.getFecha_entrada() != null ? entradaMateriales
						.getFecha_entrada() : new Date());
		if (entradaMateriales.getObservaciones() != null) {
			txtObservaciones.setValue(entradaMateriales.getObservaciones());
		}

		if (entradaMateriales.getReferencia() != null) {
			txtReferencia.setValue(entradaMateriales.getReferencia());
		}

		chkRecibido.setChecked(entradaMateriales.getRecibido());

		if (this.entradaMateriales.getDetalle() != null) {
			listaDetalle = entradaMateriales.getDetalle();
			listaModeloDetalle = new ListModelList<DetalleEntradaMateriales>(
					listaDetalle);
		} else {
			this.listaModeloDetalle = new ListModelList<DetalleEntradaMateriales>();
		}

		lstDetalleEntrada.setModel(listaModeloDetalle);

	}

	private Boolean PendienteRecibir() {

		for (DetalleEntradaMateriales detalle : entradaMaterialesService
				.recargarEntrada(entradaMateriales).getDetalle()) {
			if (!detalle.getRecibido()) {
				return true;
			}
		}

		return false;
	}

	private Boolean RecibeTodo() {
		Boolean recibido = true;
		for (DetalleEntradaMateriales detalle : listaModeloDetalle) {
			if (!detalle.getRecibido()) {
				try {
					detalle.setRecibido(true);
					detalle = entradaMaterialesService
							.actualizarDetalle(detalle);
					AplicaExistencia(detalle);
				} catch (Exception ex) {
					recibido = false;
					Messagebox.show(
							"Error al actualizar detalle. "
									+ ex.getLocalizedMessage(), "Error",
							Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
		return recibido;
	}

	private void AplicaExistencia(DetalleEntradaMateriales detalle) {
		Producto producto = productoService.getProducto(detalle.getId()
				.getCodigo_producto());
		Inventario inventario = inventarioService
				.buscarInventario(new InventarioPK(entradaMateriales
						.getAlmacen().getId_almacen(), producto
						.getCodigo_producto()));
		BigDecimal cantidad = new BigDecimal(0);
		BigDecimal costo = new BigDecimal(0);

		if (!producto.getUnidadMedida().equals(detalle.getUnidad())) {
			ConversionUnidadMedida conversion = conversionUnidadMedidaService
					.getConversion(new ConversionUnidadPK(detalle.getUnidad()
							.getId_unidad_medida(), producto.getUnidadMedida()
							.getId_unidad_medida()));
			// SI OBTENEMOS NULL QUIERE DECIR QUE EL DETALLE ES DE UNA UNIDAD
			// MENOR
			// QUE LA UNIDAD BASE DEL PRODUCTO Y SE TENDRA QUE MULTIPLICAR POR
			// LA CONVERSION DIRECTA
			// CASO CONTRARIO SE MULTIPLICA POR EL RATIO DE CONVERSION INVERSA.
			if (conversion == null) {
				// MULTIPLICAR POR RATIO DE CONVERSION DIRECTA
				conversion = conversionUnidadMedidaService
						.getConversion(new ConversionUnidadPK(producto
								.getUnidadMedida().getId_unidad_medida(),
								detalle.getUnidad().getId_unidad_medida()));
				if (conversion == null) {
					Messagebox.show(
							"No se ha definido una conversion entre la unidad base del producto "
									+ detalle.getId().getCodigo_producto()
									+ " y la unidad de entrada.",
							"Sin Conversion de Unidades", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				cantidad = detalle.getCantidad().multiply(
						conversion.getConversion_inversa());
			} else {
				cantidad = detalle.getCantidad().multiply(
						conversion.getConversion_directa());
			}

		} else {
			cantidad = detalle.getCantidad();
		}

		costo = detalle.getCosto_total().divide(cantidad);

		if (inventario == null) {
			inventario = new Inventario();
			inventario.setId_inventario(new InventarioPK(entradaMateriales
					.getAlmacen().getId_almacen(), producto
					.getCodigo_producto()));

			inventario.setCantidad(cantidad);
			inventario.setUnidadMedida(producto.getUnidadMedida());
			try {
				inventarioService.guardarInventario(inventario);
			} catch (Exception ex) {
				Messagebox.show("Se produjo un error al ingresar inventario. "
						+ ex.getMessage());
			}
		} else {
			inventario.setCantidad(inventario.getCantidad().add(cantidad));
			try {
				inventarioService.actualizarInventario(inventario);
			} catch (Exception ex) {
				Messagebox
						.show("Se produjo un error al actualizar inventario. "
								+ ex.getMessage());
			}
		}

		// SE PROCEDE A ACTUALIZAR EL COSTO DEL PRODUCTO PARA LOS INFORMES DE
		// INVENTARIO
		try {
			producto.setCosto(costo);
			productoService.updateProducto(producto);
		} catch (Exception ex) {
			Messagebox
					.show("Se presentó un error al actualizar el costo del producto "
							+ producto.getCodigo_producto()
							+ " "
							+ ex.getMessage());
		}
	}

	private void ClearFieldsDetalle() {
		listaModeloDetalle.clearSelection();
		listaModeloProductos.clearSelection();
		listaModeloUnidades.clearSelection();
		dcmbCantidad.setValue("0");
		dcmbCostoUnitario.setValue("0");
		dcmbCostoTotal.setValue("0");
		chkRecibirDetalle.setChecked(false);
	}

	private void ClearFieldsEntrada() {
		txtReferencia.setRawValue("");
		txtObservaciones.setRawValue("");
		dtbFechaEntrada.setValue(new Date());
		chkRecibido.setChecked(false);
		dtbFechaRecibido.setValue(null);
		dtbFechaRecibido.setDisabled(true);
	}

	private void EnableEntradaFields() {

		txtReferencia.setDisabled(false);
		txtObservaciones.setDisabled(false);
		dtbFechaEntrada.setDisabled(false);
		if (!entradaMateriales.getRecibido()) {
			chkRecibido.setDisabled(false);
		}

	}

	private void DisableEntradaFields() {
		txtReferencia.setDisabled(true);
		txtObservaciones.setDisabled(true);
		dtbFechaEntrada.setDisabled(true);
		if (entradaMateriales.getRecibido()) {
			chkRecibido.setDisabled(true);
		}

	}

	private void EnableDetalleFields() {
		bndbCodigoProducto.setDisabled(false);
		cmbUnidadMedida.setDisabled(false);
		dcmbCantidad.setDisabled(false);
		dcmbCostoUnitario.setDisabled(false);
		dcmbCostoTotal.setDisabled(false);
		chkRecibirDetalle.setDisabled(false);
	}

	private void DisableDetalleFields() {
		bndbCodigoProducto.setDisabled(true);
		cmbUnidadMedida.setDisabled(true);
		dcmbCantidad.setDisabled(true);
		dcmbCostoUnitario.setDisabled(true);
		dcmbCostoTotal.setDisabled(true);
		chkRecibirDetalle.setDisabled(true);
	}
}
