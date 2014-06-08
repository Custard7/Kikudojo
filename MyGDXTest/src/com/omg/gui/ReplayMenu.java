package com.omg.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSFont;
import com.omg.screens.GameScreen;
import com.omg.screens.MainMenuScreen;
import com.omg.sswindler.GameManager;

public class ReplayMenu extends JSActor {

	GameManager gameManager;
	
	public void setGameManager(GameManager gManager) {
		this.gameManager = gManager;
	}
	
	JSActor billBoard;
	Button replayButton;
	Button menuButton;
	
	JSFont highscoreText;
	
	int droppedY = 0;
	int droppedX = 0;
	
	int upY = 1100;
	int upX = 0;
	
	int timeToDrop = 2;
	
	public enum MenuState {
		up,
		goingUp,
		down,
		goingDown
	}
	
	MenuState menuState = MenuState.up;
	
	boolean goToMenu = false;
	boolean replay = false;
	
	public void setState(MenuState state) {
		
		switch(state) {
		
		case up:
			
			this.setPosition(upX, upY);
			
			if(replay) {
				gameManager.loadScreen(new GameScreen(gameManager));
			} else if (goToMenu) {
				gameManager.setScreen(new MainMenuScreen(gameManager, true));
			}
			
			break;
		case goingDown:
			
			addAction(Actions.sequence(
					Actions.moveTo(droppedX, droppedY, timeToDrop, Interpolation.bounceOut),
					Actions.run(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							setState(MenuState.down);
						}
						
					})
					));
			
			
			break;
		case down:
			
			this.setPosition(droppedX, droppedY);
			
			break;
		case goingUp:
			
			addAction(Actions.sequence(
					Actions.moveTo(upX, upY, timeToDrop, Interpolation.bounceIn),
					Actions.run(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							setState(MenuState.up);
						}
						
					})
					));

			
			break;
		
		}
		
		this.menuState = state;
	}
	public MenuState getState() {
		return menuState;
	}
	
	public ReplayMenu() {
		
		billBoard = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("Replay_Billboard"),0,0,250,250));
		billBoard.setScale(4.5f);
		billBoard.setPosition(-400 , 200);
		this.addActor(billBoard);
		
		//Replay Button
		Skin skinA = new Skin();
		skinA.add("up", new Texture("data/ui/replay_menu/try_again_button.png"));
		skinA.add("down", new Texture("data/ui/replay_menu/try_again_button_down.png"));
		
		TextureRegion upRegionA = skinA.getRegion("up");
		TextureRegion downRegionA = skinA.getRegion("down");

		ButtonStyle styleA = new ButtonStyle();
		styleA.up = new TextureRegionDrawable(upRegionA);
		styleA.down = new TextureRegionDrawable(downRegionA);
		
		replayButton = new Button(styleA);
		replayButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Changed!");
				Gdx.app.log("Replay Menu", "Replay Pressed");
		 	    //gameManager.loadScreen(new GameScreen(gameManager));
		 	    replay = true;
		 	    goToMenu = false;
		 	    pullUp();

			}
		});
		
		replayButton.setPosition(-340, 400);
		replayButton.setSize(500, 172);
		this.addActor(replayButton);
		
		replayButton.setDisabled(false);

		
		//Menu Button
		Skin skinB = new Skin();
		skinB.add("up", new Texture("data/ui/replay_menu/menu_button.png"));
		skinB.add("down", new Texture("data/ui/replay_menu/menu_button_down.png"));
		
		TextureRegion upRegionB = skinB.getRegion("up");
		TextureRegion downRegionB = skinB.getRegion("down");

		ButtonStyle styleB = new ButtonStyle();
		styleB.up = new TextureRegionDrawable(upRegionB);
		styleB.down = new TextureRegionDrawable(downRegionB);
		
		menuButton = new Button(styleB);
		menuButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Changed!");
				Gdx.app.log("Replay Menu", "Menu Pressed");
				//gameManager.setScreen(new MainMenuScreen(gameManager));
				goToMenu = true;
				replay = false;
				pullUp();
			}
		});
		
		menuButton.setPosition(160, 400);
		menuButton.setSize(500,172);
		this.addActor(menuButton);
		
		
		//High Score
		highscoreText = new JSFont("0");
		highscoreText.setPosition(-200, 1000);
		highscoreText.setScale(2.3f);
		this.addActor(highscoreText);
		
		
		menuButton.setDisabled(false);
		
	}
	
	public void setScore(int score) {
		
		highscoreText.setText(""+ score);
		
	}
	
	public void dropDown() {
		if(menuState == MenuState.up) {
			setState(MenuState.goingDown);
		}
	}
	
	public void pullUp() {
		if(menuState == MenuState.down) {
			setState(MenuState.goingUp);
		}
		
	}
	
	
	
	
	
}
