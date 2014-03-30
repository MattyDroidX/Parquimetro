package com.npogulanik.parquimetro.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.npogulanik.parquimetro.InputCallback;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.fsm.IdleState;
import com.npogulanik.parquimetro.fsm.ParquimetroContext;

public class PlayVideoActivity extends Activity {
	private VideoView myVideoView;
	private StringBuilder barcodeInput = new StringBuilder();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
        
		myVideoView = (VideoView) findViewById(R.id.vidview);
		myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
            	myVideoView.start(); //need to make transition seamless.
            }
        });
				
		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(myVideoView);

		try {// try to fetch vid from sdcard
			myVideoView.setMediaController(null);
			myVideoView.requestFocus();
			String vidpath = "android.resource://" + getPackageName() + "/" + R.raw.animacion4;
			myVideoView.setVideoURI(Uri.parse(vidpath));
			
			myVideoView.start();
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.toString(),
					Toast.LENGTH_SHORT);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9){
    		barcodeInput.append((char)event.getUnicodeChar());
    	} else {
    		if (barcodeInput.toString().length() == 10){
    			InputManager.getInstance().enableInput();
    			Intent intent = new Intent(InputManager.getInstance().getContext(),MainActivity.class);
    			InputManager.getInstance().getContext().startActivity(intent);
    			return true;
    		}
    		barcodeInput = new StringBuilder();
    	}
		
    	return true;
	}

}
