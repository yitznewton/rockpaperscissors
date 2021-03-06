package org.yitznewton.rockpaperscissors.gesture;

public class Scissors extends Gesture
{
	public int playAgainst(Gesture g)
	{
		if (this.getClass().equals(g.getClass())) return 0;
		return g instanceof Paper ? 1 : -1;
	}
	
	public Gesture losesTo()
	{
		return new Rock();
	}
	
	public int toInt()
	{
		return Gesture.SCISSORS;
	}
}
