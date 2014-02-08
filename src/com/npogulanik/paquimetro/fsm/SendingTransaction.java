package com.npogulanik.paquimetro.fsm;

import com.npogulanik.parquimetro.DisplayManager;

public class SendingTransaction implements State {

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		DisplayManager.getInstance().StopFlippingBottom();
		DisplayManager.getInstance().setTopMessage("Procesando Transaccion");  
	}

}
