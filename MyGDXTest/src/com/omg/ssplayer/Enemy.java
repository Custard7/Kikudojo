package com.omg.ssplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends Ship {

	
	public Enemy() {
		super();
		
		
		setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/ship_red.png")),0,0,256,256));
	}
	
	
	float delay = 1;
	float current = 0;
	
	@Override
	public void act(float delta) {
		
		setZIndex(10000);
		
		current+= delta;
		
		if(current > delay) {
			current = 0;
			shootLaser(1700.0f, new Color(0,1,0,1));
		}

		
	}
	
	
	
}
