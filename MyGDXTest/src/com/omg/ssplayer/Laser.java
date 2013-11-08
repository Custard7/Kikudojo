package com.omg.ssplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSEntity.JSVector2;

public class Laser extends JSActor {

	float speed;
	
	float lifeTime = 5;
	float life = 0;
	
	public Laser (float rotation, float speed) {
		super(new TextureRegion(new Texture(Gdx.files.internal("data/laser.png")),0,0,32,64));
	
		this.speed = speed;
		setRotation(rotation);
		
	}
	
	public Laser(float rotation, float speed, Color color) {
		super(new TextureRegion(new Texture(Gdx.files.internal("data/laser.png")),0,0,32,64));
		
		this.speed = speed;
		setRotation(rotation);
		this.setColor(color);
		
	}
	
	
	@Override
	public void act(float delta) {
		
		moveForward(speed * delta);
		life+= delta;
		Gdx.app.log("LASER", "Life : " + life);

		
		if(life > lifeTime) {
			this.remove();
			Gdx.app.log("LASER", "Killed");
		}
		
	}
	
	
	 
}
