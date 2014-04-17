package com.npogulanik.parquimetro.fsm;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.DummyChapaResolver;
import com.npogulanik.parquimetro.ErrorResponses;
import com.npogulanik.parquimetro.TransactionManager;
import com.npogulanik.parquimetro.entity.ParamsSalida;
import com.npogulanik.parquimetro.entity.SaldoNuevo;
import com.npogulanik.parquimetro.ws.PostResponseCallback;
import com.npogulanik.parquimetro.ws.RestApi;

public class SendingTransactionState implements State {
    private String chapa;
	
	public SendingTransactionState(String chapa) {
		// TODO Auto-generated constructor stub
		this.chapa = chapa;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		DisplayManager.getInstance().stopFlippingBottom();
		DisplayManager.getInstance().stopTimerText();
		DisplayManager.getInstance().resetSaldo();
		//DisplayManager.getInstance().setTopMessage("Procesando Transaccion");  
		//String chapa = DummyChapaResolver.getInstance().getChapa(barcode);
		//TransactionManager transactionManager = TransactionManager.getInstance();
		//transactionManager.sendTransaction(chapa);
		performSalida(chapa);
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
            	ParquimetroContext context = ParquimetroContext.getInstance();
            	if (ErrorResponses.no_estacionado.equalsMessage(message)){
            		context.setState(new PromptPostaState(chapa));
            	} else {
                  	context.setState(new OnErrorState(message)); 
            	}
            }

			@Override
			public void onSuccess(SaldoNuevo result) {
				// TODO Auto-generated method stub
				
			}
        });
	}

	@Override
	public void doExit() {
		// TODO Auto-generated method stub
		
	}

}
