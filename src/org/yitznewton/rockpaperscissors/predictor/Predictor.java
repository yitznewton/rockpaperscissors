package org.yitznewton.rockpaperscissors.predictor;

import java.util.ArrayList;

public abstract class Predictor
{
	protected ArrayList<int[]> history;
	
	public Predictor(ArrayList<int[]> h){
		history = h;
	}
	
	abstract public int get();
}
