package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.omg.drawing.JSActor;
import com.omg.gdxlucid.Timer;
import com.omg.ssplayer.mechanics.BubblePackage;
import com.omg.ssplayer.mechanics.BubblePackage.PackageType;
import com.omg.ssworld.NanoKi.NanoKiType;
import com.omg.ssworld.Platform.PlatformType;
import com.omg.ssworld.WorldManager.WorldState;

public class NanoKiSpawn extends JSActor {

	
	WorldManager worldManager;
	World physics_world;

	int length = 1;
	int currentLength = 0;
	
	
	Timer timer;
	boolean isOn = true;
	
	public boolean isOn() {
		return isOn;
	}
	
	public NanoKiSpawn(WorldManager manager, World physics, int length){
		super(new TextureRegion(new Texture(Gdx.files.internal("data/laser.png")),0,0,32,64));
		this.length = length;
		this.worldManager = manager;
		this.physics_world = physics;
		
		timer = new Timer();
		timer.start();
		
		addTag("STATIC");
		addTag("NK_spawn");
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
		
		//54000
		//if(timer.getTime()  > (24000.0f / (((float)worldManager.speed)/10.0f) * Gdx.graphics.getDeltaTime())){
		if(currentLength < length) {
			boolean canReuse = false;
			NanoKi p = null;
			
			
			for(Actor a : worldManager.getChildren()) {

				if(((JSActor)a).hasTag("NanoKi")) {

					if(!((NanoKi)a).isActive) {

						//((NanoKi)a).isActive = true;
						((NanoKi)a).activate();
						p = ((NanoKi)a);
						canReuse = true;
						//Gdx.app.log("P", "REUSING PLATFORM :) :)");
						/*int ranNum = (int) (Math.random() * 100);
						
						if(ranNum < 60) {
							p.setType(NanoKiType.normal);
						} else if(ranNum < 80) {
							p.setType(NanoKiType.moving);
						} else {
							p.setType(NanoKiType.spikes);
						}*/
						
						if(currentLength == length){
						//	p.setType(NanoKiType.special);
						}
						
						

					}
				}
			}
			
			if(!canReuse) {
				p = new NanoKi();
				/*if(this.getY() > 700)
					p = new CeilingPlatform();*/
				
				//Gdx.app.log("P", "CREATING NEW PLATFORM >:( >:(");

			}
			
			currentLength++;
			//timer.reset();
			worldManager.addNanoKi(p, last_y, this);
			//addPlatform(p, last_y, canReuse);
			

			if(currentLength == length) {
				//this.turnOff();
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
	}*/
	
	
	public void turnOff() {
		isOn = false;
		setHeight((float)(Math.random() * (worldManager.getWorldHeight()/2)));
		
	}
	
	public void setHeight(float height) {
		
		last_y = height;
		lastPlatform = null;
	}

	public void createChain(float height, int length) {
		currentLength = 0;
		this.length = length;
		setHeight(height);
	}
	
	
	public void turnOn(int length) {
		isOn = true;
		this.length = length;
		currentLength = 0;
		timer.reset();
		timer.start();

	}
	
	
}
