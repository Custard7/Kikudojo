package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.omg.drawing.JSActor;
import com.omg.ssplayer.Jumpable;
import com.omg.ssplayer.Kiku;
import com.omg.sswindler.GameManager;

public class Platform extends JSActor {

	private int x;
	private int y;
	private int width;
	private int height;
	
	Body body;
	
	protected static int c_width = 512; //512
	protected static int c_height = 1080; //128 1080
	
	protected static int c_width_t = 128;
	protected static int c_height_t = 270;
	
	public boolean isActive;
	
	public int getTextureWidth() {
		return c_width;
	}
	
	public int getTextureHeight() {
		return c_height;
	}
	
	
	public enum PlatformType {
		normal(new TextureRegion(GameManager.getAssetsManager().getTexture("Platform_Le"),0,0,c_width_t,c_height_t)),
		starting(new TextureRegion(GameManager.getAssetsManager().getTexture("Platform_Starting"),0,0,c_width_t,c_height_t)),
		moving(new TextureRegion(GameManager.getAssetsManager().getTexture("Platform_Moving"),0,0,c_width_t,c_height_t)),
		spikes(new TextureRegion(GameManager.getAssetsManager().getTexture("Platform_Spikes"),0,0,c_width_t,c_height_t)),
		falling(new TextureRegion(GameManager.getAssetsManager().getTexture("Platform_Falling"),0,0,c_width_t,c_height_t));
		
		private final TextureRegion tex;
		PlatformType(TextureRegion tex) { this.tex = tex; }
	    public TextureRegion getTextureRegion() { return tex; }
	}
	
	
	PlatformType type = PlatformType.normal;
	Action movingAction = Actions.forever(Actions.sequence(Actions.moveBy(0, -500, 2, Interpolation.sineOut), Actions.moveBy(0, 500, 2, Interpolation.sineOut)));
	
	public void addYMovement() {
		addAction(movingAction);
	}
	public void addFallMovement() {
		addAction((Actions.moveBy(0, -800, 3f)));
	}
	public void removeYMovement() {
		if(this.getActions().size > 0) {
			//removeAction(movingAction);
			this.getActions().clear();
		}
	}
	
	public void setType(PlatformType p) {
		this.type = p;
		this.setRegion(p.getTextureRegion());
		
		switch(getType()) {
		
			case normal:
				removeYMovement();
				break;
			case starting:
				removeYMovement();
				
				break;
			case moving:
				addYMovement();
				
				break;
			case spikes:
				removeYMovement();

				break;
			case falling:
				removeYMovement();
				
				addFallMovement();
				Gdx.input.vibrate(100);
				
				break;
			default:
				break;
		
		
		}
		
	}
	
	public PlatformType getType() {
		return type;
	}
	
	
	
	public Platform()
	{

		//super(new TextureRegion(new Texture(Gdx.files.internal("data/NEW_TILE_GRAY.png")),0,0,c_width,c_height));
		//super(new TextureRegion(GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Platform_Generic"), Texture.class),0,0,c_width,c_height));
		//super(new TextureRegion(GameManager.getAssetsManager().getTexture("Platform_Le"),0,0,c_width,c_height));
		super();
		setType(PlatformType.normal);
		
		
		setScale(4);
		
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
	
	
	
	public boolean isLeftOf(float screenPercent) {
		//Gdx.app.log("Platform",":" + ((float)(this.getX() - x)/width));
		return ((float)(this.getX() - x)/width) < screenPercent;
		
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
		
		switch(getType()) {
		
		case normal:
			
			break;
		case moving:
			
			
			
			break;
		case spikes:
			
			break;
		default:
			break;
		
		
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
	
	public void playerHit(Jumpable j) {
		
		//if(j.isDead())
			//return;
		
		switch(getType()) {
		
			case normal:
				int ran = (int) (Math.random() * 100);
				if(ran > 95) {
					setType(PlatformType.falling);
				}
				break;
			case moving:
				
				removeYMovement();
				
				break;
			case spikes:
				if(j.hasTag("Kiku")) {
					((Kiku)j).kill("spikes");
				}
				break;
			case falling:
				
				break;
			default:
				break;
		
		
		}
		
		
	}
	
	public void playerStopHit(Jumpable j) {
		switch(getType()) {
		
		case normal:
			
			break;
		case moving:
			
			addYMovement();

			
			break;
		case spikes:
			
			break;
		default:
			break;
	
	
	}
	}
	
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
		rectangle.setAsBox(c_width/2.0f, c_height/70.0f); //8

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
