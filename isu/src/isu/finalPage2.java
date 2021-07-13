package isu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class finalPage2 extends JPanel implements ActionListener{

	private static JFrame frame;
	private static JPanel panel;
	private JButton menu;			// button to return to main menu
	private JButton exit;			// button to end program
	private JButton highscores;		// button to go to highscores page

	// buttons to open pop-up to collect name for highscores
	private JButton p1NameEnter;	
	private JButton p2NameEnter;

	// stores player points
	private int OnePoints;			
	private int TwoPoints;			
	
	// stores player objects
	private static Player p1;
	private static Player p2;

	// booleans to determine if the scores have been submitted yet (to prevent multiple entries) 
	private boolean p1Score = false; 
	private boolean p2Score = false;

	/*	finalPage2 Method
	 * parameters: the winner in a string, the loser in a string, player 1's points, player 2's points
	 * description: adds buttons to the panel that was created in finalPage class
	 * returns: void 	
	 */

	public finalPage2(String winner, String loser, int OnePoints, int TwoPoints, Player p1, Player p2) {
		p1Score = false;
		p2Score = false;
		
		frame = new JFrame ("BomberMan");
		frame.setLocation(200,20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		this.p1 = p1;
		this.p2 = p2;

		this.OnePoints = OnePoints;
		this.TwoPoints = TwoPoints;

		// calls part 1 of code that writes out the information using graphics
		panel = new finalPage(winner, loser, OnePoints, TwoPoints).getFinalPanel(winner, loser, OnePoints, TwoPoints);

		panel.setPreferredSize(new Dimension(680, 680));
		panel.setLayout(null);

		// adds buttons to panel over graphics
		JButton menu = new JButton("Main Menu");
		menu.setBounds(140, 500, 125, 60);
		menu.addActionListener(this);
		menu.setActionCommand("menu");
		panel.add(menu);

		JButton exit = new JButton("Exit");
		exit.setBounds(285, 500, 125, 60);
		exit.addActionListener(this);
		exit.setActionCommand("exit");
		panel.add(exit);
		
		JButton highscore = new JButton("High Scores");
		highscore.setBounds(425, 500, 125, 60);
		highscore.addActionListener(this);
		highscore.setActionCommand("highscore");
		panel.add(highscore);

		JButton p1NameEnter = new JButton ("Enter Score");
		p1NameEnter.setBounds(360, 383, 100, 20);
		p1NameEnter.addActionListener(this);
		p1NameEnter.setActionCommand("p1name");
		panel.add(p1NameEnter);

		JButton p2NameEnter = new JButton ("Enter Score");
		p2NameEnter.setBounds(360, 413, 100, 20);
		p2NameEnter.addActionListener(this);
		p2NameEnter.setActionCommand("p2name");
		panel.add(p2NameEnter);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);		
	}
	/*	actionPerformed Method
	 * parameters: ActionEvent
	 * description: handles button actions
	 * returns: void 	
	 */

	public void actionPerformed(ActionEvent a) {
		String eventName = a.getActionCommand();
		if(eventName.equals("menu")) {
			new MainMenu1(1);
			frame.dispose();
		}
		else if (eventName.equals("exit")) {
			System.exit(0);
		}
		// if either of the submit buttons are pressed a popup appears if it has not been done yet
		else if (eventName.equals("p1name")) {
			if (p1Score == false) {
				String name;
				name = JOptionPane.showInputDialog (panel, "Enter your name to submit your score to high scores!");
				if (name != null) {
					new HighScore(OnePoints, name, 1);
					p1Score = true;
				}
			}
			else {
				JOptionPane.showMessageDialog (panel, "Score Already Submitted!", "Message",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		else if (eventName.equals("p2name")) {
			if (p2Score == false) {
				String name;
				name = JOptionPane.showInputDialog (panel, "Enter your name to submit your score to high scores!");
				if (name != null) {
					new HighScore(TwoPoints, name, 2);	
					p2Score = true;
				}
			}
			else {
				JOptionPane.showMessageDialog (panel, "Score Already Submitted!", "Message",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}
		else if(eventName.equals("highscore")) {
			frame.dispose();
			new HighScoresPage().openHighScores();
		}


	}

	// getter methods
	public JPanel getFinalPanel() {
		return panel;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new finalPage2("Player 1","Player 2", 546, 231, p1, p2);
		}

}
