package components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Canvas;

public abstract class Projectile extends MovableObject{
	private boolean friendly;
	private int damage;
	
	
	/*	[class constructor] Projectile
	 * 	A class that defines projectiles and their collision detction with flights.
	 *  The projectile class has a friendly-flag and a damage label.
	 *  Using these attributes, checkCollision() can be called upon in the update()
	 *  function in gameWorld, and we can see if projectiles hit their target.
	 */
	
	Projectile(float x, float y, float degree, boolean friendly, int damage){
		super(x, y, degree);
		this.friendly = friendly;
		this.damage = damage;
		this.setCenter(0, 0);
	}
	
	/*	euclideanDist()
	 * 	A function that calculates the euclidean distance between two points.
	 *  Basically we calculate the x-residual and the y-residual.
	 * 	The shortest route can then be calculated as: sqrt(xRes^2 + yRes^2).
	 */
	
	public float euclideanDist(float x1, float y1, float x2, float y2) {
		float xRes = (float)(Math.pow(x1 - x2, 2));
		float yRes = (float)(Math.pow(y1 - y2, 2));
		return (float)Math.sqrt(xRes + yRes);
	}
	
	public int getDamage() {
		return damage;
	}
	
	/*	checkCollision()
	 * 	A projectile object will inherit the checkCollision method defined in this class.
	 * 	This method will iterate over every instance of an aircraft and check the euclidean distance.
	 *  If that distance is short enough, we are within a hitbox radius, and a hit is registered.
	 *  @Param: aircrafts - A list of aircrafts currently in the air.
	 *  @Return: aircraft/null - If a hit is registred, the aircraft object is returned, else null is returned.
	 */
	
	public Aircraft checkCollision(List<Aircraft> aircrafts) {
		// 1. Get the projectile's center:
		float centeredProjectileX = center[0] + position[0];
		float centeredProjectileY = center[1] + position[1];
		
		// 2. Iterate over every aircraft (Using a while loop so we can dynamically update the list):
		int index = 0;
		while(index < aircrafts.size()) {
			Aircraft aircraft = aircrafts.get(index);
			
			// 3. Check if the projectile can hit the aircraft (Friendly bullets hurt enemies, unfriendly hurt the player):
			if(aircraft.friendly && !friendly || !(aircraft.friendly) && friendly) {
				
				// 4. Calculate the distance to the target:
				float centeredAircraftX = aircraft.getCenterX() + aircraft.getX();
				float centeredAircraftY = aircraft.getCenterY() + aircraft.getY();
				
				// 5. Account for screen offset for a player:
				if(aircraft instanceof Player) {
					centeredAircraftX += ((Player) aircraft).getDisplay().getBounds().width/2;
					centeredAircraftY += ((Player) aircraft).getDisplay().getBounds().height/2;
				}
				
				float hitRadius = aircraft.getCenterX() + 20.0f;
				float dist = this.euclideanDist(centeredProjectileX, centeredProjectileY, centeredAircraftX, centeredAircraftY);
				
				// 6. Check if the distance is within the target radius, if so, return the hit target:
				if(dist <= hitRadius) {
					boolean print = true;
					return aircraft;
				}
			}
			// 7. If no target was hit, go to the next aircraft:
			index ++;
		}
		
		// 8. If we have iterated over every aircraft with no hits we are guaranteed to get here, and we return null: 
		return null;
	}
	
	
}
