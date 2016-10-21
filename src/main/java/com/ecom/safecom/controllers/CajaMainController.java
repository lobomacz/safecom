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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.Caja;
import com.ecom.safecom.entity.CuentaContable;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContablePK;
import com.ecom.safecom.entity.MovimientoCaja;
import com.ecom.safecom.services.impl.AsientoContableServiceImpl;
import com.ecom.safecom.services.impl.AutenticacionService;
import com.ecom.safecom.services.impl.CajaServiceImpl;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.PeriodoServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CajaMainController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	CajaServiceImpl cajaService;
	@WireVariable
	CuentaContableServiceImpl cuentaContableService;
	@WireVariable
	AsientoContableServiceImpl asientoContableService;
	@WireVariable
	PeriodoServiceImpl periodoService;
	@WireVariable
	AutenticacionService autenService;

	Caja cajaSelected;

	AsientoContable asiento;

	ListModelList<Caja> listaModeloCajas;

	ListModelList<CuentaContable> listaModeloCuentas;

	@Wire
	Window wCajaMain;
	@Wire
	Listbox lstListaCajas;
	@Wire
	Listbox lstListaCuentas;
	@Wire
	Textbox txtDescripcion;
	@Wire
	Bandbox bndbCuenta;
	@Wire
	Decimalbox dcmbSaldo;
	@Wire
	Toolbarbutton tlbtnAsiento;
	@Wire
	Toolbarbutton tlbtnGuardar;
	@Wire
	Toolbarbutton tlbtnEliminar;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

		listaModeloCajas = new ListModelList<Caja>(cajaService.ListaCajas());
		lstListaCajas.setModel(listaModeloCajas);
		this.asiento = null;

		listaModeloCuentas = new ListModelList<CuentaContable>(
				cuentaContableService.getListaCuentasContables());
		lstListaCuentas.setModel(listaModeloCuentas);

	}

	@Listen("onChanging=#txtBuscaCuenta")
	public void txtBuscaCuenta_onChangingListener(InputEvent event) {
		String nombre = event.getValue();
		listaModeloCuentas = new ListModelList<CuentaContable>(
				cuentaContableService.listByName(nombre));
		this.lstListaCuentas.setModel(listaModeloCuentas);
	}

	@Listen("onSetAsiento=#wCajaMain")
	public void SetAsiento(Event evento) {
		if (evento.getData() != null) {
			asiento = (AsientoContable) evento.getData();
		} else {
			asiento = null;
		}
	}
	
	@Listen("onSelect=#lstListaCuentas")
	public void lstListaCuentas_onSelectListener(){
		bndbCuenta.setValue(listaModeloCuentas.getSelection().iterator().next().getCuenta());
		bndbCuenta.close();
	}

	@Listen("onSelect=#lstListaCajas")
	public void SeleccionaCaja() {
		if (!listaModeloCajas.isSelectionEmpty()) {
			cajaSelected = listaModeloCajas.getSelection().iterator().next();
			txtDescripcion.setValue(cajaSelected.getDescripcion());
			bndbCuenta.setValue(cajaSelected.getCuenta().getCuenta());
			dcmbSaldo.setValue(CalculaSaldo());
			EnableOpciones();
		}
	}

	@Listen("onClick=#btnVentas")
	public void btnVentas_onClickListener() {
		if (cajaSelected != null && cajaSelected.getId_caja() != null) {

			if (!cajaSelected.getAbierta()) {
				Messagebox
						.show("La caja seleccionada se encuentra cerrada. Realice el proceso de arqueo y proceda a abrirla.");
				return;
			}

			Executions.getCurrent().setAttribute("cajaSelected", cajaSelected);
			Include include = (Include) Selectors
					.find(getSelf().getPage(), "#maininclude").iterator()
					.next();
			include.setSrc("/paginas/caja.zul");
			getPage().getDesktop().setBookmark("p_caja");
		} else {
			Messagebox.show("No se ha seleccionado una caja registrada.");
		}
	}
	
	@Listen("onClick=#btnAbonos")
	public void btnAbonos_onClickListener(){
		if(cajaSelected != null && cajaSelected.getId_caja() != null){
			Executions.getCurrent().setAttribute("cajaSelected", cajaSelected);
			Include include = (Include)Selectors.find(getSelf().getPage(), "#maininclude").iterator().next();
			include.setSrc("/paginas/caja_abonos.zul");
		}
		
	}

	@Listen("onClick=#tlbtnAsiento")
	public void tlbtnAsiento_onClickListener() {
		Map<String, Object> args = new HashMap<String, Object>();
		AsientoContable asiento = new AsientoContable();
		asiento.setFecha(new Date());
		asiento.setDescripcion("Apertura Inicial de Caja");
		asiento.setReferencia("0");
		asiento.setContabilizado(false);
		asiento.setPeriodo(periodoService.getPeriodoAbierto());
		asiento.setUsuario(autenService.getUserCredential());
		List<DetalleAsientoContable> detalleAsiento = new ArrayList<DetalleAsientoContable>();
		DetalleAsientoContable detalle = new DetalleAsientoContable();
		detalle.setIddetalle(new DetalleAsientoContablePK(0, bndbCuenta
				.getValue()));
		detalle.setContabilizado(false);
		detalle.setDescripcion("APERTURA INICIAL DE CAJA, "
				+ txtDescripcion.getValue());
		detalle.setTipo_movimiento("db");
		detalle.setMonto(dcmbSaldo.getValue());
		detalleAsiento.add(detalle);
		detalle = new DetalleAsientoContable();
		detalle.setIddetalle(new DetalleAsientoContablePK(0,
				cuentaContableService.searchByName("BANC").getCuenta()));
		detalle.setDescripcion("CUENTA EN CORDOBAS");
		detalle.setTipo_movimiento("cr");
		detalle.setContabilizado(false);
		detalle.setMonto(dcmbSaldo.getValue());
		detalleAsiento.add(detalle);
		asiento.setDetalleAsiento(detalleAsiento);
		args.put("asiento", asiento);

		Window wAsiento;
		if (wCajaMain.getParent().query("#wAsientoContable") == null) {
			wAsiento = (Window) Executions.createComponents(
					"/paginas/asientos.zul", wCajaMain.getParent(), args);
			wAsiento.setPosition("center");
			wAsiento.setClosable(true);
			wAsiento.setSizable(false);
		} else {
			wAsiento = (Window) wCajaMain.getParent()
					.query("#wAsientoContable");
		}
		wAsiento.doModal();
	}

	@Listen("onClick=#tlbtnGuardar")
	public void Guardar() {
		if (cajaSelected == null) {

			if (this.asiento == null) {
				Messagebox
						.show("No se ha ingresado el asiento contable de apertura. Por favor ingrese el asiento y vuenva a intentar la operación.",
								"Error en la Operación", Messagebox.OK,
								Messagebox.ERROR);
				return;
			}
			cajaSelected = new Caja();
			cajaSelected.setDescripcion(txtDescripcion.getValue());
			cajaSelected.setAbierta(false);
			
			/*
			CuentaContable cuenta = cuentaContableService
					.getCuentaContable(bndbCuenta.getValue());
			if (cuenta != null) {
				cajaSelected.setCuenta(cuenta);
			} else {
				Messagebox
						.show("La cuenta ingresada no es correcta. Revise y vuelva a intentarlo.",
								"Error de Cuenta", Messagebox.OK,
								Messagebox.ERROR);
				return;
			}

			 */
			
			/*
			if (!listaModeloCuentas.isSelectionEmpty()) {
				cajaSelected.setCuenta(listaModeloCuentas.getSelection()
						.iterator().next());
			}
			*/
			
			if(bndbCuenta.getValue() != ""){
				cajaSelected.setCuenta(cuentaContableService.getCuentaContable(this.bndbCuenta.getValue()));
			}

			if (asiento != null) {
				try {
					cajaSelected = cajaService.GuardarCaja(cajaSelected);
					listaModeloCajas.add(cajaSelected);
					MovimientoCaja mCaja = new MovimientoCaja();
					mCaja.setCaja(cajaSelected);
					mCaja.setConcepto("Apertura inicial de caja");
					mCaja.setEmpleado(autenService.getUserCredential()
							.getEmpleado());
					mCaja.setFecha_hora_movimiento(new Date());
					mCaja.setTipo_movimiento("ingreso");
					mCaja.setTipo_pago("efectivo");
					mCaja.setMonto(dcmbSaldo.getValue());
					cajaService.GuardarMovimientoCaja(mCaja);
					// asientoContableService.guardarAsiento(asiento);
				} catch (Exception ex) {
					Messagebox
							.show("Ocurrio un problema al registrar la caja. "
									+ ex.getMessage());
					if (cajaService.BuscarCaja(cajaSelected.getId_caja()) != null) {
						cajaService.BorrarCaja(cajaSelected);
					}

					if (asientoContableService.buscarAsiento(asiento
							.getId_asiento()) != null) {
						asientoContableService.borrarAsiento(asiento);
					}

					return;
				} finally {
					cajaSelected = null;
					DisableOpciones();
					ResetCampos();
				}
			}
		} else {
			cajaSelected.setDescripcion(txtDescripcion.getValue());
			try {
				listaModeloCajas.set(listaModeloCajas.indexOf(cajaSelected),
						cajaService.ActualizarCaja(cajaSelected));
			} catch (Exception ex) {
				Messagebox.show("Ocurrio un problema al actualizar la caja. "
						+ ex.getMessage());
				return;
			} finally {
				cajaSelected = null;
				listaModeloCajas.clearSelection();
				DisableOpciones();
				ResetCampos();
				DisableCampos();
			}
		}
	}

	@Listen("onClick=#tlbtnAgregar")
	public void Nuevo() {
		cajaSelected = new Caja();
		listaModeloCajas.clearSelection();
		tlbtnGuardar.setDisabled(false);
		ResetCampos();
		EnableCampos();
	}

	// TODO: CAMBIAR POR LLAMADA A FUNCION DE LA CLASE CALCULADOR

	private BigDecimal CalculaSaldo() {
		BigDecimal saldo = BigDecimal.ZERO;

		List<DetalleAsientoContable> listaMovimientos = asientoContableService
				.listaPorCuenta(cajaSelected.getCuenta().getCuenta());

		for (DetalleAsientoContable detalle : listaMovimientos) {
			if (detalle.getTipo_movimiento().equals("db")) {
				saldo = saldo.add(detalle.getMonto()).setScale(2,
						RoundingMode.UP);
			} else {
				saldo = saldo.subtract(detalle.getMonto()).setScale(2,
						RoundingMode.UP);
			}
		}

		return saldo;
	}

	private void DisableOpciones() {
		tlbtnEliminar.setDisabled(true);
		tlbtnGuardar.setDisabled(true);
	}

	private void EnableOpciones() {
		tlbtnEliminar.setDisabled(false);
		tlbtnGuardar.setDisabled(false);
	}

	private void ResetCampos() {
		this.asiento = null;
		this.cajaSelected = null;
		txtDescripcion.setRawValue("");
		bndbCuenta.setRawValue("");
		dcmbSaldo.setValue("0");
	}

	private void DisableCampos() {
		txtDescripcion.setDisabled(true);
		bndbCuenta.setDisabled(true);
		dcmbSaldo.setDisabled(true);
	}

	private void EnableCampos() {
		txtDescripcion.setDisabled(false);
		bndbCuenta.setDisabled(false);
		dcmbSaldo.setDisabled(false);
	}
}
