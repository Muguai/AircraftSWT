package aircraftgame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GameWorld {
	
	Image mapImage;
	Canvas canvas;
	DataHandler dataHandler;

	
	public GameWorld(Display display, Shell shell, DataHandler dataHandler) {
		this.dataHandler = dataHandler;
		try {
			mapImage = new Image(display, "C:\\Users\\ADanel\\Desktop\\flygplan3\\AircraftSWT\\src\\main\\java\\aircraftgame\\test.jpg");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            //gc.drawImage(mapImage, 0, 0);
        });
        
	}
	
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
			gameObject.draw(canvas);
		}
		canvas.redraw();
	}
	
	
}
