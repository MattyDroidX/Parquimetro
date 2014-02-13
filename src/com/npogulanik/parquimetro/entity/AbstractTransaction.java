package com.npogulanik.parquimetro.entity;

public abstract class AbstractTransaction {
	private String returnMessage;

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	
}
