package components;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Bullet extends Projectile {
	
	private final String PLAYER_IMAGE_PATH = "src\\main\\java\\resources\\images\\bullets\\bullet_2_blue.png"; 
	private final String ENEMY_IMAGE_PATH = "src\\main\\java\\resources\\images\\bullets\\bullet_2_orange.png";
	private final static int damage = 10;
	private Image bulletImage;
		
	public Bullet(Display display, Aircraft aircraft, boolean friendly) {
		super(aircraft.getX(), aircraft.getY(), aircraft.degree, friendly, damage);
		
		this.speedFactor = 200.f;
		
		// Bullet picture based on player or enemy
		try {
			if (aircraft instanceof Player) {
				bulletImage = new Image(display, PLAYER_IMAGE_PATH);
			} else if (aircraft instanceof Enemy) {
				bulletImage = new Image(display, ENEMY_IMAGE_PATH);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void draw(Canvas canvas) {
		if(listenerActive) {
			return;
		}
		listenerActive = true;
		
		canvas.addPaintListener(e -> {
            GC gc = e.gc;
            int x = (int)(this.position[0] + this.offsets[0] + canvas.getBounds().width/2);
            int y = (int)(this.position[1] + this.offsets[1] + canvas.getBounds().height/2);
            
            // 1. Get the transform and translate it to (x,y):
            Transform transform = new Transform ( gc.getDevice () );
            transform.translate(x, y);
            
            // 2. After this translation, do rotation:
            transform.rotate((degree+90));
            
            // 3. Draw out the object centered in the transform's origo:
            gc.setTransform ( transform );
            gc.drawImage(bulletImage, -bulletImage.getBounds().width/2, -bulletImage.getBounds().height/2);
            
            // 4. Set the new transform as the identity transform and dispose the old one:
            gc.setTransform(null);
            transform.dispose();
		});
	}

}
