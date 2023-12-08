package aircraftgame;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
	
	private List<GameObject> gameObjects;
	
	public DataHandler() {
		gameObjects = new ArrayList<>();
	}
	
	public void addGameObject(GameObject gameObject) { gameObjects.add(gameObject); }
	
	public void removeGameObject(GameObject gameObject) { gameObjects.remove(gameObject); }
	
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
}
