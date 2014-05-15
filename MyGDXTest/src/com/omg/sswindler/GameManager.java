package com.omg.sswindler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.graphics.Texture;
import com.omg.screens.GameScreen;
import com.omg.screens.LAssetManager;
import com.omg.screens.Loadable;
import com.omg.screens.LoadingScreen;
import com.omg.screens.MainMenuScreen;

public class GameManager extends Game {

	
	MainMenuScreen menuScreen;
	GameScreen 	   gameScreen;
	
	public static final int VIRTUAL_WIDTH = 1280;
	public static final int VIRTUAL_HEIGHT = 720;
	public static final float ASPECT_RATIO =
	        (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
	
	
	
	@Override
	public void create() {
		
		menuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		
		setScreen(menuScreen);
		
	}
	
	
	public void gotoGameScreen(){
		setScreen(gameScreen);
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}
	
	public void loadScreen(Loadable loadable) {
		setScreen(new LoadingScreen(this, loadable));
	}
	
	private static LAssetManager assetManager;
	
	public static LAssetManager getAssetsManager() {
		
		if(assetManager == null) {
			assetManager = new LAssetManager();
			Texture.setAssetManager(assetManager);

		}
		return assetManager;
		
	}

}
