package com.omg.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSAnimatedActor;
import com.omg.drawing.JSAnimation;
import com.omg.sfx.SoundManager;
import com.omg.sswindler.GameManager;

public class VersusDialogue extends JSActor {

	JSActor background;
	
	JSAnimatedActor leftComp;
	JSAnimatedActor rightComp;
	
	JSActor divideLine;
	JSActor wind;
	JSActor vsText;
	
	JSActor white;
	
	JSAnimatedActor explosion;
	
	int timeTillExplode = 225;
	
	
	
	public enum VersusDialogueState {
		
		OpenTransitionIn,
		OpenVersusShow,
		OpenTransitionOut,
		Done
		
		
	}
	
	VersusDialogueState currentState = VersusDialogueState.OpenTransitionIn;
	
	
	public VersusDialogue() {
		//super(new TextureRegion(GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("VS Back"), Texture.class),0,0,1280,720));
		//super(new TextureRegion(GameManager.getAssetsManager().getTexture("VS Back"),0,0,1280,720));
		super();
		
		this.setScale(1.0f);
		this.setPosition(-640, -360);
		

		this.setChildDrawDirection(ChildrenDrawDirection.inFront);
		
		background = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("VS Back"),0,0,1280,720));
		white = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("White"),0,0,16,9));
		
	   	wind = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("VS Swoosh"),0,0,1280,720));
		//wind.addAction(Actions.forever(Actions.sequence( Actions.alpha(0, .05f), Actions.alpha(1.0f, .06f))));
	   	wind.setScale(1.25f);
		wind.addAction(Actions.forever(
				Actions.parallel(
				Actions.sequence( Actions.moveBy(-100, 0, 0.1f), Actions.moveBy(100.0f, 0.0f, 0.00f)),
				Actions.sequence( Actions.alpha(0.0f, .05f), Actions.alpha(1.0f, .06f))   
				)));
	   	
	   	divideLine = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("VS Back Border"),0,0,1280,720));
	   	vsText = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("VS VS"),0,0,300,300));
	   	vsText.setPosition(475, 200);
	   	
	   	JSAnimation kikuAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().getTexture("Kiku_Jump"), 200, 200, 10, 200);
	   	kikuAnimation.setRepeat(false);
	   	//kikuAnimation.setSpeed(2);
	   	leftComp = new JSAnimatedActor(kikuAnimation);
	   	leftComp.setPosition(-35,50);
	   	leftComp.setScale(3.5f);
	   	
	   	rightComp = new JSAnimatedActor(new JSAnimation("Idle", GameManager.getAssetsManager().getTexture("Enemy"), 200/2, 160/2, 10, 200));
	   	rightComp.setPosition(775, 60);
	   	rightComp.setScale(7f); //3.5f
	   	
	   	JSAnimation explode = new JSAnimation("Explode", GameManager.getAssetsManager().getTexture("Explosion"), 100, 100, 4, 100);
	   	explode.setRepeat(false);
	   	explosion = new JSAnimatedActor(explode);
	   	explosion.setPosition(450,200);
	   	explosion.setScale(5);
	   	explosion.setVisible(false);
	   	
		
	   	addActor(background);
	   	
	   	addActor(wind);

	   	
	   	addActor(leftComp);
	   	addActor(rightComp);
	   	
	   	addActor(divideLine);
	   	addActor(vsText);
	   	addActor(explosion);
	   	
	   	addActor(white);
	   	
	   	white.setScale(80);
	   	white.addAction(Actions.sequence(Actions.alpha(0, 1), Actions.run(new Runnable() {
			@Override
			public void run() {

				setState(VersusDialogueState.OpenVersusShow);
			
			}
	   	})));

		
		//this.setPosition(-1100, -500);
  		//vsDialogue.setPosition(-700,-500);

		
		
	}
	
	int time = 0;
	
	boolean explosionPlayed = false;
	
	public void updateFromVersus(SoundManager soundManager) {
		
		switch(currentState) {
		
		case OpenTransitionIn:
			//White fade in

			
			break;
		case OpenVersusShow:
			
			time++;
			
			if(time > timeTillExplode) {
				explosion.getAnimation().reset();
				explosion.setVisible(true);
				vsText.remove();
				if(!explosionPlayed) {
					soundManager.play("Explosion");
					explosionPlayed = true;
				}
			}
			
			if(time > timeTillExplode + 15) {
				setState(VersusDialogueState.OpenTransitionOut);
			}
			
			
			break;
		case OpenTransitionOut:
			
			break;
		case Done:
			break;
		default:
			break;
		
		
		}
		
	}
	
	public void setState(VersusDialogueState state) {
		
		switch(state) {
		
		case OpenTransitionOut:
			
			if(currentState != state) {
				white.setColor(white.getColor().r, white.getColor().g, white.getColor().b, 1.0f);

				white.addAction(Actions.sequence(Actions.alpha(.7f, .5f), Actions.run(new Runnable() {
					@Override
					public void run() {

						setState(VersusDialogueState.Done);
					
					}
			   	})));
				
				
			}
			
			break;
		case Done:
			break;
		case OpenTransitionIn:
			break;
		case OpenVersusShow:
			break;
		default:
			break;

			
		
		}
		currentState = state;

	}
	

	public VersusDialogueState getState() {
		return currentState;
	}
	public boolean isDone() {
		return (currentState == VersusDialogueState.Done);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
