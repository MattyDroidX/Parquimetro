package com.npogulanik.parquimetro;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.npogulanik.parquimetro.entity.AbstractTransaction;
import com.npogulanik.parquimetro.entity.ConsultaCredito;
import com.npogulanik.parquimetro.entity.Entrada;
import com.npogulanik.parquimetro.entity.ParamsEntrada;
import com.npogulanik.parquimetro.entity.ParamsSalida;
import com.npogulanik.parquimetro.entity.Salida;
import com.npogulanik.parquimetro.entity.TransactionType;

public class RestClient {
	
	public ConsultaCredito doSaldo(String chapa){
		 try{ 
			 final String url = TransactionType.CONSULTA_CREDITO.getUrl();
		     RestTemplate restTemplate = new RestTemplate();
		     restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		     ConsultaCredito[] credito = restTemplate.getForObject(url, ConsultaCredito[].class);
		     return credito[0];
		 } catch (Exception e){
			 e.printStackTrace();
			 return null;
		 }
	}
	
	public Entrada doEntrada(ParamsEntrada params){
		 try{ 
			 final String url = TransactionType.ENTRADA.getUrl();		
		     RestTemplate restTemplate = new RestTemplate();
		     restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		     Entrada entrada = restTemplate.postForObject(url, params,Entrada.class);
		     return entrada;
		 } catch (Exception e){
			 e.printStackTrace();
			 return null;
		 }
	}
	
	public Salida doSalida(ParamsSalida params){
		 try{ 
			 final String url = TransactionType.SALIDA.getUrl();		
		     RestTemplate restTemplate = new RestTemplate();
		     restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		     Salida salida = restTemplate.postForObject(url, params,Salida.class);
		     return salida;
		 } catch (Exception e){
			 e.printStackTrace();
			 return null;
		 }
	}
/*	
	private class HttpRequestTask extends AsyncTask<String, Void, TransactionType> {
		private ProgressDialog dialog;
		private Exception mException;
		protected Context applicationContext = DisplayManager.getInstance().getContext();
		private ConsultaCredito credito;
		private String chapa;
		protected RestClient restClient;
		
        
		
		@Override
		protected void onPreExecute() {
			this.dialog = ProgressDialog.show(applicationContext, "Procesando", " Ticket de Entrada...", true);
		}


        @Override
        protected AbstractTransaction doInBackground(TransactionType...type) {
            try {
            	//chapa = new DummyChapaResolver().getChapa(cardNumber[0]);
            	//ParamsEntrada paramsEntrada = new ParamsEntrada(chapa,"1","1");
            	//credito = restClient.doSaldo(chapa);
            	if (type[0] == TransactionType.CONSULTA_CREDITO){
            		return doSaldo(chapa);
            	} else if (type[0] == TransactionType.ENTRADA){
            	
            	} else if (type[0] == TransactionType.SALIDA){
            		
            	}
            	
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                mException = e;
                //Toast.makeText(applicationContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(AbstractTransaction transaction) {
        	this.dialog.cancel();
        	if (mException != null){
        		Toast.makeText(applicationContext, "Error ->" +  mException.getMessage(), Toast.LENGTH_SHORT).show();        		
        	}else{
	        	if (credito != null){
	        		//Toast.makeText(applicationContext, "RESPUESTA DEL WS ->" +  entrada.getEntrada().getLugar(), Toast.LENGTH_SHORT).show();
	        	    displayManager.showMessage(mTopFlipper, "CREDITO DISPONIBLE DOMINIO " + chapa + " " + credito.getCreditoSaldo());	//TODO agregar timer como parametro    	}
        	}
        	//displayManager.stopFlipping(mTopFlipper);
       	    displayManager.showMessage(mBottomFlipper, getString(R.string.text_swipe));
        }

    }
	}
*/
}
