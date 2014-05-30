package com.omg.ssplayer.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.omg.drawing.JSActor;
import com.omg.ssplayer.Kiku;
import com.omg.sswindler.GameManager;

public class KiOrb extends JSActor {

	protected static int c_width = 75;
	protected static int c_height = 75;
	
	double time = 0.0;
	float acceleration = 3.0f;
	float velocity = 0;
	float friction = .95f;
	
	float maxV = 15;

	JSActor aura;
	
	JSActor target;
	
	public int kiNumber = 0;
	
	public KiOrb() {
		super(new TextureRegion(GameManager.getAssetsManager().getTexture("Orb_B"),0,0,c_width,c_height));

		//this.setColor(getColor().r - 0.5f, getColor().g + 0.1f, getColor().b + 0.5f, getColor().a);
		
		aura = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("Aura_B"),0,0,c_width*2,c_height*2));
		aura.setX(-c_width/2);
		aura.setY(-c_height/2);
		
		aura.addAction(Actions.forever(Actions.sequence( Actions.alpha(0, 1), Actions.alpha(1.0f, .5f))));
		//aura.setColor(getColor().r, getColor().g, getColor().b, getColor().a);

		this.addActor(aura);
		addTag("KiOrb");
		addTag("STATIC");
	}
	
	@Override
	public void setColor(Color color) {
		super.setColor(color);
		aura.setColor(color);
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		setZIndex(1001);

		
		if(target != null) {
			
			//this.setY(target.getY());
			
			if(target.getY() - maxV *2 > this.getY()) {
				velocity+=acceleration;
			}
			if(target.getY() + maxV *2 < this.getY()) {
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

		
		
		
    	//time+=.04;
    	this.setY(this.getY() + (float)Math.sin(time)*1);
	}
	
	public void setTarget(JSActor actor) {
		this.target = actor;	
	}

	
	public void setKiNumber(int num) {
		kiNumber = num;
	}
	
	
	public JSActor getTarget() {
		return target;
	}
	

	
	
}
