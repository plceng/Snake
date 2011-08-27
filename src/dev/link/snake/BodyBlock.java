package dev.link.snake;

public class BodyBlock {
	
	private int coordX;
	private int coordY;
	
	public BodyBlock(int coordX, int coordY) {
		this.coordX = coordX;
		this.coordY = coordY;
	}
	/**
	* Конструктор копирования
	*/
	public BodyBlock(BodyBlock bodyBlock) {
		this.coordX = bodyBlock.coordX;
		this.coordY = bodyBlock.coordY;
	}
	
	public int hashCode() {
		return 7*coordX*coordY;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof BodyBlock))
			return false;
		BodyBlock b = (BodyBlock) obj;
		return (this.coordX == b.coordX) && (this.coordY == b.coordY) ? true : false;
	}
	
	public String toString() {
		return "[" + String.valueOf(coordX) + "; " + String.valueOf(coordY) + "]"; 
	}
	
	public int getCoordX() { return	coordX; }
	public int getCoordY() { return	coordY; }
	
	public void setCoordX(int coordX) { this.coordX = coordX; }
	public void setCoordY(int coordY) { this.coordX = coordY; }

	public void incrCoordX() { this.coordX++; }
	public void decrCoordX() { this.coordX--; }
	public void incrCoordY() { this.coordY++; }
	public void decrCoordY() { this.coordY--; }
	
	
}