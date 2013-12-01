package com.dbraillon.blitzball.graphics;

import java.util.EnumMap;
import java.util.Map;
import java.util.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public abstract class Screen implements IScreen {

	protected Map<DataZ, Vector<Renderable>> map;
	
	public Screen() {
		
		map = new EnumMap<DataZ, Vector<Renderable>>(DataZ.class);
		
		for(DataZ dataZ : DataZ.values()) {
			
			map.put(dataZ, new Vector<Renderable>());
		}
	}
	
	public void init(GameContainer gameContainer) {
		
		onInit();
		
		for(Vector<Renderable> renderables : map.values()) {
			
			for(Renderable renderable : renderables) {
				
				renderable.init(gameContainer);
			}
		}
	}
	
	public void render(GameContainer gameContainer, Graphics graphics) {
	
		for(Vector<Renderable> renderables : map.values()) {
			
			for(Renderable renderable : renderables) {
				
				renderable.render(gameContainer, graphics);
			}
		}
	}
	
	public Screen update(GameContainer gameContainer, double frameTimeModifier) {
		
		for(Vector<Renderable> renderables : map.values()) {
			
			for(Renderable renderable : renderables) {
				
				renderable.update(gameContainer);
			}
		}
		
		return onUpdate(gameContainer, frameTimeModifier);
	}
	
	public void addItem(Renderable item) {
		
		map.get(item.getDataZ()).add(item);
	}
	
	public void removeItem(Renderable item) {
		
		map.get(item.getDataZ()).remove(item);
	}
}
