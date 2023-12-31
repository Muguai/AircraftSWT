package data;
import java.util.ArrayList;
import java.util.List;

import components.Aircraft;
import components.GameObject;
import components.Player;
import components.Radar;

public class DataHandler {
	
	private List<GameObject> gameObjects;
	private List<Aircraft> aircrafts;
	private final Player player;
	private Radar radar;
	private int enemiesKilled;
	private int enemiesKillToWin;
	
	
	/*	[Class Constructor] DataHandler()
	 * 	The DataHandler is a class that keeps track of the GameObjects in the world
	 *  as well as other misc. data, such as how many enemies have been killed, etc.
	 *  A shared DataHandler object can then be used between the different stages of
	 *  the game, and the different objects that need to know some kind of data.
	 */
	
	public DataHandler(Player player) {
		gameObjects = new ArrayList<>();
		aircrafts = new ArrayList<>();
		this.player = player;
		addGameObject(player);
		this.enemiesKilled = 0;
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
	
	/*	incrementKillCount()
	 * 	Called upon to increment the internal counter of enemies killed 
	 *  (Later used in the Game Over Screen)
	 */
	
	public void incrementKillCount() {
		this.enemiesKilled ++;
	}
	
	/*	getKills()
	 * 	A getter that returns enemies killed (To the Game Over Screen).
	 */
	
	public int getKills() {
		return this.enemiesKilled;
	}
	
	/*	setRadar()
	 * 	Sets the radar object in the DataHandler class.
	 */
	public void setRadar(Radar radar) {
		this.radar = radar;
	}
	
	/*	getRadar()
	 * 	Returns said radar.
	 */
	
	public Radar getRadar() {
		return radar;
	}
	
	/*	setEnemiesKillToWin
	 * 	Sets how many enemies are to be the limit before we win the game.
	 */
	public void setEnemiesKillToWin(int enemies) {
		this.enemiesKillToWin = enemies;
	}
	
	/*  getEnemiesKillToWin()
	 *  Returns the kill limit to the GameLoop for checking.
	 */
	public int getEnemiesKillToWin() {
		return this.enemiesKillToWin;
	}
}
