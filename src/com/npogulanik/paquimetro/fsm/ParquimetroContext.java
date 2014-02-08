package com.npogulanik.paquimetro.fsm;

import android.content.Context;

public class ParquimetroContext implements State {
    private State state;
    
    public void setState(State state){
    	this.state = state;
    }
    
    public State getState(){
    	return this.state;
    }
    
	@Override
	public void doAction() {
        this.state.doAction();
	}

}
