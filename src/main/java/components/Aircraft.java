package components;

public abstract class Aircraft extends MovableObject {
	int health;
	
	Aircraft(float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		health = 100;
	}
}
