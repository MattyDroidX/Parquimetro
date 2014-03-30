package com.npogulanik.parquimetro;

public interface TimerCallBack {
	public void timeExpired();
	public void onTick(long millisUntilFinished);
}
