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
	private int offsetX = 30;
	private int offsetY = -50;

	public GameWorld(Display display, Shell shell) {
		try {
			mapImage = new Image(display, "C:\\Users\\NLeven\\OneDrive - ManpowerGroup\\Documents\\GitHub\\AircraftSWT\\src\\main\\java\\aircraftgame\\test.jpg");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// Paint up map background
		canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
		
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            gc.drawImage(mapImage, offsetX, offsetY);
        });
        
        canvas.setFocus();
        
        // Setting up event listeners
        canvas.addKeyListener(new ArrowKeyListener(this));
	}
	
	public Canvas getCanvas() { return canvas; }
	public int getOffsetX() { return offsetX; }
	public int getOffsetY() { return offsetY; }
	public void setOffsetX(int offsetX) { this.offsetX = offsetX; }
	public void setOffsetY(int offsetY) { this.offsetY = offsetY; }
	
	
}
