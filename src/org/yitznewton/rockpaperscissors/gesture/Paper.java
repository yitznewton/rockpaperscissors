package org.yitznewton.rockpaperscissors.gesture;

public class Paper extends Gesture
{
	public int playAgainst(Gesture g)
	{
		if (this.getClass().equals(g.getClass())) return 0;
		return g instanceof Rock ? 1 : -1;
	}
	
	public int toInt()
	{
		return Gesture.PAPER;
	}
}
