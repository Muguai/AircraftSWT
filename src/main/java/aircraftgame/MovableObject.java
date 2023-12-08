package aircraftgame;

public abstract class MovableObject extends GameObject implements Move{
	private DirectionVector directionVector;
	protected float speedFactor;
	private final float speedChange = 20.0f;
	private final float maxSpeed = 200.0f;
	private final float minSpeed = 10.0f;
	
	MovableObject(float xPosition, float yPosition, float xDirection, float yDirection){
		super(xPosition, yPosition);
		directionVector = new DirectionVector(xDirection, yDirection);
		speedFactor = 5.0f;
	}
	
	// getDirectionVector() - Simple getter
	public DirectionVector getDirectionVector() {
		return directionVector;
	}
	
	//public float getSpeedFactor() { return speedFactor; }
	
	public void increaseSpeed() { speedFactor = Math.min(speedFactor + speedChange, maxSpeed); }
	public void decreaseSpeed() { speedFactor = Math.max(speedFactor - speedChange, minSpeed); }
	
	
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
		this.position[0] += directionVector.getX()*deltaTime*speedFactor;
		this.position[1] += directionVector.getY()*deltaTime*speedFactor;
	}
}
