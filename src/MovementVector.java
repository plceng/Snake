package dev.link.snake;

//public enum MovementDirection { left, right, up, down }

public class MovementVector {

	static final int DIR_LEFT = -1;
	static final int DIR_RIGHT = 1;
	static final int DIR_UP = -2;
	static final int DIR_DOWN = 2;
	
	private int velocity;
	private int direction;
	
	public MovementVector(){}
	
	public int getVelocity(){}
	public int getDirection(){}

	public void setVelocity(int velocity){}
	public void setDirection(int direction){}
}