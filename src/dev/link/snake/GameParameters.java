/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dev.link.snake;


import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 *
 * @author link
 */
public class GameParameters {
	public static String FIRST_PLAYER_NAME = "first";
	public static String SECOND_PLAYER_NAME = "second";
	public static Color DEFAULT_FIRST_COLOR = Color.RED;
	public static Color DEFAULT_SECOND_COLOR = Color.CYAN;

	public static Set<Player> players = new HashSet<Player>();

	public static int[][] DEFAULT_KEY_SET =
	{	{KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN } ,
		{KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S }	};
	public static int NUMBER_OF_PLAYERS = 2;



	public static void init() {
		players = new HashSet<Player>();
//		players.add(new Player(FIRST_PLAYER_NAME, DEFAULT_FIRST_COLOR));
//		players.add(new Player(SECOND_PLAYER_NAME, DEFAULT_SECOND_COLOR));
	}

}
