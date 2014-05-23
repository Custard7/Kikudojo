
package com.omg.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.gdxlucid.Timer;

public class JSAnimation {

	
	//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/bum.png")),0,0,256/2,256));

	private String name;

	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	protected TextureRegion region;
	Texture texture;
	
	
	int startX = 0;
	int startY = 0;
	
	
	int X = 0;
	int Y = 0;
	
	int width = 100;
	int height = 100;
	
	
	int currentFrame = 0;
	int frameCount = 5;
	int speed = 50;
	
	
	Timer animationTimer;
	
	boolean repeat = true;
	boolean isFinished = false;
	
	public boolean isRepeat() {
		return repeat;
	}
	public void setRepeat(boolean value) {
		repeat = value;
	}
	
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean value) {
		this.isFinished = value;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
	public JSAnimation(String name, Texture t, int width, int height, int frames, int speed) {
		this.texture = t;
		
		region = new TextureRegion(t);
		
		this.frameCount = frames;
		this.width = width;
		this.height = height;
		
		this.speed = speed;
		
		this.name = name;
		
		region.setRegion(startX, startY, width, height);
		
		animationTimer = new Timer();
		animationTimer.start();
	}
	
	
	
	public TextureRegion update(float delta) {
		if(animationTimer.getTime() > speed) {
			
			
			X += width;
			currentFrame++;
			
			if(currentFrame >= frameCount)
			{
				if(isRepeat()) {
					reset();
				} else {
					setFinished(true);
					return null;
				}
				
				
			}
			
			region.setRegion(X, Y, width, height);
			
			animationTimer.reset();
			return region;

		}
		
		return null;	
	}
	
	
	
	public TextureRegion getRegion() {
		return region;
	}
	
	
	public void reset() {
		
		X = startX;
		currentFrame = 0;
		setFinished(false);
	}

	
	
	
	
	
	
	
}
