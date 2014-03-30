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
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final String postaA = SP.getString("prefPostaA", "");		
		final String postaB = SP.getString("prefPostaB", "");
		final String postaC = SP.getString("prefPostaC", "");		
		final String postaD = SP.getString("prefPostaD", "");
		
		listPostas.clear();
		if (postaA != null && postaA.length() > 0){
			HashMap<String,String> map = new HashMap<String,String>();
    		map.put(PromptPosta.KEY_LETRA,"A");
    		map.put(PromptPosta.KEY_NUMERO,postaA);
			listPostas.add(map);
		}
		if (postaB != null && postaB.length() > 0){
			HashMap<String,String> map = new HashMap<String,String>();
    		map.put(PromptPosta.KEY_LETRA,"B");
    		map.put(PromptPosta.KEY_NUMERO,postaB);
			listPostas.add(map);
		}
		if (postaC != null && postaC.length() > 0){
			HashMap<String,String> map = new HashMap<String,String>();
    		map.put(PromptPosta.KEY_LETRA,"C");
    		map.put(PromptPosta.KEY_NUMERO,postaC);
			listPostas.add(map);
		}
		if (postaD != null && postaD.length() > 0){
			HashMap<String,String> map = new HashMap<String,String>();
    		map.put(PromptPosta.KEY_LETRA,"D");
    		map.put(PromptPosta.KEY_NUMERO,postaD);
			listPostas.add(map);
		}
	}
	
	@Override
	public void doAction() {
		loadPostas();
		if (listPostas.size() >= 1){
			PromptPosta dialog = new PromptPosta(InputManager.getInstance().getContext(),listPostas,new NumeroPostaCallback(){
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
