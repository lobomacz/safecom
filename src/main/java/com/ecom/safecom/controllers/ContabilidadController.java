package com.ecom.safecom.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.zkoss.web.servlet.dsp.action.Remove;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Vbox;

import com.ecom.safecom.entity.AsientoContable;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.entity.Periodo;
import com.ecom.safecom.services.impl.AsientoContableServiceImpl;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.PeriodoServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ContabilidadController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	@WireVariable
	CuentaContableServiceImpl cuentaContableService;

	@WireVariable
	PeriodoServiceImpl periodoService;
	
	@WireVariable
	AsientoContableServiceImpl asientoContableService;
	
	@Wire
	Combobox cmbPeriodos;
	@Wire
	Button btnContaPeriodos;
	@Wire
	Datebox dtbFechaInicio;
	@Wire
	Datebox dtbFechaFinal;
	@Wire
	Button btnContaFechas;
	@Wire
	Progressmeter prgProgress;
	@Wire
	Label lblProgress;
	@Wire
	Vbox vbDatosProgreso;
	
	
	List<Periodo> listaPeriodos;
	ListModelList<Periodo> listaModeloPeriodos;
	
	private Integer cantidadAsientos;
	private Integer conteo;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		this.listaPeriodos = periodoService.getListaPeriodosAbiertos();
		this.listaModeloPeriodos = new ListModelList<Periodo>(this.listaPeriodos);
		cmbPeriodos.setModel(listaModeloPeriodos);
		
		this.cantidadAsientos = 0;
		this.conteo = 0;
	}
	
	@Listen("onClick=#btnContaPeriodos")
	public void ContabilizarPeriodoAbierto(){
		if(!listaModeloPeriodos.isSelectionEmpty()){
			Boolean cuadra = true;
			Periodo periodo = listaModeloPeriodos.getSelection().iterator().next();
			List<AsientoContable> asientos = asientoContableService.listaPorPeriodo(periodo.getId());
			
			if(asientos != null)
			{
				Messagebox.show("No existen asientos para el período seleccionado.");
				return;
			}
			
			if(asientos.size() > 0){
				
				cuadra = RevisaSaldos(asientos);
				
				if(cuadra){
					CreaEtiquetaProgreso();
					Label etiqueta = (Label)Selectors.find(vbDatosProgreso, "#lblProgreso").get(0);
					Progressmeter progreso = (Progressmeter)Selectors.find(vbDatosProgreso, "#prgProgreso").get(0);
					this.cantidadAsientos = asientos.size();
					
					for(AsientoContable asiento : asientos){
						this.conteo++;
						String mens_avance = String.format("Contabilizando %d asiento de %d", conteo,cantidadAsientos);
						int avance = (int)(conteo/cantidadAsientos)*100;
						etiqueta.setValue(mens_avance);
						progreso.setValue(avance);
						try{
							asiento.setContabilizado(true);
							asientoContableService.actualizarAsiento(asiento);
						}catch(Exception ex){
							Messagebox.show("Ocurrió un error al contabilizar asientos. "+ex.getMessage(), "Error!", Messagebox.OK, Messagebox.ERROR);
							return;
						}
					}
					
					RemueveEtiquetaProgreso();
					Messagebox.show(String.format("Se han contabilizado %d asientos correspondientes al período %s.", this.cantidadAsientos, periodo.getId()), "Período Contabilizado", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Messagebox.show("Se encontraron errores de partida doble en los asientos del período. Por favor revise y vuelva a intentarlo.", "Error de Sumatoria", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
			}
		}else{
			Messagebox.show("No se ha seleccionado un período contable. Verifique y vuelva a intentarlo.");
		}
	}
	
	@Listen("onClick=#btnContaFechas")
	public void ContabilizarFechas(){
		
	}
	
	private Boolean RevisaSaldos(List<AsientoContable> asientos){
		CreaEtiquetaProgreso();
		Label etiqueta = (Label)Selectors.find(vbDatosProgreso, "#lblProgreso").get(0);
		Progressmeter progreso = (Progressmeter)Selectors.find(vbDatosProgreso, "#prgProgreso").get(0);
		this.cantidadAsientos = asientos.size();
		this.conteo = 0;
		Boolean cuadra = true;
		
		for(AsientoContable asiento : asientos){
			this.conteo++;
			String mens_avance = String.format("Revisando %d asiento de %d", conteo,cantidadAsientos);
			int avance = (int)(conteo/cantidadAsientos)*100;
			etiqueta.setValue(mens_avance);
			progreso.setValue(avance);
			BigDecimal creditos = new BigDecimal(0);
			BigDecimal debitos = new BigDecimal(0);
			
			for(DetalleAsientoContable detalle : asiento.getDetallesAsiento()){
				if(detalle.getTipo_movimiento() == "cr"){
					creditos = creditos.add(detalle.getMonto());
				}else{
					debitos = debitos.add(detalle.getMonto());
				}
			}
			
			if(creditos.compareTo(debitos) != 0){
				RemueveEtiquetaProgreso();
				cuadra = false;
				return cuadra;
			}
		}
		RemueveEtiquetaProgreso();
		return cuadra;
	}
	
	private void CreaEtiquetaProgreso(){
		Executions.createComponentsDirectly("<label id='lblProgreso'>", "zul", vbDatosProgreso, null);
		Executions.createComponentsDirectly("<progressmeter id='prgProgreso' value='0' hflex='1'>", "zul", vbDatosProgreso, null);
	}
	
	private void RemueveEtiquetaProgreso(){
		vbDatosProgreso.removeChild(Selectors.find(vbDatosProgreso, "#lblProgreso").get(0));
		vbDatosProgreso.removeChild(Selectors.find(vbDatosProgreso, "#prgProgreso").get(0));
	}
}
