package com.omg.spriter.util;

import java.util.List;



import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.omg.spriter.SpriterAnimation;
import com.omg.spriter.SpriterAnimationFrame;
import com.omg.spriter.SpriterObject;
import com.omg.spriter.SpriterSprite;

public class SpriterDrawer {

  public static void draw(Batch spriteBatch, SpriterObject spriterObject,
      String animationName, float keyTime, float offsetX, float offsetY, boolean repeatAnimation,
      boolean doTweening) {
    SpriterAnimation spriterAnimation = spriterObject.getAnimations().get(animationName);
    if (spriterAnimation == null) {
      throw new IllegalArgumentException("The given animationname:" + animationName
          + " does not exist in the animation");
    }

    if (keyTime > spriterAnimation.animationLength && repeatAnimation) {
      keyTime = keyTime % spriterAnimation.animationLength;
    }

    // calculate current animation and the next frame depending on the
    // current time
    List<SpriterAnimationFrame> frames = spriterAnimation.getFrames();
    SpriterAnimationFrame currentFrame = null;
    SpriterAnimationFrame nextFrame = null;
    float curTime = 0;
    float tweenFactor = 0;
    for (int i = 0; i < frames.size(); i++) {
      SpriterAnimationFrame animationFrame = frames.get(i);

      if (curTime <= keyTime && curTime + animationFrame.duration > keyTime) {
        currentFrame = animationFrame;
        if (i < frames.size() - 1) {
          nextFrame = frames.get(i + 1);
        } else {
          nextFrame = animationFrame;
        }
        tweenFactor = (keyTime - curTime) / (animationFrame.duration);
        break;
      }
      curTime += animationFrame.duration;
    }
    if (currentFrame == null) {
      currentFrame = frames.get(frames.size() - 1);
      nextFrame = frames.get(frames.size() - 1);
    }

    // iterate through all sprites and draw them
    List<SpriterSprite> sprites1 = currentFrame.frame.getSprites();
    for (int i = 0; i < sprites1.size(); i++) {
      SpriterSprite sprite1 = sprites1.get(i);

      final SpriterSprite tweenedSprite = new SpriterSprite();
      // perform tweening, if sprite occurs in next frame too
      SpriterSprite sprite2 = nextFrame.frame.getSpriteByObjectPart(sprite1.objectPart);
      if (!doTweening || sprite2 == null) {// no tweening possible
        tweenedSprite.setValues(sprite1);
      } else {

        tweenedSprite.setTweenedValues(sprite1, sprite2, tweenFactor);
      }

      tweenedSprite.x = tweenedSprite.x /*- sprite1.objectPart.originX*/+ offsetX;
      tweenedSprite.y = -tweenedSprite.y - tweenedSprite.height /*- sprite1.objectPart.originY*/
          + offsetY;

      spriteBatch.setColor(tweenedSprite.colorRed, tweenedSprite.colorGreen,
          tweenedSprite.colorBlue, tweenedSprite.opacity);

      spriteBatch.draw(
          spriterObject.textureProvider.getTexture(spriterObject.basePath
              + tweenedSprite.objectPart.textureName), (int) tweenedSprite.x
              - tweenedSprite.objectPart.originX, (int) tweenedSprite.y
              + tweenedSprite.objectPart.originY, tweenedSprite.objectPart.originX,
          (int) tweenedSprite.height - tweenedSprite.objectPart.originY, (int) tweenedSprite.width,
          tweenedSprite.height, 1, 1, tweenedSprite.angle, 0, 0,
          (int) Math.abs(tweenedSprite.width), (int) Math.abs(tweenedSprite.height),
          tweenedSprite.flipX, tweenedSprite.flipY);

    }
    spriteBatch.setColor(1, 1, 1, 1);
  }

}
