package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.ArqueoCaja;
import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.Caja;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.Empleado;
import com.ecom.safecom.entity.MovimientoCaja;
import com.ecom.safecom.entity.Usuario;
import com.ecom.safecom.services.impl.ArqueoCajaServiceImpl;
import com.ecom.safecom.services.impl.AsientoContableServiceImpl;
import com.ecom.safecom.services.impl.AutenticacionService;
import com.ecom.safecom.services.impl.CajaServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ArqueoController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	CajaServiceImpl cajaService;

	@WireVariable
	AsientoContableServiceImpl asientoContableService;

	@WireVariable
	AutenticacionService autenService;

	@WireVariable
	ArqueoCajaServiceImpl arqueoCajaService;

	@Wire
	Window wArqueo;
	@Wire
	Combobox cmbCaja;
	@Wire
	Datebox dtbFecha;
	@Wire
	Textbox txtEmpleado;
	@Wire
	Button btnAceptar;
	@Wire
	Button btnValidar;
	@Wire
	Button btnAsiento;
	@Wire
	Intbox intb500;
	@Wire
	Decimalbox dcmb500;
	@Wire
	Intbox intb200;
	@Wire
	Decimalbox dcmb200;
	@Wire
	Intbox intb100;
	@Wire
	Decimalbox dcmb100;
	@Wire
	Intbox intb50;
	@Wire
	Decimalbox dcmb50;
	@Wire
	Intbox intb20;
	@Wire
	Decimalbox dcmb20;
	@Wire
	Intbox intb10;
	@Wire
	Decimalbox dcmb10;
	@Wire
	Intbox intb5;
	@Wire
	Decimalbox dcmb5;
	@Wire
	Intbox intb1;
	@Wire
	Decimalbox dcmb1;
	@Wire
	Intbox intb050;
	@Wire
	Decimalbox dcmb050;
	@Wire
	Intbox intb025;
	@Wire
	Decimalbox dcmb025;
	@Wire
	Intbox intb010;
	@Wire
	Decimalbox dcmb010;
	@Wire
	Intbox intb005;
	@Wire
	Decimalbox dcmb005;
	@Wire
	Intbox intb001;
	@Wire
	Decimalbox dcmb001;
	@Wire
	Decimalbox dcmbEfectivoArqueo;
	@Wire
	Decimalbox dcmbEfectivoTeorico;
	@Wire
	Decimalbox dcmbEfectivoDiferencia;
	@Wire
	Decimalbox dcmbChequesArqueo;
	@Wire
	Decimalbox dcmbChequesTeorico;
	@Wire
	Decimalbox dcmbChequesDiferencia;
	@Wire
	Decimalbox dcmbVouchersArqueo;
	@Wire
	Decimalbox dcmbVouchersTeorico;
	@Wire
	Decimalbox dcmbVouchersDiferencia;
	@Wire
	Decimalbox dcmbTotalArqueo;
	@Wire
	Decimalbox dcmbTotalTeorico;
	@Wire
	Decimalbox dcmbTotalDiferencia;

	private ListModelList<Caja> listaModeloCaja;
	private Caja cajaSelected;
	private Empleado empleado;
	private Empleado supervisor;
	private ArqueoCaja nuevoArqueo;
	private ArqueoCaja ultimoArqueo;
	private List<MovimientoCaja> listaMovimientos;
	private AsientoContable asientoAjuste;
	private BigDecimal efectivoTeorico;
	private BigDecimal chequesTeorico;
	private BigDecimal vouchersTeorico;
	private BigDecimal totalTeorico;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

		listaModeloCaja = new ListModelList<Caja>(cajaService.ListaCajas());
		cmbCaja.setModel(listaModeloCaja);
		dtbFecha.setValue(new Date());
		empleado = autenService.getUserCredential().getEmpleado();
		txtEmpleado.setValue(empleado.toString());
		nuevoArqueo = null;
		ultimoArqueo = null;
		asientoAjuste = null;
		efectivoTeorico = BigDecimal.ZERO;
		chequesTeorico = BigDecimal.ZERO;
		vouchersTeorico = BigDecimal.ZERO;
		totalTeorico = BigDecimal.ZERO;
	}
	
	@Listen("onSetAsiento=#wArqueo")
	public void wArqueo_onSetAsientoListener(Event evento){
		if(evento.getData()!=null){
			asientoAjuste = (AsientoContable)evento.getData();
		}
	}

	@Listen("onSetSupervisor=#wArqueo")
	public void wArqueo_onSetSupervisorListener(Event evento) {
		if (evento.getData() != null) {
			supervisor = ((Usuario) evento.getData()).getEmpleado();
			nuevoArqueo = new ArqueoCaja();
			nuevoArqueo.setCaja(cajaSelected);
			nuevoArqueo.setEmpleado(empleado);
			nuevoArqueo.setSupervisor(supervisor);

			if (ultimoArqueo == null) {
				nuevoArqueo.setTipo_arqueo("apertura");
			} else if(ultimoArqueo.getTipo_arqueo().equals("apertura")){
				nuevoArqueo.setTipo_arqueo("cierre");
			} else {
				nuevoArqueo.setTipo_arqueo("apertura");
			}

			btnValidar.setDisabled(true);
			btnAceptar.setDisabled(false);
		} else {
			supervisor = null;
		}
	}

	@Listen("onSelect=#cmbCaja")
	public void cmbCaja_onSelectListener() {
		cajaSelected = listaModeloCaja.getSelection().iterator().next();
		ultimoArqueo = arqueoCajaService
				.ultimoArqueo(cajaSelected.getId_caja());

		CalculaTeorico();
	}

	@Listen("onClick=#btnAceptar")
	public void btnGuardar_onClickListener() {

		if ((dcmbTotalDiferencia.getValue().compareTo(BigDecimal.ZERO) != 0) && asientoAjuste == null) {
			Messagebox.show("Existen diferencias y no se ha generado un asiento contable.", "Error de Balance", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		if (nuevoArqueo != null && nuevoArqueo.getSupervisor() != null) {
			Messagebox
					.show("Verifique si los datos son correctos para aplicar el arqueo de caja.",
							"Verificar montos", Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION, new EventListener<Event>() {

								public void onEvent(Event event)
										throws Exception {
									// TODO Auto-generated method stub
									if (event.getName().equals(
											Messagebox.ON_YES)) {
										nuevoArqueo.setBilletes_500(intb500
												.getValue());
										nuevoArqueo.setBilletes_200(intb200
												.getValue());
										nuevoArqueo.setBilletes_100(intb100
												.getValue());
										nuevoArqueo.setBilletes_50(intb50
												.getValue());
										nuevoArqueo.setBilletes_20(intb20
												.getValue());
										nuevoArqueo.setBilletes_10(intb10
												.getValue());
										nuevoArqueo.setMonedas_5(intb5
												.getValue());
										nuevoArqueo.setMonedas_1(intb1
												.getValue());
										nuevoArqueo
												.setMonedas_50_centavos(intb050
														.getValue());
										nuevoArqueo
												.setMonedas_25_centavos(intb025
														.getValue());
										nuevoArqueo
												.setMonedas_10_centavos(intb010
														.getValue());
										nuevoArqueo
												.setMonedas_5_centavos(intb005
														.getValue());
										nuevoArqueo
												.setMonedas_1_centavo(intb001
														.getValue());
										nuevoArqueo
												.setEfectivo(dcmbEfectivoArqueo
														.getValue());
										nuevoArqueo
												.setCheques(dcmbChequesArqueo
														.getValue());
										nuevoArqueo
												.setVouchers(dcmbVouchersArqueo
														.getValue());
										nuevoArqueo
												.setTotal_arqueo(dcmbTotalArqueo
														.getValue());
										nuevoArqueo.setMonto_libros(totalTeorico);
										nuevoArqueo.setFecha_arqueo(dtbFecha
												.getValue());
										nuevoArqueo.setHora_arqueo(new Date());
										try {
											arqueoCajaService
													.guardarArqueo(nuevoArqueo);
											Messagebox
													.show("Los datos se registraron con éxito.",
															"Registro Guardado",
															Messagebox.OK,
															Messagebox.EXCLAMATION);
											btnAceptar.setDisabled(true);
											if (nuevoArqueo.getTipo_arqueo()
													.equals("apertura")) {
												cajaSelected.setAbierta(true);
												cajaService
														.ActualizarCaja(cajaSelected);
											}else{
												cajaSelected.setAbierta(false);
												cajaService
														.ActualizarCaja(cajaSelected);
											}
											DisableAll();
										} catch (Exception ex) {
											Messagebox
													.show("Se encontró un problema al registrar el arqueo.",
															"Error!",
															Messagebox.OK,
															Messagebox.ERROR);
										}
									} else {

									}

									event.stopPropagation();
								}
							});
		}
	}

	@Listen("onClick=#btnValidar")
	public void btnValidar_onClickListener() {

		if (nuevoArqueo == null) {
			Window autenUser = (Window) wArqueo.getParent()
					.query("#wAutenUser");
			if (autenUser == null) {
				autenUser = (Window) Executions.getCurrent().createComponents(
						"/dialogs/auten_user.zul", wArqueo.getParent(), null);
				autenUser.setClosable(true);
				autenUser.setSizable(false);
				autenUser.setPosition("center");
			}
			autenUser.doModal();
		} else {

			Messagebox.show("El arqueo ya fué validado.", "Operación Inválida",
					Messagebox.OK, Messagebox.EXCLAMATION);

		}

	}

	@Listen("onClick=#btnAsiento")
	public void btnAsiento_onClickListener() {

	}

	@Listen("onChanging=intbox")
	public void Intbox_onChangingListener(InputEvent event) {
		try {
			if (event.getValue() != null) {
				String _id = event.getTarget().getId();
				String nombre = "#" + _id.replace("intb", "dcmb");
				String valor = _id.substring(4);

				if (valor.toCharArray()[0] == '0') {
					valor = valor.substring(0, 1) + '.' + valor.substring(1);
				}

				BigDecimal multiplo = new BigDecimal(valor);
				Decimalbox dcmb = (Decimalbox) Selectors
						.find(this.getSelf(), nombre).iterator().next();
				dcmb.setValue(new BigDecimal(event.getValue()).multiply(
						multiplo).setScale(2, RoundingMode.UP));
				CalculaEfectivo();

				CalculaTotal();
				dcmbEfectivoDiferencia.setValue(dcmbEfectivoArqueo.getValue()
						.subtract(dcmbEfectivoTeorico.getValue()));
			}
		} catch (NumberFormatException ex) {

		}

	}

	@Listen("onChange=#dcmbEfectivoArqueo")
	public void dcmbEfectivoArqueo_onChangeListener() {
		System.out.print("Puede seguir");
		CalculaTotal();
		dcmbEfectivoDiferencia.setValue(dcmbEfectivoArqueo.getValue().subtract(
				dcmbEfectivoTeorico.getValue()));

	}

	@Listen("onChange=#dcmbChequesArqueo")
	public void dcmbChequesArqueo_onChangeListener() {
		dcmbChequesDiferencia.setValue(dcmbChequesArqueo.getValue().subtract(
				dcmbChequesTeorico.getValue()));
		CalculaTotal();
	}

	@Listen("onChange=#dcmbVouchersArqueo")
	public void dcmbVouchersArqueo_onChangeListener() {
		dcmbVouchersDiferencia.setValue(dcmbVouchersArqueo.getValue().subtract(
				dcmbVouchersTeorico.getValue()));
		CalculaTotal();
	}

	@Listen("onChange=#dcmbTotalArqueo")
	public void dcmbTotalArqueo_onChangeListener() {
		dcmbTotalDiferencia.setValue(dcmbTotalArqueo.getValue().subtract(
				dcmbTotalTeorico.getValue()));
	}

	private void CalculaTotal() {
		dcmbTotalArqueo.setValue(dcmbEfectivoArqueo.getValue()
				.add(dcmbChequesArqueo.getValue())
				.add(dcmbVouchersArqueo.getValue())
				.setScale(2, RoundingMode.UP));
		dcmbTotalDiferencia.setValue(dcmbTotalArqueo.getValue()
				.subtract(dcmbTotalTeorico.getValue())
				.setScale(2, RoundingMode.UP));
		if (dcmbTotalDiferencia.getValue().compareTo(BigDecimal.ZERO) != 0) {
			btnAsiento.setDisabled(false);
		}else{
			btnAsiento.setDisabled(true);
		}
	}
	
	// TODO: REVISAR BIEN LA REFERENCIA DEL TOTAL TEÓRICO PARA EL NUEVO ARQUEO

	private void CalculaTeorico() {
		
		/*
		List<DetalleAsientoContable> listaMovimientos = asientoContableService.listaPorCuenta(cajaSelected.getCuenta().getCuenta());

		//TODO: MEJORAR LA BÚSQUEDA DE LAS DESCRIPCIONES CON EXPRESIONES REGULARES
		
		for(DetalleAsientoContable detalle : listaMovimientos){
			if(detalle.getDescripcion().indexOf("EFECTIVO")>=0 || detalle.getDescripcion().indexOf("APERTURA")>=0){
				System.out.print("Si encontro la descripcion");
				if(detalle.getTipo_movimiento().equals("db")){
					efectivoTeorico.add(detalle.getMonto()).setScale(2, RoundingMode.UP); 
				}else{
					efectivoTeorico.subtract(detalle.getMonto()).setScale(2, RoundingMode.UP);
				}
			}else if(detalle.getDescripcion().indexOf("CHEQUE")>=0){
				if(detalle.getTipo_movimiento().equals("db")){
					chequesTeorico.add(detalle.getMonto()).setScale(2, RoundingMode.UP);
				}else{
					chequesTeorico.subtract(detalle.getMonto()).setScale(2, RoundingMode.UP);
				}
			}else{
				if(detalle.getTipo_movimiento().equals("db")){
					vouchersTeorico.add(detalle.getMonto()).setScale(2, RoundingMode.UP);
				}else{
					vouchersTeorico.subtract(detalle.getMonto()).setScale(2, RoundingMode.UP);
				}
			}
		}
		*/
		
		
		if (ultimoArqueo != null) {
			if (ultimoArqueo.getTipo_arqueo().equals("apertura")) {
				Date hoy = new Date();

				listaMovimientos = cajaService.ListaMovimientosPorFecha(
						cajaSelected.getId_caja(),
						ultimoArqueo.getFecha_arqueo(), hoy);

				for (MovimientoCaja movimiento : listaMovimientos) {
					if (movimiento.getTipo_movimiento().equals("ingreso")) {
						if (movimiento.getTipo_pago().equals("efectivo")) {
							efectivoTeorico = efectivoTeorico.add(movimiento
									.getMonto());
						} else if (movimiento.getTipo_pago().equals("tarjeta")) {
							vouchersTeorico = vouchersTeorico.add(movimiento
									.getMonto());
						} else {
							chequesTeorico = chequesTeorico.add(movimiento
									.getMonto());
						}
					}
				}

			} else {
				efectivoTeorico = ultimoArqueo.getEfectivo();
				chequesTeorico = ultimoArqueo.getCheques();
				vouchersTeorico = ultimoArqueo.getVouchers();
				// totalTeorico = ultimoArqueo.getTotal_arqueo();
			}

			// totalTeorico =
			// ultimoArqueo.getTotal_arqueo().add(efectivoTeorico).add(chequesTeorico).add(vouchersTeorico);

		} else {
			listaMovimientos = cajaService.ListaMovimientos(cajaSelected.getId_caja());
			efectivoTeorico = listaMovimientos.get(0).getMonto();
		}
		
		// totalTeorico =
		// ultimoArqueo.getTotal_arqueo().add(efectivoTeorico).add(chequesTeorico).add(vouchersTeorico);

		/*
		 * if(cajaSelected.getSaldo().compareTo(totalTeorico)>0){ totalTeorico =
		 * cajaSelected.getSaldo(); }
		 */

		totalTeorico = efectivoTeorico.add(chequesTeorico).add(vouchersTeorico); //cajaSelected.getSaldo();

		dcmbEfectivoTeorico.setValue(efectivoTeorico);
		dcmbChequesTeorico.setValue(chequesTeorico);
		dcmbVouchersTeorico.setValue(vouchersTeorico);
		dcmbTotalTeorico.setValue(totalTeorico);
	}

	private void CalculaEfectivo() {
		Grid gridDetalle = (Grid) Selectors
				.iterable(wArqueo, "#grdDetalleEfectivo").iterator().next();
		// Panel panelDetalle = (Panel)getPage()

		BigDecimal suma = BigDecimal.ZERO;
		for (Component fila : gridDetalle.getRows().getChildren()) {

			Decimalbox campo = (Decimalbox) fila.query("decimalbox");

			suma = suma.add(campo.getValue()).setScale(2, RoundingMode.UP);

		}
		dcmbEfectivoArqueo.setValue(suma);
	}
	
	private void DisableAll(){
		List<Component> componentes = Selectors.find(wArqueo, "intbox");
		for(Component componente : componentes){
			((Intbox)componente).setDisabled(true);
		}
		
		componentes = Selectors.find(wArqueo, "decimalbox");
		for(Component componente : componentes){
			((Decimalbox)componente).setDisabled(true);
		}
		
		cmbCaja.setDisabled(true);
		dtbFecha.setDisabled(true);
		
		btnValidar.setDisabled(true);
		btnAceptar.setDisabled(true);
		btnAsiento.setDisabled(true);
	}

}
