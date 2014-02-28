package com.omg.ssworld.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Background;

public class MountainBack extends Background {

	
	public MountainBack() {
		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/background/back_f.png")),0,0,1280,931));
		setRegion(new TextureRegion(GameManager.getAssetsManager().get("data/background/back_f.png", Texture.class),0,0,1280,931));
		
		//setCustomSpeed( (int) (Math.random() * 6));
		setCustomSpeed(-10);
		
		setScale(1.5f);
		setY(-200);
	
	}
	
}
