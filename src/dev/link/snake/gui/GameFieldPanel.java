package dev.link.snake.gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.Ellipse2D;

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
	
 	public GameFieldPanel() {
// 		setSize(DEFAULT_PANEL_SIZE, DEFAULT_PANEL_SIZE);
// 		setBackground(Color.GREEN);
	}
 	
	public GameFieldPanel(GameFieldModel fieldModel) {
		new GameFieldPanel();
 		setBackground(Color.GREEN);
		this.fieldModel = fieldModel;
//		System.out.println("getSize(): " + getSize());
//		System.out.println("fieldModel.getFieldWidth(): " + fieldModel.getFieldWidth());
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
		graph2D.fill(new Ellipse2D.Double(200, 200, 50, 50));
	}
/* 	public static Dimension ScaleDimension(Dimension dim, int scaleValue) {
		return new Dimension(dim.width * scaleValue, dim.height * scaleValue);
	}
 */	
	private void drawRabbit(Graphics2D graph2D) {
		
	}
	
//	public static 

}
