package com.npogulanik.parquimetro.entity;

public class ParamsCredito implements IParams{
	String Tarjeta;
	
	public ParamsCredito(String tarjeta) {
		this.Tarjeta = tarjeta;
	}
	
	public String getTarjeta() {
		return Tarjeta;
	}
	
	public void setTarjeta(String tarjeta) {
		this.Tarjeta = tarjeta;
	}	
}

