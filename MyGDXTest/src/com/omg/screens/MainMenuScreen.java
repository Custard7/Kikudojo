package com.omg.screens;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSFont;
import com.omg.sfx.LucidSound;
import com.omg.sfx.MusicManager;
import com.omg.sfx.MusicManager.LucidMusic;
import com.omg.sfx.SoundManager;
import com.omg.spriter.SpriterObject;
import com.omg.spriter.TextureProvider;
import com.omg.spriter.util.SpriterDrawer;
import com.omg.spriter.util.SpriterImporter;
import com.omg.sswindler.GameManager;
import com.testflightapp.lib.TestFlight;

public class MainMenuScreen implements Screen, TextureProvider {

	
	
	GameManager gameManager;
	
	 MusicManager musicManager;
	 SoundManager soundManager;
	 
	 private Stage stage;
	 JSActor BASENODE;
	 
	 boolean skipIntro = true;
	 

	 JSActor omgLogo;
	 JSActor lincLogo;
	 
	 
	 JSActor background;
	 JSActor kikuText;
	 JSActor continueText;
	
	 
	 int kikuTextX = -100;
	 int kikuTextY = -300;
	 int kikuTextInitY = 300;

	 HashMap<String, Texture> textures;
	 
	 public enum MenuState {
		 
		 logos,
		 transitionIn,
		 open,
		 transitionOut,
		 out
		 
	 }
	 
	 MenuState menuState = MenuState.logos;

	 
	 public MenuState getState() {
		 return menuState;
	 }
	 
