package components;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.swt.widgets.Display;
import org.junit.jupiter.api.Test;

class FighterPlaneTest {
	private final static Display display = new Display();
	
	@Test
	void checkMovement() {
		float x = 0;
		float y = 0;
		FighterPlane testPlane = new FighterPlane(display, x, y, 0.0f, true);
		testPlane.moveObject(1.0f);
		assert(testPlane.getX() != 0.0f);
	}

	@Test
	void checkSpeedIncrease() {
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
	void checkSpeedDecrease() {
		float x = 0;
		float y = 0;
		FighterPlane fastPlane = new FighterPlane(display, x, y, 0.0f, true);
		FighterPlane slowPlane = new FighterPlane(display, x, y, 0.0f, true);
		slowPlane.decreaseSpeed();
		fastPlane.moveObject(1.0f);
		slowPlane.moveObject(1.0f);
		assert(fastPlane.getX() > slowPlane.getX());
	}
}
