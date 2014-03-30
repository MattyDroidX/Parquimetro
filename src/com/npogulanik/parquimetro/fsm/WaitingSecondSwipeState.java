package com.npogulanik.parquimetro.fsm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.DummyChapaResolver;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.TimerCallBack;
import com.npogulanik.parquimetro.TransactionManager;
import com.npogulanik.parquimetro.activities.AnimationActivity;

public class WaitingSecondSwipeState implements State {
    private String barcode;
    //private CountDownTimer countDown;
    
	public WaitingSecondSwipeState(String barcode){
		this.barcode = barcode;
	}

	@Override
	public void doAction() {
		DisplayManager.getInstance().stopFlippingTop();
		DisplayManager.getInstance().stopFlippingBottom();
		DisplayManager.getInstance().setBottomMessage(DisplayManager.getInstance().getContext().getString(R.string.text_swipe_again),Color.BLACK);
		
		String tarjeta = DummyChapaResolver.getInstance().getTarjeta();
		TransactionManager transactionManager = TransactionManager.getInstance();
		transactionManager.performCredito(tarjeta);
		
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final int secondSwipeTiemeout = Integer.parseInt(SP.getString("prefSecondSwipeTimeout", "20"));
		
		InputManager.getInstance().startTimer(secondSwipeTiemeout, new TimerCallBack() {			
			@Override
			public void timeExpired() {
				ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
				parquimetroContext.setState(new IdleState());
			}

			@Override
			public void onTick(long millisUntilFinished) {
				DisplayManager.getInstance().showTimerText(
						String.format("%02d", Integer.valueOf(String
								.valueOf(millisUntilFinished / 1000))));
			}
		});
		
	}
	@Override
	public void doExit() {
		// TODO Auto-generated method stub
		InputManager.getInstance().stopTimer();
	}

}
