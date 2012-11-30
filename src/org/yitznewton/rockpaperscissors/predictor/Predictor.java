package org.yitznewton.rockpaperscissors.predictor;

import java.util.List;

import org.yitznewton.rockpaperscissors.gesture.Gesture;

public abstract class Predictor
{
	protected List<int[]> history;
	
	public Predictor(List<int[]> h){
		history = h;
	}
	
	abstract public Gesture get();
}
