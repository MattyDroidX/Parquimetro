package com.npogulanik.parquimetro.fsm;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.NumeroPostaCallback;
import com.npogulanik.parquimetro.TransactionManager;
import com.npogulanik.parquimetro.activities.PreferencesActivity;
import com.npogulanik.parquimetro.activities.PromptChapa;
import com.npogulanik.parquimetro.activities.PromptDialog;
import com.npogulanik.parquimetro.activities.PromptPosta;

public class PromptPostaState implements State {
	private String chapa;
	private ArrayList<HashMap<String,String>> listPostas = new ArrayList<HashMap<String,String>>();
	
	public PromptPostaState(String chapa){
		this.chapa = chapa;
	}
	
	private void loadPostas(){
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(InputManager.getInstance().getContext());
		final String posta1 = SP.getString("prefPosta1", "");		
		final String posta2 = SP.getString("prefPosta2", "");
		final String posta3 = SP.getString("prefPosta3", "");		
		final String posta4 = SP.getString("prefPosta4", "");
		
		listPostas.clear();
		if (posta1 != null && posta1.length() > 0){
			HashMap<String,String> map = new HashMap<String,String>();
    		map.put(PromptPosta.KEY_LETRA,"1");
    		map.put(PromptPosta.KEY_NUMERO,posta1);
			listPostas.add(map);
		}
		if (posta2 != null && posta2.length() > 0){
			HashMap<String,String> map = new HashMap<String,String>();
    		map.put(PromptPosta.KEY_LETRA,"2");
    		map.put(PromptPosta.KEY_NUMERO,posta2);
			listPostas.add(map);
		}
		if (posta3 != null && posta3.length() > 0){
			HashMap<String,String> map = new HashMap<String,String>();
    		map.put(PromptPosta.KEY_LETRA,"3");
    		map.put(PromptPosta.KEY_NUMERO,posta3);
			listPostas.add(map);
		}
		if (posta4 != null && posta4.length() > 0){
			HashMap<String,String> map = new HashMap<String,String>();
    		map.put(PromptPosta.KEY_LETRA,"4");
    		map.put(PromptPosta.KEY_NUMERO,posta4);
			listPostas.add(map);
		}
		//agrego una posta para ingreso manual
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(PromptPosta.KEY_LETRA,"A");
		map.put(PromptPosta.KEY_NUMERO,"Ingresar Posta Manualmente");
		listPostas.add(map);
	}
	
	@Override
	public void doAction() {
		loadPostas();
		if (listPostas.size() >= 1){
			PromptPosta dialog = new PromptPosta(InputManager.getInstance().getContext(),chapa,listPostas,new NumeroPostaCallback(){
				@Override
				public void onPosta(String posta) {
					TransactionManager.getInstance().performEntrada(chapa, posta);
				}

				@Override
				public void onTimeout() {
					// TODO Auto-generated method stub
					ParquimetroContext context = ParquimetroContext.getInstance();
					context.setState(new IdleState());		
				}
			});
			dialog.show();
		} else {
			//si no hay postas seteadas abro los sttings
			InputManager.getInstance().disableInput();
			DisplayManager.getInstance().stopTimerText();
			Intent intent = new Intent(InputManager.getInstance().getContext(), PreferencesActivity.class);
			((Activity) InputManager.getInstance().getContext()).startActivityForResult(intent,0);
		}
	}

	@Override
	public void doExit() {
		
	}
}
