package aircraftgame;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GameWorld {
	
	Image mapImage;

	public GameWorld(Display display, Shell shell) {
		try {
			mapImage = new Image(display, "");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
}
