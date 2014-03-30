package com.npogulanik.parquimetro.fsm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;

import com.npogulanik.parquimetro.BarCodeHolder;
import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.activities.PreferencesActivity;

public class OnPreferencesState implements State {
	private static final int RESULT_SETTINGS = 1;
	private String message;
	
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		InputManager.getInstance().disableInput();
		//DisplayManager.getInstance().setTopMessage(this.message,Color.RED);
		DisplayManager.getInstance().stopTimerText();
		//InputManager.getInstance().stopTimer();
		//DisplayManager.getInstance().setBottomMessage("", Color.BLACK);
		Intent intent = new Intent(InputManager.getInstance().getContext(), PreferencesActivity.class);
		((Activity) InputManager.getInstance().getContext()).startActivityForResult(intent,0);
		
	}

	@Override
	public void doExit() {
		// TODO Auto-generated method stub
		
	}

}
