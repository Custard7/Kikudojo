package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.drawing.JSActor;

public class Background extends JSActor {

	private int customSpeed = 0;


	private int x;
	private int y;
	private int width;
	private int height;
	
	
	public Background() {
		super(new TextureRegion(new Texture(Gdx.files.internal("data/front_stars.png")),0,0,1024,1024));
		
		addTag("Background");
		
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(this.getX() < x - 1500) {
			this.remove();
			
		}
		
	}
	
	public void setWorldBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		
		
	}
	
	
	
	public int getCustomSpeed() {
		return customSpeed;
	}
	
	public void setCustomSpeed(int speed) {
		customSpeed = speed;
	}
}