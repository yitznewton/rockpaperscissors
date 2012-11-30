package org.yitznewton.rockpaperscissors.predictor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yitznewton.rockpaperscissors.gesture.Gesture;

public class Mode extends Predictor
{
	public Mode(List<int[]> h)
	{
		super(h);
	}
	
	@Override
	public Gesture get()
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
		return ratio > 0.4f ? Gesture.fromInt(mode) : null;
	}
}
