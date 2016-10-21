package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.PrecioProducto;
import com.ecom.safecom.entity.PrecioProductoPK;
import com.ecom.safecom.entity.Producto;
import com.ecom.safecom.entity.UnidadMedida;
import com.ecom.safecom.services.impl.PrecioProductoComparator;
import com.ecom.safecom.services.impl.PrecioProductoServiceImpl;
import com.ecom.safecom.services.impl.ProductoServiceImpl;
import com.ecom.safecom.services.impl.UnidadMedidaServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PreciosProductoController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	@WireVariable
	ProductoServiceImpl productoService;
	@WireVariable
	UnidadMedidaServiceImpl unidadMedidaService;
	@WireVariable
	PrecioProductoServiceImpl precioProductoService;

	@Wire
	Window mainPrecioProducto;
	@Wire
	Listbox lstPreciosProducto;
	@Wire
	Intbox numeroPrecio;
	@Wire
	Combobox cmbTipoPrecio;
	@Wire
	Combobox cmbUnidadMedidaPrecio;
	@Wire
	Decimalbox dcmPrecioProducto;

	@Wire
	Toolbarbutton agregaPrecio;
	@Wire
	Toolbarbutton guardaPrecio;
	@Wire
	Toolbarbutton borraPrecio;

	private String codProducto = (String) Executions.getCurrent().getArg()
			.get("codProducto");
	private Producto producto;
	private PrecioProducto precioSelected;
	private int secuencia;
	List<PrecioProducto> preciosProducto;
	List<UnidadMedida> unidadesMedida;
	ListModelList<PrecioProducto> listaModeloPrecios;
	ListModelList<UnidadMedida> listaModeloUnidadMedidaPrecio;

	// Map<String, String> tiposPrecio;

	@Override
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);

		this.producto = productoService.getProducto(codProducto);
		Caption capProducto = (Caption) mainPrecioProducto
				.query("caption#captProducto");
		capProducto.setLabel(this.producto.getDescripcion());
		capProducto.setImage(producto.getImagen_url());
		LlenaDatos();

		/*
		 * tiposPrecio = new HashMap<String, String>();
		 * tiposPrecio.put("Precio Base", "base");
		 * tiposPrecio.put("Precio de Menudeo", "menudeo");
		 * tiposPrecio.put("Precio Promocional", "promo");
		 * tiposPrecio.put("Precio Preferencial", "preferencial");
		 * tiposPrecio.put("Precio de Combo", "combo"); tiposPrecio.put("Otro",
		 * "otro"); ListModelList<Map> listaModeloTipos = new
		 * ListModelList<Map>((Collection<? extends Map>) tiposPrecio);
		 */
	}

	@Listen("onClick=#guardaPrecio")
	public void Guardar() {
		if (precioSelected != null) {
			Messagebox
					.show("Se enviarán los datos a la base de datos. ¿Desea Continuar?",
							"Almacenar Datos", Messagebox.YES
									| Messagebox.ABORT, Messagebox.QUESTION,
							new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (Messagebox.ON_YES.equals(event
											.getName())) {
										precioSelected = GuardarPrecio();
										try {
											// PrecioProductoPK clave = new
											// PrecioProductoPK(codProducto,
											// secuencia);
											// precioSelected =
											// precioProductoService.getPrecioProducto(clave);
											precioSelected = precioProductoService
													.reloadPrecioProducto(precioSelected);
											if (precioSelected != null) {
												Messagebox
														.show("Los datos se guardaron con ésito.",
																"Datos Almacenados",
																Messagebox.OK,
																Messagebox.EXCLAMATION);
												listaModeloPrecios
														.clearSelection();
												// listaModeloPrecios.addToSelection(precioSelected);
												guardaPrecio.setDisabled(true);
												// RefreshDatosPrecio();
												ClearFields();
											}
										} catch (Exception ex) {
											Messagebox.show(
													"Ocurrió un error y los datos no fueron almacenados. "
															+ ex.getMessage(),
													"Error", Messagebox.OK,
													Messagebox.EXCLAMATION);
										}
									} else if (Messagebox.ON_ABORT.equals(event
											.getName())) {
										precioSelected = null;
										ClearFields();
									}
								}
							});
		} else {
			Messagebox
					.show("No existen datos que enviar a la base de datos. Verifique y vuelva a intentarlo.",
							"Datos Inexistentes", Messagebox.OK,
							Messagebox.ERROR);
		}
	}

	@Listen("onClick=#agregaPrecio")
	public void Agregar() {
		ClearFields();
		this.precioSelected = new PrecioProducto();
		numeroPrecio.setValue(++this.secuencia);
		numeroPrecio.setDisabled(false);
		guardaPrecio.setDisabled(false);
		this.cmbTipoPrecio.focus();
	}

	@Listen("onClick=#borraPrecio")
	public void Borrar() {
		if (precioSelected == null
				&& listaModeloPrecios.indexOf(precioSelected) < 0) {
			Messagebox
					.show("No se han seleccionado datos para eliminar. Revise y vuelva a intentarlo.",
							"Sin Datos Válidos", Messagebox.OK,
							Messagebox.EXCLAMATION);
			return;
		}

		Messagebox.show(
				"Se eliminarán datos de la base de datos. ¿Desea Continuar?",
				"Eliminar Datos", Messagebox.YES | Messagebox.ABORT,
				Messagebox.QUESTION, new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {
						if (Messagebox.ON_YES.equals(event.getName())) {
							BorraPrecio();
							ClearFields();
						} else if (Messagebox.ON_ABORT.equals(event.getName())) {
							precioSelected = null;
							ClearFields();
						}
					}
				});

	}

	@Listen("onSelect=#lstPreciosProducto")
	public void SelectPrecio() {
		if (!listaModeloPrecios.isSelectionEmpty()) {
			this.precioSelected = listaModeloPrecios.getSelection().iterator()
					.next();
			RefreshDatosPrecio();
			borraPrecio.setDisabled(false);
			guardaPrecio.setDisabled(false);
		}
	}

	private void LlenaDatos() {
		preciosProducto = this.producto.getListaPrecios();

		unidadesMedida = unidadMedidaService.getListaUnidadMedida();
		listaModeloPrecios = new ListModelList<PrecioProducto>(preciosProducto);
		listaModeloUnidadMedidaPrecio = new ListModelList<UnidadMedida>(
				unidadesMedida);
		lstPreciosProducto.setModel(this.listaModeloPrecios);
		cmbUnidadMedidaPrecio.setModel(this.listaModeloUnidadMedidaPrecio);
		this.secuencia = listaModeloPrecios.getSize();
	}

	private void RefreshDatosPrecio() {
		if (precioSelected != null) {
			numeroPrecio.setValue(precioSelected.getPrecioPK()
					.getNumero_precio());
			for (Comboitem item : cmbTipoPrecio.getItems()) {
				if (item.getValue().equals(precioSelected.getTipo_precio())) {
					cmbTipoPrecio.setSelectedItem(item);
				}
			}
			// listaModeloUnidadMedida.addToSelection(precioSelected.getUnidad());

			for (UnidadMedida unidad : listaModeloUnidadMedidaPrecio) {
				if (unidad.equals(precioSelected.getUnidad())) {
					cmbUnidadMedidaPrecio
							.setSelectedIndex(this.listaModeloUnidadMedidaPrecio
									.indexOf(unidad));
				}
			}
			// cmbUnidadMedidaPrecio.setSelectedIndex(this.listaModeloUnidadMedidaPrecio.indexOf(precioSelected.getUnidad()));

			dcmPrecioProducto.setValue(precioSelected.getPrecio().toString());
		}
	}

	private void BorraPrecio() {
		try {
			precioProductoService.deletePrecioProducto(listaModeloPrecios
					.remove(listaModeloPrecios.indexOf(precioSelected)));
			precioSelected = null;
			LlenaDatos();
		} catch (Exception ex) {
			listaModeloPrecios.add(precioSelected);
			Messagebox
					.show("Ocurrió un problema al eliminar datos de precio. Por favor verifique y vuelva a intentarlo. "
							+ ex.getMessage(), "Error de Borrado",
							Messagebox.OK, Messagebox.ERROR);
		}
	}

	private PrecioProducto GuardarPrecio() {

		BigDecimal precio = dcmPrecioProducto.getValue();
		this.precioSelected.setPrecio(precio);
		this.precioSelected.setTipo_precio(cmbTipoPrecio.getSelectedItem()
				.getValue().toString());
		this.precioSelected.setUnidad(listaModeloUnidadMedidaPrecio
				.get(cmbUnidadMedidaPrecio.getSelectedIndex()));
		if (listaModeloPrecios.indexOf(precioSelected) < 0) {
			PrecioProductoPK precioPk = new PrecioProductoPK(codProducto,
					numeroPrecio.getValue());
			this.precioSelected.setPrecioPK(precioPk);
			try {
				this.precioSelected = precioProductoService
						.savePrecioProducto(precioSelected);
				this.listaModeloPrecios.add(precioSelected);
				this.listaModeloPrecios.sort(new PrecioProductoComparator(),
						true);
			} catch (Exception ex) {
				Messagebox
						.show("Ocurrió un problema al guardar datos de precio.Por favor verifique y vuelva a intentarlo. "
								+ ex.getMessage(), "Error de Ingreso",
								Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			try {
				this.precioSelected = precioProductoService
						.updatePrecioProducto(precioSelected);
				LlenaDatos();
			} catch (Exception ex) {
				Messagebox
						.show("Ocurrió un problema al actualizar datos de precio.Por favor verifique y vuelva a intentarlo. "
								+ ex.getMessage(), "Error de Actualización",
								Messagebox.OK, Messagebox.ERROR);
			}
		}

		return precioSelected;
	}

	private void ClearFields() {
		this.numeroPrecio.setText("");
		this.dcmPrecioProducto.setValue("0");
		this.cmbTipoPrecio.setRawValue("");
		this.cmbUnidadMedidaPrecio.setRawValue("");
		this.listaModeloPrecios.clearSelection();
	}

	private boolean IsLast(PrecioProducto precio) {
		return (listaModeloPrecios.indexOf(precio) == listaModeloPrecios
				.getSize() - 1) ? true : false;
	}

}
