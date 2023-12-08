package aircraftgame;

import org.eclipse.swt.widgets.Canvas;

public abstract class GameObject {
	float[] position = new float[2];	// [x,y]
	float[] offsets = new float[2];
	
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
	
	public void setOffsets(float xOffset, float yOffset) {
		offsets[0] = xOffset;
		offsets[1] = yOffset;
	}
	
	public abstract void draw(Canvas canvas); 
}
