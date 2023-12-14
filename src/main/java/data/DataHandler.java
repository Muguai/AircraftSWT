package data;
import java.util.ArrayList;
import java.util.List;

import components.Aircraft;
import components.GameObject;
import components.Player;

public class DataHandler {
	
	private List<GameObject> gameObjects;
	private List<Aircraft> aircrafts;
	private final Player player;
	
	public DataHandler(Player player) {
		gameObjects = new ArrayList<>();
		aircrafts = new ArrayList<>();
		this.player = player;
		gameObjects.add(player);
	}
	
	/*	addGameObject()
	 *  Takes in a gameObject and adds it to the list of gameObjects.
	 *  If the instance happens to be an aircraft, add it to a list of airplanes.
	 *  (This can then be used for collision detection with bullets)
	 */
	
	public void addGameObject(GameObject gameObject) { 
		gameObjects.add(gameObject); 
		if (gameObject instanceof Aircraft) {
			aircrafts.add((Aircraft)gameObject);
		}
		
	}
	
	/*	removeGameObject()
	 * 	Takes in a gameObject and removes it from the internal list of game objects
	 */
	
	public void removeGameObject(GameObject gameObject) { 
		gameObjects.remove(gameObject); 
		if (gameObject instanceof Aircraft)
			aircrafts.remove(gameObject);
	}
	
	/*	getGameObjects()
	 * 	Returns the internal list of game objects
	 */
	
	public List<GameObject> getGameObjects() { return gameObjects; }
	
	/*	getAircrafts()
	 * 	Returns the internal list of aircrafts
	 *  (Having two seperated lists makes it so we only have to iterate
	 *   airplanes when we are doing collision detection with bullets.)
	 */
	public List<Aircraft> getAircrafts(){
		return aircrafts;
	}
	
	/*	getPlayer()
	 * 	Returns the player object internally stored in the datahandler
	 * 
	 */
	
	public Player getPlayer() {
		return player;
	}
}
