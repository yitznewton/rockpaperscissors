package org.yitznewton.rockpaperscissors;
import java.util.List;
import java.util.Random;

public class ComputerChooser
{
	public int get(List<int[]> history)
	{
		PredictionEngine engine = new PredictionEngine(history);
		int predicted = engine.predict();
		
		if (predicted != -1) {
			switch (predicted) {
			case RoundOfPlay.CHOICE_ROCK:
				return RoundOfPlay.CHOICE_PAPER;
			case RoundOfPlay.CHOICE_PAPER:
				return RoundOfPlay.CHOICE_SCISSORS;
			case RoundOfPlay.CHOICE_SCISSORS:
				return RoundOfPlay.CHOICE_ROCK;
			}
		}
		
		Random r = new Random();
		return r.nextInt(3);
	}
}
