package dev.link.snake;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author link
 */
public class Player {

	private String name;
	private int score;
	private Color snakeColor;
	private SnakeBody snake;
	// Control keys
	private int leftKey;
	private int rightKey;
	private int upKey;
	private int downKey;

	public Player(String name, Color snakeColor) {
		this.name = name;
		this.snakeColor = snakeColor;
	}

	@Override
	public String toString() {
		return "Player{" + "name=" + name + "score=" + score
				+ "snakeBody=" + snake
				+ "snakeColor=" + snakeColor + "leftKey=" + leftKey
				+ "rightKey=" + rightKey + "upKey=" + upKey
				+ "downKey=" + downKey + '}';
	}

	public Player(String name) {
		this.name = name;
		this.snakeColor = new Color(
				new Random().nextInt(16777216)); // 2 in 24 is a decimal
		//number contains 24 bits. For RGB model
	}

	public Player(String name, int leftKey, int rightKey, int upKey, int downKey) {
		this(name);
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.downKey = downKey;
	}

	public Player(String name, int score, Color snakeColor) {
		this.name = name;
		this.score = score;
		this.snakeColor = snakeColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
// Что касается очков игрока...
	public int getScore() {
		return score;
	}

	public void incrScore() {
		score += GameParameters.ScoreIncrement;
		firePlayerEvent(new PlayerEvent(this));
	}


	private void firePlayerEvent(PlayerEvent playerEvent) {
		
		
	}

	public void setScore(int score) {
		this.score = score;
	}
	
//-----------------------------
	public Color getSnakeColor() {
		return snakeColor;
	}

	public void setSnakeColor(Color snakeColor) {
		this.snakeColor = snakeColor;
	}

	public int getDownKey() {
		return downKey;
	}

	public void setDownKey(int downKey) {
		this.downKey = downKey;
	}

	public int getLeftKey() {
		return leftKey;
	}

	public void setLeftKey(int leftKey) {
		this.leftKey = leftKey;
	}

	public int getRightKey() {
		return rightKey;
	}

	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
	}

	public int getUpKey() {
		return upKey;
	}

	public void setUpKey(int upKey) {
		this.upKey = upKey;
	}

	public void setControlKeys(int[] controlKeys) {
		try {
			setLeftKey(controlKeys[0]);
			setRightKey(controlKeys[1]);
			setUpKey(controlKeys[2]);
			setDownKey(controlKeys[3]);
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("Not enought control keys");
		}
	}

	public int[] getControlKeys() {
		int[] controlKeys = {
			getLeftKey(),
			getRightKey(),
			getUpKey(),
			getDownKey()
		};
//		return {getLeftKey(), getRightKey(), getUpKey(), getDownKey() };
		return controlKeys;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Player other = (Player) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 29 * hash + this.score;
		hash = 29 * hash + (this.snakeColor != null ? this.snakeColor.hashCode() : 0);
		return hash;
	}

	void killSnake() {
		snake.setAlive(false);
	}

	/**
	 * Get the value of snake
	 *
	 * @return the value of snake
	 */
	public SnakeBody getSnake() {
		return snake;
	}

	/**
	 * Set the value of snake
	 *
	 * @param snake new value of snake
	 */
	public void setSnake(SnakeBody snake) {
		this.snake = snake;
	}

	public void eatPartOfSnakeBoby(BodyBlock whichPart) {
		snake.getBody().remove(whichPart);
	}
}
