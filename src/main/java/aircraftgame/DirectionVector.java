package aircraftgame;

public class DirectionVector {
    private float x;
    private float y;

    public DirectionVector(float degree) {
        setDirection(degree);
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    
    public void setDirection(float degree) {
    	this.x = (float)Math.cos(degree);
    	this.y = (float)Math.sin(degree);
    }
    
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        float mag = magnitude();
        if (mag != 0.0f) {
            x /= mag;
            y /= mag;
        }
    }
    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
