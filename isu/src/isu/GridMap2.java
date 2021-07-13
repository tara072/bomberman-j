package isu;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

//import TopBar.DecreaseSec;

public class GridMap2 extends JPanel implements KeyListener
{
	private static JFrame frame;
	private static JPanel panel;

	private static int windowLength = 680;			// stores window dimensions		// stores window dimensions
	private static int wallSize = 40;				// size of wall variables in pixels
	private static int drawcheck = 1;				// to ensure destroyable walls are only randonly generated once

	private static int settingLevel = 2;			// level from settings (automatically set to medium)
	private static int settingTime = 2;				// time from settings (automatically set to 2 minutes)

	public static HashSet<DestWall> destWallSet = new HashSet<DestWall>();	// hashset to hold DestWall objects
	public static double destWallThreshold;  // this is the percentage of the time Math.random decides to spawn a destructible wall

	//	private Timer t = new Timer (5, this);			// timer to continuously refresh and redraw the JFrame

	Player p1 = new Player(1,4);	// player 1 object
	Player p2 = new Player(2,4);	// player 2 object
	private static final Color BACKGROUND_COLOUR = new Color(169,169,169);

	private static String time = "";
	private static int timeInSec = 120000;
	//private static int timeLength = 180*1000;
	private static boolean start = true;

	//	private static String lives1 = "";
	//	private static String lives2 = "";


	public static boolean p1bomb = false;
	public static boolean p2bomb = false;
	private boolean exploTimer = false;
	private int exploTime = 0;
	public static HashSet<Bomb> bombSet = new HashSet<Bomb>();	// hashset to hold bomb objects
	public static int bombName = 1; // a name variable to insert bombs into hashset and easily remove

	private String winner;
	private String loser;
	private int OnePoints;
	private int TwoPoints;

	//public static HashMap<String, Integer> elementsMap = new HashMap<String, Integer>();
	/* Legend: 
	 * 0 = nothing
	 * 1 = wall
	 * 2 = destroyable wall
	 * 3 = enemy
	 * 4 = player
	 * note: [col][row] = type
	 */// map to hold all objects and locations
	private static int[][] locationsArray = new int[17][17]; // array to check surroundings

	public static TreeMap<String, Player> playerMap = new TreeMap <String, Player>();	// map to hold players
	private Iterator<Bomb> bombItr;

	//Description: it sets the initial variables (time, level), draws the permanent walls and adds them to the array and creates the player objects and adds them to the player map
	//Parameters: none
	//Return: N/A
	public GridMap2 (int settingLevel, int settingTime) {
		//t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timeInSec = 0;
		start = true;

		// sets setting level according to input from MainMenu1 
		if (settingLevel == 1) //Easy
		{
			GridMap2.settingLevel = 1;
			destWallThreshold = 0.75;
			Enemy.setMaxNumEnemies(5);
		}
		else if (settingLevel == 2) //Medium
		{
			GridMap2.settingLevel = 2;
			destWallThreshold = 0.50;
			Enemy.setMaxNumEnemies(10);
		}
		else if (settingLevel == 3) //Hard
		{
			GridMap2.settingLevel = 3;
			destWallThreshold = 0.25;
			Enemy.setMaxNumEnemies(15);
		}
		// sets time limit according to input from MainMenu1
		if (settingTime == 1) {
			GridMap2.settingTime = 1;
			timeInSec = 60000;//1 minute or 60k milliseconds
		}
		else if (settingTime == 2) {
			GridMap2.settingTime = 2;
			timeInSec = 120000;//2 minute or 120k milliseconds
		}
		else if (settingTime == 3) {
			GridMap2.settingTime = 3;
			timeInSec = 180000;//3 minute or 180k milliseconds
		}

		// sets surrounding walls in 2D location array
		for (int i = 0; i < 4; i++) {
			// top wall
			if (i == 0) {
				for (int col = 0; col <17 ; col++) {
					locationsArray[col][0] = 1;
				}
			}
			// bottom wall
			else if (i == 1) {
				for (int col = 0; col <17 ; col++) {
					locationsArray[col][16] = 1;
				}
			}
			// left wall
			else if (i == 2) {
				for (int row = 0; row <17 ; row++) {
					locationsArray[0][row] = 1;
				}
			}
			// right wall
			else if (i == 3) {
				for (int row = 0; row <17 ; row++) {
					locationsArray[16][row] = 1;
				}
			}
		}

		// sets inner block walls in 2D location array
		for (int row = 1; row < 16; row++) {
			if (row%2 == 0) {
				for (int col = 1; col < 16; col++) {
					if (col%2 == 0) {
						locationsArray[row][col] = 1;
					}
				}
			}
		}

		// adds beginning locations of players in 2D location array
		locationsArray[p1.getArrayLocationRow()][p1.getArrayLocationCol()] = 4;
		locationsArray[p2.getArrayLocationRow()][p2.getArrayLocationCol()] = 4;


		// puts players into map
		playerMap.put("P1", p1);
		playerMap.put("P2", p2);
	}

