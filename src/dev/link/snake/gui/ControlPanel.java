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
	private JTextField scoreFirstPlayer = new JTextField("Field Width");
	private JTextField scoreSecondPlayer = new JTextField("Field Heigth");
	
	public ControlPanel(ActionListener newGameAction) {
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(newGameAction);
		
		setLayout(new GridLayout(3, 1));
		add(scoreFirstPlayer);
		add(scoreSecondPlayer);
		add(newGameButton);
	}
	
	// Отображение текущего счёта на панели
	private void viewScore(Player p) {
		scoreFirstPlayer.setText( String.valueOf( p.getScore() ) );
	}
	
//	public int getNewFieldWidth() {
//		return Integer.parseInt(fieldWidth.getText());
//	}
//
//	public int getNewFieldHeight() {
//		return Integer.parseInt(fieldHeigth.getText());
//	}
	
}
