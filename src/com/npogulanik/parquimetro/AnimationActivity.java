package com.npogulanik.parquimetro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
		setContentView(R.layout.activity_animation);
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
			public void inputPrformed() {
				// TODO Auto-generated method stub
				context.finish();
			}
			
		});
	}
}
