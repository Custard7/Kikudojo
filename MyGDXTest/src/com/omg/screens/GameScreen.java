package com.omg.screens;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSFont;
import com.omg.drawing.JSSpriter;
import com.omg.events.DialogueListener;
import com.omg.filemanagement.LEDataHandler;
import com.omg.filemanagement.QRSet.QROptions;
import com.omg.gui.ABCDDialogue;
import com.omg.gui.VersusDialogue;
import com.omg.sfx.LucidSound;
import com.omg.sfx.MusicManager;
import com.omg.sfx.SoundManager;
import com.omg.spriter.TextureProvider;
import com.omg.ssplayer.Kiku;
import com.omg.ssplayer.mechanics.KiOrb;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.CollisionHandler;
import com.omg.ssworld.Monster;
import com.omg.ssworld.WorldManager;
import com.testflightapp.lib.TestFlight;

public class GameScreen implements Screen, TextureProvider, Loadable {

	 GameManager gameManager;
	 
	 
	 
	 MusicManager musicManager;
	 SoundManager soundManager;
	 
	 
	 private Stage stage;
	 WorldManager world;
	 World physics_world;
	 
	 public static final float WORLD_TO_BOX = 0.01f;
	 public static final float BOX_TO_WORLD = 100f;
	 Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	 CollisionHandler collisionHandler;

	 HashMap<String, Texture> textures;

	 
	 Kiku player;
	 
	 JSSpriter knight;
	 
	 JSActor BASENODE;
	 
	 JSFont distanceCounter;
	 float distanceTraveled = 0.0f;
	 
	 float speedBonus = 2f;
	 
	 
	 ABCDDialogue abcdDialogue;
	 
	 
	 ShaderProgram shader;
	 LEDataHandler leDataHandler;
	 
	 
	 
	 public enum GameState {
		 
		 running,
		 showingVersus,
		 dialogue,
		 leavingVersusCorrect,
		 leavingVersusIncorrect,
		 paused,
		 
		 
	 }
	 
	 GameState gameState = GameState.running;
	 
	 
	 public GameState getState() {
		 return gameState;
	 }
	 
	 public void setGameState(GameState state) {
		 this.gameState = state;
	 }
	 

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
		

		//camera.position.set(player.getX() + player.getOriginX() + 200, player.getY() + player.getOriginY() + 75, 10000.0f);
		camera.position.set(200, 500, 10000.0f);
		camera.far = 10000.0f;
		camera.zoom = 2;
		
		//shader.begin();

		
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();
	       
		//shader.end();
	       
