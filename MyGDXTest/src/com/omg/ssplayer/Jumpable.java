package com.omg.ssplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
	
	boolean frozen = false;
	
	public float gravity = 100.0f;
	
	Body body;
	
	Timer isInAirTimer;
	
	int groundCounts = 0;
	
	int jumpCounts = 0;
	public int maxJumps = 1;
	
	public boolean isStunned = false;
	public boolean playerYFrozen = false;
	
	boolean _isDead = false;
	
	public boolean isDead() {
		return _isDead;
	}
	protected void setDead(boolean v) {
		_isDead = v;
	}
	
	public Jumpable()
	{
		super(new TextureRegion(new Texture(Gdx.files.internal("data/ship_blue.png")),0,0,256,256));

		
		setOriginX(130);
		setOriginY(55);
		
		//setScale(0.40f);
		
		movement = new JSMovementProperties();
		movement.setFriction(0.98f);
		movement.setMaxVelocity(-50, 100);
		movement.setMaxAcceleration(-60, 100);
		
		isInAirTimer = new Timer();
		//isInAirTimer.start();
		
		addTag("Jumpable");
	}
	
	public void addPhysics(World physics_world) {
		createBody(physics_world);
	}
	
	
	@Override
	 public void draw (Batch batch, float parentAlpha) {
		 super.draw(batch, parentAlpha);
		 
		
		 if(!frozen)
		 {
		 
			 //14000
		 if(isInAirTimer.getTime() > 1000 * Gdx.graphics.getDeltaTime() && jumpState == JumpableState.onGround && fellFromPlatform)
		 {
			jumpState = JumpableState.inAir;
			canJump = true;
		
		 }
		 
		 if(playerYFrozen) {
			 movement.stop();
		 }
		 
		 
		 

		 switch(jumpState) {
			
			case onGround:
				break;
			case inAir:
				if(playerYFrozen)
					break;
				accelerateY(-gravity/8);
				if(!(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched()) || isStunned)
					accelerateY(-gravity * 2 );

				break;
			default:
				break;
					
			}
		 

        //MOVEMENT UNDO
        movement.update();
        translate(movement.getVelocity().x* Gdx.graphics.getDeltaTime() * 40, movement.getVelocity().y* Gdx.graphics.getDeltaTime()*40);
        
		 }
		 
		body.setTransform(this.getX() + this.getOriginX() - 30, this.getY() + this.getOriginY() - 15, 0);
	 }
	
	
	
	
	public void accelerateX(float xAmount) {
		movement.accelerate(new JSVector2(xAmount * Gdx.graphics.getDeltaTime(), 0));
	}
	
	public void accelerateY(float yAmount) {
		movement.accelerate(new JSVector2(0, yAmount * Gdx.graphics.getDeltaTime()));
	}
	
	public void accelerate(float xAmount, float yAmount) {
		accelerateX(xAmount);
		accelerateY(yAmount);
	}
	
	
	public void jump(float strength) {
		
		//body.applyLinearImpulse(0, 100, getX(), getY(), true);
		
		/*switch(jumpState) {
	
		case onGround:
			accelerateY(strength);
			jumpState = JumpableState.inAir;
			jumpedFromGround = true;
			jumpCounts++;
			break;
		case inAir:
			//accelerateY(strength);
			if(canJump && (!jumpedFromGround || jumpCounts < maxJumps)) {
				movement.stop();
				accelerateY(strength);
				jumpCounts++;
				if(jumpCounts >= maxJumps)
					canJump = false;
			}
			break;
		default:
			break;
				
		}
		
		fellFromPlatform = false;*/
		
		if(isStunned || isDead())
			return;
		
		switch(jumpState) {
		
		case onGround:
			accelerateY(strength);
			jumpState = JumpableState.inAir;
			jumpCounts = 1;
			break;
		case inAir:
			//accelerateY(strength);
			if((jumpCounts < maxJumps)) {
				if(this.movement.getVelocity().y < 0)
					movement.stop();
				accelerateY(strength);
				jumpCounts++;
				if(jumpCounts >= maxJumps)
					canJump = false;
			}
			break;
		default:
			break;
				
		}
		
		fellFromPlatform = false;
	}
	
	
	public void hitGround() {
		
		if(this.movement.getVelocity().y <= 0 && !isDead()) {
		
			jumpState = JumpableState.onGround;
			movement.stop();
			jumpedFromGround = false;
			isInAirTimer.reset();
			fellFromPlatform = false;
			jumpCounts=0;
			isStunned = false;
		}

	}
	
	public void hitGround(boolean count) {
		hitGround();
		groundCounts++;
		//Gdx.app.log("KIKU", "----HITTING GROUND----- Counts: " + groundCounts);

	}
	
	boolean fellFromPlatform = false;
	
	public void inAir() {
		//jumpState = JumpableState.inAir;
		//canJump = true;
		
		
		
		groundCounts--;
		
		if(groundCounts < 1) {
			isInAirTimer.reset();
			isInAirTimer.start();
			fellFromPlatform = true;
		}
	}
	
	public void addJump() {
		if(jumpCounts > 0) {
			jumpCounts--;
			canJump = true;
		}
	}
	
	public boolean isOnGround() {
		return (groundCounts > 0);
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
	
	public void freeze() {
		//if(frozen = false)
			frozen = true;
	}
	
	public void unfreeze() {
		//if(frozen = true)
			frozen = false;
	}
	
}
