package components;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Enemy extends Aircraft {
	private Image planeImage;
	
	public Enemy(Display display, float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		this.speedFactor = 20.0f;
		String relPath = "src\\main\\java\\resources\\Aircraft_06.png";
		planeImage = new Image(display, relPath); 
	}
	
	/*	draw()
	 * 	Renders the enemy object onto the canvas at its current position.
	 */
	
	public void draw(Canvas canvas) {
		
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            int x = (int)(this.position[0] + this.offsets[0]);
            int y = (int)(this.position[1] + this.offsets[1]);
            gc.drawImage(planeImage, x, y);
        });
	}
}
