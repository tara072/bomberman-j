package isu;

import java.util.ArrayList;

public class HighScore
{
	private int score;
	private String name;
	private int player;
	
	// array of scores
	private static ArrayList <HighScore> highscoreArray = new ArrayList <HighScore> ();
	
	public HighScore(int score, String name, int player)
	{
		this.score = score;
		this.name = name;
		this.player = player;
		highscoreArray.add(this);
	}
	
	// getter methods
	public String getName() {
		return name;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	/*	getScoreString Method
	 * parameters: none
	 * description: returns a score as a string
	 * returns: String 	
	 */
	public String getScoreString() {
		return Integer.toString(this.score);
	}
	
	public static ArrayList <HighScore> getHSArray() {
		return highscoreArray;
	}
	
	public int getPlayer() {
		return player;
	}


}



