package com.npogulanik.parquimetro;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import android.widget.TextView;

public class DisplayManager {
	private Context context;
	private ViewFlipper topFlipper;
	private ViewFlipper bottomFlipper;
	private static DisplayManager instance;
	
	private DisplayManager(){
	}
	
	public static DisplayManager getInstance(){
		if (instance == null){
		   instance = new DisplayManager();
		}
		return instance;
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
	public Context getContext(){
		return this.context;
	}
	
	public void setTopMessage(String message){
		this.showMessage(this.topFlipper,message);
	}
	
	public void setBottomMessage(String message){
		this.showMessage(this.bottomFlipper,message);
	}
	
	private  void showMessage(ViewFlipper flipper, String string) {
		((TextView)flipper.getChildAt(0)).setText(string);
		flipper.startFlipping();
		flipper.setInAnimation(AnimationUtils.loadAnimation(this.context, android.R.anim.fade_in));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(this.context, android.R.anim.fade_out));
	}
	
	public void StopFlippingTop(){
		stopFlipping(topFlipper);
	}
	
	public void StopFlippingBottom(){
		stopFlipping(bottomFlipper);
	}
	
	private void stopFlipping(ViewFlipper flipper){
		((TextView)flipper.getChildAt(0)).setText("");
		flipper.stopFlipping();
	}

	public void setTopFlipper(ViewFlipper topFlipper) {
		// TODO Auto-generated method stub
	    this.topFlipper = topFlipper;	
	}
	
	public void setBottomFlipper(ViewFlipper bottomFlipper) {
		// TODO Auto-generated method stub
	    this.bottomFlipper = bottomFlipper;	
	}

	

}
