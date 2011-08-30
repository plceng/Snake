package dev.link.snake;

import java.util.*;
import java.awt.Dimension;

public class GameFieldModel {
	private HashSet<SnakeBody> snakes;
	private Rabbit rabbit;
	private Dimension fieldSize;
	public static final Dimension DEFAULT_FIELD_SIZE = new Dimension(50,50);
	private boolean rabbitIsAlive = true;
	
	Random rand = new Random();
	
	public GameFieldModel() {
		fieldSize = DEFAULT_FIELD_SIZE;
		snakes = new HashSet<SnakeBody>();
		snakes.add(new SnakeBody());
		createRabbit();
	}
	
	private void createRabbit() {
		int newRabbitXcoord = rand.nextInt(fieldSize.height);
		int newRabbitYcoord = rand.nextInt(fieldSize.width);
		rabbit = new Rabbit(newRabbitXcoord, newRabbitYcoord);
		rabbitIsAlive = true;
	}
	
	private void killRabbit() { rabbitIsAlive = false; }
		
	public void moveSnakes() {
		for (SnakeBody snake : snakes) {
			// ѕравило 1: —ъеден ли кролик?
			if (snake.getBody().contains(rabbit.getBody())) {
				snake.grow();
				killRabbit();
				createRabbit();
			}
			else if (snake.isValid())
				snake.move();
		}
	}
	
	public void moveRabbit() {
		// а жив ли кролик?
		if (!rabbitIsAlive)
			createRabbit();
		// сначала выберем рандомный поворот
		int intTurnDirection = rand.nextInt(4); // т.к. 4 стороны поворота
//		System.out.println("intTurnDirection" + intTurnDirection);
		rabbit.turn(intTurnDirection);
		// ≈сли тело "тестового" кролика не попадает в змейку :), то двигать реального в том же направлении 
		if (rabbitWillBeInSnakes())
			moveRabbit(); // ещЄ раз мен€еем направление
		else
			rabbit.move();
		// а если попадает, то выбрать направление ещЄ раз

	}
	
	private boolean rabbitWillBeInSnakes() {
		Rabbit futureRabbit = new Rabbit(rabbit);
		futureRabbit.move();
		boolean result = false;
		for (SnakeBody snake : snakes) {
			result = result || snake.getBody().contains(futureRabbit.getBody());
			if (result)
				return result;
		}
		return result;
		
	}
	
// 	Ёти методы нужны дл€ независимого управлени€ телами змеек, 
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
	 	 BodyBlock.COORD_LIMITS = this.fieldSize;
	 }
	 
	/**
	 * Sets the value of fieldSize in Dimension value.
	 */

	 public void setFieldSize(Dimension fieldSize) {
	 	 this.fieldSize = fieldSize;
	 	 BodyBlock.COORD_LIMITS = this.fieldSize;
	 }
	 
	 public static void main(String[] args) {
	 	 HashSet<SnakeBody> snakes = new HashSet<SnakeBody>();
	 	 System.out.println(snakes.add(new SnakeBody()));
	 	 for (SnakeBody snake : snakes) {
	 	 	 System.out.println(snake);
	 	 }
	 }
}
