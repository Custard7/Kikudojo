package com.omg.ssplayer.mechanics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSAnimation;
import com.omg.ssplayer.Kiku;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Platform.PlatformType;

public class BubblePackage extends JSActor {

	private int x;
	private int y;
	private int width;
	private int height;
	
	Body body;
	
	protected static int c_width = 150;
	protected static int c_height = 150;
	
	JSActor packageImage;
	
	public enum PackageType {
		nothing(new TextureRegion(GameManager.getAssetsManager().getTexture("Le_Bubble"),0,0,0,0)),
		frog(new TextureRegion(GameManager.getAssetsManager().getTexture("Animal_Frog"),0,0,50,50)),
		kiDude(new TextureRegion(GameManager.getAssetsManager().getTexture("Animal_KiDude"),0,0,50,50)),
		bird(new TextureRegion(GameManager.getAssetsManager().getTexture("Animal_Bird"),0,0,50,50));

		
		private final TextureRegion tex;
		PackageType(TextureRegion tex) { this.tex = tex; }
	    public TextureRegion getTextureRegion() { return tex; }
		
		
	}
	
	PackageType type = PackageType.nothing;
	
	public void setType(PackageType p) {
		this.type = p;
		packageImage.setRegion(p.getTextureRegion());
		
		switch(getType()) {
		
			case nothing:
				break;
			case frog:
				
				break;

			default:
				break;
		
		
		}
		
	}
	
	public PackageType getType() {
		return type;
	}
	
	
	public BubblePackage()
	{	   	
		super(new TextureRegion(GameManager.getAssetsManager().getTexture("Le_Bubble"),0,0,c_width,c_height));

		packageImage = new JSActor();
		packageImage.setPosition(25, 25);
		packageImage.setScale(2);
		setType(PackageType.nothing);
		addActor(packageImage);

		addTag("Bubble");
	}
	

	
	public void addPhysics(World physics_world) {
		createBody(physics_world);
	}
	
	public void removePhysics() {
		body.getWorld().destroyBody(body);
	}
	
	double time = 0;
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(this.getX() < x || kill) {
			body.getWorld().destroyBody(body);
			this.remove();
			
		}
		

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
		kiku.collectBubble(this.getType());
		pop();
	}
	
	public void pop() {
		killNextRun();
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
