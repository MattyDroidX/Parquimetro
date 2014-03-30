package com.npogulanik.parquimetro.fsm;

import java.util.ArrayList;
import java.util.HashMap;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.DummyChapaResolver;
import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.NumeroChapaCallback;
import com.npogulanik.parquimetro.activities.PromptChapa;
import com.npogulanik.parquimetro.entity.ParamsCredito;
import com.npogulanik.parquimetro.entity.SaldoNuevo;
import com.npogulanik.parquimetro.ws.PostResponseCallback;
import com.npogulanik.parquimetro.ws.RestApi;

public class PromptChapaState implements State {
	private ArrayList<HashMap<String,String>> chapasList;
	private String tarjeta;
	
	public PromptChapaState(String tarjeta){
		this.tarjeta = tarjeta;
	}
	
	@Override
	public void doAction() {
		InputManager.getInstance().disableInput();
		DisplayManager.getInstance().stopFlippingTop();
		DisplayManager.getInstance().stopFlippingBottom();
		
		//TODO harcodeo la tarjeta solo para probar
		//String tarjeta = DummyChapaResolver.getInstance().getTarjeta();
		
		performCredito(tarjeta);
	}
	
	private void performCredito(String tarjeta){
		//String tarjeta = DummyChapaResolver.getInstance().getTarjeta();
		RestApi myApi = RestApi.getInstance();
        myApi.getSaldo(new ParamsCredito(tarjeta),new PostResponseCallback() {
            @Override
			public void onSuccess(SaldoNuevo result) {
            	ArrayList<HashMap<String,String>> chapasList = new ArrayList<HashMap<String,String>>();
            	for (int i=0;i<result.getAsociado().size();i++){
            		HashMap<String,String> map = new HashMap<String,String>();
            		map.put(PromptChapa.KEY_CHAPA,result.getAsociado().get(i).getChapa()); 
            		map.put(PromptChapa.KEY_CREDITO, result.getAsociado().get(i).getSaldo());
            		chapasList.add(map);
            	}
            	PromptChapa dialog = new PromptChapa(InputManager.getInstance().getContext(),chapasList, new NumeroChapaCallback(){
					@Override
					public void onChapa(String chapa) {
						// TODO Auto-generated method stub
						ParquimetroContext context = ParquimetroContext.getInstance();
						context.setState(new SendingTransactionState(chapa));
					}

					@Override
					public void onTimeout() {
						// TODO Auto-generated method stub
						ParquimetroContext context = ParquimetroContext.getInstance();
						context.setState(new IdleState());
					}
            		
            	});
        		dialog.show();
            };
            
            @Override
			public void onError(String message) {
            	ParquimetroContext context = ParquimetroContext.getInstance();
              	context.setState(new OnErrorState(message)); 
            }

			@Override
			public void onSuccess(String message) {
				// TODO Auto-generated method stub
				
			}
        });
	}

	@Override
	public void doExit() {
		
	}
}
