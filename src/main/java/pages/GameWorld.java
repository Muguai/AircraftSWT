package pages;
import java.math.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import components.Aircraft;
import components.Explosion;
import components.FighterPlane;
import components.GameObject;
import components.MovableObject;
import components.Player;
import components.Projectile;
import components.HeatseekingMissile;
import data.DataHandler;
import eventListeners.EscapeKeyListener;
import eventListeners.GameKeyListener;
import utils.ImageManager;

public class GameWorld extends Page {
	
	private Image mapImage;
	private int offsetX;
	private int offsetY;
	private KeyListener keyListener;
	
	/*	[class constructor]	GameWorld
	 *  A GameWorld object is used as stage 2 / 3 in the main loop.
	 * 	The class that represents an ongoing game for as long as the boolean isRunning is true.
	 *  GameWorld draws up the world, sets keyListeners for the player and runs the update() function
	 *  for every frame. The update() function in turn is an iteration over every gameObject in the
	 *  world. For each such object, movement, collision detection, rendering etc is called upon
	 *  within the update() function.
	 *  @Param:	display - The display object of the application.
	 *  @Param: shell - The shell object of the application.
	 *  @Param:	dataHandler - A dataHandler object where every gameObject is stored.
	 *  @Param: canvas - A Canvas object that is all shared between the classes.
	 */
	
	public GameWorld(Display display, Shell shell, DataHandler dataHandler, Canvas canvas) {
		super(display, shell, dataHandler, canvas);
		
		setMapImage("src\\main\\java\\resources\\images\\mapBig.png");
		this.canvas = canvas;
		
		// Setting up paint listener 
		setMapImagePaintListener();
        
		
	     // 7. Setting up event listeners (For player turning, etc)
		 keyListener = new GameKeyListener(this);
	     canvas.addKeyListener(keyListener);
	     canvas.addKeyListener(new EscapeKeyListener(this));
	}

	/* setMapImage()
	 * Attempts to retrieve the map image. 
	 */
	public void setMapImage(String path) {
		try {
			mapImage = new Image(display, path); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * 
	 */
	public void setMapImagePaintListener() {
		// 1. Create a new canvas to render on:
	    canvas.addPaintListener(e -> {
	     	GC gc = e.gc;
	     	
	     	// 2. Calculate the destination rectangle within the canvas:
	        int destX = 0; 								
	        int destY = 0; 								
	        int destWidth = canvas.getBounds().width; 	
	        int destHeight = canvas.getBounds().height;	
	 
	        // 3. Calculate a map offset based on player position:
	        offsetX =  canvas.getBounds().width/2  -mapImage.getBounds().width/2 - (int)dataHandler.getPlayer().getX();
	        offsetY =  canvas.getBounds().height/2 -mapImage.getBounds().height/2 - (int)dataHandler.getPlayer().getY();
	         
	        // 4. Render the image of the map onto the canvas, with offsets:
	        int srcX = Math.max(0, offsetX);
	        int srcY = Math.max(0, offsetY);
	        int srcWidth = Math.min(mapImage.getBounds().width - srcX, destWidth);
	        int srcHeight = Math.min(mapImage.getBounds().height - srcY, destHeight);
	
	        gc.drawImage(mapImage, offsetX, offsetY);
	     });
	    
	     canvas.setFocus();
	}
	
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
				
				// 4.1 If the projectile is a heatseeking missile, run a detection scan:
				if(gameObject instanceof HeatseekingMissile) {
					((HeatseekingMissile)gameObject).heatseekAdjustDirection(dataHandler.getAircrafts());
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
			if (aircraft.getHealth() <= 0) {
				float explosionX = aircraft.getX() + aircraft.getCenterX();
				float explosionY = aircraft.getY() + aircraft.getCenterY();
				if(aircraft instanceof Player) {
					explosionX += dataHandler.getPlayer().getDisplay().getBounds().width/2;
					explosionY += dataHandler.getPlayer().getDisplay().getBounds().height/2;
				}
				
				dataHandler.addGameObject(new Explosion(display, explosionX, explosionY));
				aircraft.removePaintListener(canvas);
				dataHandler.removeGameObject(aircraft);
				if(!aircraft.isFriendly())
					dataHandler.incrementKillCount();
			}
			else {
				index++;
			}
		}
		
		// 9. Iterate over new bullets and add them to the dataHandler:
		for(Projectile bullet : newAIBullets) {
			dataHandler.addGameObject(bullet);
		}

		// 10. Feed in radar data if it is activated:
		if(dataHandler.getPlayer().radarActive()) {
			dataHandler.getRadar().setDeltaTime(deltaTime);
			dataHandler.getRadar().drawRadar(canvas);
		}
		
		// 11. Iterate over projectiles reaching the end of their life time:
		int indexLife = 0;
		while(indexLife < dataHandler.getGameObjects().size()) {
			GameObject gameObject = dataHandler.getGameObjects().get(indexLife);
			if(gameObject instanceof Projectile && ((Projectile) gameObject).endOfLife(deltaTime, canvas)) {
				dataHandler.removeGameObject(gameObject);
			}
			else {
				indexLife ++;
			}
		}
		
		canvas.redraw();
	}

	@Override
	public void exit() {
		isRunning = false;
		canvas.removeKeyListener(keyListener);
	}
	
}
