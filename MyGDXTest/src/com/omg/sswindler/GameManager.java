package com.omg.sswindler;

import com.badlogic.gdx.Game;
import com.omg.screens.GameScreen;
import com.omg.screens.MainMenuScreen;

public class GameManager extends Game {

	
	MainMenuScreen menuScreen;
	GameScreen 	   gameScreen;
	
	
	
	
	
	@Override
	public void create() {
		
		menuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
		
	}

}
