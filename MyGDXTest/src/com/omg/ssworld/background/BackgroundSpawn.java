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
	
	public BackgroundSpawn(WorldManager manager, String backgroundClassPath){
		super(new TextureRegion(GameManager.getAssetsManager().get("data/laser.png", Texture.class),0,0,32,64));

		this.worldManager = manager;
		
		timer = new Timer();
		timer.start();
		
		addTag("STATIC");
		addTag("B_Spawn");
		
		// with reflection
		try {
			c = (Class<Background>) Class.forName(backgroundClassPath);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		
		for(Actor a : this.getChildren()) {
			
			if(!((JSActor)a).hasTag("STATIC")) {
			
				int customSpeed = 0;
				
				if(((JSActor)a).hasTag("Background"))
					customSpeed = ((Background)a).getCustomSpeed();
				else if(worldManager.getState() == WorldState.frozen)
					break;
				a.translate(-(worldManager.speed + customSpeed), 0);
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
			
			if(!canReuse)
				try {
					b = c.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
