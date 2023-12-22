package components;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import data.DataHandler;

public class HeatseekingMissile extends Projectile{
	private final static int damage = 100;
	private final static float detectionRadius = 120f;
	

	
	public HeatseekingMissile(Display display, Aircraft aircraft, float offsetX, float offsetY, boolean friendly, DataHandler dataHandler) {
		super(aircraft.getX() + offsetX, aircraft.getY() + offsetY, aircraft.degree, friendly, damage);
		this.speedFactor = 550;
	}
	
	public void heatseekAdjustDirection(ArrayList<Aircraft> aircrafts) {
		Aircraft closestTarget = null;
		float smallestDistance = -1;
		for(Aircraft aircraft : aircrafts) {
			float targetX = aircraft.getX();
			float targetY = aircraft.getY();
			float x = this.getX();
			float y = this.getY();
			float resX = (float)Math.pow(x-targetX, 2);
			float resY = (float)Math.pow(y-targetY, 2);
			float distance = (float)Math.sqrt(resX + resY);
			if(distance <= detectionRadius && (distance <= smallestDistance || smallestDistance == -1)) {
				smallestDistance = distance;
				closestTarget = aircraft;
			}
		}
		
		if(closestTarget != null) {
			
		}
	}
	
	public void draw(Canvas canvas) {
		
	}
}
