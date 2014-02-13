package com.npogulanik.parquimetro.ws;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.npogulanik.parquimetro.DisplayManager;
import com.npogulanik.parquimetro.entity.Entrada;
import com.npogulanik.parquimetro.entity.IParams;
import com.npogulanik.parquimetro.entity.IResult;
import com.npogulanik.parquimetro.entity.ParamsEntrada;
import com.npogulanik.parquimetro.entity.ParamsSalida;
import com.npogulanik.parquimetro.entity.Salida;
import com.npogulanik.parquimetro.entity.TransactionType;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class PostTask extends AsyncTask<IParams, String, IResult>{
    private String mRestUrl;
    private RestTaskCallback mCallback;
    private TransactionType mType;
    private Exception mException;
    private IParams mParams;
    private ProgressDialog dialog;

    /**
     * Creates a new instance of PostTask with the specified URL, callback, and
     * request body.
     * 
     * @param restUrl The URL for the REST API.
     * @param callback The callback to be invoked when the HTTP request
     *            completes.
     * @param requestBody The body of the POST request.
     * 
     */
    public PostTask(String restUrl,IParams params, TransactionType type, RestTaskCallback callback){
        this.mRestUrl = restUrl;
        this.mType = type;
        this.mCallback = callback;
        this.mParams = params;
    }
    
    @Override
	protected void onPreExecute() {
		this.dialog = ProgressDialog.show(DisplayManager.getInstance().getContext(), "Procesando..."
				, "Ticket de " + (this.mType == TransactionType.ENTRADA ? "Entrada" : "Salida"), true);
	}

    @Override
    protected IResult doInBackground(IParams... arg0) {
        try {
	        IResult result;
		    RestTemplate restTemplate = new RestTemplate();
		    ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(1000*30);
		    ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(1000*30);
		    restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		    
		    if (mType == TransactionType.ENTRADA){
		    	result = restTemplate.postForObject(this.mRestUrl, mParams,Entrada.class);
		    } else {
		    	result = restTemplate.postForObject(this.mRestUrl, mParams,Salida.class);
		    }
		    return result;
	        
        } catch(Exception e){
        	Log.e("Error en PostTask", e.getMessage());
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