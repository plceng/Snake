package dev.link.snake.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dev.link.snake.GameParameters;
import dev.link.snake.Player;
import dev.link.snake.ScoreObserver;

/**
 * ѕанелька со счЄтом
 */
public class ScorePanel extends JPanel implements ScoreObserver {
	private JLabel scoreFirstPlayer = new JLabel("Score of Player 1: \n");
	private JLabel scoreSecondPlayer = new JLabel("Score of Player 2: \n");

	// private Map<Player, JLabel> scoreTable = null;

	public ScorePanel() {
		// scoreTable = new HashMap<Player, JLabel>();
		setLayout(new GridLayout(2, 1));
		add(scoreFirstPlayer);
		add(scoreSecondPlayer);
	}

	public void updateScore() {
		scoreFirstPlayer.setText("Score of Player 1: \n"
				+ String.valueOf(GameParameters.players.get(0).getScore()));
		scoreSecondPlayer.setText("Score of Player 2: \n"
				+ String.valueOf(GameParameters.players.get(1).getScore()));
	}

}
