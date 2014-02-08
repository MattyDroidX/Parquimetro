package com.npogulanik.parquimetro;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.npogulanik.parquimetro.entity.ConsultaCredito;
import com.npogulanik.parquimetro.entity.Entrada;
import com.npogulanik.parquimetro.entity.ParamsEntrada;
import com.npogulanik.parquimetro.entity.ParamsSalida;
import com.npogulanik.parquimetro.entity.Salida;

public class RestClient {
	
	public ConsultaCredito doSaldo(String chapa){
		 try{ 
			 final String url = "http://190.224.102.100:8080/EstacionamientoV2/rest/WorkWithDevicesEMCredito_EMCredito_List_Grid?CreditoChapa=LEV843&fmt=json&count=1&gxid=9";		
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
			 final String url = "http://190.224.102.100:8080/EstacionamientoV4/rest/EntradaWSdesa";		
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
			 final String url = "http://190.224.102.100:8080/EstacionamientoV4/rest/SalidaWSdesa";		
		     RestTemplate restTemplate = new RestTemplate();
		     restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		     Salida salida = restTemplate.postForObject(url, params,Salida.class);
		     return salida;
		 } catch (Exception e){
			 e.printStackTrace();
			 return null;
		 }
	}

}
