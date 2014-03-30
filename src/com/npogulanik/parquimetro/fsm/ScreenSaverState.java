package com.npogulanik.parquimetro.fsm;

import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.activities.AnimationActivity;
import com.npogulanik.parquimetro.activities.PlayVideoActivity;

import android.content.Intent;


public class ScreenSaverState implements State {
	
	@Override
	public void doAction() {
		InputManager.getInstance().enableInput();
		Intent intent = new Intent(InputManager.getInstance().getContext(),PlayVideoActivity.class);
		InputManager.getInstance().getContext().startActivity(intent);
	}

	@Override
	public void doExit() {
		// TODO Auto-generated method stu
	}
}
