package com.omg.ssworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSEntity;
import com.omg.gdxlucid.Timer;
import com.omg.ssworld.background.BackgroundSpawn;
import com.testflightapp.lib.TestFlight;

public class WorldManager extends JSActor {

	
	private int x;
	private int y;
	private int width;
	private int height;
	
	
	public int getWorldX()
	{ return x; }
	public int getWorldY()
	{ return y; }
	public int getWorldWidth()
	{ return width; }
	public int getWorldHeight()
	{ return height; }
	
	public float speed = 25;
	public float defaultSpeed = 20;
	public float decelerationSpeed = 1.25f;
	
	Timer worldTimer;
	Timer backTimer;
	
	Timer totalPlayTime;
	
	World physics_world;
	
	public enum WorldState {
		moving,
		frozen
	}
	
	WorldState worldState = WorldState.moving;
	
	public WorldState getState() {
		return worldState;
	}
	
	public void setWorldState(WorldState state) {
		this.worldState = state;
	}
	
	public WorldManager(int x, int y, int width, int height) {
		 setDimensions(x,y,width,height);
		
		 worldTimer = new Timer();
		 worldTimer.start();
		 
		 backTimer = new Timer();
		 backTimer.start();
		 
		 totalPlayTime = new Timer();
		 totalPlayTime.start();
		 
		/* addBackgroundSpawn(new BackgroundSpawn(this, "com.omg.ssworld.background.SkyBack"));

		 */
		 
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Sky", 0, 0))); 				//Sky Back
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Back Clouds", -15, -100))); 	//Clouds Back
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Back F", -10, -200))); 		//Mountain Back
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Front Clouds", 0, 300))); 		//Clouds Foreground
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Mid F", 1, -250))); 			//Grass Middle
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Front F", 4, -400))); 			//Grass Front

		 
	}
	
	
	public void setDimensions(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void addPhysics(World physics_world) {
		this.physics_world = physics_world;
		 addPlatformSpawn(new PlatformSpawn(this, physics_world, 5));
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		
		for(Actor a : this.getChildren()) {
			
			if(!((JSActor)a).hasTag("STATIC")) {
			
				int customSpeed = 0;
				
				if(((JSActor)a).hasTag("Background"))
					customSpeed = ((Background)a).getCustomSpeed();
				else if(getState() == WorldState.frozen)
					break;
				a.translate(-(speed + customSpeed), 0);
			}
		}
    	
		if(getState() != WorldState.frozen)
			updatePlatformCreation();
		else {
			
			if(speed > 0)
				speed -= decelerationSpeed;
			if(speed < 0)
				speed = 0;
			
		}
		
		
		//updateBackgroundCreation();
		
        
        
        
		
		if(totalPlayTime.getTime() > 60000)
		{
			if(Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS)
				TestFlight.passCheckpoint("Played for a minute!");
    		totalPlayTime.reset();
		}
		
		
		
	}
	
	
	public void updatePlatformCreation() {
		/*if(worldTimer.getTime()  > 15000 * Gdx.graphics.getDeltaTime()){
			Platform p = new Platform();
			p.addPhysics(physics_world);
			addPlatform(p);
			worldTimer.reset();
		}*/
		
		if(worldTimer.getTime()  > 120000 * Gdx.graphics.getDeltaTime()){
			//PlatformSpawn p = new PlatformSpawn(this, physics_world,(int) ((Math.random() * 8) + 4));
			//addPlatformSpawn(p);
			
			for(Actor a : this.getChildren()) {
				
				if(((JSActor)a).hasTag("P_spawn")) {
			
					if(!((PlatformSpawn)a).isOn()) {
						((PlatformSpawn)a).turnOn((int) ((Math.random() * 8) + 4));
						worldTimer.reset();

						return;

					}
				}
			}
			
			worldTimer.reset();
		}
		
	}
	

	/*
	public void updateBackgroundCreation() {
		//if(backTimer.getTime()  > (70000 / (speed/10.0f))  * Gdx.graphics.getDeltaTime()){
		if(backTimer.getTime()  > (190000 / (speed/10.0f))  * Gdx.graphics.getDeltaTime()){

			boolean canReuse = false;
			Background b = null;
			
			for(Actor a : this.getChildren()) {
				
				if(((JSActor)a).hasTag("Background")) {
			
					if(!((Background)a).isActive) {
						((Background)a).isActive = true;
						b = ((Background)a);
						canReuse = true;
					}
				}
			}
			
			if(!canReuse)
				b = new MountainBack();
			addBackground(b, canReuse);
			backTimer.reset();
		}
	}*/
	
	private float last_y = 0;
	private Platform lastPlatform;
	
	public void addPlatform(Platform p) {
		
		
		int ranNum = (int)(Math.random() * 100);
				
		p.setWorldBounds(x, y, width, height);
		
		if(lastPlatform == null)
			p.setX(x + width);
		else
			p.setX(lastPlatform.getX() + lastPlatform.getTextureWidth() - 100);
		
		float ran_y = (float)(Math.random() * (height/2));
		
		if(ranNum > 60) {
			p.setY(ran_y);
			last_y = ran_y;
		}
		else if (ranNum < 70){
			p.setY(last_y);
		}

		if(!(ranNum >= 70))
		addActor(p);
		lastPlatform = p;
	}
	
	
	public void addPlatform(Platform p, float yPos) {
		
		p.setWorldBounds(x, y, width, height);
		p.setX(x + width);
		p.addPhysics(physics_world);
		p.setY(yPos);
		addActor(p);
		
	}
	
	public void addMonster(Monster p, float yPos) {
		
		p.setWorldBounds(x, y, width, height);
		p.setX(x + width);
		p.addPhysics(physics_world);
		p.setY(yPos);
		addActor(p);
	}
	
	public void addPlatformSpawn(PlatformSpawn p) {
		//float ran_y = (float)(Math.random() * (height/2));

		//p.setY(ran_y);
		p.setY(0);
		p.setX(x + width);
		
		addActor(p);
		
		
	}
	
	public void addBackgroundSpawn(BackgroundSpawn s) {
		float ran_y = 0;
		float ran_x = x + width;
		
		s.setY(ran_y);
		s.setX(ran_x);
		
		addActor(s);
		
		
	}
	
	public void addBackground(Background b, boolean reuse) {
		
		b.setWorldBounds(x, y, width, height);

		b.setX(x + width);
		//b.setY((float) (-Math.random() * (height/2)));
		//b.setY(0);
		
		if(!reuse)
			addActor(b);
		
	}
	
	
	private float oldSpeed = 25;
	public void freezeWorld() {
		
		if(getState() != WorldState.frozen) {
			setWorldState(WorldState.frozen);
			oldSpeed = speed;
			//speed = 0;
		}
	}
	
	public void unfreeze() {

		if(getState() != WorldState.moving) {
			setWorldState(WorldState.moving);
			speed = oldSpeed;
			//worldTimer.reset();
		}
	}
	
	
}
