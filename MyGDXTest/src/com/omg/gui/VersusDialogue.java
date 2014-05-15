package com.omg.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.drawing.JSActor;
import com.omg.sswindler.GameManager;

public class VersusDialogue extends JSActor {

	
	JSActor leftComp;
	JSActor rightComp;
	
	
	public VersusDialogue() {
		//super(new TextureRegion(GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("VS Back"), Texture.class),0,0,1280,720));
		super(new TextureRegion(GameManager.getAssetsManager().getTexture("VS Back"),0,0,1280,720));

		this.setScale(1.0f);
		this.setPosition(-640, -360);
		

		
		//this.setPosition(-1100, -500);
  		//vsDialogue.setPosition(-700,-500);

		
		
	}
	
	
	
	
	
	
	
	
	
	
}
