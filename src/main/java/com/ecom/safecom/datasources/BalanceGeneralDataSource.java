package com.ecom.safecom.datasources;

import java.util.List;

import com.ecom.safecom.partidas.PartidaBalanceGeneral;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class BalanceGeneralDataSource implements JRDataSource {
	
	private List<PartidaBalanceGeneral> datos;
	
	private PartidaBalanceGeneral partida;
	
	public BalanceGeneralDataSource(List<PartidaBalanceGeneral> datos){
		this.datos = datos;
		partida = null;
	}

	public Object getFieldValue(JRField campo) throws JRException {
		
		Object valor = null;
		
		String nombrecampo = campo.getName();
		
		if("cuenta".equals(nombrecampo)){
			valor = partida.getCuenta();
		}else if ("debe".equals(nombrecampo)){
			valor = partida.getDebe();
		}else if("haber".equals(nombrecampo)){
			valor = partida.getHaber();
		}
		
		return valor;
	}

	public boolean next() throws JRException {
		boolean hasnext = datos.iterator().hasNext();
		if(hasnext){
			partida = datos.iterator().next();
		}
		return hasnext;
	}

}
