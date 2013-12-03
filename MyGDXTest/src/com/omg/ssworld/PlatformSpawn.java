package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.drawing.JSActor;
import com.omg.gdxlucid.Timer;

public class PlatformSpawn extends JSActor {

	
	WorldManager worldManager;
	int length = 1;
	int currentLength = 0;
	
	
	Timer timer;
	
	public PlatformSpawn(WorldManager manager, int length){
		super(new TextureRegion(new Texture(Gdx.files.internal("data/laser.png")),0,0,32,64));
		this.length = length;
		this.worldManager = manager;
		
		timer = new Timer();
		timer.start();
		
		addTag("STATIC");
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		
		if(timer.getTime()  > (15000.0f / (((float)worldManager.speed)/10.0f) * Gdx.graphics.getDeltaTime())){
			Platform p = new Platform();
			if(this.getY() > 700)
				p = new CeilingPlatform();
			worldManager.addPlatform(p, this.getY());
			timer.reset();
			currentLength++;
			if(currentLength == length)
				this.remove();
		}
		
	}
	
}
