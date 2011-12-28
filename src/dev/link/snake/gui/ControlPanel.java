package dev.link.snake.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import dev.link.snake.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
* Панелька управления
*/
public class ControlPanel extends JPanel {
	
	private JButton newGameButton;
	private ScorePanel scorePanel;

	
	public ControlPanel(ActionListener newGameAction) {
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(newGameAction);
		scorePanel = new ScorePanel();
		scorePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(newGameButton, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
				.addComponent(scorePanel, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(newGameButton, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
					.addComponent(scorePanel, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addGap(1))
		);
		setLayout(groupLayout);
		
		// Добавляем обработчик, реагирующий на изменение счёта
	}


	public ScoreObserver getScorePanel() {
		return scorePanel;
	}
	
	// Отображение текущего счёта на панели

}
