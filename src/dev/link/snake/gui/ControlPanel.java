package dev.link.snake.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import dev.link.snake.*;

/**
* Панелька управления
*/
public class ControlPanel extends JPanel {
	
	private JButton newGameButton;
	private JTextField fieldWidth = new JTextField("Field Width");
	private JTextField fieldHeigth = new JTextField("Field Heigth");
	
	public ControlPanel(ActionListener newGameActionListener) {
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(newGameActionListener);
		add(fieldWidth);
		add(fieldHeigth);
		add(newGameButton);
		
	}
	
	public int getNewFieldWidth() {
		return Integer.parseInt(fieldWidth.getText());
	}

	public int getNewFieldHeight() {
		return Integer.parseInt(fieldHeigth.getText());
	}
	
}
