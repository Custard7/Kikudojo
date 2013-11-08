package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.drawing.JSActor;

public class StarryBackground extends JSActor {

	
	public StarryBackground() {
		super(new TextureRegion(new Texture(Gdx.files.internal("data/front_stars.png")),0,0,1024,1024));
		
	}
	
}
