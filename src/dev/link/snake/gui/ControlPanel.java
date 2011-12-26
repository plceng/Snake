package dev.link.snake.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import dev.link.snake.*;

/**
* �������� ����������
*/
public class ControlPanel extends JPanel {
	
	private JButton newGameButton;
	private ScorePanel scorePanel;

	
	public ControlPanel(ActionListener newGameAction) {
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(newGameAction);
		scorePanel = new ScorePanel();

		setLayout(new GridLayout(4, 1));
		add(newGameButton);
		add(scorePanel);
		
		// ��������� ����������, ����������� �� ��������� �����
	}


	public ScoreObserver getScorePanel() {
		return scorePanel;
	}
	
	// ����������� �������� ����� �� ������

}
