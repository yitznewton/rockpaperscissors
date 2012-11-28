package org.yitznewton.rockpaperscissors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class ChoiceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);
	}
	
	public void onClick(View v)
	{
		Intent i = new Intent(this, ResultActivity.class);
		i.putExtra(ResultActivity.EXTRA_PLAYER_CHOICE, v.getId());
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_choice, menu);
		return true;
	}

}
