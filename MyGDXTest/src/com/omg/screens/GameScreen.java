package com.omg.screens;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSFont;
import com.omg.drawing.JSSpriter;
import com.omg.events.DialogueListener;
import com.omg.filemanagement.LEDataHandler;
import com.omg.filemanagement.QRSet.QROptions;
import com.omg.gui.ABCDDialogue;
import com.omg.gui.HUD;
import com.omg.gui.ReplayMenu;
import com.omg.gui.ReplayMenu.MenuState;
import com.omg.gui.VersusDialogue;
import com.omg.sfx.LucidSound;
import com.omg.sfx.MusicManager;
import com.omg.sfx.MusicManager.LucidMusic;
import com.omg.sfx.SoundManager;
import com.omg.spriter.TextureProvider;
import com.omg.ssplayer.Kiku;
import com.omg.ssplayer.mechanics.KiOrb;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.CollisionHandler;
import com.omg.ssworld.Monster;
import com.omg.ssworld.NanoKi;
import com.omg.ssworld.WorldManager;
import com.testflightapp.lib.TestFlight;

public class GameScreen implements Screen, TextureProvider, Loadable {

	 GameManager gameManager;
	 
	 
	 
	 MusicManager musicManager;
	 SoundManager soundManager;
	 
	 public SoundManager getSoundManager() {
		 return soundManager;
	 }
	 
	 public MusicManager getMusicManager() {
		 return musicManager;
	 }
	 
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
	 
	 HUD worldHUD;
	 
	 //JSFont distanceCounter;
	 float distanceTraveled = 0.0f;
	 
	 float speedBonus = 2f;
	 
	 
	 ABCDDialogue abcdDialogue;
	 
	 ReplayMenu replayMenu;
	 
	 ShaderProgram shader;
	 LEDataHandler leDataHandler;
	 
	 
	 
	 public enum GameState {
		 
		 running,
		 showingVersus,
		 dialogue,
		 leavingVersusCorrect,
		 leavingVersusIncorrect,
		 paused,
		 dead
		 
		 
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
	       
	     if(player.getY() < -250) {
	    	 //player.hitGround();
	    	 if(!player.isDead())
		    	 soundManager.play("Splash");
	    	 
	    	 if(player.imperviousToWater)
	    		 player.setY(1000);
	    	 player.kill("water");
	    	 
	     }
	     
