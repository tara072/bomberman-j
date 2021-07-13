package isu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.*;

public class finalPage extends JPanel{

	private static JFrame frame;
	private static JPanel panel;
	private static String winner;		// string to hold statement regarding winner
	private static String OnePoints;	// string to hold statement regarding player 1's points
	private static String TwoPoints;	// string to hold statement regarding player 2's points
	private Image p1;					// player 1 image
	private Image p2;					// player 2 image
	
	// booleans to store results
	private boolean tie = false;		
	private boolean p1win = false;
	private boolean p2win = false;
	
	/*	finalPage Method
	 * parameters: the winner in a string, the loser in a string, player 1's points, player 2's points
	 * description: adds information into strings to print
	 * returns: void 	
	 */

	public finalPage(String winner, String loser, int OnePoints, int TwoPoints) {
		if (winner.equalsIgnoreCase("Tie")) {
			tie = true;
			finalPage.winner = "It's a tie!";
		}
		else {
			if (winner.equalsIgnoreCase("Player 1")) 
				p1win = true;
			else if (winner.equalsIgnoreCase("Player 2"))
				p2win = true;
			finalPage.winner = "The winner is " + winner + "!";
		}
		finalPage.OnePoints = "Player 1: " + OnePoints;
		finalPage.TwoPoints = "Player 2: " + TwoPoints;
	}

	/*	paintComponent Method
	 * parameters: Graphics
	 * description: draws the text for the end page
	 * returns: void 	
	 */

	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		ImageObserver observer = null;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 680, 680);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 70));
		g.drawString("Game Over!",140,270); 
		g.setFont(new Font("Arial", Font.BOLD, 30));
		if (tie == true) {
			g.drawString(winner, 300, 320);
		}
		else {
			g.drawString(winner, 190, 320);
		}
		Image p1 = new ImageIcon("front.png").getImage();
		Image p2 = new ImageIcon("front blue.png").getImage();
		if (p1win == true) {
			g.drawImage(p1, 150, 290, 40, 40, observer);
		}
		else if (p2win == true) {
			g.drawImage(p2, 150, 290, 40, 40, observer);
		}
		else {
			g.drawImage(p1, 225, 290, 40, 40, observer);
			g.drawImage(p2, 450, 290, 40, 40, observer);
		}
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Final Scores:", 290, 370);
		g.drawString(OnePoints, 230, 400);
		g.drawString(TwoPoints, 230, 430);
	}

	/*	getInstructionsPanel Method
	 * parameters: the winner in a string, the loser in a string, player 1's points, player 2's points
	 * description: returns the JPanel created to display in the MainMenu
	 * returns: JPanel 	
	 */
	public JPanel getFinalPanel(String winner, String loser, int OnePoints, int TwoPoints) {	
		panel = new finalPage(winner, loser, OnePoints, TwoPoints);
		return panel;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub'
		
		frame = new JFrame ("BomberMan");
		frame.setLocation(200,20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		panel = new finalPage("tie","Player 1", 546, 231);
		panel.setPreferredSize(new Dimension(680, 680));
		panel.setLayout(null);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}
