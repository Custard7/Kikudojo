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
	JSAnimation jumpingAnimation;
	
	JSAnimation currentAnimation;
	
	public enum KikuAnim {
		
		running,
		jumping
		
	}
	
	public Kiku(){
		//runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Kiku"), Texture.class), 150, 208, 8, 50);
		//jumpingAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Kiku_Jump"), Texture.class), 200, 200, 10, 30);
		
		runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().getTexture("Kiku"), 150, 208, 8, 50);
		jumpingAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().getTexture("Kiku_Jump"), 200, 200, 10, 30);
		
		
		//runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().get("data/player/actual_sprite_sheet.png", Texture.class), 150, 208, 8, 50);
		//jumpingAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().get("data/player/Jump_Sprite_Sheet.png", Texture.class), 200, 200, 10, 30);
	
		jumpingAnimation.setRepeat(false);
		
		currentAnimation = runningAnimation;
		
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
    		setAnimation(KikuAnim.jumping);
    	}
    	
    	TextureRegion region = currentAnimation.update(delta);
    	
    	if(region != null)
    		setRegion(region);
        
		
	}
	
	@Override
	public void hitGround() {
		super.hitGround();
		
		setAnimation(KikuAnim.running);
		
	}
	
	public void setAnimation(KikuAnim anim) {
		
		switch(anim) {
		
		case running:
			currentAnimation = runningAnimation;
			setScale(1.0f);
			break;
		case jumping:
			if(!currentAnimation.getName().equalsIgnoreCase(jumpingAnimation.getName()))
				jumpingAnimation.reset();

			currentAnimation = jumpingAnimation;
			setScale(1.2f);
			break;
		
		}
		
	}
	
	
	
	
	
	
	
}
