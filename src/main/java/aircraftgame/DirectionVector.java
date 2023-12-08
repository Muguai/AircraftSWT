package aircraftgame;

public class DirectionVector {
    private float x;
    private float y;

    public DirectionVector(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getX() { return x; }
    public float getY() { return y; }

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
