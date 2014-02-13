package com.npogulanik.parquimetro;

public enum ErrorResponses {
    no_estacionado ("Vehiculo NO Estacionado"),
    estacionado ("Vehiculo Estacionado en este momento"),
    saldo_insuficiente ("Saldo Insuficiente para Estacionar");

    private final String message;       

    private ErrorResponses(String s) {
        message = s;
    }

    public boolean equalsMessage(String otherMessage){
        return (otherMessage == null)? false:message.equals(otherMessage);
    }

    public String toString(){
       return message;
    }

}