	     if((player.isDead())) {
	    	 if(musicManager.isPlaying())
	    		 musicManager.pause();
	     }
		
		
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

	     
	  		updateHUD();

	     
	     switch(getState()) {
	     
	     case running:
	    	 
	    	 world.unfreeze();
		  	 abcdDialogue.setVisible(false);
		  	 player.unfreeze();
		  	 
		  	 distanceTraveled+=world.speed*5;
		     //distanceCounter.setText((int)(distanceTraveled/1000.0f) + " Squirrels, " + (int)(world.speed/1.0f) + " Sq/s");
		  	 
		  	 //timeWithoutError += 1f;
		  	 world.speed = (float) Math.log(speedBonus) * world.defaultSpeed;
		  	 
		  	 if(player.isDead()) {
		  		 this.setGameState(GameState.dead);
		  	 }
		  	 
		  	 player.updateSound(soundManager, (int)world.speed);
		  	 
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
	    	 
	    	 world.freezeWorld();
	    	 player.freeze();
	    	 
	    	 
	    	 break;
	     case dead:
	    	 world.freezeWorld();
	    	 player.freeze();
	    	 
	    	 dropDownReplayMenu();
	    	 
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
	  		player.setX(-500);
	  		player.setY(450);
	  		player.setGameScreen(this);

	  		
	  		//KiOrb orb = new KiOrb();
	  		//orb.setTarget(player);
	  		//BASENODE.addActor(orb);
	  		
	  		//knight = new JSSpriter("data/hero/BetaFormatHero.SCML",this);
	  		//BASENODE.addActor(knight);
	
	
	  		
	  		world = new WorldManager(-1500,-720/2,1280 * 3,720 * 2);
	  		world.addPhysics(physics_world);
	  		world.setPlayer(player);
	  		BASENODE.addActor(world);
	  		
	  		world.addActor(player);

	  		
	  		
	  		
	       
	  		
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
	  		
	  		
	  		worldHUD = new HUD();
	  		worldHUD.setGameScreen(this);
	  		BASENODE.addActor(worldHUD);
	  		BASENODE.setPosition(0, 1280);
	  		
	  		BASENODE.setColor(BASENODE.getColor().r, BASENODE.getColor().g, BASENODE.getColor().b, 0);
	  		BASENODE.addAction(
	  				Actions.parallel(
	  		  		Actions.moveTo(0, 0, 1, Interpolation.swingOut),
	  				Actions.fadeIn(2.5f, Interpolation.fade)
	  				));
	  		
	  		
	  		replayMenu = new ReplayMenu();
	  		replayMenu.setPosition(0, 0);
	  		replayMenu.setState(MenuState.up);
	  		stage.addActor(replayMenu);

	  		
	  		musicManager.play(LucidMusic.GAME_MUSIC);
  		
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
       //musicManager.setVolume( preferencesManager.getVolume() );
       musicManager.setEnabled( true);
       
       //musicManager.play(LucidMusic.SWINDLER);

       //create the sound manager service
       soundManager = new SoundManager();
       //soundManager.setVolume( preferencesManager.getVolume() );
       soundManager.setEnabled( true);
       
 		
		
       leDataHandler = new LEDataHandler();
       leDataHandler.loadQR("P1_6"); //qr_sample
       for(LucidSound sound : leDataHandler.getSounds()){
       	soundManager.load(sound);
       }
       
    soundManager.load(new LucidSound("sfx/correct.ogg"), "Correct");
    soundManager.load(new LucidSound("sfx/footsteps.ogg"), "Footsteps");
    soundManager.load(new LucidSound("sfx/wrong.ogg"), "Wrong");
    soundManager.load(new LucidSound("sfx/aura_up.ogg"), "Aura Up");
    soundManager.load(new LucidSound("sfx/begin.ogg"), "Woosh");
    soundManager.load(new LucidSound("sfx/bubble_pop.ogg"), "Bubble Pop");
    soundManager.load(new LucidSound("sfx/button_click.ogg"), "Click");
    soundManager.load(new LucidSound("sfx/enemy_die.ogg"), "Enemy Death");
    soundManager.load(new LucidSound("sfx/frog.ogg"), "Ribbit");
    soundManager.load(new LucidSound("sfx/jump.ogg"), "Jump");
    soundManager.load(new LucidSound("sfx/land.ogg"), "Land");
    soundManager.load(new LucidSound("sfx/laser.ogg"), "Laser");
    soundManager.load(new LucidSound("sfx/nanoki.ogg"), "Nanoki");
    soundManager.load(new LucidSound("sfx/platform_crumble.ogg"), "Crumble");
    soundManager.load(new LucidSound("sfx/splash.ogg"), "Splash");
    soundManager.load(new LucidSound("sfx/player_die.ogg"), "Player Die");











       
     
   	LAssetManager aManager = GameManager.getAssetsManager();
   	aManager.clear();

   	aManager.loadTexture("data/2_Tile.png",  "Platform_Generic");
  	aManager.loadTexture("data/enemies/eye/eye_sprite_sheet_half.png", "Enemy");
   	aManager.loadTexture("data/enemies/eye/eye_guy_front.png", "Enemy_Eye_Front");
   	

   	
  	aManager.loadTexture("data/background/small/back_clouds_small.png", "Back Clouds");
   	aManager.loadTexture("data/background/small/front_clouds_small.png", "Front Clouds");
   	aManager.loadTexture("data/background/small/front_f_small.png", "Front F");
   	aManager.loadTexture("data/background/small/mid_f_small.png", "Mid F");
   	aManager.loadTexture("data/background/small/back_f_small.png", "Back F");
   	aManager.loadTexture("data/background/small/sky_small.png", "Sky");
   	//aManager.loadTexture("data/front_stars.png","Front Stars");
   	aManager.loadTexture("data/laser.png", "Platform Spawn");
   	
   	aManager.loadTexture("data/player/actual_sprite_sheet.png", "Kiku");
   	aManager.loadTexture("data/player/Jump_Sprite_Sheet.png", "Kiku_Jump");
   	aManager.loadTexture("data/player/kiku_back.png", "Kiku_Back");
   	aManager.loadTexture("data/player/kiku_die_final.png", "Kiku_Die");

   	
   	aManager.loadTexture("data/background.png", "VS Back");
   	aManager.loadTexture("data/wind.png", "VS Swoosh");
   	aManager.loadTexture("data/ui/VS.png", "VS VS");
   	aManager.loadTexture("data/background_border.png", "VS Back Border");

   	aManager.loadTexture("data/ui/Question Box.png", "QBox");
   	aManager.loadTexture("data/background/battle/bg2_small.png", "Battle Back");


   	
   	aManager.loadTexture("data/effects/blue_aura_small.png", "Aura_B");
   	aManager.loadTexture("data/effects/blue_orb_small.png", "Orb_B");
   	aManager.loadTexture("data/effects/blue_aura_kiku.png", "PlayerAura");
   	aManager.loadTexture("data/environment/objects/purple_nanoki.png", "NanoKi");
   	
   	aManager.loadTexture("data/ui/hud/aura.png", "HUD_Aura");
   	aManager.loadTexture("data/ui/hud/hud_circle_fill.png", "HUD_Circle_Fill");
   	aManager.loadTexture("data/ui/hud/hud_quarter_circle.png", "HUD_Circle_Line");
   	aManager.loadTexture("data/ui/hud/score_box_faded.png", "HUD_ScoreBox");
   	aManager.loadTexture("data/ui/hud/plus_10.png", "HUD_Plus10");
   	aManager.loadTexture("data/ui/hud/plus_1.png", "HUD_Plus1");
   	aManager.loadTexture("data/ui/paused.png", "HUD_Paused");
   	
   	aManager.loadTexture("data/ui/replay_menu/drop_down_box.png", "Replay_Billboard");




   	
   	aManager.loadTexture("data/effects/white flash.png", "White");
   	aManager.loadTexture("data/effects/explosion.png", "Explosion");

   	aManager.loadTexture("data/environment/platforms/small/le_platform_small.png", "Platform_Le");
   	aManager.loadTexture("data/environment/platforms/small/le_spikes_small.png", "Platform_Spikes");
   	aManager.loadTexture("data/environment/platforms/small/moving_platform_small.png", "Platform_Moving");
   	aManager.loadTexture("data/environment/platforms/small/falling_platform_small.png", "Platform_Falling");
   	aManager.loadTexture("data/environment/platforms/small/starting_platform.png", "Platform_Starting");
   	aManager.loadTexture("data/background/small/river_small.png", "River");
   	
   	aManager.loadTexture("data/player/critters/birdie_small.png", "Animal_Bird");
   	aManager.loadTexture("data/player/critters/frog.png", "Animal_Frog");
   	aManager.loadTexture("data/player/critters/ki_orb_dude.png", "Animal_KiDude");

   	aManager.loadTexture("data/effects/bubble.png", "Le_Bubble");
   	
   	aManager.loadTexture("data/mega_laser.png", "Laser");

   	






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
   	
   	/*CAUSES ERROR
   	while(!aManager.update()) {};	
   	aManager.finishLoading();
   	*/

   	//aManager.update();
   	
   	//aManager.update();
   	
   	
	
   //Gdx.app.log("AssetManager", "ENEMY TEXTURE GET GET" + aManager.get(aManager.getPath("Enemy"), Texture.class).getWidth() + " <<<<");
   	//aManager.bindTextures();
   	
   	Gdx.app.log("AssetManager", "BAM!! " + aManager.getDiagnostics());
		loaded = true;
	}
	
	
	public void dropDownReplayMenu() {
		
		if(replayMenu.getState() == MenuState.up){
			
			replayMenu.dropDown();
			replayMenu.setGameManager(gameManager);
			replayMenu.setScore(player.getNanoKiCollected());
			
			BASENODE.addAction(Actions.fadeOut(4));
			worldHUD.setVisible(false);
			

		}
		
	}
	
	
	float questionRightRev = 5;
	float questionWrongSlow = 10;
	float jumpRev = .01f;
	float timeSlow = .998f;
	float nanoKiRev = .05f;
	float laserSlow = .2f;
	
