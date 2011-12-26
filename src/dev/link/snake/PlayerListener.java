package dev.link.snake;

import java.util.EventListener;

public interface PlayerListener extends EventListener {
	public void playerStateChanged(PlayerEvent event);
}