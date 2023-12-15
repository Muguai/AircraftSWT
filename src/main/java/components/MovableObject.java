package components;

import utils.DirectionVector;

public abstract class MovableObject extends GameObject implements Move{
	protected DirectionVector directionVector;
	protected float speedFactor;
	private final float speedChange = 20.0f;
	private final float maxSpeed = 200.0f;
	private final float minSpeed = 10.0f;
	protected float degree;
	private final float degreeChange = 15.0f;
	
	/*	[class constructor] MovableObject()
	 * 	This superclass handles objects that have some kind of movement, aircrafts, missiles, etc.
	 *	@Param: xPosition - The x-position in the cartesian plane, passed to parent GameObject.
	 *	@Param: yPosition - The y-position in the cartesian plane, passed to parent GameObject.
	 *	@Param: degree - The degree that is used to create an internal direction vector for any
	 *	MovableObject objects. This is then used to compute the next position (during each frame).
	 */
	
	MovableObject(float xPosition, float yPosition, float degree){
		super(xPosition, yPosition);
		speedFactor = 5.0f;
		this.degree = degree;
		float radians = (float)Math.toRadians(degree);
		directionVector = new DirectionVector(radians);
		listenerActive = false;
	}
	
	// getDirectionVector() - Simple getter
	public DirectionVector getDirectionVector() {
		return directionVector;
	}
	
	/*	increaseSpeed()
	 * 	This function is called when UP arrow is pressed.
	 *  The function increases the speed with a stepsize of speedFactor, up to a maxSpeed.
	 */
	
	public void increaseSpeed() { speedFactor = Math.min(speedFactor + speedChange, maxSpeed); }
	
	/*	decreaseSpeed()
	 * 	This function is called when the DOWN arrow is pressed.
	 * 	It decreases the speed with a stepsize of speedFactor, down to a minSpeed.
	 */
	
	public void decreaseSpeed() { speedFactor = Math.max(speedFactor - speedChange, minSpeed); }
	
	
	/*	turnLeft()
	 * 	turnLeft() is a function that is triggered when LEFT arrow is pressed.
	 * 	On trigger, the degree of rotation is decremented by a quantity of degreeChange.
	 * 	The direction vector for the gameObject is then updated (To adjust for the left turn).
	 
	 *  Important Note: There are two ways to meassure an angle, degrees and radians. They are
	 *  not the same. 360 degrees = 2*pi radians. We need to convert degrees to radians and
	 *  this is why we are using: Math.toRadians()
	 */
	
	public void turnLeft() {
		this.degree -= degreeChange;
		float radians = (float)Math.toRadians(degree);
		directionVector.setDirection(radians);
	}
	
	
	/*	turnRight()
	 * 	turnRight is a function that is triggered when the RIGHT arrow is pressed.
	 * 	On trigger the function increments the degree of rotation, by a quantity of degreeChange.
	 * 	Similar to turnLeft(), the direction vector is then updated.
	 */
	
	public void turnRight() {
		this.degree += degreeChange;
		float radians = (float)Math.toRadians(degree);
		directionVector.setDirection(radians);
	}
	
	
	/*	moveObject()
	 * 	moveObject() is a function that updates the current position using 3 quantities:
	 *    directionVector: The direction vector tells us how much the change is in x resp y.
	 *    deltaTime: We use deltaTime to make the game independant of the frame rate.
	 *    speedFactor: Children of this class (player, enemy, etc) can adjust their speed via this.
	 *    For example, if the player ought to be faster, their speedFactor can be set higher. 
	 */
	
	public void moveObject(float deltaTime){
		 // 1. We normalize our current direction vector so the speed is consistent.
		directionVector.normalize();
		
		// 2. Component-wise update of x and y positions:
		this.position[0] += directionVector.getX()*deltaTime*speedFactor;
		this.position[1] += directionVector.getY()*deltaTime*speedFactor;
	}
}
