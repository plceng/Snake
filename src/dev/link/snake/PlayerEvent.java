/**
 * 
 */
package dev.link.snake;

/**
 * @author link
 *
 */
public class PlayerEvent {
	private Player p;
	
	public PlayerEvent(Player p) {
		this.p = p;
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
}
