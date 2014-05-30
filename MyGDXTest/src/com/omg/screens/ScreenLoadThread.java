package com.omg.screens;

import com.badlogic.gdx.Gdx;

public class ScreenLoadThread implements Runnable {

	Loadable screenToLoad;
	
	public ScreenLoadThread(Loadable toLoad) {
		screenToLoad = toLoad;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		screenToLoad.load();
	
	}

	
}
