package com.npogulanik.paquimetro.fsm;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.R;

public class IdleState implements State {

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		DisplayManager.getInstance().StopFlippingTop();
		DisplayManager.getInstance().setBottomMessage(DisplayManager.getInstance().getContext().getString(R.string.text_swipe));
	}
}
