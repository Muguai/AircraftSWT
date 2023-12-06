package aircraftgame;

public abstract class Aircraft extends MovableObject {
	int health;
	
	Aircraft(int xPosition, int yPosition, float xDirection, float yDirection){
		super(xPosition, yPosition, xDirection, yDirection);
		health = 100;
	}
}
