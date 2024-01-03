package components;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public abstract class Aircraft extends MovableObject {
	protected int health;
	protected boolean friendly;
	protected GunnerAI gunnerAI;
	
	/*	[class constructor] Aircraft()
	 * 	The Aircraft() superclass should contain data that an Aircraft object would have.
	 *	As for movement and position, the Aircraft makes use of the superclass MovableObject.
	 *  @Param: xPosition - The x position in the cartesian plane, passed to parent MovableObject.
	 *  @Param: yPosition - The y position in the cartesian plane, passed to parent MovableObject.
	 *  @Param: degree - The degree to specify a direction vector, passed to parent MovableObject.
	 *  @See: The parent class, MovableObject
	 */
	
	public Aircraft(float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		health = 100;
	}
	
	/*	getHealth()
	 * 	Returns the health of the aircraft.
	 */
	
	public int getHealth() {
		return health;
	}
	
	/*	takeDamage()
	 * 	A function that subtracts the health of the aircraft according to some inputted damage.
	 */
	
	public void takeDamage(int damage) {
		this.health -= damage;
	}
	
	/*	setDegree()
	 * 	Sets the degree of the aircraft.
	 * 	This is done when the player turns or if enemies reach the end of the map.
	 */
	
	public void setDegree(float degree) {
        this.degree = degree;
        float radians = (float)Math.toRadians(degree);
    	directionVector.setDirection(radians);
	}
	
	/*	getGunnerAi()
	 * 	Simple getter for the gunner AI object inside Aircraft.
	 *  A gunner AI will fire bullets at nearby enemies.
	 */
	
	public GunnerAI getGunnerAi() {
		return this.gunnerAI;
	}
	
	/*	isFriendly()
	 * 	Returns the friendly boolean.
	 */
	
	public boolean isFriendly() {
		return this.friendly;
	}
}
