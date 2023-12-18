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
	
	public GunnerAI(boolean friendly, float detectionRange, Aircraft aircraft) {
		this.friendly = friendly;
		this.detectionRange = detectionRange;
		this.aircraft = aircraft;
		this.timer = 3.0f;
		this.totalTime = this.timer;
	}
	
	public Projectile detectAndShoot(Display display, DataHandler dataHandler, float deltaTime) {
		for(Aircraft target : dataHandler.getAircrafts()) {
			if(aircraft == target)
				continue;

			float x1 = aircraft.getX(); //+ aircraft.offsets[0];
			float y1 = aircraft.getX(); //+ aircraft.offsets[1];
 			float x2 = target.getX() + target.offsets[0];
			float y2 = target.getY() + target.offsets[1];
			if(target instanceof Player) {
				x2 += ((Player)target).getDisplay().getBounds().width/2;
				y2 += ((Player)target).getDisplay().getBounds().height/2;
			}
			
			float xRes = (float)Math.pow(x1-x2, 2);
			float yRes = (float)Math.pow(y1-y2, 2);
			
			if(Math.sqrt(xRes + yRes) <= detectionRange && totalTime >= timer) {
				totalTime = 0;
				if(aircraft.friendly != target.friendly) {
					Bullet bullet = new Bullet(display, aircraft, 0, 0, aircraft.friendly);
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
