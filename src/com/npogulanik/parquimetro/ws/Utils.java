package com.npogulanik.parquimetro.ws;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.npogulanik.parquimetro.entity.TransactionType;

public class Utils {
	public static String constructRestUrlForSaldo(String chapa){
		return String.format(TransactionType.CONSULTA_CREDITO.getUrl(),chapa);
	}
	
	public static String serializeObjectAsString(Object object){
		Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
	}
	
	public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;   
    }
}
