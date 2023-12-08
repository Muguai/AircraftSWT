package aircraftgame;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class Player extends Aircraft{
	Player(float xPosition, float yPosition, float xDirection, float yDirection){
		super(xPosition, yPosition, xDirection, yDirection);
		this.speedFactor = 50.0f;
	}
	
	public void draw(Canvas canvas) {
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            gc.drawRectangle((int)this.position[0], (int)this.position[1], 100, 100);
        });
	}
}
