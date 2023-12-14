package components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import data.DataHandler;
import eventListeners.ArrowKeyListener;
import eventListeners.SpaceKeyListener;

public class GameWorld {
	
	private Display display;
	private Image mapImage;
	private Canvas canvas;
	private DataHandler dataHandler;
	private int offsetX;
	private int offsetY;
	private boolean isRunning;
	
	/*	[class constructor]	GameWorld
	 * 	The class that represents an ongoing game for as long as the boolean isRunning is true.
	 *  GameWorld draws up the world, sets keyListeners for the player and runs the update() function
	 *  for every frame. The update() function in turn is an iteration over every gameObject in the
	 *  world. For each such object, movement, collision detection, rendering etc is called upon
	 *  within the update() function.
	 *  @Param:	
	 *  @Param:
	 *  @Param:	dataHandler - A dataHandler object where every gameObject is stored.
	 */
	
	public GameWorld(Display display, Shell shell, DataHandler dataHandler) {
		
		this.display = display;
		
		// 1. Initiate data in the gameworld:
		this.dataHandler = dataHandler;
		isRunning = true;
		try {
			String bigMap = "src\\main\\java\\resources\\images\\mapBig.png";
			String normalMap = "src\\main\\java\\resources\\images\\map.png";
			mapImage = new Image(display, bigMap); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
        
		// 2. Create a new canvas to render on:
 		canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
 		canvas.setSize(shell.getSize().x, shell.getSize().y);
	    canvas.addPaintListener(e -> {
	     	GC gc = e.gc;
	     	
	     	// 3. Calculate the destination rectangle within the canvas:
	        int destX = 0; 								
	        int destY = 0; 								
	        int destWidth = canvas.getBounds().width; 	
	        int destHeight = canvas.getBounds().height;	
	 
	        // 4. Calculate a map offset based on player position:
	        offsetX =  -canvas.getBounds().width/2 - (int)dataHandler.getPlayer().getX();
	        offsetY =  -canvas.getBounds().height/2 - (int)dataHandler.getPlayer().getY();
	         
	        // 5. Render the image of the map onto the canvas, with offsets:
	        int srcX = Math.max(0, offsetX);
	        int srcY = Math.max(0, offsetY);
	        int srcWidth = Math.min(mapImage.getBounds().width - srcX, destWidth);
	        int srcHeight = Math.min(mapImage.getBounds().height - srcY, destHeight);
	
	        // 6. Draw the portion of the image on the canvas:
	        gc.drawImage(mapImage, offsetX, offsetY);
	     });
	    
	     canvas.setFocus();
	     
	     // 7. Setting up event listeners (For player turning, etc)
	     canvas.addKeyListener(new ArrowKeyListener(this));
	     canvas.addKeyListener(new SpaceKeyListener(this));
        
	}
	
	public Display getDisplay() { return display; }
	public Canvas getCanvas() { return canvas; }
	public DataHandler getDataHandler() { return dataHandler; }
	
	/* 	update()
	 * 	update() is called in the main loop for each frame and updates the state of the game.
	 * 	Specifically, the method iterates over every game object: gameObject and applies 
	 * 	relevant functionality to it (Move, Draw, etc), for each timestep of the game.
	 */
	
	public void update(float deltaTime) {
		List<Projectile> bulletsHit = new ArrayList<Projectile>();
		
		// 1. Iterate over every gameObject:
		for(GameObject gameObject : dataHandler.getGameObjects()) {
			
			// 2. If gameObject is also a movable object, move the object:
			if(gameObject instanceof MovableObject) {
				((MovableObject) gameObject).moveObject(deltaTime);
			}
			
			// 3. If the game object is not of the player instance, update offset based on player position:
			if(!(gameObject instanceof Player)) {
				gameObject.setOffsets(dataHandler.getPlayer().getXOffset(), dataHandler.getPlayer().getYOffset());
			}
			
			// 4. If the game object is a projectile, see if it hits something:
			if(gameObject instanceof Projectile) {
				Aircraft target = ((Projectile) gameObject).checkCollision(dataHandler.getAircrafts());
				if(target != null) {
					Projectile bullet = (Projectile)gameObject;
					bulletsHit.add(bullet);
					target.takeDamage(bullet.getDamage());
				}
			}
			
			// 5. Update the explosion sprite's time duration
			if(gameObject instanceof Explosion) {
				System.out.println("Finns en explosion");
				((Explosion) gameObject).setTotalTime(deltaTime);
			}
			
			// 6. Draw gameObject onto the canvas:
			gameObject.draw(canvas);
		}
		
		// 6. Iterate over bullets that hit a target and remove them from the game:
		for(Projectile projectile : bulletsHit) {
			projectile.removePaintListener(canvas);
			dataHandler.removeGameObject(projectile);
		}
		
		// 7. Iterate over destroyed airplanes and detach them from the actionListener and remove them from dataHandler:
		int index = 0;
		List<Aircraft> aircrafts = dataHandler.getAircrafts();
		while(index < aircrafts.size()) {
			Aircraft aircraft = aircrafts.get(index);
			if (aircraft.health <= 0) {
				dataHandler.addGameObject(new Explosion(display, aircraft.getCenterX(), aircraft.getCenterY()));
				aircraft.removePaintListener(canvas);
				dataHandler.removeGameObject(aircraft);
			}
			else {
				index++;
			}
		}
		
		canvas.redraw();
	}
	
	/*	runs()
	 * 	Returns the state of the game.
	 */
	
	public boolean runs() {
		return isRunning;
	}
	
	/* exit()
	 * Sets the state of the game to be false.
	 */
	public void exit() {
		isRunning = false;
	}
	
	
	
	
}
