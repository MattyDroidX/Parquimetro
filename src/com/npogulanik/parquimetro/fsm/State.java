package com.npogulanik.parquimetro.fsm;

public interface State {
	public void doAction();
	public void doExit();
}
