package org.yitznewton.rockpaperscissors.test;
import java.util.ArrayList;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.yitznewton.rockpaperscissors.gesture.Scissors;
import org.yitznewton.rockpaperscissors.predictor.Pattern;

public class PatternTest extends TestCase
{
	public void testSmallHistory_ReturnsNeg1()
	{
		ArrayList<int[]> h = new ArrayList<int[]>();
		h.add(new int[] {1,2});
		h.add(new int[] {2,1});
		
		Pattern ptn = new Pattern(h);
		assertNull(ptn.get());
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
		assertThat(ptn.get(), instanceOf(Scissors.class));
	}
}
