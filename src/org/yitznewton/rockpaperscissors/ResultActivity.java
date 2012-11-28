package org.yitznewton.rockpaperscissors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends Activity {
	public static final String EXTRA_PLAYER_CHOICE = "player_choice";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int playerChoice = -1;
		String playerChoiceString = "";
		int computerChoice = RoundOfPlay.CHOICE_SCISSORS;
		String computerChoiceString = getString(R.string.scissors);
		
		switch (getIntent().getIntExtra(ResultActivity.EXTRA_PLAYER_CHOICE, -1)) {
		case R.id.button_rock:
			playerChoice = RoundOfPlay.CHOICE_ROCK;
			playerChoiceString = getString(R.string.rock);
			break;
		case R.id.button_paper:
			playerChoice = RoundOfPlay.CHOICE_PAPER;
			playerChoiceString = getString(R.string.paper);
			break;
		case R.id.button_scissors:
			playerChoice = RoundOfPlay.CHOICE_SCISSORS;
			playerChoiceString = getString(R.string.scissors);
			break;
		}
		
		RoundOfPlay r = new RoundOfPlay(playerChoice, computerChoice);
		int winner = r.winner();
		String winnerString = "";
		
		switch (r.winner()) {
		case 0:
			winnerString = getString(R.string.you_win);
			break;
		case 1:
			winnerString = getString(R.string.i_win);
			break;
		case -1:
			winnerString = getString(R.string.draw);
			break;
		}
		
		setContentView(R.layout.activity_result);
		
		TextView playerChoiceView = (TextView) findViewById(R.id.choice_you);
		playerChoiceView.setText(playerChoiceString);
		
		TextView computerChoiceView = (TextView) findViewById(R.id.choice_me);
		computerChoiceView.setText(computerChoiceString);
		
		TextView winnerView = (TextView) findViewById(R.id.result_winner);
		winnerView.setText(winnerString);
	}
	
	public void playAgain(View v)
	{
		Intent i = new Intent(this, ChoiceActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result, menu);
		return true;
	}

}
