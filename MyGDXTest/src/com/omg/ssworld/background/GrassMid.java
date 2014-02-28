package com.omg.ssworld.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Background;

public class GrassMid extends Background {

	
	public GrassMid() {
		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/background/mid_f.png")),0,0,1280,931));	
		setRegion(new TextureRegion(GameManager.getAssetsManager().get("data/background/mid_f.png", Texture.class),0,0,1280,931));

		setCustomSpeed(1);		
		setScale(1.5f);
		setY(-250);

	}
}
