package com.omg.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class JSFont extends Group {

	
	 BitmapFont font;
	 String message = "";

	 
	 float r,g,b,a;
	 
	 
	 public JSFont(String message){
		 super();

		 init(message, "ktegaki");
	        
	    }
	 
	 public JSFont(String message, String fontPath) {
		 super();
		 init(message, fontPath);
	 }

	 
	 private void init(String message, String font_path) {
		//font = new BitmapFont(Gdx.files.internal("fonts/"+font_path+".fnt"), Gdx.files.internal("fonts/"+font_path+".png"), false);
	    //font = new BitmapFont();
	 	font = loadFont(font_path);
        
        r = 1.0f;
        g = 1.0f;
        b = 1.0f;
        a = 1.0f;
        
        font.setColor(r,g,b,a); 
        this.message = message;
	 }

	    @Override
	    public void draw(Batch batch, float parentAlpha) {
	    	super.draw(batch, parentAlpha);
	         //font.draw(batch, message, this.getX(), this.getY());
	    	font.draw(batch, message, this.getX(),this.getY());
	    }

	   
	   
	  public static BitmapFont loadFont(String font) 
	  {
	        return new BitmapFont(Gdx.files.internal("fonts/" + font+ ".fnt"), Gdx.files.internal("fonts/"+font +".png"), false);

	  }
	  
	  public void setText(String text) {
		  this.message = text;
	  }
	  
	  public void setScale(float scale) {
		  font.setScale(scale);
	  }
}
