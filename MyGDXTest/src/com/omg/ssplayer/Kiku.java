package com.omg.ssplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.omg.drawing.JSAnimation;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Monster;
import com.testflightapp.lib.TestFlight;

public class Kiku extends Jumpable {

	JSAnimation runningAnimation;
	
	public Kiku(){
		runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Kiku"), Texture.class), 150, 208, 8, 50);

		
		
		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/bum.png")),0,0,256/2,256));

		setRegion(runningAnimation.getRegion());
		
		
		addTag("Kiku");
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		setZIndex(1000);

    	
    	if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched())
    	{
    		jump(2000.0f);
    	}
    	
    	TextureRegion region = runningAnimation.update(delta);
    	
    	if(region != null)
    		setRegion(region);
        
		
	}
	
	
	
	
	
	
	
}
