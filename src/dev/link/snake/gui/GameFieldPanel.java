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
* ������������� �������� ����
*/
public class GameFieldPanel extends JPanel {
	private GameFieldModel fieldModel;
	private int drawBlockWidth; // ������ ��������������� �������� ����
	private int drawBlockHeight; // ������ ��������������� �������� ����
	private static final int DEFAULT_PANEL_SIZE = 750;
	
	private Timer snakesTimer;
	private Timer rabbitTimer;
	
 	public GameFieldPanel() {
		setFocusable(true);
 		setBackground(Color.GREEN);
		addKeyListener(new KeyHandler());
	}
 	
	public GameFieldPanel(GameFieldModel fieldModel) {
		new GameFieldPanel();
		setFieldModel(fieldModel);
	}
	
	public final void setFieldModel(GameFieldModel fieldModel) {
		this.fieldModel = fieldModel;
		//������ �����
		if (snakesTimer != null)
			snakesTimer.stop();
		snakesTimer = new Timer(100, new SnakeTimerListener());
		snakesTimer.start();
		//������ �������
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
	
	private class SnakeTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			fieldModel.moveSnakes(); // �� ������. ������ ������� ��� ������
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
		public void keyPressed(KeyEvent keyEvent) {
			int keyCode = keyEvent.getKeyCode();
			// ��������� ������� �������
			if (keyCode == KeyEvent.VK_LEFT)
				fieldModel.turnSnakeLeft(1);
			else if (keyCode == KeyEvent.VK_RIGHT)
				fieldModel.turnSnakeRight(1);
			else if (keyCode == KeyEvent.VK_UP)
				fieldModel.turnSnakeUp(1);
			else if (keyCode == KeyEvent.VK_DOWN)
				fieldModel.turnSnakeDown(1);
		}
	}
}
