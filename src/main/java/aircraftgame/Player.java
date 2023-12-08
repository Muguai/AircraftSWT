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
		int x = canvas.getBounds().width/2;
		int y = canvas.getBounds().height/2;
		
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            Transform transform = new Transform ( gc.getDevice () );
            transform.translate(x, y);
            transform.rotate(degree-1f);
            gc.setTransform ( transform );
            gc.drawRectangle(-50, -50, 100, 100);
            transform.translate(0, 0);
            gc.setTransform(null);
        });
	}
	
	public float getXOffset() {
		return -this.position[0];
	}
	
	public float getYOffset() {
		return -this.position[1];
	}
}
