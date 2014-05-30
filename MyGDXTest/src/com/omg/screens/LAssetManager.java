package com.omg.screens;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.sswindler.GameManager;

public class LAssetManager extends AssetManager {

	
	HashMap<String, String> assetAliases;

	public boolean useAssetManager = false;
	
	public LAssetManager() {
		super();
		assetAliases = new HashMap<String, String>();
	}
	
	
	public void load(String fileName, Class<?> type, String alias) {
		assetAliases.put(alias, fileName);
		super.load(fileName, type);
		Gdx.app.log("AssetManager", "Loading -" + alias + "- at path: " + fileName);

	}
	
	int x = 0;
	
	
	public void loadTexture(String fileName, String alias) {
		//Texture t = new Texture(Gdx.files.internal(fileName));
		//assets.put(alias, fileName);
		load(fileName, Texture.class, alias);
		//load("data/effects/blue_aura_small.png", Texture.class, alias);

		

		
	}
	
	public Texture getTexture(String alias) {
		//Gdx.app.log("AssetManager", "GETTING ASSET: " + alias + " RETURNING: " + assets.get(alias) + "- Loaded : " + super.getLoadedAssets());
		//return new Texture(Gdx.files.internal(assetAliases.get(alias)));
		

		
		if(!useAssetManager){
			
			return new Texture(Gdx.files.internal(getPath(alias)));
			//return assetTextures.get(alias);
		}
		
		return super.get(getPath(alias), Texture.class);
	}
	
	

	
	
	public String getPath(String alias) {
		Gdx.app.log("AssetManager", "GETTING ASSET: " + alias + " RETURNING: " + assetAliases.get(alias));
		return assetAliases.get(alias);
	}
	
	
	



	
}
