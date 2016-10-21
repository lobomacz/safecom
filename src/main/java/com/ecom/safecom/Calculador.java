package com.ecom.safecom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.ecom.safecom.entity.CuentaContable;
import com.ecom.safecom.entity.DetalleAsientoContable;
import com.ecom.safecom.services.impl.AsientoContableServiceImpl;
import com.ecom.safecom.services.impl.ConversionUnidadMedidaServiceImpl;
import com.ecom.safecom.services.impl.CuentaContableServiceImpl;
import com.ecom.safecom.services.impl.UnidadMedidaServiceImpl;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Calculador {
	
	
	@WireVariable
	CuentaContableServiceImpl cuentaContableService;
	@WireVariable
	AsientoContableServiceImpl asientoContableService;
	@WireVariable
	UnidadMedidaServiceImpl unidadMedidaService;
	@WireVariable
	ConversionUnidadMedidaServiceImpl conversionUnidadMedidaService;
	
	
	public Calculador(){}
	
	public HashMap<String, String> CalculaSaldoCuenta(CuentaContable cuenta){
BigDecimal saldo = BigDecimal.ZERO;
		
		List<DetalleAsientoContable> listaMovimientos = asientoContableService.listaPorCuenta(cuenta.getCuenta());
		
		
		
		HashMap<String,String> datosSaldo = new HashMap<String,String>();
		
		if(cuenta.getTipo().equals("activo") || cuenta.getTipo().equals("ingreso")){
			
			for(DetalleAsientoContable detalle:listaMovimientos){
				if(detalle.getTipo_movimiento().equals("db")){
					saldo = saldo.add(detalle.getMonto()).setScale(2, RoundingMode.UP);
				}else{
					saldo = saldo.subtract(detalle.getMonto()).setScale(2, RoundingMode.UP);
				}
			}
			
			if(saldo.compareTo(BigDecimal.ZERO) == -1){
				datosSaldo.put("tiposaldo", "acreedor");
				datosSaldo.put("saldo", saldo.negate().toString());
			}else if(saldo.compareTo(BigDecimal.ZERO) == 1){
				datosSaldo.put("tiposaldo", "deudor");
				datosSaldo.put("saldo", saldo.toString());
			}else{
				datosSaldo.put("tiposaldo", "nulo");
			}
		}else{
			
			for(DetalleAsientoContable detalle:listaMovimientos){
				if(detalle.getTipo_movimiento().equals("cr")){
					saldo = saldo.add(detalle.getMonto()).setScale(2, RoundingMode.UP);
				}else{
					saldo = saldo.subtract(detalle.getMonto()).setScale(2, RoundingMode.UP);
				}
			}
			
			if(saldo.compareTo(BigDecimal.ZERO) == -1){
				datosSaldo.put("tiposaldo", "deudor");
				datosSaldo.put("saldo", saldo.negate().toString());
			}else if(saldo.compareTo(BigDecimal.ZERO) == 1){
				datosSaldo.put("tiposaldo", "acreedor");
				datosSaldo.put("saldo", saldo.toString());
			}else{
				datosSaldo.put("tiposaldo", "nulo");
			}
			
		}
		
		return datosSaldo;
	}

}
