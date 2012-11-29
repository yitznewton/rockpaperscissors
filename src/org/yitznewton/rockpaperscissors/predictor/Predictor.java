package org.yitznewton.rockpaperscissors.predictor;

import java.util.List;

public abstract class Predictor
{
	protected List<int[]> history;
	
	public Predictor(List<int[]> h){
		history = h;
	}
	
	abstract public int get();
}
