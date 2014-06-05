package com.omg.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.omg.drawing.JSActor;
import com.omg.drawing.JSFont;
import com.omg.events.DialogueListener;
import com.omg.filemanagement.QRBlock;
import com.omg.filemanagement.QRSet.QROptions;
import com.omg.screens.VersusScreen;
import com.omg.ssworld.WorldManager;


public class ABCDDialogue extends Table {
	private int customSpeed = 0;


	private int x;
	private int y;
	private int width;
	private int height;
	
	VersusScreen vsScreen;

	
	public boolean isActive;
	
	TextureRegion upRegion;
	TextureRegion downRegion;
	BitmapFont buttonFont;
	
	
	DialogueListener listener;
	
	public QRBlock qrBlock;
	
	TextButton A;
	TextButton B;
	TextButton C;
	

	public void setVSScreen(VersusScreen screen) {
		vsScreen = screen;
	}
	
	

	public QROptions getAnswer()
	{
		return qrBlock.getCorrect();
	}
	

	
	
	public ABCDDialogue(QRBlock block) {
				
		isActive = true;
		setFillParent(true);
		
		
		buttonFont = JSFont.loadFont("ktegaki");

		
		Skin skin = new Skin();
		skin.add("up", new Texture("data/ui/button_up.png"));
		skin.add("down", new Texture("data/ui/button_down.png"));
		
		
		
		Skin skinA = new Skin();
		skinA.add("up", new Texture("data/ui/a_button.png"));
		skinA.add("down", new Texture("data/ui/a_button_down.png"));
		
		TextureRegion upRegionA = skinA.getRegion("up");
		TextureRegion downRegionA = skinA.getRegion("down");

		TextButtonStyle styleA = new TextButtonStyle();
		styleA.up = new TextureRegionDrawable(upRegionA);
		styleA.down = new TextureRegionDrawable(downRegionA);
		styleA.font = buttonFont;
		
		
		
		
		Skin skinB = new Skin();
		skinB.add("up", new Texture("data/ui/b_button.png"));
		skinB.add("down", new Texture("data/ui/b_button_down.png"));
		
		TextureRegion upRegionB = skinB.getRegion("up");
		TextureRegion downRegionB = skinB.getRegion("down");

		TextButtonStyle styleB = new TextButtonStyle();
		styleB.up = new TextureRegionDrawable(upRegionB);
		styleB.down = new TextureRegionDrawable(downRegionB);
		styleB.font = buttonFont;
		
		
		
		Skin skinC = new Skin();
		skinC.add("up", new Texture("data/ui/c_button.png"));
		skinC.add("down", new Texture("data/ui/c_button_down.png"));

		TextureRegion upRegionC = skinC.getRegion("up");
		TextureRegion downRegionC = skinC.getRegion("down");

		TextButtonStyle styleC = new TextButtonStyle();
		styleC.up = new TextureRegionDrawable(upRegionC);
		styleC.down = new TextureRegionDrawable(downRegionC);
		styleC.font = buttonFont;
		

		upRegion = skin.getRegion("up");
		downRegion = skin.getRegion("down");
		//buttonFont = JSFont.loadFont("arialblack");
		
		

		TextButtonStyle style = new TextButtonStyle();
		style.up = new TextureRegionDrawable(upRegion);
		style.down = new TextureRegionDrawable(downRegion);
		style.font = buttonFont;

		A = new TextButton("", styleA);
		A.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Changed!");
				Gdx.app.debug("Dialogue", "A Pressed");
				if (listener != null)
		            listener.onSelected(QROptions.A, getAnswer(), isRightAnswer(QROptions.A));
				randomizeAnswer();
				 playClickSound();
				
			}
		});
		A.align(Align.right);
		add(A);
		row();
		
		B = new TextButton("", styleB);
		B.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Changed!");
				Gdx.app.debug("Dialogue", "B Pressed");
				if (listener != null)
		            listener.onSelected(QROptions.B, getAnswer(), isRightAnswer(QROptions.B));
				randomizeAnswer();
				 playClickSound();

			}
		});
		B.align(Align.right);
		add(B);
		row();
		
		C = new TextButton("", styleC);
		C.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Changed!");
				Gdx.app.debug("Dialogue", "C Pressed");
				if (listener != null)
		            listener.onSelected(QROptions.C, getAnswer(), isRightAnswer(QROptions.C));
				randomizeAnswer();
				 playClickSound();


			}
		});
		C.align(Align.right);
		add(C);
		row();
		
		setQRBlock(block);

	}
	

	
	public void setWorldBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		
		
	}
	
	public void setWorldBounds(WorldManager world) {
		setWorldBounds(world.getWorldX(), world.getWorldY(), world.getWorldWidth(), world.getWorldHeight());
		this.setX(x + width/4);
		this.setY(y + height/4);
	}
	
	public void setListener(DialogueListener listener) {
        this.listener = listener;
    }	
	
	
	public boolean isRightAnswer(QROptions given) {
		
		if(given == qrBlock.getCorrect())
			return true;
		return false;
		
	}
	
	public void playClickSound() {
		if(vsScreen != null) {
			vsScreen.getSoundManager().play("Click");
		}
	}
	
	
	public void randomizeAnswer() {
		/*switch(correctAnswer) {
		case A:
			correctAnswer = Answer.B;
			break;
		case B:
			correctAnswer = Answer.C;

			break;
		case C:
			correctAnswer = Answer.D;

			break;
		case D:
			correctAnswer = Answer.A;

			break;
			
		}*/
		
	}
	
	
	public void setQRBlock(QRBlock block) {
		this.qrBlock = block;
		
		//A.setText("A: " + qrBlock.getA());
		//B.setText("B: " + qrBlock.getB());
		//C.setText("C: " + qrBlock.getC());
		
		
		//this.setScale(1.5f);
		//this.setZIndex(2000);
		//this.clear();
		
				

		
	}
	public QRBlock getQRBlock() {
		return this.qrBlock;
	}
	
	
	

}
