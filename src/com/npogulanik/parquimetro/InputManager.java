package com.npogulanik.parquimetro;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.npogulanik.paquimetro.fsm.IdleState;
import com.npogulanik.paquimetro.fsm.OnSuccessState;
import com.npogulanik.paquimetro.fsm.ParquimetroContext;
import com.npogulanik.paquimetro.fsm.SendingTransactionState;
import com.npogulanik.paquimetro.fsm.WaitingSecondSwipeState;

public class InputManager {
	private static InputManager instance;
	private Context context;
	private EditText scannedValue;
	private CountDownTimer countDown;
	private ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();

	private InputManager() {
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

	private void startTimer() {
		countDown = new CountDownTimer(10000, 1000) {
			public void onTick(long millisUntilFinished) {
				DisplayManager.getInstance().showTimerText(
						String.format("%02d", Integer.valueOf(String
								.valueOf(millisUntilFinished / 1000))));
			}

			public void onFinish() {
				BarCodeHolder.reset();
				parquimetroContext.setState(new IdleState());
			}
		}.start();
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
								callback.inputPrformed(); 
							}
							if (BarCodeHolder.isSecondSwipe(barcode)) {
								countDown.cancel();
								parquimetroContext.setState(new SendingTransactionState(barcode));
							} else {
								if (countDown != null) {
									countDown.cancel();
									countDown = null;
								}
							    startTimer();
								parquimetroContext.setState(new WaitingSecondSwipeState(barcode));
							}
						}
					}
				}
		});
	}
}
