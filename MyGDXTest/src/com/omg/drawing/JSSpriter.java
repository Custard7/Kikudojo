package com.omg.drawing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.omg.drawing.JSActor.ChildrenDrawDirection;
import com.omg.gdxlucid.Timer;
import com.omg.spriter.SpriterObject;
import com.omg.spriter.TextureProvider;
import com.omg.spriter.util.SpriterDrawer;
import com.omg.spriter.util.SpriterImporter;

public class JSSpriter extends Group {

	 SpriterObject spriterObject;

	public List<String> 	tags = new ArrayList<String>();

	
	Timer t;

	
	public JSSpriter(String filePath, TextureProvider texProvider) {
		super();
		
		t = new Timer();

        try {
			spriterObject = SpriterImporter.importFile(
			      Gdx.files.internal(filePath), texProvider);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        spriterObject.getAllTextures();
        
	}
	 
	 int frame = 0;
	
	@Override
	 public void draw (Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		   SpriterDrawer.draw(batch, spriterObject, "walk", frame,
	                this.getX(), this.getY(), true, true);
		
		if(t.getTime() > 1){
			frame++;
			t.reset();
		}
		
		if(frame > 200)
			frame = 0;
		   
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
