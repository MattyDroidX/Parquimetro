package com.npogulanik.parquimetro.entity;

import java.util.List;

public class SaldoNuevo implements IResult {
	private List<Response> Asociado;
	
	public List<Response> getAsociado() {
		return Asociado;
	}
	
	public void setAsociado(List<Response> asociado) {
		this.Asociado = asociado;
	}
	
	public static class Response {
		String Chapa;
		String Saldo;
		
		public String getChapa() {
			return Chapa;
		}
		public void setChapa(String chapa) {
			Chapa = chapa;
		}
		public String getSaldo() {
			return Saldo;
		}
		public void setSaldo(String saldo) {
			Saldo = saldo;
		}
	}
}
