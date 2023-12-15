package components;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Enemy extends Aircraft {
	private Image planeImage;
	
	public Enemy(Display display, float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		this.speedFactor = 20.0f;
		String relPath = "src\\main\\java\\resources\\images\\aircrafts\\putin.png";
		planeImage = new Image(display, relPath);
		this.health = 100;
		this.setCenter(planeImage.getBounds().width/4, planeImage.getBounds().height/4);
	}
	
	/*	draw()
	 * 	Renders the enemy object onto the canvas at its current position.
	 */
	
    public void draw(Canvas canvas) {
        if (listenerActive)
            return;

        listenerActive = true;

        paintListener = new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                GC gc = e.gc;
                int x = (int) (position[0] + offsets[0]);
                int y = (int) (position[1] + offsets[1]);

                Transform transform = new Transform(gc.getDevice());
                transform.translate(x, y);
                transform.rotate((degree + 90));
                gc.setTransform(transform);
                gc.drawImage(planeImage, -planeImage.getBounds().width / 2, -planeImage.getBounds().height / 2);
                gc.setTransform(null);
                transform.dispose();
            }
        };

        canvas.addPaintListener(paintListener);
    }
}
