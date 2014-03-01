package com.dbraillon.blitzball.graphics.components;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.TrueTypeFont;

import com.dbraillon.dbgraphics.Depth;
import com.dbraillon.dbgraphics.Point;
import com.dbraillon.dbgraphics.Renderable;
import com.dbraillon.dbgraphics.componants.StringRenderItem;

public class InfoPanel extends Renderable {

	private StringRenderItem pBallNameStringRenderItem;
	private StringRenderItem pBallStatsHeaderStringRenderItem;
	private StringRenderItem pBallStatsValueStringRenderItem;
	
	private StringRenderItem pCounterNameStringRenderItem;
	private StringRenderItem pCounterStatsHeaderStringRenderItem;
	private StringRenderItem pCounterStatsValueStringRenderItem;
	
	private FifoList fifoList;
	
	private String pBallNameString;
	private String pBallStatsHeaderString;
	private String pBallStatsValueString;
	
	private String pCounterNameString;
	private String pCounterStatsHeaderString;
	private String pCounterStatsValueString;
	
	public InfoPanel(Point position, Depth depth, Color color, float height, float width) {
		super(position, depth, color, height, width);
		
		TrueTypeFont ttf12 = new TrueTypeFont(new Font("Consolas", Font.BOLD, 12), true);
		
		pBallNameStringRenderItem = new StringRenderItem(new Point(20 + position.getX(), 40 + position.getY()), pBallNameString, ttf12);
		pBallStatsHeaderStringRenderItem = new StringRenderItem(new Point(20 + position.getX(), 60 + position.getY()), pBallStatsHeaderString, ttf12);
		pBallStatsValueStringRenderItem = new StringRenderItem(new Point(20 + position.getX(), 80 + position.getY()), pBallStatsValueString, ttf12);
		
		pCounterNameStringRenderItem = new StringRenderItem(new Point(20 + position.getX(), 120 + position.getY()), pCounterNameString, ttf12);
		pCounterStatsHeaderStringRenderItem = new StringRenderItem(new Point(20 + position.getX(), 140 + position.getY()), pCounterStatsHeaderString, ttf12);
		pCounterStatsValueStringRenderItem = new StringRenderItem(new Point(20 + position.getX(), 160 + position.getY()), pCounterStatsValueString, ttf12);
		
		fifoList = new FifoList(new Point(20 + position.getX(), 200 + position.getY()), Depth.Middle, null, 500, 260);
	}
	
	@Override
	public void render(GameContainer gameContainer) {
		
		pBallNameStringRenderItem.setValue("pBall: " + pBallNameString);
		pBallNameStringRenderItem.render(gameContainer);
		pBallStatsHeaderStringRenderItem.setValue(pBallStatsHeaderString);
		pBallStatsHeaderStringRenderItem.render(gameContainer);
		pBallStatsValueStringRenderItem.setValue(pBallStatsValueString);
		pBallStatsValueStringRenderItem.render(gameContainer);
		
		pCounterNameStringRenderItem.setValue("pCounter: " + pCounterNameString);
		pCounterNameStringRenderItem.render(gameContainer);
		pCounterStatsHeaderStringRenderItem.setValue(pCounterStatsHeaderString);
		pCounterStatsHeaderStringRenderItem.render(gameContainer);
		pCounterStatsValueStringRenderItem.setValue(pCounterStatsValueString);
		pCounterStatsValueStringRenderItem.render(gameContainer);
		
		fifoList.render(gameContainer);
	}
	

	public String getpBallNameString() {
		return pBallNameString;
	}

	public void setpBallNameString(String pBallNameString) {
		this.pBallNameString = pBallNameString;
	}

	public String getpBallStatsHeaderString() {
		return pBallStatsHeaderString;
	}

	public void setpBallStatsHeaderString(String pBallStatsHeaderString) {
		this.pBallStatsHeaderString = pBallStatsHeaderString;
	}

	public String getpBallStatsValueString() {
		return pBallStatsValueString;
	}

	public void setpBallStatsValueString(String pBallStatsValueString) {
		this.pBallStatsValueString = pBallStatsValueString;
	}

	public String getpCounterNameString() {
		return pCounterNameString;
	}

	public void setpCounterNameString(String pCounterNameString) {
		this.pCounterNameString = pCounterNameString;
	}

	public String getpCounterStatsHeaderString() {
		return pCounterStatsHeaderString;
	}

	public void setpCounterStatsHeaderString(String pCounterStatsHeaderString) {
		this.pCounterStatsHeaderString = pCounterStatsHeaderString;
	}

	public String getpCounterStatsValueString() {
		return pCounterStatsValueString;
	}

	public void setpCounterStatsValueString(String pCounterStatsValueString) {
		this.pCounterStatsValueString = pCounterStatsValueString;
	}
	
	public void addLine(String line) {
		
		fifoList.addLine(line);
	}
}
