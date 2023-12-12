package components;

import org.eclipse.swt.widgets.Display;

import data.DataHandler;

public class EnemySpawner {
	private float x;
	private float y;
	private DataHandler dataHandler;
	private int enemies;
	private Display display;
	
	public EnemySpawner(float x, float y, DataHandler dataHandler, Display display) {
		this.x = x;
		this.y = y;
		this.dataHandler = dataHandler;
		this.display = display;
	}
	
	/*	diskSpawn()
	 * 	A spawn scheme that spawns enemies randomly in a disk of specified radius.
	 *  The area where enemies are spawned is defined as the area between two radiuses.
	 *  @param: innerRadius - Defines the inner radius of the spawning disk.
	 *  @param: outerRadius - Defines the outer radius of the spawning disk. 
	 */
	
	public void diskSpawn(int enemies, float innerRadius, float outerRadius) {
		while(enemies > 0) {
			float radius = innerRadius + (float)Math.random()*(outerRadius - innerRadius);
			float deg = (float)Math.random()*360;
			float xOffset = radius*(float)Math.cos(Math.toRadians(deg));
			float yOffset = radius*(float)Math.sin(Math.toRadians(deg));
			Enemy enemy = new Enemy(display, x+xOffset, y+yOffset, deg);
			dataHandler.addGameObject(enemy);
			enemies --;
		}
	}
}
