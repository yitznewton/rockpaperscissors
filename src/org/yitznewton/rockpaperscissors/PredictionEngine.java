package org.yitznewton.rockpaperscissors;

import java.util.ArrayList;

import android.util.Log;

public class PredictionEngine {
	private ArrayList<int[]> history;
	
	public PredictionEngine(ArrayList<int[]> h)
	{
		history = h;
	}
	
	public int predict()
	{
		if (findPattern() != -1) {
			return findPattern();
		}
		
		return mode();
	}
	
	private int findPattern()
	{
		return -1;
	}
	
	private int mode()
	{
		ArrayList<int[]> pChoices = new ArrayList<int[]>();

		int value = -1;
		int maxCount = 0;
		
		for (int i = 0; i < history.size(); i++) {
			int count = 0;
			for (int j = 0; j < history.size(); j++) {
				if (history.get(i)[0] == history.get(j)[0]) count++;
			}
			if (count > maxCount) {
				maxCount = count;
				value = history.get(i)[0];
			}
		}
		
		return value;
	}
}
