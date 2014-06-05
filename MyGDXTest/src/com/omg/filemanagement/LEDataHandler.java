package com.omg.filemanagement;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.omg.sfx.LucidSound;


public class LEDataHandler {

	
	
	Array<QRSet> qrsets;
	
	public LEDataHandler() {
		
		
		qrsets = new Array<QRSet>();
		
	}
	/*
	 * <items>
			<item id="1" name="sword">
				<description text="Just a regular sword."/>
				<wieldable slot="HAND"/>
				<weapon damage = "3"/>
			</item>
		</items>
	 */
	
	
	public void loadQR(String xmlPath) {
	
		try {
		QRSet newSet = new QRSet();
		
	
		XmlReader reader = new XmlReader();
		
		Element qrset;
			qrset = reader.parse(Gdx.app.getFiles().internal("questions/"+ xmlPath + "/" + xmlPath + ".xml"));
		
		
		String title = qrset.getAttribute("title");
		String version = qrset.getAttribute("version");
		
		newSet.setTitleAndVersions(title, version);
		
		Array<Element> qrblocks = qrset.getChildrenByName("qrblock");
		for (Element qrblock : qrblocks)
		{
			System.out.println("New Block ---- " + title);
			QRBlock newBlock = new QRBlock();
			
			
		  //  String description = child.getChildByName("question").getAttribute("text");
			String soundPath = qrblock.getAttribute("sound");
			System.out.println("Sound:" + soundPath);

			String question = qrblock.getChildByName("question").getText();
			String A = qrblock.getChildByName("a").getText();
		    String B = qrblock.getChildByName("b").getText();
		    String C = qrblock.getChildByName("c").getText();
		    String correct = qrblock.getChildByName("answer").getText();
		    String hint = qrblock.getChildByName("hint").getText();

		    //Load block with new data

		    newBlock.loadBlock(soundPath, question, A, B, C, correct, hint);
			System.out.println("Status: loaded");
		    newSet.addQRBlock(newBlock);
			System.out.println("Block Created ---- ");

		
		}
		
		
		
		
		newSet.finalize();
		addQRSet(newSet);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public QRBlock getRandomBlock() {
		
		return qrsets.random().getRandomBlock();
		
	}
	
	public void addQRSet(QRSet set) {
		qrsets.add(set);
	}
	
	
	public Array<LucidSound> getSounds() {
		
		Array<LucidSound> sounds = new Array<LucidSound>();
		for(QRSet set : qrsets) {
			for(QRBlock b : set.qrblocks) {
				sounds.add(b.getQuestionSound());
			}
		}
		
		return sounds;
		
	}
	
	
	
	
}
