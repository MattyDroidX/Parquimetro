package com.npogulanik.parquimetro;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.gson.Gson;

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
	
	public static void HideStatusBar(Context context){
		try{
			String command;
			command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib service call activity 42 s16 com.android.systemui";
			Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", command });
			proc.waitFor();
		} catch(Exception ex){
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	
	public static void ShowStatusBar(){
		try {
			String command;
			command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib am startservice -n com.android.systemui/.SystemUIService";
			Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", command });
			proc.waitFor();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
