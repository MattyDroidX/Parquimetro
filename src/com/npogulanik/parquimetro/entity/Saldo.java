package com.npogulanik.parquimetro.entity;

public class Saldo implements IResult {
	private String Saldo;

	public Saldo(String saldo) {
		this.Saldo = saldo;
	}

	public String getSaldo() {
		return Saldo;
	}

	public void setSaldo(String saldo) {
		this.Saldo = saldo;
	}
	
}
