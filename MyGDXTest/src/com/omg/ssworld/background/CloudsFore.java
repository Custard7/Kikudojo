package com.omg.ssworld.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Background;

public class CloudsFore extends Background {

	
	public CloudsFore() {
		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/background/front_clouds.png")),0,0,1280,931));	
		setRegion(new TextureRegion(GameManager.getAssetsManager().get("data/background/front_clouds.png", Texture.class),0,0,1280,931));

		setCustomSpeed(0);		
		setScale(1.5f);
		setY(300);

	}
}
