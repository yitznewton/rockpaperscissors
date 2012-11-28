package org.yitznewton.rockpaperscissors;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends Activity {
	public static final String EXTRA_PLAYER_CHOICE = "player_choice";
	
	static final String STATE_PLAYER_CHOICE = "player_choice";
	static final String STATE_PLAYER_CHOICE_STRING = "player_choice_string";
	static final String STATE_COMPUTER_CHOICE = "computer_choice";
	static final String STATE_COMPUTER_CHOICE_STRING = "computer_choice_string";
	static final String STATE_WINNER = "winner";
	static final String STATE_WINNER_STRING = "winner_string";
	static final String STATE_PLAYER_SCORE = "player_score";
	static final String STATE_COMPUTER_SCORE = "computer_score";
	
	static final String PREFERENCE_PLAYER_SCORE = "player_score";
	static final String PREFERENCE_COMPUTER_SCORE = "computer_score";
	
	private int playerChoice   = -1;
	private String playerChoiceString;
	private int computerChoice = -1;
	private String computerChoiceString;
	private int winner = -1;
	private String winnerString;
	private int playerScore = 0;
	private int computerScore = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_result);
		
		if (savedInstanceState == null) {
			SharedPreferences preferences = getPreferences(MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			playerScore = preferences.getInt(PREFERENCE_PLAYER_SCORE, 0);
			computerScore = preferences.getInt(PREFERENCE_COMPUTER_SCORE, 0);
			
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
			default:
				throw new RuntimeException("Unexpected player choice value");
			}

			RoundOfPlay r = new RoundOfPlay(playerChoice, computerChoice());
			winner = r.winner();
			
			switch (winner) {
			case 0:
				winnerString = getString(R.string.you_win);
				playerScore++;
				editor.putInt(PREFERENCE_PLAYER_SCORE, playerScore);
				editor.commit();
				break;
			case 1:
				winnerString = getString(R.string.i_win);
				computerScore++;
				editor.putInt(PREFERENCE_COMPUTER_SCORE, computerScore);
				editor.commit();
				break;
			case -1:
				winnerString = getString(R.string.draw);
				break;
			}
		}
		else {
			restoreState(savedInstanceState);
		}

		
		TextView playerChoiceView = (TextView) findViewById(R.id.choice_you);
		playerChoiceView.setText(playerChoiceString);
		
		TextView computerChoiceView = (TextView) findViewById(R.id.choice_me);
		computerChoiceView.setText(computerChoiceString());
		
		TextView winnerView = (TextView) findViewById(R.id.result_winner);
		winnerView.setText(winnerString);
		
		TextView playerScoreView = (TextView) findViewById(R.id.score_you);
		playerScoreView.setText(Integer.toString(playerScore));
		
		TextView computerScoreView = (TextView) findViewById(R.id.score_me);
		computerScoreView.setText(Integer.toString(computerScore));
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		savedInstanceState.putInt(STATE_PLAYER_CHOICE, playerChoice);
		savedInstanceState.putString(STATE_PLAYER_CHOICE_STRING, playerChoiceString);
		savedInstanceState.putInt(STATE_COMPUTER_CHOICE, computerChoice);
		savedInstanceState.putString(STATE_COMPUTER_CHOICE_STRING, computerChoiceString);
		savedInstanceState.putInt(STATE_WINNER, winner);
		savedInstanceState.putString(STATE_WINNER_STRING, winnerString);
		savedInstanceState.putInt(STATE_PLAYER_SCORE, playerScore);
		savedInstanceState.putInt(STATE_COMPUTER_SCORE, computerScore);
		
		super.onSaveInstanceState(savedInstanceState);
	}
	
	private void restoreState(Bundle state)
	{
		playerChoice = state.getInt(STATE_PLAYER_CHOICE);
		playerChoiceString = state.getString(STATE_PLAYER_CHOICE_STRING);
		computerChoice = state.getInt(STATE_COMPUTER_CHOICE);
		computerChoiceString = state.getString(STATE_COMPUTER_CHOICE_STRING);
		winner = state.getInt(STATE_WINNER);
		winnerString = state.getString(STATE_WINNER_STRING);
		playerScore = state.getInt(STATE_PLAYER_SCORE);
		computerScore = state.getInt(STATE_COMPUTER_SCORE);
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

	private int computerChoice()
	{
		if (computerChoice != -1) {
			return computerChoice;
		}
		
		return computerChoice = new ComputerChooser().get();
	}
	
	private String computerChoiceString()
	{
		if (computerChoiceString != null) {
			return computerChoiceString;
		}
		
		switch(computerChoice()) {
		case RoundOfPlay.CHOICE_ROCK:
			return computerChoiceString = getString(R.string.rock);
		case RoundOfPlay.CHOICE_PAPER:
			return computerChoiceString = getString(R.string.paper);
		case RoundOfPlay.CHOICE_SCISSORS:
			return computerChoiceString = getString(R.string.scissors);
		}
		
		return null;
	}
}
