package com.omg.ssworld.background;

import com.omg.ssworld.Background;

public  class BProperties {
	
	String fileName;
	int customSpeed;
	float scale;
	int yoffset;
	
	int width;
	int height;
	
	
	public String getFileName() {
		return fileName;
	}
	
	public int getCustomSpeed() {
		return customSpeed;
	}
	
	public float getScale() {
		return scale;
	}
	
	public int getYOffset() {
		return yoffset;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public BProperties() {
		
	}
	
	public static BProperties makeProperties(String fileName, int customSpeed, int yOffset) {
		BProperties p = new BProperties();
		p.width = 1280/4;
		p.height = 931/4;
		p.customSpeed = customSpeed;
		p.yoffset = yOffset;
		p.fileName = fileName;
		p.scale = 1.5f *4;
		
		return p;
		
	}
	
	
	
}
