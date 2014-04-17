package com.npogulanik.parquimetro.fsm;

import com.npogulanik.parquimetro.InputManager;
import com.npogulanik.parquimetro.NumeroPostaCallback;
import com.npogulanik.parquimetro.TransactionManager;
import com.npogulanik.parquimetro.activities.PromptPostaManual;

public class PromptPostaManualState implements State {
	private String chapa;
	
	public PromptPostaManualState(String chapa){
		this.chapa = chapa;
	}
	
	
	@Override
	public void doAction() {
		PromptPostaManual dialog = new PromptPostaManual(InputManager.getInstance().getContext(),new NumeroPostaCallback(){
			@Override
			public void onPosta(String posta) {
				TransactionManager.getInstance().performEntrada(chapa, posta);
			}

			@Override
			public void onTimeout() {
				// TODO Auto-generated method stub
				ParquimetroContext context = ParquimetroContext.getInstance();
				context.setState(new IdleState());		
			}
		});
		dialog.show();
		
	}

	@Override
	public void doExit() {
		
	}
}
