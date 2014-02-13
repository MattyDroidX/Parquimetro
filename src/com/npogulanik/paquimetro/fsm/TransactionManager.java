package com.npogulanik.paquimetro.fsm;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.ErrorResponses;
import com.npogulanik.parquimetro.entity.ParamsEntrada;
import com.npogulanik.parquimetro.entity.ParamsSalida;
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
	
	private void performEntrada(String chapa){
		ParamsEntrada entrada = new ParamsEntrada(chapa,"1","1");
		RestApi myApi = RestApi.getInstance();
        myApi.postEntrada(entrada, new PostResponseCallback() {
          @Override
			public void onSuccess(String saldo) {
          	ParquimetroContext context = ParquimetroContext.getInstance();
          	context.setState(new OnSuccessState("ALTA EXITOSA","")); 
          	//context.doAction();
          };
          
          @Override
			public void onError(String message) {
          	ParquimetroContext context = ParquimetroContext.getInstance();
          	context.setState(new OnErrorState(message)); 
          	//context.doAction();    	
          }
      });
	}
	
	private void performSalida(final String chapa){
		ParamsSalida salida = new ParamsSalida(chapa,"1");
		RestApi myApi = RestApi.getInstance();
        myApi.postSalida(salida, new PostResponseCallback() {
            @Override
			public void onSuccess(String saldo) {
            	ParquimetroContext context = ParquimetroContext.getInstance();
            	context.setState(new OnSuccessState("BAJA EXITOSA","Saldo: $:" + saldo)); 
            	//context.doAction();
            };
            
            @Override
			public void onError(String message) {
            	if (ErrorResponses.no_estacionado.equalsMessage(message)){
            		performEntrada(chapa);
            	}
            }
        });
	}
	
	public void performCredito(final String chapa){
		RestApi myApi = RestApi.getInstance();
        myApi.getSaldo(chapa, new GetResponseCallback() {
            @Override
			public void onSuccess(String result) {
            	ParquimetroContext context = ParquimetroContext.getInstance();
            	//context.setState(new OnSuccessState("BAJA EXITOSA","Su Saldo actual es:" + saldo)); 
            	//context.doAction();
            	Double saldo = Double.parseDouble(result);
            	if (saldo <= 0 ){
            		onError("SALDO INSUFICIENTE");
            	} else {
            		DisplayManager.getInstance().showSaldoText("Saldo: $" + result);
            	}
            };
            
            @Override
			public void onError(String message) {
            	ParquimetroContext context = ParquimetroContext.getInstance();
              	context.setState(new OnErrorState(message)); 
				//context.doAction(); 
            }
        });
	}
	
	public void sendTransaction(String chapa){
		performSalida(chapa);
	}

	public static boolean isSucces() {
		return true;
	}

	public static void setSucces(boolean isSucces) {
		TransactionManager.isSucces = isSucces;
	}
	
	
}
