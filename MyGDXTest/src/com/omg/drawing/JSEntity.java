package com.omg.drawing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * AREntity is the foundation of the OpenGL objects and rendering.
 * Anything place on screen is most likely either an AREntity or inherits from it.
 * 
 * <p>
 * AREntities can be parented to eachother and hold children. Each parent will draw all of its children.
 * </p>
 * 
 * <p>
 * AREntites can also hold tags, distinguishing groups of Entities from eachother.
 * </p>
 * @author Jackson C. Smith
 *
 */
public class JSEntity extends Sprite {

	/**
	 * The name of the Entity
	 */
	public String 			name;
	
	/**
	 * A list of tags attached to this entity.
	 */
	public List<String> 	tags;
	
	/**
	 * Whether or not the entity would like to be removed or 'killed.'
	 * If so, the entity will be removed and no longer called on.
	 */
	protected boolean 		wantsKill = false;
	
	/**
	 * The Children Draw Direction enumeration.
	 * Decides which direction this entity will draw its children,
	 * either in front of it or behind it.
	 * @author Jackson C. Smith
	 *
	 */
	public enum ChildrenDrawDirection {
		/**
		 * The entity will draw itself and then draw its children in front of it.
		 */
		inFront,
		/**
		 * The entity will draw its children and then itself on top of them.
		 */
		inBack
	}
	
	/**
	 * The draw direction of this entity for its children.
	 */
	private ChildrenDrawDirection childrenDrawDirection = ChildrenDrawDirection.inFront;
	
	/**
	 * Gets the children draw direction for this entity.
	 * @return the Children Draw Direction of this entity.
	 * @see com.JSEntity.nodeaugmentation.AREntity.ChildrenDrawDirection ChildrenDrawDirection Enumeration
	 */
	public ChildrenDrawDirection getDrawDirection() {
		return childrenDrawDirection;
	}
	
	/**
	 * Sets the children draw direction for this entity.
	 * @param d is the new children draw direction
	 * @see com.JSEntity.nodeaugmentation.AREntity.ChildrenDrawDirection ChildrenDrawDirection Enumeration
	 */
	public void setDrawDirection(ChildrenDrawDirection d) {
		this.childrenDrawDirection = d;
	}
	
	/**
	 * The Children of this AREntity.
	 */
	protected CopyOnWriteArrayList<JSEntity> children;
	
	/**
	 * The parent of this AREntity. Null if it has no parent and is a root node.
	 */
	protected JSEntity Parent;
	
	/**
	 * Whether or not this AREntity is visible and should be drawn.
	 */
	private boolean isVisible = true;
	
