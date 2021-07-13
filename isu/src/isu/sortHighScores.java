package isu;

import java.util.Comparator;

// comparator class to compare and sort the scores from biggest to smallest to list
public class sortHighScores implements Comparator <HighScore> {
	public int compare (HighScore h1, HighScore h2){
		return h2.getScore() - h1.getScore();
	}
}
