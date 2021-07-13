package isu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Bomb implements ActionListener {

	// bomb images
	private Image bImage = new ImageIcon("bomb.png").getImage();	// main image

	private Image bombImage = new ImageIcon("bomb.png").getImage();	
	private Image upExpImage = new ImageIcon("topEndExplo.png").getImage();
	private Image downExpImage = new ImageIcon("botEndExplo.png").getImage();
	private Image leftExpImage = new ImageIcon("leftEndExplo.png").getImage();
	private Image rightExpImage = new ImageIcon("rightEndExplo.png").getImage();
	private Image horiExpImage = new ImageIcon("horiExplo.png").getImage();
	private Image vertExpImage = new ImageIcon("vertExplo.png").getImage();
	private Image midExpImage = new ImageIcon("midExplo.png").getImage();

	// location of bomb according to the array in GridMap class
	private int arrayLocationCol;	
	private int arrayLocationRow;
	
	// location of the bomb in coordinates
	private int x;
	private int y;
	
	// inputs
	private ImageObserver observer;
	private Graphics g;
	private String player;

	// boolean to determine wether or not the timer is complete
	private boolean explode = false;

	private Timer t;	// timer for until bomb explodes

	/*	bomb Method
	 * parameters: the locations in the array of elements, Graphics, ImageObserver, and the
	 * player that placed the bomb
	 * description:  assigns variables, sets and starts teh 4 second timer and draws the bomb
	 * returns: void 	
	 */

	public Bomb(int arrayCol, int arrayRow, Graphics g, ImageObserver observer, String player) {
		// adds all variables
		this.arrayLocationCol = arrayCol;
		this.arrayLocationRow = arrayRow;
		this.y = (arrayLocationRow * 40);
		this.x = (arrayLocationCol * 40);
		this.observer = observer;
		this.g = g;
		this.player = player;
		this.explode = false;

		// starts 4 second timer
		t = new Timer(4000, this);
		t.start();
		bImage = bombImage;

		// draws bomb
		drawBomb(g, observer);
	}

	/*	drawBomb Method
	 * parameters: Graphics and ImageObserver
	 * description: draws bomb object
	 * returns: void 	
	 */

	public void drawBomb(Graphics g, ImageObserver observer)
	{
		g.drawImage(bombImage, x, y, 40, 40, observer);
	}

	/*	drawExplo Method
	 * parameters: Graphics, ImageObserver, the array of elements and their locations, a hashset of destroyable walls,
	 * a treeMap of players, a linkedList of enemies
	 * description: calulates affects of bomb explosion and draws it. It does so by going in each direction on by one.
	 * For each direction, it checks the first space, acts accordingly before checking the second space and then acting accordingly. 
	 * returns: void 	
	 */

	public void drawExplo(Graphics g, ImageObserver observer, int[][] locArr, HashSet<DestWall> destSet, TreeMap<String, Player> playerMap, LinkedList<Enemy> enemyList) {
		// if the bomb is on top of a player
		if (locArr[arrayLocationRow][arrayLocationCol] == 4) {
			// find which player
			if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
				playerMap.get("P1").minusLives();
				if (player == "p2") {
					playerMap.get("P2").addPointsPlayer();
				}
			}
			if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
				playerMap.get("P2").minusLives();
				if (player == "p1") {
					playerMap.get("P1").addPointsPlayer();
				}
			}
			g.drawImage(midExpImage, x, y, 40, 40, observer);

		}
		else if (locArr[arrayLocationRow][arrayLocationCol] == 2) {
			destSet.remove(new DestWall(x, y));
			locArr[arrayLocationRow][arrayLocationCol] = 0;
			blockPoints(playerMap);
			g.drawImage(midExpImage, x, y, 40, 40, observer);
		}
		else if (locArr[arrayLocationRow][arrayLocationCol] == 3) {
			for (Enemy enemy: enemyList) {
				if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol) {
					enemyList.remove(enemy);
				}
			}
			g.drawImage(midExpImage, x, y, 40, 40, observer);
		}
		else {
			g.drawImage(midExpImage, x, y, 40, 40, observer);
		}

		try {

			// upwards explosion
			// if the first space is empty
			if (locArr[arrayLocationRow-1][arrayLocationCol] == 0) { 
				// if the second space is empty
				if (locArr[arrayLocationRow-2][arrayLocationCol] == 0 ) {
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 2) {
					destSet.remove(new DestWall(x, y-80));
					locArr[arrayLocationRow-2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow - 2) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow - 2) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow-2 && enemy.getArrayLocationCol() == arrayLocationCol) {
							enemyList.remove(enemy);
						}
					}

					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(upExpImage,x,y-40,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow-1][arrayLocationCol] == 2) {
				destSet.remove(new DestWall(x, y-40));
				locArr[arrayLocationRow-1][arrayLocationCol] = 0;
				blockPoints(playerMap);

				// if the second space is empty
				if (locArr[arrayLocationRow-2][arrayLocationCol] == 0 ) {
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 2) {
					destSet.remove(new DestWall(x, y-80));
					locArr[arrayLocationRow-2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow - 2) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow - 2) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow-2 && enemy.getArrayLocationCol() == arrayLocationCol) {
							enemyList.remove(enemy);
						}
					}
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(upExpImage,x,y-40,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow-1][arrayLocationCol] == 4) {

				// finding which player
				if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow - 1) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
					playerMap.get("P1").minusLives();
					if (player == "p2") {
						playerMap.get("P2").addPointsPlayer();
					}
				}
				if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow - 1) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
					playerMap.get("P2").minusLives();
					if (player == "p1") {
						playerMap.get("P1").addPointsPlayer();
					}
				}
				// if the second space is empty
				if (locArr[arrayLocationRow-2][arrayLocationCol] == 0 ) {
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 2) {
					destSet.remove(new DestWall(x, y-80));
					locArr[arrayLocationRow-2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow - 2) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow - 2) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow-2 && enemy.getArrayLocationCol() == arrayLocationCol) {
							enemyList.remove(enemy);
						}
					}					
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(upExpImage,x,y-40,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow-1][arrayLocationCol] == 3) {
				for (Enemy enemy: enemyList) {
					if (enemy.getArrayLocationRow() == arrayLocationRow-1 && enemy.getArrayLocationCol() == arrayLocationCol) {
						enemyList.remove(enemy);
					}
				}					
				
				// finding which player
				if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow - 1) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
					playerMap.get("P1").minusLives();
					if (player == "p2") {
						playerMap.get("P2").addPointsPlayer();
					}
				}
				if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow - 1) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
					playerMap.get("P2").minusLives();
					if (player == "p1") {
						playerMap.get("P1").addPointsPlayer();
					}
				}
				// if the second space is empty
				if (locArr[arrayLocationRow-2][arrayLocationCol] == 0 ) {
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 2) {
					destSet.remove(new DestWall(x, y-80));
					locArr[arrayLocationRow-2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow - 2) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow - 2) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow-2][arrayLocationCol] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow-2 && enemy.getArrayLocationCol() == arrayLocationCol) {
							enemyList.remove(enemy);
						}
					}					
					g.drawImage(vertExpImage,x,y-40,40, 40, observer);
					g.drawImage(upExpImage, x,y-80,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(upExpImage,x,y-40,40, 40, observer);
				}
			}



			// downwards explosion
			// if the first space is empty
			if (locArr[arrayLocationRow+1][arrayLocationCol] == 0) {
				// if the second space is empty
				if (locArr[arrayLocationRow+2][arrayLocationCol] == 0 ) {
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}				
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 2) {
					destSet.remove(new DestWall(x, y+80));
					locArr[arrayLocationRow+2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow + 2) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow + 2) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow+2 && enemy.getArrayLocationCol() == arrayLocationCol) {
							enemyList.remove(enemy);
						}
					}
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(upExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(downExpImage,x,y+40,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow+1][arrayLocationCol] == 2) {
				destSet.remove(new DestWall(x, y+40));
				locArr[arrayLocationRow+1][arrayLocationCol] = 0;
				blockPoints(playerMap);
				// if the second space is empty
				if (locArr[arrayLocationRow+2][arrayLocationCol] == 0 ) {
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 2) {
					destSet.remove(new DestWall(x, y+80));
					locArr[arrayLocationRow+2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow + 2) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow + 2) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow+2 && enemy.getArrayLocationCol() == arrayLocationCol) {
							enemyList.remove(enemy);
						}
					}
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(upExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(downExpImage,x,y+40,40, 40, observer);
				}

			}
			else if (locArr[arrayLocationRow+1][arrayLocationCol] == 4) {
				// finding which player
				if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow + 1) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
					playerMap.get("P1").minusLives();
					if (player == "p2") {
						playerMap.get("P2").addPointsPlayer();
					}
				}
				if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow + 1) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
					playerMap.get("P2").minusLives();
					if (player == "p1") {
						playerMap.get("P1").addPointsPlayer();
					}
				}
				// if the second space is empty
				if (locArr[arrayLocationRow+2][arrayLocationCol] == 0 ) {
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 2) {
					destSet.remove(new DestWall(x, y+80));
					locArr[arrayLocationRow+2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow + 2) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow + 2) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow+2 && enemy.getArrayLocationCol() == arrayLocationCol) {
							enemyList.remove(enemy);
						}
					}
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(upExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(downExpImage,x,y+40,40, 40, observer);
				}

			}
			else if (locArr[arrayLocationRow+1][arrayLocationCol] == 3) {
				for (Enemy enemy: enemyList) {
					if (enemy.getArrayLocationRow() == arrayLocationRow+1 && enemy.getArrayLocationCol() == arrayLocationCol) {
						enemyList.remove(enemy);
					}
				}									
				// finding which player
				if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow + 1) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
					playerMap.get("P1").minusLives();
					if (player == "p2") {
						playerMap.get("P2").addPointsPlayer();
					}
				}
				if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow + 1) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
					playerMap.get("P2").minusLives();
					if (player == "p1") {
						playerMap.get("P1").addPointsPlayer();
					}
				}
				// if the second space is empty
				if (locArr[arrayLocationRow+2][arrayLocationCol] == 0 ) {
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 2) {
					destSet.remove(new DestWall(x, y+80));
					locArr[arrayLocationRow+2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow + 2) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow + 2) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(downExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow+2][arrayLocationCol] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow+2 && enemy.getArrayLocationCol() == arrayLocationCol) {
							enemyList.remove(enemy);
						}
					}
					g.drawImage(vertExpImage,x,y+40,40, 40, observer);
					g.drawImage(upExpImage, x,y+80,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(downExpImage,x,y+40,40, 40, observer);
				}

			}


			// leftwards explosion
			// if the first space is empty
			if (locArr[arrayLocationRow][arrayLocationCol-1] == 0) {
				// if the second space is empty
				if (locArr[arrayLocationRow][arrayLocationCol-2] == 0 ) {
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 2) {
					destSet.remove(new DestWall(x, y-80));
					locArr[arrayLocationRow][arrayLocationCol-2] = 0;
					blockPoints(playerMap);
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol - 2)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol - 2)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol-2) {
							enemyList.remove(enemy);
						}
					}
					g.drawImage(vertExpImage,x-40,y,40, 40, observer);
					g.drawImage(upExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(leftExpImage,x-40,y,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow][arrayLocationCol-1] == 2) {
				destSet.remove(new DestWall(x-40, y));
				locArr[arrayLocationRow][arrayLocationCol-1] = 0;
				blockPoints(playerMap);
				// if the second space is empty
				if (locArr[arrayLocationRow][arrayLocationCol-2] == 0 ) {
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 2) {
					destSet.remove(new DestWall(x-80, y));
					locArr[arrayLocationRow-2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol - 2)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol - 2)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol-2) {
							enemyList.remove(enemy);
						}
					}					
					g.drawImage(vertExpImage,x-40,y,40, 40, observer);
					g.drawImage(upExpImage, x-80,y,40, 40, observer);
				}

				// if the second space is a solid wall
				else {
					g.drawImage(leftExpImage,x-40,y,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow][arrayLocationCol-1] == 4) {
				enemyList.remove(new Enemy(arrayLocationRow, arrayLocationCol-1, 1));
				enemyList.remove(new Enemy(arrayLocationRow, arrayLocationCol-1, 2));					
				// finding which player
				if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol - 1)) {
					playerMap.get("P1").minusLives();
					if (player == "p2") {
						playerMap.get("P2").addPointsPlayer();
					}
				}
				if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol - 1)) {
					playerMap.get("P2").minusLives();
					if (player == "p1") {
						playerMap.get("P1").addPointsPlayer();
					}
				}				
				// if the second space is empty
				if (locArr[arrayLocationRow][arrayLocationCol-2] == 0 ) {
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 2) {
					destSet.remove(new DestWall(x-80, y));
					locArr[arrayLocationRow-2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol - 2)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol - 2)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol-2) {
							enemyList.remove(enemy);
						}
					}					
					g.drawImage(vertExpImage,x-40,y,40, 40, observer);
					g.drawImage(upExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(leftExpImage,x-40,y,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow][arrayLocationCol-1] == 3) {
				for (Enemy enemy: enemyList) {
					if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol-1) {
						enemyList.remove(enemy);
					}
				}
				// finding which player
				if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol - 1)) {
					playerMap.get("P1").minusLives();
					if (player == "p2") {
						playerMap.get("P2").addPointsPlayer();
					}
				}
				if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol - 1)) {
					playerMap.get("P2").minusLives();
					if (player == "p1") {
						playerMap.get("P1").addPointsPlayer();
					}
				}				
				// if the second space is empty
				if (locArr[arrayLocationRow][arrayLocationCol-2] == 0 ) {
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 2) {
					destSet.remove(new DestWall(x-80, y));
					locArr[arrayLocationRow-2][arrayLocationCol] = 0;
					blockPoints(playerMap);
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol - 2)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol - 2)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(horiExpImage,x-40,y,40, 40, observer);
					g.drawImage(leftExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow][arrayLocationCol-2] == 3) {
					enemyList.remove(new Enemy(arrayLocationRow, arrayLocationCol-2, 1));
					enemyList.remove(new Enemy(arrayLocationRow, arrayLocationCol-2, 2));					
					g.drawImage(vertExpImage,x-40,y,40, 40, observer);
					g.drawImage(upExpImage, x-80,y,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(leftExpImage,x-40,y,40, 40, observer);
				}
			}



			// rightwards explosion
			// if the first space is empty
			if (locArr[arrayLocationRow][arrayLocationCol+1] == 0) {
				// if the second space is empty
				if (locArr[arrayLocationRow][arrayLocationCol+2] == 0 ) {
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 2) {
					destSet.remove(new DestWall(x, y+80));
					locArr[arrayLocationRow][arrayLocationCol+2] = 0;
					blockPoints(playerMap);
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol + 2)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol + 2)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol+2) {
							enemyList.remove(enemy);
						}
					}
					g.drawImage(vertExpImage,x+40,y,40, 40, observer);
					g.drawImage(upExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(rightExpImage,x+40,y,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow][arrayLocationCol+1] == 2) {
				destSet.remove(new DestWall(x+40, y));
				locArr[arrayLocationRow][arrayLocationCol+1] = 0;
				blockPoints(playerMap);
				// if the second space is empty
				if (locArr[arrayLocationRow][arrayLocationCol+2] == 0 ) {
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 2) {
					destSet.remove(new DestWall(x+80, y));
					locArr[arrayLocationRow][arrayLocationCol+2] = 0;
					blockPoints(playerMap);
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol + 2)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol + 2)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol+2) {
							enemyList.remove(enemy);
						}
					}
					g.drawImage(vertExpImage,x+40,y,40, 40, observer);
					g.drawImage(upExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(rightExpImage,x+40,y,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow][arrayLocationCol+1] == 4) {
				// finding which player
				if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol + 1)) {
					playerMap.get("P1").minusLives();
					if (player == "p2") {
						playerMap.get("P2").addPointsPlayer();
					}
				}
				if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol + 1)) {
					playerMap.get("P2").minusLives();
					if (player == "p1") {
						playerMap.get("P1").addPointsPlayer();
					}
				}
				// if the second space is empty
				if (locArr[arrayLocationRow][arrayLocationCol+2] == 0 ) {
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 2) {
					destSet.remove(new DestWall(x+80, y));
					locArr[arrayLocationRow][arrayLocationCol+2] = 0;
					blockPoints(playerMap);
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol + 2)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol + 2)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol+2) {
							enemyList.remove(enemy);
						}
					}
					g.drawImage(vertExpImage,x+40,y,40, 40, observer);
					g.drawImage(upExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(rightExpImage,x+40,y,40, 40, observer);
				}
			}
			else if (locArr[arrayLocationRow][arrayLocationCol+1] == 3) {
				for (Enemy enemy: enemyList) {
					if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol+1) {
						enemyList.remove(enemy);
					}
				}					
				// finding which player
				if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol + 1)) {
					playerMap.get("P1").minusLives();
					if (player == "p2") {
						playerMap.get("P2").addPointsPlayer();
					}
				}
				if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol + 1)) {
					playerMap.get("P2").minusLives();
					if (player == "p1") {
						playerMap.get("P1").addPointsPlayer();
					}
				}
				// if the second space is empty
				if (locArr[arrayLocationRow][arrayLocationCol+2] == 0 ) {
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a destroyable wall
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 2) {
					destSet.remove(new DestWall(x+80, y));
					locArr[arrayLocationRow][arrayLocationCol+2] = 0;
					blockPoints(playerMap);
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a player
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 4) {
					// finding which player
					if (playerMap.get("P1").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P1").getArrayLocationCol() == (arrayLocationCol + 2)) {
						playerMap.get("P1").minusLives();
						if (player == "p2") {
							playerMap.get("P2").addPointsPlayer();
						}
					}
					if (playerMap.get("P2").getArrayLocationRow() == (arrayLocationRow) && playerMap.get("P2").getArrayLocationCol() == (arrayLocationCol + 2)) {
						playerMap.get("P2").minusLives();
						if (player == "p1") {
							playerMap.get("P1").addPointsPlayer();
						}
					}
					g.drawImage(horiExpImage,x+40,y,40, 40, observer);
					g.drawImage(rightExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is an enemy
				else if (locArr[arrayLocationRow][arrayLocationCol+2] == 3) {
					for (Enemy enemy: enemyList) {
						if (enemy.getArrayLocationRow() == arrayLocationRow && enemy.getArrayLocationCol() == arrayLocationCol+2) {
							enemyList.remove(enemy);
						}
					}					
					g.drawImage(vertExpImage,x+40,y,40, 40, observer);
					g.drawImage(upExpImage, x+80,y,40, 40, observer);
				}
				// if the second space is a solid wall
				else {
					g.drawImage(rightExpImage,x+40,y,40, 40, observer);
				}
			}

		}
		catch(ArrayIndexOutOfBoundsException e) {

		}

	}

	/*	actionPerformed Method
	 * parameters: ActionEvent
	 * description: turns off the timer and sets the boolean explode to true when the timer is up
	 * returns: void 	
	 */

	public void actionPerformed(ActionEvent e) {
		//		System.out.println("boom");
		t.stop();
		this.explode = true;
	}

	/*	blockPoints Method
	 * parameters: TreeMap of players
	 * description: adds points to the player (method addPointsBlock in player class) when a block is destroyed
	 * returns: void 	
	 */

	public void blockPoints(TreeMap<String, Player> playerMap) {
		if (player == "p1") {
			playerMap.get("P1").addPointsBlock();
		}
		else if (player == "p2") {
			playerMap.get("P2").addPointsBlock();
		}
	}

	// getter method
	public boolean getExplodeStatus() {
		return explode;
	}
}

