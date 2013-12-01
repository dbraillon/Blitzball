package com.dbraillon.blitzball.graphics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface IRenderable {

	public void init(GameContainer gameContainer);
	public void render(GameContainer gameContainer, Graphics graphics);
	public void update(GameContainer gameContainer);
}
