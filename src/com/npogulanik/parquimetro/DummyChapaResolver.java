package com.npogulanik.parquimetro;

public class DummyChapaResolver implements ChapaResolver {

	@Override
	public String getChapa(String cardNumber) {
		return "EFV883";
	}

}
