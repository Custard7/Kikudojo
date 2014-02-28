package com.omg.ssworld.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Background;

public class SkyBack extends Background {

	
	public SkyBack() {
		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/background/sky.png")),0,0,1280,931));
		setRegion(new TextureRegion(GameManager.getAssetsManager().get("data/background/sky.png", Texture.class),0,0,1280,931));

		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/ship_blue.png")),0,0,100,100));
		
		//setCustomSpeed( (int) (Math.random() * 6));
		setCustomSpeed(0);
		
		setScale(1.5f);
		//setY(-200);
	
	}

}
