package isu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class settings extends JPanel implements ActionListener {
	
	private JFrame frame;		// frame
	private JPanel panel;		// panel

	private JLabel title;		// title image
	private JLabel timeLabel;	// time image
	private JLabel levelLabel;	// level image
	
	private JLabel timeNotif;	// labels to indicate selected option
	private JLabel levelNotif;

	private JButton time1;		// time settings button 1
	private JButton time2;		// time settings button 2
	private JButton time3;		// time settings button 3
	private JButton level1;		// level settings button 1
	private JButton level2;		// level settings button 2
	private JButton level3;		// level settings button 3
	private JButton backtomenu; // button to return to the menu
	
	private int time = 2;			// time settings to set up game
	private int level = 2;			// level settings to set up game
	
	private static int frameWidth = 680;
	
	/*	settings Method
	 * parameters: none
	 * description: draws buttons and images onto panel
	 * returns: void 	
	 */
	public settings() {
		// panel settings
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		
		// title image settings
		title = new JLabel (new ImageIcon ("settings title.png"));
		title.setBounds(1, 20, 680, 85);
		panel.add(title);

		// time image settings
		timeLabel = new JLabel (new ImageIcon ("settings time.png"));
		timeLabel.setBounds(50, 150, 250, 50);
		panel.add(timeLabel);

		// time buttons
		time1 = new JButton ("1 min");
		time1.setBounds(50, 220, 250, 50);
		time1.addActionListener(this);
		time1.setActionCommand("time1");
		panel.add(time1);

		time2 = new JButton ("2 min");
		time2.setBounds(50, 290, 250, 50);
		time2.addActionListener(this);
		time2.setActionCommand("time2");
		panel.add(time2);

		time3 = new JButton ("3 min");
		time3.setBounds(50, 360, 250, 50);
		time3.addActionListener(this);
		time3.setActionCommand("time3");
		panel.add(time3);
		
		// time notif label to show what time was selected
		timeNotif = new JLabel ("Current Game Length: 2 minutes");
		timeNotif.setBounds(70, 500, 250, 30);
		panel.add(timeNotif);
		
		// level image settings
		levelLabel = new JLabel(new ImageIcon ("settings level.png"));
		levelLabel.setBounds(360, 150, 270, 50);
		panel.add(levelLabel);
	
		// level buttons
		level1 = new JButton ("Easy");
		level1.setBounds(360, 220, 270, 50);
		level1.addActionListener(this);
		level1.setActionCommand("level1");
		panel.add(level1);

		level2 = new JButton ("Medium");
		level2.setBounds(360, 290, 270, 50);
		level2.addActionListener(this);
		level2.setActionCommand("level2");
		panel.add(level2);
		
		level3 = new JButton ("Hard");
		level3.setBounds(360, 360, 270, 50);
		level3.addActionListener(this);
		level3.setActionCommand("level3");
		panel.add(level3);
		
		// level notif label
		levelNotif = new JLabel ("Current Game Level: Medium");
		levelNotif.setBounds(400, 490, 270, 50);
		panel.add(levelNotif);
		
	}
	
	/*	actionPerformed Method
	 * parameters: ActionEvent
	 * description: controls the button events
	 * returns: void 	
	 */

	public void actionPerformed(ActionEvent a) {
		String eventName = a.getActionCommand();
		// changes time variables according to button clicked
		if(eventName.equals("time1")) {
			timeNotif.setText("Current Game Length: 1 minutes");
			time = 1;
		}
		else if (eventName.equals("time2")) {
			timeNotif.setText("Current Game Length: 2 minutes");
			time = 2;
		}
		else if (eventName.equals("time3")) {
			timeNotif.setText("Current Game Length: 3 minutes");
			time = 3;
		}
		// changes level variables according to button clicked
		else if (eventName.equals("level1")) {
			levelNotif.setText("Current Game Level: Easy");
			level = 1;
		}
		else if (eventName.equals("level2")) {
			levelNotif.setText("Current Game Level: Medium");
			level = 2;
		}
		else if (eventName.equals("level3")) {
			levelNotif.setText("Current Game Level: Hard");
			level = 3;
		}
	}
	
	// getter methods
	public JPanel getSettings() {	
		return panel;
	}
	
	public int getTime() {
		return time;
	}
	
	public int getLevel () {
		return level;
	}


}
