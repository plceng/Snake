package dev.link.snake;

public class BodyBlock {
	
	private int coordX;
	private int coordY;
	
	public BodyBlock(int coordX, int coordY){}
	public BodyBlock(BodyBlock bodyBlock){}
	public int hashCode(){}
	public boolean equals(Object obj){}
	
	public getCoordX() { 	}
	public getCoordY() {	}
	public setCoordX(int coordX) {	}
	public setCoordY(int coordY) {	}

	public void decrCoordX() { this.coordX++ }
	public void decrCoordX() { this.coordX-- }
	public void incrCoordY() { this.coordY++ }
	public void incrCoordY() { this.coordY-- }
}