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
	
 	public GameFieldPanel() {
		setFocusable(true);
 		setBackground(Color.GREEN);
		KeyHandler firstPlayerKeyHandler = new KeyHandler(KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, GameParameters.FIRST_PLAYER_NAME);
		addKeyListener(firstPlayerKeyHandler);
		KeyHandler secondPlayerKeyHandler = new KeyHandler(KeyEvent.VK_A,
				KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, GameParameters.SECOND_PLAYER_NAME);
		addKeyListener(secondPlayerKeyHandler);

	}
 	
	public GameFieldPanel(GameFieldModel fieldModel) {
		new GameFieldPanel();
		setFieldModel(fieldModel);
	}
	
	public final void setFieldModel(GameFieldModel fieldModel) {
		this.fieldModel = fieldModel;
		//Таймер змеек
		if (snakesTimer != null)
			snakesTimer.stop();
		snakesTimer = new Timer(100, new SnakeTimerListener());
		snakesTimer.start();
		//Таймер кролика
		if (rabbitTimer != null)
			rabbitTimer.stop();
		rabbitTimer = new Timer(300, new RabbitTimerListener());
		rabbitTimer.start();
		requestFocus();
	}
	
	private void calculateDrawBlocks() {
		drawBlockWidth = (int) Math.round( getWidth() / fieldModel.getFieldWidth() );
		drawBlockHeight = (int) Math.round( getHeight() / fieldModel.getFieldHeight() );
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
		graph2D.setColor(Color.RED);
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
		private int turnLeftKey;
		private int turnRightKey;
		private int turnUpKey;
		private int turnDownKey;
		private String playerName;

		public KeyHandler(int turnLeftKey, int turnRightKey, int turnUpKey,
				int turnDownKey, String playerName) {
			this.turnLeftKey = turnLeftKey;
			this.turnRightKey = turnRightKey;
			this.turnUpKey = turnUpKey;
			this.turnDownKey = turnDownKey;
			this.playerName = playerName;
		}

		public void keyPressed(KeyEvent keyEvent) {
			int keyCode = keyEvent.getKeyCode();
			// Обработка нажатой клавиши
			if (keyCode == turnLeftKey)
				fieldModel.turnSnakeLeft(playerName);
			else if (keyCode == turnRightKey)
				fieldModel.turnSnakeRight(playerName);
			else if (keyCode == turnUpKey)
				fieldModel.turnSnakeUp(playerName);
			else if (keyCode == turnDownKey)
				fieldModel.turnSnakeDown(playerName);
		}
	}
}
