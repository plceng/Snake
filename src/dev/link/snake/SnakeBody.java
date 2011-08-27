package dev.link.snake;

import java.util.*;

public class SnakeBody  implements Controlable {

	private ArrayList<BodyBlock> body;
	private MovementVector moveVector;

	public SnakeBody() {
		moveVector = new MovementVector();
		body = new ArrayList<BodyBlock>();
		body.add(0, new BodyBlock(10,10));
		body.add(0, new BodyBlock(11,10));
		body.add(0, new BodyBlock(12,10));
		body.add(0, new BodyBlock(13,10));
	}
	
	public int hashCode() {
		return 11*body.hashCode()*moveVector.hashCode();
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
	 * Returns the value of body.
	 */
	public ArrayList<BodyBlock> getBody() { return this.body; }

	/**
	 * Returns the value of moveVector.
	 */
	public MovementVector getMoveVector() { return this.moveVector; }
	
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
		body.add(0, newFirstBlock);
	}

	public void move() {
		body.remove(body.size() - 1);
		grow();
	}

	public boolean isValid() { return true; }
	
	public String toString() {
		return "Shake's body: " + body.toString() 
			+ "\nShake's movement vector: " + moveVector.toString();
	}

	public static void main(String[] args) {
		Controlable snakeBody = new SnakeBody();
		System.out.println(snakeBody);
//		System.out.println(snakeBody);
	}
	
}