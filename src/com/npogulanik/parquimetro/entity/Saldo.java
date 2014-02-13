package com.npogulanik.parquimetro.entity;

public class Saldo implements IResult {
	private String saldo;

	public Saldo(String saldo) {
		this.saldo = saldo;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	
}
