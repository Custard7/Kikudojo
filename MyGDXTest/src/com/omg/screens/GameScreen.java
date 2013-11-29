package com.omg.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSEntity;
import com.omg.drawing.JSEntity.JSVector2;
import com.omg.sfx.MusicManager;
import com.omg.sfx.MusicManager.LucidMusic;
import com.omg.sfx.SoundManager;
import com.omg.sfx.SoundManager.LucidSound;
import com.omg.ssplayer.Enemy;
import com.omg.ssplayer.Kiku;
import com.omg.ssplayer.Player;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.CollisionHandler;
import com.omg.ssworld.Platform;
import com.omg.ssworld.StarryBackground;
import com.omg.ssworld.WorldManager;

public class GameScreen implements Screen {

	 GameManager gameManager;
	 
	 
	/*private OrthographicCamera camera;
	private SpriteBatch batch;
	
	
	private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 320;
    private static final float ASPECT_RATIO =
        (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;

	private Rectangle viewport;
	*/
	 
	 MusicManager musicManager;
	 SoundManager soundManager;
	 
	 
	 private Stage stage;
	 WorldManager world;
	 //StarryBackground stars;
	 World physics_world;
	 
	 public static final float WORLD_TO_BOX = 0.01f;
	 public static final float BOX_TO_WORLD = 100f;
	 Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	 CollisionHandler collisionHandler;

	// Player player;
	 //Enemy enemy;
	 
	 Kiku player;
	 
	 
	 JSActor BASENODE;

     // constructor to keep a reference to the main Game class
      public GameScreen(GameManager gameManager){
              this.gameManager = gameManager;
              
      }
	
      float zoom = 0;
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		OrthographicCamera camera = (OrthographicCamera)stage.getCamera();
		
		// modify camera here
		physics_world.step(1/60f, 6, 2);
		
		
		updatePhysics();
		

		camera.position.set(player.getX() + player.getOriginX() + 200, player.getY() + player.getOriginY() + 75, 10000.0f);
		camera.far = 10000.0f;
		camera.zoom = 2;
		
		
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();
	       
		
	        
	     if(player.getY() < -250)
	    	 player.hitGround();
		
		
	   if(Gdx.input.isKeyPressed(Keys.P)) {
		   debugRenderer.setDrawBodies(false);
		   //soundManager.play(LucidSound.JUMP);
	   }
	   else if(Gdx.input.isKeyPressed(Keys.O)){
		   debugRenderer.setDrawBodies(true);
	   }

	     
	     debugRenderer.render(physics_world, camera.combined);

		
	}
	
	public void updatePhysics() {
		/*Iterator<Body> bi = physics_world.getBodies();
        
		while (bi.hasNext()){
		    Body b = bi.next();

		    // Get the bodies user data - in this example, our user 
		    // data is an instance of the Entity class
		    JSActor e = (JSActor) b.getUserData();

		    if (e != null) {
		        // Update the entities/sprites position and angle
		        e.setPosition(b.getPosition().x, b.getPosition().y);
		        // We need to convert our angle from radians to degrees
		        e.setRotation(MathUtils.radiansToDegrees * b.getAngle());
		    }
		}*/
	}
	

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		
		
		
		
  		float w = Gdx.graphics.getWidth();
  		float h = Gdx.graphics.getHeight();


  	    Camera camera = new OrthographicCamera(1, h/w);
  		
  		stage = new Stage(w,h,true);
        Gdx.input.setInputProcessor(stage);
        stage.setCamera(camera);
        
  		/*
  		camera = new OrthographicCamera(w, h);
  		camera.viewportHeight = 1280;  
  		camera.viewportWidth = 1280;
  		camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);  
  		camera.update();
*/
        
        
  		physics_world = new World(new Vector2(0,0), true);
  		collisionHandler = new CollisionHandler();
  		physics_world.setContactListener(collisionHandler);
  		
  		
  		BASENODE = new JSActor();
  		stage.addActor(BASENODE);
  		
  		player = new Kiku();
  		player.addPhysics(physics_world);
  		BASENODE.addActor(player);
  		

  		//enemy = new Enemy();
  		//BASENODE.addActor(enemy);
  		
  		
  		//stars = new StarryBackground();
  		//BASENODE.addActor(stars);
  		
  		world = new WorldManager(-1500,-720/2,1280 * 3,720 * 2);
  		world.addPhysics(physics_world);
  		BASENODE.addActor(world);
  		

  		 // create the music manager service
        musicManager = new MusicManager();
      //  musicManager.setVolume( preferencesManager.getVolume() );
        musicManager.setEnabled( true);
        
        //musicManager.play(LucidMusic.SWINDLER);

        // create the sound manager service
        soundManager = new SoundManager();
       // soundManager.setVolume( preferencesManager.getVolume() );
        soundManager.setEnabled( true);
        
  		
  		//stage.setKeyboardFocus(player);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		//batch.dispose();
        stage.dispose();

	}

}
