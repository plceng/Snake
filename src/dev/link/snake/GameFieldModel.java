package dev.link.snake;

import java.util.*;
import java.awt.Dimension;
import dev.link.snake.gui.SnakeTheGame;
import java.awt.Color;
import java.util.Map.Entry;

public class GameFieldModel {

	private Map<Player, SnakeBody> snakes;
	private Rabbit rabbit;
	private Dimension fieldSize;
	public static final Dimension DEFAULT_FIELD_SIZE = new Dimension(25, 25);
	private boolean rabbitIsAlive = true;
	private Random rand = new Random();

	public GameFieldModel() {
		fieldSize = DEFAULT_FIELD_SIZE;
		snakes = new HashMap<Player, SnakeBody>();
//		addPlayersAndSnakes();
//		onePlayerModel();
		createRabbit();
	}

	public void addPlayersAndSnakes(String[] playerNames, Color[] playerColors) {
		// TODO ��������
		for (int i = 0; i < playerNames.length; i++) {
			try {
				Player newPlayer = new Player(playerNames[i], playerColors[i]);
				GameParameters.players.add(newPlayer);
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.out.format("Color for player [%s] isn't set. Using default %n", playerNames[i]);
				GameParameters.players.add(new Player(playerNames[i]));
				continue;
			}
		}
		addSnakes();
	}

	public void addPlayersAndSnakes(int quantity) {
		for (int i = 1; i <= quantity; i++) {
			Player newPlayer = new Player(String.valueOf(i));
			newPlayer.setControlKeys(GameParameters.DEFAULT_KEY_SET[i-1]);
			GameParameters.players.add(newPlayer);
		}
		addSnakes();
		System.out.println(snakes);
	}

	//for debug

	private void addSnakes() {
		for (Player p : GameParameters.players) {
			snakes.put(p, new SnakeBody(rand.nextInt(fieldSize.width),
					rand.nextInt(fieldSize.height), p));
		}
	}

	private void createRabbit() {
		int newRabbitXcoord = rand.nextInt(fieldSize.height);
		int newRabbitYcoord = rand.nextInt(fieldSize.width);
		rabbit = new Rabbit(newRabbitXcoord, newRabbitYcoord);
		rabbitIsAlive = true;
	}

	private void killRabbit() {
		rabbitIsAlive = false;
	}

	public void moveSnakes() {
		for (SnakeBody snake : snakes.values()) {
			// ������� 1: ������ �� ������?
			if (snake.getBody().contains(rabbit.getBody())) {
				snake.grow();
				killRabbit();
				createRabbit();
			} else if (snake.isValid()) {
				snake.move();
			}
		}
	}

	public void moveRabbit() {
		// � ��� �� ������?
		if (!rabbitIsAlive) {
			createRabbit();
		}
		// ������� ������� ��������� �������
		int intTurnDirection = rand.nextInt(4); // �.�. 4 ������� ��������
//		System.out.println("intTurnDirection" + intTurnDirection);
		rabbit.turn(intTurnDirection);
		// ���� ���� "���������" ������� �� �������� � ������ :), �� ������� ��������� � ��� �� ����������� 
		if (rabbitWillBeInSnakes()) {
			moveRabbit(); // ��� ��� ������� �����������
		} else {
			rabbit.move();
		}
		// � ���� ��������, �� ������� ����������� ��� ���

	}

	private boolean rabbitWillBeInSnakes() {
		Rabbit futureRabbit = new Rabbit(rabbit);
		futureRabbit.move();
		boolean result = false;
		for (SnakeBody snake : snakes.values()) {
			result = result || snake.getBody().contains(futureRabbit.getBody());
			if (result) {
				return result;
			}
		}
		return result;

	}
//-------------	
// 	��� ������ ����� ��� ������������ ���������� ������ �����, 
//	���� �� ���������. 	

	public void turnSnakeLeft(Player player) {
		snakes.get(player).turnLeft();
	}

	public void turnSnakeRight(Player player) {
		snakes.get(player).turnRigth();
	}

	public void turnSnakeUp(Player player) {
		snakes.get(player).turnUp();
//		try {
//		snakes.get(player).turnUp();
//		} catch (Exception ex) {
//			System.out.println("���������");
//		}
	}

	public void turnSnakeDown(Player player) {
		snakes.get(player).turnDown();
	}
//------------

	/**
	 * Returns the value of snakes.
	 */
	public Collection<SnakeBody> getAllSnakes() {
		return snakes.values();
	}

	/**
	 * Returns the value of rabbit.
	 */
	public Rabbit getRabbit() {
		return rabbit;
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
