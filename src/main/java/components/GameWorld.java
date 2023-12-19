package components;
import java.math.*;
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
			String bigMap = "src\\main\\java\\resources\\images\\mapBig.png"; //"src\\main\\java\\resources\\images\\mapBig.png";
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
	        offsetX =  canvas.getBounds().width/2  -mapImage.getBounds().width/2 - (int)dataHandler.getPlayer().getX();
	        offsetY =  canvas.getBounds().height/2 -mapImage.getBounds().height/2 - (int)dataHandler.getPlayer().getY();
	         
	        // 5. Render the image of the map onto the canvas, with offsets:
	        int srcX = Math.max(0, offsetX);
	        int srcY = Math.max(0, offsetY);
	        int srcWidth = Math.min(mapImage.getBounds().width - srcX, destWidth);
	        int srcHeight = Math.min(mapImage.getBounds().height - srcY, destHeight);
	
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
		List<Projectile> newAIBullets = new ArrayList<Projectile>();
		
		
		// 1. Iterate over every gameObject:
		for(GameObject gameObject : dataHandler.getGameObjects()) {
			
			// 2. If gameObject is also a movable object, move the object:
			if(gameObject instanceof MovableObject) {
				((MovableObject) gameObject).moveObject(deltaTime);
			}
			
			// 3. If the game object is not of the player instance, update offset based on player position:
			if(!(gameObject instanceof Player)) {
				gameObject.setOffsets(dataHandler.getPlayer().getXOffset(), dataHandler.getPlayer().getYOffset());
				
				// 3.1. Also, if the non-player gameObject is an Aircraft, shoot at nearby enemies:
				if(gameObject instanceof Aircraft) {
					Projectile bullet = ((Aircraft) gameObject).getGunnerAi().detectAndShoot(display, dataHandler, deltaTime);
					if(bullet != null) {
						newAIBullets.add(bullet);
					}
				}
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
				((Explosion) gameObject).setTotalTime(deltaTime);
			}
			
			float yFloor =  mapImage.getBounds().height/2  - canvas.getBounds().height/2;
			float yCeil =   -mapImage.getBounds().height/2 + canvas.getBounds().height/2;
			float xFloor =  -mapImage.getBounds().width/2  + canvas.getBounds().width/2;
			float xCeil =   mapImage.getBounds().width/2   - canvas.getBounds().width/2;
			
			if(gameObject instanceof Aircraft) {
				if (gameObject.getY() >= yFloor) {
					((Aircraft)gameObject).setDegree(270);		
				}
				
				if (gameObject.getY() <= yCeil) {
					((Aircraft)gameObject).setDegree(90);
				}
				
				if (gameObject.getX() <= xFloor) {
					((Aircraft)gameObject).setDegree(0);
				}
				
				if (gameObject.getX() >= xCeil) {
					((Aircraft)gameObject).setDegree(180);
				}
			}
			
			// 6. Draw gameObject onto the canvas:
			gameObject.draw(canvas);
		}
		
		// 7. Iterate over bullets that hit a target and remove them from the game:
		for(Projectile projectile : bulletsHit) {
			dataHandler.addGameObject(new Explosion(display, projectile.getX(), projectile.getY(), true));
			projectile.removePaintListener(canvas);
			dataHandler.removeGameObject(projectile);
		}
		
		// 8. Iterate over destroyed airplanes and detach them from the actionListener and remove them from dataHandler:
		int index = 0;
		List<Aircraft> aircrafts = dataHandler.getAircrafts();
		while(index < aircrafts.size()) {
			Aircraft aircraft = aircrafts.get(index);
			if (aircraft.health <= 0) {
				float explosionX = aircraft.getX() + aircraft.getCenterX();
				float explosionY = aircraft.getY() + aircraft.getCenterY();
				if(aircraft instanceof Player) {
					explosionX += dataHandler.getPlayer().getDisplay().getBounds().width/2;
					explosionY += dataHandler.getPlayer().getDisplay().getBounds().height/2;
				}
				
				dataHandler.addGameObject(new Explosion(display, explosionX, explosionY));
				aircraft.removePaintListener(canvas);
				dataHandler.removeGameObject(aircraft);
			}
			else {
				index++;
			}
		}
		
		// 9. Iterate over new bullets and add them to the dataHandler:
		for(Projectile bullet : newAIBullets) {
			dataHandler.addGameObject(bullet);
		}

		// 10. 
		if(dataHandler.getPlayer().radarActive()) {
			
			dataHandler.getPlayer().getRadar().drawRadar(canvas, deltaTime);
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
