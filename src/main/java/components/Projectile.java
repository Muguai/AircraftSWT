package components;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Canvas;

public abstract class Projectile extends MovableObject{
	private boolean friendly;
	private int damage;
	
	
	Projectile(float x, float y, float degree, boolean friendly, int damage){
		super(x, y, degree);
		this.friendly = friendly;
		this.damage = damage;
	}
	
	public void checkCollision(ArrayList<Aircraft> aircrafts) {
		
	}
	
	
}
