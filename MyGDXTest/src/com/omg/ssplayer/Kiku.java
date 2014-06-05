package com.omg.ssplayer;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSAnimation;
import com.omg.screens.GameScreen;
import com.omg.sfx.SoundManager;
import com.omg.ssplayer.CollidableAttachment.CollidableShape;
import com.omg.ssplayer.mechanics.Animal;
import com.omg.ssplayer.mechanics.Animal.AnimalType;
import com.omg.ssplayer.mechanics.BubblePackage.PackageType;
import com.omg.ssplayer.mechanics.KiOrb;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.Monster;
import com.testflightapp.lib.TestFlight;

public class Kiku extends Jumpable {

	JSAnimation runningAnimation;
	JSAnimation jumpingAnimation;
	JSAnimation dieAnimation;
	
	JSAnimation currentAnimation;
	
	JSActor playerAura;
	TextureRegion plus1Texture;

	
	public Color currentColor;
	
	CollidableAttachment head;
	
	public enum KikuAnim {
		
		running,
		jumping,
		die
	}
	
	
	
	public Kiku(){
		//runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Kiku"), Texture.class), 150, 208, 8, 50);
		//jumpingAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().get(GameManager.getAssetsManager().getPath("Kiku_Jump"), Texture.class), 200, 200, 10, 30);
		
		runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().getTexture("Kiku"), 150, 208, 8, 50);
		jumpingAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().getTexture("Kiku_Jump"), 200, 200, 10, 30);
		dieAnimation = new JSAnimation("Die", GameManager.getAssetsManager().getTexture("Kiku_Die"), 141, 271, 1, 1000);
		
		
		//this.setColor(getColor().r + 0.9f, getColor().g - 0.9f, getColor().b - 0.9f, getColor().a);

		
		//runningAnimation = new JSAnimation("Running", GameManager.getAssetsManager().get("data/player/actual_sprite_sheet.png", Texture.class), 150, 208, 8, 50);
		//jumpingAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().get("data/player/Jump_Sprite_Sheet.png", Texture.class), 200, 200, 10, 30);
	
		jumpingAnimation.setRepeat(false);
		
		currentAnimation = runningAnimation;
		
		//setRegion(new TextureRegion(new Texture(Gdx.files.internal("data/bum.png")),0,0,256/2,256));

		setRegion(runningAnimation.getRegion());
		
		
		animal = new Animal();
		animal.setType(AnimalType.nothing);
		//this.getParent().addActor(animal);

		
		currentColor = Color.CYAN;
		
		playerAura = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("PlayerAura"),0,0,200,200));
		playerAura.setScale(2);
		playerAura.setVisible(false);
		playerAura.setPosition(-100, -50);
		playerAura.addAction(Actions.forever(Actions.sequence( Actions.alpha(0, 1), Actions.alpha(1.0f, .5f))));
		//playerAura.setColor(playerAura.getColor().r + currentColor.r, playerAura.getColor().g + currentColor.g, playerAura.getColor().b + currentColor.b, playerAura.getColor().a);
		playerAura.setColor(currentColor);
		this.addActor(playerAura);
		this.setChildDrawDirection(ChildrenDrawDirection.inFront);
		
		plus1Texture = new TextureRegion(GameManager.getAssetsManager().getTexture("HUD_Plus1"),0,0,90,150);

		
		head = new CollidableAttachment("Kiku", CollidableShape.circle);
		head.setRadius(30.0f);
		head.setPosition(100, 100);
		this.addActor(head);
		
		addTag("Kiku");
		addTag("STATIC");
	}
	

	
	@Override
	public void addPhysics(World physics_world) {
		super.addPhysics(physics_world);
		head.addPhysics(physics_world);
	}
	
	
	boolean spacePressed = false;
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		//setZIndex(1000);
		
		
		
    	TextureRegion region = currentAnimation.update(delta);
    	
    	if(region != null)
    		setRegion(region);
    	
    	if(isDead())
			return;

 
    	if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.justTouched() )
    	{
    		if(!spacePressed) {
    			if(gameScreen != null) {
    				gameScreen.playerJumped();
    			}
	    		jump(2000.0f);
	    		setAnimation(KikuAnim.jumping);
	    		
	    		if(isStunned)
	    		{
	    			stunTaps++;
	    			this.addAction(Actions.sequence(Actions.moveBy(-30f, 0, .2f),Actions.moveBy(30f, 0, .2f)));
	    			Gdx.input.vibrate(100);
	    		}
	    		
    		}
    		spacePressed = true;
    	} else {
    		spacePressed = false;
    	}
    	

    	
    	if(stunTaps >= tapsToRelease) {
    		unfreezeY();
    	}
    	
    	
    	
    	

        
		
	}
	
	int footstepTimer = 0;
	int footstepInterval = 250;
	
	public void updateSound(SoundManager soundManager, int speed) {
		
		if(isOnGround()) {
			footstepTimer++;
			
			int footstepIntervalDynamic = footstepInterval/speed;
			
			if(footstepTimer > footstepIntervalDynamic) {
				soundManager.play("Footsteps");
				footstepTimer = 0;
			}
		} else {
			footstepTimer = 1000;
		}
		
	}
	
	@Override
	public void hitGround() {
		super.hitGround();
		
		setAnimation(KikuAnim.running);
		
	}
	
	public void kill(String data) {
		
		if(data.equalsIgnoreCase("spikes") && imperviousToSpikes)
			return;
		
		if(data.equalsIgnoreCase("water") && imperviousToWater)
			return;
			
			
		setAnimation(KikuAnim.die);
		
		if(!isDead()) {
			Gdx.input.vibrate(new long[] {0,300,50,200,45,100,40,100,35,100,50,300}, -1);
			gameScreen.getSoundManager().play("Player Die");
		}
		
		setDead(true);
		
		
		
	}
	
	
	int stunTaps = 0;
	int tapsToRelease = 0;
	
	public void stun(int taps) {
		if(!isStunned) {
			gameScreen.getSoundManager().play("Laser");
		}
		this.isStunned = true;
		stunTaps = 0;
		tapsToRelease = taps;
	}
	
	public void unstun() {
		this.isStunned = false;
	}
	
	public void unfreezeY() {
		this.playerYFrozen = false;
		this.setColor(Color.WHITE);

	}
	
	public void freezeY() {
		this.playerYFrozen = true;
		this.setColor(Color.GREEN);

	}
	
	public void setAnimation(KikuAnim anim) {
		
		switch(anim) {
		
		case running:
			currentAnimation = runningAnimation;
			setScale(1.0f);
			break;
		case jumping:
			if(!currentAnimation.getName().equalsIgnoreCase(jumpingAnimation.getName()))
				jumpingAnimation.reset();

			currentAnimation = jumpingAnimation;
			setScale(1.2f);
			break;	
			
		case die:
			currentAnimation = dieAnimation;
			setScale(1.0f);
			if(!isDead())
				this.addAction(Actions.sequence(Actions.moveBy(-25, 200, 1, Interpolation.exp10Out), Actions.moveBy(-25, -1500, 3.5f, Interpolation.bounceOut)));
			break;
		
		}
		
			
		
	}
	
	

	
	Animal animal;
	boolean firstAnimal = true;
	
	public void collectBubble(PackageType t) {
		
		Gdx.app.log("KIKU", "Bubble Collected! Type: " + t.toString());
		
		gameScreen.getSoundManager().play("Bubble Pop");
		
		switch(t) {
		
		case nothing:
			break;
		case frog:
			

			if(animal == null) 
				animal = new Animal();
			
			animal.setType(AnimalType.frog);
			animal.setX(this.getX()- 100);
			animal.setTarget(this);								
			gameScreen.getSoundManager().play("Ribbit");
			
			break;
		case bird:
			
			if(animal == null) 
				animal = new Animal();
			
			animal.setType(AnimalType.bird);
			animal.setX(this.getX()- 100);
			animal.setTarget(this);
			
			break;
		case kiDude:
			
			if(animal == null) 
				animal = new Animal();
			
			animal.setType(AnimalType.kiDude);
			animal.setX(this.getX()- 100);
			animal.setTarget(this);
			
			break;
		
		}
		
		if(firstAnimal)
		{
			this.getParent().addActor(animal);
			firstAnimal = false;
		}
		
		setPowers(animal.getType());
		
	}
	
	
	
	public void setPowers(AnimalType t) {
		
		maxJumps = 1;
		lessKi = 0;
		recovery = false;
		
		switch(t) {
		
		case frog:
			maxJumps = 2;
			break;
		case bird:
			recovery = true;
			break;
		case kiDude:
			lessKi = 1;
			break;
		
		}
		
		
	}
	
	int lessKi = 0;
	boolean recovery = false;
	boolean imperviousToSpikes = false;
	public boolean imperviousToWater = false;
	
	
	
	int kiTotal = 0;
	int auraTotal = 0;
	JSActor lastTarget;
	
	
	public void collectKi() {
		if(lastTarget == null)
			lastTarget = this;
		
		KiOrb orb = new KiOrb();
		orb.setKiNumber(kiTotal);
		orb.setX(this.getX()- 100 - 100 * kiTotal);
		orb.setTarget(lastTarget);
		this.getParent().addActor(orb);
		
		orb.setColor(currentColor);
		//orb.setColor(Color.MAGENTA);
		
		lastTarget = orb;
		
		kiTotal++;
		plus1();
		
		if(kiTotal > 5 - lessKi) {
			collectAura();
			removeAllKi(true);
		}
	}
	

	
	public void removeKi() {
		for(Actor a : this.getParent().getChildren()) {
			if(a instanceof KiOrb) {
				//Gdx.app.log("MONSTER", "Removing KI! " + kiTotal + " kiNumber:" + ((KiOrb)a).kiNumber);
				if(((KiOrb)a).kiNumber == kiTotal-1){
						lastTarget = ((KiOrb)a).getTarget();
						((KiOrb)a).remove();
						kiTotal--;
						return;
				}
			}
				
		}
			
	}
	
	public void removeAllKi(boolean good) {
		for(int x = kiTotal-1; x >= 0; x--) {
			removeKi();
		}
	}
	
	public void collectAura() {
		auraTotal++;
		updateAura();
		
		gameScreen.getSoundManager().play("Aura Up");
		
	}
	
	public void updateAura() {
		if(auraTotal > 0) {
			playerAura.setVisible(true);
			
		} else {
			playerAura.setVisible(false);
		}
		
		if(auraTotal == 0) {
			currentColor = Color.CYAN;
		} else if(auraTotal == 1) {
			currentColor = Color.ORANGE;
		} else if (auraTotal == 2) {
			currentColor = Color.GREEN;
		} else {
			currentColor = Color.RED;
		}
		
		playerAura.setColor(currentColor);

	}
	
	public void removeAura() {
		auraTotal--;
		updateAura();
	}
	
	
	int nanoKiCollected = 0;
	
	public void collectNanoKi(int value) {
		nanoKiCollected+=value;
		gameScreen.getSoundManager().play("Nanoki");
	}
	
	
	public int getNanoKiCollected() {
		return nanoKiCollected;
	}
	
	public void plus1() {
		
		JSActor plus1Particle = new JSActor(plus1Texture);
		plus1Particle.setPosition(0, 50);
		plus1Particle.setScale(1.8f);
		plus1Particle.addAction(Actions.sequence(Actions.moveBy(0, 200, .5f, Interpolation.exp10Out), Actions.parallel(Actions.moveBy(0, -200, 2, Interpolation.exp10In), Actions.alpha(0, 3) ), Actions.removeActor()));
		this.addActor(plus1Particle);
		
	}
	
	
	
	
}
