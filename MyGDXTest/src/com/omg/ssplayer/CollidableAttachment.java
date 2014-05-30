package com.omg.ssplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.omg.drawing.JSActor;
import com.omg.ssplayer.Jumpable.JumpableState;
import com.omg.sswindler.GameManager;

public class CollidableAttachment extends JSActor {
	Body body;

	
	public enum CollidableShape {
		circle,
		rectangle
	}
	
	private CollidableShape shape = CollidableShape.circle;
	
	float radius = 10.0f;
	float width = 10.0f;
	float height = 10.0f;
	
	public CollidableAttachment(String tag, CollidableShape shape) {
		//super(new TextureRegion(GameManager.getAssetsManager().getTexture("Le_Bubble"),0,0,0,0));
		super();
		this.shape = shape;
		addTag(tag);
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public void setDimensions(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	
	@Override
	 public void act (float delta) {
		super.act(delta);		
		body.setTransform(this.getParent().getX() + getX(), this.getParent().getY() + this.getY(), 0);
	 }
	
	public void addPhysics(World physics_world) {
		createBody(physics_world);
	}
	
	private void createBody(World physics_world) {
		
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(0,0);

		// Create our body in the world using our body definition
		body = physics_world.createBody(bodyDef);

		Shape s = null;
		
		
		switch(shape) {
			
		case circle:
			// Create a circle shape and set its radius to 6
			CircleShape circle = new CircleShape();
			circle.setRadius(radius);
			s = circle;
			break;
		case rectangle:
			// Create a circle shape and set its radius to 6
			PolygonShape rectangle = new PolygonShape();
			rectangle.setAsBox(width, height);
			s = rectangle;
			break;
		}

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = s;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit
		

		// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);
		
		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		s.dispose();
		
		
		body.setUserData(this.getParent());
	}
	
	
	
}
