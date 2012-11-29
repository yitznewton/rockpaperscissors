package org.yitznewton.rockpaperscissors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yitznewton.rockpaperscissors.predictor.Mode;
import org.yitznewton.rockpaperscissors.predictor.Pattern;

public class PredictionEngine {
	private ArrayList<int[]> history;
	
	public PredictionEngine(ArrayList<int[]> h)
	{
		history = h;
	}
	
	public int predict()
	{
		int fromPattern = new Pattern(history).get();
		
		if (fromPattern != -1) {
			return fromPattern;
		}
		
		return new Mode(history).get();
	}
}
