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
import com.npogulanik.parquimetro.fsm.IdleState;
import com.npogulanik.parquimetro.fsm.ParquimetroContext;
import com.npogulanik.parquimetro.fsm.PromptPostaManualState;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
    private String chapa;
    
    public PromptPosta(Context context,String chapa,ArrayList<HashMap<String,String>>listPostas,final NumeroPostaCallback callback) {
		super(context);
		this.postasList = listPostas;
		this.callback = callback;
		this.chapa = chapa;
		
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
		
		//getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		
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
		String key = KeyMapResolver.getInstance().getInput(keyCode);
		if (key != null && key.length() > 0){
			if (key.equals("A")){
				InputManager.getInstance().stopTimer();
	    		ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
	    		parquimetroContext.setState(new PromptPostaManualState(chapa));
	    		dismiss();
			} else if (android.text.TextUtils.isDigitsOnly(key)){
				int numericOption = Integer.parseInt(key);
				if (numericOption <= adapter.getCount() -1 && numericOption > 0){
					list.performItemClick(list, numericOption - 1, -1);
				}
			}
		}
		return true;
	}
    /*
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_1:
				list.performItemClick(list, 0, -1);
				return true;
			case KeyEvent.KEYCODE_2:
				list.performItemClick(list, 1, -1);
				return true;
			case KeyEvent.KEYCODE_3:
				list.performItemClick(list, 2, -1);
				return true;
			case KeyEvent.KEYCODE_4:
				list.performItemClick(list, 3, -1);
				return true;
			case KeyEvent.KEYCODE_5:
				list.performItemClick(list, 4, -1);
				return true;
			case KeyEvent.KEYCODE_6:
				list.performItemClick(list, 5, -1);
				return true;
			case KeyEvent.KEYCODE_7:
				list.performItemClick(list, 6, -1);
				return true;
			case KeyEvent.KEYCODE_8:
				list.performItemClick(list, 7, -1);
				return true;
			case KeyEvent.KEYCODE_9:
				list.performItemClick(list, 8, -1);
				return true;
			case KeyEvent.KEYCODE_A:
				InputManager.getInstance().stopTimer();
	    		ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
	    		parquimetroContext.setState(new PromptPostaManualState(chapa));
	    		dismiss();
				return true;				
			default:
				return super.onKeyDown(keyCode, event);				
		}
	}*/
    /*
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
    	if (KeyMapResolver.getInstance().isValidKey(keyCode)){
    		String optionSelected = KeyMapResolver.getInstance().getInput(keyCode);
        	if (optionSelected != null && optionSelected.length() > 0){
    	    	if (android.text.TextUtils.isDigitsOnly(optionSelected)){ //si selecciono un item de la lista busco el numero de posta correspondiente
    	    		if ((Integer.parseInt(optionSelected)) <= postasList.size()){
	    	    		String posta = postasList.get(Integer.parseInt(optionSelected)-1).get(KEY_NUMERO);
	    	    		InputManager.getInstance().stopTimer();
	    	    		callback.onPosta(posta);
	    	    		InputManager.getInstance().stopTimer();
	    				dismiss();
    	    		}
    	    	} else if (optionSelected.equals("A")){  //si selecciona la letra A pregunto por el numero de posta
    	    		InputManager.getInstance().stopTimer();
    	    		ParquimetroContext parquimetroContext = ParquimetroContext.getInstance();
    	    		parquimetroContext.setState(new PromptPostaManualState(chapa));
    	    		dismiss();
    	    		
    	    	}
        	}
    	} 
    	return true;
	}*/
    
    /*
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
	}*/

	
}