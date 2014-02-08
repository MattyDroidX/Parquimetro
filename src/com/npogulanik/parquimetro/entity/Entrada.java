package com.npogulanik.parquimetro.entity;


public class Entrada {
	private Response entrada;
	
	public Response getEntrada() {
		return entrada;
	}
	
	public void setEntrada(Response entrada) {
		this.entrada = entrada;
	}
	
	public static class Response {
		String Numero;
		String Ano;
		String Tarifa;
		String Posta;
		String Zona;
		String Dominio;
		String Inicio;
		String Maximo;
		String Lugar;
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
		public String getInicio() {
			return Inicio;
		}
		public void setInicio(String inicio) {
			Inicio = inicio;
		}
		public String getMaximo() {
			return Maximo;
		}
		public void setMaximo(String maximo) {
			Maximo = maximo;
		}
		public String getLugar() {
			return Lugar;
		}
		public void setLugar(String lugar) {
			Lugar = lugar;
		}
		public String getSaldo() {
			return Saldo;
		}
		public void setSaldo(String saldo) {
			Saldo = saldo;
		}
        
    }
	
	
}
