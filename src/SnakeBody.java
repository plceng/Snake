package dev.link.snake;

public class SnakeBody  implements Controlable {

	private Deque<BodyBlock> bodyBlocks;
	private MovementVector moveVector;

	public SnakeBody(){}

	// public void turnLeft(){
		// moveVector.setDirection(MovementVector.DIR_LEFT);
	// }

	// public void turnRigth(){
		// moveVector.setDirection(MovementVector.DIR_RIGHT);
	// }

	// public void turnUp(){
		// moveVector.setDirection(MovementVector.DIR_UP);
	// }

	// public void turnDown(){
		// moveVector.setDirection(MovementVector.DIR_DOWN);
	// }

	public void turnLeft(){
		moveVector.setDirection(MovementDirection.DIR_LEFT);
	}

	public void turnRigth(){
		moveVector.setDirection(MovementDirection.DIR_RIGHT);
	}

	public void turnUp(){
		moveVector.setDirection(MovementDirection.DIR_UP);
	}

	public void turnDown(){
		moveVector.setDirection(MovementDirection.DIR_DOWN);
	}

	public void grow(){
		BodyBlock newFirstBlock = new BodyBlock(bodyBlocks.getLast());
		switch (moveVector.getDirection()) {
			case DIR_LEFT: newFirstBlock.decrCoordX(); break;
			
			case DIR_RIGHT: newFirstBlock.incrCoordX(); break;
			
			case DIR_UP: newFirstBlock.decrCoordY(); break;
			
			case DIR_DOWN: newFirstBlock.incrCoordY(); break;
		}
		bodyBlocks.addFirst(newFirstBlock);
	}

	public void step() {
		bodyBlocks.removeLast();
		grow();
	}

	public boolean isValid() {}

}