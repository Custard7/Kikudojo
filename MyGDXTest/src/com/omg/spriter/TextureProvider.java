package com.omg.spriter;

import com.badlogic.gdx.graphics.Texture;

/**
 * Interface required from the SpriterDrawer to get Texture objects for the given Filenames. The
 * Implementer can implement its own texture caching. This class does not handle the texture
 * disposal.
 * 
 * @author cfinckle
 */
public interface TextureProvider {

  public Texture getTexture(String filename);
  
  public void disposeTexture(String filename);

}
