package dev.link.snake;

import java.util.*;
import java.awt.Dimension;

public class Rabbit implements Controlable {
	private BodyBlock body;
	private MovementVector moveVector;
	
	public Rabbit(int x, int y) {
		body = new BodyBlock(x, y);
		moveVector = new MovementVector();
	}
	
	public int hashCode() {
		return 17 * body.hashCode() * moveVector.hashCode();
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Rabbit))
			return false;
		Rabbit rabbit = (Rabbit) obj;
		return (	this.body.equals(rabbit.body)
				 && this.moveVector.equals(rabbit.moveVector)) 
			? true : false;
	}	

	public BodyBlock getBody() { return this.body; }
	
	public MovementVector getMoveVector() { return this.moveVector; }
	
	public void turn(int intTurnDirection) {
		if (intTurnDirection == MovementDirection.DIR_LEFT.ordinal())
			turnLeft();
		else if (intTurnDirection == MovementDirection.DIR_RIGHT.ordinal())
			turnRigth();
		else if (intTurnDirection == MovementDirection.DIR_UP.ordinal())
			turnUp();
		else if (intTurnDirection == MovementDirection.DIR_DOWN.ordinal())
			turnDown();
		else 
			System.out.println("Game Error::Неверное направление: " + intTurnDirection);
	}
	
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
	
	public void move() {
		BodyBlock nextRabbitBoody = new BodyBlock(body);
		MovementDirection direction = moveVector.getDirection();
		switch (direction) {
			case DIR_LEFT: nextRabbitBoody.decrCoordX(); break;
			
			case DIR_RIGHT: nextRabbitBoody.incrCoordX(); break;
			
			case DIR_UP: nextRabbitBoody.decrCoordY(); break;
			
			case DIR_DOWN: nextRabbitBoody.incrCoordY(); break;
		}
		body = new BodyBlock(nextRabbitBoody); // Тело кролика состоит из нового расчитанный блок
	}
	
	public static void main(String[] args) {
		System.out.println(MovementDirection.DIR_LEFT);
	}

}
