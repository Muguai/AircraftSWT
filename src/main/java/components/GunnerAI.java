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
	private float timer;
	private float spreadAngle;
	
	public GunnerAI(boolean friendly, float detectionRange, Aircraft aircraft) {
		this.friendly = friendly;
		this.detectionRange = detectionRange;
		this.aircraft = aircraft;
		this.timer = 3.0f;
		this.totalTime = this.timer;
		this.spreadAngle = 15;
	}
	
	public float getDegree(float x1, float y1, float x2, float y2) {
		float radians = (float)(Math.atan2(y1 - y2, x1 - x2));
		return (float)Math.toDegrees(radians);
	}
	
	public Projectile detectAndShoot(Display display, DataHandler dataHandler, float deltaTime) {
		for(Aircraft target : dataHandler.getAircrafts()) {
			if(aircraft == target)
				continue;

			float x1 = aircraft.getX(); //+ aircraft.offsets[0];
			float y1 = aircraft.getY(); //+ aircraft.offsets[1];
 			float x2 = target.getX();
			float y2 = target.getY();
			if(target instanceof Player) {
				x2 += ((Player)target).getDisplay().getBounds().width/2;
				y2 += ((Player)target).getDisplay().getBounds().height/2;
			}
			
			float xRes = (float)Math.pow(x1-x2, 2);
			float yRes = (float)Math.pow(y1-y2, 2);
			float distance = (float)Math.sqrt(xRes + yRes);
			if(distance <= detectionRange && totalTime >= timer) {
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
