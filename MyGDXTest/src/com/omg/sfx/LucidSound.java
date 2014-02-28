package com.omg.sfx;

/**
 * The available sound files.
 */
public class LucidSound
{
   // JUMP( "sfx/gold_collect.ogg" ),
    //QUESTION( "questions/mayo.wav" );

    private final String fileName;
    
    public LucidSound(
        String fileName )
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
    
    public String name() {
    	return fileName;
    }
    
   
    
}
