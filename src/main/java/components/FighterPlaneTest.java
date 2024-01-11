package components;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.swt.widgets.Display;
import org.junit.jupiter.api.Test;

class FighterPlaneTest {
	private final static Display display = new Display();
	
	@Test
	void directionalMovement() {
		float x = 0;
		float y = 0;
		FighterPlane testPlane = new FighterPlane(display, x, y, 0.0f, true);
		testPlane.moveObject(1.0f);
		assert(testPlane.getX() != 0.0f);
	}

	@Test
	void speedIncrease() {
		float x = 0;
		float y = 0;
		FighterPlane fastPlane = new FighterPlane(display, x, y, 0.0f, true);
		FighterPlane slowPlane = new FighterPlane(display, x, y, 0.0f, true);
		fastPlane.increaseSpeed();
		fastPlane.moveObject(1.0f);
		slowPlane.moveObject(1.0f);
		assert(fastPlane.getX() > slowPlane.getX());
	}
	
	@Test
	void speedDecrease() {
		float x = 0;
		float y = 0;
		FighterPlane fastPlane = new FighterPlane(display, x, y, 0.0f, true);
		FighterPlane slowPlane = new FighterPlane(display, x, y, 0.0f, true);
		slowPlane.decreaseSpeed();
		fastPlane.moveObject(1.0f);
		slowPlane.moveObject(1.0f);
		assert(fastPlane.getX() > slowPlane.getX());
	}
	
	@Test
	void takeDamage() {
		float x = 0;
		float y = 0;
		FighterPlane plane = new FighterPlane(display, x, y, 0.0f, true);
		int startHealth = plane.getHealth();
		plane.takeDamage(50);
		assert plane.getHealth() != startHealth;
	}
}
