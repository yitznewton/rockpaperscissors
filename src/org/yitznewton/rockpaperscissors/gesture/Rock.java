package org.yitznewton.rockpaperscissors.gesture;

public class Rock extends Gesture
{
	public int playAgainst(Gesture g)
	{
		if (this.getClass().equals(g.getClass())) return 0;
		return g instanceof Scissors ? 1 : -1;
	}
	
	public Gesture losesTo()
	{
		return new Paper();
	}
	
	public int toInt()
	{
		return Gesture.ROCK;
	}
}
