package dev.link.snake.gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import dev.link.snake.*;

class MainFrame extends JFrame {
	private final int DEFAULT_WIDTH = 640;
	private final int DEFAULT_HEIGHT = 480;
	
	public MainFrame() {
		GameFieldModel fieldModel = new GameFieldModel();
		fieldModel.addSnakeBody(new SnakeBody());
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("Беся + Бодя = Балбесы :)");
		GameFieldPanel fieldPanel = new GameFieldPanel(fieldModel);
		add(fieldPanel);
		System.out.println(fieldPanel.getSize());
	}
}






