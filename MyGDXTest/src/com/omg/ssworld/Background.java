package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.drawing.JSActor;
import com.omg.sswindler.GameManager;

public class Background extends JSActor {

	private int customSpeed = 0;


	private int x;
	private int y;
	private int width;
	private int height;
	
	
	public boolean isActive;
	
	
	

	
	
	
	public Background() {
		super(new TextureRegion(GameManager.getAssetsManager().getTexture("Sky"),0,0,1,1));
		//super(new TextureRegion(GameManager.getAssetsManager().get("data/front_stars.png", Texture.class),0,0,1024,1024));
		//super(new TextureRegion(GameManager.getAssetsManager().getTexture("Front Stars"),0,0,1024,1024));
		
		addTag("Background");
		
		isActive = true;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(this.getX() < x - 4000) {
			//this.remove();
			this.isActive = false;
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
