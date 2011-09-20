package dev.link.snake.gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import dev.link.snake.*;

class MainFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;
	private ActionListener newGameActionListener;
	private GameFieldPanel fieldPanel;
	private GameFieldModel fieldModel;
	private ControlPanel controlPanel;

	
	public MainFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("Беся + Бодя = Балбесы :)");

		newGameActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startNewGame();	
			}
		};


		controlPanel = new ControlPanel(newGameActionListener);
		add(controlPanel, BorderLayout.EAST);

		
		fieldPanel = new GameFieldPanel();
		startNewGame();
		add(fieldPanel);
	}
	
	private void startNewGame(){
		fieldModel = new GameFieldModel();
		fieldModel.addSnakeBody(new SnakeBody());
		fieldPanel.setFieldModel(fieldModel);
	}
	
/*	private class NewGameActionListener implements ActionListener {
		public void actionPerformed() {
			startNewGame();	
		}

	}
*/	
	
	
}






