package com.npogulanik.parquimetro.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.npogulanik.parquimetro.BarCodeHolder;
import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InputCallback;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.KeyMapResolver;
import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.Utils;
import com.npogulanik.parquimetro.R.id;
import com.npogulanik.parquimetro.R.layout;
import com.npogulanik.parquimetro.R.menu;
import com.npogulanik.parquimetro.fsm.IdleState;
import com.npogulanik.parquimetro.fsm.OnPreferencesState;
import com.npogulanik.parquimetro.fsm.ParquimetroContext;
import com.npogulanik.parquimetro.fsm.PromptChapaState;
import com.npogulanik.parquimetro.fsm.SendingTransactionState;

public class MainActivity extends Activity {
	private static final int RESULT_SETTINGS = 1;

	private static final String TAG = "HIDE STATUS BAR";
	
	private ViewFlipper mBottomFlipper,mTopFlipper;
    private EditText scannedValue;
    private TextView timerText,saldoText;
    private DisplayManager displayManager;
    private InputManager inputManager;
    private Button prefsButton;
    private boolean onCreateRunned = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		onCreateRunned = true;
		Utils.HideStatusBar(this);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		scannedValue=(EditText)findViewById(R.id.cardCode);
		timerText=(TextView)findViewById(R.id.timerText);
		saldoText=(TextView)findViewById(R.id.saldoText);
		mBottomFlipper = ((ViewFlipper)findViewById(R.id.flipperBottom));
		mTopFlipper = ((ViewFlipper)findViewById(R.id.flipperTop));
		prefsButton = ((Button)findViewById(R.id.prefs));
		//prefsButton.setOnClickListener(prefsListener);
		
		displayManager = DisplayManager.getInstance();
		displayManager.setContext(this);
		
		inputManager = InputManager.getInstance();
		inputManager.setContext(this);
		inputManager.setScannedValue(scannedValue);
		
		displayManager.setTopFlipper(mTopFlipper);
		displayManager.setBottomFlipper(mBottomFlipper);
		displayManager.setTimerText(timerText);
		displayManager.setSaldoText(saldoText);
		
		ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
		parquimetroContext.setState(new IdleState());
		
		//empiezo a escuchar eventos de entrada
		startListeningForEvents();	
	}
	
	public void onPrefsClicked(View v){
	    if(v.getId() == R.id.prefs){
	    	if (ParquimetroContext.getInstance().getState() instanceof IdleState){
		    	ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
				parquimetroContext.setState(new OnPreferencesState());
	    	}
	    }
	}
	
	private void setFullScreen(){
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
		if (!(parquimetroContext.getState() instanceof IdleState)){
			parquimetroContext.setState(new IdleState());
		}
		
		if(onCreateRunned){
	        onCreateRunned = false; //important, or it will run only once.
	    } else {
	    	Utils.HideStatusBar(this);
	    }
		
	};
	
	public void startListeningForEvents() {
		scannedValue.addTextChangedListener(new TextWatcher() {
			ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
			
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
						parquimetroContext.setState(new PromptChapaState(barcode));
					}
				}
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 0) {
	    	ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
			parquimetroContext.setState(new IdleState());
	    }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
    	return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_exit:
				Intent i = new Intent(Intent.ACTION_MAIN);
				i.addCategory(Intent.CATEGORY_HOME);
				i.addCategory(Intent.CATEGORY_DEFAULT);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(i, "Seleccione Launcher"));
				break;
				
			case R.id.menu_settings:
				ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
				parquimetroContext.setState(new OnPreferencesState());
				break;
	   
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	private void KillStatusBar()
	{
	    Process proc = null;

	    String ProcID = "79"; //HONEYCOMB AND OLDER

	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
	        ProcID = "42"; //ICS AND NEWER
	    }

	    try {
	        proc = Runtime
	                .getRuntime()
	                .exec(new String[] { "su", "-c",
	                        "service call activity "+ProcID+" s16 com.android.systemui" });
	    } catch (IOException e) {
	        Log.w(TAG,"Failed to kill task bar (1).");
	        e.printStackTrace();
	    }
	    try {
	        proc.waitFor();
	    } catch (InterruptedException e) {
	        Log.w(TAG,"Failed to kill task bar (2).");
	        e.printStackTrace();
	    }

	}
}
