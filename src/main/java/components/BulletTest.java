package components;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.junit.jupiter.api.Test;

class BulletTest {
	private final static Display display = new Display();
	
	@Test
	void noAllyDamage() {
		FighterPlane aircraft = new FighterPlane(display, 0.0f, 0.0f, 0.0f, true);
		Bullet bullet = new Bullet(display, aircraft,  0.0f, 0.0f, true);
		assert(null == bullet.checkCollision(new ArrayList<Aircraft>(Arrays.asList(aircraft))));
	}
	
	@Test
	void noSelfDamage() {
		Player aircraft = new Player(display, 0.0f, 0.0f, 0.0f);
		Bullet bullet = new Bullet(display, aircraft,  0.0f, 0.0f, true);
		assert(null == bullet.checkCollision(new ArrayList<Aircraft>(Arrays.asList(aircraft))));
	}
	
	@Test
	void damageEnemy() {
		FighterPlane aircraft = new FighterPlane(display, 0.0f, 0.0f, 0.0f, false);
		Bullet bullet = new Bullet(display, aircraft,  0.0f, 0.0f, true);
		assert(null != bullet.checkCollision(new ArrayList<Aircraft>(Arrays.asList(aircraft))));
	}
}
