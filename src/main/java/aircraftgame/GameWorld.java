package aircraftgame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GameWorld {
	
	private Image mapImage;
	private Canvas canvas;
	private DataHandler dataHandler;

	public GameWorld(Display display, Shell shell, DataHandler dataHandler) {
		try {
			mapImage = new Image(display, "C:\\Users\\NLeven\\OneDrive - ManpowerGroup\\Documents\\GitHub\\AircraftSWT\\src\\main\\java\\aircraftgame\\map.png"); // C:\\Users\\NLeven\\OneDrive - ManpowerGroup\\Documents\\GitHub\\AircraftSWT\\src\\main\\java\\aircraftgame
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.dataHandler = dataHandler;
		
		// Paint up map background
		canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
		
		canvas.setSize(600, 400);
		
        canvas.addPaintListener(e -> {
        	GC gc = e.gc;
        	
        	// Calculate the destination rectangle within the canvas
//            int destX = 0; // X for top left corner
//            int destY = 0; // Y for top left corner
//            int destWidth = canvas.getBounds().width; // portion of image drawn on canvas
//            int destHeight = canvas.getBounds().height;// portion of image drawn on canvas
//
//            // Calculate the source rectangle within the image, applying the offset values
//            int srcX = Math.max(0, offsetX);
//            int srcY = Math.max(0, offsetY);
//            int srcWidth = Math.min(mapImage.getBounds().width - srcX, destWidth);
//            int srcHeight = Math.min(mapImage.getBounds().height - srcY, destHeight);

         // Draw the portion of the image on the canvas
            //gc.drawImage(mapImage, srcX, srcY, srcWidth, srcHeight, destX, destY, destWidth, destHeight);
        	//gc.drawImage(mapImage, dataHandler., offsetY)
        });
       
        canvas.setFocus();
        
        // Setting up event listeners
        canvas.addKeyListener(new ArrowKeyListener(this));
	}
	
	public Canvas getCanvas() { return canvas; }
	
	
}
