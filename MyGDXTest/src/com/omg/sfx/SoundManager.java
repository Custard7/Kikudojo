package com.omg.sfx;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.omg.sfx.LRUCache.CacheEntryRemovedListener;

/**
 * A service that manages the sound effects.
 */
public class SoundManager
    implements
        CacheEntryRemovedListener<LucidSound,Sound>,
        Disposable
{
    
    
    public LucidSound JUMP() {
    	return new LucidSound("sfx/gold_collect.ogg");
    }
    public LucidSound QUESTION() {
    	return new LucidSound("questions/mayo.wav");
    }
    
    HashMap<String, LucidSound> soundsLoaded;

    /**
     * The volume to be set on the sound.
     */
    private float volume = 1f;

    /**
     * Whether the sound is enabled.
     */
    private boolean enabled = true;

    /**
     * The sound cache.
     */
    private final LRUCache<LucidSound,Sound> soundCache;

    static String TAG = "SFX";

    /**
     * Creates the sound manager.
     */
    public SoundManager()
    {
        soundCache = new LRUCache<LucidSound,Sound>( 30 );
        soundCache.setEntryRemovedListener( this );
        
        soundsLoaded = new HashMap<String, LucidSound>();
    }

    /**
     * Plays the specified sound.
     */
    public void play(
        LucidSound sound )
    {
        // check if the sound is enabled
        if( ! enabled ) return;

        // try and get the sound from the cache
        Sound soundToPlay = soundCache.get( sound );
        if( soundToPlay == null ) {
            FileHandle soundFile = Gdx.files.internal( sound.getFileName() );
            soundToPlay = Gdx.audio.newSound( soundFile );
            soundCache.add( sound, soundToPlay );
        }

        // play the sound
        //Gdx.app.log( TAG, "Playing sound: " + sound.name() );
       soundToPlay.play( volume );
        
    }
    public void play(LucidSound sound, int priority) {
    	
    	
    	long result = 0;
    	int abortCount = 100;	
    	do {
    	  

    	
	    	 // check if the sound is enabled
	        if( ! enabled ) return;
	
	        // try and get the sound from the cache
	        Sound soundToPlay = soundCache.get( sound );
	        if( soundToPlay == null ) {
	            FileHandle soundFile = Gdx.files.internal( sound.getFileName() );
	            soundToPlay = Gdx.audio.newSound( soundFile );
	            soundCache.add( sound, soundToPlay );
	        }
	
	        // play the sound
	        //Gdx.app.log( TAG, "Playing sound: " + sound.name() );
	       result = soundToPlay.play( volume );
       
       if(result == -1) {
   		try {
   			Thread.sleep(2);
   		} catch (InterruptedException e) {
   			e.printStackTrace();
   		}
   	  }
   	} while(result == -1 && abortCount-- > 0); 
    	
    }
    
    /**
     * Plays a specified sound by alias
     * @param alias
     */
    public void play(String alias) {
    	play(soundsLoaded.get(alias));
    }
    
    
    /**
     * Caches a specific sound
     */
    public void load(LucidSound sound) {
    	Sound soundToPlay = soundCache.get( sound );
        if( soundToPlay == null ) {
            FileHandle soundFile = Gdx.files.internal( sound.getFileName() );
            soundToPlay = Gdx.audio.newSound( soundFile );
            soundCache.add( sound, soundToPlay );
        }
    }

    /**
     * Caches a specific sound with an alias
     */
    public void load(LucidSound sound, String alias) {
    	load(sound);
    	soundsLoaded.put(alias, sound);
    }
    
    /**
     * Sets the sound volume which must be inside the range [0,1].
     */
    public void setVolume(
        float volume )
    {
        Gdx.app.log( TAG, "Adjusting sound volume to: " + volume );

        // check and set the new volume
        if( volume < 0 || volume > 1f ) {
            throw new IllegalArgumentException( "The volume must be inside the range: [0,1]" );
        }
        this.volume = volume;
    }

    /**
     * Enables or disabled the sound.
     */
    public void setEnabled(
        boolean enabled )
    {
        this.enabled = enabled;
    }

    // EntryRemovedListener implementation

    @Override
    public void notifyEntryRemoved(
        LucidSound key,
        Sound value )
    {
        Gdx.app.log( TAG, "Disposing sound: " + key.name() );
        value.dispose();
    }

    /**
     * Disposes the sound manager.
     */
    public void dispose()
    {
        Gdx.app.log( TAG, "Disposing sound manager" );
        for( Sound sound : soundCache.retrieveAll() ) {
            sound.stop();
            sound.dispose();
        }
    }
}