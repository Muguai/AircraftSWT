package components;

import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

import data.DataHandler;

public class EnemySpawner {
	private float x;
	private float y;
	private DataHandler dataHandler;
	private int enemies;
	private Display display;
	
	/*	[class constructor] EnemySpawner
	 * 	Sets the location for an EnemySpawner object that can then use different spawning schemes.
	 *  @Param: float x - The x position of the EnemySpawner's center
	 *  @Param: float y - The y position of the EnemySpawner's center
	 *  @Param: DataHandler dataHandler - The dataHandler for which to append objects to.
	 *  @Param: Display display - The display object needed to initiate enemies.
	 */
	
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
		boolean kim = false; // hehe
		while(enemies > 0) {
			float radius = innerRadius + (float)Math.random()*(outerRadius - innerRadius);
			float deg = (float)Math.random()*360;
			float xOffset = radius*(float)Math.cos(Math.toRadians(deg));
			float yOffset = radius*(float)Math.sin(Math.toRadians(deg));
			Enemy enemy = new Enemy(display, x+xOffset, y+yOffset, deg, kim);
			dataHandler.addGameObject(enemy);
			enemies --;
			kim = !kim; // hoho
		}
	}
	
	/*	vFormationSpawn()
	 * 	A spawning scheme that spawns a V-formation.
	 * 	The Leader of the V-formation is spawned in on coordinates (x,y).
	 *  Other enemy objects are then filled onto left-V side and right V side until emptied.
	 *  @Param: enemies - How many enemy objects are in the V-formation.
	 *  @Param: spacing - The spacing between enemy objects.
	 *  @Param: degree - The degree of the direction that they are flying towards.
	 *  @Param: vDegree - The degree from the leader to the left and right side resp.
	 */
	
	public void vFormationSpawn(int enemies, float spacing, float degree, float vDegree) {
		// 1. Check that the amount of enemies is non-zero:
		if (enemies == 0) {
			return;
		}
		
		// 2. Create a leader object. Iterations = 3 will set some special spacing:
		int iterations = 3;
		Enemy leader = new Enemy(display, x, y, degree, true);
		dataHandler.addGameObject(leader);
		enemies --;
		
		// 3. Calculate the degree of the two 
		float leftDeg = (float)Math.toRadians(degree + vDegree);
		float rightDeg = (float)Math.toRadians(degree - vDegree);
		
		// 4. Fill enemies:
		while(enemies > 0) {
			Enemy enemy;
			
			// 5. Alternate between left hand side and right hand side:
			if(iterations % 2 == 0) {
				float xEnemy = x - (spacing*(iterations-1))*(float)Math.cos(leftDeg);
				float yEnemy = y - (spacing*(iterations-1))*(float)Math.sin(leftDeg);
				enemy = new Enemy(display, xEnemy, yEnemy, degree, false);
			}
			else {
				float xEnemy = x - (spacing*(iterations))*(float)Math.cos(rightDeg);
				float yEnemy = y - (spacing*(iterations))*(float)Math.sin(rightDeg);
				enemy = new Enemy(display, xEnemy, yEnemy, degree, false);
			}
			
			// 6. Append the new enemy to the dataHandler.
			dataHandler.addGameObject(enemy);
			enemies --;
			iterations ++;
		}
	}
}
