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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSFont;
import com.omg.sfx.MusicManager;
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
	 
	 JSFont menuText;
	 JSFont touchToContinueText;

	 HashMap<String, Texture> textures;

	 

    // constructor to keep a reference to the main Game class
     public MainMenuScreen(GameManager gameManager){
             this.gameManager = gameManager;
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
	        
	
	    if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched())
	    	//gameManager.gotoGameScreen();
	    {
	    	gameManager.loadScreen(new GameScreen(gameManager));
	    }
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
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
        
        textures = new HashMap<String, Texture>();

        
  		BASENODE = new JSActor();
  		stage.addActor(BASENODE);
  		
  		BASENODE.setPosition(-300, 200);
  		
  		menuText = new JSFont("Kikudojo");
  		menuText.setPosition(100,0);
  		BASENODE.addActor(menuText);
  		
  		touchToContinueText = new JSFont("Touch to continue.");
  		touchToContinueText.setPosition(50,-150);
  		BASENODE.addActor(touchToContinueText);
  		
  		
  		
  		
  		
  	// create the music manager service
        musicManager = new MusicManager();
      //  musicManager.setVolume( preferencesManager.getVolume() );
        musicManager.setEnabled( true);
        
        //musicManager.play(LucidMusic.SWINDLER);

        // create the sound manager service
        soundManager = new SoundManager();
       // soundManager.setVolume( preferencesManager.getVolume() );
        soundManager.setEnabled( true);
        
  		
		
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
