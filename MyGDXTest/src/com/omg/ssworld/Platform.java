package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.drawing.JSActor;

public class Platform extends JSActor {

	private int x;
	private int y;
	private int width;
	private int height;
	
	public Platform()
	{
		super(new TextureRegion(new Texture(Gdx.files.internal("data/mega_laser.png")),0,350,1000,256));

		
		//setOriginX(130);
		//setOriginY(55);
		
		setScale(0.40f);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(this.getX() < x)
			this.remove();
		
		
	}
	
	public void setWorldBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		
		
	}
	
}
