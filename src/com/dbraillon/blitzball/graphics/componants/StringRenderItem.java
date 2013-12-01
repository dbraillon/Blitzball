package com.dbraillon.blitzball.graphics.componants;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.dbraillon.blitzball.graphics.DataZ;
import com.dbraillon.blitzball.graphics.Renderable;
import com.dbraillon.blitzball.tools.Point;

public class StringRenderItem extends Renderable {

	private String string;
	
	public StringRenderItem(Point position, DataZ z, Color color, String data) {
		super(position, z, color);
		
		setString(data);
	}

	@Override
	public void init(GameContainer gameContainer) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) {
		
		height = graphics.getFont().getHeight(string);
		width = graphics.getFont().getWidth(string);
		
		graphics.setColor(dataColor);
		graphics.drawString(string, (float)dataPosition.getX(), (float)dataPosition.getY());
	}
	
	@Override
	public void update(GameContainer gameContainer) {
		
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		
		this.string = string;
	}
}
