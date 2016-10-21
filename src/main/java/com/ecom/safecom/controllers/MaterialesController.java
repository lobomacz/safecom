package com.ecom.safecom.controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.CuentaContable;
import com.ecom.safecom.entity.Producto;
import com.ecom.safecom.entity.TipoProducto;
import com.ecom.safecom.entity.UnidadMedida;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.ProductoComparator;
import com.ecom.safecom.services.impl.ProductoServiceImpl;
import com.ecom.safecom.services.impl.TipoProductoServiceImpl;
import com.ecom.safecom.services.impl.UnidadMedidaServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MaterialesController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	ProductoServiceImpl productoService;
	@WireVariable
	TipoProductoServiceImpl tipoProductoService;
	@WireVariable
	CuentaContableServiceImpl cuentaContableService;
	@WireVariable
	UnidadMedidaServiceImpl unidadMedidaService;

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
	Window wMateriales;
	@Wire
	Listbox lstProductos;
	@Wire
	Listheader hdcodigo_producto;
	@Wire
	Listheader hddescripcion_producto;

	// Campos de Productos
	@Wire
	Textbox txtCodigoProducto;
	@Wire
	Textbox txtDescripcionProducto;
	@Wire
	Image imgImagenProducto;
	@Wire
	Fileupload fuplImagenProducto;
	@Wire
	Combobox cmbTipoProducto;
	@Wire
	Decimalbox dcmCostoProducto;
	@Wire
	Spinner spnMinimoProducto;
	@Wire
	Spinner spnMaximoProducto;
	@Wire
	Combobox cmbUnidadMedida;
	@Wire
	Checkbox chkComboProducto;
	@Wire
	Checkbox chkExento;
	@Wire
	Textbox txtTerminoArticulo;

	private Producto productoSelected;

	ProductoComparator pcCodAsc = new ProductoComparator(true, 1);
	ProductoComparator pcCodDsc = new ProductoComparator(false, 1);
	ProductoComparator pcDescAsc = new ProductoComparator(true, 2);
	ProductoComparator pcDescDsc = new ProductoComparator(false, 2);

	ListModelList<Producto> listaModeloProducto;
	ListModelList<TipoProducto> listaModeloTiposProducto;
	ListModelList<CuentaContable> listaModeloCuentas;
	ListModelList<UnidadMedida> listaModeloUnidadMedida;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		fuplImagenProducto.addEventListener("onUpload",
				new EventListener<UploadEvent>() {

					public void onEvent(UploadEvent event) throws Exception {
						final Media media = event.getMedia();
						if (media != null) {
							if (media instanceof org.zkoss.image.Image) {
								List<String> formatos = new ArrayList<String>();
								formatos.add("png");
								formatos.add("jpg");
								formatos.add("jpeg");

								if (!(formatos.indexOf(media.getFormat()) < 0)) {
									
									String rutaImagen = "files/imgs/articulos/";
									String realPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(rutaImagen);
									Messagebox.show(realPath);
									String nombreImagen = txtCodigoProducto.getValue().toLowerCase().replace('-', '_');
									String srcImagen = rutaImagen+nombreImagen+"_tmb.png";
									boolean res = GuardaImagen(media, realPath, nombreImagen);
									if(res == true){
										//File imageFile = new File(realPath+nombreImagen+"_tmb.png");
										//org.zkoss.image.Image imagen = (org.zkoss.image.Image) ImageIO.read(imageFile);
										productoSelected.setImagen_url(srcImagen);
										//org.zkoss.image.Image imagen = (org.zkoss.image.Image) media;
										//imgImagenProducto.setContent(imagen);
										imgImagenProducto.setSrc(srcImagen);
									}
								} else {
									Messagebox
											.show("El formato de imagen no es admitido. Sólo se aceptan formatos PNG y JPG",
													"Error de Formato",
													Messagebox.OK,
													Messagebox.ERROR);
								}
							} else {
								Messagebox
										.show("El archivo no es una imágen válida. Verifique y vuelva a intentarlo.",
												"Error de Archivo",
												Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});

		CargaListaProductos();
	}

	private void CargaListaProductos() {
		List<Producto> listaProductos = productoService.getListaProductos();
		listaModeloProducto = new ListModelList<Producto>(listaProductos);
		lstProductos.setModel(listaModeloProducto);
		List<TipoProducto> listaTiposProducto = tipoProductoService
				.getListaTipoProducto();
		listaModeloTiposProducto = new ListModelList<TipoProducto>(
				listaTiposProducto);
		cmbTipoProducto.setModel(listaModeloTiposProducto);
		List<UnidadMedida> listaUnidadesMedida = unidadMedidaService
				.getUnidadesBase();
		listaModeloUnidadMedida = new ListModelList<UnidadMedida>(
				listaUnidadesMedida);
		cmbUnidadMedida.setModel(listaModeloUnidadMedida);

		if (listaModeloProducto.isSelectionEmpty()) {
			productoSelected = null;
		} else {
			productoSelected = listaModeloProducto.getSelection().iterator()
					.next();
		}

		hdcodigo_producto.setSortAscending(pcCodAsc);
		hdcodigo_producto.setSortDescending(pcCodDsc);
		hddescripcion_producto.setSortAscending(pcDescAsc);
		hddescripcion_producto.setSortDescending(pcDescDsc);

		// INCLUIR CODIGO QUE SELECCIONE EL PRIMER ELEMENTO DE LA LISTA Y
		// REFRESQUE LOS CAMPOS
	}

	private boolean GuardaImagen(Media media, String rutaImagen, String nombreImagen ) throws IOException {
		boolean resultado = false;

		try {
			int altura = 96;
			int anchura = 96;
			String nombreMin = nombreImagen + "_tmb";

			BufferedImage imagen = ImageIO.read(media.getStreamData());
			OutputStream ois = new FileOutputStream(new File(rutaImagen + nombreImagen + ".png"));
			// BufferedImage grande = new BufferedImage(imagen.getWidth(),
			// imagen.getHeight(), imagen.getType());

			BufferedImage miniatura = new BufferedImage(anchura, altura,
					imagen.getType());
			OutputStream oms = new FileOutputStream(new File(rutaImagen + nombreMin + ".png"));

			Graphics2D g2d = miniatura.createGraphics();
			g2d.drawImage(imagen, 0, 0, anchura, altura, null);
			g2d.dispose();

			ImageOutputStream iois = ImageIO.createImageOutputStream(ois);
			ImageOutputStream ioms = ImageIO.createImageOutputStream(oms);
			ImageIO.write(imagen, "png", iois);
			ImageIO.write(miniatura, "png", ioms);
			oms.close();
			iois.close();
			ioms.close();
			resultado = true;

		} catch (IOException ex) {
			Messagebox.show("Error al procesar imágen." + ex.getMessage(),"Error",Messagebox.OK,Messagebox.ERROR);
		}

		return resultado;
	}

	@Listen("onSelect=#lstProductos")
	public void SelecionaProducto() {
		if (!listaModeloProducto.isSelectionEmpty()) {
			productoSelected = listaModeloProducto.getSelection().iterator()
					.next();
		}

		RefreshProductoDetailView();
	}

	@Listen("onClick=#tlbtnGuardar")
	public void Guardar() {
		if (productoSelected != null) {
			if (listaModeloProducto.indexOf(productoSelected) < 0
					&& productoService
							.getProducto(txtCodigoProducto.getValue()) != null) {
				txtCodigoProducto
						.setErrorMessage("El código de producto ya existe!");
				return;
			}

			Messagebox
					.show("Se guardarán los datos en la base de datos. ¿Desea continuar?",
							"Guardar Datos de Producto", Messagebox.YES
									| Messagebox.ABORT, Messagebox.QUESTION,
							new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (Messagebox.ON_YES.equals(event
											.getName())) {
										productoSelected = GuardarProducto();

										if (productoSelected != null) {
											listaModeloProducto
													.addToSelection(productoSelected);
											RefreshProductoDetailView();
											Messagebox
													.show("El registro de Producto se ha guardado.",
															"Registro Guardado",
															Messagebox.OK,
															Messagebox.INFORMATION);
											DisableProductoFields();
											tlbtnGuardar.setDisabled(true);
										}
									} else if (Messagebox.ON_ABORT.equals(event
											.getName())) {
										productoSelected = null;
										listaModeloProducto.iterator().next();
										// RefreshProductoDetailView();
										ClearProductoFields();
										DisableProductoFields();
										tlbtnGuardar.setDisabled(true);
										tlbtnEditar.setDisabled(true);
										tlbtnEliminar.setDisabled(true);
									}
								}
							});
		}
	}

	@Listen("onClick=#tlbtnAgregar")
	public void Nuevo() {
		listaModeloProducto.clearSelection();
		productoSelected = new Producto();
		ClearProductoFields();
		EnableProductoFields();
		txtCodigoProducto.select();
		tlbtnGuardar.setDisabled(false);
	}

	@Listen("onClick=#tlbtnEditar")
	public void Editar() {
		EnableProductoFields();
		tlbtnGuardar.setDisabled(false);
	}

	@Listen("onClick=#tlbtnEliminar")
	public void Eliminar() {
		if (productoSelected != null) {
			if (listaModeloProducto.indexOf(productoSelected) < 0
					&& productoService
							.getProducto(txtCodigoProducto.getValue()) == null) {
				Messagebox
						.show("No se ha seleccionado un registro válido para eliminar. Por favor revise y vuelva a intentarlo.",
								"Registro Inválido", Messagebox.OK,
								Messagebox.ERROR);
				return;
			}

			Messagebox
					.show("Se eliminará el retistro de la base de datos. ¿Desea continuar?",
							"Eliminar Producto",
							Messagebox.NO | Messagebox.YES,
							Messagebox.EXCLAMATION, new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (Messagebox.ON_YES.equals(event
											.getName())) {
										BorraProducto();
										ClearProductoFields();

									} else {
										listaModeloProducto.clearSelection();
										productoSelected = null;
										ClearProductoFields();
									}
									DisableProductoFields();
									tlbtnEliminar.setDisabled(true);
									tlbtnGuardar.setDisabled(true);
									tlbtnEditar.setDisabled(true);
								}
							});
		}
	}

	@Listen("onClick=#btnPreciosproducto")
	public void VerPreciosProducto() {
		// Include pagInclude =
		// (Include)Selectors.iterable(tabsMateriales.getPage(),
		// "#maininclude").iterator().next();
		// pagInclude.setSrc("/paginas/precios_producto.zul");
		if (this.productoSelected != null
				&& listaModeloProducto.indexOf(productoSelected) >= 0) {
			Map<String, Object> arg = new HashMap<String, Object>();
			arg.put("codProducto", productoSelected.getCodigo_producto());
			Window wPrecioProducto = (Window) Executions.getCurrent()
					.createComponents("/paginas/precios_producto.zul",
							wMateriales, arg);
			wPrecioProducto.setClosable(true);
			wPrecioProducto.setSizable(false);
			wPrecioProducto.setPosition("center");
			// wPrecioProducto.doPopup();
			wPrecioProducto.doModal();
		} else {
			Messagebox
					.show("No se ha seleccionado un producto válido. Revise y vuelva a intentarlo.",
							"Acción Inválida", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Listen("onClick=#btnBuscarArticulo")
	public void BuscarArticulos() {
		if (txtTerminoArticulo.getValue() != null) {
			listaModeloProducto = new ListModelList<Producto>(
					productoService.getListaProductos(txtTerminoArticulo
							.getValue()));
			lstProductos.setModel(listaModeloProducto);
		}
	}

	@Listen("onClick=#recargaListaProductos")
	public void RecargaListaProductos() {
		CargaListaProductos();
		txtTerminoArticulo.setValue("");
	}

	// /TODO:GENERAR CODIGO PARA LA CARGA DE LA IMAGEN

	private void RefreshProductoDetailView() {
		if (productoSelected != null
				&& productoSelected.getCodigo_producto() != null) {
			txtCodigoProducto.setText(productoSelected.getCodigo_producto());
			txtDescripcionProducto.setText(productoSelected.getDescripcion());
			// INCORPORAR CODIGO PARA CARGAR LA IMAGEN DEL PRODUCTO
			cmbTipoProducto.setSelectedIndex(listaModeloTiposProducto
					.indexOf(productoSelected.getTipoProducto()));
			dcmCostoProducto.setValue(productoSelected.getCosto().toString());
			spnMaximoProducto.setValue(productoSelected.getMaximo().intValue());
			spnMinimoProducto.setValue(productoSelected.getMinimo().intValue());
			cmbUnidadMedida.setSelectedIndex(listaModeloUnidadMedida
					.indexOf(productoSelected.getUnidadMedida()));
			imgImagenProducto.setSrc(productoSelected.getImagen_url()==null?"imgs/96/image-x-generic.png":productoSelected.getImagen_url());
			chkComboProducto.setChecked(productoSelected.getPara_combo());
			chkExento.setChecked(productoSelected.getExento());

			DisableProductoFields();

			tlbtnEditar.setDisabled(false);
			tlbtnEliminar.setDisabled(false);
			tlbtnGuardar.setDisabled(true);
		}
	}

	private Producto GuardarProducto() {

			productoSelected.setCodigo_producto(txtCodigoProducto.getValue());
			productoSelected.setDescripcion(txtDescripcionProducto.getValue());
			productoSelected.setTipoProducto(tipoProductoService
					.getTipoProducto((Integer) cmbTipoProducto
							.getSelectedItem().getValue()));
			BigDecimal costo = dcmCostoProducto.getValue();
			productoSelected.setCosto(costo);
			productoSelected.setMaximo(spnMaximoProducto.getValue()
					.floatValue());
			productoSelected.setMinimo(spnMinimoProducto.getValue()
					.floatValue());
			productoSelected.setUnidadMedida(unidadMedidaService
					.getUnidadMedida((Integer) cmbUnidadMedida
							.getSelectedItem().getValue()));
			// FALTA INGRESO DE FUNCION PARA CARGAR IMAGEN DEL PRODUCTO
			//productoSelected.setImagen_url(imgImagenProducto.getSrc());
			productoSelected.setPara_combo(chkComboProducto.isChecked());
			productoSelected.setExento(chkExento.isChecked());

			if (productoSelected.getCodigo_producto() != null
					&& productoService.getProducto(productoSelected
							.getCodigo_producto()) != null) {
				try {
					productoSelected = productoService
							.updateProducto(productoSelected);
				} catch (Exception ex) {
					// MOSTRAR MENSAJE DE ERROR
					Messagebox.show(
							"Ocurrió un error al actualizar los datos de producto. "
									+ ex.getMessage(), "Error de Datos",
							Messagebox.OK, Messagebox.ERROR);
				}
			} else {
				try {
					productoSelected = productoService
							.saveProducto(productoSelected);
				} catch (Exception ex) {
					// MOSTRAR MENSAJE DE ERROR
					Messagebox.show(
							"Ocurrió un error al guardar los datos de producto. "
									+ ex.getMessage(), "Error de Datos",
							Messagebox.OK, Messagebox.ERROR);
				} finally {
					listaModeloProducto.add(productoSelected);
				}
			}

		return productoSelected;
	}

	private void BorraProducto() {
		if (productoSelected != null
				&& productoSelected.getCodigo_producto() != null) {
			try {
				productoService.deleteProducto(listaModeloProducto
						.remove(listaModeloProducto.indexOf(productoSelected)));

				Messagebox.show("El registro de producto fué eliminado!",
						"Registro Eliminado", Messagebox.OK,
						Messagebox.INFORMATION);

			} catch (Exception ex) {
				Messagebox.show(
						"Ocurrió un error al eliminar datos de producto. "
								+ ex.getMessage(), "Error de Datos",
						Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	private void DisableProductoFields() {
		txtCodigoProducto.setDisabled(true);
		txtDescripcionProducto.setDisabled(true);
		cmbTipoProducto.setDisabled(true);
		dcmCostoProducto.setDisabled(true);
		spnMaximoProducto.setDisabled(true);
		spnMinimoProducto.setDisabled(true);
		cmbUnidadMedida.setDisabled(true);
		fuplImagenProducto.setVisible(false);
		chkComboProducto.setDisabled(true);
	}

	private void EnableProductoFields() {
		txtCodigoProducto.setDisabled(false);
		txtDescripcionProducto.setDisabled(false);
		cmbTipoProducto.setDisabled(false);
		dcmCostoProducto.setDisabled(false);
		spnMaximoProducto.setDisabled(false);
		spnMinimoProducto.setDisabled(false);
		cmbUnidadMedida.setDisabled(false);
		fuplImagenProducto.setVisible(true);
		chkComboProducto.setDisabled(false);
	}

	private void ClearProductoFields() {
		txtCodigoProducto.setRawValue("");
		txtDescripcionProducto.setRawValue("");
		cmbUnidadMedida.setRawValue("");
		cmbTipoProducto.setRawValue("");
		dcmCostoProducto.setValue("0.00");
		imgImagenProducto.setSrc("imgs/96/image-x-generic.png");
		chkComboProducto.setChecked(false);
	}

}
