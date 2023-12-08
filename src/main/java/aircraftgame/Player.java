package aircraftgame;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class Player extends Aircraft{
	Player(float xPosition, float yPosition, float xDirection, float yDirection){
		super(xPosition, yPosition, xDirection, yDirection);
		this.speedFactor = 50.0f;
	}
	
	/*	draw()
	 * 	Renders the player object onto the canvas at its current position.
	 */
	
	public void draw(Canvas canvas) {
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            //gc.drawRectangle((int)this.position[0], (int)this.position[1], 100, 100);
            gc.drawRectangle(canvas.getBounds().width/2, canvas.getBounds().height/2, 100, 100);
        });
	}
	
	public float getXOffset() {
		return -this.position[0];
	}
	
	public float getYOffset() {
		return -this.position[1];
	}
}
