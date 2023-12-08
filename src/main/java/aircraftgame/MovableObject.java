package aircraftgame;

public abstract class MovableObject extends GameObject implements Move{
	DirectionVector directionVector;
	float speedFactor;
	
	MovableObject(float xPosition, float yPosition, float xDirection, float yDirection){
		super(xPosition, yPosition);
		directionVector = new DirectionVector(xDirection, yDirection);
		speedFactor = 5.0f;
	}
	
	public DirectionVector getDirectionVector() {
		return directionVector;
	};
	
	
	/*	moveObject()
	 * 
	 */
	public void moveObject(float deltaTime){
		System.out.println(deltaTime);
		this.position[0] += directionVector.x*deltaTime*speedFactor;
		this.position[1] += directionVector.y*deltaTime*speedFactor;
	}
}
