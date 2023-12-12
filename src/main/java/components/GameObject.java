package components;

import org.eclipse.swt.widgets.Canvas;


public abstract class GameObject {
	float[] position = new float[2];	// [x,y]
	float[] offsets = new float[2];
	
	
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
	
	/*	draw()
	 * 	An abstract method that children of this class can use to render themselves onto a canvas.
	 */
	
	public abstract void draw(Canvas canvas); 
}
