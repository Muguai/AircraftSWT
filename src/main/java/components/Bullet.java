package components;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import utils.SoundManager;

public class Bullet extends Projectile {
	
	private final String PLAYER_IMAGE_PATH =  "src\\main\\java\\resources\\images\\bullets\\bullet_2_blue.png"; //"src\\main\\java\\resources\\images\\bullets\\baby.png";
	private final String ENEMY_IMAGE_PATH = "src\\main\\java\\resources\\images\\bullets\\bullet_2_orange.png"; // "src\\main\\java\\resources\\images\\bullets\\baby.png"; //
	private final static int damage = 50;
	private SoundManager soundManager;
	
	/* 	[class constructor] bullet
	 * 	A class that defines a bullet object.
	 * 	A bullet has a set speedFactor and moves forward with that speed.
	 *  The bullet will collide with the first enemy (aircraft with non-matching 'friendly' boolean).
	 *  At that point that aircraft will take sustaing damage according to the variable: 'damage'-
	 *  Blue bullets are friendly to the player team and orange bullets are friendly to the enemy team.
	 */
	
	public Bullet(Display display, Aircraft aircraft, float offsetX, float offsetY, boolean friendly) {
		
		// 1. Set fields via the superclass:
		super(aircraft.getX() + offsetX, aircraft.getY() + offsetY, aircraft.degree, friendly, damage, 3.0f);
		this.speedFactor = 400.f;
		this.lifetime = 2.0f;
		
		// 2. Bullet picture based on player or enemy:
		try {
			if (friendly) {
				projectileImage = new Image(display, PLAYER_IMAGE_PATH);
			} else if (aircraft instanceof FighterPlane) {
				projectileImage = new Image(display, ENEMY_IMAGE_PATH);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// 3. Play the gun shot sound file:
		soundManager = new SoundManager();
		this.soundManager.playGunshot();
	}

}
