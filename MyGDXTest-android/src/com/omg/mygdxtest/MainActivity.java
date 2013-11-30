package com.omg.mygdxtest;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.omg.sswindler.GameManager;
import com.testflightapp.lib.TestFlight;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        
      //Initialize TestFlight with your app token.
        TestFlight.takeOff(this.getApplication(), "0a3e694e-8454-4b6b-a810-43280cc01d68");
        
        initialize(new GameManager(), cfg);
    }
}