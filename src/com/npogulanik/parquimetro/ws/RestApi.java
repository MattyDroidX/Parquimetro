package com.npogulanik.parquimetro.ws;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.TransactionType;
import com.npogulanik.parquimetro.entity.Entrada;
import com.npogulanik.parquimetro.entity.EntradaNueva;
import com.npogulanik.parquimetro.entity.IResult;
import com.npogulanik.parquimetro.entity.ParamsCredito;
import com.npogulanik.parquimetro.entity.ParamsEntrada;
import com.npogulanik.parquimetro.entity.ParamsSalida;
import com.npogulanik.parquimetro.entity.Saldo;
import com.npogulanik.parquimetro.entity.SaldoNuevo;
import com.npogulanik.parquimetro.entity.Salida;

public class RestApi implements WebServiceApi{   
    private static RestApi instance; 
	
    public static RestApi getInstance(){
        //Choose an appropriate creation strategy.
    	if (instance == null){
    		instance = new RestApi();
    	}
    	return instance;
    }

 
    public void getSaldo(ParamsCredito credito, final PostResponseCallback callback){
    	SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final String restUrl = SP.getString("prefWSCredito", "");
    	new PostTask(restUrl, credito,TransactionType.CONSULTA_CREDITO, new RestTaskCallback(){
            @Override
            public void onTaskSuccess(IResult response){
            	SaldoNuevo saldo = (SaldoNuevo) response;
                callback.onSuccess(saldo);
            };
            public void onTaskError(String response){
                callback.onError(response);
            }
        }).execute();
    }

 
    public void postEntrada(ParamsEntrada entrada, final PostResponseCallback callback){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final String restUrl = SP.getString("prefWSAlta", "");
        new PostTask(restUrl, entrada,TransactionType.ENTRADA, new RestTaskCallback(){
            public void onTaskSuccess(IResult response){
            	EntradaNueva entrada = (EntradaNueva) response;
            	if (entrada.getHoraMax().length() > 0){
                	callback.onSuccess(entrada.getHoraMax());
            	} else {
            		onTaskError(entrada.getError());
            	}
            };
            public void onTaskError(String response){
                callback.onError(response);
            };
        }).execute();
    }
    
    public void postSalida(ParamsSalida salida, final PostResponseCallback callback){
    	SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(DisplayManager.getInstance().getContext());
		final String restUrl = SP.getString("prefWSBaja", "");
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

