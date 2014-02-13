
package com.npogulanik.parquimetro.entity;

public class ParamsSalida implements IParams {
	String chapa;
	String linea;
	
	public ParamsSalida(String chapa, String linea) {
		this.chapa = chapa;
		this.linea = linea;
	}
	
	public String getChapa() {
		return chapa;
	}
	public void setChapa(String chapa) {
		this.chapa = chapa;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
}
