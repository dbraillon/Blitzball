package com.dbraillon.blitzball;

import com.dbraillon.blitzball.enumerations.DecisionType;
import com.dbraillon.blitzball.enumerations.PlayerPosition;
import com.dbraillon.blitzball.models.Player;
import com.dbraillon.blitzball.models.Stadium;
import com.dbraillon.blitzball.models.Team;
import com.dbraillon.blitzball.tools.Decision;
import com.dbraillon.math.Point;

public class AI {

	public static void play(Player mPlayer, Player bPlayer, Team mTeam, Team oTeam, Stadium stadium) {
		
		mPlayer.getReflex().increase();
		
		Decision decision = makeADecision(mPlayer, bPlayer, mTeam, oTeam, stadium);
		System.out.println(mPlayer.toString('n') + ":" + decision.toString());
		
		if(decision.decisionType == DecisionType.CONTINUE) {
			
			decision = mPlayer.getLastDecision();
		}
		else if(decision.decisionType != DecisionType.NOTHING) {
			
			mPlayer.getReflex().reset();
		}
		
		switch(decision.decisionType) {
		
			case NOTHING:
				break;
				
			case FOLLOW:
				
				mPlayer.turnToDestination(decision.player.getCurrentPosition());
				mPlayer.goForward();
				break;
				
			case MOVE:
				
				mPlayer.turnToDestination(new Point(decision.x, decision.y));
				mPlayer.goForward();
				break;
				
			case PASS:
				break;
				
			case SHOOT:
				break;
				
			default:
				break;
		}
		
		mPlayer.setLastDecision(decision);
	}
	
	public static Decision makeADecision(Player mPlayer, Player bPlayer, Team mTeam, Team oTeam, Stadium stadium) {
		
		// le goal ne fait rien
		if(mPlayer.getPosition() == PlayerPosition.GL) 
			return new Decision(DecisionType.NOTHING);
		
		// si j'ai pas mon reflex je prend pas de décision
		if(!mPlayer.getReflex().isAware()) 
			return new Decision(DecisionType.CONTINUE);
		
		// qui a le ballon ?
		if(mPlayer.getTeam() == bPlayer.getTeam()) {
			
			// quelqu'un de ma team ! mais qui ?
			if(mPlayer == bPlayer) {
				
				// c'est moi !
				
				// ça vaut le coup que je tire ?
				float d = Point.getDistance(oTeam.getPlayer(PlayerPosition.GL).getCurrentPosition(), mPlayer.getCurrentPosition());
				
				System.out.println("" + d);
				
				if(d/20 < mPlayer.sh * 70 / 100) {
					
					/*
					if(mPlayer.shoot(bPlayer, oTeam.getPlayer(Team.GL))) {
						
						System.out.println("GOAL !");
						
						if(oTeam.get_tPosition() == TeamPosition.LEFT) oTeam.makeBlueTeam(); 
						else oTeam.makeRedTeam();
						
						if(mTeam.get_tPosition() == TeamPosition.LEFT) mTeam.makeBlueTeam(); 
						else mTeam.makeRedTeam();
					}
					else {
						
						System.out.println("MISS !");
					}*/
				}
				
				// dans tous les cas j'avance en direction des cages adverses
				return new Decision(DecisionType.MOVE, 
									oTeam.getPlayer(PlayerPosition.GL).getCurrentPosition().getX(), 
									oTeam.getPlayer(PlayerPosition.GL).getCurrentPosition().getY());
			}
			else {
				
				if(mPlayer.getCurrentPosition() == mPlayer.getOriginPosition()) {
					
					return new Decision(DecisionType.NOTHING);
				}
				else {
				
					return new Decision(DecisionType.MOVE, 
							mPlayer.getOriginPosition().getX(), 
							mPlayer.getOriginPosition().getY());
				}
			}
		}
		else {
			
			// quelqu'un de la team adverse !
			if(mPlayer.canFollow(bPlayer)) {
				
				return new Decision(DecisionType.FOLLOW, bPlayer);
			}
			else {
				
				if(mPlayer.getCurrentPosition() == mPlayer.getOriginPosition()) {
					
					return new Decision(DecisionType.NOTHING);
				}
				else {
				
					return new Decision(DecisionType.MOVE, 
							mPlayer.getOriginPosition().getX(), 
							mPlayer.getOriginPosition().getY());
				}
			}
		}
	}

	
}
