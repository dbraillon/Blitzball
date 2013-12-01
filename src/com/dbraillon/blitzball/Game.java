package com.dbraillon.blitzball;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.dbraillon.blitzball.graphics.Screen;
import com.dbraillon.blitzball.graphics.screens.MenuScreen;

public class Game extends BasicGame {

	public static AppGameContainer app;
	
	public static void main(String[] args) throws SlickException {
		
		app = new AppGameContainer(new Game(Constants.windowsName));
		app.setDisplayMode(Constants.windowsWidth, Constants.windowsHeight, false);
		app.setTargetFrameRate(Constants.windowsFrameRate);
		app.start();
	}
	
	
	private Screen defaultScreen;
	private Screen currentScreen;
	
	public Game(String title) {
		super(title);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {

		defaultScreen = new MenuScreen();
		defaultScreen.init(gameContainer);
		
		currentScreen = defaultScreen;
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

		currentScreen.render(gameContainer, graphics);
	}

	@Override
	public void update(GameContainer gameContainer, int lastFrameTime) throws SlickException {
		
		Screen screen = currentScreen.update(gameContainer, lastFrameTime / Constants.windowsFrameRate);
		
		if(screen == null) {
			
			app.exit();
		}
		else if(screen != currentScreen) {
			
			currentScreen = screen;
			currentScreen.init(gameContainer);
		}
	}
}
