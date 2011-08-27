package dev.link.snake;

import java.util.*;
import java.awt.Dimension;

public class GameFieldModel {
	private HashSet<SnakeBody> snakes;
	private Dimension fieldSize;
	private static final Dimension DEFAULT_FIELD_SIZE = new Dimension(50,50);
	
	public GameFieldModel() {
		fieldSize = DEFAULT_FIELD_SIZE;
		snakes = new HashSet<SnakeBody>();
		snakes.add(new SnakeBody());
	}
	
	public void addSnakeBody(SnakeBody snakeBody) {
		this.snakes.add(snakeBody);	
	}

	/**
	 * Returns the value of snakes.
	 */
	public HashSet<SnakeBody> getSnakes() {
		return snakes;
	}

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