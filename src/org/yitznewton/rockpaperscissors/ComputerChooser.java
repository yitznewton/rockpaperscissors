package org.yitznewton.rockpaperscissors;
import java.util.Random;

public class ComputerChooser
{
	public int get()
	{
		Random r = new Random();
		return r.nextInt(3);
	}
}
