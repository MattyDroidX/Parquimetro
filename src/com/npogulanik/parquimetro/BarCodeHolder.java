package com.npogulanik.parquimetro;

public class BarCodeHolder {
	private static String mBarcode = "";
	
	public static boolean isSecondSwipe(String barcode){
		if (barcode.length() > 0){
			if (mBarcode.equals(barcode)){
				mBarcode = "";
				return true;
			} else {
				mBarcode = barcode;
				return false;
			}
		}
		return false;
	}

	public static void reset() {
		// TODO Auto-generated method stub
		mBarcode = "";
	}
}
