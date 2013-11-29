package com.omg.ssworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSEntity;
import com.omg.gdxlucid.Timer;

public class WorldManager extends JSActor {

	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private int speed = 10;
	
	Timer worldTimer;
	Timer backTimer;
	
	World physics_world;
	
	
	public WorldManager(int x, int y, int width, int height) {
		 setDimensions(x,y,width,height);
		
		 worldTimer = new Timer();
		 worldTimer.start();
		 
		 backTimer = new Timer();
		 backTimer.start();
	}
	
	public void setDimensions(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void addPhysics(World physics_world) {
		this.physics_world = physics_world;
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		
		for(Actor a : this.getChildren()) {
			
			int customSpeed = 0;
			
			if(((JSActor)a).hasTag("Background"))
				customSpeed = ((Background)a).getCustomSpeed();
			a.translate(-(speed + customSpeed), 0);
		}
    	
    	
		updatePlatformCreation();
        updateBackgroundCreation();
		
	}
	
	
	public void updatePlatformCreation() {
		if(worldTimer.getTime()  > 255){
			Platform p = new Platform();
			p.addPhysics(physics_world);
			addPlatform(p);
			worldTimer.reset();
		}
		
		
	}
	
	public void updateBackgroundCreation() {
		if(backTimer.getTime()  > 700){
			StarryBackground b = new StarryBackground();
			addBackground(b);
			backTimer.reset();
		}
	}
	
	private float last_y = 0;
	
	public void addPlatform(Platform p) {
		
		
		int ranNum = (int)(Math.random() * 100);
				
		p.setWorldBounds(x, y, width, height);
		p.setX(x + width);
		
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
		
	}
	
	public void addBackground(StarryBackground b) {
		
		b.setWorldBounds(x, y, width, height);

		b.setX(x + width);
		b.setY((float) (-Math.random() * (height/2)));
		
		addActor(b);
		
	}
	
	
	
}
