package com.dbraillon.blitzball;

import org.newdawn.slick.SlickException;

import com.dbraillon.blitzball.graphics.screens.MenuScreen;
import com.dbraillon.dbgraphics.Screen;
import com.dbraillon.dbgraphics.UiApplication;

public class Game extends UiApplication {

	public static void main(String[] args) throws SlickException {
		
		new Game(Constants.windowsHeight, Constants.windowsWidth, Constants.windowsName,
				 new MenuScreen(), Constants.windowsFrameRate);
	}
	
	public Game(int height, int width, String title, Screen firstScreen, int frameRate) throws SlickException {
		super(height, width, title, firstScreen, frameRate);
		
	}
}
