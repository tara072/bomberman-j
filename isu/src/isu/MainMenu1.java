// Name: Ivan Zhang, Tara Chow
// Date: January 18, 2020
/*Purpose: Create a game (Bomberman) using JFrames, Java and other requirements (a set, a map,
 * comparator/comparable).  
 */

package isu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@SuppressWarnings("serial")
public class MainMenu1 extends JFrame {

	private JFrame frame;
	private JPanel screen;

	private JLabel title;

	private JButton start;	
	private JButton instructions;	
	private JButton exit;
	private JButton settings;
	private JButton highscores;
	private JButton instructionsClose;
	private JButton settingsClose;
	
	// settings to change if changed in settings page and to start game
	private static int settingLevel = 2;
	private static int settingTime = 2;
	
	private static settings open = new settings();	// settings panel				
	
	/*	MainMenu1 Method
	 * parameters: screenType to determine what to open
	 * description: controls which panel or frame is opened and run
	 * returns: void 	
	 */

	public MainMenu1 (int screenType) 
	{
		frame = new JFrame ("Bomberman");
		frame.setLocation(75,20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		screen = new JPanel();
		screen.setPreferredSize(new Dimension(680, 680));

		// main menu (beginning screen)
		if (screenType == 1) {
			displayMainMenu();
		}
		// game
		else if (screenType == 2) {
			frame = new GridMap2(settingLevel, settingTime).getGridMapFrame();
			frame.pack();
			frame.setVisible(true);
		}
		// instructions
		else if (screenType == 3) {
			screen = new instructions().getInstructionsPanel(); 	// gets panel from instructions class
			screen.setPreferredSize(new Dimension(680, 680));
			instructionsClose = new JButton ("main menu");			// adds new button to exit instructions
			instructionsClose.addActionListener(new Listener());
			instructionsClose.setBounds(1, 1, 50, 50);
			instructionsClose.setActionCommand("infoclose");
			screen.add(instructionsClose);
			
			frame.add(screen);	// adds new screen panel
			frame.pack();
			frame.setVisible(true);
			}
		// settings
		else if (screenType == 4) {
			screen = open.getSettings();							// gets panel from settings class
			screen.setPreferredSize(new Dimension(680, 680));
			settingsClose = new JButton("main menu");				// adds new button to exit settings
			settingsClose.setBounds(280, 550, 100, 30);	
			settingsClose.addActionListener(new Listener());
			settingsClose.setActionCommand("settingsclose");
			screen.add(settingsClose);
			
			frame.add(screen);
			frame.pack();
			frame.setVisible(true);
		}
		// high scores
		else if (screenType == 5) {
			frame = new HighScoresPage().getHighScoresFrame();
//			frame.add(screen);
			frame.pack();
			frame.setVisible(true);
		}
	}
	
	/*	displayMainMenu Method
	 * parameters: none
	 * description: creates main menu page
	 * returns: void 	
	 */
	public void displayMainMenu()
	{
		screen.setLayout(null);
		screen.setBackground(Color.WHITE);

		// title logo picture
		ImageIcon icon = new ImageIcon("title resized.png");
		title = new JLabel (icon);
		screen.add(title);

		Dimension size = title.getPreferredSize();
		title.setBounds(25, 200, size.width, size.height);

		// start button to begin the game
		start = new JButton ("Start!");
		screen.add(start);
		start.addActionListener(new Listener());
		start.setActionCommand("start");
		start.setBounds(50, 400, 575, 60);

		// settings button to open the settings page
		settings = new JButton ("Settings!");
		screen.add(settings);
		settings.addActionListener(new Listener());
		settings.setActionCommand("settings");
		settings.setBounds(50, 475, 125, 60);

		// instructions button to open the instructions page
		instructions = new JButton ("Instructions!");
		screen.add(instructions);
		instructions.addActionListener(new Listener());
		instructions.setActionCommand("info");
		instructions.setBounds(200, 475, 125, 60);

		// highschores button to open the high scores page
		highscores = new JButton ("High Scores!");
		screen.add(highscores);
		highscores.addActionListener(new Listener());
		highscores.setActionCommand("highscore");
		highscores.setBounds(350, 475, 125, 60);

		// exit button to close the program
		exit = new JButton ("Exit!");
		screen.add(exit);
		exit.addActionListener(new Listener());
		exit.setActionCommand("exit");
		exit.setBounds(500, 475, 125, 60);

		// final compliation of the frame
		frame.add (screen);
		frame.pack ();
		frame.setVisible (true);//updates and shows window

	}

	// setter methods to get time and level changes from settings page
	public void setTime(int newTime) {
		this.settingTime = newTime;
	}
	
	public void setLevel(int newLevel) {
		this.settingLevel = newLevel;
	}

	
	public static void main(String[] args) {
		new MainMenu1(1);
	}
	

	class Listener implements ActionListener {
		/*	actionListener Method
		 * parameters: ActionEvent
		 * description: controls button actions
		 * returns: void 	
		 */
		@Override
		public void actionPerformed(ActionEvent a) {
			String eventName = a.getActionCommand();
			// begin game
			if(eventName.equals("start")) {
				// close current frame and open new game frame
				frame.dispose();
				new MainMenu1(2);
			}
			// 
			else if (eventName.equals("settings")) {
				// open settings page
				frame.dispose();
				new MainMenu1(4);
			}
			else if (eventName.equals("settingsclose")) {
				// close settings page
				MainMenu1 openmenu = new MainMenu1(1);
				openmenu.setTime(open.getTime());
				openmenu.setLevel(open.getLevel());
				frame.dispose();

			}
			else if (eventName.equals("info")) {
				// open instructions page
				frame.dispose();
				new MainMenu1(3);
			}
			else if (eventName.equals("infoclose")) {
				// close instructions page
				frame.dispose();
				new MainMenu1(1);
			}
			else if (eventName.equals("highscore")) {
				// open high score page
				frame.dispose();
				new HighScoresPage().openHighScores();
			}
			else if (eventName.equals("exit")) {
				// exit program
				System.exit(0);
			}

		}
	}


}


