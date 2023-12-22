package components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import data.DataHandler;

public class GunnerAI {
	private boolean friendly;
	private float detectionRange;
	private Aircraft aircraft;
	private float totalTime;
	private float reloadTime;
	private float spreadAngle;
	
	/*	[class constructor] GunnerAI()
	 * 	An object that can attach to friendly aircrafts and/or enemy aircrafts.
	 *  A GunnerAI object detects an enemy in a radius and opens fire at them.
	 *  @Param: friendly - A boolean to discern what team the object is on.
	 *  @Param: detectionRange - A radius where we can discover aircrafts.
	 *  @Param: aircraft - The aircraft object that the GunnerAI is attached to.
	 */
	
	public GunnerAI(boolean friendly, float detectionRange, Aircraft aircraft) {
		this.friendly = friendly;
		this.detectionRange = detectionRange;
		this.aircraft = aircraft;
		this.reloadTime = 10.0f;
		this.totalTime = this.reloadTime;
		this.spreadAngle = 30;
	}
	
	public float getDegree(float x1, float y1, float x2, float y2) {
		float radians = (float)(Math.atan2(y1 - y2, x1 - x2));
		return (float)Math.toDegrees(radians);
	}
	
	public Projectile detectAndShoot(Display display, DataHandler dataHandler, float deltaTime) {
		// 1. Iterate over every aircraft:
		for(Aircraft target : dataHandler.getAircrafts()) {
			
			// 2. If the target is ourselves, continue:
			if(aircraft == target)
				continue;

			// 3. Obtain coordinates for the aircraft and the target:
			float x1 = aircraft.getX(); 
			float y1 = aircraft.getY(); 
 			float x2 = target.getX();
			float y2 = target.getY();
			if(target instanceof Player) {
				x2 += ((Player)target).getDisplay().getBounds().width/2;
				y2 += ((Player)target).getDisplay().getBounds().height/2;
			}
			
			// 4. Calculate the distance between aircraft and target:
			float xRes = (float)Math.pow(x1-x2, 2);
			float yRes = (float)Math.pow(y1-y2, 2);
			float distance = (float)Math.sqrt(xRes + yRes);
			
			// 5. If the airplane has reloaded and an enemy is in range, fire in a spread angle and start to reload again:
			if(distance <= detectionRange && totalTime >= reloadTime) {
				if(aircraft.friendly != target.friendly) {
					float fireDegree = getDegree(x1, y1, x2, y2);
					float initDegree = aircraft.degree;
					totalTime = 0;
					float randomDegree = (float)(Math.random()-0.5)*(2*this.spreadAngle);
					aircraft.degree = 180+fireDegree + randomDegree;
					Bullet bullet = new Bullet(display, aircraft, 0, 0, aircraft.friendly);
					aircraft.degree = initDegree;
					return bullet;
				}
				
			}
			else {
				totalTime += deltaTime;
			}
		}
		return null;
	}
}
