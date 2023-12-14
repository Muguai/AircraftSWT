package eventListeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Display;

import components.Bullet;
import components.Explosion;
import components.GameWorld;
import components.Player;

public class SpaceKeyListener implements KeyListener {
	
	private final GameWorld gameWorld;
	
	public SpaceKeyListener(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.keyCode == SWT.SPACE) {
			Display display = gameWorld.getDisplay();
			Player player = gameWorld.getDataHandler().getPlayer();
			gameWorld.getDataHandler().addGameObject(new Bullet(display, player));
		}
		if (e.keyCode == SWT.TAB) {
			Display display = gameWorld.getDisplay();
			Player player = gameWorld.getDataHandler().getPlayer();
			gameWorld.getDataHandler().addGameObject(new Explosion(display, player.getX(), player.getY()));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Not to use but must be implemented
	}
	
}
