package com.npogulanik.parquimetro.activities;

import com.npogulanik.parquimetro.InputCallback;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.R.id;
import com.npogulanik.parquimetro.R.layout;
import com.npogulanik.parquimetro.fsm.IdleState;
import com.npogulanik.parquimetro.fsm.ParquimetroContext;
import com.npogulanik.parquimetro.fsm.WaitingSecondSwipeState;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

@SuppressLint("SetJavaScriptEnabled")
public class AnimationActivity extends Activity {

	private WebView wv;
	private EditText scannedValue;
	private Activity context = this;

	String html = "<iframe src='http://my.ewcpresenter.com/projects/f31761&iframe=1&nocontrols=1' height='600' width='1024' style='border: 0px;' webkitAllowFullScreen mozallowfullscreen allowFullScreen></iframe>";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		
		setContentView(R.layout.activity_animation);
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		scannedValue=(EditText)findViewById(R.id.cardCode2);
		wv = (WebView) findViewById(R.id.webView);
		wv.setWebChromeClient(new WebChromeClient());
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
		
		InputManager inputManager = InputManager.getInstance();
		inputManager.setContext(inputManager.getContext());
		inputManager.setScannedValue(scannedValue);
		inputManager.startListeningForEvents(new InputCallback(){
			@Override
			public void inputPrformed(String barcode) {
				// TODO Auto-generated method stub
				context.finish();
				//ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
				//parquimetroContext.setState(new WaitingSecondSwipeState(barcode));
			}
			
		});
	}
}
