package aircraftgame;

public abstract class Aircraft extends GameObject implements Move{
	Aircraft(int x, int y){
		super(x, y);
	}
	public abstract void draw();
}
