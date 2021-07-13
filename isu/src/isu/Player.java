package isu;

import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.*;

public class Player {
	private int x;
	private int y;
	private int speed = 4;

	private Image upImage;
	private Image downImage;
	private Image rightImage;
	private Image leftImage;
	private Image currentImage;

	private int playerNum;
	private int velx;
	private int vely;

	private int arrayLocationRow;
	private int arrayLocationCol;

	private int lives;
	private String direction = "stay";
	private int points;

	public Player(int player, int speed)
	{
		playerNum = player;
		if (playerNum == 1)
			player1();
		else 
			player2();
	}

	private void player1()
	{
		upImage = new ImageIcon("back.png").getImage();
		downImage = new ImageIcon("front.png").getImage();
		rightImage = new ImageIcon("right.png").getImage();
		leftImage = new ImageIcon("left.png").getImage();
		currentImage = downImage;

		x = 45;
		y = 45;

		arrayLocationRow = 1;
		arrayLocationCol = 1;
		this.lives = 3;
		points = 0;
	}

	private void player2()
	{
		upImage = new ImageIcon("back blue.png").getImage();
		downImage = new ImageIcon("front blue.png").getImage();
		rightImage = new ImageIcon("right blue.png").getImage();
		leftImage = new ImageIcon("left blue.png").getImage();
		currentImage = downImage;

		x = 605;
		y = 605;

		arrayLocationRow = 15;
		arrayLocationCol = 15;
		this.lives = 3;
		points = 0;

	}

	// getter methods

	public int getArrayLocationRow () {
		return arrayLocationRow;
	}

	public int getArrayLocationCol() {
		return arrayLocationCol;
	}

