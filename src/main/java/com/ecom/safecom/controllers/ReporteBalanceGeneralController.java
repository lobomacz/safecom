package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.ecom.safecom.datasources.BalanceGeneralDataSource;
import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.CuentaContable;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.Periodo;
import com.ecom.safecom.partidas.PartidaBalanceGeneral;
import com.ecom.safecom.services.impl.AsientoContableServiceImpl;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.EjercicioServiceImpl;
import com.ecom.safecom.services.impl.PeriodoServiceImpl;

public class ReporteBalanceGeneralController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@WireVariable
	private PeriodoServiceImpl periodoService;
	@WireVariable
	private EjercicioServiceImpl ejercicioService;
	@WireVariable
	private CuentaContableServiceImpl cuentaContableService;
	@WireVariable
	private AsientoContableServiceImpl asientoContableService;
	
	private List<PartidaBalanceGeneral> listaPartidas;
	private BalanceGeneralDataSource dsBalanceGeneral;
	private ListModelList<Periodo> listaModeloPeriodos;
	
	@Wire
	private Listbox lstPeriodos;
	@Wire
	private Combobox cmbFormato;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		listaPartidas = new ArrayList<PartidaBalanceGeneral>();
		
		listaModeloPeriodos = new ListModelList<Periodo>(ejercicioService.getEjercicioActivo().getPeriodos());
		lstPeriodos.setModel(listaModeloPeriodos);
	}
	
	
	
	
	private void PrepararReporte(){
		Periodo periodo = listaModeloPeriodos.getSelection().iterator().next();
		BigDecimal saldoDebe = BigDecimal.ZERO;
		BigDecimal saldoHaber = BigDecimal.ZERO;
		this.listaPartidas = new ArrayList<PartidaBalanceGeneral>();
		List<CuentaContable> listaCuentasPadre = new ArrayList<CuentaContable>();
		List<DetalleAsientoContable> detallesSaldo = asientoContableService.listaAntesDeFecha(periodo.getFecha_inicio());
		List<AsientoContable> listaAsientos = asientoContableService.listaPorPeriodo(periodo.getId());
		
		//Calculamos el saldo al inicio del período
		if(detallesSaldo != null){
			for(DetalleAsientoContable detalle:detallesSaldo){
				CuentaContable cuenta = cuentaContableService.getCuentaContable(detalle.getIddetalle().getCuenta());
				
				if(cuenta.getTipo().equals("activo")){
					if(detalle.getTipo_movimiento().equals("db")){
						saldoDebe = saldoDebe.add(detalle.getMonto());
					}else{
						saldoDebe = saldoDebe.subtract(detalle.getMonto());
					}
				}else if(cuenta.getTipo().equals("pasivo")){
					if(detalle.getTipo_movimiento().equals("cr")){
						saldoHaber = saldoHaber.add(detalle.getMonto());
					}else{
						saldoHaber = saldoHaber.subtract(detalle.getMonto());
					}
					
				}else if(cuenta.getTipo().equals("capital")){
					if(detalle.getTipo_movimiento().equals("cr")){
						saldoHaber = saldoHaber.add(detalle.getMonto());
					}else{
						saldoHaber = saldoHaber.subtract(detalle.getMonto());
					}
				}
			}
		}
		
		//Ahora acomodamos las cuentas para crear el dataset
		
		
	}
	
	private int IndicePartida(String nombre){
		int indice = -1;
		
		for(PartidaBalanceGeneral partida:listaPartidas){
			if(partida.getCuenta().equals(nombre)){
				indice = listaPartidas.indexOf(partida);
			}
		}
		
		return indice;
	}
}