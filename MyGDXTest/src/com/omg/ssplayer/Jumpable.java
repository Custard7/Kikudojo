package com.omg.ssplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSEntity.JSMovementProperties;
import com.omg.drawing.JSEntity.JSVector2;
import com.omg.gdxlucid.Timer;
import com.omg.screens.GameScreen;

public class Jumpable extends JSActor {

	JSMovementProperties movement;

	public enum JumpableState {
		onGround,
		inAir
		
	}
	JumpableState jumpState = JumpableState.onGround;
	
	boolean canJump = true;
	boolean jumpedFromGround = false;
	
	public float gravity = 1.0f;
	
	Body body;
	
	Timer isInAirTimer;
	
	public Jumpable()
	{
		super(new TextureRegion(new Texture(Gdx.files.internal("data/ship_blue.png")),0,0,256,256));

		
		setOriginX(130);
		setOriginY(55);
		
		//setScale(0.40f);
		
		movement = new JSMovementProperties();
		movement.setFriction(0.98f);
		movement.setMaxVelocity(-100, 100);
		movement.setMaxAcceleration(-100, 100);
		
		isInAirTimer = new Timer();
		//isInAirTimer.start();
		
		addTag("Jumpable");
	}
	
	public void addPhysics(World physics_world) {
		createBody(physics_world);
	}
	
	
	@Override
	 public void draw (SpriteBatch batch, float parentAlpha) {
		 super.draw(batch, parentAlpha);
		
		 if(isInAirTimer.getTime() > 250 && jumpState == JumpableState.onGround && fellFromPlatform)
		 {
			jumpState = JumpableState.inAir;
			canJump = true;
			
		 }
		 

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
		 

        //MOVEMENT UNDO
        movement.update();
        translate(movement.getVelocity().x, movement.getVelocity().y);
		body.setTransform(this.getX() + this.getOriginX() - 100, this.getY() + this.getOriginY() + 100, 0);
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
		
		//body.applyLinearImpulse(0, 100, getX(), getY(), true);
		
		switch(jumpState) {
	
		case onGround:
			accelerateY(strength);
			jumpState = JumpableState.inAir;
			jumpedFromGround = true;
			break;
		case inAir:
			//accelerateY(strength);
			if(canJump && !jumpedFromGround) {
				movement.stop();
				accelerateY(strength);
				canJump = false;
			}
			break;
		default:
			break;
				
		}
		
	}
	
	public void hitGround() {
		
		jumpState = JumpableState.onGround;
		movement.stop();
		jumpedFromGround = false;
		isInAirTimer.reset();
		fellFromPlatform = false;
		
	}
	
	boolean fellFromPlatform = false;
	
	public void inAir() {
		//jumpState = JumpableState.inAir;
		//canJump = true;
		isInAirTimer.reset();
		isInAirTimer.start();
		fellFromPlatform = true;
	}
	
	
	private void createBody(World physics_world) {
		
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(300, 300);

		// Create our body in the world using our body definition
		body = physics_world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(10f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit
		

		// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);
		
		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();
		
		
		body.setUserData(this);
	}
	
	
	
}
