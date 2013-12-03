package com.omg.ssworld;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.drawing.JSActor;

public class StarryBackground extends Background {

	
	public StarryBackground() {
		setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/front_stars.png")),0,0,1024,1024));
		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/ship_blue.png")),0,0,100,100));
		
		setCustomSpeed( (int) (Math.random() * 6));
		//setCustomSpeed(3);
		
		setScale(2);
	
	}
	
	
	
	
	
}
