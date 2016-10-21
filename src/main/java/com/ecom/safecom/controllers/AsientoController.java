package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.CuentaContable;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContablePK;
import com.ecom.safecom.entity.Periodo;
import com.ecom.safecom.entity.Usuario;
import com.ecom.safecom.services.impl.AsientoContableServiceImpl;
import com.ecom.safecom.services.impl.AutenticacionService;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.EntradaMaterialesServiceImpl;
import com.ecom.safecom.services.impl.PeriodoServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AsientoController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	private AsientoContableServiceImpl asientoContableService;
	@WireVariable
	private CuentaContableServiceImpl cuentaContableService;
	@WireVariable
	private PeriodoServiceImpl periodoService;
	@WireVariable
	private AutenticacionService autenService;

	private AsientoContable asientoContable; // = null;
	private List<DetalleAsientoContable> listaDetalleAsiento; // =
																// Executions.getCurrent().getArg().get("detalle")!=null?(List<DetalleAsientoContable>)Executions.getCurrent().getArg().get("detalle"):null;
	private ListModelList<DetalleAsientoContable> listaModeloDetalle;
	private DetalleAsientoContable detalleAsientoSelected;
	// private Date fecha =
	// Executions.getCurrent().getArg().get("fechaAsiento")!=null?(Date)Executions.getCurrent().getArg().get("fechaAsiento"):null;
	// private Integer idAsiento =
	// Executions.getCurrent().getArg().get("idAsiento")!=null?(Integer)Executions.getCurrent().getArg().get("idAsiento"):null;

	@Wire
	Label lblCreditos;
	@Wire
	Label lblDebitos;
	@Wire
	Datebox dtbFecha;
	@Wire
	Textbox txtDescripcion;
	@Wire
	Textbox txtReferencia;
	@Wire
	Checkbox chkContabilizado;
	@Wire
	Combobox cmbCuenta;
	@Wire
	Textbox txtDescripcionDetalle;
	@Wire
	Combobox cmbTipoMovimiento;
	@Wire
	Decimalbox dcmbMonto;
	@Wire
	Listbox lstDetalleAsiento;
	@Wire
	Button btnGuardarDetalle;
	@Wire
	Button btnEliminarDetalle;
	@Wire
	Button btnAgregarDetalle;
	@Wire
	Window wAsientoContable;

	private List<CuentaContable> listaCuentas;
	private ListModelList<CuentaContable> listaModeloCuentas;
	private BigDecimal creditos = new BigDecimal(0);
	private BigDecimal debitos = new BigDecimal(0);

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

		asientoContable = (AsientoContable) Executions.getCurrent().getArg()
				.get("asiento");
		// listaDetalleAsiento =
		// asientoContableService.recargarAsiento(asientoContable).getDetallesAsiento();
		// listaModeloDetalle = new
		// ListModelList<DetalleAsientoContable>(listaDetalleAsiento);
		// lstDetalleAsiento.setModel(listaModeloDetalle);

		listaCuentas = cuentaContableService.getListaCuentasContables();
		listaModeloCuentas = new ListModelList<CuentaContable>(listaCuentas);
		cmbCuenta.setModel(listaModeloCuentas);

		LlenaDatos();
	}

	@Listen("onSelect=#lstDetalleAsiento")
	public void SeleccionaDetalle() {
		if (!listaModeloDetalle.isSelectionEmpty()) {
			detalleAsientoSelected = listaModeloDetalle.getSelection()
					.iterator().next();
			for (CuentaContable cuenta : listaModeloCuentas) {
				if (cuenta.getCuenta() == detalleAsientoSelected.getIddetalle()
						.getCuenta()) {
					listaModeloCuentas.addToSelection(cuenta);
				}
			}
			cmbCuenta.setDisabled(true);
			txtDescripcionDetalle.setValue(detalleAsientoSelected
					.getDescripcion());
			cmbTipoMovimiento.setValue(detalleAsientoSelected
					.getTipo_movimiento());
			dcmbMonto.setValue(detalleAsientoSelected.getMonto());
			btnEliminarDetalle.setDisabled(false);
			btnGuardarDetalle.setDisabled(false);
			EnableFieldsDetalle();
			cmbCuenta.setDisabled(true);
		}
	}

	@Listen("onClick=#btnAgregarDetalle")
	public void AgregarDetalle() {
		detalleAsientoSelected = new DetalleAsientoContable();
		ClearFieldsDetalle();
		EnableFieldsDetalle();
		btnGuardarDetalle.setDisabled(false);
		btnEliminarDetalle.setDisabled(true);
	}

	@Listen("onClick=#btnGuardarDetalle")
	public void GuardarDetalle() {
		Integer _id = asientoContable.getId_asiento() == null ? 0
				: asientoContable.getId_asiento();
		if (listaModeloDetalle.indexOf(detalleAsientoSelected) < 0) {

			// detalleAsientoSelected = new DetalleAsientoContable();
			detalleAsientoSelected.setIddetalle(new DetalleAsientoContablePK(
					_id, listaModeloCuentas.getSelection().iterator().next()
							.getCuenta()));
			detalleAsientoSelected.setDescripcion(txtDescripcionDetalle
					.getValue());
			detalleAsientoSelected.setTipo_movimiento(cmbTipoMovimiento
					.getValue());
			detalleAsientoSelected.setMonto(dcmbMonto.getValue());
			detalleAsientoSelected.setContabilizado(false);
			listaModeloDetalle.add(detalleAsientoSelected);
		} else {
			detalleAsientoSelected.setDescripcion(txtDescripcionDetalle
					.getValue());
			detalleAsientoSelected.setTipo_movimiento(cmbTipoMovimiento
					.getValue());
			detalleAsientoSelected.setMonto(dcmbMonto.getValue());
			listaModeloDetalle.set(
					listaModeloDetalle.indexOf(detalleAsientoSelected),
					detalleAsientoSelected);
		}

		CalculaBalance();

		ClearFieldsDetalle();
		btnEliminarDetalle.setDisabled(false);
	}

	@Listen("onClick=#btnEliminarDetalle")
	public void EliminarDetalle() {
		if (detalleAsientoSelected != null) {
			if (asientoContableService.buscarDetalle(detalleAsientoSelected
					.getIddetalle()) == null) {
				listaModeloDetalle.remove(detalleAsientoSelected);
			} else {
				if (asientoContable.getContabilizado()) {
					Messagebox
							.show("El asiento ya ha sido contabilizado y no admite modificaciones.");
					return;
				}
				asientoContableService.borrarDetalle(listaModeloDetalle
						.remove(listaModeloDetalle
								.indexOf(detalleAsientoSelected)));
			}
			CalculaBalance();
		}
	}

	@Listen("onClick=#btnGuardar")
	public void GuardarAsiento() {
		Periodo periodo = periodoService.getPeriodoAbierto();
		if (asientoContable.getId_asiento() != null) {

			if (asientoContableService.recargarAsiento(asientoContable)
					.getContabilizado()) {
				Messagebox
						.show("El asiento ya ha sido contabilizado y no admite modificaciones.");
				return;
			}

		} else {
			asientoContable.setFecha(dtbFecha.getValue());
			asientoContable.setDescripcion(txtDescripcion.getValue());
			asientoContable.setReferencia(txtReferencia.getValue());
			
			asientoContable.setUsuario(autenService.getUserCredential());
			asientoContable.setContabilizado(false);
		}

		/*
		 * if(asientoContable == null){ asientoContable = new AsientoContable();
		 * }else if(asientoContableService.recargarAsiento(asientoContable).
		 * getContabilizado()){ Messagebox.show(
		 * "El asiento ya ha sido contabilizado y no admite modificaciones.");
		 * return; }
		 */

		if (creditos.compareTo(debitos) != 0) {
			Messagebox
					.show("Los montos del asiento no cuadran. Revise y vuelva a intentarlo.","Error en montos",Messagebox.OK,Messagebox.EXCLAMATION);
			return;
		}

		// if (dtbFecha.getValue().after(periodo.getFecha_inicio()) &&
		// dtbFecha.getValue().before(periodo.getFecha_final())) {
		if (dtbFecha.getValue().compareTo(periodo.getFecha_inicio()) >= 0
				&& dtbFecha.getValue().compareTo(periodo.getFecha_final()) <= 0) {

			try {
				if (asientoContable.getId_asiento() == null) {
					if(asientoContable.getPeriodo()==null){
						asientoContable.setPeriodo(periodo);
					}
					asientoContable.setDetalleAsiento(null);
					asientoContable = asientoContableService
							.guardarAsiento(asientoContable);
					if (listaModeloDetalle.size() > 0) {
						for (DetalleAsientoContable detalle : listaModeloDetalle) {
							detalle.setIddetalle(new DetalleAsientoContablePK(
									asientoContable.getId_asiento(), detalle
											.getIddetalle().getCuenta()));
						}
						asientoContable.setDetalleAsiento(listaModeloDetalle
								.getInnerList());
						asientoContableService
								.actualizarAsiento(asientoContable);
					}
				} else {
					asientoContable.setDetalleAsiento(listaModeloDetalle
							.getInnerList());
					asientoContable = asientoContableService
							.actualizarAsiento(asientoContable);
				}
				/*
				 * for(DetalleAsientoContable detalle : listaModeloDetalle){
				 * if(detalle.getIddetalle().getId_asiento() == 0){
				 * detalle.setIddetalle(new
				 * DetalleAsientoContablePK(asientoContable
				 * .getId_asiento(),detalle.getIddetalle().getCuenta()));
				 * asientoContableService.guardarDetalle(detalle); }else{
				 * asientoContableService.actualizarDetalle(detalle); } }
				 */

				//Events.postEvent("onSetAsiento",
				//		getSelf().getPreviousSibling(), asientoContable);
				Events.sendEvent("onClose", getSelf(), asientoContable);

			} catch (Exception ex) {
				Messagebox.show(
						"Ocurrión un error al guardar datos contables. "
								+ ex.getMessage(), "Error al Guardar",
						Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("La fecha del asiento no corresponde al periodo contable vigente.");
		}

	}
	
	@Listen("onClose=#wAsientoContable")
	public void Cerrar(Event event){
		Events.postEvent("onSetAsiento", getSelf().getPreviousSibling(), event.getData());
	}

	@Listen("onClick=#btnCancelar")
	public void Cancelar() {
		Events.sendEvent("onClose", getSelf(), null);
	}

	@Listen("onSelect=#cmbCuenta")
	public void SeleccionaDescripcion() {
		this.txtDescripcionDetalle.setValue(listaModeloCuentas.getSelection()
				.iterator().next().getNombre());
		this.txtDescripcionDetalle.focus();
	}

	private void LlenaDatos() {
		this.listaModeloDetalle = new ListModelList<DetalleAsientoContable>();
		if (asientoContable == null) {
			this.asientoContable = new AsientoContable();
			this.asientoContable.setFecha(new Date());
		} else {

			this.dtbFecha.setValue(this.asientoContable.getFecha());
			this.dtbFecha.setReadonly(true);
			this.txtDescripcion.setValue(this.asientoContable.getDescripcion());
			this.txtReferencia.setValue(this.asientoContable.getReferencia());
			this.chkContabilizado.setChecked(this.asientoContable
					.getContabilizado());

			if (asientoContable.getId_asiento() != null) {
				this.listaDetalleAsiento = asientoContableService
						.recargarAsiento(asientoContable).getDetallesAsiento();
			} else {
				this.listaDetalleAsiento = asientoContable.getDetallesAsiento();
			}

			// this.listaDetalleAsiento =
			// this.asientoContable.getDetallesAsiento();
			// EnableFieldsDetalle();
		}

		if (asientoContable.getDetallesAsiento() != null) {
			this.listaDetalleAsiento = asientoContable.getDetallesAsiento();
			this.listaModeloDetalle = new ListModelList<DetalleAsientoContable>(
					this.listaDetalleAsiento);
		}
		// this.listaModeloDetalle = new ListModelList<DetalleAsientoContable>(
		// this.listaDetalleAsiento);
		this.lstDetalleAsiento.setModel(listaModeloDetalle);
		CalculaBalance();
	}

	private void CalculaBalance() {
		creditos = new BigDecimal(0);
		debitos = new BigDecimal(0);
		lblCreditos.setStyle("color:black;");
		lblDebitos.setStyle("color:black;");
		for (DetalleAsientoContable detalle : listaModeloDetalle) {
			if (detalle.getTipo_movimiento().indexOf("cr") > -1) {
				creditos = creditos.add(detalle.getMonto());
			} else {
				debitos = debitos.add(detalle.getMonto());
			}
		}

		lblCreditos.setValue(String.format("%.2f", creditos.floatValue()));
		lblDebitos.setValue(String.format("%.2f", debitos.floatValue()));
		if (creditos.compareTo(debitos) == -1) {
			lblCreditos.setStyle("color:red;");
		} else if (debitos.compareTo(creditos) == -1) {
			lblDebitos.setStyle("color:red;");
		} else if (creditos.compareTo(debitos) == 0) {
			lblCreditos.setStyle("color:black;");
			lblDebitos.setStyle("color:black;");
		}
	}

	private void EnableFieldsDetalle() {
		cmbCuenta.setDisabled(false);
		txtDescripcionDetalle.setDisabled(false);
		cmbTipoMovimiento.setDisabled(false);
		dcmbMonto.setDisabled(false);
	}

	private void ClearFieldsDetalle() {
		//detalleAsientoSelected = null;
		listaModeloCuentas.clearSelection();
		txtDescripcionDetalle.setRawValue("");
		cmbTipoMovimiento.setValue("");
		dcmbMonto.setValue("0");
	}

}
