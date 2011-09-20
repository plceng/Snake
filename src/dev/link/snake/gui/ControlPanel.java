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
	
	public ControlPanel(ActionListener newGameActionListener) {
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(newGameActionListener);
		add(newGameButton);
	}

}
