package components;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.swt.widgets.Display;
import org.junit.jupiter.api.Test;

class FighterPlaneTest {

	@Test
	void checkMovement() {
		Display display = new Display();
		float x = 0;
		float y = 0;
		FighterPlane testPlane = new FighterPlane(display, x, y, 0.0f, true);
		testPlane.moveObject(1.0f);
		assert(testPlane.getX() != 0.0f);
	}

}
