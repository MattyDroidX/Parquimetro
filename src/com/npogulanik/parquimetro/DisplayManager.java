package com.npogulanik.parquimetro;

import android.app.Activity;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import android.widget.TextView;

public class DisplayManager {
	private Context mContext;
	
	public DisplayManager(Activity context){
		mContext = context;
	}
	
	public  void showMessage(ViewFlipper flipper, String string) {
		((TextView)flipper.getChildAt(0)).setText(string);
		flipper.startFlipping();
		flipper.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
	}
	
	public void stopFlipping(ViewFlipper flipper){
		((TextView)flipper.getChildAt(0)).setText("");
		flipper.stopFlipping();
	}

	

}
