package com.omg.drawing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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

	/**
	 * A list of tags attached to this entity.
	 */
	public List<String> 	tags = new ArrayList<String>();
	
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
	 public void draw (Batch batch, float parentAlpha) {
		
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
	
	protected void draw_actor(Batch batch, float parentAlpha) {
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
	
	
	
	/**
	 * Adds a tag to this AREntity.
	 * @param tag to add.
	 */
	public void addTag(String tag) {
		
		tags.add(tag);
		
	}
	
	/**
	 * Returns whether this entity has a given tag.
	 * @param tag to check.
	 * @return True if it does have the tag, False if not.
	 */
	public boolean hasTag(String tag) {
		for (String t : tags) {
			if(tag.equals(t))
				return true;
		}
		return false;
	}
	
	/**
	 * Removes a given tag from this AREntity.
	 * @param tag to remove.
	 */
	public void removeTag(String tag) {
		Iterator<String> it = tags.iterator();
		
		while(it.hasNext()) {
			String t = it.next();
			if(t.equals(tag)) {
				it.remove();
				//TODO: FINISH REMOVE TAG FUNCTION
			}	
		}

	}
	
	/**
	 * Gets all tags associated with this AREntity.
	 * @return all tags associated with this AREntity.
	 */
	public List<String> getTags() {
		return tags;
	}
	
	
}
