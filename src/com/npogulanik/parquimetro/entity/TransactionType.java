package com.npogulanik.parquimetro.entity;

public enum TransactionType {
	CONSULTA_CREDITO("http://190.224.102.100:8080/EstacionamientoV4/rest/WorkWithDevicesEMCredito_EMCredito_List_Grid?CreditoChapa=%s&fmt=json&count=1&gxid=9"),
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
