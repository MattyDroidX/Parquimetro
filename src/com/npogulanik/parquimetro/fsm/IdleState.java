package com.npogulanik.parquimetro.fsm;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;

import com.npogulanik.parquimetro.BarCodeHolder;
import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.TimerCallBack;

public class IdleState implements State {
	//CountDownTimer countDown;
	
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		InputManager.getInstance().enableInput();
		DisplayManager.getInstance().stopFlippingTop();
		DisplayManager.getInstance().stopTimerText();
		DisplayManager.getInstance().resetSaldo();
		DisplayManager.getInstance().setBottomMessage(DisplayManager.getInstance().getContext().getString(R.string.text_swipe),Color.BLACK);
		BarCodeHolder.reset();
		
		//verifico si tengo que mostrar el protector de pantalla
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final boolean screenSaverEnabled = SP.getBoolean("prefScreenSaverEnabled", true);
		if (screenSaverEnabled){
			final int screenSaverTimeout = Integer.parseInt(SP.getString("prefScreenSaverTime", "20"));	
			InputManager.getInstance().startTimer(screenSaverTimeout, new TimerCallBack() {			
				@Override
				public void timeExpired() {
					ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
					parquimetroContext.setState(new ScreenSaverState());
				}
	
				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
				} 
			});
		}
	}

	@Override
	public void doExit() {
		InputManager.getInstance().stopTimer();
	}
}
