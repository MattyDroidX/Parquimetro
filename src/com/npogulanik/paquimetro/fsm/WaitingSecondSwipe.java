package com.npogulanik.paquimetro.fsm;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.R;

public class WaitingSecondSwipe implements State {

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		//displayManager.stopFlipping(mBottomFlipper);
		  //displayManager.stopFlipping(mTopFlipper);
		  //displayManager.showMessage(mBottomFlipper, getString(R.string.text_swipe_again));
		DisplayManager.getInstance().StopFlippingTop();
		DisplayManager.getInstance().StopFlippingBottom();
		DisplayManager.getInstance().setBottomMessage(DisplayManager.getInstance().getContext().getString(R.string.text_swipe_again));;
	}

}
