package com.npogulanik.parquimetro.ws;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.entity.ConsultaCredito;
import com.npogulanik.parquimetro.entity.IResult;
import com.npogulanik.parquimetro.entity.Saldo;
import com.npogulanik.parquimetro.entity.TransactionType;

public class GetTask extends AsyncTask<String, String, IResult>{

    private String mRestUrl;
    private RestTaskCallback mCallback;
    private Exception mException;
    private ProgressDialog dialog;
    /**
     * Creates a new instance of GetTask with the specified URL and callback.
     * 
     * @param restUrl The URL for the REST API.
     * @param callback The callback to be invoked when the HTTP request
     *            completes.
     * 
     */
    public GetTask(String restUrl, RestTaskCallback callback){
        this.mRestUrl = restUrl;
        this.mCallback = callback;
    }
    
    @Override
	protected void onPreExecute() {
		this.dialog = ProgressDialog.show(DisplayManager.getInstance().getContext(), "Procesando..."
				, "Consulta de Crédito", true);
	}
    

    @Override
    protected IResult doInBackground(String... params) {
        try{
	    	RestTemplate restTemplate = new RestTemplate();
	    	((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(1000*30);
	    	((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(1000*30);
	    	restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
	    	ConsultaCredito[] credito = restTemplate.getForObject(this.mRestUrl, ConsultaCredito[].class);
	    	Saldo saldo = new Saldo(credito[0].getCreditoSaldo());
		    return saldo;
        } catch (Exception e){
        	Log.e("Error en GetTask",e.getMessage());
        	mException = e;
        	return null;
        }
    }

    @Override
    protected void onPostExecute(IResult result) {
    	this.dialog.cancel();
    	if (result != null){
        	mCallback.onTaskSuccess(result);
        } else {
        	mCallback.onTaskError(mException.getMessage());
        }
        super.onPostExecute(result);
    }
}
