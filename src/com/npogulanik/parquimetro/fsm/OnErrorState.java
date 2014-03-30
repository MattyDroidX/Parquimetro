package com.npogulanik.parquimetro.fsm;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;

import com.npogulanik.parquimetro.BarCodeHolder;
import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InputManager;

public class OnErrorState implements State {
	private String message;
	
	public OnErrorState(String message) {
		this.message = message;
	}
	
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		InputManager.getInstance().disableInput();
		DisplayManager.getInstance().setTopMessage(this.message,Color.RED);
		DisplayManager.getInstance().stopTimerText();
		DisplayManager.getInstance().setBottomMessage("", Color.BLACK);
		
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final int resultsTiemeout = Integer.parseInt(SP.getString("prefResultsTimeout", "5"));
		
		CountDownTimer countDown = new CountDownTimer(resultsTiemeout * 1000, 1000) {
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
