package components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import data.DataHandler;
import eventListeners.ArrowKeyListener;

public class GameWorld {
	
	private Image mapImage;
	private Canvas canvas;
	private DataHandler dataHandler;
	private int offsetX;
	private int offsetY;

	

	public GameWorld(Display display, Shell shell, DataHandler dataHandler) {
		this.dataHandler = dataHandler;
		try {
			String relPath = "src\\main\\java\\aircraftgame\\map.png";
			mapImage = new Image(display, relPath); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Avsluta programmet
		}
		
        
     // Paint up map background
     		canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
     		
     		canvas.setSize(600, 400);
     		
             canvas.addPaintListener(e -> {
             	GC gc = e.gc;
             	
             	// Calculate the destination rectangle within the canvas
                 int destX = 0; // X for top left corner
                 int destY = 0; // Y for top left corner
                 int destWidth = canvas.getBounds().width; // portion of image drawn on canvas
                 int destHeight = canvas.getBounds().height;// portion of image drawn on canvas
     
                 // Calculate the source rectangle within the image, applying the offset values
                 offsetX =  -canvas.getBounds().width/2 - (int)dataHandler.getPlayer().getX();
                 offsetY =  -canvas.getBounds().height/2 - (int)dataHandler.getPlayer().getY();
                 
                 
                 int srcX = Math.max(0, offsetX);
                 int srcY = Math.max(0, offsetY);
                 int srcWidth = Math.min(mapImage.getBounds().width - srcX, destWidth);
                 int srcHeight = Math.min(mapImage.getBounds().height - srcY, destHeight);

              // Draw the portion of the image on the canvas
              //   gc.drawImage(mapImage, srcX, srcY, srcWidth, srcHeight, destX, destY, destWidth, destHeight);
             	 gc.drawImage(mapImage, offsetX, offsetY);
             });
            
             canvas.setFocus();
             
             // Setting up event listeners
             canvas.addKeyListener(new ArrowKeyListener(this));

        
	}
	
	public Canvas getCanvas() { return canvas; }
	public DataHandler getDataHandler() { return dataHandler; }
	
	/* 	update()
	 * 	update() is called in the main loop for each frame and updates the state of the game.
	 * 	Specifically, the method iterates over every game object: gameObject.
	 *  1. If gameObject is also a movable object, move the object: gameObject.moveObject()
	 *  2. Draw gameObject onto the canvas: gameObject().draw
	 */
	
	public void update(float deltaTime) {
		for(GameObject gameObject : dataHandler.getGameObjects()) {
			if(gameObject instanceof MovableObject) {
				((MovableObject) gameObject).moveObject(deltaTime);
			}
			if(!(gameObject instanceof Player)) {
				gameObject.setOffsets(dataHandler.getPlayer().getXOffset(), dataHandler.getPlayer().getYOffset());
			}
			gameObject.draw(canvas);
		}
		canvas.redraw();
	}
	
	
	
	
}
