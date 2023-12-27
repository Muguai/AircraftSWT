package utils;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

/*
 * Utility class for managing images
 */
public class ImageManager {

	
	public static Image resizeImage(Image originalImage, int newWidth, int newHeight) {
        ImageData imageData = originalImage.getImageData();
        ImageData scaledImageData = imageData.scaledTo(newWidth, newHeight);
        return new Image(Display.getDefault(), scaledImageData);
    }
}
