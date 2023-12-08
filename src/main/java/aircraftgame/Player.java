package aircraftgame;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;

public class Player extends Aircraft{
	private boolean radar;
	
	Player(float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		this.speedFactor = 50.0f;
	}
	
	/*	draw()
	 * 	Renders the player object onto the canvas at its current position.
	 */
	
	public void draw(Canvas canvas) {
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            Transform transform = new Transform(gc.getDevice());
            transform.rotate(this.degree);
            gc.setTransform(transform);
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
