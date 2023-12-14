package components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Canvas;

public abstract class Projectile extends MovableObject{
	private boolean friendly;
	private int damage;
	
	Projectile(float x, float y, float degree, boolean friendly, int damage){
		super(x, y, degree);
		this.friendly = friendly;
		this.damage = damage;
		this.setCenter(0, 0);
	}
	
	public float euclideanDist(float x1, float y1, float x2, float y2) {
		float xRes = (float)(Math.pow(x1 - x2, 2));
		float yRes = (float)(Math.pow(y1 - y2, 2));
		return (float)Math.sqrt(xRes + yRes);
	}
	
	public int getDamage() {
		return damage;
	}
	
	public Aircraft checkCollision(List<Aircraft> aircrafts) {
		int index = 0;
		float centeredProjectileX = center[0] + position[0];
		float centeredProjectileY = center[1] + position[1];
		while(index < aircrafts.size()) {
			Aircraft aircraft = aircrafts.get(index);
			if(aircraft instanceof Player && !friendly || !(aircraft instanceof Player) && friendly) {
				float centeredAircraftX = aircraft.getCenterX() + aircraft.getX();
				float centeredAircraftY = aircraft.getCenterY() + aircraft.getY();
				float hitRadius = aircraft.getCenterX() + 20.0f;
				float dist = this.euclideanDist(centeredProjectileX, centeredProjectileY, centeredAircraftX, centeredAircraftY);
				
				if(dist <= hitRadius) {
					
					boolean print = true;
					if(print) {
						System.out.println("dist to target: " + dist + " hit radius: " + hitRadius);
						System.out.println("Is the player: " + (aircraft instanceof Player));
						System.out.println("Enemy Coordinates: " + aircraft.getX() + " " + aircraft.getY());
						System.out.println("Bullet coordinates: " + this.getX() + " " + this.getY());
						System.out.println("Centers:" + centeredProjectileX + " " + centeredProjectileY + " " +  centeredAircraftX + " " + centeredAircraftY);
					}
					return aircraft;
				}
			}
			index ++;
		}
		return null;
	}
	
	
}
