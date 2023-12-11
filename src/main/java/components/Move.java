package components;

import utils.DirectionVector;

public interface Move {
	 public DirectionVector getDirectionVector();
	 public void moveObject(float deltaTime);
}
