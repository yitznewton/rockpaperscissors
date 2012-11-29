package org.yitznewton.rockpaperscissors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		
		return significantMode();
	}
	
	private int findPattern()
	{
		return -1;
	}
	
	private int significantMode()
	{
		HashMap<Integer, Integer> frequencies
			= new HashMap<Integer, Integer>();
		
		for (int[] round : history) {
			Integer frequency = frequencies.get(round[0]);
			frequencies.put(round[0], (frequency == null ? 1 : ++frequency));
		}
		
		int mode = 0;
		int maxFrequency = 0;
		
		for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()) {
			int frequency = entry.getValue();
			if (frequency > maxFrequency) {
				maxFrequency = frequency;
				mode = entry.getKey();
			}
		}
		
		float ratio = (float) maxFrequency / (float) history.size();
		return ratio > 0.4f ? mode : -1;
	}
}
