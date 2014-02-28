package com.omg.screens;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class LAssetManager extends AssetManager {

	
	HashMap<String, String> assetAliases;

	
	
	public LAssetManager() {
		assetAliases = new HashMap<String, String>();

	}
	
	
	public void load(String fileName, Class<?> type, String alias) {
		assetAliases.put(alias, fileName);
		load(fileName, type);
		
	}
	
	
	public String getPath(String alias) {
		return assetAliases.get(alias);
	}
	

	

	
}
