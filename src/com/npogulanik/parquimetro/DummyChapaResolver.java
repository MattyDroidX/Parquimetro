package com.npogulanik.parquimetro;

import java.util.HashMap;
import java.util.Map;

public class DummyChapaResolver {	
	private static  Map<String, String> myMap;
    private static DummyChapaResolver instance;
	
	private DummyChapaResolver()
    {
        myMap = new HashMap<String, String>();
        myMap.put("0005937829", "EFV883");
        myMap.put("0005715176", "KVG179");
        myMap.put("0005745671", "GOG859");
        myMap.put("0006062562", "MPN500");
        myMap.put("0007992103", "HEX968");
        myMap.put("0007990420", "KWD046");
    }
	
	public static DummyChapaResolver getInstance(){
		if (instance == null){
			instance = new DummyChapaResolver();
		}
		return instance;
	}
    
	public String getChapa(String cardNumber) {
		if (myMap.containsKey(cardNumber)){
			return myMap.get(cardNumber);
		}
		return "HRK386";
	}
	
	public String getTarjeta(){
		return "5918825";
	}
    
}
