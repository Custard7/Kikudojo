package com.omg.gdxlucid;

import com.badlogic.gdx.utils.TimeUtils;

public class Timer
{
    private long startTime;

    public Timer()
    {

    }

    public void start()
    {
        startTime = TimeUtils.millis();
        
    }
    
    public void reset(){
        startTime = TimeUtils.millis();
    }

    public int getTime()
    {
        return (int)(TimeUtils.millis()  - startTime);
    }
}