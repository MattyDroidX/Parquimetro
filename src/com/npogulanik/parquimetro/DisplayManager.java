package com.npogulanik.parquimetro;



import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import android.widget.TextView;

public class DisplayManager {
	private Context context;
	private ViewFlipper topFlipper;
	private ViewFlipper bottomFlipper;
	private TextView timerText;
	private TextView saldoText;
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
	
	public void setTopMessage(String message,int color){
		this.showMessage(this.topFlipper,message,color);
	}
	
	public void setBottomMessage(String message,int color){
		this.showMessage(this.bottomFlipper,message,color);
	}
	
	public void setSuccessMessage(String message, String saldo, int color){
		this.showSuccessMessage(this.topFlipper,message,saldo,color);
	}
	
	private  void showMessage(ViewFlipper flipper, String string,int color) {
		TextView textView = ((TextView)flipper.getChildAt(0));
		textView.setTextColor(color);
		textView.setText(string);
		flipper.startFlipping();
		flipper.setInAnimation(AnimationUtils.loadAnimation(this.context, android.R.anim.fade_in));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(this.context, android.R.anim.fade_out));
	}
	
	private  void showSuccessMessage(ViewFlipper flipper, String mensaje,String saldo,int color) {
		TextView textView = ((TextView)flipper.getChildAt(0));
		textView.setTextColor(color);
		textView.setText(mensaje);
		TextView textView1 = ((TextView)flipper.getChildAt(1));
		textView1.setTextColor(Color.BLACK);
		textView1.setText(saldo);
		flipper.startFlipping();
		flipper.setInAnimation(AnimationUtils.loadAnimation(this.context, android.R.anim.fade_in));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(this.context, android.R.anim.fade_out));
	}
	
	public void showTimerText(String text){
		this.timerText.setText(text);
	}
	
	public void showSaldoText(String text){
		this.saldoText.setText(text);
	}
	
	public void resetSaldo(){
		this.saldoText.setText("");
	}
	
	public void stopTimerText(){
		showTimerText("");
	}
	
	public void stopFlippingTop(){
		stopFlipping(topFlipper);
	}
	
	public void stopFlippingBottom(){
		stopFlipping(bottomFlipper);
	}
	
	private void stopFlipping(ViewFlipper flipper){
		((TextView)flipper.getChildAt(0)).setText("");
		flipper.stopFlipping();
	}

	public void setTopFlipper(ViewFlipper topFlipper) {
	    this.topFlipper = topFlipper;	
	}
	
	public void setBottomFlipper(ViewFlipper bottomFlipper) {
	    this.bottomFlipper = bottomFlipper;	
	}

	public void setTimerText(TextView timerText) {
		this.timerText = timerText;
	}
	
	public void setSaldoText(TextView saldoText) {
		this.saldoText = saldoText;
	}
}
