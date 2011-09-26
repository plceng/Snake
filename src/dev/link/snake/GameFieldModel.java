package dev.link.snake;

import java.util.*;
import java.awt.Dimension;
import java.awt.Color;

public class GameFieldModel {


	private Map<Player, SnakeBody> snakes;
//	private Collection<SnakeBody> snakes;
	private Rabbit rabbit;
	private Dimension fieldSize;
	public static final Dimension DEFAULT_FIELD_SIZE = new Dimension(25, 25);
	private boolean rabbitIsAlive = true;
	private Random rand = new Random();
	public static Player[][] fieldCells;

	public GameFieldModel() {
		fieldSize = DEFAULT_FIELD_SIZE;
		snakes = new HashMap<Player, SnakeBody>();
		fieldCells = new Player[fieldSize.width][fieldSize.height];
//		addPlayersAndSnakes();
//		onePlayerModel();
		createRabbit();
	}

	public void addPlayersAndSnakes(String[] playerNames, Color[] playerColors) {
		// TODO допилить
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
		addRandomSnakes();
	}

	public void addPlayersAndSnakes(int quantity) {
		for (int i = 1; i <= quantity; i++) {
			Player newPlayer = new Player(String.valueOf(i));
			newPlayer.setControlKeys(GameParameters.DEFAULT_KEY_SET[i - 1]);
			GameParameters.players.add(newPlayer);
		}
//		System.out.println(GameParameters.players);
		addRandomSnakes();
		bindSnakesForPlayers();

		//System.out.println(snakes);
	}

	private void addRandomSnakes() {
		for (Player p : GameParameters.players) {
			SnakeBody newSnake = new SnakeBody(rand.nextInt(fieldSize.width),
					rand.nextInt(fieldSize.height), p);
			addSnakeToFieldCell(newSnake);
			snakes.put(p, newSnake);
		}
	}

	private void bindSnakesForPlayers() {
		for (Player p : snakes.keySet()) {
			p.setSnake(snakes.get(p));
		}
	}

	private void addSnakeToFieldCell(SnakeBody newSnake) {
		Player newPlayer = newSnake.getPlayer();
		for (BodyBlock b : newSnake.getBody()) {
			fieldCells[b.getCoordX()][b.getCoordY()] = newPlayer;
		}

	}

	static void addToFieldCell(BodyBlock newBlock, Player player) {
		fieldCells[newBlock.getCoordX()][newBlock.getCoordY()] = player;
	}

	static void removeFromFieldCell(int x, int y) {
		fieldCells[x][y] = null;
	}
	//for debug

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
			if (!snake.isValid()) continue;
			// Ситуация 1: Съеден кролик
			if (snake.getBody().contains(rabbit.getBody())) {
				snake.grow();
				killRabbit();
				createRabbit();
			} //Ситуация 2: Съеден кусок другой змеи
			else if (eatOtherSnake(snake)) {
				snake.grow();
			} // Если ни одна особая ситуация не произошла, то просто двигаем змею
			else if (snake.isValid()) {
				snake.move();
			}
		}
	}
	public static void printFieldCells() {
		for (int i = 0; i < fieldCells.length; i++) {
			for (int j = 0; j < fieldCells[i].length; j++) {
				System.out.format("fieldCells[%d][%d] = %s", i, j, fieldCells[i][j]);
			}
		}
	}

	private boolean eatOtherSnake(SnakeBody thisSnake) {
	//	boolean result = false;
		BodyBlock head = thisSnake.getBody().get(0);
		int headX = head.getCoordX();
		int headY = head.getCoordY();
		Player otherPlayer = fieldCells[headX][headY];
//		printFieldCells();
		if (!otherPlayer.equals(thisSnake.getPlayer())
				&& otherPlayer != null) {
			// убиваем съеденную змею
			otherPlayer.killSnake();
			// Съедаем тот кусок убитой змеи, который укушен (головой :))
			otherPlayer.eatPartOfSnakeBoby(head); 
			return true;
		} else {
			return false;
		}
	}

	public void moveRabbit() {
		// а жив ли кролик?
		if (!rabbitIsAlive) {
			createRabbit();
		}
		// сначала выберем рандомный поворот
		int intTurnDirection = rand.nextInt(4); // т.к. 4 стороны поворота
//		System.out.println("intTurnDirection" + intTurnDirection);
		rabbit.turn(intTurnDirection);
		// Если тело "тестового" кролика не попадает в змейку :), то двигать реального в том же направлении 
		if (rabbitWillBeInSnakes()) {
			moveRabbit(); // ещё раз меняеем направление
		} else {
			rabbit.move();
		}
		// а если попадает, то выбрать направление ещё раз

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
// 	Эти методы нужны для независимого управления телами змеек, 
//	если их несколько. 	

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
//			System.out.println("ДАВАААААЙ");
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
		fieldCells = new Player[width][height];
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
