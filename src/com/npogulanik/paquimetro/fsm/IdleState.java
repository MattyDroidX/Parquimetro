package com.npogulanik.paquimetro.fsm;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;

import com.npogulanik.parquimetro.AnimationActivity;
import com.npogulanik.parquimetro.BarCodeHolder;
import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.R;

public class IdleState implements State {
	CountDownTimer countDown;
	
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		InputManager.getInstance().enableInput();
		DisplayManager.getInstance().stopFlippingTop();
		DisplayManager.getInstance().stopTimer();
		DisplayManager.getInstance().resetSaldo();
		DisplayManager.getInstance().setBottomMessage(DisplayManager.getInstance().getContext().getString(R.string.text_swipe),Color.BLACK);
		
		if (countDown != null) {
			countDown.cancel();
			countDown = null;
		}
		countDown = new CountDownTimer(10000, 1000) {
			public void onTick(long millisUntilFinished) {
			}

			public void onFinish() {
				Intent intent = new Intent(InputManager.getInstance().getContext(),AnimationActivity.class);
				InputManager.getInstance().getContext().startActivity(intent);
			}
		}.start();
	}

	@Override
	public void doExit() {
		// TODO Auto-generated method stub
		if (countDown != null) {
			countDown.cancel();
			countDown = null;
		}
	}
}
