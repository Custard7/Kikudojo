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
import com.omg.ssworld.Platform.PlatformType;
import com.omg.ssworld.WorldManager.WorldState;

public class PlatformSpawn extends JSActor {

	
	WorldManager worldManager;
	World physics_world;

	NanoKiSpawn nanoKiSpawn;
	
	int length = 1;
	int currentLength = 0;
	
	
	Timer timer;
	boolean isOn = true;
	
	public boolean isOn() {
		return isOn;
	}
	
	public PlatformSpawn(WorldManager manager, World physics, int length, NanoKiSpawn spawn){
		super(new TextureRegion(new Texture(Gdx.files.internal("data/laser.png")),0,0,32,64));
		this.length = length;
		this.worldManager = manager;
		this.physics_world = physics;
		this.setNanoKiSpawn(spawn);
		
		timer = new Timer();
		timer.start();
		
		addTag("STATIC");
		addTag("P_spawn");
	}
	
	int spikesInARow = 0;
	int maxSpikesInARow = 2;
	
	boolean startingPlatforms = true;
	
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
						int ranNum = (int) (Math.random() * 100);
						
						if(ranNum < 60) {
							p.setType(PlatformType.normal);
							spikesInARow = 0;
						} else if(ranNum < 80) {
							p.setType(PlatformType.moving);
							spikesInARow = 0;
						} else {
							boolean gotSpikes = false;
							if(length != (currentLength + 1) ) {
								if(currentLength != 0) {
									if(spikesInARow < maxSpikesInARow) {
										p.setType(PlatformType.spikes); 
										spikesInARow++;
										gotSpikes = true;
									}
								}
							}
							if(!gotSpikes) {
								p.setType(PlatformType.normal);
								spikesInARow = 0;
							}
						}
						
						
						

					}
				}
			}
			
			if(!canReuse) {
				p = new Platform();
				/*if(this.getY() > 700)
					p = new CeilingPlatform();*/
				
				//Gdx.app.log("P", "CREATING NEW PLATFORM >:( >:(");
				
			}
			
			if(startingPlatforms) {
				p.setType(PlatformType.starting);
			}
			
			currentLength++;
			timer.reset();
			worldManager.addPlatform(p, last_y, this);
			//addPlatform(p, last_y, canReuse);
			
			addAnimal();
			
			if(p.getType() != PlatformType.spikes) {
				
				int random = (int)(Math.random() * 100);
				
				if(random > 75) {
					if(nanoKiSpawn != null)
						nanoKiSpawn.createChain(getHeight() + 305, 5);
				}
			}

			if(currentLength == length) {
				if(!startingPlatforms)
				{
					Monster monster = new Monster();				
					worldManager.addMonster(monster, last_y + 1100); //150
				}
				
				lastChainLastPlatform = p;
				startingPlatforms = false;

				//this.remove();
				this.turnOff();
				unchain();
			} else {
				chain();
			}
		}
		
	}
	
	public void startIfLeftOf(float screenPercent) {
		int randLength = (int) ((Math.random() * 8) + 4);

		
		if(getLastInLastChain() == null) {
			//Gdx.app.log("PlatformSpawn","LAST IS NULL");
			if(!isOn()) {
				turnOn(randLength);
			}
		} else if(getLastInLastChain().isLeftOf(screenPercent) && !isOn()) {
			turnOn(randLength);
		}
	}	
	
	public void addAnimal() {
		
		int ranNum = (int) (Math.random() * 1000);
		if(ranNum < 990) 
			return;

		BubblePackage b = new BubblePackage();
		if (ranNum < 995) {
			b.setType(PackageType.frog);
		} else {
			b.setType(PackageType.kiDude);
		}
		worldManager.addBubble(b, last_y + 1250);
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
	
	public void setNanoKiSpawn(NanoKiSpawn spawn) {
		this.nanoKiSpawn = spawn;
	}
	
	private float last_y = -600;
	private Platform lastPlatform;
	private Platform lastChainLastPlatform;
	
	
	public void createPlatformChain(int xPos, int yPos, int length) {
		
		
		for(int x = 0; x < length; x++) {
			Platform p = new Platform();
			p.setType(PlatformType.starting);
			if(x == 0)
				worldManager.addPlatform(p, xPos, yPos, this);
			else
				worldManager.addPlatform(p, yPos, this);

		}

		
		
	}

	/*
	public void addPlatform(Platform p, float yPos, boolean canReuse) {
		
		p.setWorldBounds(worldManager.getWorldX(), worldManager.getWorldY(), worldManager.getWorldWidth(), worldManager.getWorldHeight());
		if(lastPlatform == null)
		{
			p.setX(worldManager.getWorldX() + worldManager.getWorldWidth());
			
		}
		else {
			p.setX(lastPlatform.getX() + lastPlatform.getTextureWidth());
		}
		

		
		p.addPhysics(physics_world);
		p.setY(yPos);
		if(!canReuse) {
			addActor(p);

		} 
		lastPlatform = p;
		
	}*/
	

	
	public void addMonster(Monster p, float yPos) {
		
		p.setWorldBounds(worldManager.getWorldX(),worldManager.getWorldY(), worldManager.getWorldWidth(), worldManager.getWorldHeight());
		p.setX(worldManager.getWorldX() + worldManager.getWorldWidth());
		p.addPhysics(physics_world);
		p.setY(yPos);
		addActor(p);
	}
	
	int heightOffset = 800;
	
	public void turnOff() {
		isOn = false;
		last_y = (float)(Math.random() * (worldManager.getWorldHeight()/2.2f) - heightOffset);
		lastPlatform = null;
		
	}
	
	public Platform getLastInLastChain() {
		return lastChainLastPlatform;
	}
	
	public void turnOn(int length) {
		isOn = true;
		this.length = length;
		currentLength = 0;
		timer.reset();
		timer.start();

	}
	
	public float getHeight() {
		return last_y + heightOffset;
	}
	
	public float getPlatformHeight() {
		if(lastPlatform != null)
			return lastPlatform.getY();
		return last_y;
	}
	
	public float getLastHeight() {
		if(getLastInLastChain() != null)
			return getLastInLastChain().getY();
		return last_y;
	}
	
	public float getHeightDifference() {
		if(getLastHeight() != 0)
			return getPlatformHeight() / getLastHeight();
		return 1;
	}
	
}
