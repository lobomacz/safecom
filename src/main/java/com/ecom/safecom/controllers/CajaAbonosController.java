package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.Caja;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContablePK;
import com.ecom.safecom.entity.MovimientoCaja;
import com.ecom.safecom.entity.Venta;
import com.ecom.safecom.services.impl.AsientoContableServiceImpl;
import com.ecom.safecom.services.impl.AutenticacionService;
import com.ecom.safecom.services.impl.CajaServiceImpl;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.PeriodoServiceImpl;
import com.ecom.safecom.services.impl.VentaServiceImpl;

public class CajaAbonosController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ListModelList<Venta> listaModelosVenta;
	private Venta ventaSelected;
	private Caja cajaSelected;
	private AsientoContable asientoContable;
	
	@WireVariable
	private VentaServiceImpl ventaService;
	@WireVariable
	private AsientoContableServiceImpl asientoContableService;
	@WireVariable
	private CajaServiceImpl cajaService;
	@WireVariable
	private PeriodoServiceImpl periodoService;
	@WireVariable
	private AutenticacionService autenService;
	@WireVariable
	private CuentaContableServiceImpl cuentaContableService;
	
	@Wire
	Decimalbox dcmbMonto;
	@Wire
	Label lblIdVenta;
	@Wire
	Label lblFechaVenta;
	@Wire
	Label lblCliente;
	@Wire
	Listbox lstVentas;
	@Wire
	Button btnGuardar;
	@Wire
	Button btnRecibo;
	@Wire
	Window wCajaAbonos;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		if(Executions.getCurrent().hasAttribute("cajaSelected")){
			Caja caja = (Caja)Executions.getCurrent().getAttribute("cajaSelected");
			if(caja != null){
				this.cajaSelected = caja;
			}
		}
		
		LlenaLista();
	}
	
	@Listen("onSelect=#lstVentas")
	public void lstVentas_onSelectListener(){
		if(!listaModelosVenta.isSelectionEmpty()){
			this.ventaSelected = listaModelosVenta.getSelection().iterator().next();
			this.CargaDatos();
		}
	}

	@Listen("onSetAsiento=#wCajaAbonos")
	public void wCajaAbonos_onSetAsientoListener(Event evento){
		if(evento.getData() != null){
			this.asientoContable = (AsientoContable)evento.getData();
		}else{
			this.asientoContable = null;
		}
	}
	
	@Listen("onClick=#btnAsiento")
	public void btnAsiento_onClickListener(){
		Map<String,Object> args = new HashMap<String,Object>();
		AsientoContable asiento = new AsientoContable();
		asiento.setDescripcion("Abono s/venta a credito");
		asiento.setFecha(new Date());
		asiento.setReferencia(ventaSelected.getNumero_factura());
		asiento.setPeriodo(periodoService.getPeriodoAbierto());
		asiento.setUsuario(autenService.getUserCredential());
		asiento.setContabilizado(false);
		List<DetalleAsientoContable> detalleAsiento = new ArrayList<DetalleAsientoContable>();
		DetalleAsientoContable detalle = new DetalleAsientoContable();
		detalle.setIddetalle(new DetalleAsientoContablePK(0, cajaSelected.getCuenta().getCuenta()));
		detalle.setContabilizado(false);
		detalle.setDescripcion("Abono s/venta a credito");
		detalle.setMonto(dcmbMonto.getValue());
		detalle.setTipo_movimiento("db");
		detalleAsiento.add(detalle);
		detalle = new DetalleAsientoContable();
		detalle.setIddetalle(new DetalleAsientoContablePK(0, cuentaContableService.getByName("CLIENTES").getCuenta()));
		detalle.setDescripcion("Abono s/factura #"+ventaSelected.getNumero_factura());
		detalle.setMonto(dcmbMonto.getValue());
		detalle.setContabilizado(false);
		detalle.setTipo_movimiento("cr");
		detalleAsiento.add(detalle);
		asiento.setDetalleAsiento(detalleAsiento);
		args.put("asiento", asiento);
		
		Window wAsiento;
		if(wCajaAbonos.getParent().query("#wAsientoContable")==null){
			wAsiento = (Window)Executions.createComponents("/paginas/asientos.zul", wCajaAbonos.getParent(), args);
			wAsiento.setPosition("center");
			wAsiento.setClosable(true);
			wAsiento.setSizable(false);
		}else{
			wAsiento = (Window)wCajaAbonos.getParent().query("#wAsientoContable");
		}
		
		wAsiento.doModal();
	}
	
	@Listen("onClick=#btnGuardar")
	public void btnGuardar_onClickListener(){
		if(this.asientoContable != null && asientoContableService.recargarAsiento(asientoContable)!=null){
			MovimientoCaja ingreso = new MovimientoCaja();
			ingreso.setCaja(cajaSelected);
			ingreso.setConcepto("Abono s/venta a credito");
			ingreso.setFecha_hora_movimiento(new Date());
			ingreso.setEmpleado(autenService.getUserCredential().getEmpleado());
			ingreso.setReferencia(ventaSelected.getNumero_factura());
			ingreso.setTipo_movimiento("ingreso");
			ingreso.setMonto(dcmbMonto.getValue());
			ingreso.setTipo_pago("efectivo");
			try{
				cajaService.GuardarMovimientoCaja(ingreso);
			}catch(Exception ex){
				Messagebox.show("Ocurrió un problema al guardar el registro. "+ex.getMessage(), "Error de Ejecución", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Messagebox.show("El registro se guardó con éxito.", "Ingreso Registrado", Messagebox.OK, Messagebox.INFORMATION);
			
		}
	}
	
	@Listen("onClick=#btnCancelarVenta")
	public void btnCancelarVenta_onClickListener(){
		ventaSelected = ventaService.recargarVenta(ventaSelected);
		
		if(ventaSelected != null && ventaService.recargarVenta(ventaSelected) != null){
			
			Messagebox.show("La acción cancelará el saldo de la venta. ¿Desea Continuar?", "Cancelar Saldo?", Messagebox.YES | Messagebox.ABORT, Messagebox.QUESTION, new EventListener<Event>() {
				
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(event.getName().equals(Messagebox.YES)){
						ventaSelected.setCancelado(true);
						try{
							ventaService.actualizarVenta(ventaSelected);
						}catch(Exception ex){
							Messagebox.show("Ocurrió un problema al actualizar el registro. "+ex.getMessage(), "Error de Ejecución", Messagebox.OK, Messagebox.ERROR);
							return;
						}
						
						Messagebox.show("El registro se actualizó con éxito.", "Venta Cancelada", Messagebox.OK, Messagebox.INFORMATION);
						
						LlenaLista();
					}else{
						LimpiaDatos();
					}
				}
			});
		}
	}
	
	private void LlenaLista(){
		listaModelosVenta = new ListModelList<Venta>(ventaService.listaTodoCredito());
		lstVentas.setModel(listaModelosVenta);
	}
	
	private void CargaDatos(){
		if(!listaModelosVenta.isSelectionEmpty() && ventaSelected != null){
			LimpiaDatos();
			lblIdVenta.setValue(ventaSelected.getId_venta().toString());
			lblFechaVenta.setValue(ventaSelected.getFecha_venta().toString());
			lblCliente.setValue(ventaSelected.getCliente().getNombre());
		}
	}
	
	private void LimpiaDatos(){
		this.listaModelosVenta.clearSelection();
		this.ventaSelected = null;
		this.asientoContable = null;
		lblIdVenta.setValue("");
		lblFechaVenta.setValue("");
		lblCliente.setValue("");
		dcmbMonto.setValue(BigDecimal.ZERO);
		btnGuardar.setDisabled(true);
		btnRecibo.setDisabled(true);
	}
}
