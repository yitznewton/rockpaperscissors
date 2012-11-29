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
		ArrayList<Integer> alreadyChecked = new ArrayList<Integer>();

		int value = -1;
		int maxCount = 0;
		
		for (int i = 0; i < history.size(); i++) {
			int current = history.get(i)[0];
			if (alreadyChecked.indexOf(Integer.valueOf(current)) != -1) continue;
			alreadyChecked.add(Integer.valueOf(current));
			
			int count = 0;
			for (int j = 0; j < history.size(); j++) {
				if (history.get(j)[0] == current) count++;
			}
			if (count > maxCount) {
				maxCount = count;
				value = history.get(i)[0];
			}
		}
		
		return value;
	}
}
