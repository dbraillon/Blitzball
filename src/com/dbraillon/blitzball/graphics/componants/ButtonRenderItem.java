package com.dbraillon.blitzball.graphics.componants;

import java.util.concurrent.Callable;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

import com.dbraillon.blitzball.graphics.DataZ;
import com.dbraillon.blitzball.graphics.Renderable;
import com.dbraillon.blitzball.tools.Point;

public class ButtonRenderItem extends Renderable {

	private String string;
	private Callable<Void> onClick;
	
	public ButtonRenderItem(Point position, DataZ z, Color color, String label, Callable<Void> onClickMethod) {
		super(position, z, color);
		
		string = label;
		onClick = onClickMethod;
	}
	
	@Override
	public void init(GameContainer gameContainer) {
		
		gameContainer.getInput().addMouseListener(new MouseListener() {
			
			@Override
			public void setInput(Input arg0) { 
				
			}
			
			@Override
			public boolean isAcceptingInput() {
				
				return true;
			}
			
			@Override
			public void inputStarted() {


			}
			
			@Override
			public void inputEnded() {


			}
			
			@Override
			public void mouseWheelMoved(int arg0) {


			}
			
			@Override
			public void mouseReleased(int arg0, int arg1, int arg2) {


			}
			
			@Override
			public void mousePressed(int arg0, int arg1, int arg2) {


			}
			
			@Override
			public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {


			}
			
			@Override
			public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {


			}
			
			@Override
			public void mouseClicked(int arg0, int x, int y, int arg3) {

				if(x > dataPosition.getX() && x < dataPosition.getX() + width
				&& y > dataPosition.getY() && y < dataPosition.getY() + height) {
					
					try {
						onClick.call();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) {
		
		height = graphics.getFont().getHeight(string);
		width = graphics.getFont().getWidth(string);
		
		double xMouse = gameContainer.getInput().getMouseX();
		double yMouse = gameContainer.getInput().getMouseY();
		
		if(xMouse > dataPosition.getX() && xMouse < dataPosition.getX() + width
		&& yMouse > dataPosition.getY() && yMouse < dataPosition.getY() + height) {
			
			graphics.setColor(dataColor.darker());
		}
		else {
			
			graphics.setColor(dataColor);
		}
		
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
