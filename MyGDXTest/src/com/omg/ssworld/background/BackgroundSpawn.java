package com.omg.ssworld.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.omg.drawing.JSActor;
import com.omg.gdxlucid.Timer;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Background;
import com.omg.ssworld.CeilingPlatform;
import com.omg.ssworld.Monster;
import com.omg.ssworld.Platform;
import com.omg.ssworld.WorldManager;
import com.omg.ssworld.WorldManager.WorldState;

public class BackgroundSpawn extends JSActor {

	WorldManager worldManager;

	
	Timer timer;
	Class<Background> c;
	
	boolean useReflection = false;
	BProperties bProperties;
	
	boolean freezeWorldStopsEverything = false;
	
	public BackgroundSpawn(WorldManager manager, String backgroundClassPath){
		//super(new TextureRegion(GameManager.getAssetsManager().get("data/laser.png", Texture.class),0,0,32,64));
		super(new TextureRegion(GameManager.getAssetsManager().getTexture("Laser"),0,0,32,64));

		init(manager);
		
		// with reflection
		try {
			c = (Class<Background>) Class.forName(backgroundClassPath);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		useReflection = true;
		
	}
	
	public BackgroundSpawn(WorldManager manager, BProperties p) {
		super(new TextureRegion(GameManager.getAssetsManager().getTexture((p.getFileName())),0,0,p.getWidth(),p.getHeight()));
		//super(new TextureRegion(GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath(p.getFileName()), Texture.class),0,0,p.getWidth(),p.getHeight()));
		//super(new TextureRegion(GameManager.getAssetsManager().getTexture(p.getFileName()),0,0,p.getWidth(),p.getHeight()));

		init(manager);
		
		bProperties = p;
		
		useReflection = false;
		
	}
	
	private void init(WorldManager manager) {
		this.worldManager = manager;
		
		timer = new Timer();
		timer.start();
		
		addTag("STATIC");
		addTag("B_Spawn");
		
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		float speed = worldManager.speed;
		if(this.getChildren().size <= 3)
			speed = 1000;
		
		if(!(freezeWorldStopsEverything && worldManager.getState() == WorldState.frozen)) {
		
			for(Actor a : this.getChildren()) {
				
				if(!((JSActor)a).hasTag("STATIC")) {
				
					int customSpeed = 0;
					
					if(((JSActor)a).hasTag("Background"))
						customSpeed = ((Background)a).getCustomSpeed();
	
					if(speed + customSpeed >= 0)
						a.translate(-(speed + customSpeed), 0);
					else if(speed >= 0)
						a.translate(-(speed), 0);
					
				}
			}
		}
		
		boolean canCreate = false;
		
		if(lastBackground == null){
			//if(timer.getTime()  > (190000.0f / (((float)worldManager.speed)/10.0f) * Gdx.graphics.getDeltaTime())){
				canCreate = true;
			//}
			
		} else if (lastBackground.getX() + lastBackground.getWidth() < worldManager.getX() + worldManager.getWidth()) {
			canCreate = true;
		}
		
		//if(timer.getTime()  > (190000.0f / (((float)worldManager.speed)/10.0f) * Gdx.graphics.getDeltaTime())){
		if(canCreate){
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
			
			if(!canReuse) {
				
				if(useReflection) {
					try {
						b = c.newInstance();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					b = new GrasslandBackgrounds(bProperties);
				}
			}
			addBackground(b, canReuse);			
			timer.reset();
			
		}
		
		
		
	}
	
	
	Background lastBackground;
	
	public void addBackground(Background b, boolean reuse) {
		
		b.setWorldBounds(worldManager.getWorldX(), worldManager.getWorldY(), worldManager.getWorldWidth(), worldManager.getWorldHeight());

		if(lastBackground == null)
			b.setX(worldManager.getWorldX() + worldManager.getWorldWidth());
		else
			b.setX(lastBackground.getX() + 1280 * 1.5f);
		//b.setY((float) (-Math.random() * (height/2)));
		//b.setY(0);
		
		if(!reuse)
			addActor(b);
		lastBackground = b;
		
	}
	
}
