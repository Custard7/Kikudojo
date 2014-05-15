package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.omg.drawing.JSActor;
import com.omg.sswindler.GameManager;

public class Platform extends JSActor {

	private int x;
	private int y;
	private int width;
	private int height;
	
	Body body;
	
	protected static int c_width = 512;
	protected static int c_height = 128;
	
	
	public boolean isActive;
	
	public int getTextureWidth() {
		return c_width;
	}
	
	public int getTextureHeight() {
		return c_height;
	}
	
	public Platform()
	{

		//super(new TextureRegion(new Texture(Gdx.files.internal("data/NEW_TILE_GRAY.png")),0,0,c_width,c_height));
		//super(new TextureRegion(GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Platform_Generic"), Texture.class),0,0,c_width,c_height));
		super(new TextureRegion(GameManager.getAssetsManager().getTexture("Platform_Generic"),0,0,c_width,c_height));


		
		//super(new TextureRegion(GameManager.getAssetsManager().get("data/2_Tile.png", Texture.class),0,0,c_width,c_height));
		//super(new TextureRegion(new Texture(Gdx.files.internal(GameManager.getAssetsManager().getPath("Platform_Generic"))),0,0,c_width,c_height));

		addTag("Platform");
		isActive = true;
	}
	
	public Platform(TextureRegion t) {
		super(t);
		
		addTag("Platform");
		isActive = true;
		
	}
	
	public void addPhysics(World physics_world) {
		createBody(physics_world);
	}
	
	public void removePhysics() {
		if(physics != null) {
			if(body != null) {
				physics.destroyBody(body);
			}
			body = null;
			//body = null;
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(this.getX() < x) {
			//body.getWorld().destroyBody(body);
			//this.remove();
			this.isActive = false;
			this.removePhysics();
			
		}
		
		if(body != null) {
			//body.setTransform(this.getX() + this.getOriginX() + c_width/2, this.getY() + this.getOriginY() + c_height, 0);
			body.setTransform(this.getX() + this.getOriginX() + c_width/2, this.getY() + this.getOriginY() + c_height, 0);

		}
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
	
	World physics;
	
private void createBody(World physics_world) {
		
		this.physics = physics_world;
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.KinematicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(-5000, -5000);
		//bodyDef.position.set(0, 0);

		// Create our body in the world using our body definition
		body = physics_world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(c_width/2.0f, c_height/8);

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
