package com.omg.spriter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a spriter object with all its object parts and animations.
 * This object can be importer with the SpriterImporter and afterwards manipulated manually or constructed on its own.
 * Additionally its possible to exchange the texture pathes on the fly.
 * For example to switch the weapon that is displayed.
 * This class does not hold disposable objects i.e. textures.
 * For Drawing the Object, an instance of an SpriterDrawer should be used.
 * 
 * @author cfinckle
 */
public class SpriterObject {
	public String basePath;
	
	public TextureProvider textureProvider;
	
  private Map<String, SpriterObjectPart> objectParts;
  private Map<String, SpriterAnimation> animations;
  
  public SpriterObject(){
    this.objectParts = new HashMap<String, SpriterObjectPart>();
    this.animations = new HashMap<String, SpriterAnimation>();
  }
  public Map<String, SpriterObjectPart> getObjectParts() {
    return Collections.unmodifiableMap(objectParts);
  }
  public void addObjectPart(SpriterObjectPart objectPart) {
    this.objectParts.put(objectPart.textureName, objectPart);
  }
  
  public Map<String, SpriterAnimation> getAnimations() {
    return Collections.unmodifiableMap(animations);
  }
  public void addAnimation(SpriterAnimation animation) {
    this.animations.put(animation.name, animation);
  }
  
  public void getAllTextures(){
    for (SpriterObjectPart spriterObjectPart : objectParts.values()) {
      textureProvider.getTexture(this.basePath + spriterObjectPart.textureName);
    }
  }
  
  public void disposeAllTextures(){
    for (SpriterObjectPart spriterObjectPart : objectParts.values()) {
      textureProvider.disposeTexture(this.basePath + spriterObjectPart.textureName);
    }
  }
  
  @Override
  public String toString() {
    return "SpriterObject [objectParts=" + objectParts + ", animations=" + animations + "]";
  }
  
  
}
