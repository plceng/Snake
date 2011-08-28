package dev.link.snake;

import java.util.*;
import java.awt.Dimension;

public class GameFieldModel {
	private HashSet<SnakeBody> snakes;
	private Rabbit rabbit;
	private Dimension fieldSize;
	private static final Dimension DEFAULT_FIELD_SIZE = new Dimension(50,50);
	
	Random rand = new Random();
	
	public GameFieldModel() {
		fieldSize = DEFAULT_FIELD_SIZE;
		snakes = new HashSet<SnakeBody>();
		snakes.add(new SnakeBody());
		
		rabbit = new Rabbit(20, 20); }
	
	public void moveSnakes() {
		for (SnakeBody snake : snakes)
			snake.move();
	}
	
	public void moveRabbit() {
		// сначала выберем рандомный поворот
		int intTurnDirection = rand.nextInt(4); // т.к. 4 стороны поворота
		System.out.println("intTurnDirection" + intTurnDirection);
		rabbit.turn(intTurnDirection);
		rabbit.move();
	}
	
// 	Эти методы нужны для независимого управления телами змеек, 
//	если их несколько. 	
	public void turnSnakeLeft(int snakeID) {
		for (SnakeBody snake : snakes)
			snake.turnLeft();
	}	

	public void turnSnakeRight(int snakeID) {
		for (SnakeBody snake : snakes)
			snake.turnRigth();
	}	

	public void turnSnakeUp(int snakeID) {
		for (SnakeBody snake : snakes)
			snake.turnUp();
	}
	
	public void turnSnakeDown(int snakeID) {
		for (SnakeBody snake : snakes)
			snake.turnDown();
	}
	
	public void addSnakeBody(SnakeBody snakeBody) {
		this.snakes.add(snakeBody);	
	}

	/**
	 * Returns the value of snakes.
	 */
	public HashSet<SnakeBody> getSnakes() { return snakes; }
	
	/**
	 * Returns the value of rabbit.
	 */
	 public Rabbit getRabbit() { return rabbit; }

	/**
	 * Returns the value of fieldSize.
	 */
	public Dimension getFieldSize() {
		return fieldSize;
	}

	public double getFieldWidth() {
		return fieldSize.getWidth();
	}

	public double getFieldHeight() {
		return fieldSize.getHeight();
	}
	
	/**
	 * Sets the value of fieldSize in int values.
	 */
	 public void setFieldSize(int width, int height) {
	 	 this.fieldSize.width = width;
	 	 this.fieldSize.height = height;
	 }
	 
	/**
	 * Sets the value of fieldSize in Dimension value.
	 */

	 public void setFieldSize(Dimension fieldSize) {
	 	 this.fieldSize = fieldSize;
	 }
	 
	 public static void main(String[] args) {
	 	 HashSet<SnakeBody> snakes = new HashSet<SnakeBody>();
	 	 System.out.println(snakes.add(new SnakeBody()));
	 	 for (SnakeBody snake : snakes) {
	 	 	 System.out.println(snake);
	 	 }
	 }
}
