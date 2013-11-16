package com.omg.ssplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSEntity.JSMovementProperties;
import com.omg.drawing.JSEntity.JSVector2;

public class Jumpable extends JSActor {

	JSMovementProperties movement;

	public enum JumpableState {
		onGround,
		inAir
		
	}
	JumpableState jumpState = JumpableState.onGround;
	
	public float gravity = 1.0f;
	
	public Jumpable()
	{
		super(new TextureRegion(new Texture(Gdx.files.internal("data/ship_blue.png")),0,0,256,256));

		
		setOriginX(130);
		setOriginY(55);
		
		setScale(0.40f);
		
		movement = new JSMovementProperties();
		movement.setFriction(0.98f);
		movement.setMaxVelocity(-100, 100);
		movement.setMaxAcceleration(-100, 100);
		
		
	}
	
	
	@Override
	 public void draw (SpriteBatch batch, float parentAlpha) {
		 super.draw(batch, parentAlpha);
		

		 switch(jumpState) {
			
			case onGround:
				break;
			case inAir:
				accelerateY(-gravity/10);
				if(!Gdx.input.isKeyPressed(Keys.SPACE))
					accelerateY(-gravity/3);

				break;
			default:
				break;
					
			}
		 

        
        movement.update();
        translate(movement.getVelocity().x, movement.getVelocity().y);
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
	
	
	public void jump(float strength) {
		
		switch(jumpState) {
	
		case onGround:
			accelerateY(strength);
			jumpState = JumpableState.inAir;
			break;
		case inAir:
			break;
		default:
			break;
				
		}
		
	}
	
	public void hitGround() {
		
		jumpState = JumpableState.onGround;
		movement.stop();
		
	}
	
	
	
	
}
