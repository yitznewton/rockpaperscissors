package org.yitznewton.rockpaperscissors.gesture;

import org.yitznewton.rockpaperscissors.R;

public abstract class Gesture
{
	abstract public int playAgainst(Gesture g);
	
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
