package com.npogulanik.parquimetro;

import java.util.ArrayList;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.DeadObjectException;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;

import com.npogulanik.parquimetro.fsm.IdleState;
import com.npogulanik.parquimetro.fsm.OnSuccessState;
import com.npogulanik.parquimetro.fsm.ParquimetroContext;
import com.npogulanik.parquimetro.fsm.PromptChapaState;
import com.npogulanik.parquimetro.fsm.SendingTransactionState;
import com.npogulanik.parquimetro.fsm.WaitingSecondSwipeState;


public class InputManager {
	private static InputManager instance;
	private Context context;
	private EditText scannedValue;
	private CountDownTimer countDown;
	private ArrayList<String> chapas = new ArrayList<String>();
	private ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();

	private InputManager() {
	}
	
	public void addChapa(String chapa){
		chapas.add(chapa);
	}
	
	public void clearChapas(){
		chapas.clear();
	}

	public static InputManager getInstance() {
		if (instance == null) {
			instance = new InputManager();
		}
		return instance;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return this.context;
	}

	public EditText getScannedValue() {
		return scannedValue;
	}
	
	public void disableInput(){
		scannedValue.setEnabled(false);
	}
	
	public void enableInput(){
		scannedValue.setEnabled(true);
	}

	public void setScannedValue(EditText scannedValue) {
		this.scannedValue = scannedValue;
	}

	
	public void startTimer(int seconds, final TimerCallBack timerCallBack) {
		countDown = new CountDownTimer(seconds*1000, 1000) {
			public void onTick(long millisUntilFinished) {
				timerCallBack.onTick(millisUntilFinished);
			}

			public void onFinish() {
				timerCallBack.timeExpired();
			}
		}.start();
		//return timer;
	}
	
	public void stopTimer(){
		if (countDown != null) {
			countDown.cancel();
			countDown = null;
		}
	}
	

	public void startListeningForEvents(final InputCallback callback) {
		scannedValue.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

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
							if (callback != null){
								callback.inputPrformed(barcode); 
							}
							if (BarCodeHolder.isSecondSwipe(barcode)) {
								parquimetroContext.setState(new SendingTransactionState(barcode));
							} else {
								parquimetroContext.setState(new PromptChapaState(barcode));
							}
						}
					}
				}
		});
	}
}
