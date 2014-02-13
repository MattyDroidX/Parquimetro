package com.npogulanik.paquimetro.fsm;

import android.graphics.Color;
import android.os.CountDownTimer;

import com.npogulanik.parquimetro.BarCodeHolder;
import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InputManager;

public class OnSuccessState implements State {
    private String message;
    private String saldo;
	
	
	public OnSuccessState(String message,String saldo) {
		this.message = message;
		this.saldo = saldo;
	}


	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		InputManager.getInstance().disableInput();
		DisplayManager.getInstance().setTopMessage(this.message,Color.GREEN);
		DisplayManager.getInstance().showSaldoText(this.saldo);
		CountDownTimer countDown = new CountDownTimer(5000, 1000) {
		     public void onTick(long millisUntilFinished) {
		    	 //DisplayManager.getInstance().showTimerText(String.format("%02d", Integer.valueOf(String.valueOf(millisUntilFinished / 1000))));
		     }
		     public void onFinish() {
		    	 BarCodeHolder.reset();
		    	 ParquimetroContext context = ParquimetroContext.getInstance();
	        	 context.setState(new IdleState());
		     }
		  }.start();
	}


	@Override
	public void doExit() {
		// TODO Auto-generated method stub
		
	}

}
