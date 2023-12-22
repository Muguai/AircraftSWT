package components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import data.DataHandler;

public class HeatseekingMissile extends Projectile{
	private final static int damage = 250;
	private final static float DETECTION_RADIUS = 120f;
	private final static String MISSILE_URL = "src\\main\\java\\resources\\images\\bullets\\storm_shadow.png"; 

	/*	[class constructor] HeastseekingMissile
	 * 	A type of projectile that deals a lot of damage and that uses homing on nearby enemies. 
	 */
	
	public HeatseekingMissile(Display display, Aircraft aircraft, float offsetX, float offsetY, boolean friendly, DataHandler dataHandler) {
		super(aircraft.getX() + offsetX, aircraft.getY() + offsetY, aircraft.degree, friendly, damage);
		this.speedFactor = 750;
		try {
			projectileImage = new Image(display, MISSILE_URL);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*	heatseekAdjustDirection()
	 * 	A method that scans the air for enemies and uses homing towards them if one such can be found.
	 */
	
	public void heatseekAdjustDirection(List<Aircraft> aircrafts) {
		
		// 1. Scan for enemies:
		Aircraft closestTarget = null;
		float smallestDistance = -1;
		float enemyDegree = 0;
		for(Aircraft aircraft : aircrafts) {
			if(aircraft.friendly == this.friendly)
				continue;
			
			float targetX = aircraft.getX();
			float targetY = aircraft.getY();
			float x = this.getX();
			float y = this.getY();
			float resX = (float)Math.pow(x-targetX, 2);
			float resY = (float)Math.pow(y-targetY, 2);
			float distance = (float)Math.sqrt(resX + resY);
			if(distance <= DETECTION_RADIUS && (distance <= smallestDistance || smallestDistance == -1)) {
				smallestDistance = distance;
				closestTarget = aircraft;
				enemyDegree = (float)Math.PI +(float)(Math.atan2(y - targetY, x - targetX));
			}
		}
		
		// 2. If an enemy is found, change direction vector towards them:
		if(closestTarget != null) {
			this.degree = enemyDegree;
			this.directionVector.setDirection(enemyDegree);
		}
	}
}
