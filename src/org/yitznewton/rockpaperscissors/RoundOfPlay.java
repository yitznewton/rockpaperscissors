package org.yitznewton.rockpaperscissors;

public class RoundOfPlay {
	public static final int CHOICE_ROCK     = 0;
	public static final int CHOICE_PAPER    = 1;
	public static final int CHOICE_SCISSORS = 2;
	
	protected int choice0;
	protected int choice1;
	
	public RoundOfPlay(int c0, int c1)
	{
		choice0 = c0;
		choice1 = c1;
	}
	
	public int winner()
	{
		if (choice0 == choice1) {
			// draw
			return -1;
		}
		
		if (choice0 == CHOICE_ROCK) {
			return choice1 == CHOICE_PAPER ? 1 : 0; 
		}
		
		if (choice0 == CHOICE_PAPER) {
			return choice1 == CHOICE_SCISSORS ? 1 : 0; 
		}
		
		if (choice0 == CHOICE_SCISSORS) {
			return choice1 == CHOICE_ROCK ? 1 : 0; 
		}
		
		return -1;
		
	}
}
