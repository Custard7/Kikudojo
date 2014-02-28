package com.omg.ssworld.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Background;

public class CloudsBack extends Background {

	
	public CloudsBack() {
		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/background/back_clouds.png")),0,0,1280,931));
		//super(new TextureRegion(GameManager.getAssetsManager().get("data/big_pixel_coin.png", Texture.class),0,0,c_width,c_height));
		setRegion(new TextureRegion(GameManager.getAssetsManager().get("data/background/back_clouds.png", Texture.class),0,0,1280,931));

		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/ship_blue.png")),0,0,100,100));
		
		//setCustomSpeed( (int) (Math.random() * 6));
		setCustomSpeed(-15);
		
		setScale(1.5f);
		setY(-100);
	
	}

}
