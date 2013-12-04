package com.dbraillon.blitzball.graphics.screens;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

import com.dbraillon.blitzball.enumerations.PlayerPosition;
import com.dbraillon.blitzball.models.Match;
import com.dbraillon.dbgraphics.Point;
import com.dbraillon.dbgraphics.Screen;
import com.dbraillon.dbgraphics.componants.StringRenderItem;

public class MatchScreen extends Screen {
	
	private Match match;
	
	public MatchScreen() {
		super();
		
		this.match = new Match();
		
		addItem(new StringRenderItem(new Point(20, 40), match.getLeftTeam().getName(), new TrueTypeFont(new Font("Consolas", Font.BOLD, 16), true)));
		
		addItem(new StringRenderItem(new Point(20, 120), match.getLeftTeam().getPlayer(PlayerPosition.GL).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 140), match.getLeftTeam().getPlayer(PlayerPosition.GL).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 160), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(20, 200), match.getLeftTeam().getPlayer(PlayerPosition.LD).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 220), match.getLeftTeam().getPlayer(PlayerPosition.LD).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 240), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(20, 280), match.getLeftTeam().getPlayer(PlayerPosition.RD).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 300), match.getLeftTeam().getPlayer(PlayerPosition.RD).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 320), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(20, 360), match.getLeftTeam().getPlayer(PlayerPosition.MF).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 380), match.getLeftTeam().getPlayer(PlayerPosition.MF).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 400), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(20, 440), match.getLeftTeam().getPlayer(PlayerPosition.LF).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 460), match.getLeftTeam().getPlayer(PlayerPosition.LF).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 480), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(20, 520), match.getLeftTeam().getPlayer(PlayerPosition.LD).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 540), match.getLeftTeam().getPlayer(PlayerPosition.LD).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(20, 560), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		
		addItem(new StringRenderItem(new Point(830, 40), match.getRightTeam().getName(), new TrueTypeFont(new Font("Consolas", Font.BOLD, 16), true)));
		
		addItem(new StringRenderItem(new Point(830, 120), match.getRightTeam().getPlayer(PlayerPosition.GL).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 140), match.getRightTeam().getPlayer(PlayerPosition.GL).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 160), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(830, 200), match.getRightTeam().getPlayer(PlayerPosition.LD).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 220), match.getRightTeam().getPlayer(PlayerPosition.LD).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 240), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(830, 280), match.getRightTeam().getPlayer(PlayerPosition.RD).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 300), match.getRightTeam().getPlayer(PlayerPosition.RD).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 320), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(830, 360), match.getRightTeam().getPlayer(PlayerPosition.MF).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 380), match.getRightTeam().getPlayer(PlayerPosition.MF).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 400), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(830, 440), match.getRightTeam().getPlayer(PlayerPosition.LF).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 460), match.getRightTeam().getPlayer(PlayerPosition.LF).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 480), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
		addItem(new StringRenderItem(new Point(830, 520), match.getRightTeam().getPlayer(PlayerPosition.LD).toString(), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 540), match.getRightTeam().getPlayer(PlayerPosition.LD).toString('s'), new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		addItem(new StringRenderItem(new Point(830, 560), "HP  SP EN AT PA BL SH CA", new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true)));
		
	}
}
