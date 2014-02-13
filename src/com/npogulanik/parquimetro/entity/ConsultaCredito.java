package com.npogulanik.parquimetro.entity;

public class ConsultaCredito extends AbstractTransaction {
	String CreditoID;
	String CreditoChapa;
	String CreditoFecha;
	String CreditoImporte;
	String CreditoSaldo;
	String CreditoDV;
	
	public String getCreditoID() {
		return CreditoID;
	}
	public void setCreditoID(String creditoID) {
		CreditoID = creditoID;
	}
	public String getCreditoChapa() {
		return CreditoChapa;
	}
	public void setCreditoChapa(String creditoChapa) {
		CreditoChapa = creditoChapa;
	}
	public String getCreditoFecha() {
		return CreditoFecha;
	}
	public void setCreditoFecha(String creditoFecha) {
		CreditoFecha = creditoFecha;
	}
	public String getCreditoImporte() {
		return CreditoImporte;
	}
	public void setCreditoImporte(String creditoImporte) {
		CreditoImporte = creditoImporte;
	}
	public String getCreditoSaldo() {
		return CreditoSaldo;
	}
	public void setCreditoSaldo(String creditoSaldo) {
		CreditoSaldo = creditoSaldo;
	}
	public String getCreditoDV() {
		return CreditoDV;
	}
	public void setCreditoDV(String creditoDV) {
		CreditoDV = creditoDV;
	}
}
