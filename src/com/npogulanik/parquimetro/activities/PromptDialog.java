package com.npogulanik.parquimetro.activities;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InvalidButtonException;
import com.npogulanik.parquimetro.NumeroPostaCallback;
import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.R.id;
import com.npogulanik.parquimetro.R.layout;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class PromptDialog extends Dialog implements
		android.view.View.OnClickListener {

	private NumeroPostaCallback callback;
	private LinearLayout relativeLayout;
	private Button buttonA,buttonB,buttonC,buttonD;

	public PromptDialog(Context context,NumeroPostaCallback callback) {
		super(context);
		this.callback = callback;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.prompt);
		
		//escondo el fojo por default 
		this.getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		relativeLayout = (LinearLayout) findViewById(R.id.Botones);
		
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final String postaA = SP.getString("prefPostaA", "");		
		final String postaB = SP.getString("prefPostaB", "");
		final String postaC = SP.getString("prefPostaC", "");		
		final String postaD = SP.getString("prefPostaD", "");
		
		if (postaA.length() > 0){
			buttonA = new Button(DisplayManager.getInstance().getContext());
			buttonA.setId(1);
			buttonA.setOnClickListener(this);
			buttonA.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.red_button_a, 0, 0);
			buttonA.setText(postaA);
			buttonA.setTextColor(Color.BLACK);
			relativeLayout.addView(buttonA);
		}
		
		if (postaB.length() > 0){
			buttonB = new Button(DisplayManager.getInstance().getContext());
			buttonB.setId(2);
			buttonB.setOnClickListener(this);
			buttonB.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.red_button_b, 0, 0);
			buttonB.setText(postaB);
			buttonB.setTextColor(Color.BLACK);
			relativeLayout.addView(buttonB);
		}
		
		if (postaC.length() > 0){
			buttonC = new Button(DisplayManager.getInstance().getContext());
			buttonC.setId(3);
			buttonC.setOnClickListener(this);
			buttonC.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.red_button_c, 0, 0);
			buttonC.setText(postaC);
			buttonC.setTextColor(Color.BLACK);
			relativeLayout.addView(buttonC);
		}
		
		if (postaD.length() > 0){
			buttonD = new Button(DisplayManager.getInstance().getContext());
			buttonD.setId(4);
			buttonD.setOnClickListener(this);
			buttonD.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.red_button_d, 0, 0);
			buttonD.setText(postaD);
			buttonD.setTextColor(Color.BLACK);
			relativeLayout.addView(buttonD);
		}
				
		//this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_NUM_LOCK)); 

	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    switch (keyCode) {
	        case KeyEvent.KEYCODE_H:
				try {
					callback.onPosta(getButtonText("A"));
					dismiss();
				} catch (InvalidButtonException e) {
				}
	            return true;
	        case KeyEvent.KEYCODE_FORWARD_DEL:
	        	try {
					callback.onPosta(getButtonText("B"));
					dismiss();
				} catch (InvalidButtonException e) {
				}
	            return true;
	        case KeyEvent.KEYCODE_INSERT:
	        	try {
					callback.onPosta(getButtonText("C"));
					dismiss();
				} catch (InvalidButtonException e) {
				}
	            return true;
	        case KeyEvent.KEYCODE_SPACE:
	        	try {
					callback.onPosta(getButtonText("D"));
					dismiss();
				} catch (InvalidButtonException e) {
				}
	            return true;
	            
	        default:
	            return super.onKeyUp(keyCode, event);
	    }
	}
	
	private String getButtonText(String letter) throws InvalidButtonException{
		try{
			if (letter.equals("A")){
				return buttonA.getText().toString();
			} else if (letter.equals("B")){
				return buttonB.getText().toString();
			} else if (letter.equals("C")){
				return buttonC.getText().toString();
			} else if (letter.equals("D")){
				return buttonD.getText().toString();
			}
			return "";
		}catch (Exception e){
			throw new InvalidButtonException(e.getMessage());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case 1:
				try {
					callback.onPosta(getButtonText("A"));
					dismiss();
				} catch (InvalidButtonException e) {
				}
				break;
	        case 2:
	        	try {
					callback.onPosta(getButtonText("B"));
					dismiss();
				} catch (InvalidButtonException e) {
				}
	        	break;
	        case 3:
	        	try {
					callback.onPosta(getButtonText("C"));
					dismiss();
				} catch (InvalidButtonException e) {
				}
	        	break;
	        case 4:
	        	try {
					callback.onPosta(getButtonText("D"));
					dismiss();
				} catch (InvalidButtonException e) {
				}
	            break;
	        default:
	            break;
		}
	}
}
