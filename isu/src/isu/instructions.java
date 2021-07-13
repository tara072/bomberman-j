// this is the instructions page class
package isu;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.*;

public class instructions extends JPanel {

	private static JFrame frame;
	private static JPanel panel;

	
	/*	paintComponent Method
	 * parameters: Graphics
	 * description: draws the text from a text file onto the panel
	 * returns: void 	
	 */

	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		ImageObserver observer = null;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 680, 680);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("Instructions",220,60); 
		g.drawImage(new ImageIcon("front.png").getImage(),120 , 20, 50, 50, observer);
		g.drawImage(new ImageIcon("front blue.png").getImage(),520 , 20, 50, 50, observer);
		g.setFont(new Font("Arial", Font.PLAIN, 20));

		try {
			// reads instructions from a txt file
			BufferedReader inFile = new BufferedReader(new FileReader("instructions.txt"));
			int y = 100;
			for (int i = 1; i < 29; i++) {
				if (i == 1 || i == 5 || i == 9 || i == 13 || i == 21) {
					g.setFont(new Font("Arial", Font.BOLD, 20));
				}
				else {
					g.setFont(new Font("Arial", Font.PLAIN, 20));
				}
				g.drawString(inFile.readLine(), 10, y);
				y += 20;
			}
			inFile.close();
		} 
		catch (FileNotFoundException e) {}
		catch (@SuppressWarnings("hiding") IOException e) {}
	}

	/*	getInstructionsPanel Method
	 * parameters: none
	 * description: returns the JPanel created to display in the MainMenu
	 * returns: JPanel 	
	 */

	public JPanel getInstructionsPanel() {	
		panel = new instructions();
		return panel;
	}

}
