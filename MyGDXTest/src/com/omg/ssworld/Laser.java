package com.omg.ssworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.omg.drawing.JSActor;
import com.omg.ssplayer.Kiku;
import com.omg.ssplayer.mechanics.BubblePackage.PackageType;
import com.omg.sswindler.GameManager;

public class Laser extends JSActor {
	private int x;
	private int y;
	private int width;
	private int height;
	
	Body body;
	
	protected static int c_width = 1000;
	protected static int c_height = 100;
	
	public float speed = 0;
	public int taps = 5;
	float timeFrozen = 100;
	float currentTime = 0;
	
	boolean playerCaught = false;
	Kiku caughtKiku;
	
	public Laser()
	{	   	
		super(new TextureRegion(GameManager.getAssetsManager().getTexture("Laser"),0,0,c_width,c_height));


		addAction(Actions.forever(Actions.parallel(Actions.alpha(0.25f, 0.2f, Interpolation.exp10), Actions.alpha(1.0f, 0.1f, Interpolation.exp10), Actions.sequence(Actions.color(Color.WHITE, 0.1f, Interpolation.exp10), Actions.color(Color.GREEN, 0.1f, Interpolation.exp10)))));
		addTag("Laser");
	}
	

	
	public void addPhysics(World physics_world) {
		createBody(physics_world);
	}
	
	public void removePhysics() {
		body.getWorld().destroyBody(body);
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(this.getX() < x || kill) {
			body.getWorld().destroyBody(body);
			this.remove();
			
		}
		
		if(playerCaught) {
			currentTime++;
			if(currentTime >= timeFrozen) {
				playerStopHit(caughtKiku);
				killNextRun();
			}
		}
		
		//this.setPosition(this.getX() - speed, this.getY());
		//this.getRegion().setRegionWidth(getRegion().getRegionWidth() - 50);

		
		body.setTransform(this.getX() + this.getOriginX() + c_width/2, this.getY() + this.getOriginY() + c_height/2, 0);
	}
	
	public void setWorldBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		
		
	}
	
	@Override
	public void translate(float x, float y) {
		super.translate(x, y);

	}
	
	@Override
	public void setX(float x){
		super.setX(x);
	}
	
	@Override
	public void setY(float y){
		super.setY(y);
	}
	
	public void playerHit(Kiku kiku) {
		kiku.stun(taps);
		kiku.freezeY();
		
		playerCaught = true;
		caughtKiku = kiku;
		
	}
	
	public void playerStopHit(Kiku kiku) {
		kiku.unfreezeY();
	}

	
	boolean kill = false;
	public void killNextRun() {
		kill = true;
	}
	
	
private void createBody(World physics_world) {
		
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.KinematicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(-5000, -5000);

		// Create our body in the world using our body definition
		body = physics_world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(c_width/2.0f, c_height/2.0f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit

		// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		rectangle.dispose();
		
		body.setUserData(this);
	}
	
	
	
}
