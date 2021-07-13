package isu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HighScoresPage extends JPanel implements ActionListener {

	private static JFrame frame;
	private static JPanel panel;
	private static JButton menu;	// button to return to the main menu
	
	/* paintComponent Method
	 * parameters: Graphics 
	 * description: adds the coordinates of the wall to a map
	 * returns: void 	
	 */

	@Override
	public void paintComponent (Graphics g) {

		// sorts the array from highest score to smallest using the sortHighScores comparator
		Collections.sort(HighScore.getHSArray(), new sortHighScores());

		super.paintComponent(g);
		ImageObserver observer = null;

		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("High Scores",210,50); 

		g.setFont(new Font("Arial", Font.PLAIN, 30));

		Image p1 = new ImageIcon("front.png").getImage();
		Image p2 = new ImageIcon("front blue.png").getImage();

		int y = 100;
		for (int i = 0; i < HighScore.getHSArray().size(); i++) {
			g.drawString(HighScore.getHSArray().get(i).getName(), 200, y);
			g.drawString(HighScore.getHSArray().get(i).getScoreString(), 400, y);
			if (HighScore.getHSArray().get(i).getPlayer() == 1) {
				g.drawImage(p1, 150, y-25, 30, 30, observer);
			}
			else if (HighScore.getHSArray().get(i).getPlayer() == 2) {
				g.drawImage(p2, 150, y-25, 30, 30, observer);
			}
			y += 40;
		}

	}

	/*	getHighScoresFrame Method
	 * parameters: none
	 * description: creates and returns frame holding the highscores page
	 * returns: JFrame 	
	 */

	public JFrame getHighScoresFrame() {
		frame = new JFrame ("BomberMan");
		frame.setLocation(200,20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		panel = new HighScoresPage();
		panel.setPreferredSize(new Dimension(680, 680));
		panel.setLayout(null);

		JButton menu = new JButton ("Main Menu");
		menu.setBounds(20, 10, 100, 40);
		menu.addActionListener(this);
		menu.setActionCommand("menu");
		panel.add(menu);
		
		frame.add(panel);

		return frame;
	}
	
	/*	getHighScoresFrame Method
	 * parameters: none
	 * description: creates and executes frame holding the highscores page
	 * returns: void 	
	 */

	public void openHighScores() {
		frame = new JFrame ("BomberMan");
		frame.setLocation(200,20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		panel = new HighScoresPage();
		panel.setPreferredSize(new Dimension(680, 680));
		panel.setLayout(null);

		JButton menu = new JButton ("Main Menu");
		menu.setBounds(20, 10, 100, 40);
		menu.addActionListener(this);
		menu.setActionCommand("menu");
		panel.add(menu);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		frame = new JFrame ("BomberMan");
		frame.setLocation(200,20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame = new HighScoresPage().getHighScoresFrame();
		frame.pack();
		frame.setVisible(true);		

	}

	/*	actionPerformed Method
	 * parameters: ActionEvent
	 * description: returns to the main menu on button click
	 * returns: void 	
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.dispose();
		new MainMenu1(1);
	}

}

