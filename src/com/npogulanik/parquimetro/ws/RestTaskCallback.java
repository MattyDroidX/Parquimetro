package com.npogulanik.parquimetro.ws;

import com.npogulanik.parquimetro.entity.IResult;

public abstract class RestTaskCallback{
    /**
     * Called when the HTTP request completes.
     * 
     * @param result The result of the HTTP request.
     */
    public abstract void onTaskSuccess(IResult result);
    public abstract void onTaskError(String result);
}
