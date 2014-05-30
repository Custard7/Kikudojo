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
import com.omg.ssplayer.Kiku;
import com.omg.ssplayer.mechanics.BubblePackage;
import com.omg.ssworld.background.BackgroundSpawn;

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
	Timer nanoKiTimer;
	
	Timer totalPlayTime;
	
	World physics_world;
	
	BackgroundSpawn foregroundSpawn;
	BackgroundSpawn riverSpawn;
	Kiku player;
	
	NanoKiSpawn nanoKiSpawn;
	PlatformSpawn platformSpawn;
	
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
		 
		 nanoKiTimer = new Timer();
		 nanoKiTimer.start();
		 
		 nanoKiSpawn = new NanoKiSpawn(this, physics_world, 0);
		 platformSpawn = new PlatformSpawn(this, physics_world, 15, nanoKiSpawn);
		 
		/* addBackgroundSpawn(new BackgroundSpawn(this, "com.omg.ssworld.background.SkyBack"));

		 */
		 
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Sky", 0, 0))); 				//Sky Back
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Back Clouds", -7, -100))); 	//Clouds Back 15
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Back F", -10, -200))); 		//Mountain Back
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Front Clouds", 0, 300))); 		//Clouds Foreground
		 addBackgroundSpawn(new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Mid F", 1, -250))); 			//Grass Middle
		 
		 riverSpawn = new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("River", 10, -1200));
		 addBackgroundSpawn(riverSpawn); 			//River
		 
		 foregroundSpawn =  new BackgroundSpawn(this, com.omg.ssworld.background.BProperties.makeProperties("Front F", 4, -400));
		 addBackgroundSpawn(foregroundSpawn); 			//Grass Front

		 addTag("WorldManager");
		 
	}
	
	
	public void setDimensions(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void addPhysics(World physics_world) {
		this.physics_world = physics_world;
		 //addPlatformSpawn(new PlatformSpawn(this, physics_world, 5, nanoKiSpawn));
		 addPlatformSpawn(platformSpawn);
		 addNanoKiSpawn(nanoKiSpawn);
		// addNanoKiSpawn(new NanoKiSpawn(this, physics_world, 5));
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		for(Actor a : this.getChildren()) {
			
			if(!((JSActor)a).hasTag("STATIC")) {
			
				int customSpeed = 0;
				
				if(((JSActor)a).hasTag("Background"))
					customSpeed = ((Background)a).getCustomSpeed();
				
				/*if(getState() == WorldState.frozen) {
					customSpeed = 0;
					speed = 0;
					return;
				}*/
				
				if(getState() != WorldState.frozen)
					a.translate(-(speed + customSpeed), 0);

			}
		}

    	
		if(getState() != WorldState.frozen) {
			updatePlatformCreation();
			updateNanoKiCreation();
		}
		else {
			
			if(speed > 0)
				speed -= decelerationSpeed;
			if(speed < 0)
				speed = 0;
			
		}
		
		
		//updateBackgroundCreation();
		
        
        
        
		/*
		if(totalPlayTime.getTime() > 60000)
		{
			if(Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS)
				TestFlight.passCheckpoint("Played for a minute!");
    		totalPlayTime.reset();
		}*/
		
		//Gdx.app.log("WORLD","CHILDREN: " + this.getChildren().size);
		
		//this.getZIndex()
		
		if(lastChildAdded != null) {
			int maxZIndex = lastChildAdded.getZIndex();
			if(maxZIndex >= 0) {
				player.setZIndex(maxZIndex);
				riverSpawn.setZIndex(maxZIndex + 12);
				foregroundSpawn.setZIndex(maxZIndex + 13);
			}
		}
	}
	
	public void setPlayer(Kiku k) {
		this.player = k;
	}
	
	
	public void updatePlatformCreation() {
		
		//120000
		/*if(worldTimer.getTime()  > 200000 * Gdx.graphics.getDeltaTime()){

			for(Actor a : this.getChildren()) {
				
				if(((JSActor)a).hasTag("P_spawn")) {
			
					if(!((PlatformSpawn)a).isOn()) {
						int randLength = (int) ((Math.random() * 8) + 4);
						((PlatformSpawn)a).turnOn(randLength);
						worldTimer.reset();

						return;

					}
				}
			}
			
			worldTimer.reset();
		}*/
		
		/*
		if(platformSpawn.getLastInChain().isLeftOf(0.50f) && !platformSpawn.isOn()) {
			int randLength = (int) ((Math.random() * 8) + 4);
			platformSpawn.turnOn(randLength);
		}*/
		platformSpawn.startIfLeftOf(.7f);
		/*
		if(platformSpawn.getHeightDifference() < 1) {
			float percent = .2f/(platformSpawn.getHeightDifference());
			if(percent < .5f)
				percent = .5f;
			if(percent > .9f) {
				percent = .9f;
			}
			Gdx.app.log("Lower: Platform",":" + percent + " delta: " + platformSpawn.getHeightDifference());

			platformSpawn.startIfLeftOf(percent);

			
		} else {
			float percent = 1.5f/platformSpawn.getHeightDifference();

			if(percent > .9f)
			 percent = .9f;
			if(percent < .5f) {
				percent = .5f;
			}
			Gdx.app.log("Higher: Platform",":" + percent + " delta: " + platformSpawn.getHeightDifference());

			platformSpawn.startIfLeftOf(percent);

		}*/
		
		
		
	}
	
	public void updateNanoKiCreation() {
		/*if(1==1)
			return;
		//120000
		if(nanoKiTimer.getTime()  > 2000 * Gdx.graphics.getDeltaTime()){

			
			for(Actor a : this.getChildren()) {
				
				if(((JSActor)a).hasTag("NK_spawn")) {
			
					if(!((NanoKiSpawn)a).isOn()) {
						((NanoKiSpawn)a).turnOn((int) ((Math.random() * 8) + 4));
						nanoKiTimer.reset();

						return;

					}
				}
			}
			
			nanoKiTimer.reset();
		}*/
		
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
	
	/*public void addPlatform(Platform p) {
		
		
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
	}*/
	
	
	Platform lastPlatformX = null;
	//boolean samePlatformChain = false;
	

	
	public void addPlatform(Platform p, float yPos, PlatformSpawn spawn) {
		
		p.setWorldBounds(x, y, width, height);
		if(spawn.shouldChain() && lastPlatformX != null) {
			p.setX(lastPlatformX.getX() + lastPlatformX.getWidth() * 4);
		} else {
			p.setX(x + width);
		}
		p.addPhysics(physics_world);
		p.setY(yPos);
		addActor(p);
		
		
		lastPlatformX = p;
		
	}
	
	NanoKi lastNanoKi = null;
	
	public void addNanoKi(NanoKi p, float yPos, NanoKiSpawn spawn) {
		
		p.setWorldBounds(x, y, width, height);
		if(spawn.shouldChain() && lastNanoKi != null) {
			p.setX(lastNanoKi.getX() + lastNanoKi.getWidth() * 4);
		} else {
			p.setX(x + width);
		}
		p.addPhysics(physics_world);
		p.setY(yPos);
		addActor(p);
		
		
		lastNanoKi = p;
		
	}
	
	public void addMonster(Monster p, float yPos) {
		
		p.setWorldBounds(x, y, width, height);
		p.setX(x + width);
		p.addPhysics(physics_world);
		p.setY(yPos);
		addActor(p);
	}
	
	public void addBubble(BubblePackage b, float yPos) {
		b.setWorldBounds(x, y, width, height);
		b.setX(x + width);
		b.addPhysics(physics_world);
		b.setY(yPos);
		addActor(b);
	}
	
	public void addLaser(Laser l, float xPos, float yPos, Monster m) {
		l.setWorldBounds(x, y, width, height);
		l.setX(xPos - l.getWidth() + 35);
		l.addPhysics(physics_world);
		l.setY(yPos + l.getHeight()/2 + 25);
		addActor(l);
		l.setZIndex(m.getZIndex()-1);

	}
	
	public void addPlatformSpawn(PlatformSpawn p) {

		p.setY(0);
		p.setX(x + width);
		
		addActor(p);
		
		
	}
	
	public void addNanoKiSpawn(NanoKiSpawn n) {

		n.setY(0);
		n.setX(x + width);
		
		addActor(n);
		
		
	}
	
	public void addBackgroundSpawn(BackgroundSpawn s) {
		float ran_y = 0;
		float ran_x = x + width;
		
		s.setY(ran_y);
		s.setX(ran_x);
		
		//s.setZIndex(1000);
		addActor(s);
		
		
	}
	
	/*public void addBackground(Background b, boolean reuse) {
		
		b.setWorldBounds(x, y, width, height);

		b.setX(x + width);
		//b.setY((float) (-Math.random() * (height/2)));
		//b.setY(0);
		
		if(!reuse)
			addActor(b);
		
	}*/
	
	Actor lastChildAdded;
	
	@Override
	public void addActor(Actor a) {
		super.addActor(a);
		lastChildAdded = a;
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
