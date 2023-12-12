package eventListeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import components.GameWorld;

public class SpaceKeyListener implements KeyListener {
	
	private final GameWorld gameWorld;
	
	public SpaceKeyListener(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.keyCode == SWT.SPACE) {
			//gameWorld.getDataHandler()
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Not to use but must be implemented
	}
	
}
