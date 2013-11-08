package com.omg.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class JSActor extends Group {


	public enum ChildrenDrawDirection {
		inFront,
		inBack
	}
	
	ChildrenDrawDirection cDirection = ChildrenDrawDirection.inFront;
	

	
	
	TextureRegion region;

	
	
	public JSActor() {
		super();

		/*Texture t = new Texture(Gdx.files.internal("data/libgdx.png"));
		TextureRegion r = new TextureRegion(t,0,0,t.getWidth(),t.getHeight());
		
		setRegion(r);*/
	}
	
	
	public TextureRegion getRegion() {
		return region;
	}



	public void setRegion(TextureRegion region) {
		this.region = region;
		setWidth(region.getRegionWidth());
		setHeight(region.getRegionHeight());
	}



	public JSActor(TextureRegion region) {
		setRegion(region);
	}
	

	
	
	@Override
	 public void draw (SpriteBatch batch, float parentAlpha) {
		
		if(cDirection == ChildrenDrawDirection.inFront) {
			super.draw(batch, parentAlpha);
			draw_actor(batch, parentAlpha);
		}
		else
		{
			draw_actor(batch, parentAlpha);
			super.draw(batch, parentAlpha);
		}
         
		//Gdx.app.log("JSActor", "ZIndex: " + getZIndex());
	}
	
	protected void draw_actor(SpriteBatch batch, float parentAlpha) {
		if(getRegion() != null) {
	         Color color = getColor();
	         batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
	         batch.draw(getRegion(), getX(), getY(), getOriginX(), getOriginY(),
	                 getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		 }
	}
	
	
	public void moveForward(float amount) {
	    translate((float)(-amount * Math.sin(Math.toRadians(getRotation()))), (float)(amount * Math.cos(Math.toRadians(getRotation()))));
	}
	
	
	public void setChildDrawDirection(ChildrenDrawDirection c) {
		this.cDirection = c;
	}
	
	public ChildrenDrawDirection getChildDrawDirection() {
		return cDirection;
	}
}
