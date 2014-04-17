package com.npogulanik.parquimetro;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.view.KeyEvent;

@SuppressLint("UseSparseArrays")
public class KeyMapResolver {
	private static  Map<Integer, String> myMap;
    private static KeyMapResolver instance;
	
	private KeyMapResolver(){
        myMap = new HashMap<Integer, String>();
        myMap.put(KeyEvent.KEYCODE_1, "1");
        myMap.put(KeyEvent.KEYCODE_2, "2");
        myMap.put(KeyEvent.KEYCODE_3, "3");
        myMap.put(KeyEvent.KEYCODE_4, "4");
        myMap.put(KeyEvent.KEYCODE_5, "5");
        myMap.put(KeyEvent.KEYCODE_6, "6");
        myMap.put(KeyEvent.KEYCODE_7, "7");
        myMap.put(KeyEvent.KEYCODE_8, "8");
        myMap.put(KeyEvent.KEYCODE_9, "9");
        myMap.put(KeyEvent.KEYCODE_0, "0");
 
        myMap.put(KeyEvent.KEYCODE_A, "A");
        myMap.put(KeyEvent.KEYCODE_B, "B");
        myMap.put(KeyEvent.KEYCODE_C, "C");
        myMap.put(KeyEvent.KEYCODE_D, "D");
        myMap.put(KeyEvent.KEYCODE_POUND, "#");
        myMap.put(KeyEvent.KEYCODE_STAR, "*");
    }
	
	public static KeyMapResolver getInstance(){
		if (instance == null){
			instance = new KeyMapResolver();
		}
		return instance;
	}
	
	public boolean isValidKey(Integer keyCode){
		if (myMap.containsKey(keyCode)){
			return true;
		}
		return false;
	}
    
	public String getInput(Integer keyCode) {
		if (myMap.containsKey(keyCode)){
			return myMap.get(keyCode);
		}
		return "";
	}  
}
