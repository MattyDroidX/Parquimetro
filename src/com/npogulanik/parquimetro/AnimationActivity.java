package com.npogulanik.parquimetro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class AnimationActivity extends Activity {

	private WebView wv;

	String html = "<iframe src='http://my.ewcpresenter.com/projects/f31761&iframe=1&nocontrols=1' height='800' width='1280' style='border: 0px;' webkitAllowFullScreen mozallowfullscreen allowFullScreen></iframe>";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);
		wv = (WebView) findViewById(R.id.webView);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
	}
}
