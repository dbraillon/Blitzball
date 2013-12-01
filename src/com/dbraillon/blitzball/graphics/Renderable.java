package com.dbraillon.blitzball.graphics;

import org.newdawn.slick.Color;

import com.dbraillon.blitzball.tools.Point;

public abstract class Renderable implements IRenderable  {
	
	// position in windows
	protected Point dataPosition;
	
	// position in z
	protected DataZ dataZ;
	
	// color of data
	protected Color dataColor;
	
	// size
	protected double height, width;
	
	
	public Renderable(Point position, DataZ z, Color color) {
		
		dataPosition = position;
		dataZ = z;
		dataColor = color;
	}

	public DataZ getDataZ() {
		return dataZ;
	}
}
