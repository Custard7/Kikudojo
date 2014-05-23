package com.omg.screens;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSAnimatedActor;
import com.omg.drawing.JSAnimation;
import com.omg.drawing.JSFont;
import com.omg.events.DialogueListener;
import com.omg.filemanagement.QRSet.QROptions;
import com.omg.gui.ABCDDialogue;
import com.omg.gui.VersusDialogue;
import com.omg.sfx.MusicManager;
import com.omg.sfx.SoundManager;
import com.omg.spriter.TextureProvider;
import com.omg.sswindler.GameManager;

public class VersusScreen implements Screen, TextureProvider {

	GameManager gameManager;
	
	MusicManager musicManager;
	SoundManager soundManager;
	 
	private Stage stage;
	JSActor BASENODE;
	 
	JSFont touchToContinueText;

	HashMap<String, Texture> textures;

	GameScreen previousScreen;
	 
	ABCDDialogue dialogue;

	VersusDialogue vsDialogue;
	 
	JSActor battleBASE;
	
	JSActor battleBackground;

	
	JSActor battleBottom;
	
	JSActor battleBottomBack;
	
	JSFont a1Text;
	JSFont a2Text;
	JSFont a3Text;
	
	JSActor battleScene;	
	JSAnimatedActor battleEnemy;
	JSAnimatedActor battleKiku;
	
	 
	public enum VSScreenState {
		 
		Opening,
		Battling,
		Finishing
		 
	}
	 
	VSScreenState currentState = VSScreenState.Opening;
	 
	public void setState(VSScreenState state) {
		
		if(state == VSScreenState.Battling && currentState != VSScreenState.Battling) {
			updateAnswerText();
			animateBattleTransition();
		}
		
		currentState  = state;
	}
	
	public void updateAnswerText() {
		a1Text.setText("A: " + dialogue.getQRBlock().getA());
		a2Text.setText("B: " + dialogue.getQRBlock().getB());
		a3Text.setText("C: " + dialogue.getQRBlock().getC());

	}
	
	public void animateBattleTransition() {
		
		//battleBottom;
		//dialogue;
		
  		//battleBottom.setPosition(-300, -350);
  		battleBottom.setPosition(-300, -610);
  		dialogue.setPosition(1055, -200);

  		battleBottom.setColor(Color.CLEAR);
  		battleBackground.setColor(Color.CLEAR);
  		
  		battleBottom.addAction(Actions.parallel(Actions.moveTo(-300, -350, 1.5f, Interpolation.bounceOut), Actions.color(Color.WHITE, 2)));
  		dialogue.addAction(Actions.parallel(Actions.moveTo(855, -200, 1.5f, Interpolation.bounceOut), Actions.color(Color.WHITE, 2)));
  		
  		
  		battleEnemy.addAction(Actions.forever(Actions.sequence(Actions.moveBy(-20, 0, 1, Interpolation.sineOut), Actions.moveBy(20, 0, 1.1f, Interpolation.sineIn))));
  		battleKiku.addAction(Actions.forever(Actions.sequence(Actions.moveBy(-20, 0, 1, Interpolation.sineOut), Actions.moveBy(20, 0, 1.1f, Interpolation.sineIn))));

  		
  		battleBackground.addAction(Actions.parallel(Actions.color(Color.WHITE, 1)));
	}
	 
