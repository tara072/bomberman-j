package isu;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.*;

import javax.swing.ImageIcon;

public class Enemy {

	private static LinkedList<Enemy> enemyList = new LinkedList<Enemy>();

	private static int maxNumEnemies;
	private static int totalNumEnemies = 0;

	private int x;
	private int y;
	private int dx;
	private int dy;
	private int arrayLocationRow;
	private int arrayLocationCol;

	private Image rightImage;
	private Image leftImage;
	private Image currentImage;
	
	private String direction = "up";


	public Enemy(int arrayLocationRow, int arrayLocationCol, int enemyType)
	{
		this.arrayLocationRow = arrayLocationRow;
		this.arrayLocationCol = arrayLocationCol;
		this.x = arrayLocationCol*40;
		this.y = arrayLocationRow*40;
		if(enemyType == 5)
			orange();
		else if(enemyType == 2)
			blue();
		enemyList.add(this);
		totalNumEnemies++;
	}

	private void orange()
	{
		rightImage = new ImageIcon("OranR.png").getImage();
		leftImage = new ImageIcon("OranL.png").getImage();
		currentImage = leftImage;

	}

	private void blue()
	{
		rightImage = new ImageIcon("BlueR.png").getImage();
		leftImage = new ImageIcon("BlueL.png").getImage();
		currentImage = leftImage;
	}

	public static LinkedList<Enemy> getEnemyList()
	{
		return enemyList;
	}
	public int getArrayLocationRow () {
		return arrayLocationRow;
	}

	public int getArrayLocationCol() {
		return arrayLocationCol;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	public static int getMaxNumEnemies() 
	{
		return maxNumEnemies;
	}
	public static int getTotalNumEnemies() 
	{
		return totalNumEnemies;
	}

	public static void setMaxNumEnemies(int num) 
	{
		maxNumEnemies = num;
	}
	public void setDirection(String direction)
	{
		this.direction = direction;
		
	}
	public void updateEnemyPos(int[][] locArr)
	{
		if((y - 5) < 45)
			y = 45;

		if ((y + 5) > 610)
			y = 610;

		if ((x - 5) < 45) 
			x = 45;

		if ((x + 5) > 610) 
			x = 610;

		arrayLocationRow = ((y+5)/40);
		arrayLocationCol = ((x+5)/40);
		
		if(direction.equals("up"))
		{
			int tempArrRow = (y-5)/40;
			if(locArr[tempArrRow][arrayLocationCol] != 5 && locArr[tempArrRow][arrayLocationCol] !=2 
					&& locArr[tempArrRow][(x+29)/40] != 5 && locArr[tempArrRow][(x+29)/40] != 2)
			{
//				System.out.println("up");
				y-=5;
			}

		}
		if(direction.equals("down"))
		{
			int tempArrRow = (y+29+5)/40;
			//		System.out.println(arrayLocationRow+ " vs " + tempArrRow);
			if (locArr[tempArrRow][arrayLocationCol] != 5 && locArr[tempArrRow][arrayLocationCol] !=2 
					&& locArr[tempArrRow][(x+29)/40] != 5 && locArr[tempArrRow][(x+29)/40] != 2)
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
			if (locArr[arrayLocationRow][tempArrCol] != 5 && locArr[arrayLocationRow][tempArrCol] !=2 
					&& locArr[(y+29)/40][tempArrCol] != 5 && locArr[(y+29)/40][tempArrCol] != 2)
			{
				x+=5;
			}

		}
		if(direction.equals("left"))
		{
			int tempArrCol = (x-5)/40;
			//		System.out.println(arrayLocationRow+ " vs " + tempArrCol);
			if (locArr[arrayLocationRow][tempArrCol] != 5 && locArr[arrayLocationRow][tempArrCol] !=2 
					&& locArr[(y+29)/40][tempArrCol] != 5 && locArr[(y+29)/40][tempArrCol] != 2)
			{
//				System.out.println("left");
				x-=5;
			}
		}
	}

	public void drawEnemy(Graphics g, ImageObserver o)
	{
		g.drawImage(currentImage, x, y, 30, 30, o);
	}


	public String toString()
	{
		return arrayLocationRow + " : " + arrayLocationCol;
	}

}


