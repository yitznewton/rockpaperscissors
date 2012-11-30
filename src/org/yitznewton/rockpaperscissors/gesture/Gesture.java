package org.yitznewton.rockpaperscissors.gesture;

import org.yitznewton.rockpaperscissors.R;

public abstract class Gesture
{
	public static final int ROCK     = 0;
	public static final int PAPER    = 1;
	public static final int SCISSORS = 2;
	
	abstract public int playAgainst(Gesture g);
	abstract public int toInt();
	
	public static Gesture fromInt(int i)
	{
		switch(i) {
		case ROCK:
			return new Rock();
		case PAPER:
			return new Paper();
		case SCISSORS:
			return new Scissors();
		default:
			throw new RuntimeException("Invalid integer");
		}
	}
	
	public static Gesture fromButtonId(int id)
	{
		switch (id) {
		case R.id.button_rock:
			return new Rock();
		case R.id.button_paper:
			return new Paper();
		case R.id.button_scissors:
			return new Scissors();
		default:
			throw new RuntimeException("Unknown ID");
		}
	}
}
