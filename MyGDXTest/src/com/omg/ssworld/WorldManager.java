package com.omg.ssworld;

import java.util.ArrayList;
import java.util.List;

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
	
	World physics_world;
	
	
	public WorldManager(int x, int y, int width, int height) {
		 setDimensions(x,y,width,height);
		
		 worldTimer = new Timer();
		 worldTimer.start();
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
			a.translate(-speed, 0);
		}
    	
    	
		updatePlatformCreation();
        
		
	}
	
	
	public void updatePlatformCreation() {
		if(worldTimer.getTime()  > 160){
			Platform p = new Platform();
			p.addPhysics(physics_world);
			addPlatform(p);
			worldTimer.reset();
		}
		
		
	}
	
	private float last_y;
	
	public void addPlatform(Platform p) {
		p.setWorldBounds(x, y, width, height);
		p.setX(x + width);
		p.setY((float)(Math.random() * (height/2)));
		addActor(p);
		
		last_y = y;
	}
	
	
	
}
