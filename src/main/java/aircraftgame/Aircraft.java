package aircraftgame;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public abstract class Aircraft extends MovableObject {
	int health;
	
	Aircraft(float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		health = 100;
	}
	
}
