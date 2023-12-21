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
	
	/*	[class constructor] Enemy()
	 * 	This class defines an enemy aircraft.
	 * 	The enemy aircraft has a position that is affected by offsets set via the root super parent: Game Object.
	 *  The enemy aircraft moves around at a predefined speed factor.
	 *  A gunnerAI object is attached to the enemy and will open fire on the player and player teammates possibly.
	 *  
	 */
	
	public Enemy(Display display, float xPosition, float yPosition, float degree, boolean kim){
		super(xPosition, yPosition, degree);
		this.speedFactor = 20.0f;
		this.friendly = false;
		this.gunnerAI = new GunnerAI(false, 500.0f, this);
		
		// Hihi
		String relPath = "src\\main\\java\\resources\\images\\aircrafts\\";
		if (kim) {
			relPath = "src\\main\\java\\resources\\images\\aircrafts\\aircraft_02.png";
		} else {
			relPath = "src\\main\\java\\resources\\images\\aircrafts\\aircraft_05.png";
		}
		//
		
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
