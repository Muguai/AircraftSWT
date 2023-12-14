package eventListeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Display;

import components.Bullet;
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
			float offsetX = gameWorld.getCanvas().getBounds().width/2;
			float offsetY = gameWorld.getCanvas().getBounds().height/2;
			gameWorld.getDataHandler().addGameObject(new Bullet(display, player, offsetX, offsetY, true));
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Not to use but must be implemented
	}
	
}
