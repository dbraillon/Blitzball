package com.dbraillon.blitzball.graphics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


/**
 * Order calls: init(), render(), update()
 * @author d.braillon
 */
public interface IScreen {

	public void onInit();
	public void onRender(Graphics graphics);
	
	/**
	 * Remember, update come AFTER render.
	 * @param frameTimeModifier
	 * @return Next screen to display, set to 'this' if you want this screen continue to be displayed.
	 */
	public Screen onUpdate(GameContainer gameContainer, double frameTimeModifier);
}
