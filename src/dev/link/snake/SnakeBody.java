package dev.link.snake;

import java.awt.Color;
import java.util.*;

public class SnakeBody implements Controlable {

	private ArrayList<BodyBlock> body;
	private boolean alive = true;
	private Player player;
	private int snakeID = 1;
	private MovementVector moveVector;

	public SnakeBody() {
		moveVector = new MovementVector();
		body = new ArrayList<BodyBlock>();
		body.add(0, new BodyBlock(10, 10));
		body.add(0, new BodyBlock(11, 10));
		body.add(0, new BodyBlock(12, 10));
		body.add(0, new BodyBlock(13, 10));
	}

	public SnakeBody(int initX, int initY, Player player) {
		moveVector = new MovementVector();
		body = new ArrayList<BodyBlock>();
		int maxX = GameFieldModel.getFieldSize().width;
		int maxY = GameFieldModel.getFieldSize().height;
		for (int i = 0, newX, newY; i < 5; i++) {
			newX = (initX + i > maxX) ?
					maxX - initX + i :
					initX + i;
			newY = (initY + i > maxY) ?
					maxY - initY :
					initY;
			body.add(0, new BodyBlock(newX, newY));
		}
		this.player = player;
	}

	/**
	 * Get the value of player
	 *
	 * @return the value of player
	 */
	public Player getPlayer() {
		if (player == null) {
			System.out.println("Null Player for Snake: " + this);
			return null;
		}
		return player;
	}

	/**
	 * Set the value of player
	 *
	 * @param player new value of player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + (this.body != null ? this.body.hashCode() : 0);
		hash = 97 * hash + (this.alive ? 1 : 0);
		hash = 97 * hash + (this.player != null ? this.player.hashCode() : 0);
		hash = 97 * hash + this.snakeID;
		hash = 97 * hash + (this.moveVector != null ? this.moveVector.hashCode() : 0);
		return hash;
	}

//	public int hashCode() {
//		return 11 * body.hashCode() * moveVector.hashCode() * snakeID;
//	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SnakeBody)) {
			return false;
		}
		SnakeBody snake = (SnakeBody) obj;
		return (this.body.equals(snake.body)
				&& this.moveVector.equals(snake.moveVector))
				? true : false;
	}

	public BodyBlock getHead() {
		return body.get(0);
	}

	public BodyBlock getTail() {
		return body.get(body.size() - 1);
	}

	/**
	 * Returns the value of body.
	 */
	public ArrayList<BodyBlock> getBody() {
		return this.body;
	}

	/**
	 * Returns the value of moveVector.
	 */
	public MovementVector getMoveVector() {
		return this.moveVector;
	}

	/**

	 * Returns the value of name.

	 */
	public void turnLeft() {
		moveVector.setDirection(MovementDirection.DIR_LEFT);
	}

	public void turnRight() {
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
			case DIR_LEFT:
				newFirstBlock.decrCoordX();
				break;

			case DIR_RIGHT:
				newFirstBlock.incrCoordX();
				break;

			case DIR_UP:
				newFirstBlock.decrCoordY();
				break;

			case DIR_DOWN:
				newFirstBlock.incrCoordY();
				break;
		}
		// Проверка на самосъедание
		if (body.contains(newFirstBlock)) {
			setAlive(false);
			System.out.println("Game over for player " + this.player);
		} else {
//			GameFieldModel.addToFieldCell( newFirstBlock, this.getPlayer() );
			body.add(0, newFirstBlock);
		}
	}

	public void move() {
		BodyBlock tail = body.remove(body.size() - 1);
		grow();
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

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

	public void takeaBite(BodyBlock whichHead) {
		body.remove(whichHead);
		setAlive(false);
	}
}
