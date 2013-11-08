package com.omg.ssplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Player extends Ship {

	public Player() {
		super();

	}
	
	
	@Override
	public void act(float delta) {
		
		setZIndex(10000);

		if(Gdx.input.isKeyPressed(Keys.W))
			moveForward(0.25f);
		else if(Gdx.input.isKeyPressed(Keys.S))
			moveForward(-0.25f);
        	
    	if(Gdx.input.isKeyPressed(Keys.A)) {
    		rotate(.05f);
    	}
    	else if(Gdx.input.isKeyPressed(Keys.D)) {
    		rotate(-.10f);
    	}
    	
    	if(Gdx.input.isKeyPressed(Keys.SPACE))
    	{
    		shootLaser(1700.0f);
    	}
        
		
	}

}
