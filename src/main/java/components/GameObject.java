package components;

import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;


public abstract class GameObject {
	protected float[] position = new float[2];	// [x,y]
	protected float[] offsets = new float[2];
	protected float[] center = new float[2];
	protected PaintListener paintListener;
	protected boolean listenerActive;
	
	/*	[class constructor] GameObject
	 * 	This is the superclass of almost every object in the game.
	 * 	It is defined to have methods that can be called for each timestep in the game.
	 * 	@Param: x - The x position of the game object.
	 *  @Param: y - The y position of the game object.
	 */

	GameObject(float x, float y){
		position[0] = x;
		position[1] = y;
	}
	
	// Getters:
	public float getX(){
		return position[0];
	}
	public float getY() {
		return position[1];
	}
	
	/*	setOffsets()
	 * 	A function that sets gameObject offsets.
	 * 	Important: When a player object is moving we update player position but we always
	 * 	render player at the center of the screen. Instead of player moving every gameObject
	 * 	moves. This is done via using the player position as the offset and this function then
	 * 	updates the offset of the gameObject, relative the player object.
	 */
	
	public void setOffsets(float xOffset, float yOffset) {
		offsets[0] = xOffset;
		offsets[1] = yOffset;
	}
	
	/*	setCenter()
	 * 	A function that sets a centerpoint comprised of a x-component and an y-component.
	 *  This can be used to center an image for collision detection.
	 */
	
	public void setCenter(float x, float y) {
		center[0] = x;
		center[1] = y;
	}
	
	public float getCenterX(){
		return center[0];
	}
	
	public float getCenterY() {
		return center[1];
	}
	
	/*	draw()
	 * 	An abstract method that children of this class can use to render themselves onto a canvas.
	 */
	
	public abstract void draw(Canvas canvas); 
	
	/*	removePaintListener()
	 * 	A method that can be invoked by objects that are to be deleted.
	 *  This makes it so that the paintListener that the object is using is deleted.
	 *  (An example are bullets that hit the target, their paintListener is removed 
	 *   and they disappear at the next canvas.redraw())
	 */
	
	public void removePaintListener(Canvas canvas) {
		if(paintListener != null)
			canvas.removePaintListener(paintListener);
		paintListener = null;
	}
}
