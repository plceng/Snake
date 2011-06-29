package dev.link.snake;

import java.util.*;

public class SnakeBody  implements Controlable {

	private Deque<BodyBlock> body;
	private MovementVector moveVector;

	public SnakeBody() {
		moveVector = new MovementVector();
		body = new ArrayDeque<BodyBlock>();
		body.addFirst(new BodyBlock(10,10));
		body.addFirst(new BodyBlock(11,10));
		body.addFirst(new BodyBlock(12,10));
		body.addFirst(new BodyBlock(13,10));
	}
	
	public MovementVector getMoveVector() { return moveVector; }
	
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
		BodyBlock newFirstBlock = new BodyBlock(body.getFirst());
		MovementDirection direction = moveVector.getDirection();
		switch (direction) {
			case DIR_LEFT: newFirstBlock.decrCoordX(); break;
			
			case DIR_RIGHT: newFirstBlock.incrCoordX(); break;
			
			case DIR_UP: newFirstBlock.decrCoordY(); break;
			
			case DIR_DOWN: newFirstBlock.incrCoordY(); break;
		}
		body.addFirst(newFirstBlock);
	}

	public void move() {
		body.removeLast();
		grow();
	}

	public boolean isValid() { return true; }
	
	public String toString() {
		return "Shake's body: " + body.toString() 
			+ "\nShake's movement vector: " + moveVector.toString();
	}

}