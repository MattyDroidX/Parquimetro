package com.npogulanik.parquimetro;

public class BarCodeHolder {
	private static String mBarcode = "";
	
	public static boolean isSecondSwipe(String barcode){
		if (mBarcode.equals(barcode)){
			mBarcode = "";
			return true;
		} else {
			mBarcode = barcode;
			return false;
		}
	}

	public static void reset() {
		// TODO Auto-generated method stub
		mBarcode = "";
	}
}
