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
	
	public void addGameObject(GameObject gameObject) { 
		gameObjects.add(gameObject); 
	}
	
	public void removeGameObject(GameObject gameObject) { gameObjects.remove(gameObject); }
	
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	public Player getPlayer() {
		return player;
	}
}