	 public void setState(MenuState state) {
		 switch(state) {
	     case logos:
	    	 background.setVisible(false);
	    	 kikuText.setVisible(false);
	    	 continueText.setVisible(false);
	    	 
	    	 lincLogo.setVisible(true);
	    	 omgLogo.setVisible(true);
	    	 
	    	 lincLogo.setColor(lincLogo.getColor().r, lincLogo.getColor().g, lincLogo.getColor().b, 0);
	    	 omgLogo.setColor(omgLogo.getColor().r, omgLogo.getColor().g, omgLogo.getColor().b, 0);
	    	 
	    	 lincLogo.addAction(Actions.sequence(
	    			 Actions.fadeIn(1),
	    			 Actions.delay(1),
	    			 Actions.fadeOut(1),
	    			 Actions.run(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
					    	 musicManager.play(LucidMusic.INTRO_MUSIC, false);

							omgLogo.addAction(Actions.sequence(
					    			 Actions.fadeIn(1),
					    			 Actions.delay(1),
					    			 Actions.fadeOut(1),
					    			 Actions.run(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											setState(MenuState.transitionIn);
										}
					    				 
					    			 })
					    			 ));
						}
	    				 
	    			 })
	    			 ));

	    	 musicManager.stop();
	    	 
	    	 break;
	     case transitionIn:
	    	 
	    	 
	    	 background.setVisible(true);
	    	 background.setColor(background.getColor().r, background.getColor().g, background.getColor().b, 0);
	    	 
	    	 kikuText.setVisible(true);
	    	 kikuText.setPosition(kikuTextX, kikuTextInitY);
	    	 kikuText.addAction(Actions.sequence(
	    			 Actions.moveTo(kikuTextX, kikuTextY, 3, Interpolation.bounceOut),
	    			 Actions.run(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							background.addAction(Actions.sequence(
									Actions.fadeIn(2, Interpolation.fade),
									Actions.run(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											setState(MenuState.open);
										}
										
									})
									));
							continueText.addAction(
									Actions.forever(
											Actions.sequence(
													Actions.fadeOut(2, Interpolation.fade),
													Actions.fadeIn(1, Interpolation.fade)
									)));
						}
	    				 
	    			 })
	    			 ));
	    	 
	    	 continueText.setVisible(true);
	    	 continueText.setColor(background.getColor().r, background.getColor().g, background.getColor().b, 0);

	    	 
	    	 lincLogo.setVisible(false);
	    	 omgLogo.setVisible(false);
	    	 
	    	 break;
	     case open:
	    	 
	    	 musicManager.play(LucidMusic.MENU_MUSIC);
	    	 
	    	 background.setVisible(true);
	    	 background.setColor(background.getColor().r, background.getColor().g, background.getColor().b, 1);
	    	 
	    	 kikuText.setVisible(true);
	    	 kikuText.setPosition(kikuTextX, kikuTextY);
	    	 
	    	 continueText.setVisible(true);
	    	 continueText.setColor(background.getColor().r, background.getColor().g, background.getColor().b, 1);
	    	 
	    	 lincLogo.setVisible(false);
	    	 omgLogo.setVisible(false);
	    	 
	    	 break;
	     case transitionOut:
	    	 //musicManager.play(LucidMusic.MENU_OUTRO, false);
	    	 
	    	 background.setVisible(true);
	    	 background.setColor(background.getColor().r, background.getColor().g, background.getColor().b, 1);
	    	 
	    	 background.addAction(Actions.sequence(
	    			 Actions.fadeOut(1.5f)
	    			 ));
	    	 
	    	 kikuText.setVisible(true);
	    	 kikuText.setPosition(kikuTextX, kikuTextY);
	    	 
	    	 kikuText.addAction(Actions.sequence(
	    			 Actions.moveTo(kikuTextX, kikuTextInitY, 1.5f, Interpolation.bounceIn),
	    			 Actions.run(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							setState(MenuState.out);
						}
	    				 
	    			 })
	    			 ));
	    	 
	    	 continueText.setVisible(false);
	    	 continueText.setColor(background.getColor().r, background.getColor().g, background.getColor().b, 1);
	    	 
	    	 lincLogo.setVisible(false);
	    	 omgLogo.setVisible(false);
	    	 
	    	 break;
     }
		 
		 
		 menuState = state;
	 }
	 

    // constructor to keep a reference to the main Game class
     public MainMenuScreen(GameManager gameManager){
             this.gameManager = gameManager;
     }
     // constructor to keep a reference to the main Game class
     public MainMenuScreen(GameManager gameManager, boolean skipIntro){
             this.gameManager = gameManager;
             this.skipIntro = skipIntro;
     }	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		OrthographicCamera camera = (OrthographicCamera)stage.getCamera();
		
		

		camera.position.set(0.0f,0.0f, 0.0f);
		camera.zoom = 1.0f;
		
		
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();
	        
	        
	        
	     switch(menuState) {
		     case logos:
		    	 //setState(MenuState.transitionIn);
		    	 
		    	 
		    	 
		    	 break;
		     case transitionIn:

		    	 
		    	 break;
		     case open:
		    	 
		    	 if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched())
		 	    	//gameManager.gotoGameScreen();
		 	    {
		 	    	//gameManager.loadScreen(new GameScreen(gameManager));
		    		 setState(MenuState.transitionOut);
		    		 soundManager.play("Begin");
		    		 musicManager.stop();
		 	    }
		    	 break;
		     case transitionOut:
		    	 
		    	 break;
		     case out:
		    	 
		 	     gameManager.loadScreen(new GameScreen(gameManager));
		    	 break;
	     }

	        
	    
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//stage.setViewport(width, height, true);
		stage.setViewport(GameManager.VIRTUAL_WIDTH, GameManager.VIRTUAL_HEIGHT, true);
	}

	@Override
	public void show() {
  		float w = Gdx.graphics.getWidth();
  		float h = Gdx.graphics.getHeight();
		//float w = GameManager.VIRTUAL_WIDTH;
		//float h = GameManager.VIRTUAL_HEIGHT;
  		//Gdx.gl.glViewport(0, 0, (int)w, (int)h);

  		Gdx.app.log("MONSTER", "w: " + w + " h: " + h);
  		
  	    Camera camera = new OrthographicCamera(1, h/w);
  		
  		stage = new Stage(w,h,true);
        Gdx.input.setInputProcessor(stage);
        stage.setCamera(camera);
        
        

        
        textures = new HashMap<String, Texture>();

        
  		BASENODE = new JSActor();
  		stage.addActor(BASENODE);
  		
  		
  		BASENODE.setPosition(-300, 200);
 
  		background = new JSActor(new TextureRegion(new Texture(Gdx.files.internal("data/ui/menu/menu_background.png")),0,0,1280,720)); //data/splash screen.png
  		background.setPosition(-340, -560);
  		BASENODE.addActor(background);
  		
  		kikuText = new JSActor(new TextureRegion(new Texture(Gdx.files.internal("data/ui/menu/kikudojo_text.png")),0,0,767,212));
  		kikuText.setPosition(-100,-300);
  		BASENODE.addActor(kikuText);
  		
  		continueText = new JSActor(new TextureRegion(new Texture(Gdx.files.internal("data/ui/menu/continue_japanese.png")),0,0,437,123));
  		continueText.setPosition(50, -550);
  		BASENODE.addActor(continueText);
  		
  		
  		
  		omgLogo = new JSActor(new TextureRegion(new Texture(Gdx.files.internal("data/ui/logos/OMG_LOGO_HEADER_BIG.png")),0,0,1280,720));
  		omgLogo.setPosition(-340, -560);
  		BASENODE.addActor(omgLogo);
  		
  		lincLogo = new JSActor(new TextureRegion(new Texture(Gdx.files.internal("data/ui/logos/linc_logo_final.png")),0,0,1280,720));
  		lincLogo.setPosition(-340,-560);
  		BASENODE.addActor(lincLogo);
  		
  		
  		
  		
  		
  	// create the music manager service
        musicManager = new MusicManager();
      //  musicManager.setVolume( preferencesManager.getVolume() );
        musicManager.setEnabled( true);
        
        
        
        //musicManager.play(LucidMusic.SWINDLER);

        // create the sound manager service
        soundManager = new SoundManager();
       // soundManager.setVolume( preferencesManager.getVolume() );
        soundManager.setEnabled( true);
        soundManager.load(new LucidSound("sfx/begin.ogg"), "Begin");
        
  		Gdx.app.log("MONSTER", "Showing Main Menu");
		
  		if(skipIntro) {
  			setState(MenuState.open);
  		}
  		else {
  			setState(MenuState.logos);
  		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
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
	
}
