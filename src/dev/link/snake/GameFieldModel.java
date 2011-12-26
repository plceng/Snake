package dev.link.snake;

import java.util.*;
import java.awt.Dimension;
import java.awt.Color;

public class GameFieldModel implements ScoreObservable {
/**
 * Поле необходимо для идентификации фрагмента змеи. Содержит пары (фрагмент - змея)
 * Используется, главным образом, для определения съеденной и/или съевшей змеи
 */
	private Map<BodyBlock, SnakeBody> gameField;
//	private Collection<SnakeBody> snakes;
	private Rabbit rabbit;
	private static Dimension fieldSize;
	public static final Dimension DEFAULT_FIELD_SIZE = new Dimension(25, 25);
	private boolean rabbitIsAlive = true;
	private Random rand = new Random();
	private Set<SnakeBody> snakesSet;
	private List<ScoreObserver> scoreObservers;
//	public static Player[][] fieldCells;

	public GameFieldModel() {
		fieldSize = DEFAULT_FIELD_SIZE;
		gameField = new HashMap<BodyBlock, SnakeBody>();
		snakesSet = new HashSet<SnakeBody>();
		scoreObservers = new ArrayList<ScoreObserver>();
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
//		addRandomSnakes();
	}

	public void addRandomPlayersAndSnakes(int quantity) {
		for (int i = 1; i <= quantity; i++) {
			// игрок
			Player newPlayer = new Player(String.valueOf(i));
			newPlayer.setControlKeys(GameParameters.DEFAULT_KEY_SET[i - 1]);
			// его змейка :) Указываем игрока в конструкторе
			SnakeBody newSnake = new SnakeBody(rand.nextInt(fieldSize.width),
					rand.nextInt(fieldSize.height), newPlayer);
			// связываем теперь Игрока со змеёй.
			newPlayer.setSnake(newSnake);
			addToSnakesSet(newSnake);

			// TODO А нужен ли этот массив -- Нужен для ведения счёта как минимум (казалось бы...)
			// Добавим игрока в глобальный массив для чего-нибудь
			GameParameters.players.add(newPlayer);

			//закладываем в this.gameField значения типа (кусок змеи ; змея)
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
	private void createRabbit() {
		int newRabbitXcoord = rand.nextInt(fieldSize.height);
		int newRabbitYcoord = rand.nextInt(fieldSize.width);
		rabbit = new Rabbit(newRabbitXcoord, newRabbitYcoord);
		rabbitIsAlive = true;
	}

	private void growSnake(SnakeBody snake) {
		 // увеличить длину тела
		snake.grow();
		// добавление очков игроку
		snake.getPlayer().incrScore();
		// оповещение обработчиков счёта
		notifyScoreObservers();
		// добавление нового фрагмента змеи (её головы) в gameField
		BodyBlock snakeHead = snake.getHead();
		gameField.put(snakeHead, snake);
	}

	private void killRabbit() {
		rabbitIsAlive = false;
	}

	public void moveSnakes() {
		for (SnakeBody snake : getAllSnakes()) {
			if (!snake.isAlive()) { // Мертвых змей за людей не считаем :)
				continue;
			}
			// Ситуация 1: Съеден кролик
			if (snake.getBody().contains(rabbit.getBody())) {
				growSnake(snake);
				killRabbit();
				createRabbit();
			} //Ситуация 2: Съеден кусок другой змеи
			else if (eatOtherSnake(snake)) {
				growSnake(snake);
			}
			 // Ничего особенного: простое движение змеи
			else if (snake.isAlive()) {
				moveSnake(snake);
			}
		}
	}

	private void moveSnake(SnakeBody snake) {
		snake.move();
		// шаг - это добавление в поле головы и удаление хвоста
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
					//исключение себя
					&& (!otherSnake.getPlayer().equals(thisSnake.getPlayer()))) {
				otherSnake.takeaBite(thisHead);
				SnakeBody removedSnakeBlock = gameField.remove(thisHead);
				System.out.println("Съеденная змея: " + removedSnakeBlock);
				gameField.put(thisHead, thisSnake);
				System.out.println("Съевшая змея: " + removedSnakeBlock);
				return true;
			}
		}
		return false;
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
		for (SnakeBody snake : getAllSnakes()) {
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
////			System.out.println("ДАВАААААЙ");
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
		fieldSize.width = width;
		fieldSize.height = height;
		BodyBlock.COORD_LIMITS = fieldSize;
	}

	/**
	 * Sets the value of fieldSize in Dimension value.
	 */
	public void setFieldSize(Dimension newfieldSize) {
		fieldSize = newfieldSize;
		BodyBlock.COORD_LIMITS = fieldSize;
	}

	public static void main(String[] args) {
		HashSet<SnakeBody> snakes = new HashSet<SnakeBody>();
		System.out.println(snakes.add(new SnakeBody()));
		for (SnakeBody snake : snakes) {
			System.out.println(snake);
		}
	}

	@Override
	public void addScoreObserver(ScoreObserver so) {
		try {
			scoreObservers.add(so);
		} catch (NullPointerException ex) {
			System.out.println("Ошибка назначения обработчика");
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public void removeScoreObserver(ScoreObserver so) {
		scoreObservers.remove(so);
		
	}

	@Override
	public void notifyScoreObservers() {
		for (ScoreObserver so : scoreObservers)
			so.updateScore();
	}
}
