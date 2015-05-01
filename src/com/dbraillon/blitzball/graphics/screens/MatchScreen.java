package com.dbraillon.blitzball.graphics.screens;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.dbraillon.blitzball.enumerations.GameState;
import com.dbraillon.blitzball.graphics.components.InfoPanel;
import com.dbraillon.blitzball.models.Match;
import com.dbraillon.blitzball.models.Player;
import com.dbraillon.blitzball.models.Team;
import com.dbraillon.dbgraphics.Depth;
import com.dbraillon.dbgraphics.Navigator;
import com.dbraillon.dbgraphics.Point;
import com.dbraillon.dbgraphics.Screen;

public class MatchScreen extends Screen {
	
	private Match match;
	private InfoPanel infoPanel;
	
	private Animation leftAnimation;
	private Animation rightAnimation;
	private Animation ballAnimation;
	
	// Info in the match
	private GameState previousState = GameState.NORMAL;
	private int previousDefensiveStat = 0;
	private Player previousAttacker;
	
	public MatchScreen() {
		super();
		
		match = new Match(new Point(207, 120));
		infoPanel = new InfoPanel(new Point(6, 34), Depth.Middle, null, 700, 300);
		try {
			SpriteSheet ss = new SpriteSheet("assets/characters/brownhairgirl.png", 32, 32);
			SpriteSheet ss2 = new SpriteSheet("assets/characters/redheadboy.png", 32, 32);
			SpriteSheet ballSpriteSheet = new SpriteSheet("assets/blitzball.png", 32, 32);
			leftAnimation = new Animation(ss, 0, 0, 2, 0, true, 400, true);
			leftAnimation.setPingPong(true);
			rightAnimation = new Animation(ss2, 0, 0, 2, 0, true, 400, true);
			rightAnimation.setPingPong(true);
			ballAnimation = new Animation(ballSpriteSheet, 0, 0, 3, 0, true, 400, true);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addItem(infoPanel);
	}
	
	@Override
	public void init(Navigator navigator, GameContainer gameContainer) {
		super.init(navigator, gameContainer);
		
		
	}
	
	@Override
	public void update(GameContainer gameContainer, double frameTimeModifier) {
		
		match.update(gameContainer, frameTimeModifier);
		
		infoPanel.setpBallNameString(match.getpBall().toString('n'));
		infoPanel.setpBallStatsHeaderString("HP  SP EN AT PA BL SH CA");
		infoPanel.setpBallStatsValueString(match.getpBall().toString('s'));
		
		if(match.getpCounter() != null) {
			
			infoPanel.setpCounterNameString(match.getpCounter().toString('n'));
			infoPanel.setpCounterStatsHeaderString("HP  SP EN AT PA BL SH CA");
			infoPanel.setpCounterStatsValueString(match.getpCounter().toString('s'));
		}
		
		if(previousState == GameState.NORMAL && match.getgState() == GameState.CAUGHT)
			infoPanel.addLine(match.getpBall().toString('n') + " has been caught!");
		if(previousState == GameState.CAUGHT && match.getgState() == GameState.ATTACK)
			infoPanel.addLine(match.getpBall().toString('n') + " has " + match.getpBall().en + " EN.");
		if(previousDefensiveStat != match.getpDefensiveStat() && previousAttacker != null) {
			infoPanel.addLine(previousAttacker.toString('n') + " hit for " + (previousDefensiveStat - match.getpDefensiveStat()) + "!");
			if(match.getpDefensiveStat() < 0)
				infoPanel.addLine(previousAttacker.toString('n') + " grab the ball!");
		}
		
		previousState = match.getgState();
		previousDefensiveStat = match.getpDefensiveStat();
		previousAttacker = match.getpCounter();
	}
	
	@Override
	public void render(GameContainer gameContainer) {
		super.render(gameContainer);
		
		infoPanel.render(gameContainer);
		
		// draw stadium in black
		gameContainer.getGraphics().setColor(new Color(255, 255, 255));
		gameContainer.getGraphics().drawOval(318, 34, match.getStadium().get_totalRadius(), match.getStadium().get_totalRadius());
		
		// start players rendering
		for(int i = 0; i < Team.PLAYER_COUNT; i++) {
			
			Player pBlue = match.getLeftTeam().getPlayer(i);
			Player pRed = match.getRightTeam().getPlayer(i);
			
			// draw the red player
			gameContainer.getGraphics().drawAnimation(
					leftAnimation, 
					318 + pRed.getCurrentPosition().getX(), 
					34 + pRed.getCurrentPosition().getY());
			
			gameContainer.getGraphics().drawAnimation(
					rightAnimation, 
					318 + pBlue.getCurrentPosition().getX(), 
					34 + pBlue.getCurrentPosition().getY());
			
			gameContainer.getGraphics().drawAnimation(ballAnimation, 318 + match.getBall().getPosition().getX(), 34 + match.getBall().getPosition().getY());
			/*gameContainer.getGraphics().setColor(new Color(255, 122, 122));
			gameContainer.getGraphics().drawRect(318 + pRed.getCurrentPosition().getX() - (float)pRed.getPlayerZone().getRadius() / 2, 34 + pRed.getCurrentPosition().getY() - (float)pRed.getPlayerZone().getRadius() / 2, 
					(float)pRed.getPlayerZone().getRadius(), (float)pRed.getPlayerZone().getRadius());*/
			
			// draw the blue player
			/*gameContainer.getGraphics().setColor(new Color(122, 122, 255));
			gameContainer.getGraphics().drawRect(318 + pBlue.getCurrentPosition().getX(), 34 + pBlue.getCurrentPosition().getY(), 
					(float)pBlue.getPlayerZone().getRadius(), (float)pBlue.getPlayerZone().getRadius());*/
		}
	}
}
