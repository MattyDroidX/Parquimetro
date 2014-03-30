package com.npogulanik.parquimetro;

public enum TransactionType {
	CONSULTA_CREDITO("http://190.224.102.100:8080/EstacionamientoV4/rest/VerSaldoWS"),
	ENTRADA("http://190.224.102.100:8080/EstacionamientoV4/rest/EntradaWSdesa"),
	SALIDA("http://190.224.102.100:8080/EstacionamientoV4/rest/SalidaWSdesa");
	
	private String url;
	
	private TransactionType(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return this.url;
	}
}
