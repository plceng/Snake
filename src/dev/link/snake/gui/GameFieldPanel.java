package dev.link.snake.gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.Ellipse2D;
import javax.swing.Timer;
import javax.swing.JPanel;

import dev.link.snake.*;

/**
 * Представление игрового поля
 */
public class GameFieldPanel extends JPanel {

	private GameFieldModel fieldModel;
	private int drawBlockWidth; // Ширина отрисовываемого сегмента поля
	private int drawBlockHeight; // Высота отрисовываемого сегмента поля
	private static final int DEFAULT_PANEL_SIZE = 750;
	private Timer snakesTimer;
	private Timer rabbitTimer;
	private KeyHandler[] keyHandlers = new KeyHandler[GameParameters.NUMBER_OF_PLAYERS];

	public GameFieldPanel() {
		setFocusable(true);
		setBackground(Color.GREEN);
//		initHandlers();
	}

	public void initHandlers() {
		removeKeyListeners();
		int keyListenerNum = 0;
		//TODO ЗАменить GameParameters.players на fieldModel.getPlayers()
		try {
			for (SnakeBody sn : fieldModel.getAllSnakes()) {
				keyHandlers[keyListenerNum] = new KeyHandler(sn);

				addKeyListener(keyHandlers[keyListenerNum]);
				System.out.println(keyHandlers[keyListenerNum]);
				keyListenerNum++;
//			System.out.println(p);
			}
//		System.out.println(keyHandlers);
		} catch (NullPointerException ex) {
			System.out.println("fieldModel.getAllSnakes() returns NULL");
			ex.printStackTrace();
		}
	}

	public boolean removeKeyListeners() {
		// Сперва удалить старые обработчики клавиш, если они есть.
		boolean removed = false;
		for (int i = 0; i < keyHandlers.length; i++) {
			if (keyHandlers[i] != null) {
				removeKeyListener(keyHandlers[i]);
				removed = true;
			}
		}
		return removed;
	}

	public GameFieldPanel(GameFieldModel fieldModel) {
		this();
		setFieldModel(fieldModel);
	}

	public final void setFieldModel(GameFieldModel fieldModel) {
		this.fieldModel = fieldModel;
		//Таймер змеек
		if (snakesTimer != null) {
			snakesTimer.stop();
		}
		snakesTimer = new Timer(100, new SnakeTimerListener());
		snakesTimer.start();
		//Таймер кролика
		if (rabbitTimer != null) {
			rabbitTimer.stop();
		}
		rabbitTimer = new Timer(300, new RabbitTimerListener());
		rabbitTimer.start();
		requestFocus();
	}

	private void calculateDrawBlocks() {
		drawBlockWidth = (int) Math.round(getWidth() / fieldModel.getFieldWidth());
		drawBlockHeight = (int) Math.round(getHeight() / fieldModel.getFieldHeight());
	}

	public void paintComponent(Graphics graph) {
		calculateDrawBlocks();
		super.paintComponent(graph);
//		System.out.println("Hello from: paintComponent(Graphics graph)");
		Graphics2D graph2D = (Graphics2D) graph;
		drawAllSnakes(graph2D);
		drawRabbit(graph2D);
	}

	private void drawAllSnakes(Graphics2D graph2D) {
//		System.out.println("Hello from: drawAllSnakes(Graphics2D graph2D)");
		Collection<SnakeBody> snakesSet = fieldModel.getAllSnakes();
		for (SnakeBody snake : snakesSet) {
			drawOneSnake(snake, graph2D);
		}
	}

	private void drawOneSnake(SnakeBody snake, Graphics2D graph2D) {
//		System.out.println("Hello from: drawOneSnake(SnakeBody snake, Graphics2D graph2D)");
		ArrayList<BodyBlock> snakeBody = snake.getBody();
		Dimension fieldSize = fieldModel.getFieldSize();
		graph2D.setColor(snake.getColor());
		for (BodyBlock bb : snakeBody) {
			Shape drawBlock = new Ellipse2D.Double(bb.getCoordX() * drawBlockWidth,
					bb.getCoordY() * drawBlockHeight,
					drawBlockWidth,
					drawBlockHeight);
			graph2D.fill(drawBlock);
//			System.out.println("bb.getCoordX(): " + bb.getCoordX());
//			System.out.println("drawBlockWidth: " + drawBlockWidth);
		}
	}

	private void drawRabbit(Graphics2D graph2D) {
		Dimension fieldSize = fieldModel.getFieldSize();
		graph2D.setColor(Color.YELLOW);
		Rabbit rabbit = fieldModel.getRabbit();
		BodyBlock bb = rabbit.getBody();
		Shape drawBlock = new Ellipse2D.Double(bb.getCoordX() * drawBlockWidth,
				bb.getCoordY() * drawBlockHeight,
				drawBlockWidth,
				drawBlockHeight);
		graph2D.fill(drawBlock);
	}

	private class SnakeTimerListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			fieldModel.moveSnakes(); // Не менять. Должен двигать все змейки
			repaint();
		}
	}

	private class RabbitTimerListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			fieldModel.moveRabbit();
			repaint();
		}
	}

	private class KeyHandler extends KeyAdapter {
		// а нужны ли эти поля?..

		private int turnLeftKey;
		private int turnRightKey;
		private int turnUpKey;
		private int turnDownKey;
		// Без них при каждом нажатии клавиши будет обращение по ссылке к методу get...Key игрока
		private SnakeBody snake;

		public KeyHandler(SnakeBody snake) {
			this.snake = snake;
			initControlKeys();
		}

		private void initControlKeys() {
			Player player = snake.getPlayer();
			this.turnLeftKey = player.getLeftKey();
			this.turnRightKey = player.getRightKey();
			this.turnUpKey = player.getUpKey();
			this.turnDownKey = player.getDownKey();
		}

		public void keyPressed(KeyEvent keyEvent) {
			int keyCode = keyEvent.getKeyCode();
			// Обработка нажатой клавиши
			if (keyCode == turnLeftKey) {
				snake.turnLeft();
			} else if (keyCode == turnRightKey) {
				snake.turnRight();
			} else if (keyCode == turnUpKey) {
				snake.turnUp();
			} else if (keyCode == turnDownKey) {
				snake.turnDown();
			}
		}

		@Override
		public String toString() {
			return "KeyHandler{" + "turnLeftKey=" + turnLeftKey
					+ "turnRightKey=" + turnRightKey + "turnUpKey=" + turnUpKey
					+ "turnDownKey=" + turnDownKey + "player=" + snake + '}';
		}
	}

	public static void main(String args) {
	}
}
