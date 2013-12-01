package com.omg.spriter.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.omg.spriter.SpriterAnimation;
import com.omg.spriter.SpriterAnimationFrame;
import com.omg.spriter.SpriterFrame;
import com.omg.spriter.SpriterObject;
import com.omg.spriter.SpriterObjectPart;
import com.omg.spriter.SpriterSprite;
import com.omg.spriter.TextureProvider;

public class SpriterImporter {

	public static SpriterObject importFile(FileHandle file, TextureProvider textureProvider) throws IOException {

		XmlReader xmlReader = new XmlReader();
		Element rootElement = xmlReader.parse(file);
		SpriterObject spriterObject = new SpriterObject();
		spriterObject.textureProvider = textureProvider;
		spriterObject.basePath = file.path().replace(file.name(), "");
		// import the objectParts with the hotspots
		Element xmlHotSpotArray = rootElement.getChildByName("hotspotarray");
		if (xmlHotSpotArray != null) {
			Array<Element> xmlHotSpots = xmlHotSpotArray.getChildrenByName("hotspot");
			for (int i = 0; i < xmlHotSpots.size; i++) {
				Element xmlHotSpot = xmlHotSpots.get(i);
				SpriterObjectPart objectPart = new SpriterObjectPart();
				objectPart.textureName = xmlHotSpot.getChildByName("filepath").getText();
				objectPart.originX = Float.valueOf(xmlHotSpot.getChildByName("x").getText());
				objectPart.originY = Float.valueOf(xmlHotSpot.getChildByName("y").getText());

				spriterObject.addObjectPart(objectPart);
			}
		}
		// Import the frames
		Map<String, SpriterFrame> frames = new HashMap<String, SpriterFrame>();
		Array<Element> xmlFrames = rootElement.getChildrenByName("frame");
		for (int i = 0; i < xmlFrames.size; i++) {
			Element xmlFrame = xmlFrames.get(i);
			SpriterFrame frame = new SpriterFrame();
			frame.name = xmlFrame.getChildByName("name").getText();

			Array<Element> xmlSprites = xmlFrame.getChildrenByName("sprite");
			for (int j = 0; j < xmlSprites.size; j++) {
				Element xmlSprite = xmlSprites.get(j);
				SpriterSprite sprite = new SpriterSprite();
				sprite.objectPart = spriterObject.getObjectParts().get(xmlSprite.getChildByName("image").getText());
				if (sprite.objectPart == null) {// create new object part
					SpriterObjectPart objectPart = new SpriterObjectPart();
					objectPart.textureName = xmlSprite.getChildByName("image").getText();
					sprite.objectPart = objectPart;
					spriterObject.addObjectPart(objectPart);
				}
				setColorOnSprite(sprite, Integer.valueOf(xmlSprite.getChildByName("color").getText()));
				sprite.opacity = Float.valueOf(xmlSprite.getChildByName("opacity").getText())/100;
				sprite.angle = Float.valueOf(xmlSprite.getChildByName("angle").getText()) % 360;
				if (sprite.angle < 0) {
					sprite.angle += 360;
				}
				sprite.x = Float.valueOf(xmlSprite.getChildByName("x").getText());
				sprite.y = Float.valueOf(xmlSprite.getChildByName("y").getText());
				sprite.width = Float.valueOf(xmlSprite.getChildByName("width").getText());
				sprite.height = Float.valueOf(xmlSprite.getChildByName("height").getText());
				
				frame.addSprite(sprite);
			}
			frames.put(frame.name, frame);
		}
		// import the animations
		Array<Element> xmlAnims = rootElement.getChildByName("char").getChildrenByName("anim");
		for (int i = 0; i < xmlAnims.size; i++) {
			Element xmlAnim = xmlAnims.get(i);
			SpriterAnimation animation = new SpriterAnimation();
			animation.name = xmlAnim.getChildByName("name").getText();
			xmlFrames = xmlAnim.getChildrenByName("frame");
			float animationLength = 0;
			for (int j = 0; j < xmlFrames.size; j++) {
				Element xmlFrame = xmlFrames.get(j);
				SpriterAnimationFrame animationFrame = new SpriterAnimationFrame();
				animationFrame.duration = Float.valueOf(xmlFrame.getChildByName("duration").getText());
				animationLength += animationFrame.duration;
				String text = xmlFrame.getChildByName("name").getText();
				animationFrame.frame = frames.get(text);
				animation.addFrame(animationFrame);
			}
			animation.animationLength = animationLength;

			spriterObject.addAnimation(animation);
		}

		System.out.println(spriterObject);
		return spriterObject;
	}
	
	private static void setColorOnSprite(SpriterSprite sprite, int colorIntBits){
    int B_MASK = 255;
    int G_MASK = 255<<8;
    int R_MASK = 255<<16;
    
    int r = (colorIntBits & R_MASK)>>16;
    int g = (colorIntBits & G_MASK)>>8;
    int b = colorIntBits & B_MASK;
    
    sprite.colorRed = r/256f;
    sprite.colorGreen = g/256f;
    sprite.colorBlue = b/256f;
  }
}
