package com.omg.sfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

/**
 * A service that manages the background music.
 * <p>
 * Only one music may be playing at a given time.
 */
public class MusicManager
    implements
        Disposable
{
    /**
     * The available music files.
     */
    public enum LucidMusic
    {
        SWINDLER( "music/swindler_audio2.ogg" ),
        MENU_MUSIC( "music/menurepeat.ogg" ),
        MENU_OUTRO( "music/outro.ogg" ),
        GAME_MUSIC( "music/technorepeatable.ogg" ),
        INTRO_MUSIC("music/intro_music.ogg");
        

        private final String fileName;

        private LucidMusic(
            String fileName )
        {
            this.fileName = fileName;
        }

        public String getFileName()
        {
            return fileName;
        }
    }

    /**
     * Holds the music currently being played, if any.
     */
    private Music musicBeingPlayed;

    /**
     * The volume to be set on the music.
     */
    private float volume = 1f;

    /**
     * Whether the music is enabled.
     */
    private boolean enabled = true;

    static String TAG = "MUSIC";
    
    /**
     * Creates the music manager.
     */
    public MusicManager()
    {
    }

    /**
     * Plays the given music (starts the streaming).
     * <p>
     * If there is already a music being played it is stopped automatically.
     */
    public void play(
        LucidMusic music )
    {
    	play(music, true);
    }
    
    public void play(LucidMusic music, boolean loop) {
    	play(music.getFileName(), loop);
    }
    
    public void play(String fileName, boolean loop) {
    	// check if the music is enabled
        if( ! enabled ) return;

        // stop any music being played
        Gdx.app.log( TAG, "Playing music: " + fileName );
        stop();

        // start streaming the new music
        FileHandle musicFile = Gdx.files.internal( fileName );
        musicBeingPlayed = Gdx.audio.newMusic( musicFile );
        musicBeingPlayed.setVolume( volume );
        musicBeingPlayed.setLooping( true );
        musicBeingPlayed.play();
    }
    
    public void pause() {
    	if(musicBeingPlayed != null) {
    		musicBeingPlayed.pause();
    	}
    }
    public void resume() {
    	if(musicBeingPlayed != null) {
    		musicBeingPlayed.play();
    	}
    }

    /**
     * Stops and disposes the current music being played, if any.
     */
    public void stop()
    {
        if( musicBeingPlayed != null ) {
            Gdx.app.log( TAG, "Stopping current music" );
            musicBeingPlayed.stop();
            musicBeingPlayed.dispose();
        }
    }
    
    public boolean isPlaying() {
    	if(musicBeingPlayed != null)
    		return musicBeingPlayed.isPlaying();
    	return false;
    }

    /**
     * Sets the music volume which must be inside the range [0,1].
     */
    public void setVolume(
        float volume )
    {
        Gdx.app.log( TAG, "Adjusting music volume to: " + volume );

        // check and set the new volume
        if( volume < 0 || volume > 1f ) {
            throw new IllegalArgumentException( "The volume must be inside the range: [0,1]" );
        }
        this.volume = volume;

        // if there is a music being played, change its volume
        if( musicBeingPlayed != null ) {
            musicBeingPlayed.setVolume( volume );
        }
    }

    /**
     * Enables or disabled the music.
     */
    public void setEnabled(
        boolean enabled )
    {
        this.enabled = enabled;

        // if the music is being deactivated, stop any music being played
        if( ! enabled ) {
            stop();
        }
    }

    /**
     * Disposes the music manager.
     */
    public void dispose()
    {
        Gdx.app.log( TAG, "Disposing music manager" );
        stop();
    }
}