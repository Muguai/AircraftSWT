package components;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import utils.ImageManager;
import utils.SoundManager;

public class Explosion extends GameObject {
	
	private Display display;
	private String IMAGE_PATH_START = "src/main/java/resources/images/explosion/tile";
	private String IMAGE_PATH_END = ".png";
	private Image explosionImage;
	private int nextSprite = 0;
	private final int SPRITES = 15;
	private float totalTime = 0;
	private final float TIMER = 0.04f;
	private boolean isSmall = false;
	private final int SMALL_IMAGE_SIZE = 100;
	private SoundManager soundManager;
	
	public Explosion(Display display, float x, float y) {
		super(x, y);
		this.display = display;
		setNextSpriteImage();
		soundManager = new SoundManager();
		soundManager.playRandomExplosion();
	}
	
	public Explosion(Display display, float x, float y, boolean isSmall) {
		super(x,y);
		this.display = display;
		setNextSpriteImage();
		this.isSmall = isSmall;
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
        	// if its a small explosion resize the image to a smaller size
        	Image image = new Image(display, relPath);;
        	if (isSmall) {
        		image = ImageManager.resizeImage(image, SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE);
        	}
            explosionImage = image;
            nextSprite++;
        } catch (Exception error) {
        	System.out.println(error.getMessage());
        }
	}
	
	
	@Override
	public void draw(Canvas canvas) {
        if (listenerActive)
            return;
        listenerActive = true;
        
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
            
            int x = (int)(this.position[0] + this.offsets[0] - explosionImage.getBounds().width/2);
            int y = (int)(this.position[1] + this.offsets[1] - explosionImage.getBounds().height/2);
            gc.drawImage(explosionImage, x, y);
            
		});
	}
}
