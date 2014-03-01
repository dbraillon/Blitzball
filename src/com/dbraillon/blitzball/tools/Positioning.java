package com.dbraillon.blitzball.tools;

import com.dbraillon.blitzball.enumerations.TeamPosition;
import com.dbraillon.blitzball.models.Player;
import com.dbraillon.math.Point;

public class Positioning {

	public static Point getCaughtPosition(Player pBall, int i)
	{
		Point catchPosition = null;
		
		float pbx = pBall.getCurrentPosition().getX();
		float pby = pBall.getCurrentPosition().getY();
		
		if(i == 0) catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius() + 10, pby);
		else if(i == 1) catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius() + 10, pby - 10);
		else if(i == 2) catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius() + 10, pby + 10);
		else if(i == 3) catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius() + 10, pby - 15);
		else catchPosition = new Point(pbx + pBall.getCaughtZone().getRadius() + 10, pby + 15);
		
		// player ball belongs to right team so the current player needs to be to the left
		if(pBall.getTeam().get_tPosition() == TeamPosition.RIGHT) {
			
			catchPosition = Point.getOppositeAgainst(catchPosition, pBall.getCurrentPosition());
		}
		
		return catchPosition;
	}
	
	public static Point getAttackPosition(Player pBall, Point origin)
	{
		return Point.getOppositeAgainst(origin, pBall.getCurrentPosition());
	}
}
