package com.omg.events;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.omg.filemanagement.QRSet.QROptions;



public interface DialogueListener extends EventListener {

	void onSelected(QROptions given, QROptions correct, boolean isRight);
	
}
