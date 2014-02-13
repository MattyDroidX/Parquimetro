package com.npogulanik.parquimetro.entity;

public class Salida implements IResult{
	private Response salida;
	
	public Response getSalida() {
		return salida;
	}
	
	public void setSalida(Response salida) {
		this.salida = salida;
	}
	
	public static class Response {
		String Numero;
		String Ano;
		String Tarifa;
		String Posta;
		String Zona;
		String Dominio;
		String Fin;
		String Lugar;
		String Importe;
		String Saldo;
		
		
		public String getNumero() {
			return Numero;
		}
		public void setNumero(String numero) {
			Numero = numero;
		}
		public String getAno() {
			return Ano;
		}
		public void setAno(String ano) {
			Ano = ano;
		}
		public String getTarifa() {
			return Tarifa;
		}
		public void setTarifa(String tarifa) {
			Tarifa = tarifa;
		}
		public String getPosta() {
			return Posta;
		}
		public void setPosta(String posta) {
			Posta = posta;
		}
		public String getZona() {
			return Zona;
		}
		public void setZona(String zona) {
			Zona = zona;
		}
		public String getDominio() {
			return Dominio;
		}
		public void setDominio(String dominio) {
			Dominio = dominio;
		}
		public String getFin() {
			return Fin;
		}
		public void setFin(String fin) {
			Fin = fin;
		}
		public String getLugar() {
			return Lugar;
		}
		public void setLugar(String lugar) {
			Lugar = lugar;
		}
		public String getImporte() {
			return Importe;
		}
		public void setImporte(String importe) {
			Importe = importe;
		}
		public String getSaldo() {
			return Saldo;
		}
		public void setSaldo(String saldo) {
			Saldo = saldo;
		}
        
    }
	
	
}
