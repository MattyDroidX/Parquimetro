package com.npogulanik.parquimetro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.npogulanik.paquimetro.fsm.IdleState;
import com.npogulanik.paquimetro.fsm.ParquimetroContext;

public class MainActivity extends Activity {
    private ViewFlipper mBottomFlipper,mTopFlipper;
    private EditText scannedValue;
    private TextView timerText,saldoText;
    private DisplayManager displayManager;
    private InputManager inputManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		scannedValue=(EditText)findViewById(R.id.cardCode);
		timerText=(TextView)findViewById(R.id.timerText);
		saldoText=(TextView)findViewById(R.id.saldoText);
		mBottomFlipper = ((ViewFlipper)findViewById(R.id.flipperBottom));
		mTopFlipper = ((ViewFlipper)findViewById(R.id.flipperTop));
		
		displayManager = DisplayManager.getInstance();
		displayManager.setContext(this);
		
		inputManager = InputManager.getInstance();
		inputManager.setContext(this);
		inputManager.setScannedValue(scannedValue);
		
		displayManager.setTopFlipper(mTopFlipper);
		displayManager.setBottomFlipper(mBottomFlipper);
		displayManager.setTimerText(timerText);
		displayManager.setSaldoText(saldoText);
		
		//escondo el navigation bar
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		//empiezo a escuchar eventos de entrada
		inputManager.startListeningForEvents(null);
		
		//pongo el parquimetro en estado idle al arrancar la app
		ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
		parquimetroContext.setState(new IdleState());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		inputManager.setScannedValue(scannedValue);
	};
	

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    switch (keyCode) {
	        case KeyEvent.KEYCODE_MOVE_HOME:
	        	Toast.makeText(this, "Amarillo", Toast.LENGTH_SHORT).show();
	        	scannedValue.setText("");
	            return true;
	        case KeyEvent.KEYCODE_MOVE_END:
	        	Toast.makeText(this, "Rojo", Toast.LENGTH_SHORT).show();
	        	scannedValue.setText("");
	            return true;
	        case KeyEvent.KEYCODE_NUMPAD_ENTER:
	        	Toast.makeText(this, "Marron", Toast.LENGTH_SHORT).show();
	        	scannedValue.setText("");
	            return true;    
	        default:
	            return super.onKeyUp(keyCode, event);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
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
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
