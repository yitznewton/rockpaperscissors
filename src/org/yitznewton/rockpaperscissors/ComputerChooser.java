package org.yitznewton.rockpaperscissors;
import java.util.List;
import java.util.Random;

import org.yitznewton.rockpaperscissors.gesture.Gesture;
import org.yitznewton.rockpaperscissors.gesture.Paper;
import org.yitznewton.rockpaperscissors.gesture.Rock;
import org.yitznewton.rockpaperscissors.gesture.Scissors;

public class ComputerChooser
{
	public Gesture get(List<int[]> history)
	{
		
		PredictionEngine engine = new PredictionEngine(history);
		Gesture predicted = engine.predict();

		if (predicted == null) {
			Random r = new Random();
			
			switch (r.nextInt(3)) {
			case 0: return new Rock();
			case 1: return new Paper();
			case 2: return new Scissors();
			}
		}
		
		return predicted.losesTo();	
	}
}