	public int getLives() {
		return this.lives;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	
	public boolean livesStatus() {
		if (lives > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getPoints() {
		return points;
	}
	public Image getLivesImage()
	{
		return downImage;
	}

	// setter methods

	public void minusLives() {
		this.lives -=1;
	}

	public void setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setVelY(int vely)
	{
		this.vely = vely;
	}
	public void setVelX(int velx)
	{

		this.velx = velx;
	}
	
	public void addPointsBlock() {
		this.points += 5;
	}
	
	public void addPointsEnemy() {
		this.points += 20;
	}
	
	public void addPointsPlayer() {
		this.points += 50;
	}
	
	// other methods

//	public void up(int[][] locArr) 
//	{			
//		int tempArrRow = (y-speed)/40;
//		//		System.out.println(arrayLocationRow+ " vs " + tempArrRow);
//		if (tempArrRow == arrayLocationRow || (locArr[tempArrRow][arrayLocationCol] == 0 && locArr[tempArrRow][(x+29)/40] == 0 ))
//		{
//			//			System.out.println("up to " + tempArrRow);
//			//			System.out.println((tempArrRow == arrayLocationRow) + " : " + (locArr[tempArrRow][arrayLocationCol] == 0));
//			vely = -1*speed;
//			velx = 0;
//
//		}
//		else 
//		{
//			vely = 0;
//			velx = 0;
//		}
//		updatePos();
//	}
//
//	public void down(int[][] locArr) 
//	{
//		int tempArrRow = (y+29+speed)/40;
//		//		System.out.println(arrayLocationRow+ " vs " + tempArrRow);
//		if (tempArrRow == arrayLocationRow || (locArr[tempArrRow][arrayLocationCol] == 0 && locArr[tempArrRow][(x+29)/40] == 0))
//		{
//			//			System.out.println("down to " + tempArrRow);
//			//			System.out.println((tempArrRow == arrayLocationRow) + " : " + (locArr[tempArrRow][arrayLocationCol] == 0));
//			vely = speed;
//			velx = 0;
//		}
//		else 
//		{
//			vely = 0;
//			velx = 0;
//		}
//		updatePos();
//
//	}
//
//	public void left(int[][] locArr) 
//	{
//		int tempArrCol = (x-speed)/40;
//		//		System.out.println(arrayLocationRow+ " vs " + tempArrCol);
//		if (tempArrCol == arrayLocationCol || (locArr[arrayLocationRow][tempArrCol] == 0 && locArr[(y+29)/40][tempArrCol] == 0))
//		{
//			//			System.out.println("left to " + tempArrCol);
//			//			System.out.println((tempArrCol == arrayLocationRow) + " : " + (locArr[arrayLocationRow][tempArrCol] == 0));
//			vely = 0;
//			velx = -speed;
//		}
//		else {
//			vely = 0;
//			velx = 0;
//		}
//		updatePos();
//
//
//	}
//	public void right(int[][] locArr) 
//	{
//		int tempArrCol = (x+29+speed)/40;
//		//		System.out.println(arrayLocationRow+ " vs " + tempArrCol);
//		if (tempArrCol == arrayLocationCol || (locArr[arrayLocationRow][tempArrCol] == 0 && locArr[(y+29)/40][tempArrCol] == 0))
//		{
//			//			System.out.println("left to " + tempArrCol);
//			//			System.out.println((tempArrCol == arrayLocationRow) + " : " + (locArr[arrayLocationRow][tempArrCol] == 0));
//			vely = 0;
//			velx = speed;
//		}
//		else {
//			vely = 0;
//			velx = 0;
//		}
//		updatePos();
//	}
	public void setDirection(String direction)
	{
		this.direction = direction;
		
	}
	public void updatePos(int[][] locArr)
	{
		if((y - speed) < 45)
			y = 45;

		if ((y + speed) > 610)
			y = 610;

		if ((x-speed) < 45) 
			x = 45;

		if ((x+speed) > 610) 
			x = 610;

//		y += vely;
//		x += velx;

		arrayLocationRow = ((y+1)/40);
		arrayLocationCol = ((x+1)/40);
		
		if(direction.equals("up"))
		{
			int tempArrRow = (y-5)/40;
			if(locArr[tempArrRow][arrayLocationCol] != 1 && locArr[tempArrRow][arrayLocationCol] !=2 
					&& locArr[tempArrRow][(x+29)/40] != 1 && locArr[tempArrRow][(x+29)/40] != 2)
			{
//				System.out.println("up");
				y-=5;
			}

		}
		if(direction.equals("down"))
		{
			int tempArrRow = (y+29+5)/40;
			//		System.out.println(arrayLocationRow+ " vs " + tempArrRow);
			if (locArr[tempArrRow][arrayLocationCol] != 1 && locArr[tempArrRow][arrayLocationCol] !=2 
					&& locArr[tempArrRow][(x+29)/40] != 1 && locArr[tempArrRow][(x+29)/40] != 2)
			{
//				System.out.println("down");
				y+=5;
			}

		}
		if(direction.equals("right"))
		{
			int tempArrCol = (x+29+5)/40;
//			System.out.println("right");
			//		System.out.println(arrayLocationRow+ " vs " + tempArrCol);
			if (locArr[arrayLocationRow][tempArrCol] != 1 && locArr[arrayLocationRow][tempArrCol] !=2 
					&& locArr[(y+29)/40][tempArrCol] != 1 && locArr[(y+29)/40][tempArrCol] != 2)
			{
				x+=5;
			}

		}
		if(direction.equals("left"))
		{
			int tempArrCol = (x-5)/40;
			//		System.out.println(arrayLocationRow+ " vs " + tempArrCol);
			if (locArr[arrayLocationRow][tempArrCol] != 1 && locArr[arrayLocationRow][tempArrCol] !=2 
					&& locArr[(y+29)/40][tempArrCol] != 1 && locArr[(y+29)/40][tempArrCol] != 2)
			{
//				System.out.println("left");
				x-=5;
			}
		}


	}
	public void setCurrentImage(String dir)
	{
		if (dir.equals("up"))
			currentImage = upImage;
		else if (dir.equals("down"))
			currentImage = downImage;
		else if (dir.equals("right"))
			currentImage = rightImage;
		else if (dir.equals("left"))
			currentImage = leftImage;
	}


	public void drawPlayer(Graphics g, ImageObserver observer)
	{
		g.drawImage(currentImage, x, y, 30, 30, observer);
	}

}




