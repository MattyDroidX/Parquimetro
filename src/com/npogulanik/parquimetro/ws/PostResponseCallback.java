package com.npogulanik.parquimetro.ws;

public abstract class PostResponseCallback{
    /**
     * Called when a POST success response is received. <br/>
     * This method is guaranteed to execute on the UI thread.
     */
    public abstract void onSuccess(String message);
    public abstract void onError(String message);
    

}
