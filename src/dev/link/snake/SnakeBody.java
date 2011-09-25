package dev.link.snake;

import java.awt.Color;
import java.util.*;

public class SnakeBody  implements Controlable {

	private ArrayList<BodyBlock> body;
	private boolean valid = true;
	private Player player;
	private int snakeID = 1;

	private MovementVector moveVector;

	public SnakeBody() {
		moveVector = new MovementVector();
		body = new ArrayList<BodyBlock>();
		body.add(0, new BodyBlock(10,10));
		body.add(0, new BodyBlock(11,10));
		body.add(0, new BodyBlock(12,10));
		body.add(0, new BodyBlock(13,10));
	}

	public SnakeBody(int initX, int initY, Player player) {
		moveVector = new MovementVector();
		body = new ArrayList<BodyBlock>();
		for (int i = 0; i < 5; i++) {
			body.add(0, new BodyBlock(initX + i,initY));
		}
		this.player = player;
	}

	
	public int hashCode() {
		return 11 * body.hashCode() * moveVector.hashCode() * snakeID;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof SnakeBody))
			return false;
		SnakeBody snake = (SnakeBody) obj;
		return (	this.body.equals(snake.body)
				 && this.moveVector.equals(snake.moveVector)) 
			? true : false;
	}
	
	/**

	 * Returns the value of snakeID.

	 */

	public int getSnakeID() {

		return snakeID;

	}



	/**

	 * Sets the value of snakeID.

	 * @param snakeID The value to assign snakeID.

	 */

	public void setSnakeID(int snakeID) {

		this.snakeID = snakeID;

	}

	
	
	/**
	 * Returns the value of body.
	 */
	public ArrayList<BodyBlock> getBody() { return this.body; }

	/**
	 * Returns the value of moveVector.
	 */
	public MovementVector getMoveVector() { return this.moveVector; }
	/**

	 * Returns the value of name.

	 */

	 

	public void turnLeft() {
		moveVector.setDirection(MovementDirection.DIR_LEFT);
	}

	public void turnRigth() {
		moveVector.setDirection(MovementDirection.DIR_RIGHT);
	}

	public void turnUp() {
		moveVector.setDirection(MovementDirection.DIR_UP);
	}

	public void turnDown() {
		moveVector.setDirection(MovementDirection.DIR_DOWN);
	}

	public void grow() {
		BodyBlock newFirstBlock = new BodyBlock(body.get(0));
		MovementDirection direction = moveVector.getDirection();
		switch (direction) {
			case DIR_LEFT: newFirstBlock.decrCoordX(); break;
			
			case DIR_RIGHT: newFirstBlock.incrCoordX(); break;
			
			case DIR_UP: newFirstBlock.decrCoordY(); break;
			
			case DIR_DOWN: newFirstBlock.incrCoordY(); break;
		}
		// Проверка на самосъедание
		if (body.contains(newFirstBlock)) {
			setValid(false);
			System.out.println("Game over for player " + this.player);
		}
		else 
			body.add(0, newFirstBlock);
	}

	public void move() {
		body.remove(body.size() - 1);
		grow();
	}

	public boolean isValid() { return this.valid; }
	public void setValid(boolean valid) { this.valid = valid; }
	
	public String toString() {
		return "Shake's body: " + body.toString() 
			+ "\nShake's movement vector: " + moveVector.toString();
	}

	public static void main(String[] args) {
		Controlable snakeBody = new SnakeBody();
		System.out.println(snakeBody);
//		System.out.println(snakeBody);
	}

	public Color getColor() {
		return player.getSnakeColor();
	}
	
}