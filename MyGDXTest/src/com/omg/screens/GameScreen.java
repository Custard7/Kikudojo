package com.omg.screens;

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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSEntity;
import com.omg.drawing.JSEntity.JSVector2;
import com.omg.ssplayer.Enemy;
import com.omg.ssplayer.Player;
import com.omg.sswindler.GameManager;
import com.omg.ssworld.StarryBackground;

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
	 
	 
	 private Stage stage;
	 
	 StarryBackground stars;

	 Player player;
	 Enemy enemy;
	 
	 
	 JSActor BASENODE;

     // constructor to keep a reference to the main Game class
      public GameScreen(GameManager gameManager){
              this.gameManager = gameManager;
              
      }
	
      float zoom = 0;
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		Camera camera = stage.getCamera();
		
		// modify camera here
		
		

		camera.position.set(player.getX() + player.getOriginX(), player.getY() + player.getOriginY(), 0.0f);
		
		
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();
	        
		
	        
		

		
		
		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		
		
		
		
  		float w = Gdx.graphics.getWidth();
  		float h = Gdx.graphics.getHeight();
  		
  		
  		stage = new Stage(w,h,true);
        Gdx.input.setInputProcessor(stage);
        
  		/*
  		camera = new OrthographicCamera(w, h);
  		camera.viewportHeight = 1280;  
  		camera.viewportWidth = 1280;
  		camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);  
  		camera.update();
*/
        
        
        
  		
  		BASENODE = new JSActor();
  		stage.addActor(BASENODE);
  		
  		player = new Player();
  		BASENODE.addActor(player);
  		
  		enemy = new Enemy();
  		BASENODE.addActor(enemy);
  		
  		
  		stars = new StarryBackground();
  		BASENODE.addActor(stars);
  		
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
