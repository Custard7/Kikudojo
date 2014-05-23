package com.omg.drawing;

import java.util.HashMap;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class JSAnimatedActor extends JSActor {

	
	JSAnimation currentAnimation;
	
	HashMap<String, JSAnimation> animations;
	
	
	public JSAnimatedActor(JSAnimation animation) {
		super();
		

		
		animations = new HashMap<String, JSAnimation>();
		addAnimation(animation);
		setAnimation(animation);
		
		setRegion(currentAnimation.getRegion());

		
		addTag("Animated");

	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
    	
    	TextureRegion region = currentAnimation.update(delta);
    	
    	if(region != null)
    		setRegion(region);
        
		
	}
	
	
	public void setAnimation(JSAnimation anim) {
		currentAnimation = anim;
	}
	
	public JSAnimation getAnimation() {
		return currentAnimation;
	}
	
	public void addAnimation(JSAnimation animation) {
		animations.put(animation.getName(), animation);
	}
	
	public void setAnimation(String name) {
		setAnimation(animations.get(name));
	}
	
	
}
