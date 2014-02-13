package com.npogulanik.parquimetro.entity;

public class ParamsEntrada implements IParams{
	String chapa;
	String linea;
	String posta;
	
	public ParamsEntrada(String chapa, String linea, String posta) {
		this.chapa = chapa;
		this.linea = linea;
		this.posta = posta;
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
	public String getPosta() {
		return posta;
	}
	public void setPosta(String posta) {
		this.posta = posta;
	}	
	
}
