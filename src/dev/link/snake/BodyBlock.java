package dev.link.snake;

import java.awt.Dimension;

public class BodyBlock {
	
	private int coordX;
	private int coordY;
	
	public static Dimension COORD_LIMITS = GameFieldModel.DEFAULT_FIELD_SIZE;
	
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

	public void incrCoordX() { 
		if (this.coordX >= COORD_LIMITS.width - 1)
			this.coordX = 0; 
		else
			this.coordX++;
	}
	public void decrCoordX() {
		if (this.coordX <= 0)
			this.coordX = COORD_LIMITS.width - 1;
		else
			this.coordX--;
	}
	public void incrCoordY() { 		
		if (this.coordY >= COORD_LIMITS.height - 1)
			this.coordY = 0; 
		else
			this.coordY++;
 }
	public void decrCoordY() { 		
		if (this.coordY <= 0)
			this.coordY = COORD_LIMITS.height - 1;
		else
			this.coordY--; 
	}

	
}