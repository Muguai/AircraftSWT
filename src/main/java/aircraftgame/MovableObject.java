package aircraftgame;

public abstract class MovableObject extends GameObject implements Move{
	DirectionVector directionVector;
	float speedFactor;
	
	MovableObject(float xPosition, float yPosition, float xDirection, float yDirection){
		super(xPosition, yPosition);
		directionVector = new DirectionVector(xDirection, yDirection);
		speedFactor = 5.0f;
	}
	
	// getDirectionVector() - Simple getter
	public DirectionVector getDirectionVector() {
		return directionVector;
	};
	
	
	/*	moveObject()
	 * 	moveObject() is a function that updates the current position.
	 * 	1. We normalize our current direction vector so the speed is consistent.
	 *  2. Component-wise update of x and y:
	 *    directionVector: The direction vector tells us how much the change is in x resp y.
	 *    deltaTime: We use deltaTime to make the game independant of the frame rate.
	 *    speedFactor: Children of this class (player, enemy, etc) can adjust their speed via this.
	 *    For example, if the player ought to be faster, their speedFactor can be set higher. 
	 */
	
	public void moveObject(float deltaTime){
		directionVector.normalize();
		this.position[0] += directionVector.x*deltaTime*speedFactor;
		this.position[1] += directionVector.y*deltaTime*speedFactor;
	}
}
