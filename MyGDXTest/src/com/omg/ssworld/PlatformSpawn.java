package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.omg.drawing.JSActor;
import com.omg.gdxlucid.Timer;
import com.omg.ssworld.WorldManager.WorldState;

public class PlatformSpawn extends JSActor {

	
	WorldManager worldManager;
	World physics_world;

	int length = 1;
	int currentLength = 0;
	
	
	Timer timer;
	boolean isOn = true;
	
	public boolean isOn() {
		return isOn;
	}
	
	public PlatformSpawn(WorldManager manager, World physics, int length){
		super(new TextureRegion(new Texture(Gdx.files.internal("data/laser.png")),0,0,32,64));
		this.length = length;
		this.worldManager = manager;
		this.physics_world = physics;
		
		timer = new Timer();
		timer.start();
		
		addTag("STATIC");
		addTag("P_spawn");
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		

		for(Actor a : this.getChildren()) {
			
			if(!((JSActor)a).hasTag("STATIC")) {
			
				int customSpeed = 0;
				
				if(worldManager.getState() == WorldState.frozen)
					break;
				a.translate(-(worldManager.speed + customSpeed), 0);
			}
		}
		
		if(!isOn)
			return;
		
		//30000
		if(timer.getTime()  > (54000.0f / (((float)worldManager.speed)/10.0f) * Gdx.graphics.getDeltaTime())){
			boolean canReuse = false;
			Platform p = null;
			
			//Gdx.app.log("P", "CREATING PLATFORM BABY :) :) SIZE: " + worldManager.getChildren().size);
			
			for(Actor a : worldManager.getChildren()) {

				if(((JSActor)a).hasTag("Platform")) {

					if(!((Platform)a).isActive) {

						((Platform)a).isActive = true;
						p = ((Platform)a);
						canReuse = true;
						//Gdx.app.log("P", "REUSING PLATFORM :) :)");


					}
				}
			}
			
			if(!canReuse) {
				p = new Platform();
				if(this.getY() > 700)
					p = new CeilingPlatform();
				
				//Gdx.app.log("P", "CREATING NEW PLATFORM >:( >:(");

			}
			
			currentLength++;
			timer.reset();


			
			worldManager.addPlatform(p, last_y, this);
			//addPlatform(p, last_y, canReuse);

			if(currentLength == length) {
				Monster monster = new Monster();				
				//addMonster(monster, this.getY() + 100);
				worldManager.addMonster(monster, last_y + 1250); //150

				//this.remove();
				this.turnOff();
				unchain();
			} else {
				chain();
			}
		}
		
	}
	
	boolean samePlatformChain = true;
	
	public boolean shouldChain() {
		
		return samePlatformChain;
	}
	
	public void unchain() {
		samePlatformChain = false;
	}
	public void chain() {
		samePlatformChain = true;
	}
	
	private float last_y = 0;
	private Platform lastPlatform;
	
	/*
	public void addPlatform(Platform p) {
		
		
		int ranNum = (int)(Math.random() * 100);
				
		p.setWorldBounds(worldManager.getWorldX(), worldManager.getWorldY(), worldManager.getWorldWidth(), worldManager.getWorldHeight());
		
		if(lastPlatform == null)
			p.setX(worldManager.getWorldX() + worldManager.getWorldWidth());
		else
			p.setX(lastPlatform.getX() + lastPlatform.getTextureWidth() - 100);
		
		float ran_y = (float)(Math.random() * (worldManager.getWorldHeight()/2));
		
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
	
	public void addPlatform(Platform p, float yPos, boolean canReuse) {
		
		p.setWorldBounds(worldManager.getWorldX(), worldManager.getWorldY(), worldManager.getWorldWidth(), worldManager.getWorldHeight());
		if(lastPlatform == null)
			p.setX(worldManager.getWorldX() + worldManager.getWorldWidth());
		else
			p.setX(lastPlatform.getX() + lastPlatform.getTextureWidth());
		p.addPhysics(physics_world);
		p.setY(yPos);
		if(!canReuse) {
			addActor(p);

		} 
		lastPlatform = p;
		
	}
	

	
	public void addMonster(Monster p, float yPos) {
		
		p.setWorldBounds(worldManager.getWorldX(),worldManager.getWorldY(), worldManager.getWorldWidth(), worldManager.getWorldHeight());
		p.setX(worldManager.getWorldX() + worldManager.getWorldWidth());
		p.addPhysics(physics_world);
		p.setY(yPos);
		addActor(p);
	}
	
	
	public void turnOff() {
		isOn = false;
		//last_y = (float)(Math.random() * (worldManager.getWorldHeight()/2));
		last_y = (float)(Math.random() * (worldManager.getWorldHeight()/2) - 1000);
		lastPlatform = null;
		
	}
	
	public void turnOn(int length) {
		isOn = true;
		this.length = length;
		currentLength = 0;
		timer.reset();
		timer.start();

	}
	
	
}
