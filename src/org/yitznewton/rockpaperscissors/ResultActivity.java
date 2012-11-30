package org.yitznewton.rockpaperscissors;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.yitznewton.rockpaperscissors.gesture.Gesture;
import org.yitznewton.rockpaperscissors.gesture.Paper;
import org.yitznewton.rockpaperscissors.gesture.Rock;
import org.yitznewton.rockpaperscissors.gesture.Scissors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends Activity {
	public static final String EXTRA_PLAYER_CHOICE = "player_choice";
	
	static final String STATE_PLAYER_GESTURE = "player_gesture";
	static final String STATE_COMPUTER_GESTURE = "computer_gesture";
	static final String STATE_WINNER = "winner";
	static final String STATE_WINNER_STRING = "winner_string";
	static final String STATE_PLAYER_SCORE = "player_score";
	static final String STATE_COMPUTER_SCORE = "computer_score";
	static final String STATE_HISTORY = "history";
	
	static final String PREFERENCE_PLAYER_SCORE = "player_score";
	static final String PREFERENCE_COMPUTER_SCORE = "computer_score";
	
	private Gesture playerGesture;
	private Gesture computerGesture;
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
			retrievePlayerGesture();
			playRound(savedInstanceState);
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
		savedInstanceState.putString(STATE_PLAYER_GESTURE, playerGesture.getClass().getName());
		savedInstanceState.putString(STATE_COMPUTER_GESTURE, computerGesture.getClass().getName());
		savedInstanceState.putInt(STATE_WINNER, winner);
		savedInstanceState.putString(STATE_WINNER_STRING, winnerString);
		savedInstanceState.putInt(STATE_PLAYER_SCORE, playerScore);
		savedInstanceState.putInt(STATE_COMPUTER_SCORE, computerScore);
		savedInstanceState.putSerializable(STATE_HISTORY, history);
		
		super.onSaveInstanceState(savedInstanceState);
	}
	
	private void restoreState(Bundle state)
	{
		try {
			playerGesture = (Gesture) Class.forName(
				state.getString(STATE_PLAYER_GESTURE)).newInstance();
			computerGesture = (Gesture) Class.forName(
				state.getString(STATE_COMPUTER_GESTURE)).newInstance();
		}
		catch (ClassNotFoundException e) {
			finish();
		}
		catch (IllegalAccessException e) {
			finish();
		}
		catch (InstantiationException e) {
			finish();
		}
		
		winner = state.getInt(STATE_WINNER);
		winnerString = state.getString(STATE_WINNER_STRING);
		playerScore = state.getInt(STATE_PLAYER_SCORE);
		computerScore = state.getInt(STATE_COMPUTER_SCORE);
		history = (ArrayList<int[]>) state.getSerializable(STATE_HISTORY);
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
	
	private void retrievePlayerGesture()
	{
		playerGesture = Gesture.fromButtonId(getIntent()
				.getIntExtra(ResultActivity.EXTRA_PLAYER_CHOICE, -1));
	}
	
	private void playRound(Bundle state)
	{
		int outcome = playerGesture.playAgainst(computerGesture());
		
		switch (outcome) {
		case 1:
			winnerString = getString(R.string.you_win);
			playerScore++;
			saveScore();
			break;
		case -1:
			winnerString = getString(R.string.i_win);
			computerScore++;
			saveScore();
			break;
		case 0:
			winnerString = getString(R.string.draw);
			break;
		}
		
		// FIXME
		history.add(new int[] {0,0});
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
		// FIXME
		pv.setImageDrawable(resourceForChoice(0));
		
		ImageView cv = (ImageView) findViewById(R.id.choice_me);
		// FIXME
		cv.setImageDrawable(resourceForChoice(1));
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

	private Gesture computerGesture()
	{
		if (computerGesture == null) {
			int computerChoice = new ComputerChooser().get(history);
			
			switch (computerChoice) {
			case RoundOfPlay.CHOICE_ROCK:
				computerGesture = new Rock();
				break;
			case RoundOfPlay.CHOICE_PAPER:
				computerGesture = new Paper();
				break;
			case RoundOfPlay.CHOICE_SCISSORS:
				computerGesture = new Scissors();
				break;
			}
		}
		
		return computerGesture;
	}
}
