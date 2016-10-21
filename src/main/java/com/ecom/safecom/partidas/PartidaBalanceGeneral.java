package com.ecom.safecom.partidas;

import java.math.BigDecimal;

public class PartidaBalanceGeneral {

	private String cuenta;
	
	private BigDecimal debe;
	
	private BigDecimal haber;
	
	public PartidaBalanceGeneral(){}
	
	public String getCuenta(){
		return this.cuenta;
	}
	
	public void setCuenta(String cuenta){
		this.cuenta = cuenta;
	}
	
	public BigDecimal getDebe(){
		return this.debe;
	}
	
	public void setDebe(BigDecimal debe){
		this.debe = debe;
	}
	
	public BigDecimal getHaber(){
		return this.haber;
	}
	
	public void setHaber(BigDecimal haber){
		this.haber = haber;
	}
}
