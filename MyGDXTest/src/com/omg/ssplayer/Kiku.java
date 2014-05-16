package com.omg.ssplayer;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSAnimation;
import com.omg.ssplayer.mechanics.KiOrb;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Monster;
import com.testflightapp.lib.TestFlight;

public class Kiku extends Jumpable {

	JSAnimation runningAnimation;
	JSAnimation jumpingAnimation;
	
	JSAnimation currentAnimation;
	
	JSActor playerAura;
	
	public Color currentColor;
	
	public enum KikuAnim {
		
		running,
		jumping
		
	}
	
	public Kiku(){
		//runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Kiku"), Texture.class), 150, 208, 8, 50);
		//jumpingAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Kiku_Jump"), Texture.class), 200, 200, 10, 30);
		
		runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().getTexture("Kiku"), 150, 208, 8, 50);
		jumpingAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().getTexture("Kiku_Jump"), 200, 200, 10, 30);
		
		//this.setColor(getColor().r + 0.9f, getColor().g - 0.9f, getColor().b - 0.9f, getColor().a);

		
		//runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().get("data/player/actual_sprite_sheet.png", Texture.class), 150, 208, 8, 50);
		//jumpingAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().get("data/player/Jump_Sprite_Sheet.png", Texture.class), 200, 200, 10, 30);
	
		jumpingAnimation.setRepeat(false);
		
		currentAnimation = runningAnimation;
		
		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/bum.png")),0,0,256/2,256));

		setRegion(runningAnimation.getRegion());
		
		currentColor = Color.CYAN;
		
		playerAura = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("PlayerAura"),0,0,200,200));
		playerAura.setScale(2);
		playerAura.setVisible(false);
		playerAura.setPosition(-100, -50);
		playerAura.addAction(Actions.forever(Actions.sequence( Actions.alpha(0, 1), Actions.alpha(1.0f, .5f))));
		//playerAura.setColor(playerAura.getColor().r + currentColor.r, playerAura.getColor().g + currentColor.g, playerAura.getColor().b + currentColor.b, playerAura.getColor().a);
		playerAura.setColor(currentColor);
		this.addActor(playerAura);
		this.setChildDrawDirection(ChildrenDrawDirection.inFront);
		
		addTag("Kiku");
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		setZIndex(1000);

    	
    	if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched())
    	{
    		jump(2000.0f);
    		setAnimation(KikuAnim.jumping);
    	}
    	
    	TextureRegion region = currentAnimation.update(delta);
    	
    	if(region != null)
    		setRegion(region);
        
		
	}
	
	@Override
	public void hitGround() {
		super.hitGround();
		
		setAnimation(KikuAnim.running);
		
	}
	
	public void setAnimation(KikuAnim anim) {
		
		switch(anim) {
		
		case running:
			currentAnimation = runningAnimation;
			setScale(1.0f);
			break;
		case jumping:
			if(!currentAnimation.getName().equalsIgnoreCase(jumpingAnimation.getName()))
				jumpingAnimation.reset();

			currentAnimation = jumpingAnimation;
			setScale(1.2f);
			break;
		
		}
		
	}
	
	int kiTotal = 0;
	int auraTotal = 0;
	JSActor lastTarget;
	
	
	public void collectKi() {
		if(lastTarget == null)
			lastTarget = this;
		
		KiOrb orb = new KiOrb();
		orb.setKiNumber(kiTotal);
		orb.setX(this.getX()- 100 - 100 * kiTotal);
		orb.setTarget(lastTarget);
		this.getParent().addActor(orb);
		
		orb.setColor(currentColor);
		//orb.setColor(Color.MAGENTA);
		
		lastTarget = orb;
		
		kiTotal++;
		
		if(kiTotal > 5) {
			collectAura();
			removeAllKi(true);
		}
	}
	

	
	public void removeKi() {
		for(Actor a : this.getParent().getChildren()) {
			if(a instanceof KiOrb) {
				//Gdx.app.log("MONSTER", "Removing KI! " + kiTotal + " kiNumber:" + ((KiOrb)a).kiNumber);
				if(((KiOrb)a).kiNumber == kiTotal-1){
						lastTarget = ((KiOrb)a).getTarget();
						((KiOrb)a).remove();
						kiTotal--;
						return;
				}
			}
				
		}
			
	}
	
	public void removeAllKi(boolean good) {
		for(int x = kiTotal-1; x >= 0; x--) {
			removeKi();
		}
	}
	
	public void collectAura() {
		auraTotal++;
		updateAura();
	}
	
	public void updateAura() {
		if(auraTotal > 0) {
			playerAura.setVisible(true);
			
		} else {
			playerAura.setVisible(false);
		}
		
		if(auraTotal == 0) {
			currentColor = Color.CYAN;
		} else if(auraTotal == 1) {
			currentColor = Color.ORANGE;
		} else if (auraTotal == 2) {
			currentColor = Color.GREEN;
		} else {
			currentColor = Color.RED;
		}
		
		playerAura.setColor(currentColor);

	}
	
	public void removeAura() {
		auraTotal--;
		updateAura();
	}
	
	
	
	
}
