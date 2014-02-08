package com.npogulanik.parquimetro;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.npogulanik.paquimetro.fsm.IdleState;
import com.npogulanik.paquimetro.fsm.ParquimetroContext;
import com.npogulanik.parquimetro.entity.ConsultaCredito;
import com.npogulanik.parquimetro.entity.Entrada;
import com.npogulanik.parquimetro.entity.Greeting;
import com.npogulanik.parquimetro.entity.ParamsEntrada;

public class MainActivity extends Activity {
    private ViewFlipper mBottomFlipper,mTopFlipper;
    private EditText scannedValue;
    private RestClient restClient = new RestClient();
    private DisplayManager displayManager;
    private InputManager inputManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		displayManager = DisplayManager.getInstance();
		displayManager.setContext(this);
		
		inputManager = InputManager.getInstance();
		inputManager.setContext(this);
		
		scannedValue=(EditText)findViewById(R.id.cardCode);
		mBottomFlipper = ((ViewFlipper)findViewById(R.id.flipperBottom));
		mTopFlipper = ((ViewFlipper)findViewById(R.id.flipperTop));
		
		inputManager.setScannedValue(scannedValue);
		
		
		displayManager.setTopFlipper(mTopFlipper);
		displayManager.setBottomFlipper(mBottomFlipper);
		
		ParquimetroContext parquimetroContext = new ParquimetroContext();
		parquimetroContext.setState(new IdleState());
		parquimetroContext.doAction();
		
		inputManager.setParquimetroContext(parquimetroContext);
		
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		
		inputManager.startListeningForEvents();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
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
	
	/*
	private class HttpRequestTask extends AsyncTask<String, Void, Greeting> {
		private ProgressDialog dialog;
		private Exception mException;
		protected Context applicationContext;
		private ConsultaCredito credito;
		private String chapa;
		
        
		
		@Override
		protected void onPreExecute() {
			this.dialog = ProgressDialog.show(applicationContext, "Procesando", " Ticket de Entrada...", true);
		}


        @Override
        protected Greeting doInBackground(String... cardNumber) {
            try {
            	chapa = new DummyChapaResolver().getChapa(cardNumber[0]);
            	//ParamsEntrada paramsEntrada = new ParamsEntrada(chapa,"1","1");
            	credito = restClient.doSaldo(chapa);      	  
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                mException = e;
                //Toast.makeText(applicationContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
        	this.dialog.cancel();
        	if (mException != null){
        		Toast.makeText(applicationContext, "Error ->" +  mException.getMessage(), Toast.LENGTH_SHORT).show();        		
        	}else{
	        	if (credito != null){
	        		//Toast.makeText(applicationContext, "RESPUESTA DEL WS ->" +  entrada.getEntrada().getLugar(), Toast.LENGTH_SHORT).show();
	        	    displayManager.showMessage(mTopFlipper, "CREDITO DISPONIBLE DOMINIO " + chapa + " " + credito.getCreditoSaldo());	//TODO agregar timer como parametro    	}
        	}
        	//displayManager.stopFlipping(mTopFlipper);
       	    displayManager.showMessage(mBottomFlipper, getString(R.string.text_swipe));
        }

    }
	}*/
}
