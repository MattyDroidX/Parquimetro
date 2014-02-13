package com.npogulanik.parquimetro.ws;

import com.google.gson.Gson;
import com.npogulanik.parquimetro.entity.Entrada;
import com.npogulanik.parquimetro.entity.IResult;
import com.npogulanik.parquimetro.entity.ParamsEntrada;
import com.npogulanik.parquimetro.entity.ParamsSalida;
import com.npogulanik.parquimetro.entity.Saldo;
import com.npogulanik.parquimetro.entity.Salida;
import com.npogulanik.parquimetro.entity.TransactionType;

public class RestApi{   
    private static RestApi instance; 
	
    public static RestApi getInstance(){
        //Choose an appropriate creation strategy.
    	if (instance == null){
    		instance = new RestApi();
    	}
    	return instance;
    }

    /**
     * Request a User Profile from the REST server.
     * @param userName The user name for which the profile is to be requested.
     * @param callback Callback to execute when the profile is available.
     */
    public void getSaldo(String chapa, final GetResponseCallback callback){
        String restUrl = String.format(TransactionType.CONSULTA_CREDITO.getUrl(),chapa);
        new GetTask(restUrl, new RestTaskCallback (){
            @Override
            public void onTaskSuccess(IResult response){
            	Saldo saldo = (Saldo) response;
                callback.onSuccess(saldo.getSaldo());
            };
            public void onTaskError(String response){
                callback.onError(response);
            }
        }).execute();
    }

    /**
     * Submit a user profile to the server.
     * @param profile The profile to submit
     * @param callback The callback to execute when submission status is available.
     */
    public void postEntrada(ParamsEntrada entrada, final PostResponseCallback callback){
        String restUrl = TransactionType.ENTRADA.getUrl();
        new PostTask(restUrl, entrada,TransactionType.ENTRADA, new RestTaskCallback(){
            public void onTaskSuccess(IResult response){
            	Entrada entrada = (Entrada) response;
            	if (entrada.getEntrada().getDominio().length() > 0){
                	callback.onSuccess(entrada.getEntrada().getSaldo());
            	} else {
            		onTaskError(entrada.getEntrada().getLugar());
            	}
            };
            public void onTaskError(String response){
                callback.onError(response);
            };
        }).execute();
    }
    
    public void postSalida(ParamsSalida salida, final PostResponseCallback callback){
        String restUrl = TransactionType.SALIDA.getUrl();
        new PostTask(restUrl, salida, TransactionType.SALIDA, new RestTaskCallback(){
            public void onTaskSuccess(IResult response){
            	Salida salida = (Salida)response;
            	if (salida.getSalida().getDominio().length() > 0){
                	callback.onSuccess(salida.getSalida().getSaldo());
            	} else {
            		onTaskError(salida.getSalida().getLugar());
            	}
            };
            public void onTaskError(String response){
                callback.onError(response);
            };
        }).execute();
    }
}




/**
 * 
 * Class definition for a callback to be invoked when the response for the data 
 * submission is available.
 * 
 */

