package com.omg.ssworld;

import java.awt.Color;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
import com.omg.drawing.JSAnimation;
import com.omg.sswindler.GameManager;

public class Monster extends JSActor {
	private int x;
	private int y;
	private int width;
	private int height;
	
	Body body;
	
	protected static int c_width = 200;
	protected static int c_height = 160;
	
	protected static int c_width_t = 200/2;
	protected static int c_height_t = 160/2;
	
	JSAnimation idleAnimation;
	
	int randomNum = 0;
	boolean hasBackedDown = false;

	
	public Monster()
	{
		//super(new TextureRegion(GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Enemy"), Texture.class),0,0,c_width,c_height));
	   	
		super(new TextureRegion(GameManager.getAssetsManager().getTexture("Enemy"),0,0,c_width_t,c_height));

		randomNum = (int) (Math.random() * 100);
		
		if(randomNum < 95) {
			shotLaser = true;
			hasBackedDown = true;
		}
		
		//Gdx.app.log("MONSTER", "ENEMY TEXTURE GET : " + GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Enemy"), Texture.class).getTextureObjectHandle() + " <<<<");
		//Gdx.app.log("MONSTER", "ENEMY TEXTURE GET : " + GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Kiku"), Texture.class).getTextureObjectHandle() + " <<<<");

		//idleAnimation = new JSAnimation("Idle", GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Enemy"), Texture.class), c_width, c_height, 10, 100);		
		idleAnimation = new JSAnimation("Idle", GameManager.getAssetsManager().getTexture("Enemy"), c_width_t, c_height_t, 10, 100);		
		setRegion(idleAnimation.getRegion());

		this.setScale(4);
		
		//super(new TextureRegion(new Texture(Gdx.files.internal("data/big_pixel_coin.png")),0,0,c_width,c_height));
		//super(new TextureRegion(GameManager.getAssetsManager().get("data/big_pixel_coin.png", Texture.class),0,0,c_width,c_height));

		addTag("Monster");
	}
	
	public Monster(TextureRegion t) {
		super(t);
		
		addTag("Monster");
		
	}
	
	public void addPhysics(World physics_world) {
		createBody(physics_world);
	}
	
	public void removePhysics() {
		body.getWorld().destroyBody(body);
	}
	
	double time = 0.0;
	boolean shotLaser = false;
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(this.getX() < x) {
			body.getWorld().destroyBody(body);
			this.remove();
			
		}
		if(this.getX() < x + 2500 && !shotLaser && !hasBackedDown) 
		{
			this.addAction(Actions.parallel(Actions.scaleBy(1.2f, 1.2f, 0.3f, Interpolation.bounceOut)));
			hasBackedDown = true;
		}
		
		if(this.getX() < x + 2200 && !shotLaser ) {
			shootLaser();
			this.addAction(Actions.moveBy(0f, -150, 0.35f,Interpolation.bounceOut));

		}
		
		TextureRegion region = idleAnimation.update(delta);
    	
    	if(region != null)
    		setRegion(region);
    	
    	time+=.04;
    	this.setY(this.getY() + (float)Math.sin(time)*1);
		
		body.setTransform(this.getX() + this.getOriginX() + c_width/2, this.getY() + this.getOriginY() + c_height/2, 0);
	}
	
	public void setWorldBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		
		
	}
	
	public void shootLaser() {
		
		Laser l = new Laser();
		
		
		if( ((JSActor)getParent() ).hasTag("WorldManager") ) {
			((WorldManager)getParent()).addLaser(l, this.getX(), this.getY(), this);
		}
		
		/*if( ((JSActor)getParent() ).hasTag("WorldManager") ) {
		
			l.setWorldBounds(((WorldManager)getParent()).getWorldX(), ((WorldManager)getParent()).getWorldY(), ((WorldManager)getParent()).getWorldWidth(), ((WorldManager)getParent()).getWorldHeight());
			l.setX(this.getX() - l.getWidth() + 10);
			l.addPhysics(((WorldManager)getParent()).physics_world);
			l.setY(this.getY());
			addActor(l);
		
		}*/

		shotLaser = true;
		
	}
	
	@Override
	public void translate(float x, float y) {
		super.translate(x, y);
		//body.setLinearVelocity(x, y);
		//body.setLinearVelocity(x, y);
	}
	
	@Override
	public void setX(float x){
		super.setX(x);
		//body.setTransform(x, this.getY(), 0);
	}
	
	@Override
	public void setY(float y){
		super.setY(y);
		//body.setTransform(this.getX(), y, 0);
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
