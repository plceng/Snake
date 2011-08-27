package dev.link.snake;

enum MovementDirection { DIR_LEFT, DIR_RIGHT, DIR_UP, DIR_DOWN }

public class MovementVector {

	// static final int DIR_LEFT = -1;
	// static final int DIR_RIGHT = 1;
	// static final int DIR_UP = -2;
	// static final int DIR_DOWN = 2;
	
	private int velocity;
	private MovementDirection direction;
	
	public MovementVector() {
		this.velocity = 2;
		this.direction = MovementDirection.DIR_RIGHT;
	}
	
	public int getVelocity() { return velocity; }
	public MovementDirection getDirection() { return direction; }

	public void setVelocity(int velocity) {}
	public void setDirection(MovementDirection direction) { this.direction = direction; }
	
	public String toString() { return "Direction = " + direction + "   Velocity = " + velocity; }
	
	public int hashCode() {
		return 13*velocity*direction.hashCode();
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof MovementVector))
			return false;
		MovementVector other = (MovementVector) obj;
		return ((this.velocity == other.velocity)
				&& this.direction.equals(other.direction)) 
			? true : false;
	}
	
	public static void main(String[] args) {
		MovementVector mv = new MovementVector();
		System.out.println(mv);
		MovementDirection md = mv.getDirection();
		System.out.println(md);
	}
}
