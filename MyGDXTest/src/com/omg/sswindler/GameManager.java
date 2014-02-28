package com.omg.sswindler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.omg.screens.GameScreen;
import com.omg.screens.LAssetManager;
import com.omg.screens.Loadable;
import com.omg.screens.LoadingScreen;
import com.omg.screens.MainMenuScreen;

public class GameManager extends Game {

	
	MainMenuScreen menuScreen;
	GameScreen 	   gameScreen;
	
	
	
	
	
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
		
		if(assetManager == null)
			assetManager = new LAssetManager();
		return assetManager;
		
	}

}
