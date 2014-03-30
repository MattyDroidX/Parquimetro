package com.npogulanik.parquimetro.activities;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.InvalidButtonException;
import com.npogulanik.parquimetro.KeyMapResolver;
import com.npogulanik.parquimetro.NumeroChapaCallback;
import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.TimerCallBack;
import com.npogulanik.parquimetro.fsm.ParquimetroContext;
import com.npogulanik.parquimetro.fsm.ScreenSaverState;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class PromptChapa extends Dialog {
	public static final String KEY_CHAPA = "chapa"; // parent node
	public static final String KEY_CREDITO = "credito";
	private ArrayList<HashMap<String, String>> chapasList;// = new ArrayList<HashMap<String, String>>();
	private ListView list;
	private TextView textViewTimer;
    private LazyAdapterChapas adapter;
    private NumeroChapaCallback callback;
    
    public PromptChapa(Context context,ArrayList<HashMap<String,String>> listChapas,final NumeroChapaCallback callback) {
		super(context);
		this.chapasList = listChapas;
		this.callback = callback;
		
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final int promptChapaTiemeout = Integer.parseInt(SP.getString("prefPromptChapaTimeout", "20"));
		
		InputManager.getInstance().startTimer(promptChapaTiemeout, new TimerCallBack() {			
			@Override
			public void timeExpired() {
				callback.onTimeout();
				dismiss();
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				textViewTimer.setText(String.format("%02d", Integer.valueOf(String
								.valueOf(millisUntilFinished / 1000))));
			} 
		});
	}
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prompt_chapas);
		this.setTitle("Seleccione Chapa");
		
		list=(ListView)findViewById(R.id.listChapas);
		textViewTimer = (TextView)findViewById(R.id.timerTextChapas);
        adapter=new LazyAdapterChapas(DisplayManager.getInstance().getContext(), chapasList);        
        list.setAdapter(adapter);
        
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				  String chapa = chapasList.get(position).get(KEY_CHAPA);
				  InputManager.getInstance().stopTimer();
				  callback.onChapa(chapa);
				  dismiss();
			}
		});		
	}	
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
    	if (KeyMapResolver.getInstance().isValidKey(keyCode)){
    		String optionSelected = KeyMapResolver.getInstance().getInput(keyCode);
        	if (optionSelected != null && optionSelected.length() > 0){
    	    	if (android.text.TextUtils.isDigitsOnly(optionSelected)){
    	    		if ((Integer.parseInt(optionSelected)) <= chapasList.size()){
	    	    		String chapa = chapasList.get(Integer.parseInt(optionSelected)-1).get(KEY_CHAPA);
	    	    		InputManager.getInstance().stopTimer();
	    	    		callback.onChapa(chapa);
	    				dismiss();
    	    		}
    	    	}
        	}
    	} 
    	return true;
	}

	
}