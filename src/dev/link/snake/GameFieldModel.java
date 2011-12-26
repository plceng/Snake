package dev.link.snake;

import java.util.*;
import java.awt.Dimension;
import java.awt.Color;

public class GameFieldModel {
/**
 * ��������� ��� ������������� ��������� ����. �������� ���� (�������� - ����)
 * ������������, ������� �������, ��� ����������� ��������� �/��� ������� ����
 */
	private Map<BodyBlock, SnakeBody> gameField;
//	private Collection<SnakeBody> snakes;
	private Rabbit rabbit;
	private static Dimension fieldSize;
	public static final Dimension DEFAULT_FIELD_SIZE = new Dimension(25, 25);
	private boolean rabbitIsAlive = true;
	private Random rand = new Random();
	private Set<SnakeBody> snakesSet;
//	public static Player[][] fieldCells;

	public GameFieldModel() {
		fieldSize = DEFAULT_FIELD_SIZE;
		gameField = new HashMap<BodyBlock, SnakeBody>();
		snakesSet = new HashSet<SnakeBody>();
//		fieldCells = new Player[fieldSize.width][fieldSize.height];
//		addRandomPlayersAndSnakes();
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
//		addRandomSnakes();
	}

	public void addRandomPlayersAndSnakes(int quantity) {
		for (int i = 1; i <= quantity; i++) {
			// �����
			Player newPlayer = new Player(String.valueOf(i));
			newPlayer.setControlKeys(GameParameters.DEFAULT_KEY_SET[i - 1]);
			// ��� ������ :) ��������� ������ � ������������
			SnakeBody newSnake = new SnakeBody(rand.nextInt(fieldSize.width),
					rand.nextInt(fieldSize.height), newPlayer);
			// ��������� ������ ������ �� ����.
			newPlayer.setSnake(newSnake);
			addToSnakesSet(newSnake);

			// TODO � ����� �� ���� ������ -- ����� ��� ������� ����� ��� ������� (�������� ��...)
			// ������� ������ � ���������� ������ ��� ����-������
			GameParameters.players.add(newPlayer);

			//����������� � this.gameField �������� ���� (����� ���� ; ����)
			for (BodyBlock b : newSnake.getBody()) {
				gameField.put(b, newSnake);
			}
		}
		//System.out.println(snakes);
	}

	private boolean addToSnakesSet(SnakeBody snake) {
		return snakesSet.add(snake);
	}

	/**
	 * Returns the value of snakesSet.
	 */
	public Collection<SnakeBody> getAllSnakes() {
		return snakesSet;
	}
//	private void addRandomSnakes() {
//		for (Player p : GameParameters.players) {
//			SnakeBody newSnake = new SnakeBody(rand.nextInt(fieldSize.width),
//					rand.nextInt(fieldSize.height), p);
//			addSnakeToFieldCell(newSnake);
//			gameField.put(p, newSnake);
//		}
//	}
//	private void bindSnakesForPlayers() {
//		for (Player p : gameField.keySet()) {
//			p.setSnake(gameField.get(p));
//		}
//	}
//
//	private void addSnakeToFieldCell(SnakeBody newSnake) {
//		Player newPlayer = newSnake.getPlayer();
//		for (BodyBlock b : newSnake.getBody()) {
//			fieldCells[b.getCoordX()][b.getCoordY()] = newPlayer;
//		}
//
//	}
//
//	static void addToFieldCell(BodyBlock newBlock, Player player) {
//		fieldCells[newBlock.getCoordX()][newBlock.getCoordY()] = player;
//	}
//
//	static void removeFromFieldCell(int x, int y) {
//		fieldCells[x][y] = null;
//	}
	//for debug
	private void createRabbit() {
		int newRabbitXcoord = rand.nextInt(fieldSize.height);
		int newRabbitYcoord = rand.nextInt(fieldSize.width);
		rabbit = new Rabbit(newRabbitXcoord, newRabbitYcoord);
		rabbitIsAlive = true;
	}

	private void growSnake(SnakeBody snake) {
		 // ��������� ����� ����
		snake.grow();
		// ���������� ����� ������
		snake.getPlayer().incrScore();
		// ���������� ������ ��������� ���� (� ������) � gameField
		BodyBlock snakeHead = snake.getHead();
		gameField.put(snakeHead, snake);
	}

	private void killRabbit() {
		rabbitIsAlive = false;
	}

	public void moveSnakes() {
		for (SnakeBody snake : getAllSnakes()) {
			if (!snake.isAlive()) { // ������� ���� �� ����� �� ������� :)
				continue;
			}
			// �������� 1: ������ ������
			if (snake.getBody().contains(rabbit.getBody())) {
				growSnake(snake);
				killRabbit();
				createRabbit();
			} //�������� 2: ������ ����� ������ ����
			else if (eatOtherSnake(snake)) {
				growSnake(snake);
			}
			 // ������ ����������: ������� �������� ����
			else if (snake.isAlive()) {
				moveSnake(snake);
			}
		}
	}

	private void moveSnake(SnakeBody snake) {
		snake.move();
		// ��� - ��� ���������� � ���� ������ � �������� ������
		BodyBlock snakeTail = snake.getTail();
		gameField.remove(snakeTail);
		BodyBlock snakeHead = snake.getHead();
		gameField.put(snakeHead, snake);
	}

//	public static void printFieldCells() {
//		for (int i = 0; i < fieldCells.length; i++) {
//			for (int j = 0; j < fieldCells[i].length; j++) {
//				System.out.format("fieldCells[%d][%d] = %s", i, j, fieldCells[i][j]);
//			}
//		}
//	}

	private boolean eatOtherSnake(SnakeBody thisSnake) {
		//	boolean result = false;
		BodyBlock thisHead = thisSnake.getBody().get(0);
		int headX = thisHead.getCoordX();
		int headY = thisHead.getCoordY();
		Player otherPlayer = null;
		for (SnakeBody otherSnake : getAllSnakes()) {
			if (otherSnake.getBody().contains(thisHead)
					//���������� ����
					&& (!otherSnake.getPlayer().equals(thisSnake.getPlayer()))) {
				otherSnake.takeaBite(thisHead);
				SnakeBody removedSnakeBlock = gameField.remove(thisHead);
				System.out.println("��������� ����: " + removedSnakeBlock);
				gameField.put(thisHead, thisSnake);
				System.out.println("������� ����: " + removedSnakeBlock);
				return true;
			}
		}
		return false;
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
		for (SnakeBody snake : getAllSnakes()) {
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

//	public void turnSnakeLeft(SnakeBody snake) {
//		snake.turnLeft();
//	}
//
//	public void turnSnakeRight(SnakeBody snake) {
//		snake.turnRight();
//	}
//
//	public void turnSnakeUp(SnakeBody snake) {
//		snake.turnUp();
////		try {
////		snakes.get(player).turnUp();
////		} catch (Exception ex) {
////			System.out.println("���������");
////		}
//	}
//
//	public void turnSnakeDown(SnakeBody snake) {
//		snake.turnDown();
//	}
//------------



	/**
	 * Returns the value of rabbit.
	 */
	public Rabbit getRabbit() {
		return rabbit;
	}

	/**
	 * Returns the value of fieldSize.
	 */
	public static Dimension getFieldSize() {
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
