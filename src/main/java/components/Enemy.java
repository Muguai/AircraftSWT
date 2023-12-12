package components;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Enemy extends Aircraft {
	private Image planeImage;
	private boolean listenerActive;
	
	public Enemy(Display display, float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		this.speedFactor = 20.0f;
		String relPath = "src\\main\\java\\resources\\Aircraft_06.png";
		planeImage = new Image(display, relPath); 
		listenerActive = false;
	}
	
	/*	draw()
	 * 	Renders the enemy object onto the canvas at its current position.
	 */
	
	public void draw(Canvas canvas) {
		if(listenerActive)
			return;
		
		listenerActive = true;
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            int x = (int)(this.position[0] + this.offsets[0]);
            int y = (int)(this.position[1] + this.offsets[1]);
    		
            // 1. Get the transform and translate it to (x,y):
            Transform transform = new Transform ( gc.getDevice () );
            transform.translate(x, y);
            
            // 2. After this translation, do rotation:
            transform.rotate((degree+90));
            
            // 3. Draw out the object centered in the transform's origo:
            gc.setTransform ( transform );
            gc.drawImage(planeImage, -planeImage.getBounds().width/2, -planeImage.getBounds().height/2);
            
            // 4. Set the new transform as the identity transform and dispose the old one:
            gc.setTransform(null);
            transform.dispose();
        });
	}
}
