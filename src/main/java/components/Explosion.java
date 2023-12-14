package components;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Explosion extends GameObject {
	
	private Display display;
	private String IMAGE_PATH_START = "src/main/java/resources/images/explosion/tile";
	private String IMAGE_PATH_END = ".png";
	private Image explosionImage;
	private int spriteNumber = 0;
	private int sprites = 15;
	
	public Explosion(Display display, float x, float y) {
		super(x, y);
		this.display = display;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.addPaintListener(e -> {
            GC gc = e.gc;
            
            // If explosion animation has come to the end
            if (spriteNumber >= sprites) {
            	// remove itself??
            	return;
            }
            
            // Find the image then draw it on the specified position
            try {
            	String relPath = IMAGE_PATH_START + String.format("%03d", spriteNumber) + IMAGE_PATH_END;
                explosionImage = new Image(display, relPath);
                
                int x = (int)(this.position[0] + this.offsets[0] + canvas.getBounds().width/2 - explosionImage.getBounds().width/2);
                int y = (int)(this.position[1] + this.offsets[1] + canvas.getBounds().height/2 - explosionImage.getBounds().height/2);
                gc.drawImage(explosionImage, x, y);
            } catch (Exception error) {
            	System.out.println(error.getMessage());
            }
            
            spriteNumber++;
		});
	}
}
