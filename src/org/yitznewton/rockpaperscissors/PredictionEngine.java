package org.yitznewton.rockpaperscissors;

import java.util.List;

import org.yitznewton.rockpaperscissors.gesture.Gesture;
import org.yitznewton.rockpaperscissors.predictor.Mode;
import org.yitznewton.rockpaperscissors.predictor.Pattern;

public class PredictionEngine {
	private List<int[]> history;
	
	public PredictionEngine(List<int[]> h)
	{
		history = h;
	}
	
	public Gesture predict()
	{
		Gesture fromPattern = new Pattern(history).get();
		
		if (fromPattern != null) {
			return fromPattern;
		}
		
		return new Mode(history).get();
	}
}