	/**
	 * Returns whether or not this AREntity is visible and should be drawn.
	 * @return True if the Entity is visible. False if not.
	 */
	public boolean IsVisible() {
		return isVisible;
	}
	/**
	 * Sets whether or not this AREntity is visible and should be drawn.
	 * @param visible is the visibility of this AREntity.
	 */
	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}
	
	/**
	 * Whether or not this AREntity will draw its children.
	 */
	private boolean areChildrenVisible = true;
	
	/**
	 * Gets whether or not this AREntity will draw its children.
	 * @return True if its children are being drawn, false if not.
	 */
	public boolean AreChildrenVisible() {
		return areChildrenVisible;
	}
	/**
	 * Sets whether or not this AREntity will draw its children.
	 * @param visible
	 */
	public void setChildrenVisible(boolean visible){
		areChildrenVisible = visible;
	}
	
	
	private float x;
	private float y;
	
	
	public Vector2 getPosition()
	{
		if(getParent() != null)
			return new Vector2(getLocalX() + getParent().getX(), getLocalY() + getParent().getY());
		return getLocalPosition();
	}
	
	
	@Override
	public float getX() {
		if(getParent() != null)
			return getLocalX() + getParent().getX();
		return getLocalX();
	}
	
	public float getLocalX() {
		return x;
	}
	
	@Override
	public float getY() {
		if(getParent() != null)
			return getLocalY() + getParent().getY();
		return getLocalY();		
	}
	
	public float getLocalY() {
		return y;
	}
	
	
	@Override
	public void setX(float x) {
		this.x = x;
	}
	
	@Override
	public void setY(float y) {
		this.y = y;
	}
	

	
	@Override
	public void translateX(float xAmount) {
		setX(getLocalX() + xAmount);
	}
	
	@Override
	public void translateY(float yAmount) {
		setY(getLocalY() + yAmount);
	}
	
	
	@Override
	public void translate(float xAmount, float yAmount) {
		translateX(xAmount);
		translateY(yAmount);
	}
	
	
	public void moveForward(float amount) {
	    translateX((float)(-amount * Math.sin(Math.toRadians(getRotation()))));
	    translateY((float)(amount * Math.cos(Math.toRadians(getRotation()))));
	}
	
	
	/**
	 * Gets the local position of this JSEntity relative to its parent.
	 * @return the OpenGL x,y relative location of this JSEntity. If this is a root node,
	 * the position will be its absolute position.
	 */
	public Vector2 getLocalPosition() {
		return new Vector2(getLocalX(), getLocalY());
	}
	/**
	 * Sets the relative location of this JSEntity.
	 * @param pos in x,y GLCoordinates.
	 */
	public void setPosition(Vector2 pos)
	{
		setX(pos.x);
		setY(pos.y);
	}
	

	
	@Override
	/** Returns the packed vertices, colors, and texture coordinates for this sprite. */
	public float[] getVertices () {
		super.translateX(getX() - super.getX());
		super.translateY(getY() - super.getY());
		
		float[] returnVerts = super.getVertices();
		return returnVerts;
	}

	
	
	/**
	 * Gets whether or not this entity would like to be removed.
	 * @return True if the entity would like to be removed or 'killed.' False if not.
	 */
	public boolean doesWantKill() {
		return wantsKill;
	}
	


	
	/**
	 * ARMovement properties holds information about movement and handles the updating
	 * of velocities and accelerations. 
	 * 
	 * <p>
	 * <b>MUST</b> call the update method if anything is to work properly.
	 * </p>
	 * 
	 * <p>
	 * Useful reusable class to make smooth movements and accelerations.
	 * </p>
	 * @author Jackson C. Smith
	 *
	 */
	public static class JSMovementProperties {
		
		/**
		 * Acceleration in the X,Y directions
		 */
		JSVector2 acceleration = new JSVector2(0,0);
		/**
		 * Velocity in the X,Y directions.
		 */
		JSVector2 velocity = new JSVector2(0,0);
		/**
		 * Friction applied in the X,Y directions.
		 */
		JSVector2 friction = new JSVector2(0.95f, 0.95f);
		/**
		 * Minimum (X) and Maximum (Y) values that the acceleration can reach.
		 */
		JSVector2 accelerationBounds = new JSVector2(-10,10);
		/**
		 * Minimum (X) and Maximum (Y) values that the velocity can reach.
		 */
		JSVector2 velocityBounds = new JSVector2(-30,30);
		
		/**
		 * Constructor for ARMovementProperties.
		 */
		public JSMovementProperties() {
			
			
		}
		
		/**
		 * Accelerates the object in a given direction and magnitude.
		 * @param magnitude is the acceleration direction and magnitude.
		 */
		public void accelerate(JSVector2 magnitude) {
			acceleration = acceleration.add(magnitude);
		}
		/**
		 * Updates the movement properties. Accelerating the velocity and adding friction.
		 */
		public void update() {
			acceleration = ARVector2.Clamp(acceleration, accelerationBounds);
			velocity = velocity.add(acceleration);
			velocity = velocity.mul(friction);
			
			velocity = ARVector2.Clamp(velocity, velocityBounds);
			
			acceleration = new JSVector2(0,0);
		}
		
		/**
		 * Gets the velocity.
		 * @return X,Y velocity in AR Units
		 */
		public JSVector2 getVelocity() {
			return velocity;
		}

		
		/**
		 * Stops all movement. Velocity and acceleration are zeroed.
		 */
		public void stop() {
			velocity = new JSVector2(0,0);
			acceleration = new JSVector2(0,0);
		}
		
		/**
		 * Sets the friction in the X and Y directions.
		 * @param x direction friction. 0 < x < 1 makes friction. x > 1 makes rapid acceleration.
		 * @param y direction friction. 0 < y < 1 makes friction. y > 1 makes rapid acceleration.
		 */
		public void setFriction(float x, float y) {
			friction.x = x;
			friction.y = y;
		}
		/**
		 * Sets the friction in both the X and Y directions based on f.
		 * @param f direction friction. 0 < f < 1 makes friction. f > 1 makes rapid acceleration.
		 */
		public void setFriction(float f) {
			setFriction(f,f);
		}
		/**
		 * Sets the maximum values for the velocity to be.
		 * @param min is the minimum value the velocity can be.
		 * @param max is the maximum value the velocity can be.
		 */
		public void setMaxVelocity(float min, float max) {
			velocityBounds = new JSVector2(min, max);
		}
		
		public void setMaxAcceleration(float min, float max) {
			accelerationBounds = new JSVector2(min, max);
		}
		
	}
	
	/**
	 * The ARVector2 class holds both an x and y coordinate.
	 * @author Jackson C. Smith
	 *
	 */
	public static class ARVector2  {
		

		
		/**
		 * Clamps a value between a minimum and a maximum. 
		 * @param value is the value to clamp
		 * @param min is the minimum the value can be
		 * @param max is the maximum the value can be
		 * @return If the value is below the minimum, the minimum is returned. 
		 * If the value is above the maximum, the maximum is returned.
		 * Otherwise, the value is returned.
		 */
		public static float Clamp(float value, float min, float max) {
			float returnValue = value;
			if(value < min)
				returnValue = min;
			else if(value > max)
				returnValue = max;
			return returnValue;
		}
		
		/**
		 * Clamps a vector2 between a minimum and maximum bounds.
		 * @param value is the value to clamp
		 * @param bounds is the minimum (X) the value can be an the Maximum (Y) it can be.
		 * @return If the value is below the minimum, the minimum is returned. 
		 * If the value is above the maximum, the maximum is returned.
		 * Otherwise, the value is returned.
		 */
		public static JSVector2 Clamp(JSVector2 value, JSVector2 bounds) {
			JSVector2 returnVector = value;
			
			returnVector.x = Clamp(returnVector.x, bounds.x, bounds.y);
			returnVector.y = Clamp(returnVector.y, bounds.x, bounds.y);
			return returnVector;
			
		}
		
		
	}
	
	
	public static class JSVector2 {
		
		public float x;
		public float y;
		
		
		public JSVector2(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public JSVector2 add(JSVector2 other) {
			return new JSVector2(this.x + other.x, this.y + other.y);
		}
		
		public JSVector2 mul(JSVector2 other) {
			return new JSVector2(this.x * other.x, this.y * other.y);
		}
		
		public JSVector2 add(float mag) {
			return new JSVector2(this.x + mag, this.y + mag);
		}
		
	
		
		
	}
	
	/**
	 * The constructor for the AREntity.
	 */
	public JSEntity() {
		super();
		init();
	}
	
	public JSEntity(Texture t) {
		super(t);
		init();
	}
	
	/**
	 * The constructor for the AREntity
	 * @param name to give the entity.
	 */
	public JSEntity(String name) {
		super();
		this.name = name;
		init();
	}
	
	/**
	 * Initialization method for the Entity.
	 * Zeroes out the position.
	 * Eliminates all children, reinitializes them.
	 * Elimates all tags, reinitializes them.
	 */
	protected void init() {
		
		x = 0;
		y = 0;
		children = new CopyOnWriteArrayList<JSEntity>();
		tags = new ArrayList<String>();
	}
	
	
	/**
	 * Sets the parent of this entity to the given entity.
	 * @param parent entity to make this a child of.
	 */
	public void setParent(JSEntity parent) {
		this.Parent = parent;
		if(!parent.hasChild(this))
			parent.addChild(this);
	}
	/**
	 * Gets the current parent of this entity. Null if it is a root node.
	 */
	public JSEntity getParent() {
		return this.Parent;
	}
	
	/**
	 * Returns whether or not this AREntity has a given child.
	 * @param aChild to check if this is a parent of.
	 * @return True if the child is a child of this, False if not.
	 */
	public boolean hasChild(JSEntity aChild) {
		for(JSEntity child : getChildren()) {
			if(aChild.toString().equalsIgnoreCase(child.toString()))
				return true;
		}
		return false;
	}
	
	/**
	 * Draws all children of this AREntity
	 * @param gl is the OpenGL context
	 * @param context is th application context
	 */
	protected void drawChildren(Batch batch) {
		
		List<JSEntity> toKill = new ArrayList<JSEntity>();
		for(JSEntity child : children){
			if(AreChildrenVisible())
				child.draw(batch);
			
			if(child.doesWantKill())
				toKill.add(child);
		}
		
		for(JSEntity child : toKill){
			children.remove(child);
		}
	}
	
	/**
	 * Adds a child to this AREntity.
	 * @param child to add.
	 */
	public void addChild(JSEntity child) {
		children.add(child);
		child.setParent(this);
	}
	
	/**
	 * Gets all children of this AREntity.
	 * @return all children of this AREntity.
	 */
	public List<JSEntity> getChildren()
	{
		return children;
	}
	
	/**
	 * Draws this AREntity. Also will draw all children.
	 * @param gl is the OpenGL context.
	 * @param context is the application context.
	 */
	@Override
	public void draw(Batch batch) {
		
		switch(getDrawDirection()) {
		
		case inFront:
			
			if(IsVisible()) {
				super.draw(batch);
			}
			
			drawChildren(batch);
			break;
		case inBack:
			//If drawing children in back, draw children then texture.
			drawChildren(batch);
			
			if(IsVisible()) {
				super.draw(batch);
			}
			
			break;
	}
	}
	

	
	/**
	 * Adds a tag to this AREntity.
	 * @param tag to add.
	 */
	public void addTag(String tag) {
		
		tags.add(tag);
		
	}
	
	/**
	 * Returns whether this entity has a given tag.
	 * @param tag to check.
	 * @return True if it does have the tag, False if not.
	 */
	public boolean hasTag(String tag) {
		for (String t : tags) {
			if(tag.equals(t))
				return true;
		}
		return false;
	}
	
	/**
	 * Removes a given tag from this AREntity.
	 * @param tag to remove.
	 */
	public void removeTag(String tag) {
		Iterator<String> it = tags.iterator();
		
		while(it.hasNext()) {
			String t = it.next();
			if(t.equals(tag)) {
				it.remove();
				//TODO: FINISH REMOVE TAG FUNCTION
			}	
		}

	}
	
	/**
	 * Gets all tags associated with this AREntity.
	 * @return all tags associated with this AREntity.
	 */
	public List<String> getTags() {
		return tags;
	}
	
	
	
	/**
	 * Kills this AREntity. Flags it for removal.
	 */
	public void kill() {
		wantsKill = true;
	}
}
