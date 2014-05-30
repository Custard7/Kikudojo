package com.omg.ssworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
import com.omg.ssworld.Platform.PlatformType;

public class NanoKi extends JSActor {

	private int x;
	private int y;
	private int width;
	private int height;
	
	Body body;
	
	protected static int c_width = 50; //512
	protected static int c_height = 125; //128 1080
	
	protected static int c_width_t = 25; //25
	protected static int c_height_t = 50; //50
	
	public boolean isActive;
	
	public int value = 10;
	
	public int getTextureWidth() {
		return c_width;
	}
	
	public int getTextureHeight() {
		return c_height;
	}
	
	
	public enum NanoKiType {
		normal(new TextureRegion(GameManager.getAssetsManager().getTexture("NanoKi"),0,0,c_width_t,c_height_t));
		
		private final TextureRegion tex;
		NanoKiType(TextureRegion tex) { this.tex = tex; }
	    public TextureRegion getTextureRegion() { return tex; }
	}
	
	
	NanoKiType type = NanoKiType.normal;
	Action movingAction = Actions.forever(Actions.sequence(Actions.moveBy(0, -20, 2, Interpolation.sineOut), Actions.moveBy(0, 20, 2, Interpolation.sineOut)));
	
	public void addYMovement() {
		addAction(movingAction);
	}
	public void addFallMovement() {
		addAction((Actions.moveBy(0, -800, 3f)));
	}
	public void removeYMovement() {
		if(this.getActions().size > 0) {
			this.getActions().clear();
		}
	}
	
	public void setType(NanoKiType p) {
		this.type = p;
		this.setRegion(p.getTextureRegion());
		
		switch(getType()) {
		
			case normal:
				addYMovement();
				break;
			default:
				break;
		
		
		}
		
	}
	
	public NanoKiType getType() {
		return type;
	}
	
	
	
	public NanoKi()
	{
		super();
		setType(NanoKiType.normal);
		
		
		setScale(2);
		this.setOriginY(-25);

		addTag("NanoKi");
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
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		
		if(this.getX() < x || kill) {
			kill();
		}
		
		if(body != null) {
			body.setTransform(this.getX() + this.getOriginX() + c_width/2, this.getY() + this.getOriginY() + c_height/2, 0);
		}
		
		switch(getType()) {
		
		case normal:
			

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
	}
	
	@Override
	public void setX(float x){
		super.setX(x);
	}
	
	@Override
	public void setY(float y){
		super.setY(y);
	}
	
	World physics;
	
	public void playerHit(Jumpable j) {
		
		//if(j.isDead())
			//return;
		
		switch(getType()) {
		
			case normal:
				if(j.hasTag("Kiku")) {
					((Kiku)j).collectNanoKi(this.value);
				}
				killNextRun();
				break;
			default:
				break;
		
		
		}
		
		
	}
	
	public void playerStopHit(Jumpable j) {
		switch(getType()) {
		
		case normal:
			break;
		default:
			break;
	
	
	}
	}
	
	boolean kill = false;
	public void killNextRun() {
		kill = true;
	}
	
	public void kill() {

			this.setVisible(false);
			this.isActive = false;
			this.removePhysics();
			this.setY(-10000);
	
	}
	
	public void activate() {
		this.isActive = true;
		this.setVisible(true);
		this.kill = false;
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
		rectangle.setAsBox(c_width/2.0f, c_height/1.5f); //8

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
