package com.dbraillon.blitzball.graphics.screens;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.dbraillon.blitzball.graphics.DataZ;
import com.dbraillon.blitzball.graphics.Screen;
import com.dbraillon.blitzball.models.Match;
import com.dbraillon.blitzball.tools.Point;

public class MatchScreen extends Screen {

	private Match match;
	
	@Override
	public void onInit() {
		
		match = new Match(new Point(0, 0), DataZ.Far, new Color(0, 0, 0));
	}
	
	@Override
	public void onRender(Graphics graphics) {
		
		
	}
	
	@Override
	public Screen onUpdate(GameContainer gameContainer, double frameTimeModifier) {
		
		if(gameContainer.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			
			return new MenuScreen();
		}
		
		return this;
	}
}
