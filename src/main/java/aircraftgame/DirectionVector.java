package aircraftgame;

public class DirectionVector {
    private float x;
    private float y;

    public DirectionVector(float degree) {
        setDirection(degree);
    }
    
    // 	Simple getters:
    public float getX() { return x; }
    public float getY() { return y; }
    
    /*	setDirection()
     * 	Read in a degree and set the direction vector according to the degree.
     */
    
    public void setDirection(float degree) {
    	this.x = (float)Math.cos(degree);
    	this.y = (float)Math.sin(degree);
    }
    
    /*	magnitude()
     * 	Get the magnitude of the vector
     */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }
    
    /*	normalize()
     * 	Normalize the vector, i.e, make it's length to 1 (unit vector).
     * 	The magnitude cannot be zero, i.e, the zero vector.
     */
    
    public void normalize() {
        float mag = magnitude();
        if (mag != 0.0f) {
            x /= mag;
            y /= mag;
        }
    }
    
    /*	toString()
     * 	Simply return the vector as a string with both components of the vector
     */
    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
