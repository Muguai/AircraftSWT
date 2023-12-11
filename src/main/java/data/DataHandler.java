package data;
import java.util.ArrayList;
import java.util.List;

import components.GameObject;
import components.Player;

public class DataHandler {
	
	private List<GameObject> gameObjects;
	private final Player player;
	
	public DataHandler(Player player) {
		gameObjects = new ArrayList<>();
		this.player = player;
		gameObjects.add(player);
	}
	
	/*	addGameObject()
	 *  Takes in a gameObject and adds it to the list of gameObjects
	 */
	
	public void addGameObject(GameObject gameObject) { gameObjects.add(gameObject); }
	
	/*	removeGameObject()
	 * 	Takes in a gameObject and removes it from the internal list of game objects
	 */
	
	public void removeGameObject(GameObject gameObject) { gameObjects.remove(gameObject); }
	
	/*	getGameObjects()
	 * 	Returns the internal list of game objects
	 */
	
	public List<GameObject> getGameObjects() { return gameObjects; }
	
	/*	getPlayer()
	 * 	Returns the player object internally stored in the datahandler
	 * 
	 */
	
	public Player getPlayer() {
		return player;
	}
}
