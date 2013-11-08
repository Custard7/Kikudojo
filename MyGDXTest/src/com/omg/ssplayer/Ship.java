package com.omg.ssplayer;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSEntity;
import com.omg.drawing.JSEntity.JSMovementProperties;
import com.omg.drawing.JSEntity.JSVector2;

public class Ship extends JSActor {

	JSMovementProperties movement;
	JSMovementProperties rotation;
	

	
	
	public Ship () {
		super(new TextureRegion(new Texture(Gdx.files.internal("data/ship_blue.png")),0,0,256,256));
       
		
		
		setOriginX(130);
		setOriginY(55);
		
		setScale(0.40f);
		
		
        movement = new JSMovementProperties();
		movement.setFriction(0.98f);
		movement.setMaxVelocity(-10, 10);
		movement.setMaxAcceleration(-2, 2);
		 
		 rotation = new JSMovementProperties();
		 rotation.setFriction(0.98f);
		 
		 setColor(1, 1, 1, 1);
		 
		 
}
	
	
	
	@Override
	 public void draw (SpriteBatch batch, float parentAlpha) {
		 super.draw(batch, parentAlpha);
		
         
         movement.update();
         translate(movement.getVelocity().x, movement.getVelocity().y);
 		
 		rotation.update();
 		super.rotate(rotation.getVelocity().x);
	 }
	
	public void accelerateX(float xAmount) {
		movement.accelerate(new JSVector2(xAmount, 0));
	}
	
	public void accelerateY(float yAmount) {
		movement.accelerate(new JSVector2(0, yAmount));
	}
	
	public void accelerate(float xAmount, float yAmount) {
		accelerateX(xAmount);
		accelerateY(yAmount);
	}
	
	@Override
	public void moveForward(float amount) {
		accelerateX((float)(-amount * Math.sin(Math.toRadians(getRotation()))));
	    accelerateY((float)(amount * Math.cos(Math.toRadians(getRotation()))));
	}
	
	
	@Override
	public void rotate(float degrees) {
		rotation.accelerate(new JSVector2(degrees, 0));
	}
	
	
	
	public void shootLaser(float speed) {
		
		
		shootLaser(speed, new Color(0,1,0,1));
		
	}
	
	public void shootLaser(float speed, Color color) {
		Laser l = new Laser(getRotation(), speed, color);
		l.setPosition(getX() + getOriginX() - 5, getY()+getOriginY());
		getParent().addActorBefore(this, l);
	}
	

	
}
