package dev.link.snake.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dev.link.snake.GameParameters;
import dev.link.snake.Player;
import dev.link.snake.ScoreObserver;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * ѕанелька со счЄтом
 */
public class ScorePanel extends JPanel implements ScoreObserver {
	private JLabel scoreFirstPlayer = new JLabel("");
	private JLabel scoreSecondPlayer = new JLabel("");

	// private Map<Player, JLabel> scoreTable = null;

	public ScorePanel() {
		setLayout(new GridLayout(0, 1));
		
		JLabel lblScoreOfPlayer = new JLabel("Score of Player 1: ");
		add(lblScoreOfPlayer);
		add(scoreFirstPlayer);
		
		JLabel lblNewLabel = new JLabel("Score of Player 2: ");
		add(lblNewLabel);
		add(scoreSecondPlayer);
	}

	public void updateScore() {
		scoreFirstPlayer.setText( String.valueOf( GameParameters.players.get(0).getScore() ) );
		scoreSecondPlayer.setText( String.valueOf( GameParameters.players.get(1).getScore() ) );
	}
}
