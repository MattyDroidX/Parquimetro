package com.npogulanik.paquimetro.fsm;

import android.graphics.Color;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.DummyChapaResolver;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.R;

public class WaitingSecondSwipeState implements State {
    private String barcode;
	
	public WaitingSecondSwipeState(String barcode) {
		this.barcode = barcode;
	}
	@Override
	public void doAction() {
		DisplayManager.getInstance().stopFlippingTop();
		DisplayManager.getInstance().stopFlippingBottom();
		DisplayManager.getInstance().setBottomMessage(DisplayManager.getInstance().getContext().getString(R.string.text_swipe_again),Color.BLACK);
		
		String chapa = new DummyChapaResolver().getChapa(barcode);
		TransactionManager transactionManager = TransactionManager.getInstance();
		transactionManager.performCredito(chapa);
		
	}
	@Override
	public void doExit() {
		// TODO Auto-generated method stub	
	}

}
