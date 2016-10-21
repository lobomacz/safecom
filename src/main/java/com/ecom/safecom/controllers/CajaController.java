package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.Caja;
import com.ecom.safecom.entity.ConversionUnidadMedida;
import com.ecom.safecom.entity.ConversionUnidadPK;
import com.ecom.safecom.entity.CuentaContable;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContablePK;
import com.ecom.safecom.entity.DetalleSalidaMateriales;
import com.ecom.safecom.entity.DetalleSalidaMaterialesPK;
import com.ecom.safecom.entity.DetalleVenta;
import com.ecom.safecom.entity.MovimientoCaja;
import com.ecom.safecom.entity.Producto;
import com.ecom.safecom.entity.SalidaMateriales;
import com.ecom.safecom.entity.Venta;
import com.ecom.safecom.services.impl.AlmacenServiceImpl;
import com.ecom.safecom.services.impl.AsientoContableServiceImpl;
import com.ecom.safecom.services.impl.AutenticacionService;
import com.ecom.safecom.services.impl.CajaServiceImpl;
import com.ecom.safecom.services.impl.ConversionUnidadMedidaServiceImpl;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.PeriodoServiceImpl;
import com.ecom.safecom.services.impl.ProductoServiceImpl;
import com.ecom.safecom.services.impl.SalidaMaterialesServiceImpl;
import com.ecom.safecom.services.impl.UserInfoServiceImpl;
import com.ecom.safecom.services.impl.VentaServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CajaController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	VentaServiceImpl ventaService;
	@WireVariable
	AsientoContableServiceImpl asientoContableService;
	@WireVariable
	SalidaMaterialesServiceImpl salidaMaterialesService;
	@WireVariable
	AutenticacionService autenService;
	@WireVariable
	UserInfoServiceImpl userInfoServe;
	@WireVariable
	CajaServiceImpl cajaService;
	@WireVariable
	AlmacenServiceImpl almacenService;
	@WireVariable
	CuentaContableServiceImpl cuentaContableService;
	@WireVariable
	PeriodoServiceImpl periodoService;
	@WireVariable
	ConversionUnidadMedidaServiceImpl conversionUnidadMedidaService;
	@WireVariable
	ProductoServiceImpl productoService;

	ListModelList<Venta> listaModeloVentas;

	Venta ventaSelected;

	Caja cajaSelected;

	AsientoContable asientoContable;

	SalidaMateriales salidaMateriales;

	@Wire
	Window wCaja;
	@Wire
	Checkbox chkCreditos;
	@Wire
	Button btnCerrar;
	@Wire
	Listbox lstVentas;
	@Wire
	Label lblIdVenta;
	@Wire
	Textbox txtNombreCliente;
	@Wire
	Textbox txtNombreVendedor;
	@Wire
	Textbox txtComprobante;
	@Wire
	Radiogroup rgrpFormaPago;
	@Wire
	Decimalbox dcmbSubTotal;
	@Wire
	Decimalbox dcmbIva;
	@Wire
	Decimalbox dcmbTotal;
	@Wire
	Decimalbox dcmbRecibido;
	@Wire
	Decimalbox dcmbCambio;
	@Wire
	Decimalbox dcmbMontoDocumento;
	@Wire
	Radiogroup rgrpTipoVenta;
	@Wire
	Checkbox chkFactura;
	@Wire
	Checkbox chkExonerado;
	@Wire
	Textbox txtNumeroFactura;
	@Wire
	Button btnAsiento;
	@Wire
	Button btnGuardar;
	@Wire
	Button btnImprimir;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		if (Executions.getCurrent().getAttribute("cajaSelected") != null) {
			cajaSelected = (Caja) Executions.getCurrent().getAttribute(
					"cajaSelected");
		} else {
			cajaSelected = null;
		}
		asientoContable = null;
		salidaMateriales = null;

		// listaModeloVentas = new
		// ListModelList<Venta>(ventaService.listaTodo());
		// lstVentas.setModel(listaModeloVentas);
		LlenaListaVentas();
	}

	@Listen("onSetAsiento=#wCaja")
	public void wCaja_onSetAsientoListener(Event evento) {
		if (evento.getData() != null) {
			asientoContable = (AsientoContable) evento.getData();
			btnAsiento.setDisabled(true);
			btnGuardar.setDisabled(false);
		} else {
			asientoContable = null;
		}
	}

	@Listen("onSelect=#lstVentas")
	public void SelectVentasListener() {
		if (!listaModeloVentas.isSelectionEmpty()) {
			LimpiaCampos();
			ventaSelected = listaModeloVentas.getSelection().iterator().next();
			btnAsiento.setDisabled(false);
			LlenaCampos();
		}
	}

	@Listen("onCheck=#chkFactura")
	public void CheckFacturaListener() {
		if (chkFactura.isChecked()) {
			txtNumeroFactura.setDisabled(false);
		} else {
			txtNumeroFactura.setDisabled(true);
		}
	}

	@Listen("onCheck=#rgrpTipoVenta")
	public void CheckTipoVentaListener() {
		Radio opcion = rgrpTipoVenta.getSelectedItem();

		if (opcion.getValue().equals("contado")) {
			for (Radio radio : rgrpFormaPago.getItems()) {
				radio.setDisabled(false);
			}
		} else {
			for (Radio radio : rgrpFormaPago.getItems()) {
				radio.setDisabled(true);
			}
		}
	}

	@Listen("onCheck=#rgrpFormaPago")
	public void rgrpFormaPago_onCheckListener() {
		Radio opcion = rgrpFormaPago.getSelectedItem();

		if (opcion.getValue().equals("efectivo")) {
			// dcmbMontoDocumento.setDisabled(true);
			dcmbRecibido.setDisabled(false);
			dcmbCambio.setDisabled(false);
		} else {
			// dcmbMontoDocumento.setDisabled(false);
			dcmbRecibido.setDisabled(true);
			dcmbCambio.setDisabled(true);
			txtComprobante.setDisabled(false);
		}

	}

	@Listen("onChanging=#dcmbRecibido")
	public void dcmbRecibido_onChangingListener(InputEvent event) {
		try {
			if (!event.getValue().isEmpty() && ventaSelected != null) {
				BigDecimal monto = new BigDecimal(event.getValue());
				dcmbCambio.setValue(monto.subtract(ventaSelected.getTotal())
						.setScale(2, RoundingMode.DOWN));
			}
		} catch (Exception ex) {

		}
	}
	
	@Listen("onCheck=#chkExonerado")
	public void chkExonerado_onCheckListener(){
		if(chkExonerado.isChecked()){
			dcmbIva.setValue(BigDecimal.ZERO);
			dcmbTotal.setValue(dcmbSubTotal.getValue());
		}else{
			dcmbIva.setValue(ventaSelected.getIva());
			dcmbTotal.setValue(ventaSelected.getTotal());
		}
	}

	@Listen("onClick=#btnAsiento")
	public void btnAsiento_onClickListener() {

		this.asientoContable = new AsientoContable();
		this.asientoContable.setFecha(new Date());
		this.asientoContable.setDescripcion("Venta de Materiales al " + " "
				+ rgrpTipoVenta.getSelectedItem().getValue());
		this.asientoContable.setReferencia("Venta No."
				+ ventaSelected.getId_venta());
		this.asientoContable.setContabilizado(false);
		List<DetalleAsientoContable> detalleAsiento = new ArrayList<DetalleAsientoContable>();
		DetalleAsientoContable detalle = new DetalleAsientoContable();
		CuentaContable cuenta = null;
		if (rgrpTipoVenta.getSelectedItem().getValue().equals("contado")) {
			String descripcion = "Venta al contado pagado ";
			if (rgrpFormaPago.getSelectedItem().getValue().equals("efectivo")) {
				descripcion += "en efectivo.";
			} else if (rgrpFormaPago.getSelectedItem().getValue()
					.equals("cheque")) {
				descripcion += "con cheque No." + txtComprobante.getValue();
			} else {
				descripcion += "con tarjeta No. comprobante "
						+ txtComprobante.getValue();
			}
			detalle.setDescripcion(descripcion);
			detalle.setIddetalle(new DetalleAsientoContablePK(0, cajaSelected
					.getCuenta().getCuenta()));

			detalle.setTipo_movimiento("db");
			detalle.setMonto(ventaSelected.getTotal());
			detalle.setContabilizado(false);
			detalleAsiento.add(detalle);
		} else {
			cuenta = cuentaContableService.searchByName("CLIENTES");
			detalle.setIddetalle(new DetalleAsientoContablePK(0, cuenta
					.getCuenta()));
			detalle.setDescripcion(cuenta.getDescripcion());
			detalle.setTipo_movimiento("db");
			detalle.setMonto(ventaSelected.getTotal());
			detalle.setContabilizado(false);
			detalleAsiento.add(detalle);
		}

		detalle = new DetalleAsientoContable();

		if (rgrpFormaPago.getSelectedItem().getValue().equals("tarjeta")) {
			cuenta = cuentaContableService.getByName("VENTAS C/TARJETAS");
			detalle.setIddetalle(new DetalleAsientoContablePK(0, cuenta
					.getCuenta()));
			detalle.setDescripcion("Venta con tarjeta. Comprobante No. "
					+ txtComprobante.getValue());
			detalle.setTipo_movimiento("cr");
			detalle.setMonto(ventaSelected.getSubtotal());
			detalleAsiento.add(detalle);
		} else {
			cuenta = cuentaContableService.searchByName("VENTAS");
			detalle.setIddetalle(new DetalleAsientoContablePK(0, cuenta
					.getCuenta()));
			detalle.setDescripcion(cuenta.getDescripcion());
			detalle.setTipo_movimiento("cr");
			detalle.setMonto(ventaSelected.getSubtotal());
			detalleAsiento.add(detalle);
		}

		detalle = new DetalleAsientoContable();
		cuenta = cuentaContableService.searchByName("IVA");
		detalle.setIddetalle(new DetalleAsientoContablePK(0, cuenta.getCuenta()));
		detalle.setDescripcion(cuenta.getDescripcion());
		detalle.setTipo_movimiento("cr");
		detalle.setMonto(ventaSelected.getIva());
		detalle.setContabilizado(false);
		detalleAsiento.add(detalle);
		asientoContable.setDetalleAsiento(detalleAsiento);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("asiento", asientoContable);
		Window wAsiento;
		if (wCaja.getParent().query("#wAsientoContable") == null) {
			wAsiento = (Window) Executions.createComponents(
					"/paginas/asientos.zul", getSelf().getParent(), args);
			wAsiento.setClosable(true);
			wAsiento.setSizable(false);
			wAsiento.setPosition("center");
		} else {
			wAsiento = (Window) wCaja.getParent().query("#wAsientoContable");
		}

		wAsiento.doModal();

	}

	@Listen("onClick=#btnGuardar")
	public void btnGuardar_onClickListener() {

		if (ventaSelected != null) {

			Messagebox
					.show("Se guardarán los datos de la venta. Verifique que los datos son correctos.",
							"Guardar Datos", Messagebox.OK | Messagebox.ABORT,
							Messagebox.EXCLAMATION, new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (event.getName()
											.equals(Messagebox.ON_OK)) {
										// Ejecuta proceso de guardado

										MovimientoCaja movCaja = new MovimientoCaja();

										if (asientoContable != null) {

											if (rgrpTipoVenta.getSelectedItem()
													.getValue()
													.equals("contado")) {

												// MANEJAMOS EL REGISTRO DEL
												// MOVIMIENTO DE CAJA DE LA
												// COMPRA AL CONTADO

												movCaja.setFecha_hora_movimiento(new Date());
												movCaja.setCaja(cajaService
														.RecargarCaja(cajaSelected));
												movCaja.setConcepto("Venta al contado");
												movCaja.setReferencia("Venta No."
														+ ventaSelected
																.getId_venta());
												movCaja.setEmpleado(autenService
														.getUserCredential()
														.getEmpleado());
												movCaja.setTipo_movimiento("ingreso");
												movCaja.setTipo_pago(rgrpFormaPago
														.getSelectedItem()
														.getValue().toString()
														.toLowerCase());
												
												if(chkExonerado.isChecked()){
													movCaja.setMonto(dcmbTotal.getValue());
													ventaSelected.setExonerado(true);
												}else{
													movCaja.setMonto(ventaSelected
															.getTotal());
												}
												
												
												try {
													cajaService
															.GuardarMovimientoCaja(movCaja);
												} catch (Exception ex) {
													Messagebox.show(
															"Se produjo un error al registrar el movimiento de caja. "
																	+ ex.getMessage(),
															"Error al registrar movimiento.",
															Messagebox.OK,
															Messagebox.ERROR);
													LimpiaCampos();
													return;
												}
											}

											salidaMateriales = new SalidaMateriales();
											salidaMateriales
													.setFecha_salida(new Date());
											salidaMateriales
													.setAlmacen(ventaSelected
															.getAlmacen());
											String referencia = chkFactura
													.isChecked() ? txtNumeroFactura
													.getValue() : "Vta. No"
													+ ventaSelected
															.getId_venta();
											salidaMateriales
													.setReferencia(referencia);
											salidaMateriales
													.setDespachado(false);
											salidaMateriales.setUsuario(autenService
													.getUserCredential());
											salidaMateriales.setObservaciones("Venta No."
													+ ventaSelected
															.getId_venta());
											List<Venta> ventas = new ArrayList<Venta>();
											ventas.add(ventaService.recargarVenta(ventaSelected));
											salidaMateriales.setVentas(ventas);
											List<DetalleSalidaMateriales> detalleSalida = new ArrayList<DetalleSalidaMateriales>();

											for (DetalleVenta detallev : ventaService
													.recargarVenta(
															ventaSelected)
													.getDetalle()) {
												DetalleSalidaMateriales dsalida = new DetalleSalidaMateriales();
												dsalida.setIddetalle(new DetalleSalidaMaterialesPK(
														0,
														detallev.getProducto()
																.getCodigo_producto()));
												dsalida.setCantidad(detallev
														.getCantidad());
												dsalida.setUnidad_medida(detallev
														.getUnidad());
												dsalida.setEntregado(false);
												dsalida.setPrecio_unitario(detallev
														.getProducto()
														.getCosto());
												if (detallev
														.getUnidad()
														.equals(detallev
																.getProducto()
																.getUnidadMedida())) {
													dsalida.setMonto_total(dsalida
															.getCantidad()
															.multiply(
																	dsalida.getPrecio_unitario()));
												} else {
													ConversionUnidadMedida conversionDirecta = conversionUnidadMedidaService
															.getConversion(new ConversionUnidadPK(
																	detallev.getProducto()
																			.getUnidadMedida()
																			.getId_unidad_medida(),
																	dsalida.getUnidad_medida()
																			.getId_unidad_medida()));
													if (conversionDirecta != null) {
														dsalida.setMonto_total(dsalida
																.getPrecio_unitario()
																.divide(conversionDirecta
																		.getConversion_directa())
																.multiply(
																		dsalida.getCantidad()));
													} else {
														conversionDirecta = conversionUnidadMedidaService
																.getConversion(new ConversionUnidadPK(
																		dsalida.getUnidad_medida()
																				.getId_unidad_medida(),
																		detallev.getProducto()
																				.getUnidadMedida()
																				.getId_unidad_medida()));
														if (conversionDirecta != null) {
															dsalida.setMonto_total(dsalida
																	.getPrecio_unitario()
																	.multiply(
																			conversionDirecta
																					.getConversion_directa())
																	.multiply(
																			dsalida.getCantidad()));
														}
													}
												}
												detalleSalida.add(dsalida);
											}

											AsientoContable asientoSalida = SetAsientoSalidaMateriales(detalleSalida);

											try {

												salidaMateriales
														.setAsiento(asientoSalida);
												salidaMateriales = salidaMaterialesService
														.guardarSalida(salidaMateriales);
												for (DetalleSalidaMateriales detalle : detalleSalida) {
													detalle.setIddetalle(new DetalleSalidaMaterialesPK(
															salidaMateriales
																	.getId_salida(),
															detalle.getIddetalle()
																	.getCodigo_producto()));
												}
												salidaMateriales
														.setDetalle_salida(detalleSalida);
												salidaMateriales = salidaMaterialesService
														.actualizarSalida(salidaMateriales);
												// salidaMaterialesService.actualizarSalida(salidaMateriales);
											} catch (Exception ex) {
												Messagebox.show(
														"Se produjo un error al registrar la salida de materiales. "
																+ ex.getMessage(),
														"Error de Registro",
														Messagebox.OK,
														Messagebox.ERROR);
												asientoContableService
														.borrarAsiento(asientoSalida);
												LimpiaCampos();
												return;
											}

											ventaSelected
													.setAsiento(asientoContable);
											if (rgrpTipoVenta.getSelectedItem()
													.getValue()
													.equals("contado")) {
												ventaSelected
														.setCancelado(true);
												ventaSelected.setCredito(false);
											} else {
												ventaSelected
														.setCancelado(false);
												ventaSelected.setCredito(true);
											}

											if (chkFactura.isChecked()
													&& txtNumeroFactura
															.getValue()
															.length() > 0) {
												ventaSelected
														.setNumero_factura(txtNumeroFactura
																.getValue());
											}

											try {
												ventaSelected = ventaService
														.actualizarVenta(ventaSelected);
												// listaModeloVentas.set(listaModeloVentas.indexOf(ventaSelected),
												// ventaService.actualizarVenta(ventaSelected));
											} catch (Exception ex) {
												Messagebox.show(
														"Se produjo un error al actualizar datos de la venta. "
																+ ex.getMessage(),
														"Error de Actualización",
														Messagebox.OK,
														Messagebox.ERROR);
												asientoContableService
														.borrarAsiento(asientoContable);
												LimpiaCampos();
												return;
											}

											LimpiaCampos();
											LlenaListaVentas();
											btnImprimir.setDisabled(false);
										}

										// fin del bloque de guardado
									}
								}
							});

		}
	}

	@Listen("onClick=#btnActualizar")
	public void ClickActualizarListener() {
		LlenaListaVentas();
	}
	
	@Listen("onClick=#btnCerrar")
	public void btnCerrar_onClickListener(){
		Include include = (Include)Selectors.find(getSelf().getPage(), "#maininclude").iterator().next();
		include.setSrc("/paginas/caja_arqueo.zul");
		getPage().getDesktop().setBookmark("p_arqueo_caja");
	}

	private void LlenaListaVentas() {
		listaModeloVentas = new ListModelList<Venta>(ventaService.listaTodo());
		lstVentas.setModel(listaModeloVentas);
	}

	private void LlenaCampos() {
		if (ventaSelected != null) {
			lblIdVenta.setValue(ventaSelected.getId_venta().toString());
			txtNombreVendedor.setValue(ventaSelected.getEmpleado().toString());
			txtNombreCliente.setValue(ventaSelected.getCliente().getNombre());
			dcmbSubTotal.setValue(ventaSelected.getSubtotal());
			dcmbIva.setValue(ventaSelected.getIva());
			dcmbTotal.setValue(ventaSelected.getTotal());
			dcmbRecibido.setValue(BigDecimal.ZERO);
			dcmbCambio.setValue(BigDecimal.ZERO);
			txtNumeroFactura.setRawValue("");
			if (ventaService.recargarVenta(ventaSelected).getAsiento() != null) {
				this.asientoContable = ventaService
						.recargarVenta(ventaSelected).getAsiento();
				btnAsiento.setDisabled(true);
				btnGuardar.setDisabled(false);
			}
		}
	}

	private void LimpiaCampos() {
		ventaSelected = null;
		asientoContable = null;
		lblIdVenta.setValue("");
		txtNombreVendedor.setValue("Vendedor");
		txtNombreCliente.setValue("Cliente");
		dcmbSubTotal.setValue(BigDecimal.ZERO);
		dcmbIva.setValue(BigDecimal.ZERO);
		dcmbTotal.setValue(BigDecimal.ZERO);
		dcmbRecibido.setValue(BigDecimal.ZERO);
		dcmbCambio.setValue(BigDecimal.ZERO);
		txtNumeroFactura.setRawValue("");
		rgrpFormaPago.setSelectedItem(null);
		rgrpTipoVenta.setSelectedItem(null);
		for (Radio radio : rgrpFormaPago.getItems()) {
			radio.setDisabled(true);
		}
		txtComprobante.setValue("");
		txtComprobante.setDisabled(true);
		btnAsiento.setDisabled(true);
		btnGuardar.setDisabled(true);
		btnImprimir.setDisabled(true);
		txtNumeroFactura.setDisabled(true);
		chkExonerado.setChecked(false);
	}

	private AsientoContable SetAsientoSalidaMateriales(
			List<DetalleSalidaMateriales> detalleSalida) {
		AsientoContable asiento = new AsientoContable();
		asiento.setContabilizado(false);
		asiento.setFecha(new Date());
		asiento.setDescripcion("Salida de Mercadería Vendida");
		asiento.setReferencia("Vta No." + ventaSelected.getId_venta());
		asiento.setPeriodo(periodoService.getPeriodoAbierto());
		asiento.setUsuario(ventaSelected.getEmpleado().getUsuario());
		List<DetalleAsientoContable> detalleAsiento = new ArrayList<DetalleAsientoContable>();
		for (DetalleSalidaMateriales detalle : detalleSalida) {
			Producto producto = productoService.getProducto(detalle
					.getIddetalle().getCodigo_producto());
			DetalleAsientoContable detallea = new DetalleAsientoContable();
			detallea.setIddetalle(new DetalleAsientoContablePK(0, producto
					.getTipoProducto().getCuentaContable().getCuenta()));
			detallea.setDescripcion("Costo del producto - "
					+ producto.getDescripcion());
			detallea.setContabilizado(false);
			detallea.setTipo_movimiento("cr");
			detallea.setMonto(detalle.getMonto_total());
			detalleAsiento.add(detallea);
			detallea = new DetalleAsientoContable();
			detallea.setIddetalle(new DetalleAsientoContablePK(0, producto
					.getTipoProducto().getCuentaCosto().getCuenta()));
			detallea.setDescripcion("Costo de venta - "
					+ producto.getDescripcion());
			detallea.setContabilizado(false);
			detallea.setTipo_movimiento("db");
			detallea.setMonto(detalle.getMonto_total());
			detalleAsiento.add(detallea);
		}

		try {
			asiento = asientoContableService.guardarAsiento(asiento);
			for (DetalleAsientoContable detalle : detalleAsiento) {
				detalle.setIddetalle(new DetalleAsientoContablePK(asiento
						.getId_asiento(), detalle.getIddetalle().getCuenta()));
			}
			asiento.setDetalleAsiento(detalleAsiento);
			asiento = asientoContableService.actualizarAsiento(asiento);
		} catch (Exception ex) {
			Messagebox
					.show("Ocurrió un error al generar asiento de salida de materiales.",
							"Error", Messagebox.OK, Messagebox.ERROR);
			return null;
		}

		return asiento;
	}

}
