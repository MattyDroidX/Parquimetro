package com.npogulanik.parquimetro;

import com.npogulanik.paquimetro.fsm.IdleState;
import com.npogulanik.paquimetro.fsm.ParquimetroContext;
import com.npogulanik.paquimetro.fsm.SendingTransaction;
import com.npogulanik.paquimetro.fsm.WaitingSecondSwipe;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class InputManager {
	private static InputManager instance;
	private Context context;
	private EditText scannedValue;
	private Handler handler;
	private Runnable runnable;
	private ParquimetroContext parquimetroContext;
	
	private InputManager(){
	}
	
	public static InputManager getInstance(){
		if (instance == null){
			instance = new InputManager();
		}
		return instance;
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
	public Context getContext(){
		return this.context;
	}

	public EditText getScannedValue() {
		return scannedValue;
	}

	public void setScannedValue(EditText scannedValue) {
		this.scannedValue = scannedValue;
	}

	public void startListeningForEvents() {
		// TODO Auto-generated method stub
		handler = new Handler();
		scannedValue.addTextChangedListener(new TextWatcher() {	
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
					
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 1) {
				      char lastCharacter = s.charAt(s.length() - 1); 
				      if (lastCharacter == '\n') { 
				    	  String barcode = s.subSequence(0, s.length() - 1).toString();
				    	  scannedValue.setText("");
		
				    	  if (BarCodeHolder.isSecondSwipe(barcode)){
				    		  handler.removeCallbacks(runnable);
				    		  parquimetroContext.setState(new SendingTransaction());
				    		  parquimetroContext.doAction();
				    		  //displayManager.stopFlipping(mBottomFlipper);
				    		  //displayManager.showMessage(mTopFlipper, "Procesando entrada...");
				    		  //HttpRequestTask task = new HttpRequestTask();
					    	  //task.applicationContext = MainActivity.this;
					    	  //((TextView)mFlipper.getChildAt(0)).setText("");
					    	  //task.execute(barcode);
				    	  } else {
				    		  runnable = new Runnable() { 
				    		         public void run() { 
				    		        	 BarCodeHolder.reset();
				    		        	 //displayManager.stopFlipping(mTopFlipper);
				    		        	 //displayManager.showMessage(mBottomFlipper, getString(R.string.text_swipe));
				    		        	 parquimetroContext.setState(new IdleState());
				    		        	 parquimetroContext.doAction();
				    		         } 
				    		  };
				    		  //displayManager.stopFlipping(mBottomFlipper);
				    		  //displayManager.stopFlipping(mTopFlipper);
				    		  //displayManager.showMessage(mBottomFlipper, getString(R.string.text_swipe_again));
				    		  parquimetroContext.setState(new WaitingSecondSwipe());
				    		  parquimetroContext.doAction();
				    		  handler.postDelayed(runnable, 5000);    		  
				    	  }
				      }
				}
			}
		});
	}

	public void setParquimetroContext(ParquimetroContext parquimetroContext) {
		this.parquimetroContext = parquimetroContext;
	}
}
