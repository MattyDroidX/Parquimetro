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
import com.npogulanik.parquimetro.NumeroPostaCallback;
import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.TimerCallBack;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PromptPosta extends Dialog {
	public static final String KEY_LETRA = "letra"; // parent node
	public static final String KEY_NUMERO = "numero";
	private ArrayList<HashMap<String,String>> postasList;// = new ArrayList<HashMap<String, String>>();
	private ListView list;
	private TextView textViewTimer;
    private LazyAdapterPostas adapter;
    private NumeroPostaCallback callback;
    
    public PromptPosta(Context context,ArrayList<HashMap<String,String>>listPostas,final NumeroPostaCallback callback) {
		super(context);
		this.postasList = listPostas;
		this.callback = callback;
		
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final int promptPostaTiemeout = Integer.parseInt(SP.getString("prefPromptPostaTimeout", "20"));
		
		InputManager.getInstance().startTimer(promptPostaTiemeout, new TimerCallBack() {			
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
		setContentView(R.layout.prompt_postas);
		this.setTitle("Seleccione Posta");
		
		list=(ListView)findViewById(R.id.listPostas);	
		textViewTimer = (TextView)findViewById(R.id.timerTextPostas);
        adapter=new LazyAdapterPostas(DisplayManager.getInstance().getContext(), postasList);        
        list.setAdapter(adapter);
        
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				  String posta = postasList.get(position).get(KEY_NUMERO);
				  InputManager.getInstance().stopTimer();
				  callback.onPosta(posta);
				  dismiss();
			}
		});		
	}	
    
    private String findKeyValue(String key){
    	for (HashMap<String,String>i : postasList){
    		if (i.get(KEY_LETRA).equals(key)){
    			return i.get(KEY_NUMERO);
    		}
    	}
    	return "";
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	String posta = "";
    	switch (keyCode) {
	        case KeyEvent.KEYCODE_H:
				posta = findKeyValue("A");
				if (posta.length() > 0){
					InputManager.getInstance().stopTimer();
					callback.onPosta(posta);
					dismiss();
				}
				break;
	        case KeyEvent.KEYCODE_NUMPAD_DOT:
	        	posta = findKeyValue("B");
				if (posta.length() > 0){
					InputManager.getInstance().stopTimer();
					callback.onPosta(posta);
					dismiss();
				}
				break;
	        //case KeyEvent.KEYCODE_INSERT:
	        case KeyEvent.KEYCODE_NUMPAD_0:	
	        	posta = findKeyValue("C");
				if (posta.length() > 0){
					InputManager.getInstance().stopTimer();
					callback.onPosta(posta);
					dismiss();
				} 
				break;
	        case KeyEvent.KEYCODE_SPACE:
	        	posta = findKeyValue("D");
				if (posta.length() > 0){
					InputManager.getInstance().stopTimer();
					callback.onPosta(posta);
					dismiss();
				}
				break;
    	}
    	return true;
	}

	
}