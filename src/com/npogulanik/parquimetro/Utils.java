package com.npogulanik.parquimetro;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

public class Utils {
	
	/* 
     * Information of all APNs
     * Details can be found in com.android.providers.telephony.TelephonyProvider
     */
    public static final Uri APN_TABLE_URI = 
        Uri.parse("content://telephony/carriers");
    /* 
     * Information of the preferred APN
     * 
     */
    public static final Uri PREFERRED_APN_URI =
        Uri.parse("content://telephony/carriers/preferapn");  
    /*
     * 
     */
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
	
	public static int InsertAPN(Context context) 
    {
        int id = -1;
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", "movistar");
        values.put("apn", "internet");
        //values.put("user", "internet");
        //values.put("password", "internet");
        
        
        /*
         * The following three field values are for testing in Android emulator only
         * The APN setting page UI will ONLY display APNs whose 'numeric' filed is 
         * TelephonyProperties.PROPERTY_SIM_OPERATOR_NUMERIC.
         * On Android emulator, this value is 310260, where 310 is mcc, and 260 mnc.
         * With these field values, the newly added apn will appear in system UI.
         */
        values.put("mcc", "722");
        values.put("mnc", "07");
        values.put("numeric", "310260");
        
        Cursor c = null;
        try
        {
            Uri newRow = resolver.insert(APN_TABLE_URI, values);
            if(newRow != null)
            {
                c = resolver.query(newRow, null, null, null, null);
                //Log.d(TAG, "Newly added APN:");
                //printAllData(c); //Print the entire result set
                
                // Obtain the apn id
                int idindex = c.getColumnIndex("_id");
                c.moveToFirst();
                id = c.getShort(idindex);
                //Log.d(TAG, "New ID: " + id + ": Inserting new APN succeeded!");
            }
        }
        catch(SQLException e)
        {
            //Log.d(TAG, e.getMessage());
        }

        if(c !=null ) 
            c.close();
        return id;
    }
}
