package com.omg.ssplayer.mechanics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSAnimation;
import com.omg.ssplayer.mechanics.BubblePackage.PackageType;
import com.omg.sswindler.GameManager;

public class Animal extends JSActor {

	protected static int c_width = 100;
	protected static int c_height = 100;
	
	double time = 0.0;
	float acceleration = 3.0f;
	float velocity = 0;
	float friction = .95f;
	
	float maxV = 15;

	float yOffset = 200;
	
	JSAnimation currentAnimation;

	
	JSActor target;
	
	public enum AnimalType {
		nothing(new JSAnimation("Nothing", GameManager.getAssetsManager().getTexture("Animal_Frog"), 0, 0, 1, 1000)),
		frog(new JSAnimation("Froggy", GameManager.getAssetsManager().getTexture("Animal_Frog"), 50, 50, 4, 150)),
		kiDude(new JSAnimation("KiDude", GameManager.getAssetsManager().getTexture("Animal_KiDude"), 50, 50, 2, 400)),
		bird(new JSAnimation("Birdy", GameManager.getAssetsManager().getTexture("Animal_Bird"), 50, 50, 4, 50));

		
		private final JSAnimation anim;
		AnimalType(JSAnimation anim) { this.anim = anim; }
	    public JSAnimation getAnimation() { return anim; }
		
		
	}
	
	
	
	public Animal() {
		super(new TextureRegion(GameManager.getAssetsManager().getTexture("Animal_Frog"),0,0,c_width,c_height));

		setType(AnimalType.frog);
	
		setScale(2);
		
		addTag("Animal");
		addTag("STATIC");
	}
	
	
	AnimalType animalType = AnimalType.frog;

	public void setType(AnimalType p) {
		this.animalType = p;
		//this.setRegion(p.getTextureRegion());
		currentAnimation = p.getAnimation();
		setRegion(currentAnimation.getRegion());

		
		switch(getType()) {
			case frog:
				break;
			default:
				break;
		
		
		}
		
	}
	
	
	public AnimalType getType() {
		return animalType;
	}
	

	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		setZIndex(1001);

		
		if(target != null) {
			
			//this.setY(target.getY());
			
			if(target.getY() + yOffset - maxV *2 > this.getY()) {
				velocity+=acceleration;
			}
			if(target.getY() + yOffset + maxV *2 < this.getY()) {
				velocity-=acceleration;
			}
			
			if(Math.abs(velocity) > maxV) {
				if(velocity < 0)
					velocity = -maxV;
				else
					velocity = maxV;
			}
			velocity *= friction;
			
			this.setY(this.getY() + velocity);
			//Gdx.app.log("MONSTER","Ki Y Pos: " + this.getY());
		}


    	TextureRegion region = currentAnimation.update(delta);
    	
    	if(region != null)
    		setRegion(region);
		
		
    	this.setY(this.getY() + (float)Math.sin(time)*1);
	}
	
	public void setTarget(JSActor actor) {
		this.target = actor;	
	}

	
	
	
	public JSActor getTarget() {
		return target;
	}
	

	
	
}
