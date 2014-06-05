package com.omg.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSFont;
import com.omg.filemanagement.QRSet.QROptions;
import com.omg.screens.GameScreen;
import com.omg.sfx.MusicManager.LucidMusic;
import com.omg.sswindler.GameManager;

public class HUD extends JSActor {

	
	JSActor spinner;
	
	JSActor circleLine;
	JSActor circleFill;
	
	JSActor scoreBox;
	JSFont nanoKiCounter;
	
	
	TextureRegion plus10Texture;
	
	int circleFillDimension = 500;
	
	int spinnerXPos = -1475;
	int spinnerYPos = -625;
	float spinnerScale = 2f; //1.59f
	
	float spinnerSpeed = 1f; //Degrees per second
	
	
	Button pauseButton;
	boolean _isPaused = false;
	Button pausedDialogue;
	
	GameScreen gameScreen;
	
	public void setGameScreen(GameScreen screen){
		this.gameScreen = screen;
	}

	
	public boolean isPaused() {
		return _isPaused;
	}
	public void setPaused(boolean p) {
		_isPaused = p;
		if(_isPaused) {
			pauseButton.setDisabled(true);
			pausedDialogue.setDisabled(false);
			pausedDialogue.setVisible(true);
			
			if(gameScreen != null)
				gameScreen.getMusicManager().pause();

		} else {
			pauseButton.setDisabled(false);
			pausedDialogue.setDisabled(true);
			pausedDialogue.setVisible(false);
			
			if(gameScreen != null)
				gameScreen.getMusicManager().resume();
			
		}
	}
	
	
	public float getSpinnerSpeed() {
		return spinnerSpeed;
	}
	
	/**
	 * Sets the spinner speed in degrees / second
	 */
	public void setSpinnerSpeed(float speed) {
		
		if(speed < 0)
			spinnerSpeed = 0;
		else {
			spinnerSpeed = speed;
		}

		setCircleFill(spinnerSpeed/5f);
		//Gdx.app.log("HUD", "Speed: " + spinnerSpeed);
	}
	
	
	
	
	public HUD() {
		super();
		
		scoreBox = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("HUD_ScoreBox"),0,0,500,500));
		scoreBox.setScale(spinnerScale);
		scoreBox.setPosition(spinnerXPos + 705 , spinnerYPos - 80);
		this.addActor(scoreBox);
		
		nanoKiCounter = new JSFont("0");
		nanoKiCounter.setPosition(-450 , -40);
		nanoKiCounter.setScale(2.3f);
		this.addActor(nanoKiCounter);
		
		spinner = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("HUD_Aura"),0,0,500,500));
		spinner.setScale(spinnerScale);
		spinner.setPosition(spinnerXPos + 265 , spinnerYPos + 265);
		spinner.setOrigin(250, 250);
		this.addActor(spinner);
		
		circleLine = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("HUD_Circle_Line"),0,0,500,500));
		circleLine.setScale(spinnerScale);
		circleLine.setPosition(spinnerXPos, spinnerYPos);
		this.addActor(circleLine);
		
		circleFill = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("HUD_Circle_Fill"),0,0,circleFillDimension,circleFillDimension));
		circleFill.setScale(spinnerScale);
		circleFill.setPosition(spinnerXPos, spinnerYPos);
		this.addActor(circleFill);
		
		
		plus10Texture = new TextureRegion(GameManager.getAssetsManager().getTexture("HUD_Plus10"),0,0,100,100);
		
		
		
		Skin skinA = new Skin();
		skinA.add("up", new Texture("data/ui/pause_button.png"));
		skinA.add("down", new Texture("data/ui/pause_button_down.png"));
		
		TextureRegion upRegionA = skinA.getRegion("up");
		TextureRegion downRegionA = skinA.getRegion("down");

		ButtonStyle styleA = new ButtonStyle();
		styleA.up = new TextureRegionDrawable(upRegionA);
		styleA.down = new TextureRegionDrawable(downRegionA);
	
		
		pauseButton = new Button(styleA);
		pauseButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Changed!");
				Gdx.app.log("HUD", "Pause Pressed");

				setPaused(true);

			}
		});
		
		pauseButton.setPosition(-1050, 1000);
		
		
		this.addActor(pauseButton);
		
		
		Skin skinB = new Skin();
		skinB.add("up", new Texture("data/ui/paused.png"));
		skinB.add("down", new Texture("data/ui/paused.png"));
		
		TextureRegion upRegionB = skinB.getRegion("up");
		TextureRegion downRegionB = skinB.getRegion("down");

		ButtonStyle styleB = new ButtonStyle();
		styleB.up = new TextureRegionDrawable(upRegionB);
		styleB.down = new TextureRegionDrawable(downRegionB);
	
		
		pausedDialogue = new Button(styleB);
		pausedDialogue.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Changed!");
				Gdx.app.log("HUD", "Unpaused");

				setPaused(false);

			}
		});
		
		pausedDialogue.setPosition(-200, 250);
		
		
		this.addActor(pausedDialogue);
		
		setPaused(false);
		
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		//spinner.addAction(Actions.forever(Actions.rotateBy(-360, 3)));
		//if(spinner.getActions().size == 0) {
			//spinner.addAction(Actions.rotateBy(-spinnerSpeed, 1));
		//}
		spinner.rotate(-spinnerSpeed);
		
	}
	
	
	public void plus10() {
		
		JSActor plus10Particle = new JSActor(plus10Texture);
		plus10Particle.setPosition(250, -400);
		plus10Particle.setScale(1.8f);
		plus10Particle.addAction(Actions.sequence(Actions.moveBy(0, 300, .5f, Interpolation.exp10Out), Actions.parallel(Actions.moveBy(0, -300, 2, Interpolation.exp10In), Actions.alpha(0, 3) ), Actions.removeActor()));
		this.addActor(plus10Particle);
		
	}
	
	public void setNanoKiCollected(int collected) {
		nanoKiCounter.setText("" + collected);
	}
	
	
	public void revSpinner(float amount) {
		setSpinnerSpeed(spinnerSpeed+amount);
	}
	
	public void slowSpinner(float amount) {
		setSpinnerSpeed(spinnerSpeed-amount);
	}
	
	public void applyFrictionToSpinner(float percent) {
		spinnerSpeed *= percent;
	}
	
	public void setCircleFill(float percent) {
		if(percent > 1)
			percent = 1;
		if(percent < 0)
			percent = 0;
		//circleFill.getRegion().setRegionHeight((int)((float)circleFillDimension * percent));
		//circleFill.getRegion().setRegion(0, 0, circleFillDimension, (circleFillDimension * percent));
	}
	
}

