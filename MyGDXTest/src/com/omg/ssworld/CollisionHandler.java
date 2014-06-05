package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.omg.drawing.JSActor;
import com.omg.screens.GameScreen;
import com.omg.ssplayer.Jumpable;
import com.omg.ssplayer.Kiku;
import com.omg.ssplayer.mechanics.BubblePackage;

public class CollisionHandler implements ContactListener {

	static String TAG = "COLLISION";
	
	WorldManager world;
	GameScreen gameScreen;
	
	
	
	public void setWorld(WorldManager world) {
		this.world = world;
	}
	
	public void setGameScreen(GameScreen screen){
		this.gameScreen = screen;
	}
	
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
	    //Gdx.app.log(TAG, "Begin Contact! :D");

	
		Body a=contact.getFixtureA().getBody();
	    Body b=contact.getFixtureB().getBody();
	    JSActor j = (JSActor)a.getUserData();
	    JSActor s = (JSActor)b.getUserData();
	    
	    
	    //Gdx.app.log(TAG, "Collision: J:" + j.getTags().get(0) + " S: " + s.getTags().get(0));

	    
	    

	    if(j.hasTag("Platform") && s.hasTag("Jumpable"))
	    {
	    	
    		((Jumpable)s).hitGround(true);
    		((Platform)j).playerHit((Jumpable)s);
    		
	    }
	    
	    
	    if(j.hasTag("Monster") && s.hasTag("Kiku"))
	    {
	    	
	    	//((Kiku)s).hitMonster((Monster)j);
	    	gameScreen.hitMonster((Monster)j);
	    	
	    }
	    
	    if(j.hasTag("Bubble") && s.hasTag("Kiku")) {
    		((BubblePackage)j).playerHit(((Kiku)s));

	    	
	    }
	    
	    if(j.hasTag("Laser") && s.hasTag("Kiku")) {
    		((Laser)j).playerHit(((Kiku)s));
    		gameScreen.isHittingLaser();
	    	
	    }
	    
	    if(j.hasTag("NanoKi") && s.hasTag("Kiku"))
	    {
	    	
    		((NanoKi)j).playerHit((Jumpable)s);
    		gameScreen.collectedNanoKi((NanoKi)j);
	    }
	    

	    
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
	   // Gdx.app.log(TAG, "End Contact! :(");

		Body a = null;
		Body b = null;
		
		if(contact.getFixtureA() == null)
			return;
		
		a=contact.getFixtureA().getBody();
		
		if(contact.getFixtureB() == null)
			return;
		
		b=contact.getFixtureB().getBody();
		
		
	    JSActor j = (JSActor)a.getUserData();
	    JSActor s = (JSActor)b.getUserData();
	    
	   // Gdx.app.log(TAG, "Collision Ended: J:" + j.getTags().get(0) + " S: " + s.getTags().get(0));


	    if(j.hasTag("Platform") && s.hasTag("Jumpable"))
	    {
	    	((Jumpable)s).inAir();
    		((Platform)j).playerStopHit((Jumpable)s);
	    	
	    }
	    
	    if(j.hasTag("NanoKi") && s.hasTag("Kiku"))
	    {
	    	
    		((NanoKi)j).playerStopHit((Jumpable)s);
	    }
	    
	    if(j.hasTag("Laser") && s.hasTag("Kiku")) {
    		((Laser)j).playerStopHit(((Kiku)s));

	    	
	    }
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
