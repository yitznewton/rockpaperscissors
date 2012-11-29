package org.yitznewton.rockpaperscissors.predictor;

import java.util.ArrayList;
import java.util.Arrays;

public class Pattern extends Predictor
{
	public Pattern(ArrayList<int[]> h)
	{
		super(h);
		
		if (h.size() > 50) {
			history = (ArrayList<int[]>) h.subList(h.size()-50, h.size());
		}
	}
	
	@Override
	public int get()
	{
		// loop through sets in history, from largest to smallest, looking
		// for a match to recent history
		
		// start with a largest possible range of 6 members
		int sizeToFind = history.size() > 6 ? 6 : history.size() - 1; 
		
		while (sizeToFind >= 3) {
			int recentStart = history.size() - sizeToFind;
			int recentEnd = history.size();
			
			for (int i = 0; i < history.size() - sizeToFind - 1; i++) {
				int setEnd = i + sizeToFind;
				
				// both intervals are the same size
				assert recentEnd - recentStart == setEnd - i;
				
				if (same(recentStart, recentEnd, i, setEnd)) {
					return history.get(i + sizeToFind)[0];
				}
			}
			
			sizeToFind--;
		}
		
		return -1;
	}
	
	private boolean same(int a0, int a1, int b0, int b1)
	{
		for (int i = a0, j = b0; i < a1; i++, j++) {
			if (Arrays.equals(history.get(i), history.get(j)) == false) {
				return false;
			}
		}
		
		return true;
	}
}
