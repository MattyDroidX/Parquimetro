package com.npogulanik.parquimetro;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class PromptDialog extends Dialog implements
		android.view.View.OnClickListener {

	public Button yes, no;

	public PromptDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.prompt);
		yes = (Button) findViewById(R.id.aceptar);
		no = (Button) findViewById(R.id.cancelar);
		yes.setOnClickListener(this);
		no.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.aceptar:
			break;
		case R.id.cancelar:
			dismiss();
			break;
		default:
			break;
		}
		dismiss();
	}
}
