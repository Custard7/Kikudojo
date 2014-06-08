package com.omg.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.omg.drawing.JSActor;
import com.omg.ssplayer.Kiku.KikuAnim;
import com.omg.sswindler.GameManager;

public class Instructions extends JSActor {

	JSActor screen1;
	JSActor screen2;
	
	public enum InstructionState {
		
		screen1,
		screen2,
		away
		
	}
	
	InstructionState currentState = InstructionState.screen1;
	
	public InstructionState getState() {
		return currentState;
	}
	
	public void setState(InstructionState state) {
		
		switch(state) {
		
		case screen1:
			screen1.setVisible(true);
			screen2.setVisible(false);
			break;
		case screen2:
			screen1.setVisible(false);
			screen2.setVisible(true);
			break;
		case away:
			screen1.setVisible(false);
			screen2.setVisible(false);
			break;
		
		}
		
		this.currentState = state;
	}
	
	public Instructions() {
		
		screen1 = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("Instructions_Touch"),0,0,1280,720));
		screen1.setScale(2f);
		screen1.setPosition(-1080 , -220);
		this.addActor(screen1);
		
		screen2 = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("Instructions_Game"),0,0,1280,720));
		screen2.setScale(2f);
		screen2.setPosition(-1080 , -220);
		this.addActor(screen2);
		
		setState(InstructionState.screen1);
		
	}
	
	boolean spacePressed = false;

	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.justTouched() )
    	{
    		if(!spacePressed) {
    			
    			if(getState() == InstructionState.screen1) {
    				setState(InstructionState.screen2);
    			} else if(getState() == InstructionState.screen2) {
    				setState(InstructionState.away);
    			} else {
    				
    			}
    			
	    		
    		}
    		spacePressed = true;
    	} else {
    		spacePressed = false;
    	}
	}
	
	
}
