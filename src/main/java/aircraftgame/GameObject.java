package aircraftgame;

public abstract class GameObject {
	int[] position = new int[2];	// [x,y]
	GameObject(int x, int y){
		position[0] = x;
		position[1] = y;
	}
	
	// Getters:
	public int getX(){
		return position[0];
	}
	public int getY() {
		return position[1];
	}
	public abstract void draw(/*SWT Canvas Component*/); 
}
