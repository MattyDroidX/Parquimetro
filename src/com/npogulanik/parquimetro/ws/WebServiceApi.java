package com.npogulanik.parquimetro.ws;

import com.npogulanik.parquimetro.entity.ParamsCredito;
import com.npogulanik.parquimetro.entity.ParamsEntrada;
import com.npogulanik.parquimetro.entity.ParamsSalida;

public interface WebServiceApi {
	public void getSaldo(ParamsCredito credito, final PostResponseCallback callback);
	public void postEntrada(ParamsEntrada entrada, final PostResponseCallback callback);
	public void postSalida(ParamsSalida salida, final PostResponseCallback callback);
}
