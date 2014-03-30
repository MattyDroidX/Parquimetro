package com.npogulanik.parquimetro.entity;

public class ParamsEntrada implements IParams{
	String chapa;
	String posta;
	
	public ParamsEntrada(String chapa, String posta) {
		this.chapa = chapa;
		this.posta = posta;
	}
	
	public String getChapa() {
		return chapa;
	}
	public void setChapa(String chapa) {
		this.chapa = chapa;
	}

	public String getPosta() {
		return posta;
	}
	public void setPosta(String posta) {
		this.posta = posta;
	}	
	
}
