package com.dbraillon.blitzball.graphics.screens;

import java.util.concurrent.Callable;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.dbraillon.blitzball.graphics.DataZ;
import com.dbraillon.blitzball.graphics.Screen;
import com.dbraillon.blitzball.graphics.componants.ButtonRenderItem;
import com.dbraillon.blitzball.graphics.componants.StringRenderItem;
import com.dbraillon.blitzball.tools.Point;

public class MenuScreen extends Screen {

	private Screen nextScreen;
	
	@Override
	public void onInit() {
		
		nextScreen = this;
		
		// Componants
		addItem(new StringRenderItem(new Point(100, 100), DataZ.Middle, new Color(255, 255, 255), "Blitzball"));
		addItem(new ButtonRenderItem(new Point(100, 500), DataZ.Middle, new Color(255, 255, 255), "Quick Match", new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				
				nextScreen = new MatchScreen();
				return null;
			}
		}));
		addItem(new ButtonRenderItem(new Point(100, 520), DataZ.Middle, new Color(255, 255, 255), "Quit", new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				
				nextScreen = null;
				return null;
			}
		}));
	}
	
	@Override
	public void onRender(Graphics graphics) {
		
		
	}

	@Override
	public Screen onUpdate(GameContainer gameContainer, double frameTimeModifier) {
		
		return nextScreen;
	}
}
