package aircraftgame;

public abstract class Aircraft extends GameObject implements Move{
	float[] directionVector = new float[2];
	boolean team;
	int health;
	
	Aircraft(int xPosition, int yPosition, float xDirection, float yDirection, boolean team){
		super(xPosition, yPosition);
		directionVector[0] = xDirection;
		directionVector[1] = yDirection;
		this.team = team;
		health = 100;
	}
	
	public abstract float[] getDirectionVector();
	public abstract void draw();
}
