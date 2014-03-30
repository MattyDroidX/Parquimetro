package com.npogulanik.parquimetro.entity;

public class EntradaNueva implements IResult{
	private String Error;
	private String HoraMax;
	
	public String getError() {
		return Error;
	}
	public void setError(String error) {
		Error = error;
	}
	public String getHoraMax() {
		return HoraMax;
	}
	public void setHoraMax(String horaMax) {
		HoraMax = horaMax;
	}
}
