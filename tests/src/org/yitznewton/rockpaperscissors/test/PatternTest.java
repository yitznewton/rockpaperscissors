package org.yitznewton.rockpaperscissors.test;
import java.util.ArrayList;

import org.yitznewton.rockpaperscissors.predictor.Pattern;

import junit.framework.TestCase;


public class PatternTest extends TestCase
{
	public void testSmallHistory_ReturnsNeg1()
	{
		ArrayList<int[]> h = new ArrayList<int[]>();
		h.add(new int[] {1,2});
		h.add(new int[] {2,1});
		
		Pattern ptn = new Pattern(h);
		assertEquals(-1, ptn.get());
	}
	
	public void testThreeSequence()
	{
		ArrayList<int[]> h = new ArrayList<int[]>();
		h.add(new int[] {0,0});
		h.add(new int[] {0,1});
		h.add(new int[] {1,2});
		h.add(new int[] {2,0});
		h.add(new int[] {2,2});
		h.add(new int[] {0,1});
		h.add(new int[] {1,2});
		h.add(new int[] {2,0});
		
		Pattern ptn = new Pattern(h);
		assertEquals(2, ptn.get());
	}
}