	//Description: Starts and draws the timer based on how long was chosen in the settings
	//Parameters: graphics
	//Return: N/A
	public void displayTimer(Graphics g)
	{
		DecreaseSec timer = new DecreaseSec();
		timer.start();
	}

	//Description: Draws everything and is constantly called to redraw
	//Parameters: graphics
	//Return: N/A
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(BACKGROUND_COLOUR);
		g.fillRect(0, windowLength, windowLength, 50);


		ImageObserver observer = null;
		
		g.setColor(new Color(0, 102, 0));
		g.fillRect(0, 0, 680, 680);
		

		//These 4 for loops draws the outside border wall
		for(int i = 0; i < windowLength/wallSize; i++)
		{
			Wall tempWall = new Wall();
			g.drawImage(tempWall.getWallImage(), wallSize*i, 0, observer);
		}

		for(int i = 0; i < windowLength/wallSize; i++)
		{
			Wall tempWall = new Wall();
			g.drawImage(tempWall.getWallImage(), wallSize*i, windowLength - wallSize, observer);

		}

		for(int i = 1; i < windowLength/wallSize - 1; i++)
		{
			Wall tempWall = new Wall();
			g.drawImage(tempWall.getWallImage(), 0, wallSize*i, observer);

		}

		//These 2 draws the inside walls that occupy every other space
		for(int i = 1; i < windowLength/wallSize - 1; i++)
		{
			Wall tempWall = new Wall();
			g.drawImage(tempWall.getWallImage(), windowLength - wallSize,  wallSize*i, observer);

		}

