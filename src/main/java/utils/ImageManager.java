package utils;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/*
 * Utility class for managing images
 */
public class ImageManager {

	
	public static Image findImage(Display display, String path) {
		Image image = null;
		try {
			image = new Image(display, path); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return image;
	}
}
