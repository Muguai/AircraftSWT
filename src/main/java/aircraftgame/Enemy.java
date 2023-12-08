package aircraftgame;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class Enemy extends Aircraft {
	Enemy(float xPosition, float yPosition, float xDirection, float yDirection){
		super(xPosition, yPosition, xDirection, yDirection);
		this.speedFactor = 20.0f;
	}
	
	/*	draw()
	 * 	Renders the enemy object onto the canvas at its current position.
	 */
	
	public void draw(Canvas canvas) {
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            gc.drawOval((int)this.position[0], (int)this.position[1], 100, 100);
        });
	}
}