		for(int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
			{
				Wall tempWall = new Wall();
				g.drawImage(tempWall.getWallImage(), 2*wallSize + j*(2*wallSize),  2*wallSize + i*(2*wallSize), observer);
			}

		//creation of destructible blocks at first on the screen page
		if (drawcheck == 1) {
			for(int i = 0; i < 15; i++)
				for (int j = 0; j < 15; j++)
				{
					if(!((i == 0 && j == 0) || (i == 0 && j== 1) || (i == 1 && j == 0) ||
							(i == 14 && j == 13) || (i == 14 && j == 14) || (i == 13 && j == 7) ))//This doesn't allow walls to be spawned in the spawn area
					{
						if(Math.random() > destWallThreshold)
						{
							//This spawns them between the static walls
							if(i%2 == 1)
							{
								if(j > 7)
									continue;
								else
								{
									int xCord = wallSize + j*2*wallSize;
									int yCord = wallSize + i*wallSize;
									DestWall tempWall = new DestWall(xCord,yCord);
									g.drawImage(tempWall.getDestWallImage(), xCord, yCord, observer);
									destWallSet.add(tempWall);
								}
							}
							else//This spawns them when there are no static walls in that row
							{
								int xCord = wallSize + j*wallSize;
								int yCord = wallSize + i*wallSize;
								DestWall tempWall = new DestWall(xCord,yCord);
								g.drawImage(tempWall.getDestWallImage(), xCord, yCord, observer);
								destWallSet.add(tempWall);

							}
						}
					}
				}
			drawcheck ++;

			// adds destroyable walls to 2D locations array
			for (DestWall destWall : destWallSet) {
				int row = (destWall.getDestWallx()/40);
				int col = (destWall.getDestWally()/40);
				locationsArray[col][row] = 2;

			}
			//For the entire map adds the enemies
			for (int i = 1; i < 16; i++)
				for (int j = 1; j < 16; j++)
					if(locationsArray[i][j] == 0 && Enemy.getTotalNumEnemies() < Enemy.getMaxNumEnemies())//if the grid location is empty and there is less than the maximum amount of enemies, spawn one
						if(Math.random() > 0.95) //sometimes spawn
						{
							int oneOrTwo = (int)(Math.round(Math.random()+1));
							Enemy tempEnemy = new Enemy(i,j,oneOrTwo);
							locationsArray[i][j] = 3;

						}

		}
		// after first creation of game screen it draws all the breakable walls
		else if (drawcheck > 1 && destWallSet.size() > 0){
			for (DestWall destWall : destWallSet) {
				g.drawImage(destWall.getDestWallImage(), destWall.getDestWallx(), destWall.getDestWally(), observer);	
			}

		}
		// updates player locations and redraws
		p1.updatePos(locationsArray);
		p2.updatePos(locationsArray);
		updateLocArr();
		p1.drawPlayer(g, observer);
		p2.drawPlayer(g, observer);


		//Updates and draws the enemies
		ListIterator<Enemy> iter = Enemy.getEnemyList().listIterator();
		while(iter.hasNext())
		{
			Enemy tempEnemy = iter.next();
			
			//These check if the enemy touches a player and takes a life and removes the enemy
			if(tempEnemy.getArrayLocationRow() == p1.getArrayLocationRow() && tempEnemy.getArrayLocationCol() == p1.getArrayLocationCol())
			{
				p1.minusLives();
				iter.remove();
				break;
			}
			if(tempEnemy.getArrayLocationRow() == p2.getArrayLocationRow() && tempEnemy.getArrayLocationCol() == p2.getArrayLocationCol())
			{
				p2.minusLives();
				iter.remove();
				break;
			}

			tempEnemy.updateEnemyPos(locationsArray);
			tempEnemy.drawEnemy(g, observer);
			updateLocArr();

		}
		
		// if the buttons to place bombs are placed, the location of the current player is collected
		// the location is then used to create a new bomb and add it to both the set of bombs
		// and the local array
		if (p1bomb == true) {
			int bombRow = p1.getArrayLocationRow();
			int bombCol = p1.getArrayLocationCol();
			bombSet.add(new Bomb(bombCol, bombRow, g, observer, "p1"));
			updateLocArr();
			p1bomb = false;
		}
		if (p2bomb == true) {
			int bombRow = p2.getArrayLocationRow();
			int bombCol = p2.getArrayLocationCol();
			bombSet.add(new Bomb(bombCol, bombRow, g, observer, "p2"));
			updateLocArr();
			p2bomb = false;
		}

		// if bombs have been created, then they'd be in this set
		// this goes through the set and redraws the bombs if they have not exploded by checking the explode boolean
		// ensuring they stay in the same position by drawing them based on their original locations. If the
		// timer for the bomb is up, the drawExplo method is called for the bomb (see bomb class) and the bomb is removed from the set. 
		if (bombSet.size()>0) {
			bombItr = bombSet.iterator();
			while(bombItr.hasNext()){
				Bomb temp = bombItr.next();
				if (temp.getExplodeStatus() == false) {
					temp.drawBomb(g, observer);
					updateLocArr();
				}
				else {
					temp.drawExplo(g, observer, locationsArray, destWallSet, playerMap, Enemy.getEnemyList());
					//					if (exploTime == 0)
					//						exploTimer = true;
					bombItr.remove();
					updateLocArr();

				}
			}

		}
		// Displays and starts the timer only once at the beginning
		if (start == true) {
			displayTimer (g);
			start = false;
		}

		//Draws the actual number for the Timer
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(time, windowLength/2 - 30, windowLength + 40);

		// when one's lives are all gone, this determines the winner by comparing points and lives
		// the winner and loser is added to strings which are used to create the final screen		
		if (p1.getLives() > 0 && p2.getLives() > 0) 
		{
			for(int i = 0; i < p1.getLives(); i++)
				g.drawImage(p1.getLivesImage(), i*35 + 10, windowLength + 10, 30, 30, observer);
			for(int i = 0; i < p2.getLives(); i++)
				g.drawImage(p2.getLivesImage(), windowLength - (40 + i*35), windowLength + 10, 30, 30, observer);
		}
		else if (p1.getLives() == 0 || p2.getLives() == 0){
			OnePoints = p1.getPoints();
			TwoPoints = p2.getPoints();


			if (p1.getLives() > p2.getLives()) {
				winner = "Player 1";
				loser = "Player 2";
			}
			else if (p1.getLives() < p2.getLives()) {
				winner = "Player 2";
				loser = "Player 1";
			}
			else  if (p1.getLives() == p2.getLives()){
				if (p1.getPoints() > p2.getPoints()) {
					winner = "Player 1";
					loser = "Player 2";
				}
				else if (p1.getPoints() < p2.getPoints()) {
					winner = "Player 2";
					loser = "Player 1";
				}
				else if (p1.getPoints() == p2.getPoints()) {
					winner = "Tie";
					loser = "";
				}
			}
			frame.dispose();
			drawcheck --;
			new finalPage2(winner,loser, OnePoints, TwoPoints, p1, p2);
		}

	}

