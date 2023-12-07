package aircraftgame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GameWorld {
	
	Image mapImage;
	Canvas canvas;

	public GameWorld(Display display, Shell shell) {
		try {
			mapImage = new Image(display, "C:\\Users\\NLeven\\OneDrive - ManpowerGroup\\Documents\\GitHub\\AircraftSWT\\src\\main\\java\\aircraftgame\\pictest.jpg");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
		
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            gc.drawImage(mapImage, 0, 0);
        });
        //canvas.addKeyListener();
	}
	
	
}
