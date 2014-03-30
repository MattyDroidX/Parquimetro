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
        myMap.put(KeyEvent.KEYCODE_U, "1");
        myMap.put(KeyEvent.KEYCODE_Y, "2");
        myMap.put(KeyEvent.KEYCODE_J, "3");
        myMap.put(KeyEvent.KEYCODE_NUMPAD_9, "4");
        myMap.put(KeyEvent.KEYCODE_DPAD_RIGHT, "5");
        myMap.put(KeyEvent.KEYCODE_PAGE_DOWN, "6");
        myMap.put(KeyEvent.KEYCODE_DPAD_UP, "7");
        myMap.put(KeyEvent.KEYCODE_NUMPAD_5, "8");
        myMap.put(KeyEvent.KEYCODE_DPAD_DOWN, "9");
        myMap.put(KeyEvent.KEYCODE_MOVE_HOME, "*");
        myMap.put(KeyEvent.KEYCODE_DPAD_LEFT, "0");
        myMap.put(KeyEvent.KEYCODE_MOVE_END, "#");
        myMap.put(KeyEvent.KEYCODE_H, "A");
        myMap.put(KeyEvent.KEYCODE_FORWARD_DEL, "B");
        myMap.put(KeyEvent.KEYCODE_INSERT, "C");
        myMap.put(KeyEvent.KEYCODE_SPACE, "D");
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