    // constructor to keep a reference to the main Game class
    public VersusScreen(GameManager gameManager, GameScreen previousScreen, ABCDDialogue d){
             this.gameManager = gameManager;
             
             this.previousScreen = previousScreen;
             this.dialogue = d;
             dialogue.setVisible(false);
             
             dialogue.setListener(new DialogueListener() {
            		
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
             
             
             
             
             
     }
	
     
     public void answerSelected(QROptions given, QROptions correct, boolean isRight) {
    	gameManager.setScreen(previousScreen);
 		previousScreen.answerSelected(given, correct, isRight);

    	
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
	        
	        
	        
	    switch(currentState) {
	    
	    case Opening:
	    	
	    	dialogue.setVisible(false);
	    	battleBASE.setVisible(false);
	    	vsDialogue.setVisible(true);
	    	vsDialogue.updateFromVersus();
	    	
	    	if(vsDialogue.isDone()) {
	    		setState(VSScreenState.Battling);
	    	}
	    	
	    	break;
	    case Battling:
	    	
	    	dialogue.setVisible(true);
	    	vsDialogue.setVisible(false);
	    	battleBASE.setVisible(true);
	    	
	    	break;
	    case Finishing:
	    	
	    	dialogue.setVisible(false);
	    	vsDialogue.setVisible(false);

	    	
	    	break;
	    default:
	    	
	    	break;
	    	
	    
	    
	    }
	        
	        
	
	    if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched())
	    	//gameManager.gotoGameScreen();
	    {
	    	//gameManager.loadScreen(new GameScreen(gameManager));
	    	//gameManager.setScreen(previousScreen);
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


  	    Camera camera = new OrthographicCamera(1, h/w);
  		
  		stage = new Stage(w,h,true);
        Gdx.input.setInputProcessor(stage);
        stage.setCamera(camera);
        
        textures = new HashMap<String, Texture>();

  		vsDialogue = new VersusDialogue();
  		stage.addActor(vsDialogue);
        
  		BASENODE = new JSActor();
  		stage.addActor(BASENODE);
  		
  		BASENODE.setPosition(-300, 200);
  		

  		
  		touchToContinueText = new JSFont("Fight!");
  		touchToContinueText.setPosition(50,-150);
  		//BASENODE.addActor(touchToContinueText);
  		
  		
  		//BATTLE SCREEN BASE
  		battleBASE = new JSActor();
  		battleBASE.setVisible(false);
  		BASENODE.addActor(battleBASE);
  		
  		battleBackground = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("Battle Back"),0,0,1280,720));
  		battleBackground.setPosition(-340, -560);
  		battleBASE.addActor(battleBackground);
  		
  		
  		//BATTLE SCENE
  		battleScene = new JSActor();
  		battleBASE.addActor(battleScene);
  		
  		JSAnimation enemyAnimation = new JSAnimation("LookingEvil", GameManager.getAssetsManager().getTexture("Enemy_Eye_Front"), 300, 200, 10, 100);
  		battleEnemy = new JSAnimatedActor(enemyAnimation);
  		battleEnemy.setPosition(125,-250);
  		battleEnemy.setScale(2);
  		battleScene.addActor(battleEnemy);
  		
  		
  		
  		//BATTLE BOTTOM TEXT
  		battleBottom = new JSActor();
  		battleBottom.setPosition(-300, -350);
  		battleBASE.addActor(battleBottom);
  		
  		battleBottomBack = new JSActor(new TextureRegion(GameManager.getAssetsManager().getTexture("QBox"),0,0,1280,250));
  		battleBottomBack.setPosition(-30, -200);
  		battleBottom.addActor(battleBottomBack);

  		a1Text = new JSFont("A: ");
  		a1Text.setPosition(10, 0);
        
  		a2Text = new JSFont("B: ");
  		a2Text.setPosition(10, -50);

        a3Text = new JSFont("C: ");
  		a3Text.setPosition(10, -100);

        
  		battleBottom.addActor(a1Text);
  		battleBottom.addActor(a2Text);
  		battleBottom.addActor(a3Text);
  		
  		
	   	JSAnimation kikuAnimation = new JSAnimation("Jumping", GameManager.getAssetsManager().getTexture("Kiku_Jump"), 200, 200, 10, 200);
  		battleKiku = new JSAnimatedActor(kikuAnimation);
  		battleKiku.setPosition(-250,-350);
  		battleKiku.setScale(2);
  		battleScene.addActor(battleKiku);
  		
  		//BUTTONS
  		dialogue.setVisible(false);
  		dialogue.setPosition(855, -200);
  		BASENODE.addActor(dialogue);

  		
  		
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
