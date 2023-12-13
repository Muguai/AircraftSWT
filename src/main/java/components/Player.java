package components;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Player extends Aircraft{
	private boolean radar;
	private Image planeImage;
	private Image hitPlaneImage;
	private boolean listenerActive;
	
	/*	[class constructor] player()
	 * 	This class is used to construct a player object as a child of the Aircraft.
	 * 	
	 */
	
	public Player(Display display, float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		this.speedFactor = 50.0f;
		try {
			String planeImagePath = "src\\main\\java\\resources\\images\\aircrafts\\Aircraft_05.png";
			planeImage = new Image(display, planeImagePath); 
			String hitPlaneImagePath =  "src\\main\\java\\resources\\images\\aircrafts\\Aircraft_05_hit.png"; 
			hitPlaneImage = new Image(display, hitPlaneImagePath);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		listenerActive = false;
	}
	
	
	/*	draw()
	 * 	Sets the actionListener for performing graphics on the Player object.
	 * 	Since the player graphics should rotate around its own axis we have to make sure
	 * 	that the graphics is centered in the origo of the object's transform.
	 */
	
	public void draw(Canvas canvas) {
		if(listenerActive)
			return;
		listenerActive = true;
		
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            
    		int x = canvas.getBounds().width/2;
    		int y = canvas.getBounds().height/2;
    		
            // 1. Get the transform and translate it to (x,y):
            Transform transform = new Transform ( gc.getDevice () );
            transform.translate(x, y);
            
            // 2. After this translation, do rotation:
            transform.rotate((degree+90));
            
            // 3. Draw out the object centered in the transform's origo:
            gc.setTransform ( transform );
            gc.drawImage(planeImage, -planeImage.getBounds().width/2, -planeImage.getBounds().height/2);
            
            // 4. Set the new transform as the identity transform and dispose the old one:
            gc.setTransform(null);
            transform.dispose();
        });
	}
	
	/*	getXOffset()
	 * 	A getter for the offset that other gameObjects (other than player) should use.
	 *  Since every gameObject is offsetted by the position of the player we simply return it
	 *  here. Another gameObject will then use this offset to render themself on the map. 
	 */
	
	public float getXOffset() {
		return -this.position[0];
	}
	
	/*	getYOffset()
	 * 	Similar to getXOffset() but for Y.
	 *  Simply returns the Y position of the player object which is then used as an offset.
	 */
	
	public float getYOffset() {
		return -this.position[1];
	}
}
