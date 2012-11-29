package org.yitznewton.rockpaperscissors;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends Activity {
	public static final String EXTRA_PLAYER_CHOICE = "player_choice";
	
	static final String STATE_PLAYER_CHOICE = "player_choice";
	static final String STATE_COMPUTER_CHOICE = "computer_choice";
	static final String STATE_WINNER = "winner";
	static final String STATE_WINNER_STRING = "winner_string";
	static final String STATE_PLAYER_SCORE = "player_score";
	static final String STATE_COMPUTER_SCORE = "computer_score";
	static final String STATE_HISTORY = "history";
	
	static final String PREFERENCE_PLAYER_SCORE = "player_score";
	static final String PREFERENCE_COMPUTER_SCORE = "computer_score";
	
	private int playerChoice = -1;
	private int computerChoice = -1;
	private int winner = -1;
	private String winnerString;
	private int playerScore = 0;
	private int computerScore = 0;
	private ArrayList<int[]> history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_result);
		
		if (savedInstanceState == null) {
			loadHistory();
			loadScore();
			retrievePlayerChoice();
			playRound(playerChoice, computerChoice(), savedInstanceState);
		}
		else {
			restoreState(savedInstanceState);
		}

		TextView winnerView = (TextView) findViewById(R.id.result_winner);
		winnerView.setText(winnerString);

		drawScore();
		drawChoices();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		savedInstanceState.putInt(STATE_PLAYER_CHOICE, playerChoice);
		savedInstanceState.putInt(STATE_COMPUTER_CHOICE, computerChoice);
		savedInstanceState.putInt(STATE_WINNER, winner);
		savedInstanceState.putString(STATE_WINNER_STRING, winnerString);
		savedInstanceState.putInt(STATE_PLAYER_SCORE, playerScore);
		savedInstanceState.putInt(STATE_COMPUTER_SCORE, computerScore);
		savedInstanceState.putSerializable(STATE_HISTORY, history);
		
		super.onSaveInstanceState(savedInstanceState);
	}
	
	private void restoreState(Bundle state)
	{
		playerChoice = state.getInt(STATE_PLAYER_CHOICE);
		computerChoice = state.getInt(STATE_COMPUTER_CHOICE);
		winner = state.getInt(STATE_WINNER);
		winnerString = state.getString(STATE_WINNER_STRING);
		playerScore = state.getInt(STATE_PLAYER_SCORE);
		computerScore = state.getInt(STATE_COMPUTER_SCORE);
		history = (ArrayList) state.getSerializable(STATE_HISTORY);
	}
	
	public void playAgain(View v)
	{
		Intent i = new Intent(this, ChoiceActivity.class);
		startActivity(i);
	}
	
	public void resetScore(View v)
	{
		playerScore = 0;
		computerScore = 0;
		history = new ArrayList<int[]>();
		
		saveHistory();
		saveScore();
		drawScore();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result, menu);
		return true;
	}
	
	private void retrievePlayerChoice()
	{
		int pChoice = getIntent()
				.getIntExtra(ResultActivity.EXTRA_PLAYER_CHOICE, -1);
		
		switch (pChoice) {
		case R.id.button_rock:
			playerChoice = RoundOfPlay.CHOICE_ROCK;
			break;
		case R.id.button_paper:
			playerChoice = RoundOfPlay.CHOICE_PAPER;
			break;
		case R.id.button_scissors:
			playerChoice = RoundOfPlay.CHOICE_SCISSORS;
			break;
		default:
			throw new RuntimeException("Unexpected player choice value");
		}
	}
	
	private void playRound(int pChoice, int cChoice, Bundle state)
	{
		RoundOfPlay r = new RoundOfPlay(pChoice, cChoice);
		
		winner = r.winner();
		
		switch (winner) {
		case 0:
			winnerString = getString(R.string.you_win);
			playerScore++;
			saveScore();
			break;
		case 1:
			winnerString = getString(R.string.i_win);
			computerScore++;
			saveScore();
			break;
		case -1:
			winnerString = getString(R.string.draw);
			break;
		}
		
		history.add(new int[] {pChoice, cChoice});
		saveHistory();
	}
	
	private void loadScore()
	{
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		playerScore = preferences.getInt(PREFERENCE_PLAYER_SCORE, 0);
		computerScore = preferences.getInt(PREFERENCE_COMPUTER_SCORE, 0);
	}
	
	private void saveScore()
	{
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt(PREFERENCE_PLAYER_SCORE, playerScore);
		editor.putInt(PREFERENCE_COMPUTER_SCORE, computerScore);
		editor.commit();
	}
	
	private void loadHistory()
	{
		try {
			FileInputStream fis = openFileInput("history");
			ObjectInputStream ois = new ObjectInputStream(fis);
			history = (ArrayList<int[]>) ois.readObject();	
			ois.close();
		}
		catch (IOException e) {
			
		}
		catch (ClassNotFoundException e) {
			
		}
		finally {
			if (history == null) {
				history = new ArrayList<int[]>();
			}
		}
	}
	
	private void saveHistory()
	{
		try {
			FileOutputStream fos
				= openFileOutput("history", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(history);
			fos.getFD().sync();
			oos.close();
		}
		catch (IOException e) {
			
		}
	}

	private void drawScore()
	{
		TextView playerScoreView = (TextView) findViewById(R.id.score_you);
		playerScoreView.setText(Integer.toString(playerScore));
		
		TextView computerScoreView = (TextView) findViewById(R.id.score_me);
		computerScoreView.setText(Integer.toString(computerScore));
	}
	
	private void drawChoices()
	{
		ImageView pv = (ImageView) findViewById(R.id.choice_you);
		pv.setImageDrawable(resourceForChoice(playerChoice));
		
		ImageView cv = (ImageView) findViewById(R.id.choice_me);
		cv.setImageDrawable(resourceForChoice(computerChoice));
	}
	
	private Drawable resourceForChoice(int c)
	{
		switch (c) {
		case RoundOfPlay.CHOICE_ROCK:
			return getResources().getDrawable(R.drawable.rock);
		case RoundOfPlay.CHOICE_PAPER:
			return getResources().getDrawable(R.drawable.paper);
		case RoundOfPlay.CHOICE_SCISSORS:
			return getResources().getDrawable(R.drawable.scissors);
		default:
			throw new RuntimeException("Invalid choice");
		}
	}

	private int computerChoice()
	{
		if (computerChoice == -1) {
			computerChoice = new ComputerChooser().get(history);
		}
		
		return computerChoice;
	}
}
