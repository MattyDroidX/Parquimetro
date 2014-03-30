package com.npogulanik.parquimetro.fsm;


public class ParquimetroContext implements State {
    private State state;
    private static ParquimetroContext instance;
    
    private ParquimetroContext(){};
    
    public static ParquimetroContext getInstance(){
    	if (instance == null){
    		instance = new ParquimetroContext();
    	}
    	return instance;
    }
    
    public void setState(State state){
    	this.doExit();
    	this.state = state;
    	this.doAction();
    }
    
    public State getState(){
    	return this.state;
    }
    
	@Override
	public void doAction() {
		if (this.state != null){
			this.state.doAction();
		}
	}

	@Override
	public void doExit() {
		if (this.state != null){
			this.state.doExit();
		}
	}

}
