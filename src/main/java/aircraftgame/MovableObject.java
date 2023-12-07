package aircraftgame;

public abstract class MovableObject extends GameObject implements Move{
	DirectionVector directionVector;
	MovableObject(int xPosition, int yPosition, float xDirection, float yDirection){
		super(xPosition, yPosition);
		directionVector = new DirectionVector(xDirection, yDirection);
	}
	
	public DirectionVector getDirectionVector() {
		return directionVector;
	}
	
	public void moveObject(){
		
	}
	public abstract void draw();
}
