package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.Almacen;
import com.ecom.safecom.entity.ConversionUnidadMedida;
import com.ecom.safecom.entity.ConversionUnidadPK;
import com.ecom.safecom.entity.DetalleSalidaMateriales;
import com.ecom.safecom.entity.DetalleVenta;
import com.ecom.safecom.entity.DetalleVentaPK;
import com.ecom.safecom.entity.Inventario;
import com.ecom.safecom.entity.InventarioPK;
import com.ecom.safecom.entity.PrecioProducto;
import com.ecom.safecom.entity.Producto;
import com.ecom.safecom.entity.SalidaMateriales;
import com.ecom.safecom.entity.Tercero;
import com.ecom.safecom.entity.UnidadMedida;
import com.ecom.safecom.entity.Venta;
import com.ecom.safecom.services.impl.AlmacenServiceImpl;
import com.ecom.safecom.services.impl.ConversionUnidadMedidaServiceImpl;
import com.ecom.safecom.services.impl.InventarioServiceImpl;
import com.ecom.safecom.services.impl.PrecioProductoServiceImpl;
import com.ecom.safecom.services.impl.ProductoServiceImpl;
import com.ecom.safecom.services.impl.SalidaMaterialesServiceImpl;
import com.ecom.safecom.services.impl.TerceroServiceImpl;
import com.ecom.safecom.services.impl.UnidadMedidaServiceImpl;
import com.ecom.safecom.services.impl.VentaServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VentasNuevoController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	ProductoServiceImpl productoService;

	@WireVariable
	PrecioProductoServiceImpl precioProductoService;

	@WireVariable
	TerceroServiceImpl terceroService;

	@WireVariable
	UnidadMedidaServiceImpl unidadMedidaService;

	@WireVariable
	VentaServiceImpl ventaService;

	@WireVariable
	ConversionUnidadMedidaServiceImpl conversionUnidadMedidaService;

	@WireVariable
	SalidaMaterialesServiceImpl salidaMaterialesService;

	@WireVariable
	AlmacenServiceImpl almacenService;

	@WireVariable
	InventarioServiceImpl inventarioService;

	Venta venta;

	Almacen almacen;

	DetalleVenta detalleSelected;

	Producto productoSelected;

	UnidadMedida unidadSelected;

	PrecioProducto precioSelected;

	Tercero clienteSelected;

	BigDecimal Total;

	BigDecimal Iva;

	SalidaMateriales salida;

	DetalleSalidaMateriales detalleSalida;

	ListModelList<Producto> listaModeloProductos;

	ListModelList<PrecioProducto> listaModeloPrecios;

	ListModelList<DetalleVenta> listaModeloDetalleVenta;

	ListModelList<Tercero> listaModeloClientes;

	ListModelList<UnidadMedida> listaModeloUnidadMedida;

	@Wire
	Window wVentas;
	@Wire
	Bandbox bdbCliente;
	@Wire
	Listbox lstClientes;
	@Wire
	Textbox txtObservaciones;
	@Wire
	Checkbox chkEntregado;
	@Wire
	Checkbox chkAnulado;
	@Wire
	Label lblTotal;
	@Wire
	Label lblSubTotal;
	@Wire
	Label lblIva;
	@Wire
	Bandbox bdbArticulo;
	@Wire
	Textbox txtBuscaCodArticulo;
	@Wire
	Textbox txtBuscaDescArticulo;
	@Wire
	Listbox lstProductos;
	@Wire
	Combobox cmbUnidadMedida;
	@Wire
	Label lblAlmacen;
	@Wire
	Decimalbox dcmbExistencia;
	@Wire
	Decimalbox dcmbCantidad;
	@Wire
	Listbox lstPrecios;
	@Wire
	Listbox lstDetalleVenta;
	@Wire
	Button btnAgregarArticulo;
	@Wire
	Button btnEntregarArticulo;
	@Wire
	Button btnRemoverArticulo;
	@Wire
	Button btnGuardar;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

		// Se recupera la venta guardada en la ejecución enviada desde la
		// pantalla ventas o proforma
		venta = (Venta) Executions.getCurrent().getAttribute("ventaSelected");

		if (venta == null) {

			Messagebox.show("No se encontraron datos de venta.");
			return;

		}

		Total = new BigDecimal(0);
		Iva = new BigDecimal(0);
		Total.setScale(2, RoundingMode.UP);
		Iva.setScale(2, RoundingMode.UP);
		
		listaModeloProductos = new ListModelList<Producto>(
				productoService.getListaProductos());
		lstProductos.setModel(listaModeloProductos);

		lblAlmacen.setValue(venta.getAlmacen().getUbicacion());

		listaModeloClientes = new ListModelList<Tercero>(
				terceroService.listaTipo("cliente"));
		lstClientes.setModel(listaModeloClientes);

		listaModeloUnidadMedida = new ListModelList<UnidadMedida>(
				unidadMedidaService.getListaUnidadMedida());
		cmbUnidadMedida.setModel(listaModeloUnidadMedida);

		// Se comprueba si la venta es nueva o se va a editar
		// Si se va a editar, se cargan los datos de la venta.
		if (venta.getId_venta() != null) {

			wVentas.setTitle("Editar");
			DeshabilitaCampos();
			
			listaModeloClientes.addToSelection(venta.getCliente());
			bdbCliente.setValue(venta.getCliente().getNombre());
			listaModeloDetalleVenta = new ListModelList<DetalleVenta>(
					ventaService.buscarVenta(venta.getId_venta()).getDetalle());
			lstDetalleVenta.setModel(listaModeloDetalleVenta);

			if (listaModeloDetalleVenta.size() > 0) {
				CalculaTotal();
				lblTotal.setValue(String.format("%.2f",
						(Total.add(Iva)).floatValue()));
				MuestraEntregados();
				HabilitaDetalle();
				if (!(venta.getCancelado() || venta.getCredito())) {
					HabilitaCamposDetalle();
				} else {
					salida = venta.getSalidas().iterator().next();
				}
			}
		} else {
			listaModeloDetalleVenta = new ListModelList<DetalleVenta>();
			lstDetalleVenta.setModel(listaModeloDetalleVenta);
			wVentas.setTitle("Ingresar");
			// HabilitaCampos();
		}

		Caption caption = (Caption) wVentas.query("caption#captVentas");

		if (venta.getProforma()) {
			caption.setLabel("Proformas");
		} else {
			caption.setLabel("Ventas");
		}

		
	}

	@Listen("onAddCliente=#wVentas")
	public void AddCliente(Event event) {
		if (event.getData() != null) {
			Tercero cliente = (Tercero) event.getData();
			listaModeloClientes.add(cliente);
		}
	}

	@Listen("onSelect=#lstClientes")
	public void SeleccionaCliente() {

		if (!listaModeloClientes.isSelectionEmpty()) {
			clienteSelected = listaModeloClientes.getSelection().iterator()
					.next();
			bdbCliente.setValue(clienteSelected.getNombre());
			bdbCliente.close();
		}

	}

	@Listen("onSelect=#lstProductos")
	public void SeleccionaProducto() {
		if (!listaModeloProductos.isSelectionEmpty()) {
			Inventario inventario = inventarioService.buscarInventario(new InventarioPK(almacenService.buscaPrincipal().getId_almacen(), listaModeloProductos.getSelection().iterator().next().getCodigo_producto()));
			
			if(inventario == null){
				Messagebox.show("No existe inventario de el producto seleccionado.", "Error de Existencia", Messagebox.OK, Messagebox.ERROR);
				listaModeloProductos.clearSelection();
				bdbArticulo.setValue("");
				bdbArticulo.close();
				DeshabilitaCamposDetalle();
				return;
			}
			
			productoSelected = productoService.getProducto(listaModeloProductos
					.getSelection().iterator().next().getCodigo_producto());
			bdbArticulo.setValue(productoSelected.getDescripcion());
			bdbArticulo.close();
			listaModeloPrecios = new ListModelList<PrecioProducto>(
					productoSelected.getListaPrecios());
			lstPrecios.setModel(listaModeloPrecios);
		}
	}
	
	@Listen("onSelect=#cmbUnidadMedida")
	public void cmbUnidadMedida_onSelectListener(){
		Inventario inventario = inventarioService.buscarInventario(new InventarioPK(almacenService.buscaPrincipal().getId_almacen(), listaModeloProductos.getSelection().iterator().next().getCodigo_producto()));
		
		if(listaModeloUnidadMedida.getSelection().iterator().next().equals(inventario.getUnidadMedida())){
			dcmbExistencia.setValue(inventario.getCantidad());
		}else{
			ConversionUnidadMedida conversion = conversionUnidadMedidaService.getConversion(new ConversionUnidadPK(inventario.getUnidadMedida().getId_unidad_medida(), listaModeloUnidadMedida.getSelection().iterator().next().getId_unidad_medida()));
			if(conversion != null){
				dcmbExistencia.setValue(inventario.getCantidad().multiply(conversion.getConversion_directa()));
			}else{
				conversion = conversionUnidadMedidaService.getConversion(new ConversionUnidadPK(listaModeloUnidadMedida.getSelection().iterator().next().getId_unidad_medida(), inventario.getUnidadMedida().getId_unidad_medida()));
				if(conversion != null){
					dcmbExistencia.setValue(inventario.getCantidad().divide(conversion.getConversion_directa()));
				}
			}
		}
	}
	
	@Listen("onChange=#dcmbCantidad")
	public void dcmbCantidad_onChangeListener(){
		Inventario inventario = inventarioService.buscarInventario(new InventarioPK(almacenService.buscaPrincipal().getId_almacen(), listaModeloProductos.getSelection().iterator().next().getCodigo_producto()));
		if(listaModeloUnidadMedida.getSelection().iterator().next().equals(inventario.getUnidadMedida())){
			if(dcmbCantidad.getValue().compareTo(inventario.getCantidad())==1){
				Messagebox.show("La existencia de este producto es menor a la cantidad ingresada. Rectifique la cantidad del producto a vender.", "Error de Cantidad", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	@Listen("onSelect=#lstPrecios")
	public void SeleccionaPrecio() {
		if (!listaModeloPrecios.isSelectionEmpty()) {
			precioSelected = listaModeloPrecios.getSelection().iterator()
					.next();
		}
	}

	@Listen("onSelect=#lstDetalleVenta")
	public void SeleccionaDetalleVenta() {
		if (!listaModeloDetalleVenta.isSelectionEmpty()) {
			detalleSelected = listaModeloDetalleVenta.getSelection().iterator()
					.next();
			if ((!venta.getCancelado() || venta.getCredito())
					&& !detalleSelected.getEntregado()) {
				btnRemoverArticulo.setDisabled(false);
				btnEntregarArticulo.setDisabled(false);
			} else {
				btnRemoverArticulo.setDisabled(true);
				btnEntregarArticulo.setDisabled(true);
			}
		}
	}

	@Listen("onClick=#btnGuardar")
	public void GuardarVenta() {
		if (venta.getId_venta() == null) {
			if (!listaModeloClientes.isSelectionEmpty()) {
				clienteSelected = listaModeloClientes.getSelection().iterator()
						.next();
				venta.setCliente(clienteSelected);
				venta.setObservaciones(txtObservaciones.getValue());
				venta.setTotal(Total.add(Iva).setScale(2, RoundingMode.UP));
				venta.setIva(Iva);
				venta.setSubtotal(Total);

				try {
					venta = ventaService.guardarVenta(venta);
					DeshabilitaCampos();
					if(listaModeloDetalleVenta.size()>0){
						for(DetalleVenta detalle : listaModeloDetalleVenta){
							detalle.setIddetalle(new DetalleVentaPK(venta.getId_venta(), detalle.getProducto().getCodigo_producto()));
						}
						venta.setDetalle(listaModeloDetalleVenta.getInnerList());
						venta = ventaService.actualizarVenta(venta);
					}
				} catch (Exception ex) {
					Messagebox.show("No se guardaron los datos de la venta. "
							+ ex.getMessage(), "Error", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			} else {
				Messagebox
						.show("No se ha ingresado el cliente. Revise y vuelva a intentarlo.",
								"Error", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} else {
			try {
				venta.setDetalle(listaModeloDetalleVenta.getInnerList());
				/*CalculaTotal();
				venta.setTotal(Total.add(Iva));
				venta.setIva(Iva);
				venta.setSubtotal(Total);*/
				venta = ventaService.actualizarVenta(venta);
				//lblTotal.setValue(String.format("%.2f", Total.add(Iva)
				//		.floatValue()));
			} catch (Exception ex) {
				Messagebox.show("No se actualizaron los datos de la venta. "
						+ ex.getMessage(), "Error", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		/*
		 * Include include = (Include) Selectors.iterable(wVentas.getPage(),
		 * "#maininclude"); String pagina = venta.getProforma() ?
		 * "/paginas/ventas_proformas.zul" : "/paginas/ventas_ventas.zul";
		 * include.setSrc(pagina); getPage().getDesktop().setBookmark( "p_" +
		 * pagina.substring(pagina.indexOf("_"), pagina.indexOf(".")));
		 */
		
	}

	@Listen("onClick=#lnkNuevoCliente")
	public void NuevoCliente() {
		Map<String, Object> arg = new HashMap<String, Object>();
		arg.put("tipo", "cliente");
		Window wCliente;
		if (wVentas.getParent().query("#wclientes") == null) {
			wCliente = (Window) Executions.createComponents(
					"/paginas/clientes_nuevo.zul", wVentas.getParent(), arg);
			wCliente.setClosable(true);
			wCliente.setSizable(false);
			wCliente.setPosition("center");
		} else {
			wCliente = (Window) wVentas.getParent().query("#wclientes");
		}

		wCliente.doModal();
	}

	@Listen("onClick=#btnAgregarArticulo")
	public void AgregarArticulo() {
		Integer _id = venta.getId_venta() != null ? venta.getId_venta() : 0;
		if (venta != null && !(venta.getCancelado() || venta.getCredito())) {
			if (productoSelected == null
					|| precioSelected == null
					|| listaModeloUnidadMedida.isSelectionEmpty()
					|| dcmbCantidad.getValue() == null
					|| dcmbCantidad.getValue().compareTo(new BigDecimal(0)) <= 0) {
				Messagebox
						.show("No se han seleccionado todos los datos para agregar.");
				return;
			}
			detalleSelected = new DetalleVenta();
			detalleSelected.setIddetalle(new DetalleVentaPK(_id,
					productoSelected.getCodigo_producto()));
			detalleSelected.setProducto(productoService.getProducto(productoSelected.getCodigo_producto()));
			detalleSelected.setCantidad(dcmbCantidad.getValue());
			detalleSelected.setUnidad(listaModeloUnidadMedida.getSelection()
					.iterator().next());

			// CALCULAR EL MONTO TOTAL DE ACUERDO A LA UNIDAD DE MEDIDA
			// SELECCIONADA
			// Y LA UNIDAD DE MEDIDA DEL PRECIO SELECCIONADO

			// SI LA UNIDAD DE VENTA ES IGUAL A LA UNIDAD DEL PRECIO
			// SELECCIONADO
			// EL CÁLCULO DEL MONTO TOTAL ES DIRECTO

			if (detalleSelected.getUnidad().equals(precioSelected.getUnidad())) {
				detalleSelected.setPrecio_unitario(precioSelected.getPrecio());
			} else {
				ConversionUnidadMedida conversion = conversionUnidadMedidaService
						.getConversion(new ConversionUnidadPK(precioSelected
								.getUnidad().getId_unidad_medida(),
								detalleSelected.getUnidad()
										.getId_unidad_medida()));

				if (conversion != null) {
					detalleSelected.setPrecio_unitario(precioSelected
							.getPrecio()
							.divide(conversion.getConversion_directa())
							.setScale(2, RoundingMode.UP));
				} else {
					conversion = conversionUnidadMedidaService
							.getConversion(new ConversionUnidadPK(
									detalleSelected.getUnidad()
											.getId_unidad_medida(),
									precioSelected.getUnidad()
											.getId_unidad_medida()));
					if (conversion != null) {
						detalleSelected.setPrecio_unitario(precioSelected
								.getPrecio()
								.multiply(conversion.getConversion_directa())
								.setScale(2, RoundingMode.UP));
					} else {
						Messagebox
								.show("No existe una relación entre el precio seleccionado y la unidad seleccionada.",
										"Error", Messagebox.OK,
										Messagebox.ERROR);
						detalleSelected = null;
						return;
					}
				}
			}

			/*
			 * if (detalleSelected.getUnidad().equals(
			 * listaModeloProductos.getSelection().iterator().next()
			 * .getUnidadMedida())) {
			 * detalleSelected.setPrecio_unitario(precioSelected.getPrecio());
			 * detalleSelected.setMonto_total(precioSelected.getPrecio()
			 * .multiply(dcmbCantidad.getValue())); } else { // Obtenemos la
			 * conversión de la unidad base del producto a una // unidad menor
			 * ConversionUnidadMedida conversion = conversionUnidadMedidaService
			 * .getConversion(new ConversionUnidadPK(productoSelected
			 * .getUnidadMedida().getId_unidad_medida(),
			 * listaModeloUnidadMedida.getSelection() .iterator().next()
			 * .getId_unidad_medida())); if (conversion != null) { // Si la
			 * unidad seleccionada es menor que la unidad base, // dividimos el
			 * precio unitario entre la conversión directa // de // las
			 * unidades. BigDecimal precio = precioSelected.getPrecio().divide(
			 * conversion.getConversion_directa());
			 * detalleSelected.setPrecio_unitario(precio);
			 * detalleSelected.setMonto_total(detalleSelected
			 * .getPrecio_unitario().multiply( dcmbCantidad.getValue())); } else
			 * { // Si la conversión no existe, se intenta una conversión //
			 * entre // la unidad seleccionada y la unidad base del producto.
			 * conversion = conversionUnidadMedidaService .getConversion(new
			 * ConversionUnidadPK( listaModeloUnidadMedida.getSelection()
			 * .iterator().next() .getId_unidad_medida(),
			 * productoSelected.getUnidadMedida() .getId_unidad_medida())); if
			 * (conversion == null) { // Si aún no se encuentra una conversión,
			 * no se puede // proceder // Se muestra un mensaje de error y se
			 * asigna el detalle // a // null // y se aborta la acción.
			 * Messagebox .show(
			 * "No existe una conversión entre la unidad de medida seleccionada y la unidad base del producto."
			 * , "Error de conversion", Messagebox.OK, Messagebox.ERROR);
			 * detalleSelected = null; return; } // Si existe la conversión,
			 * significa que la unidad // seleccionada // es mayor que la unidad
			 * base. // El precio se multiplica por la conversión directa de las
			 * // unidades. BigDecimal precio =
			 * precioSelected.getPrecio().multiply(
			 * conversion.getConversion_directa());
			 * detalleSelected.setPrecio_unitario(precio);
			 * detalleSelected.setMonto_total(detalleSelected
			 * .getPrecio_unitario().multiply( dcmbCantidad.getValue())); } }
			 */

			detalleSelected.setMonto_total(detalleSelected.getPrecio_unitario()
					.multiply(dcmbCantidad.getValue()).setScale(2, RoundingMode.UP));

			detalleSelected.setEntregado(false);

			listaModeloDetalleVenta.add(detalleSelected);
			//listaModeloDetalleVenta.addToSelection(detalleSelected);
			CalculaTotal();
			

			/*
			 * try { listaModeloDetalleVenta.add(ventaService
			 * .guardarDetalle(detalleSelected));
			 * listaModeloDetalleVenta.addToSelection(detalleSelected);
			 * CalculaTotal(); lblSubtotal.setValue(Total.toString());
			 * lblIva.setValue(Iva.toString());
			 * lblTotal.setValue(Total.add(Iva).setScale(2,
			 * RoundingMode.UP).toString());
			 * //lblTotal.setValue(String.format("%.2f", //
			 * (Total.add(Iva)).floatValue())); } catch (Exception ex) {
			 * Messagebox.show(
			 * "Ocurrió un error al ingresar el detalle de venta. " +
			 * ex.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR); }
			 * finally { detalleSelected = null; }
			 */
			
		}
	}

	@Listen("onClick=#btnEntregarArticulo")
	public void EntregarArticulo() {
		if (detalleSelected != null
				&& ventaService.buscarDetalle(detalleSelected.getIddetalle()) != null) {
			if ((venta.getCancelado() || venta.getCredito())
					&& !detalleSelected.getEntregado()) {

				Messagebox.show("Verifique que el detalle a entregar es: "
						+ detalleSelected.getCantidad() + " "
						+ detalleSelected.getUnidad().getDescripcion_corta()
						+ " de "
						+ detalleSelected.getProducto().getDescripcion(),
						"Verificar Detalle", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.EXCLAMATION, new EventListener<Event>() {

							public void onEvent(Event event) throws Exception {
								// TODO Auto-generated method stub
								if (Messagebox.ON_OK.equals(event.getName())) {
									detalleSelected.setEntregado(true);
									Integer indice = listaModeloDetalleVenta
											.indexOf(detalleSelected);
									listaModeloDetalleVenta.set(
											indice,
											ventaService
													.actualizarDetalle(detalleSelected));
									detalleSalida = salidaMaterialesService.buscarDetalle(
											salida.getId_salida(),
											detalleSelected.getIddetalle()
													.getCodigo_producto());
									detalleSalida.setEntregado(true);
									try {
										salidaMaterialesService
												.actualiarDetalle(detalleSalida);
										ActualizarInventario(detalleSalida);
										if (!PendienteEntrega()) {
											venta.setEntregado(true);
											venta.setFecha_entregado(new Date());
											venta = ventaService
													.actualizarVenta(venta);
											salida.setDespachado(true);
											salida.setFecha_entregado(new Date());
											salida = salidaMaterialesService
													.actualizarSalida(salida);
										}
										detalleSelected = null;
										detalleSalida = null;
									} catch (Exception ex) {
										Messagebox.show(
												"Ocurrió un problema al registrar la entrega del artículo. "
														+ ex.getLocalizedMessage(),
												"Error", Messagebox.OK,
												Messagebox.ERROR);
									} finally {
										listaModeloDetalleVenta
												.clearSelection();
									}
								} else {
									detalleSelected = null;
									listaModeloDetalleVenta.clearSelection();
									return;
								}
							}
						});

			} else {
				Messagebox
						.show("La venta no ha sido guardada, cancelada, generada orden de crédito o el producto ya fué entregado.",
								"Error!", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}

	}

	@Listen("onClick=#btnRemoverArticulo")
	public void RemoverArticulo() {
		if (detalleSelected != null) {
			if (!(venta.getCancelado() || venta.getCredito())) {
				Messagebox
						.show("Se eliminará el producto de la lista de detalle. ¿Desea continuar?",
								"Eliminar Producto", Messagebox.YES
										| Messagebox.NO, Messagebox.QUESTION,
								new EventListener<Event>() {

									public void onEvent(Event event)
											throws Exception {
										// TODO Auto-generated method stub
										if (Messagebox.ON_YES.equals(event
												.getName())) {
											DetalleVenta detalle = listaModeloDetalleVenta
													.remove(listaModeloDetalleVenta
															.indexOf(detalleSelected));
											CalculaTotal();
											
											if (ventaService
													.buscarDetalle(detalle
															.getIddetalle()) != null) {
												ventaService
														.borrarDetalle(detalle);
											} else {
												detalleSelected = null;
												return;
											}
										}
									}
								});
			} else {
				Messagebox
						.show("La venta ha sido cancelada o generada una orden de crédito. El producto no se puede eliminar.",
								"Error!", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	@Listen("onCheck=#chkEntregado")
	public void EntregarVenta() {
		if (chkEntregado.isChecked()) {
			if ((venta.getCancelado() || venta.getCredito())
					&& !venta.getEntregado()) {
				if (listaModeloDetalleVenta.size() > 0) {
					if (EntregarTodo()) {
						venta.setEntregado(true);
						venta.setFecha_entregado(new Date());
						salida.setDespachado(true);
						salida.setFecha_entregado(new Date());
						try {
							venta = ventaService.actualizarVenta(venta);
							salidaMaterialesService.actualizarSalida(salida);
						} catch (Exception ex) {
							Messagebox.show(
									"Ocurrió un error en la operación. "
											+ ex.getMessage(), "Error",
									Messagebox.OK, Messagebox.ERROR);
						} finally {
							chkEntregado.setDisabled(true);
						}
					}
				}
			} else {
				Messagebox
						.show("La venta no ha sido cancelada o generada orden de crédito. No se puede entregar el producto.",
								"Error!", Messagebox.OK, Messagebox.ERROR);
				chkEntregado.setChecked(false);
				return;
			}
		} else {
			Messagebox
					.show("La venta ya ha sido recibida. No se puede cambiar el estado.",
							"Error!", Messagebox.OK, Messagebox.ERROR);
			return;
		}

	}

	@Listen("onCheck=#chkAnulado")
	public void AnulaVenta() {
		if (!(venta.getCancelado() || venta.getCredito())) {
			venta.setAnulado(true);
			try {
				ventaService.actualizarVenta(venta);
				DeshabilitaCampos();
				DeshabilitaDetalle();
			} catch (Exception ex) {
				btnGuardar.setDisabled(true);
			}
		}
	}

	@Listen("onChanging=#txtBuscaCodArticulo")
	public void txtBuscaCodigoArticulo_onChangingListener(InputEvent event) {
		/*listaModeloProductos = new ListModelList<Producto>(
				productoService.getProductosPorCodigo(txtBuscaCodArticulo
						.getValue().toUpperCase()));
		lstProductos.setModel(listaModeloProductos);
		*/
		
		listaModeloProductos = new ListModelList<Producto>(
				productoService.getProductosPorCodigo(event.getValue()));
		if(listaModeloProductos != null){
			lstProductos.setModel(listaModeloProductos);
		}
	}

	@Listen("onChanging=#txtBuscaDescArticulo")
	public void txtBuscaDescripcionArticulo_onChangingListener(InputEvent event) {
		//listaModeloProductos = new ListModelList<Producto>(
		//		productoService.getListaProductos(txtBuscaDescArticulo
		//				.getValue().toUpperCase()));
		listaModeloProductos = new ListModelList<Producto>(
				productoService.getListaProductos(event.getValue()));
		if(listaModeloProductos != null){
			lstProductos.setModel(listaModeloProductos);
		}
		
	}

	private void ActualizarInventario(DetalleSalidaMateriales detalleSalida) {
		Inventario inventario = inventarioService
				.buscarInventario(new InventarioPK(salidaMaterialesService
						.buscaSalida(
								detalleSalida.getIddetalle().getId_salida())
						.getAlmacen().getId_almacen(), detalleSalida
						.getProducto().getCodigo_producto()));
		if (inventario != null) {
			UnidadMedida unidadMedida = detalleSalida.getUnidad_medida();
			
			if (unidadMedida.equals(inventario.getUnidadMedida())) {
				inventario.setCantidad(inventario.getCantidad().subtract(
						detalleSalida.getCantidad()).setScale(2,RoundingMode.DOWN));
			} else {
				ConversionUnidadMedida conversion = conversionUnidadMedidaService
						.getConversion(new ConversionUnidadPK(inventario
								.getUnidadMedida().getId_unidad_medida(),
								detalleSalida.getUnidad_medida()
										.getId_unidad_medida()));

				if (conversion != null) {
					inventario.setCantidad(inventario.getCantidad().subtract(
							detalleSalida.getCantidad().multiply(
									conversion.getConversion_inversa()).setScale(2,RoundingMode.DOWN)));
				} else {
					conversion = conversionUnidadMedidaService
							.getConversion(new ConversionUnidadPK(detalleSalida
									.getUnidad_medida().getId_unidad_medida(),
									inventario.getUnidadMedida()
											.getId_unidad_medida()));
					if (conversion != null) {
						inventario
								.setCantidad(inventario
										.getCantidad()
										.subtract(
												detalleSalida
														.getCantidad()
														.multiply(
																conversion
																		.getConversion_directa()).setScale(2,RoundingMode.DOWN)));
					}
				}
			}

			try {
				inventarioService.actualizarInventario(inventario);
			} catch (Exception ex) {
				Messagebox.show(
						"Ocurrió un problema al actualizar datos de inventario. "
								+ ex.getMessage(), "Error", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Throwable excepcion = new Throwable(
					"Excepcion de inventario inexistente.");
			excepcion.notify();
		}

	}

	private void CalculaTotal() {
		BigDecimal suma = new BigDecimal(0);
		BigDecimal iva = new BigDecimal(0);
		BigDecimal impuesto = new BigDecimal("0.15");
		suma.setScale(2, RoundingMode.UP);
		iva.setScale(2, RoundingMode.UP);

		for (DetalleVenta detalle : listaModeloDetalleVenta) {
			suma = suma.add(detalle.getMonto_total());
			if (!productoService.getProducto(detalle.getIddetalle().getCodigo_producto()).getExento()) {
				// iva = suma.multiply(new BigDecimal(0.15));
				iva = iva.add(detalle.getMonto_total().multiply(
						impuesto).setScale(2, RoundingMode.UP));
			}
		}

		Total = suma;
		Iva = iva;
		
		lblSubTotal.setValue(Total.toString());
		lblIva.setValue(Iva.toString());
		lblTotal.setValue(Total.add(Iva).setScale(2, RoundingMode.UP)
				.toString());
	}

	private void MuestraEntregados() {

		for (Listitem item : lstDetalleVenta.getItems()) {
			if (listaModeloDetalleVenta.get(item.getIndex()).getEntregado()) {
				item.setClass("entregado");
			}
		}
	}

	private Boolean PendienteEntrega() {

		for (DetalleVenta detalle : listaModeloDetalleVenta) {
			if (ventaService.recargarDetalle(detalle) != null
					&& !detalle.getEntregado()) {
				return true;
			}
		}
		return false;
	}

	private Boolean EntregarTodo() {

		Boolean entregado = true;
		List<DetalleSalidaMateriales> detalleSalidaMateriales = salida
				.getDetalle_salida();

		for (DetalleVenta detalle : listaModeloDetalleVenta) {

			if (!detalle.getEntregado()) {

				for (DetalleSalidaMateriales detallesal : detalleSalidaMateriales) {

					if (detallesal
							.getIddetalle()
							.getCodigo_producto()
							.equals(detalle.getIddetalle().getCodigo_producto())) {		
						
						try {
							detallesal.setEntregado(true);
							detalle.setEntregado(true);
							salidaMaterialesService
									.actualiarDetalle(detallesal);
							ventaService.actualizarDetalle(detalle);
							ActualizarInventario(detallesal);
						} catch (Exception ex) {
							entregado = false;
							return entregado;
						}
					}

				}

			}
		}

		return entregado;
	}

	private void HabilitaDetalle() {
		lstDetalleVenta.setDisabled(false);
		btnRemoverArticulo.setDisabled(false);
		btnEntregarArticulo.setDisabled(false);
	}

	private void DeshabilitaDetalle() {
		lstDetalleVenta.setDisabled(true);
		btnRemoverArticulo.setDisabled(true);
		btnEntregarArticulo.setDisabled(true);
	}

	private void HabilitaCamposDetalle() {
		bdbArticulo.setDisabled(false);
		cmbUnidadMedida.setDisabled(false);
		dcmbCantidad.setDisabled(false);
		btnAgregarArticulo.setDisabled(false);
		lstPrecios.setDisabled(false);
	}

	private void DeshabilitaCamposDetalle() {
		bdbArticulo.setDisabled(true);
		cmbUnidadMedida.setDisabled(true);
		dcmbCantidad.setDisabled(true);
		btnAgregarArticulo.setDisabled(true);
		lstPrecios.setDisabled(true);
	}

	private void HabilitaCampos() {
		bdbCliente.setDisabled(false);
		chkAnulado.setDisabled(false);
		chkEntregado.setDisabled(false);
		txtObservaciones.setDisabled(false);
		btnGuardar.setDisabled(false);
	}

	private void DeshabilitaCampos() {
		bdbCliente.setDisabled(true);
		txtObservaciones.setDisabled(true);
	}

}
