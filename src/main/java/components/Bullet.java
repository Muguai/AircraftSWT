package components;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Bullet extends Projectile {
	
	private final String PLAYER_IMAGE_PATH =  "src\\main\\java\\resources\\images\\bullets\\bullet_2_blue.png"; //"src\\main\\java\\resources\\images\\bullets\\baby.png";
	private final String ENEMY_IMAGE_PATH = "src\\main\\java\\resources\\images\\bullets\\bullet_2_orange.png"; // "src\\main\\java\\resources\\images\\bullets\\baby.png"; //
	private final static int damage = 50;
	private Image bulletImage;
		
	public Bullet(Display display, Aircraft aircraft, float offsetX, float offsetY, boolean friendly) {
		super(aircraft.getX() + offsetX, aircraft.getY() + offsetY, aircraft.degree, friendly, damage);
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
                gc.drawImage(bulletImage, -bulletImage.getBounds().width / 2, -bulletImage.getBounds().height / 2);
                gc.setTransform(null);
                transform.dispose();
            }

        };

        canvas.addPaintListener(paintListener);
	}

}
