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
	private static final int DEFAULT_PANEL_SIZE = 600;
	
	private Timer timer;
	
 	public GameFieldPanel() {
// 		setSize(DEFAULT_PANEL_SIZE, DEFAULT_PANEL_SIZE);
// 		setBackground(Color.GREEN);
	}
 	
	public GameFieldPanel(GameFieldModel fieldModel) {
		new GameFieldPanel();
		setFocusable(true);
		timer = new Timer(200, new TimerListener());
 		setBackground(Color.GREEN);
		this.fieldModel = fieldModel;
//		System.out.println("getSize(): " + getSize());
//		System.out.println("fieldModel.getFieldWidth(): " + fieldModel.getFieldWidth());
		addKeyListener(new KeyHandler());
		timer.start();
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
		graph2D.setColor(Color.RED);
		drawAllSnakes(graph2D);
		drawRabbit(graph2D);
	}
	
	private void drawAllSnakes(Graphics2D graph2D) {
//		System.out.println("Hello from: drawAllSnakes(Graphics2D graph2D)");
		Set<SnakeBody> snakesSet = fieldModel.getSnakes();
		for (SnakeBody snake : snakesSet) 
			drawOneSnake(snake, graph2D);
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
	
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			fieldModel.moveSnakes(); // Не менять. Должен двигать все змейки
			fieldModel.moveRabbit();
			repaint();
		}
	}

	private class KeyHandler extends KeyAdapter {
		public void keyPressed(KeyEvent keyEvent) {
			int keyCode = keyEvent.getKeyCode();
			// Обработка нажатой клавиши
			if (keyCode == KeyEvent.VK_LEFT)
				fieldModel.turnSnakeLeft(1);
			else if (keyCode == KeyEvent.VK_RIGHT)
				fieldModel.turnSnakeRight(1);
			else if (keyCode == KeyEvent.VK_UP)
				fieldModel.turnSnakeUp(1);
			else if (keyCode == KeyEvent.VK_DOWN)
				fieldModel.turnSnakeDown(1);
			System.out.println("Key Pressed: " + keyCode);
		}
	}
}
