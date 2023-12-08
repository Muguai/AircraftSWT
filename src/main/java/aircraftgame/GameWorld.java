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
