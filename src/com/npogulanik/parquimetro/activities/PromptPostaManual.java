package com.npogulanik.parquimetro.activities;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.KeyMapResolver;
import com.npogulanik.parquimetro.NumeroPostaCallback;
import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.TimerCallBack;
import com.npogulanik.parquimetro.fsm.ParquimetroContext;
import com.npogulanik.parquimetro.fsm.PromptPostaManualState;

import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class PromptPostaManual extends Dialog {
	private EditText result;
	private TextView textViewTimer;
	private NumeroPostaCallback callback;
	
	public PromptPostaManual(Context context,final NumeroPostaCallback callback) {
		super(context);
		this.callback = callback;
		
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final int promptPostaTiemeout = Integer.parseInt(SP.getString("prefPromptPostaTimeout", "20"));
		
		InputManager.getInstance().startTimer(promptPostaTiemeout, new TimerCallBack() {			
			@Override
			public void timeExpired() {
				callback.onTimeout();
				dismiss();
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				textViewTimer.setText(String.format("%02d", Integer.valueOf(String
						.valueOf(millisUntilFinished / 1000))));
			} 
		});
		// TODO Auto-generated constructor stub
	}
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prompt_posta_manual);
		this.setTitle("Ingrese Nœmero de Posta");
		result = (EditText) findViewById(R.id.textPosta);
		textViewTimer = (TextView)findViewById(R.id.timerTextPosta);	
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		String key = KeyMapResolver.getInstance().getInput(keyCode);
		if (key != null && key.length() > 0){
			if (key.equals("#")){
				String posta = result.getText().toString();
				if (posta != null && posta.length() > 0){
					callback.onPosta(result.getText().toString());
		    		InputManager.getInstance().stopTimer();
					dismiss();
				}
			} else if  (key.equals("*")){
				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
			}else if (android.text.TextUtils.isDigitsOnly(key)){
				super.onKeyDown(keyCode, event);
			}
		}
		return true;		
	}
	/*
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {  
    	if (KeyMapResolver.getInstance().isValidKey(keyCode)){
    		String optionSelected = KeyMapResolver.getInstance().getInput(keyCode);
        	if (optionSelected != null && optionSelected.length() > 0){
    	    	if (android.text.TextUtils.isDigitsOnly(optionSelected)){ //si selecciono un item de la lista busco el numero de posta correspondiente
    	    		int numberPressed = Integer.parseInt(optionSelected);
    	    		switch (numberPressed){
    	    			case 1:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
    	    				break;
    	    			case 2:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_2));
    	    				break;
    	    			case 3:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_3));
    	    				break;
    	    			case 4:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_4));
    	    				break;
    	    			case 5:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_5));
    	    				break;
    	    			case 6:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_6));
    	    				break;
    	    			case 7:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_7));
    	    				break;
    	    			case 8:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_8));
    	    				break;
    	    			case 9:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_9));
    	    				break;
    	    			case 0:
    	    				result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_0));
    	    				break;	
    	    		}
    	    	} else if (optionSelected.equals("#")){  //si selecciona la letra A pregunto por el numero de posta
    	    		callback.onPosta(result.getText().toString());
    	    		InputManager.getInstance().stopTimer();
    				dismiss();
    	    		
    	    	}  else if (optionSelected.equals("*")){  //si selecciona la letra A pregunto por el numero de posta
    	    		result.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
    	    	}
        	}
    	}     	
    	return true;
	}*/
}
