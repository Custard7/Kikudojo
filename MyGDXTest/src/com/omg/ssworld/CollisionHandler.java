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
	    	
    		((Jumpable)s).hitGround();

	    	/*if(j.hasTag("Ceiling"))
	    	{
	    		((Jumpable)s).hitGround();
	    	}
	    	else {
	    		((Jumpable)s).hitGround();
	    	}*/
	    }
	    
	    
	    if(j.hasTag("Monster") && s.hasTag("Kiku"))
	    {
	    	
	    	//((Kiku)s).hitMonster((Monster)j);
	    	gameScreen.hitMonster((Monster)j);
	    	
	    }
	    

	    
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
	   // Gdx.app.log(TAG, "End Contact! :(");

		
		Body a=contact.getFixtureA().getBody();
	    Body b=contact.getFixtureB().getBody();
	    JSActor j = (JSActor)a.getUserData();
	    JSActor s = (JSActor)b.getUserData();
	    
	   // Gdx.app.log(TAG, "Collision Ended: J:" + j.getTags().get(0) + " S: " + s.getTags().get(0));


	    if(j.hasTag("Platform") && s.hasTag("Jumpable"))
	    {
	    	((Jumpable)s).inAir();
		    //Gdx.app.log(TAG, "Collision b/w Jumpable and Platform");
	    	
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
