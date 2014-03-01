package com.dbraillon.blitzball.graphics.screens;

import java.util.concurrent.Callable;

import org.newdawn.slick.Color;

import com.dbraillon.dbgraphics.Depth;
import com.dbraillon.dbgraphics.Point;
import com.dbraillon.dbgraphics.Screen;
import com.dbraillon.dbgraphics.componants.ButtonRenderItem;
import com.dbraillon.dbgraphics.componants.StringRenderItem;

public class MenuScreen extends Screen {
	
	public MenuScreen() {
		super();
		
		addItem(new StringRenderItem(new Point(100, 100), "dBlitzball"));
		addItem(new ButtonRenderItem(new Point(100, 650), Depth.Middle, new Color(255, 255, 255), "Quick Match", new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				
				getNavigator().pushScreen(new MatchScreen());
				return null;
			}
		}));
		addItem(new ButtonRenderItem(new Point(100, 670), Depth.Middle, new Color(255, 255, 255), "Quit", new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				
				getNavigator().popScreen(MenuScreen.this);
				return null;
			}
		}));
	}
}
