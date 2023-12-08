package aircraftgame;

public abstract class Aircraft extends MovableObject {
	int health;
	
	Aircraft(float xPosition, float yPosition, float xDirection, float yDirection){
		super(xPosition, yPosition, xDirection, yDirection);
		health = 100;
	}
}