	//Description: Checks which key is pressed and acts accordingly
	//Parameters: key events
	//Return: N/A
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			p2.setCurrentImage("up");
			p2.setDirection("up");
		}
		if (code == KeyEvent.VK_DOWN) {
			p2.setCurrentImage("down");
			p2.setDirection("down");
		}
		if (code == KeyEvent.VK_RIGHT) {
			p2.setCurrentImage("right");
			p2.setDirection("right");
		}
		if (code == KeyEvent.VK_LEFT) {
			p2.setCurrentImage("left");
			p2.setDirection("left");
		}
		if (code == KeyEvent.VK_W) {
			p1.setCurrentImage("up");
			p1.setDirection("up");;
		}
		if (code == KeyEvent.VK_S) {
			p1.setCurrentImage("down");
			p1.setDirection("down");;
		}
		if (code == KeyEvent.VK_D) {
			p1.setCurrentImage("right");				
			p1.setDirection("right");;
		}
		if (code == KeyEvent.VK_A) {
			p1.setCurrentImage("left");				
			p1.setDirection("left");;
		}
		if (code == KeyEvent.VK_SHIFT) {
			p2bomb = true;
		}
		if (code == KeyEvent.VK_SPACE) {
			p1bomb = true;
		}

	}

	//Description: Makes the player stay still when the keys are released
	//Parameters: key events
	//Return: N/A
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_RIGHT||code == KeyEvent.VK_LEFT||code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN) {
			p2.setDirection("stay");
		}
		if (code == KeyEvent.VK_A||code == KeyEvent.VK_D||code == KeyEvent.VK_W || code == KeyEvent.VK_S) {
			p1.setDirection("stay");
		}

	}

	//Description: Thread that keeps track of time and runs code at specific intervals
	//Parameters: key events
	//Return: N/A
	public class DecreaseSec extends Thread
	{
		public void run ()
		{
			
			while (timeInSec > 0)
			{
				if (timeInSec % 1000 == 0)//every second or 1000 milliseconds update the count down clock
				{
					//System.out.println (timeInSec +":" + timeInSec/1000 %60);
					String sec = "" + (timeInSec/1000 %60);
					if (sec.length() == 1)
						sec = "0" +  sec;
					time = timeInSec/1000/60 + ":" + sec;

				}
				try
				{
					sleep (1);
				}
				catch (Exception e)
				{}

				if(timeInSec%200 == 0)//more frequently but not every millisecond it randomly changes the direction of all enemies
				{
					ListIterator<Enemy> iter = Enemy.getEnemyList().listIterator();
					while(iter.hasNext())
					{
						Enemy tempEnemy = iter.next();
						int direction = (int)(Math.random()*4);
						if (direction == 0)
							tempEnemy.setDirection("up");
						if (direction == 1)
							tempEnemy.setDirection("down");
						if (direction == 2)
							tempEnemy.setDirection("right");
						if (direction == 3)
							tempEnemy.setDirection("left");
					}
				}

				repaint ();

				timeInSec--;

			}
			// when the time is up, this determines the winner by comparing points and lives
			// the winner and loser is added to strings which are used to create the final screen
			if (timeInSec <= 0) {
				OnePoints = p1.getPoints();
				TwoPoints = p2.getPoints();
				if (p1.getPoints() > p2.getPoints()) {
					winner = "Player 1";
					loser = "Player 2";
				}
				else if (p1.getPoints() < p2.getPoints()) {
					winner = "Player 2";
					loser = "Player 1";
				}
				else if (p1.getPoints() == p2.getPoints()) {
					if (p1.getLives() > p2.getLives()) {
						winner = "Player 1";
						loser = "Player 2";
					}
					else if (p1.getLives() < p2.getLives()) {
						winner = "Player 2";
						loser = "Player 1";
					}
					else  if (p1.getLives() == p2.getLives()){
						winner = "Tie";
						loser = "";
					}
				}
				frame.dispose();
				drawcheck --;
				new finalPage2(winner,loser, OnePoints, TwoPoints, p1, p2);
			}	
		}

	}	


	//Description: Recalculates the 2d array
	//Parameters: nothing
	//Return: N/A
	public void updateLocArr()
	{
		//first sets everything inside to zero
		for (int i = 1; i < 16; i++)
			for (int j = 1; j < 16; j++)
				locationsArray[i][j] = 0;
		// inside static walls
		for (int row = 1; row < 16; row++) 
			if (row%2 == 0) 
				for (int col = 1; col < 16; col++) 
					if (col%2 == 0) 
						locationsArray[row][col] = 1;
		//breakable walls
		for (DestWall destWall : destWallSet) 
		{
			int row = (destWall.getDestWallx()/40);
			int col = (destWall.getDestWally()/40);
			locationsArray[col][row] = 2;
		}
		//players
		locationsArray[p1.getArrayLocationRow()][p1.getArrayLocationCol()] = 4;
		locationsArray[p2.getArrayLocationRow()][p2.getArrayLocationCol()] = 4;
		//enemies
		ListIterator<Enemy> iter = Enemy.getEnemyList().listIterator();
		while(iter.hasNext())
		{
			Enemy tempEnemy = iter.next();
			locationsArray[tempEnemy.getArrayLocationRow()][tempEnemy.getArrayLocationCol()] = 3;
		}


	}
	/*	getGridMapFrame Method
	 * parameters: none
	 * description: returns the panel containing GridMap
	 * returns: JPanel 	
	 */

	public JPanel getGameBoard() {
		panel = new GridMap2(settingLevel, settingTime);
		return panel;
	}

	/*	getGridMapFrame Method
	 * parameters: none
	 * description: returns the frame containing GridMap
	 * returns: JFrame 	
	 */

	public JFrame getGridMapFrame() {
		frame = new JFrame ("BomberMan");
		frame.setLocation(200,20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		panel = new GridMap2(settingLevel, settingTime);
		panel.setPreferredSize(new Dimension(windowLength, windowLength+50));
		panel.setLayout(null);

		frame.add(panel);
		return frame;
	}
	public static int[][] getArray(){
		return locationsArray;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		frame = new JFrame ("BomberMan");
		frame.setLocation(200,20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		panel = new GridMap2(settingLevel, settingTime);
		panel.setPreferredSize(new Dimension(windowLength, windowLength+50));
		panel.setLayout(null);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}

}
