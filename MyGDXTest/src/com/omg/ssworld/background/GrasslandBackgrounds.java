package com.omg.ssworld.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Background;

public class GrasslandBackgrounds extends Background {

	public GrasslandBackgrounds(BProperties properties) {
		//setRegion(new TextureRegion(GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath(properties.getFileName()), Texture.class),0,0,properties.getWidth(),properties.getHeight()));
		setRegion(new TextureRegion(GameManager.getAssetsManager().getTexture(properties.getFileName()),0,0,properties.getWidth(),properties.getHeight()));

		setCustomSpeed(properties.getCustomSpeed());
		
		setScale(properties.getScale());
		setY(properties.getYOffset());
	
	}
	
}
