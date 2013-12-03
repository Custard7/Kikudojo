package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CeilingPlatform extends Platform {

	
	
	public CeilingPlatform()
	{

		super(new TextureRegion(new Texture(Gdx.files.internal("data/NEW_TILE_SPLIT_PEA_SOUP.png")),0,0,c_width,c_height));

		
		
		addTag("Ceiling");
		
	}
	
}
