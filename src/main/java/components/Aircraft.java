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
	
	Aircraft(float xPosition, float yPosition, float degree){
		super(xPosition, yPosition, degree);
		health = 10000;
	}
	
	/*	takeDamage()
	 * 	A function that subtracts the health of the aircraft according to some inputted damage.
	 */
	
	public void takeDamage(int damage) {
		this.health -= damage;
	}
	
	public void setDegree(float degree) {
        this.degree = degree;
        float radians = (float)Math.toRadians(degree);
    	directionVector.setDirection(radians);
	}
	
	public GunnerAI getGunnerAi() {
		return this.gunnerAI;
	}
}
