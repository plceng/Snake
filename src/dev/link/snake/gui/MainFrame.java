package dev.link.snake.gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import dev.link.snake.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.CompoundBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;

class MainFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;
	private ActionListener newGameAction;
	private GameFieldPanel fieldPanel;
	private GameFieldModel fieldModel;
	private ControlPanel controlPanel;

	public MainFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("Беся + Бодя = Балбесы :)");

		newGameAction = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startNewGame();
			}
		};
		
		fieldPanel = new GameFieldPanel();
		controlPanel = new ControlPanel(newGameAction);
		startNewGame();		

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(fieldPanel, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
					.addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(controlPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 435, Short.MAX_VALUE)
				.addComponent(fieldPanel, GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
		);
		getContentPane().setLayout(groupLayout);
		getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{controlPanel, fieldPanel}));


//		controlPanel = new ControlPanel(newGameAction);
//		add(controlPanel, BorderLayout.EAST);
//		fieldPanel = new GameFieldPanel();
//		add(fieldPanel);


}

	private void startNewGame() {
		GameParameters.init(); // сбрасывает игроков, однако!
		fieldModel = new GameFieldModel();
		fieldModel.addRandomPlayersAndSnakes(GameParameters.NUMBER_OF_PLAYERS);
		// добавляем обработчик счёта
		ScoreObserver scorePanel = controlPanel.getScorePanel();
		scorePanel.updateScore();
		fieldModel.addScoreObserver(scorePanel);
		// назначаем отображению игрового поля его модель
		fieldPanel.setFieldModel(fieldModel);
		// инициализация обработчиков нажатия клавиш, таймера
		fieldPanel.initKeyHandlers();

	}
}