	public void updateHUD() {
		if(worldHUD != null) {
		  	 worldHUD.setNanoKiCollected(player.getNanoKiCollected());
		  	 worldHUD.applyFrictionToSpinner(timeSlow);
		  	 if(player.isDead()) {
		  		 worldHUD.setSpinnerSpeed(0);
		  	 }
		  	 
		  	 if(worldHUD.isPaused()) {
		  		 setGameState(GameState.paused);
		  	 } else if(getState() == GameState.paused && !player.isDead()) {
		  		 setGameState(GameState.running);

		  	 }
		}
		
	}
	
	
	
	public void collectedNanoKi(NanoKi ki) {
		worldHUD.plus10();
		worldHUD.revSpinner(nanoKiRev);
	}
   	
	public void isHittingLaser() {
		worldHUD.slowSpinner(laserSlow);
	}
	public void playerJumped() {
		worldHUD.revSpinner(jumpRev);
	}
	
	
	public void hitMonster(Monster j) {
		
		if(getState() == GameState.running) {
			
			if(!player.isStunned) {
			
				setGameState(GameState.showingVersus);
				//soundManager.play(soundManager.QUESTION());
				soundManager.play(abcdDialogue.getQRBlock().getQuestionSound());
				soundManager.setVolume(1.0f);
				musicManager.setVolume(.20f);
				
				gameManager.setScreen(new VersusScreen(gameManager, this, abcdDialogue));
				lastHit = j;
			} else {
				player.removeKi();
				j.addAction(Actions.parallel(Actions.color(Color.GREEN, .2f, Interpolation.exp10), Actions.moveBy(0, 600, 2f)));
			}
			
		}
		
		
	}
	
	Monster lastHit;
	
	
	public void answerSelected(QROptions given, QROptions correct, boolean isRight) {
		
		musicManager.setVolume(1.0f);

		if(isRight) {
			setGameState(GameState.running);
			abcdDialogue.setQRBlock(leDataHandler.getRandomBlock());
			
			speedBonus += 0.25f;
			player.collectKi();
			
			lastHit.removePhysics();
			lastHit.remove();
			
			soundManager.play("Correct");
			soundManager.play("Enemy Death");
			
			worldHUD.revSpinner(questionRightRev);
		}
		else {
			
			
			player.removeKi();
			
			lastHit.removePhysics();
			lastHit.remove();
			
			setGameState(GameState.running);
			speedBonus *= 0.5f;
			if(speedBonus < 2) {
				speedBonus = 2;
			}
			soundManager.play("Wrong");
			
			worldHUD.slowSpinner(questionWrongSlow);


		}
		
		player.addJump();
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
