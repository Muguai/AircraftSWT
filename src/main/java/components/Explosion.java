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
	private int nextSprite = 0;
	private final int SPRITES = 15;
	private float totalTime = 0;
	private final float TIMER = 0.01f;
	
	public Explosion(Display display, float x, float y) {
		super(x, y);
		this.display = display;
		setNextSpriteImage();
	}
	
	public float getTotalTime() {
		return totalTime;
	}
	
	public void setTotalTime(float deltaTime) {
		totalTime += deltaTime;
	}
	
	public void setNextSpriteImage() {
		// Finds and sets the next image in the sprite sheet
        try {
        	String relPath = IMAGE_PATH_START + String.format("%03d", nextSprite) + IMAGE_PATH_END;
            explosionImage = new Image(display, relPath);
            nextSprite++;
        } catch (Exception error) {
        	System.out.println(error.getMessage());
        }
	}
	
	
	@Override
	public void draw(Canvas canvas) {
		canvas.addPaintListener(e -> {
            GC gc = e.gc;
            
            // If explosion animation has come to the end
            if (nextSprite >= SPRITES) {
            	// remove itself??
            	return;
            }
            
            // Update to next sprite on the TIMER
            if (totalTime >= TIMER) {
            	setNextSpriteImage();
                totalTime = 0;
    		}
            
            int x = (int)(this.position[0] + this.offsets[0] + canvas.getBounds().width/2 - explosionImage.getBounds().width/2);
            int y = (int)(this.position[1] + this.offsets[1] + canvas.getBounds().height/2 - explosionImage.getBounds().height/2);
            gc.drawImage(explosionImage, x, y);
            
		});
	}
}
