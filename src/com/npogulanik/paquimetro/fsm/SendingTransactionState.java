package com.npogulanik.paquimetro.fsm;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.DummyChapaResolver;

public class SendingTransactionState implements State {
    private String barcode;
	
	public SendingTransactionState(String barcode) {
		// TODO Auto-generated constructor stub
		this.barcode = barcode;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		DisplayManager.getInstance().stopFlippingBottom();
		DisplayManager.getInstance().stopTimer();
		DisplayManager.getInstance().resetSaldo();
		//DisplayManager.getInstance().setTopMessage("Procesando Transaccion");  
		String chapa = new DummyChapaResolver().getChapa(barcode);
		TransactionManager transactionManager = TransactionManager.getInstance();
		transactionManager.sendTransaction(chapa);
		
	}

	@Override
	public void doExit() {
		// TODO Auto-generated method stub
		
	}

}
