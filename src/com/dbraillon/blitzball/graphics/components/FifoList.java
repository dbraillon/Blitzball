package com.dbraillon.blitzball.graphics.components;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.TrueTypeFont;

import com.dbraillon.dbgraphics.Depth;
import com.dbraillon.dbgraphics.Point;
import com.dbraillon.dbgraphics.Renderable;

public class FifoList extends Renderable {

	private ArrayList<String> lines;
	private int maxLineCount;
	private TrueTypeFont ttf12;
	
	protected FifoList(Point position, Depth depth, Color color, float height, float width) {
		super(position, depth, color, height, width);
		
		ttf12 = new TrueTypeFont(new Font("Consolas", Font.BOLD, 12), true);
		maxLineCount = (int) ((height - 20) / 20);
		lines = new ArrayList<String>();
	}
	
	@Override
	public void render(GameContainer gameContainer) {
		
		int index = 1;
		for(String line : lines) {
			
			gameContainer.getGraphics().setFont(ttf12);
			gameContainer.getGraphics().drawString(line, getPosition().getX(), getPosition().getY() + getHeight() - (20*index));
			
			index++;
		}
	}
	
	public void addLine(String line) {
		
		if(lines.size() >= maxLineCount)
			lines.remove(lines.size()-1);
		
		lines.add(0, line);
	}
}
