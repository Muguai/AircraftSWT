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
	
	/*	randomDiskSpawn()
	 * 	A spawn scheme that spawns enemies randomly in a disk of specified radius.
	 * 	We have two radiuses and enemies randomly spawn in the area defined as the area
	 *  in between the two radiuses. Their angle (and thus direction) is randomized.
	 */
	
	public void randomDiskSpawn(int enemies, float minRadius, float maxRadius) {
		while(enemies > 0) {
			float r = minRadius + (float)Math.random()*(maxRadius - minRadius);
			float deg = (float)Math.random()*360;
			float xOffset = r*(float)Math.cos(deg);
			float yOffset = r*(float)Math.sin(deg);
			Enemy enemy = new Enemy(display, x+xOffset, y+yOffset, deg);
			dataHandler.addGameObject(enemy);
			enemies --;
		}
	}
}