	     if(player.getY() < -250)
	    	 player.hitGround();
		
		
	   if(Gdx.input.isKeyPressed(Keys.P)) {
		   debugRenderer.setDrawBodies(false);
		   //soundManager.play(LucidSound.JUMP);
		   if(Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS)
			   TestFlight.log("Turned off debug renderer..");
	   }
	   else if(Gdx.input.isKeyPressed(Keys.O)){
		   debugRenderer.setDrawBodies(true);
	   }

	     
	     debugRenderer.render(physics_world, camera.combined);

	     
	     switch(getState()) {
	     
	     case running:
	    	 
	    	 world.unfreeze();
		  	 abcdDialogue.setVisible(false);
		  	 player.unfreeze();
		  	 
		  	 distanceTraveled+=world.speed*5;
		  	 distanceCounter.setText((int)(distanceTraveled/1000.0f) + " Squirrels, " + (int)(world.speed/1.0f) + " Sq/s");
		  	 
		  	 //timeWithoutError += 1f;
		  	 world.speed = (float) Math.log(speedBonus) * world.defaultSpeed;
		  	 
	    	 break;
	     case showingVersus:
	    	 
	    	 world.freezeWorld();
	    	 player.freeze();
	    	 
	    	 //versusDialogue.setVisible(true);
	    	 
	    	 
	    	 //
	    	 
	    	 //TODO: VS Dialogue
	    	 
	    	 
	    	 break;
	     case dialogue:
	    	 
	    	 world.freezeWorld();
		  	abcdDialogue.setVisible(true);
		  	player.freeze();
	    	 
	    	// if(Gdx.input.isKeyPressed(Keys.SPACE))
	    		// setGameState(GameState.running);
	    	 
	    	 break;
	     case paused:
	    	 break;
	     default:
	    	 break;
	     
	     
	     }
	     
	     
	        Table.drawDebug(stage); // This is optional, but enables debug lines for tables.

		
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
		//stage.setViewport(width, height, true);
		stage.setViewport(GameManager.VIRTUAL_WIDTH, GameManager.VIRTUAL_HEIGHT, true);

	}

	boolean hasShown = false;
	
	@Override
	public void show() {
		if(!isLoaded())
			load();
		
		if(!hasShown) {
			
			float w = Gdx.graphics.getWidth();
	  		float h = Gdx.graphics.getHeight();
	
	
	  	    Camera camera = new OrthographicCamera(1, h/w);
	  		
	  		stage = new Stage(w,h,true);
	        Gdx.input.setInputProcessor(stage);
	        stage.setCamera(camera);
	  	  
	        debugRenderer.setDrawBodies(false);

	        
	       
	 		//stage.setKeyboardFocus(player);
	
	        
	        //shader = new ShaderProgram(vertexShader, fragmentShader);
	        
	        
	  		physics_world = new World(new Vector2(0,0), true);
	  		collisionHandler = new CollisionHandler();
	  		collisionHandler.setWorld(world);
	  		collisionHandler.setGameScreen(this);
	  		physics_world.setContactListener(collisionHandler);
	  		
	  		
	  		BASENODE = new JSActor();
	  		stage.addActor(BASENODE);
	  		
	  		player = new Kiku();
	  		player.addPhysics(physics_world);
	  		BASENODE.addActor(player);
	  		
	  		//KiOrb orb = new KiOrb();
	  		//orb.setTarget(player);
	  		//BASENODE.addActor(orb);
	  		
	  		//knight = new JSSpriter("data/hero/BetaFormatHero.SCML",this);
	  		//BASENODE.addActor(knight);
	
	
	  		
	  		world = new WorldManager(-1500,-720/2,1280 * 3,720 * 2);
	  		world.addPhysics(physics_world);
	  		BASENODE.addActor(world);
	  		
	  		
	  		distanceCounter = new JSFont("0.0 Squirrels");
	  		distanceCounter.setPosition(-1000,-50);
	  		BASENODE.addActor(distanceCounter);
	  		
	  		
	       
	  		
	  		abcdDialogue = new ABCDDialogue(leDataHandler.getRandomBlock());
	  		abcdDialogue.setVisible(false);
	  		abcdDialogue.setWorldBounds(world);
	  		abcdDialogue.setListener(new DialogueListener() {
	
				@Override
				public boolean handle(Event event) {
					// TODO Auto-generated method stub
					return true;
				}
	
				@Override
				public void onSelected(QROptions given, QROptions correct, boolean isRight) {
					// TODO Auto-generated method stub
					answerSelected(given, correct, isRight);
				}
	  			
	  		});
	  		abcdDialogue.setFillParent(true);
	  		abcdDialogue.debug();
	  		//stage.addActor(abcdDialogue);
  		
		}

  		hasShown = true;
  		

	}
	
	private boolean loaded = false;
	public boolean isLoaded() {
		return loaded;
	}
	
	public void load() {
		 
        textures = new HashMap<String, Texture>();
        

 		 // create the music manager service
       musicManager = new MusicManager();
     //  musicManager.setVolume( preferencesManager.getVolume() );
       musicManager.setEnabled( true);
       
       //musicManager.play(LucidMusic.SWINDLER);

       // create the sound manager service
       soundManager = new SoundManager();
      // soundManager.setVolume( preferencesManager.getVolume() );
       soundManager.setEnabled( true);
       
 		
		
       leDataHandler = new LEDataHandler();
       leDataHandler.loadQR("qr_sample");
       for(LucidSound sound : leDataHandler.getSounds()){
       	soundManager.load(sound);
       }
       
     
   	LAssetManager aManager = GameManager.getAssetsManager();
   	aManager.clear();

   	aManager.load("data/2_Tile.png", Texture.class, "Platform_Generic");
   	aManager.load("data/eye_sprite_sheet.png",Texture.class, "Enemy");
  	aManager.load("data/background/back_clouds.png",Texture.class, "Back Clouds");
   	aManager.load("data/background/front_clouds.png",Texture.class, "Front Clouds");
   	aManager.load("data/background/front_f.png",Texture.class, "Front F");
   	aManager.load("data/background/mid_f.png",Texture.class, "Mid F");
   	aManager.load("data/background/back_f.png",Texture.class, "Back F");
   	aManager.load("data/background/sky.png",Texture.class, "Sky");
   	aManager.load("data/front_stars.png",Texture.class, "Front Stars");
   	aManager.load("data/laser.png",Texture.class, "Platform Spawn");
   	
   	aManager.load("data/player/actual_sprite_sheet.png",Texture.class, "Kiku");
   	aManager.load("data/player/Jump_Sprite_Sheet.png",Texture.class, "Kiku_Jump");

   	
   	aManager.load("data/background.png", Texture.class, "VS Back");
   	aManager.load("data/wind.png", Texture.class, "VS Swoosh");
   	
   	aManager.loadTexture("data/effects/blue_aura_small.png", "Aura_B");
   	aManager.loadTexture("data/effects/blue_orb_small.png", "Orb_B");
   	aManager.loadTexture("data/effects/blue_aura_kiku.png", "PlayerAura");


   	/*
   	
  	aManager.loadTexture("data/2_Tile.png",  "Platform_Generic");
   	aManager.loadTexture("data/eye_sprite_sheet.png",  "Enemy");
  	aManager.loadTexture("data/background/back_clouds.png", "Back Clouds");
   	aManager.loadTexture("data/background/front_clouds.png",  "Front Clouds");
   	aManager.loadTexture("data/background/front_f.png", "Front F");
   	aManager.loadTexture("data/background/mid_f.png", "Mid F");
   	aManager.loadTexture("data/background/back_f.png","Back F");
   	aManager.loadTexture("data/background/sky.png","Sky");
   	aManager.loadTexture("data/front_stars.png", "Front Stars");
   	aManager.loadTexture("data/laser.png", "Platform Spawn");
   	
   	aManager.loadTexture("data/player/actual_sprite_sheet.png", "Kiku");
   	aManager.loadTexture("data/player/Jump_Sprite_Sheet.png", "Kiku_Jump");

   	
   	aManager.loadTexture("data/background.png",  "VS Back");
   	aManager.loadTexture("data/wind.png", "VS Swoosh");
*/
   	
   	while(!aManager.update()) {};	
   	aManager.finishLoading();
   	//aManager.update();
	
   //Gdx.app.log("AssetManager", "ENEMY TEXTURE GET GET" + aManager.get(aManager.getPath("Enemy"), Texture.class).getWidth() + " <<<<");
   	//aManager.bindTextures();
   	
   	Gdx.app.log("AssetManager", "BAM!! " + aManager.getDiagnostics());
		loaded = true;
	}
   	
	
	
	public void hitMonster(Monster j) {
		
		if(getState() == GameState.running) {
			setGameState(GameState.showingVersus);
			//soundManager.play(soundManager.QUESTION());
			soundManager.play(abcdDialogue.getQRBlock().getQuestionSound());
			
			
			
			gameManager.setScreen(new VersusScreen(gameManager, this, abcdDialogue));
			lastHit = j;
			
		}
		
		
	}
	
	Monster lastHit;
	
	
	public void answerSelected(QROptions given, QROptions correct, boolean isRight) {
		
		if(isRight) {
			setGameState(GameState.running);
			abcdDialogue.setQRBlock(leDataHandler.getRandomBlock());
			
			speedBonus += 0.25f;
			player.collectKi();
			
			lastHit.removePhysics();
			lastHit.remove();
		}
		else {
			
			player.removeKi();
			
			setGameState(GameState.running);
			speedBonus *= 0.5f;
			if(speedBonus < 2)
				speedBonus = 2;
		}
	}
	
	
	

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {

		loaded = false;
   	
		
		//loaded = true;
		
	}

	@Override
	public void dispose() {
		//batch.dispose();
        stage.dispose();
        
        /*
       	LAssetManager aManager = GameManager.getAssetsManager();
        
    	aManager.unload("data/2_Tile.png");
       	aManager.unload("data/big_pixel_coin.png");
       	aManager.unload("data/background/back_clouds.png");
       	aManager.unload("data/background/front_clouds.png");
       	aManager.unload("data/background/front_f.png");
       	aManager.unload("data/background/mid_f.png");
       	aManager.unload("data/background/back_f.png");
       	aManager.unload("data/background/sky.png");
       	aManager.unload("data/front_stars.png");
       	aManager.unload("data/laser.png");
       	
       	//aManager.clear();
       	 * */
    

	}
	
	@Override
	  public Texture getTexture(String filename) {
	    if (!textures.containsKey(filename)) {
	      textures.put(filename, new Texture(Gdx.files.internal(filename)));
	      System.out.println("Texture " + filename + " loaded");
	    }
	    return textures.get(filename);

	  }

	  @Override
	  public void disposeTexture(String filename) {
	    textures.get(filename).dispose();
	    textures.remove(filename);
	    System.out.println("Texture " + filename + " disposed");
	  }
	  
	  
	  String vertexShader = "attribute vec4 a_position;    \n" + 
              "attribute vec4 a_color;\n" +
              "attribute vec2 a_texCoord0;\n" + 
              "uniform mat4 u_worldView;\n" + 
              "varying vec4 v_color;" + 
              "varying vec2 v_texCoords;" + 
              "void main()                  \n" + 
              "{                            \n" + 
              "   v_color = vec4(1, 1, 1, 1); \n" + 
              "   v_texCoords = a_texCoord0; \n" + 
              "   gl_Position =  u_worldView * a_position;  \n"      + 
              "}                            \n" ;
	  String fragmentShader = "#ifdef GL_ES\n" +
                "precision mediump float;\n" + 
                "#endif\n" + 
                "varying vec4 v_color;\n" + 
                "varying vec2 v_texCoords;\n" + 
                "uniform sampler2D u_texture;\n" + 
                "void main()                                  \n" + 
                "{                                            \n" + 
                "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" + 
                "}";

}
