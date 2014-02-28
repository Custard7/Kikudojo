package com.omg.filemanagement;

import com.badlogic.gdx.utils.Array;

public class QRSet {

	String title;
	double version;
	
	
	Array<QRBlock> qrblocks;
	
	public enum QROptions {
		A,
		B,
		C
	}

	
	
	public QRSet() {
		
		qrblocks = new Array<QRBlock>();
		
	}
	
	public void setTitleAndVersions(String title, String version) {
		
		this.title = title;
		this.version = Double.parseDouble(version);
		
	}
	
	public void addQRBlock(QRBlock block) {
		
		
		qrblocks.add(block);
	}
	
	public void finalize() {
		
		for(QRBlock block : qrblocks) {
			
			block.loadSound(title);
			
			
		}
		
		
	}
	
	public QRBlock getRandomBlock() {
		return qrblocks.random();
		
		
	}
	
	
	
	
}
