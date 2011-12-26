package dev.link.snake.gui;

import java.util.EventListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dev.link.snake.GameParameters;
import dev.link.snake.Player;
import dev.link.snake.PlayerEvent;
import dev.link.snake.PlayerListener;

/**
 * ѕанелька со счЄтом
 */
public class ScorePanel extends JPanel {
	private JLabel scoreFirstPlayer = new JLabel("Score of Player");
	private JLabel scoreSecondPlayer = new JLabel("Field Heigth");

	public ScorePanel() {
		add(scoreFirstPlayer);
		add(scoreSecondPlayer);
		addPlayerListener(new PlayerListener() {
			@Override
			public void playerStateChanged(PlayerEvent event) {
				Player p = event.getPlayer();
				switch (GameParameters.players.indexOf(p)) {
				case 0 : scoreFirstPlayer.setText( String.valueOf( p.getScore() ) ); break;  
				case 1 : scoreSecondPlayer.setText( String.valueOf( p.getScore() ) ); break;				}
			}
		});
	}

	public void addPlayerListener(PlayerListener listener) {
		listenerList.add(PlayerListener.class, listener);
	}

	public void removePlayerListener(PlayerListener listener) {
		listenerList.add(PlayerListener.class, listener);
	}

}
