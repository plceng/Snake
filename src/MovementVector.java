package dev.link.snake;

public enum MovementDirection { DIR_LEFT, DIR_RIGHT, DIR_UP, DIR_DOWN }

public class MovementVector {

	// static final int DIR_LEFT = -1;
	// static final int DIR_RIGHT = 1;
	// static final int DIR_UP = -2;
	// static final int DIR_DOWN = 2;
	
	private int velocity;
	private MovementDirection direction;
	
	public MovementVector(){}
	
	public int getVelocity(){ return velocity; }
	public int getDirection(){ return direction; }

	public void setVelocity(int velocity){}
	public void setDirection(MovementDirection direction){}
}