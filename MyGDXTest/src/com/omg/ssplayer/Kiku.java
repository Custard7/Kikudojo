package com.omg.ssplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Kiku extends Jumpable {

	
	public Kiku(){
	
		setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/bum.png")),0,0,256/2,256));

		
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		setZIndex(10000);

    	
    	if(Gdx.input.isKeyPressed(Keys.SPACE))
    	{
    		jump(10.0f);
    	}
        
		
	}
	
	
	
}
