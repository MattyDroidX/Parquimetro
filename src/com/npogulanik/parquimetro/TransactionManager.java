package com.npogulanik.parquimetro;

import java.util.ArrayList;
import java.util.HashMap;

import com.npogulanik.parquimetro.activities.PromptChapa;
import com.npogulanik.parquimetro.entity.ParamsCredito;
import com.npogulanik.parquimetro.entity.ParamsEntrada;
import com.npogulanik.parquimetro.entity.ParamsSalida;
import com.npogulanik.parquimetro.entity.SaldoNuevo;
import com.npogulanik.parquimetro.fsm.OnErrorState;
import com.npogulanik.parquimetro.fsm.OnSuccessState;
import com.npogulanik.parquimetro.fsm.ParquimetroContext;
import com.npogulanik.parquimetro.fsm.PromptChapaState;
import com.npogulanik.parquimetro.fsm.PromptPostaState;
import com.npogulanik.parquimetro.ws.GetResponseCallback;
import com.npogulanik.parquimetro.ws.PostResponseCallback;
import com.npogulanik.parquimetro.ws.RestApi;

public class TransactionManager {
	private static TransactionManager instance;
	private static boolean isSucces;
	
	private TransactionManager(){
	}
	
	public static TransactionManager getInstance(){
		if (instance == null){
			instance = new TransactionManager();
		}
		return instance;
	}
	
	public void performEntrada(final String chapa, final String posta){
		ParamsEntrada entrada = new ParamsEntrada(chapa, posta);
		RestApi myApi = RestApi.getInstance();
		myApi.postEntrada(entrada, new PostResponseCallback() {
			@Override
			public void onSuccess(String saldo) {
				ParquimetroContext context = ParquimetroContext.getInstance();
				context.setState(new OnSuccessState("ALTA EXITOSA", ""));
			};

			@Override
			public void onError(String message) {
				ParquimetroContext context = ParquimetroContext.getInstance();
				context.setState(new OnErrorState(message));
			}

			@Override
			public void onSuccess(SaldoNuevo result) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public void performSalida(final String chapa){
		ParamsSalida salida = new ParamsSalida(chapa,"1");
		RestApi myApi = RestApi.getInstance();
        myApi.postSalida(salida, new PostResponseCallback() {
            @Override
			public void onSuccess(String saldo) {
            	ParquimetroContext context = ParquimetroContext.getInstance();
            	context.setState(new OnSuccessState("BAJA EXITOSA","Saldo: $:" + saldo)); 
            };
            
            @Override
			public void onError(String message) {
            	if (ErrorResponses.no_estacionado.equalsMessage(message)){
            		//performEntrada(chapa);
            		ParquimetroContext context = ParquimetroContext.getInstance();
            		context.setState(new PromptPostaState(chapa));
            	}
            }

			@Override
			public void onSuccess(SaldoNuevo result) {
				// TODO Auto-generated method stub
				
			}
        });
	}
	
	public void performCredito(final String chapa){
		RestApi myApi = RestApi.getInstance();
        myApi.getSaldo(new ParamsCredito(chapa),new PostResponseCallback() {
            @Override
			public void onSuccess(SaldoNuevo result) {
            	ArrayList<HashMap<String,String>> chapasList = new ArrayList<HashMap<String,String>>();
            	for (int i=0;i<result.getAsociado().size();i++){
            		HashMap<String,String> map = new HashMap<String,String>();
            		map.put(PromptChapa.KEY_CHAPA,result.getAsociado().get(i).getChapa()); 
            		map.put(PromptChapa.KEY_CREDITO, result.getAsociado().get(i).getSaldo());
            		chapasList.add(map);
            	}
            	//ParquimetroContext context = ParquimetroContext.getInstance();
              	//context.setState(new PromptChapaState(chapasList)); 
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
	
	public void sendTransaction(String chapa){
		performSalida(chapa);
	}
}
