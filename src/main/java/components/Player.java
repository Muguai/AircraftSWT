package components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class Player extends Aircraft{
	private Radar radar;
	private boolean radarActive;
	private Image planeImage;
	private Image hitPlaneImage;
	private Display display;
	
	/*	[class constructor] player()
	 * 	This class is used to construct a player object as a child of the Aircraft.
	 * 	The player object is a special kind of object and centered in the middle of the screen.
	 *  Like with other objects the position of the playerObject is updated for each timestep,
	 *  but since the player object is centered on the screen, the position is used as an offset
	 *  instead to affect other gameObjects.
	 * 	@Param: display - The display object.
	 *  @Param: xPosition - The initial xPosition.
	 *  @Param: yPosition - The initial yPosition.
	 *  @Param: degree - The initial degree for the direction of the player.
	 */
	
	public Player(Display display, float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		this.speedFactor = 50.0f;
		this.friendly = true;
		this.display = display;
		this.radarActive = false;
		this.radar = radar;
		this.health = 1000;
		
		try {
			String planeImagePath = "src\\main\\java\\resources\\images\\aircrafts\\aircraft_06.png"; // "src\\main\\java\\resources\\images\\aircrafts\\Aircraft_05.png";
			planeImage = new Image(display, planeImagePath); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.setCenter(planeImage.getBounds().width/2, planeImage.getBounds().height/2);
		
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
            
     
            FontData fontData = new FontData("Arial", 18, SWT.NORMAL);
 
            // Dispose of the existing font to avoid memory leaks
            if (gc.getFont() != null) {
                gc.getFont().dispose();
            }
 
            Font font = new Font(canvas.getDisplay(), fontData);
 
            // Set the new Font to the GC
            gc.setFont(font);
 
            // Calculate the position for the bottom right with an offset
            int fontX = canvas.getBounds().width - 500; 
            int fontY = canvas.getBounds().height - 50; 
 
            // Draw your health bar
            gc.drawText("Health: " + this.health, fontX, fontY);
 
            // Dispose of the Font to free up resources
            font.dispose();
  
            
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
	
	// getDisplay - Returns the display object.
	public Display getDisplay() {
		return display;
	}
	
	// getHealth - Returns how much health we have.
	public int getHealth() {
		return health;
	}
	
	
	// radarActive - Returns the boolean value of the radar being active.
	public boolean radarActive() {
		return radarActive;
	}
	
	// toggleRadar - Toggles the radar by setting the radarActive bool to its negation.
	public void toggleRadar() {
		radarActive = !radarActive;
	}
	
	// getDegree - Returns the degree.
	public float getDegree() {
		return this.degree;
	}
}
